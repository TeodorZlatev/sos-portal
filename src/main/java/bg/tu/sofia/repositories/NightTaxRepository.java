package bg.tu.sofia.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.entities.NightTax;

public interface NightTaxRepository extends PagingAndSortingRepository<NightTax, Integer> {

	// Query - bg.tu.sofia.entities.NightTax
	public Page<NightTax> findByUserIdAndStatus(@Param("userId") int userId, @Param("status") NightTaxStatusEnum status,
			Pageable pageable);

	public List<NightTax> findByUserIdAndGuestNameAndDate(@Param("userId") int userId, @Param("guestName") String guestName,
			@Param("date") Date date);
}
