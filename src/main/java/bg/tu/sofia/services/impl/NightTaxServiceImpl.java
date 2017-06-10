package bg.tu.sofia.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.NightTaxService;
import bg.tu.sofia.utils.DateUtil;
import bg.tu.sofia.utils.MailUtil;
import bg.tu.sofia.utils.PageUtil;
import bg.tu.sofia.utils.StructuredResponse;
import bg.tu.sofia.utils.StructuredResponse.RESPONSE_STATUS;

@Component
public class NightTaxServiceImpl implements NightTaxService {

	private static final int PAGE_SIZE = 10;

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

	@Autowired
	private MailUtil mailUtil;

	@Override
	public StructuredResponse createNightTax(NightTaxDto nightTaxDto) {

		String message;

		// expect list, because of more than one existing night taxes, otherwise
		// Exception
		List<NightTax> existingNightTaxes = nightTaxRepository.findByUserIdAndGuestNameAndDate(nightTaxDto.getHostId(),
				nightTaxDto.getGuestName(), dateUtil.convertFromStringToDate(nightTaxDto.getDate()));

		if (existingNightTaxes.size() > 0) {
			message = "Текущата нощувка е въведена. Домакин: " + nightTaxDto.getHostName() + ", гост: "
					+ nightTaxDto.getGuestName() + ", дата: " + nightTaxDto.getDate();

			return new StructuredResponse(409, RESPONSE_STATUS.FAIL, null, message);
		}

		NightTax nightTax = toEntity(nightTaxDto);

		nightTax = nightTaxRepository.save(nightTax);

		message = "Успешно създадена нощувка. Домакин: " + nightTax.getHost().getUsername() + ", гост: "
				+ nightTax.getGuestName() + ", дата: " + dateUtil.convertFromDateToString(nightTax.getDate());

		User host = nightTax.getHost();
		
		mailUtil.sendMail(host.getEmail(), host.getUsername(), nightTaxDto.getGuestName(), nightTaxDto.getDate());

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, message);
	}

	@Override
	public List<NightTaxDto> getNightTaxesOfUser(int userId, NightTaxStatusEnum status, int pageNumber) {

		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);

		Page<NightTax> nightTaxes = nightTaxRepository.findByUserIdAndStatus(userId, status, request);

		return nightTaxes.getContent().stream().map(this::fromEntity).collect(Collectors.toList());
	}

	@Override
	public String getCountNightTaxes(int userId, NightTaxStatusEnum status, int pageNumber) {

		PageRequest pageable = new PageRequest(0, PAGE_SIZE);

		int pagesCount = nightTaxRepository.findByUserIdAndStatus(userId, status, pageable).getTotalPages();

		return pageUtil.createPagination(pageNumber, pagesCount);
	}
	
	private NightTax toEntity(NightTaxDto nightTaxDto) {
		NightTax nightTax = new NightTax();

		User host = userRepository.findOne(nightTaxDto.getHostId());
		
		nightTax.setHost(host);
		nightTax.setRoom(roomRepository.findOne(nightTaxDto.getRoomId()));
		nightTax.setGuestName(nightTaxDto.getGuestName());
		nightTax.setDate(dateUtil.convertFromStringToDate(nightTaxDto.getDate()));
		nightTax.setCreator(userRepository.findOne(1));
		nightTax.setStatus(NightTaxStatusEnum.UNPAID);
		nightTax.setDateCreated(new Date());
		
		return nightTax;
	}

	private NightTaxDto fromEntity(NightTax nightTax) {
		NightTaxDto nightTaxDto = new NightTaxDto();

		nightTaxDto.setRoomNumber(roomRepository.findOne(nightTax.getRoom().getId()).getNumber());
		nightTaxDto.setStatus(nightTax.getStatus());
		nightTaxDto.setDate(dateUtil.convertFromDateToString(nightTax.getDate()));
		nightTaxDto.setHostName(userRepository.findOne(nightTax.getHost().getId()).getUsername());
		nightTaxDto.setGuestName(nightTax.getGuestName());
		nightTaxDto.setCreatorName(userRepository.findOne(nightTax.getCreator().getId()).getUsername());
		nightTaxDto.setDateCreated(dateUtil.convertFromDateWithTimeToString(nightTax.getDateCreated()));

		return nightTaxDto;
	}

}
