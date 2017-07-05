package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserRoomDto;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.entities.UserRoom;
import bg.tu.sofia.repositories.UserRoomRepository;
import bg.tu.sofia.services.UserRoomService;

@Component
public class UserRoomServiceImpl implements UserRoomService {
	
	@Autowired
	private UserRoomRepository userRoomRepository; 

	@Override
	public void insertUserRoom(UserRoomDto userRoomDto) {
		userRoomRepository.save(this.toEntity(userRoomDto));
	}

	private UserRoomDto fromEntity(UserRoom userRoom) {
		UserRoomDto userRoomDto = new UserRoomDto();
		
		userRoomDto.setId(userRoom.getId());
		userRoomDto.setUserId(userRoom.getUser().getId());
		userRoomDto.setRoomId(userRoom.getRoom().getId());
		
		return userRoomDto;
	}

	private UserRoom toEntity(UserRoomDto userRoomDto) {
		UserRoom userRoom = new UserRoom();
		
		userRoom.setId(userRoomDto.getId());

		User user = new User();
		user.setId(userRoomDto.getUserId());
		userRoom.setUser(user);
		
		Room room = new Room();
		room.setId(userRoomDto.getRoomId());
		userRoom.setRoom(room);

		return userRoom;
	}

}
