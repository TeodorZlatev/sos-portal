package bg.tu.sofia.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.tu.sofia.SosPortalApplication;
import bg.tu.sofia.dtos.UserDto;
import bg.tu.sofia.entities.Block;
import bg.tu.sofia.entities.NightTax;
import bg.tu.sofia.entities.Role;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.entities.UserRoom;
import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.repositories.CredentialsRepository;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoleRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.repositories.UserRoomRepository;
import bg.tu.sofia.utils.MailUtil;
import bg.tu.sofia.utils.StructuredResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SosPortalApplication.class)
public class UserServiceTests {

	private static final String USERNAME_FOR_MARKER = "USERNAME";
	private static final String MARKER = "U";
	private static final String USERNAME = "Теодор Христов Златев";
	private static final String PERSONAL_NUMBER = "9402024124";
	private static final String EMAIL = "test_1@gmail.com";
	private static final String BLOCK_NUMBER = "block number";
	private static final String ROOM_NUMBER = "room number";
	private static final String ROLE = "role";
	private static final String PAGINATION_RESULT = "{\"nextPages\":[],\"firstPage\":1,\"lastPage\":1,\"active\":1,\"previousPages\":[]}";

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoomRepository userRoomRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private NightTaxRepository nightTaxRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private UserServiceImpl userService;

	private MailUtil mailUtilMock = mock(MailUtil.class);

	@Before
	public void setup() {
		nightTaxRepository.deleteAll();
		credentialsRepository.deleteAll();
		userRoomRepository.deleteAll();
		roomRepository.deleteAll();
		blockRepository.deleteAll();
		userRepository.deleteAll();
		
		doNothing().when(mailUtilMock).sendMailForRegistration(any(), any(), any(), any());

		userService.setMailUtil(mailUtilMock);
	}

	@Test
	public void testGetAllByRoomIdAndBlockId() {
		Block block = createBlock();

		Room room = createRoom(block);

		User user1 = createUser(USERNAME);
		User user2 = createUser(USERNAME);

		createUserRoom(user1, room);
		createUserRoom(user2, room);

		List<UserDto> users = userService.getAllByRoomIdAndBlockId(room.getId(), block.getId());

		assertEquals(2, users.size());
		assertEquals(user1.getId(), users.get(0).getId());
		assertEquals(user2.getId(), users.get(1).getId());
	}

	@Test
	public void testInsertUser() {
		
		Block block = createBlock();

		Room room = createRoom(block);

		User user = createUser(USERNAME);
		user.getRole().setId(1);
		
		UserDto userDto = createUserDto(user, room.getId(), block.getId());

		StructuredResponse response = userService.insertUser(userDto);

		assertEquals(200, response.getCode());
	}

	@Test
	public void testGetPersonWithNightTaxes() {
		Block block = createBlock();

		Room room = createRoom(block);

		User user = createUser(USERNAME);

		createUserRoom(user, room);

		createNightTax(user, room);

		UserDto userDto = userService.getPersonWithNightTaxes(user.getId());

		assertEquals(user.getPersonalNumber(), userDto.getPersonalNumber());
	}

	@Test
	public void testGetPeopleWithNightTaxes() {
		
		int blockId = createEnvironment();
 
		List<UserDto> usersByMakrer = userService.getPeopleWithNightTaxes(MARKER, blockId + "", 1);
		
		List<UserDto> users = userService.getPeopleWithNightTaxes(null, blockId + "", 1);
		
		assertEquals(1, usersByMakrer.size());
		assertEquals(USERNAME_FOR_MARKER, usersByMakrer.get(0).getUsername());
		
		assertEquals(2, users.size());
		
	}
	
	@Test
	public void testGetPaginationPeopleWithNightTaxes() {
		
		int blockId = createEnvironment();
 
		String pagination = userService.getPaginationPeopleWithNightTaxes(MARKER, blockId + "", 1);
		
		assertEquals(PAGINATION_RESULT, pagination);
	}

	private int createEnvironment() {
		Block block = createBlock();

		Room room = createRoom(block);

		User user1 = createUser(USERNAME_FOR_MARKER);
		User user2 = createUser(USERNAME);

		createUserRoom(user1, room);
		createUserRoom(user2, room);

		createNightTax(user1, room);
		createNightTax(user2, room);
		
		return block.getId();
	}

	private NightTax createNightTax(User host, Room room) {
		NightTax nightTax = new NightTax();

		nightTax.setHost(host);
		nightTax.setRoom(room);

		nightTax = nightTaxRepository.save(nightTax);

		return nightTax;
	}

	private Room createRoom(Block block) {
		Room room = new Room();

		room.setNumber(ROOM_NUMBER);
		room.setBlock(block);

		room = roomRepository.save(room);

		return room;
	}

	private Block createBlock() {
		Block block = new Block();

		block.setNumber(BLOCK_NUMBER);

		block = blockRepository.save(block);

		return block;
	}

	private User createUser(String name) {
		Role role = new Role();
		role.setName(ROLE);

		role = roleRepository.save(role);

		User user = new User();

		user.setUsername(name);
		user.setPersonalNumber(PERSONAL_NUMBER);
		user.setEmail(EMAIL);
		user.setRole(role);

		user = userRepository.save(user);

		return user;
	}

	private UserRoom createUserRoom(User user, Room room) {
		UserRoom userRoom = new UserRoom();

		userRoom.setUser(user);
		userRoom.setRoom(room);

		userRoom = userRoomRepository.save(userRoom);

		return userRoom;
	}

	public UserDto createUserDto(User user, int roomId, int blockId) {
		UserDto userDto = new UserDto();

		userDto.setUsername(user.getUsername());
		userDto.setPersonalNumber(user.getPersonalNumber());
		userDto.setEmail(user.getEmail());
		userDto.setRoleId(user.getRole().getId());
		userDto.setRoomId(roomId + "");
		userDto.setBlockId(blockId + "");

		return userDto;
	}
}
