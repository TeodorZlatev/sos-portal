package bg.tu.sofia.repositories;

import org.springframework.data.repository.CrudRepository;

import bg.tu.sofia.entities.Block;

public interface BlockRepository extends CrudRepository<Block, Integer> {
	public Block findByUserId(int userId);
}
