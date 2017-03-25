package bg.tu.sofia.dtos;

import bg.tu.sofia.entities.User;

public class UserDto {
	private int id;
	private String username;
	private String personalNumber;
	private String room;
	private int roleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public User toEntity() {
		User user = new User();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setPersonalNumber(this.getPersonalNumber());
		// TODO: role
		return user;
	}

	public static UserDto fromEntity(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setPersonalNumber(user.getPersonalNumber());
		userDto.setRoleId(user.getRole().getId());
		return userDto;
	}

}
