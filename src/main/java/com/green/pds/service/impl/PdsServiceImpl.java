package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.pds.dto.PdsDto;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Service
public class PdsServiceImpl implements PdsService {

	@Autowired
	private   PdsMapper  pdsMapper;
	
	@Override
	public List<PdsDto> getPdsList(HashMap<String, Object> map) {
		
		List<PdsDto> pdsList =  pdsMapper.getPdsList( map ); 
		
		return       pdsList;
		
	}

}




