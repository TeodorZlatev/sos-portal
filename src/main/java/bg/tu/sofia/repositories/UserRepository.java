package bg.tu.sofia.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import bg.tu.sofia.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

	public List<User> findAllByRoomIdAndBlockId(int roomId, int blockId);
}
