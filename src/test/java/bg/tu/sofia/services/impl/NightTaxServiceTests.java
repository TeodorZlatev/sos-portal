package bg.tu.sofia.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.tu.sofia.SosPortalApplication;
import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.dtos.NightTaxDto;
import bg.tu.sofia.entities.Block;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.utils.MailUtil;
import bg.tu.sofia.utils.StructuredResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SosPortalApplication.class)
public class NightTaxServiceTests {

	private static final String NAME = "NAME";
	private static final String GUEST_NAME = "Теодор Христов Златев";
	private static final String ROOM_NUMBER = "номер на стая";
	private static final String BLOCK_NUMBER = "номер на блок";
	private static final String DATE_STR = "21-05-2017";
	private static final Date DATE = new Date();

	@Autowired
	private NightTaxRepository nightTaxRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private NightTaxServiceImpl nightTaxService;

	private MailUtil mailUtilMock = mock(MailUtil.class);

	@Before
	public void setup() {
		doNothing().when(mailUtilMock).sendMailForNightTax(any(), any(), any(), any());

		nightTaxService.setMailUtil(mailUtilMock);
	}

	@Test
	public void testCreateNightTax() {

		NightTaxDto nightTaxDto = new NightTaxDto();

		Room room = new Room();
		room = roomRepository.save(room);

		User user = new User();
		user.setUsername(NAME);
		user = userRepository.save(user);

		nightTaxDto.setGuestName(GUEST_NAME);
		nightTaxDto.setRoomId(room.getId() + "");
		nightTaxDto.setHostId(user.getId());
		nightTaxDto.setDate(DATE_STR);
		nightTaxDto.setStatus(NightTaxStatusEnum.PAID);

		StructuredResponse response = nightTaxService.createNightTax(nightTaxDto);

		assertEquals(200, response.getCode());
	}

	@Test
	public void testGetNightTaxesOfUser() {
		NightTax nightTax1 = createNightTax(NightTaxStatusEnum.PAID);
		NightTax nightTax2 = createNightTax(NightTaxStatusEnum.UNPAID);

		List<NightTaxDto> paidNT = nightTaxService.getNightTaxesOfUser(nightTax1.getHost().getId(),
				NightTaxStatusEnum.PAID, 1);

		assertEquals(1, paidNT.size());

		List<NightTaxDto> unpaidNT = nightTaxService.getNightTaxesOfUser(nightTax2.getHost().getId(),
				NightTaxStatusEnum.UNPAID, 1);

		assertEquals(1, unpaidNT.size());
	}

	@Test
	public void testPayNightTaxes() {
		NightTax nightTax1 = createNightTax(NightTaxStatusEnum.UNPAID);
		NightTax nightTax2 = createNightTax(NightTaxStatusEnum.UNPAID);

		NightTaxDto nightTaxDto1 = new NightTaxDto();
		NightTaxDto nightTaxDto2 = new NightTaxDto();

		nightTaxDto1.setId(nightTax1.getId());
		nightTaxDto2.setId(nightTax2.getId());

		StructuredResponse response = nightTaxService.payNightTaxes(Arrays.asList(nightTaxDto1, nightTaxDto2));

		assertEquals(200, response.getCode());
		assertEquals("Заплатени са 2 нощувки.", response.getMessage());
	}

	private NightTax createNightTax(NightTaxStatusEnum status) {

		User user = new User();
		user.setUsername(NAME);
		user = userRepository.save(user);

		Block block = new Block();
		block.setNumber(BLOCK_NUMBER);
		block = blockRepository.save(block);

		Room room = new Room();
		room.setNumber(ROOM_NUMBER);
		room.setBlock(block);

		room = roomRepository.save(room);

		NightTax nightTax = new NightTax();

		nightTax.setHost(user);
		nightTax.setCreator(user);
		nightTax.setRoom(room);
		nightTax.setStatus(status);
		nightTax.setDate(DATE);
		nightTax.setDateCreated(DATE);
		nightTax.setDatePaid(DATE);

		nightTax = nightTaxRepository.save(nightTax);

		return nightTax;
	}

}
