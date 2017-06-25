package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.dtos.BlockDto;

public interface BlockService {
	public BlockDto getBlockById(int blockId);

	public BlockDto getBlockByHostId(int hostId);

	public List<BlockDto> getAllBlocks();
	
	public void saveOrUpdateBlock(BlockDto blockDto);
}
