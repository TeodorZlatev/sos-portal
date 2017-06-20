package bg.tu.sofia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.tu.sofia.repositories.BlockRepository;
import bg.tu.sofia.services.BlockService;

@Component
public class BlockServiceImpl implements BlockService{
	
	@Autowired
	private BlockRepository blockRepository;

	@Override
	public int getBlockIdByUserId(int userId) {
		return blockRepository.findByUserId(userId).getId();
	}

}
