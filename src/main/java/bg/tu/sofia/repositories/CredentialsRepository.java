package bg.tu.sofia.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import bg.tu.sofia.entities.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Integer> {

	// Query - bg.tu.sofia.entities.Credentials
	public Credentials findByUserIdAndPassword(@Param("userId") int id,@Param("password") String password);

}
