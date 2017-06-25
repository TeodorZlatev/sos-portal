package bg.tu.sofia.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.BlockDto;
import bg.tu.sofia.entities.Block;
import bg.tu.sofia.entities.User;
import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.services.BlockService;

@Component
public class BlockServiceImpl implements BlockService {

	@Autowired
	private BlockRepository blockRepository;

	@Override
	public BlockDto getBlockById(int blockId) {
		return fromEntity(blockRepository.findOne(blockId));
	}

	@Override
	public BlockDto getBlockByHostId(int hostId) {
		return fromEntity(blockRepository.findByHostId(hostId));
	}

	@Override
	public List<BlockDto> getAllBlocks() {
		List<Block> blocks = blockRepository.findAll();
		return blocks.stream().map(this::fromEntity).collect(Collectors.toList());
	}
	
	@Override
	public void saveOrUpdateBlock(BlockDto blockDto) {
		this.blockRepository.save(this.toEntity(blockDto));
	}

	private BlockDto fromEntity(Block block) {
		BlockDto blockDto = new BlockDto();

		blockDto.setId(block.getId());
		blockDto.setNumber(block.getNumber());
		// blockDto.setHostId(block.getHost().getId());

		return blockDto;
	}
	
	private Block toEntity(BlockDto blockDto) {
		Block block = new Block();
		
		block.setId(blockDto.getId());
		block.setNumber(blockDto.getNumber());
		
		User host = new User();
		host.setId(blockDto.getHostId());
		
		block.setHost(host);
		
		return block;
		
	}

	

}
