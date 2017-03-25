package bg.tu.sofia.dtos;

import bg.tu.sofia.entities.Role;

public class RoleDto {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static RoleDto fromEntity(Role role){
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}
	
	public Role toEntity(){
		Role role = new Role();
		role.setId(this.getId());
		role.setName(this.getName());
		return role;
	}
}
