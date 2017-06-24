package bg.tu.sofia.services;

import java.util.List;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.utils.StructuredResponse;

@Component
public interface UserService {

	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId);

	public StructuredResponse insertInhabitant(UserDto user);
	
	public UserDto getPersonWithNightTaxes(int userId);
	
	public List<UserDto> getPeopleWithNightTaxes(String blockId, int pageNumber);
	
	public List<UserDto> getPeopleWithNightTaxesByMarker(String marker, String blockId, int pageNumber);
	
	public String getCountPeopleWithNightTaxes(String blockId, int pageNumber);
	
	public User authenticateUser(String personalNumber, String password) throws Exception;

}
