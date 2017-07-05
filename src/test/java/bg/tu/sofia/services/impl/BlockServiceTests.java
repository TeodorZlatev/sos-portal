package bg.tu.sofia.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bg.tu.sofia.SosPortalApplication;
import bg.tu.sofia.dtos.BlockDto;
import bg.tu.sofia.entities.Block;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.repositories.CredentialsRepository;
import bg.tu.sofia.repositories.NightTaxRepository;
import bg.tu.sofia.repositories.RoomRepository;
import bg.tu.sofia.repositories.UserRepository;
import bg.tu.sofia.repositories.UserRoomRepository;
import bg.tu.sofia.services.BlockService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SosPortalApplication.class)
public class BlockServiceTests {
	
	private static final String NUMBER_1 = "number 1";
	private static final String NUMBER_2 = "number 2";
	private static final String NUMBER_3 = "number 3";
	
	private static final String HOST_1 = "host 1";
	private static final String HOST_2 = "host 2";
	private static final String HOST_3 = "host 3";
	
	@Autowired
	private BlockRepository blockRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private NightTaxRepository nightTaxRepository;
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private UserRoomRepository userRoomRepository;
	
	@Autowired 
	private BlockService blockService;
	
	@Before
	public void setup() {
		nightTaxRepository.deleteAll();
		credentialsRepository.deleteAll();
		userRoomRepository.deleteAll();
		roomRepository.deleteAll();
		blockRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void testGetByBlockId() {
		
		Block block1 = createBlock(NUMBER_1, HOST_1);
		
		BlockDto fetchedBlock = blockService.getBlockById(block1.getId());
		
		assertEquals(block1.getNumber(), fetchedBlock.getNumber());
	}
	
	@Test
	public void testGetBlockByHostId() {
		
		Block block1 = createBlock(NUMBER_1, HOST_1);
		Block block2 = createBlock(NUMBER_2, HOST_2);
		
		BlockDto fetchedBlock = blockService.getBlockByHostId(block1.getHost().getId());
		
		assertEquals(block1.getNumber(), fetchedBlock.getNumber());
	}
	
	@Test
	public void testGetAllBlocks() {
		
		createBlock(NUMBER_1, HOST_1);
		createBlock(NUMBER_2, HOST_2);
		createBlock(NUMBER_3, HOST_3);
		
		List<BlockDto> fetchedBlocks = blockService.getAllBlocks();
		
		assertEquals(3, fetchedBlocks.size());
	}
	
	private Block createBlock(String number, String username) {
		Block block = new Block();
		
		block.setNumber(number);
		
		User user = new User();
		user.setUsername(username);
		user = userRepository.save(user);
		block.setHost(user);
		
		return blockRepository.save(block);
	}

}
