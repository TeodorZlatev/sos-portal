package bg.tu.sofia.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import bg.tu.sofia.entities.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {

	public List<Room> findAllByBlockIdOrderByNumberAsc(int blockId);

	public Room findByNumberAndBlockId(String roomNumber, int blockId);

	public Room findByUserId(@Param("userId") int userId);
}
