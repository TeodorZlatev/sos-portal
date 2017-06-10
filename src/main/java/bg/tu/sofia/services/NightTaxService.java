package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.utils.StructuredResponse;

public interface NightTaxService {
	public StructuredResponse createNightTax(NightTaxDto nightTax);
	
	public List<NightTaxDto> getNightTaxesOfUser(int userId, NightTaxStatusEnum status, int pageNumber);
	
	public String getCountNightTaxes(int userId, NightTaxStatusEnum status, int pageNumber);
}
