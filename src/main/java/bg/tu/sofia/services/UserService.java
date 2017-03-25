package bg.tu.sofia.services;

import java.util.List;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;

@Component
public interface UserService {

	List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId);

	void insertInhabitant(UserDto user);

}
