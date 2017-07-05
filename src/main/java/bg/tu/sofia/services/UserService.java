package bg.tu.sofia.services;

import java.util.List;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.utils.StructuredResponse;

@Component
public interface UserService {

	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId);

	public StructuredResponse insertUser(UserDto user);
	
	public UserDto getPersonWithNightTaxes(int userId);
	
	public List<UserDto> getPeopleWithNightTaxes(String marker, String blockId, int pageNumber);
	
	public String getPaginationPeopleWithNightTaxes(String marker, String blockId, int pageNumber);
	
	public User authenticateUser(String personalNumber, String password) throws Exception;

}
