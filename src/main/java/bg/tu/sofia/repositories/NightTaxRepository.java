package bg.tu.sofia.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import bg.tu.sofia.entities.NightTax;

public interface NightTaxRepository extends PagingAndSortingRepository<NightTax, Integer>{

}
