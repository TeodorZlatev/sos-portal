package bg.tu.sofia.services;

import java.util.List;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;

@Component
public interface UserService {

	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId);

	public void insertInhabitant(UserDto user);
	
	public List<UserDto> getPeopleWithNightTaxes(int blockId, int pageNumber);
	
	public String getCountPeopleWithNightTaxes(int blockId, int pageNumber);

}
