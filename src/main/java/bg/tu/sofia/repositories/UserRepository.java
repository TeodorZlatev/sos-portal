package bg.tu.sofia.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import bg.tu.sofia.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	//Query - bg.tu.sofia.entities.User
	public List<User> findAllByRoomIdAndBlockId(@Param("roomId") int roomId, @Param("blockId") int blockId);
	
	//Query - bg.tu.sofia.entities.User
	public Page<User> getPageOfPeopleWithTaxesByBlockId(@Param("blockId") int blockId, Pageable pageable);
	
}
