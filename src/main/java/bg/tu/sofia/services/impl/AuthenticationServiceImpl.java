package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.Constants;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.security.transfer.JwtUserDto;
import bg.tu.sofia.security.util.JwtTokenGenerator;
import bg.tu.sofia.services.AuthenticationService;
import bg.tu.sofia.services.BlockService;
import bg.tu.sofia.services.RoomService;
import bg.tu.sofia.services.UserService;
import bg.tu.sofia.utils.StructuredResponse;
import bg.tu.sofia.utils.StructuredResponse.RESPONSE_STATUS;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired 
	private BlockService blockService;
	
	@Autowired 
	private JwtTokenGenerator jwtTokenGenerator;

	@Override
	public StructuredResponse authenticate(String personalNumber, String password) {
		try {
			User user = this.userService.authenticateUser(personalNumber, password);

			if (user != null) {
				
				JwtUserDto jwtUser = new JwtUserDto();
				jwtUser.setId(user.getId());
				jwtUser.setRole(user.getRole().getName().toString());
				
				RoomDto room = this.roomService.getRoomByUserId(user.getId());
				jwtUser.setRoomId(room.getId() + "");
				
				if ("ROLE_HOST".equals(user.getRole().getName())) {
					jwtUser.setBlockId(this.blockService.getBlockIdByUserId(user.getId()) + "");
				} else if ("ROLE_INHABITED".equals(user.getRole().getName())) {
					jwtUser.setBlockId(room.getBlockId() + "");
				}
				
				String token = jwtTokenGenerator.generateToken(jwtUser, Constants.SECRET_KEY);
				
				return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, token, null);
			}

			return new StructuredResponse(401, RESPONSE_STATUS.FAIL, null, "Невалидни данни");
		} catch (Exception e) {
			return new StructuredResponse(401, RESPONSE_STATUS.FAIL, null, "Невалидни данни");
		}
	}

}
