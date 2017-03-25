package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserRoomDto;
import bg.tu.sofia.entities.UserRoom;
import bg.tu.sofia.repositories.UserRoomRepository;
import bg.tu.sofia.services.UserRoomService;

@Component
public class UserRoomServiceImpl implements UserRoomService{
	
	@Autowired
	private UserRoomRepository userRoomRepository; 

	@Override
	public void insertUserRoom(UserRoomDto userRoomDto) {
		UserRoom userRoom = userRoomDto.toEntity();
		userRoomRepository.save(userRoom);
	}

}
