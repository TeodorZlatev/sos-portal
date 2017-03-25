package bg.tu.sofia.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import bg.tu.sofia.entities.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

}
