package bg.tu.sofia.dtos;

import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.entities.UserRoom;

public class UserRoomDto {
	private int id;

	private int userId;

	private int roomId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public static UserRoomDto fromEntity(UserRoom userRoom) {
		UserRoomDto userRoomDto = new UserRoomDto();
		userRoomDto.setId(userRoom.getId());
		userRoomDto.setUserId(userRoom.getUser().getId());
		userRoomDto.setRoomId(userRoom.getRoom().getId());
		return userRoomDto;
	}

	public UserRoom toEntity() {
		UserRoom userRoom = new UserRoom();
		userRoom.setId(this.getId());

		User user = new User();
		user.setId(this.getUserId());
		userRoom.setUser(user);
		
		Room room = new Room();
		room.setId(this.getRoomId());
		userRoom.setRoom(room);

		return userRoom;
	}

}
