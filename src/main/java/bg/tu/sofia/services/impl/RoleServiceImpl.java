package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.RoleDto;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.repositories.RoleRepository;
import bg.tu.sofia.services.RoleService;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleDto getRoleById(int id) {
		return this.fromEntity(roleRepository.findOne(id));
	}
	
	private RoleDto fromEntity(Role role){
		RoleDto roleDto = new RoleDto();
		
		roleDto.setId(role.getId());
		roleDto.setName(role.getName().toString());
		
		return roleDto;
	}
	
	private Role toEntity(RoleDto roleDto){
		Role role = new Role();
		
		role.setId(roleDto.getId());
		role.setName(roleDto.getName());
		
		return role;
	}

}
