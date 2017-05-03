package bg.tu.sofia.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.RoleEnum;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.dtos.UserRoomDto;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.RoleService;
import bg.tu.sofia.services.RoomService;
import bg.tu.sofia.services.UserRoomService;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.PageUtil;

@Component
public class UserServiceImpl implements UserService {
	
	private static final int PAGE_SIZE = 1;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoomService userRoomService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PageUtil pageUtil;

	@Override
	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId) {
		List<User> users = userRepository.findAllByRoomIdAndBlockId(roomId, blockId);
		List<UserDto> usersDto = new ArrayList<>();
		users.forEach(user -> {
			UserDto userDto = UserDto.fromEntity(user);
			usersDto.add(userDto);
		});
		return usersDto;
	}

	@Override
	public void insertInhabitant(UserDto userDto) {
		//TODO: validation save user, but not userRoom
		User user = userDto.toEntity();
		
		Role role = new Role();
		role.setId(RoleEnum.INHABITED.getId());
		user.setRole(role);
		
		userRepository.save(user);
		
		RoomDto roomDto = roomService.getByRoomAndBlockId(userDto.getRoom(), 1);
		
		UserRoomDto userRoomDto = new UserRoomDto();
		userRoomDto.setUserId(user.getId());
		userRoomDto.setRoomId(roomDto.getId());
		userRoomService.insertUserRoom(userRoomDto);
	}

	@Override
	public List<UserDto> getPeopleWithNightTaxes(int blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "username");

		Page<User> users = userRepository.getPageOfPeopleWithTaxesByBlockId(blockId, pageable);
		List<UserDto> usersDto = new ArrayList<>();

		users.forEach(user -> {
			UserDto userDto = UserDto.fromEntity(user);

			usersDto.add(userDto);
		});

		return usersDto;
	}
	
	@Override
	public String getCountPeopleWithNightTaxes(int blockId, int pageNumber) {

		PageRequest pageable = new PageRequest(0, PAGE_SIZE);

		int pagesCount = userRepository.getPageOfPeopleWithTaxesByBlockId(blockId, pageable).getTotalPages();
		
		String page = pageUtil.createPagination(pageNumber, pagesCount);
		return page;
	}
}
