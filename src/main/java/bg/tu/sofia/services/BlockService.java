package bg.tu.sofia.services;

import java.util.List;

import bg.tu.sofia.dtos.BlockDto;

public interface BlockService {
	public BlockDto getBlockIdById(int blockId);

	public BlockDto getBlockByHostId(int hostId);

	public List<BlockDto> getAllBlocks();
}
