package bg.tu.sofia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.sofia.dtos.BlockDto;
import bg.tu.sofia.services.BlockService;

@RestController
public class BlockController {

	@Autowired
	private BlockService blockService;

	@RequestMapping(method = RequestMethod.GET, 
					value = "/api/blocks",
					produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BlockDto> getBlocks() {
		return blockService.getAllBlocks();
	}
	
}
