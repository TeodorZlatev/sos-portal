package bg.tu.sofia.services;

import java.util.List;

import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.utils.StructuredResponse;

@Component
public interface UserService {

	public List<UserDto> getAllByRoomIdAndBlockId(int roomId, int blockId);

	public StructuredResponse insertInhabitant(UserDto user);
	
	public List<UserDto> getPeopleWithNightTaxes(int blockId, int pageNumber);
	
	public List<UserDto> getPeopleWithNightTaxesByMarker(String marker, int blockId, int pageNumber);
	
	public String getCountPeopleWithNightTaxes(int blockId, int pageNumber);
	
	public Integer authenticateUser(String personalNumber, String password) throws Exception;

	public void insertToken(int userId, String token);

}
