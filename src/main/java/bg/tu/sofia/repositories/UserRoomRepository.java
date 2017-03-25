package bg.tu.sofia.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import bg.tu.sofia.entities.UserRoom;

public interface UserRoomRepository extends PagingAndSortingRepository<UserRoom, Integer>{

}
