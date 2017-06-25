package bg.tu.sofia.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.Constants;
import bg.tu.sofia.dtos.BlockDto;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.dtos.UserRoomDto;
import bg.tu.sofia.entities.Credentials;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.CredentialsRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.security.util.TextHasher;
import bg.tu.sofia.services.BlockService;
import bg.tu.sofia.services.RoomService;
import bg.tu.sofia.services.UserRoomService;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.MailUtil;
import bg.tu.sofia.utils.PageUtil;
import bg.tu.sofia.utils.StructuredResponse;
import bg.tu.sofia.utils.StructuredResponse.RESPONSE_STATUS;

@Component
public class UserServiceImpl implements UserService {

	private static final int PAGE_SIZE = 10;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private UserRoomService userRoomService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private BlockService blockService;

	@Autowired
	private PageUtil pageUtil;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private TextHasher textHasher;

	@Override
	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId) {
		List<User> users = userRepository.findAllByRoomIdAndBlockId(roomId, blockId);

		return users.stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public StructuredResponse insertUser(UserDto userDto) {
		// TODO: validation save user, but not userRoom
		StringBuilder sb = new StringBuilder();

		if (userDto.getUsername() == null || !userDto.getUsername().matches("[А-Яа-я]+[ ]*[А-Яа-я]*[ ]*[А-Яа-я]*")) {
			sb.append("Невалидни имена! (пр. Иван Иванов Иванов)</br>");
		}

		if (userDto.getPersonalNumber() == null || !userDto.getPersonalNumber().matches("[\\d]{10}")) {
			sb.append("Невалидно ЕГН! (пр. 9402024124)</br>");
		}

		if (userDto.getEmail() == null || !userDto.getEmail().matches("[A-Za-z0-9._]+@[a-z]+.[a-z]+")) {
			sb.append("Невалиден email! (пр. test_1@gmail.com)</br>");
		}

		// inhabitant or host
		if ((userDto.getRoleId() == 2 || userDto.getRoleId() == 1)
				&& (userDto.getBlockId() == null || !userDto.getBlockId().matches("[1-9]+[0-9]*"))) {
			sb.append("Невалиден избор на блок!</br>");
		}

		// inhabitant
		// check for 0, because of insertInhabitant by host, the role is
		// INHABITED default
		if ((userDto.getRoleId() == 0 || userDto.getRoleId() == 1)
				&& (userDto.getRoomId() == null || !userDto.getRoomId().matches("[0-9]+[А-Яа-яA-Za-z]*"))) {
			sb.append("Невалиден избор на стая!");
		}

		if (sb.length() != 0) {
			return new StructuredResponse(400, RESPONSE_STATUS.FAIL, null, sb.toString());
		}

		User user = this.toEntity(userDto);

		// if ROLE_HOST has made a request, the role is default
		// (ROLE_INHABITANT)
		if (user.getRole().getId() == 0) {
			user.getRole().setId(1);
		}

		userRepository.save(user);

		Credentials credentials = new Credentials();
		credentials.setUser(user);
		String password = textHasher.get_SHA_512_SecurePassword(user.getPersonalNumber(), Constants.SALT);
		credentials.setPassword(password);

		credentialsRepository.save(credentials);

		user = userRepository.save(user);
		userDto.setId(user.getId());

		StructuredResponse response = null;

		switch (user.getRole().getId()) {
		case 1:
			response = completeInhabitantInsertion(userDto);
			break;
		case 2:
			response = completeHostInsertion(userDto);
			break;
		case 3:
		case 4:
			response = completeCashierOrAdministratorInsertion(userDto);
			break;
		}

		return response;

	}

	private StructuredResponse completeInhabitantInsertion(UserDto user) {
		RoomDto roomDto = roomService.getByRoomId(Integer.parseInt(user.getRoomId()));

		UserRoomDto userRoomDto = new UserRoomDto();
		userRoomDto.setUserId(user.getId());
		userRoomDto.setRoomId(roomDto.getId());

		userRoomService.insertUserRoom(userRoomDto);

		BlockDto blockDto = blockService.getBlockById(roomDto.getBlockId());

		String message = "Успешно вписан живущ. Име: " + user.getUsername() + ", ЕГН: " + user.getPersonalNumber()
				+ ", стая: " + roomDto.getNumber() + ", email: " + user.getEmail();

		mailUtil.sendMailForRegistration(user, "живущ", roomDto.getNumber(), blockDto.getNumber());

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, message);
	}

	private StructuredResponse completeHostInsertion(UserDto user) {
		BlockDto blockDto = blockService.getBlockById(Integer.parseInt(user.getBlockId()));

		blockDto.setHostId(user.getId());
		blockService.saveOrUpdateBlock(blockDto);

		String message = "Успешно вписан домакин. Име: " + user.getUsername() + ", ЕГН: " + user.getPersonalNumber()
				+ ", блок: " + blockDto.getNumber() + ", email: " + user.getEmail();

		mailUtil.sendMailForRegistration(user, "домакин", null, blockDto.getNumber());

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, message);
	}

	private StructuredResponse completeCashierOrAdministratorInsertion(UserDto user) {
		String role = user.getRoleId() == 3 ? "касиер" : "администратор";
		String message = "Успешно вписан " + role + " . Име: " + user.getUsername() + ", ЕГН: "
				+ user.getPersonalNumber() + ", email: " + user.getEmail();

		mailUtil.sendMailForRegistration(user, role, null, null);

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, message);
	}

	@Override
	public UserDto getPersonWithNightTaxes(int userId) {

		User user = userRepository.getPersonWithNightTaxesByUserId(userId);
		if (user == null) {
			return null;
		}

		return this.fromEntity(user);
	}

	@Override
	public List<UserDto> getPeopleWithNightTaxes(String blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);

		Page<User> users;
		if (blockId != null) {
			int blockIdInt = Integer.parseInt(blockId);

			users = userRepository.getPageOfPeopleWithTaxesByBlockId(blockIdInt, pageable);
		} else {
			users = userRepository.getPageOfPeopleWithTaxes(pageable);
		}
		return users.getContent().stream().map(this::fromEntity).collect(Collectors.toList());

	}

	@Override
	public List<UserDto> getPeopleWithNightTaxesByMarker(String marker, String blockId, int pageNumber) {
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);

		Page<User> users = null;

		if (blockId != null) {

			int blockIdInt = Integer.parseInt(blockId);

			if (StringUtils.isBlank(marker)) {
				users = userRepository.getPageOfPeopleWithTaxesByBlockId(blockIdInt, pageable);
			} else {
				users = userRepository.getPageOfPeopleWithTaxesByBlockIdAndMarker("%" + marker + "%", blockIdInt,
						pageable);
			}
		} else {
			if (StringUtils.isBlank(marker)) {
				users = userRepository.getPageOfPeopleWithTaxes(pageable);
			} else {
				users = userRepository.getPageOfPeopleWithTaxesByMarker("%" + marker + "%", pageable);
			}
		}

		return users.getContent().stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public String getCountPeopleWithNightTaxes(String blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(0, PAGE_SIZE);

		int pagesCount;

		if (blockId != null) {
			int blockIdInt = Integer.parseInt(blockId);

			pagesCount = userRepository.getPageOfPeopleWithTaxesByBlockId(blockIdInt, pageable).getTotalPages();
		} else {
			pagesCount = userRepository.getPageOfPeopleWithTaxes(pageable).getTotalPages();
		}

		return pageUtil.createPagination(pageNumber, pagesCount);
	}

	public User authenticateUser(String personalNumber, String password) throws Exception {
		User user = this.userRepository.findByPersonalNumber(personalNumber);

		if (user == null) {
			throw new Exception("user");
		}

		Credentials credentials = this.credentialsRepository.findByUserIdAndPassword(user.getId(), password);

		if (credentials == null) {
			throw new Exception("password");
		}

		return user;
	}

	private User toEntity(UserDto userDto) {
		User user = new User();

		user.setId(userDto.getId());
		user.setUsername(userDto.getUsername());
		user.setPersonalNumber(userDto.getPersonalNumber());
		user.setEmail(userDto.getEmail());

		Role role = new Role();
		role.setId(userDto.getRoleId());
		user.setRole(role);

		return user;
	}

	private UserDto fromEntity(User user) {
		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setPersonalNumber(user.getPersonalNumber());
		userDto.setEmail(user.getEmail());
		userDto.setRoleId(user.getRole().getId());

		RoomDto room = roomService.getRoomByUserId(user.getId());
		userDto.setRoomId(room.getId() + "");
		userDto.setRoomNumber(room.getNumber());

		BlockDto block = blockService.getBlockById(room.getBlockId());
		userDto.setBlockId(block.getId() + "");
		userDto.setBlockNumber(block.getNumber());

		return userDto;
	}

}