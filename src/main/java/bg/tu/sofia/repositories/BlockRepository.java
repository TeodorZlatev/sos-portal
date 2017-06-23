package bg.tu.sofia.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import bg.tu.sofia.entities.Block;

public interface BlockRepository extends CrudRepository<Block, Integer> {
	public Block findByHostId(int hostId);
	
	public List<Block> findAll();
}
