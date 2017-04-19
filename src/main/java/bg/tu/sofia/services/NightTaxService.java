package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;

public interface NightTaxService {
	public void createNightTax(NightTaxDto nightTax);
	
	public List<NightTaxDto> getNightTaxesOfUser(int userId, NightTaxStatusEnum status, int pageNumber);
	
	public String getCountNightTaxes(int userId, NightTaxStatusEnum status, int pageNumber);
}
