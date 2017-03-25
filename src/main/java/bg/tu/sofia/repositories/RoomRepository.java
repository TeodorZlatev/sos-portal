package bg.tu.sofia.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import bg.tu.sofia.entities.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Integer>{

	public List<Room> findAllByBlockId(int blockId);

	public Room findByNumberAndBlockId(String roomNumber, int blockId);
}
