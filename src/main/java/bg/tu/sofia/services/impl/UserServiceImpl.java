package bg.tu.sofia.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.RoleEnum;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.dtos.UserRoomDto;
import bg.tu.sofia.entities.Credentials;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.entities.Token;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.CredentialsRepository;
import bg.tu.sofia.repositories.TokenRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.RoomService;
import bg.tu.sofia.services.UserRoomService;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.PageUtil;

@Component
public class UserServiceImpl implements UserService {
	
	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UserRoomService userRoomService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private PageUtil pageUtil;

	@Override
	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId) {
		List<User> users = userRepository.findAllByRoomIdAndBlockId(roomId, blockId);

		return users.stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public void insertInhabitant(UserDto userDto) {
		//TODO: validation save user, but not userRoom
		User user = this.toEntity(userDto);
		
		userRepository.save(user);
		
		RoomDto roomDto = roomService.getByNumberAndBlockId(userDto.getRoom(), 1);
		
		UserRoomDto userRoomDto = new UserRoomDto();
		userRoomDto.setUserId(user.getId());
		userRoomDto.setRoomId(roomDto.getId());
		
		userRoomService.insertUserRoom(userRoomDto);
	}

	@Override
	public List<UserDto> getPeopleWithNightTaxes(int blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);

		Page<User> users = userRepository.getPageOfPeopleWithTaxesByBlockId(blockId, pageable);
		
		return users.getContent().stream().map(this::fromEntity).collect(Collectors.toList());

	}
	
	@Override
	public List<UserDto> getPeopleWithNightTaxesByMarker(String marker, int blockId, int pageNumber) {
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		
		Page<User> users = userRepository.getPageOfPeopleWithTaxesByBlockIdAndMarker("%" + marker + "%", blockId, pageable);
		
		return users.getContent().stream().map(this::fromEntity).collect(Collectors.toList());
	}
	
	@Override
	public String getCountPeopleWithNightTaxes(int blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(0, PAGE_SIZE);

		int pagesCount = userRepository.getPageOfPeopleWithTaxesByBlockId(blockId, pageable).getTotalPages();
		
		return pageUtil.createPagination(pageNumber, pagesCount);
	}
	
	public Integer authenticateUser(String personalNumber, String password) throws Exception  {
		User user = this.userRepository.findByPersonalNumber(personalNumber);
		
		if (user == null) {
			throw new Exception("user");
		}
		
		Credentials credentials = this.credentialsRepository.findByUserIdAndPassword(user.getId(), password);
		
		if (credentials == null) {
			throw new Exception("password");
		}
		
		return user.getId();
	}

	public void insertToken(int userId, String token) {
		Token mainToken = new Token();
		
		mainToken.setToken(token);
		mainToken.setUserId(userId);
		
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		
		mainToken.setExpirationDate(date);
		
		this.tokenRepository.save(mainToken);
	}
	
	private User toEntity(UserDto userDto) {
		User user = new User();
		
		user.setId(userDto.getId());
		user.setUsername(userDto.getUsername());
		user.setPersonalNumber(userDto.getPersonalNumber());

		Role role = new Role();
		role.setId(RoleEnum.INHABITED.getId());
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
		
		return userDto;
	}

}