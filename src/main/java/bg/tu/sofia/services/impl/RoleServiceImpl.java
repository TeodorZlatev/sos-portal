package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.RoleDto;
import bg.tu.sofia.repositories.RoleRepository;
import bg.tu.sofia.services.RoleService;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleDto getRoleById(int id) {
		return RoleDto.fromEntity(roleRepository.findOne(id));
	}

}
