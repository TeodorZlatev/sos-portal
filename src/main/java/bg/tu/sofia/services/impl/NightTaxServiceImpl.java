package bg.tu.sofia.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.BlockDto;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.services.BlockService;
import bg.tu.sofia.services.NightTaxService;
import bg.tu.sofia.services.RoomService;
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
	private RoomService roomService;

	@Autowired
	private BlockService blockService;

	@Autowired
	private NightTaxRepository nightTaxRepository;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private PageUtil pageUtil;

	private MailUtil mailUtil;

	@Autowired
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	@Override
	public StructuredResponse createNightTax(NightTaxDto nightTaxDto) {

		StringBuilder sb = new StringBuilder();

		if (nightTaxDto.getGuestName() == null
				|| !nightTaxDto.getGuestName().matches("[А-Яа-я]+[ ]*[А-Яа-я]*[ ]*[А-Яа-я]*")) {
			sb.append("Невалидни имена! (пр. Иван Иванов Иванов)</br>");
		}

		if (nightTaxDto.getRoomId() == null || !nightTaxDto.getRoomId().matches("[0-9]+[А-Яа-яA-Za-z]*")) {
			sb.append("Невалиден избор на стая!</br>");
		}

		if (nightTaxDto.getHostId() == 0) {
			sb.append("Невалиден избор на домакин!</br>");
		}

		if (nightTaxDto.getDate() == null || "".equals(nightTaxDto.getDate())) {
			sb.append("Невалиден избор на дата!");
		}

		if (sb.length() != 0) {
			return new StructuredResponse(400, RESPONSE_STATUS.FAIL, null, sb.toString());
		}

		// expect list, because of more than one existing night taxes, otherwise
		// Exception
		List<NightTax> existingNightTaxes = nightTaxRepository.findByUserIdAndGuestNameAndDate(nightTaxDto.getHostId(),
				nightTaxDto.getGuestName(), dateUtil.convertFromStringToDate(nightTaxDto.getDate()));

		if (existingNightTaxes.size() > 0) {
			User host = userRepository.findOne(nightTaxDto.getHostId());
			
			sb.append("Текущата нощувка е въведена. Домакин: " + host.getUsername() + ", гост: "
					+ nightTaxDto.getGuestName() + ", дата: " + nightTaxDto.getDate());

			return new StructuredResponse(409, RESPONSE_STATUS.FAIL, null, sb.toString());
		}

		NightTax nightTax = toEntity(nightTaxDto);

		nightTax = nightTaxRepository.save(nightTax);

		sb.append("Успешно създадена нощувка. Домакин: " + nightTax.getHost().getUsername() + ", гост: "
				+ nightTax.getGuestName() + ", дата: " + dateUtil.convertFromDateToString(nightTax.getDate()));

		User host = nightTax.getHost();

		mailUtil.sendMailForNightTax(host.getEmail(), host.getUsername(), nightTaxDto.getGuestName(),
				nightTaxDto.getDate());

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, sb.toString());
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

	@Override
	public StructuredResponse payNightTaxes(List<NightTaxDto> nightTaxes) {
		// extract ids from not null dtos
		List<Integer> ids = nightTaxes.stream().
				filter(nightTax -> nightTax != null).
				map(nightTax -> nightTax.getId())
				.collect(Collectors.toList());

		
		String message = null;
		if (ids.size() == 1) {
			message = "Заплатена е " + ids.size() + " нощувка.";
		} else {
			message = "Заплатени са " + ids.size() + " нощувки.";
		}
		
		nightTaxRepository.payNightTaxes(new Date(), ids);

		return new StructuredResponse(200, RESPONSE_STATUS.SUCCESS, null, message);
	}

	private NightTax toEntity(NightTaxDto nightTaxDto) {
		NightTax nightTax = new NightTax();

		User host = userRepository.findOne(nightTaxDto.getHostId());

		nightTax.setHost(host);

		Room room = new Room();
		room.setId(Integer.parseInt(nightTaxDto.getRoomId()));

		nightTax.setRoom(room);
		nightTax.setGuestName(nightTaxDto.getGuestName());
		nightTax.setDate(dateUtil.convertFromStringToDate(nightTaxDto.getDate()));
		nightTax.setCreator(userRepository.findOne(nightTaxDto.getCreatorId()));
		nightTax.setStatus(NightTaxStatusEnum.UNPAID);
		nightTax.setDateCreated(new Date());

		return nightTax;
	}

	private NightTaxDto fromEntity(NightTax nightTax) {
		NightTaxDto nightTaxDto = new NightTaxDto();

		nightTaxDto.setId(nightTax.getId());

		RoomDto room = roomService.getByRoomId(nightTax.getRoom().getId());

		nightTaxDto.setRoomId(room.getId() + "");
		nightTaxDto.setRoomNumber(room.getNumber());

		BlockDto block = blockService.getBlockById(room.getBlockId());

		nightTaxDto.setBlockId(block.getId());
		nightTaxDto.setBlockNumber(block.getNumber());

		nightTaxDto.setStatus(nightTax.getStatus());
		nightTaxDto.setDate(dateUtil.convertFromDateToString(nightTax.getDate()));
		nightTaxDto.setHostName(userRepository.findOne(nightTax.getHost().getId()).getUsername());
		nightTaxDto.setGuestName(nightTax.getGuestName());
		nightTaxDto.setCreatorName(userRepository.findOne(nightTax.getCreator().getId()).getUsername());
		nightTaxDto.setDateCreated(dateUtil.convertFromDateWithTimeToString(nightTax.getDateCreated()));
		nightTaxDto.setDatePaid(dateUtil.convertFromDateWithTimeToString(nightTax.getDatePaid()));

		return nightTaxDto;
	}

}
