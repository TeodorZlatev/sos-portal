package bg.tu.sofia.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.tu.sofia.SosPortalApplication;
import bg.tu.sofia.dtos.RoomDto;
import bg.tu.sofia.entities.Block;
import bg.tu.sofia.entities.Room;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.entities.UserRoom;
import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.repositories.UserRoomRepository;
import bg.tu.sofia.services.RoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SosPortalApplication.class)
public class RoomServiceTests {
	
	private static final String USERNAME = "username";
	private static final String BLOCK_NUMBER = "block number";
	private static final String ROOM_NUMBER = "room number";
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private BlockRepository blockRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserRoomRepository userRoomRepository;
	
	@Autowired
	private RoomService roomService;
	
	@Test
	public void testGetAllByBlockId() {
		Block block = createBlock();
		
		Room room = createRoom(block);
		
		roomRepository.save(room);
		
		List<RoomDto> rooms = roomService.getAllByBlockId(block.getId());
		
		assertEquals(1, rooms.size());
		assertEquals(ROOM_NUMBER, rooms.get(0).getNumber());
		
	}

	@Test
	public void testGetByRoomId() {
		Room room = createRoom(createBlock());
		
		RoomDto roomDto = roomService.getByRoomId(room.getId());
		
		assertEquals(room.getNumber(), roomDto.getNumber());
	}
	
	@Test
	public void testGetRoomByUserId() {
		Room room = createRoom(createBlock());
		
		User user = createUser();
		
		UserRoom userRoom = createUserRoom(user, room);
		
		userRoomRepository.save(userRoom);
		
		RoomDto roomDto = roomService.getRoomByUserId(user.getId());
		
		assertEquals(room.getNumber(), roomDto.getNumber());
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
	
	private User createUser() {
		User user = new User();
		
		user.setUsername(USERNAME);
		
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

}
