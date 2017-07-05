package bg.tu.sofia.services.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.tu.sofia.SosPortalApplication;
import bg.tu.sofia.dtos.RoleDto;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.repositories.RoleRepository;
import bg.tu.sofia.services.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SosPortalApplication.class)
public class RoleServiceTests {
	
	private static final String ROLE = "role";
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testGetRoleById() {
		Role role = new Role();
		role.setName(ROLE);
		
		role = roleRepository.save(role);
		
		RoleDto roleDto = roleService.getRoleById(role.getId());
		
		assertEquals(roleDto.getName(), role.getName());
	}

}
