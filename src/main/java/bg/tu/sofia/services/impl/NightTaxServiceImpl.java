package bg.tu.sofia.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.NightTaxService;
import bg.tu.sofia.utils.DateUtil;
import bg.tu.sofia.utils.PageUtil;

@Component
public class NightTaxServiceImpl implements NightTaxService {

	private static final int PAGE_SIZE = 1;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private NightTaxRepository nightTaxRepository;

	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private PageUtil pageUtil;

	@Override
	public void createNightTax(NightTaxDto nightTaxDto) {
		NightTax nightTax = nightTaxDto.toEntity();
		
		nightTax.setHost(userRepository.findOne(nightTaxDto.getHostId()));
		nightTax.setRoom(roomRepository.findOne(nightTaxDto.getRoomId()));
		// guestName
		nightTax.setDate(dateUtil.convertFromStringToDate(nightTaxDto.getDate()));
		nightTax.setCreator(userRepository.findOne(1));
		nightTax.setStatus(NightTaxStatusEnum.UNPAID);
		nightTax.setDateCreated(new Date());
		
		nightTaxRepository.save(nightTax);
	}

	@Override
	public List<NightTaxDto> getNightTaxesOfUser(int userId, NightTaxStatusEnum status, int pageNumber) {
		
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "dateCreated");

		Page<NightTax> nightTaxes = nightTaxRepository.findByUserIdAndStatus(userId, status, request);

		List<NightTaxDto> nightTaxesDto = new ArrayList<>();

		nightTaxes.forEach(nightTax -> {
			NightTaxDto nightTaxDto = NightTaxDto.fromEntity(nightTax);

			nightTaxDto.setRoomNumber(roomRepository.findOne(nightTax.getRoom().getId()).getNumber());

			nightTaxDto.setHostName(userRepository.findOne(nightTax.getHost().getId()).getUsername());

			nightTaxDto.setCreatorName(userRepository.findOne(nightTax.getCreator().getId()).getUsername());

			nightTaxesDto.add(nightTaxDto);
		});

		return nightTaxesDto;
	}


	@Override
	public String getCountNightTaxes(int userId, NightTaxStatusEnum status, int pageNumber) {

		PageRequest pageable = new PageRequest(0, PAGE_SIZE);

		int pagesCount = nightTaxRepository.findByUserIdAndStatus(userId, status, pageable).getTotalPages();
		
		return pageUtil.createPagination(pageNumber, pagesCount);
	}

}
