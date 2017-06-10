package bg.tu.sofia.repositories;

import org.springframework.data.repository.CrudRepository;

import bg.tu.sofia.entities.Token;

public interface TokenRepository extends CrudRepository<Token, Integer>{

}
