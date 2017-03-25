package bg.tu.sofia.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoomService userRoomService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoleService roleService;

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
}
