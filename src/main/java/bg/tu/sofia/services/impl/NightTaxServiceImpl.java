package bg.tu.sofia.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.NightTaxService;
import bg.tu.sofia.utils.DateUtil;

@Component
public class NightTaxServiceImpl implements NightTaxService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private NightTaxRepository nightTaxRepository;

	@Autowired
	private DateUtil dateUtil;

	@Override
	public void createNightTax(NightTaxDto nightTaxDto) {
		NightTax nightTax = nightTaxDto.toEntity();
		nightTax.setHost(userRepository.findOne(nightTaxDto.getHostId()));
		nightTax.setRoom(roomRepository.findOne(nightTaxDto.getRoomNumber()));
		// guestName
		nightTax.setDate(dateUtil.convertFromStringToDate(nightTaxDto.getDate()));
		nightTax.setCreator(userRepository.findOne(1));
		nightTax.setStatus(NightTaxStatusEnum.UNPAID.toString());
		nightTax.setDateCreated(new Date());
		nightTaxRepository.save(nightTax);
	}

}
