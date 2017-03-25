package bg.tu.sofia.services;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserRoomDto;

@Component
public interface UserRoomService {

	public void insertUserRoom(UserRoomDto userRoomDto);
}
