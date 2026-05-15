package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.PdsDto;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Service
public class PdsServiceImpl implements PdsService {

	// @Value 가 application.properties 에 있는
	// part1.upload-path=D:/dev/springboot/data/
	// org.springframework.beans.factory.annotation.Value; -> 로 @Value 를 import 해야한다
	@Value("${part1.upload-path}")
	private String uploadPath;
	
	@Autowired
	private   PdsMapper  pdsMapper;
	
	@Override
	public List<PdsDto> getPdsList(HashMap<String, Object> map) {
		
		List<PdsDto> pdsList =  pdsMapper.getPdsList( map ); 
		
		return       pdsList;
		
	}

	@Override
	public void setWrite(HashMap<String, Object> map, MultipartFile[] uploadfiles) {
		// 파일저장 + db 저장
		// 1. 파일저장 : uploadfiles[] -> uploadPath : d:/dev/springboot/data/
		
		//String uploadPath = "d:/dev/springboot/data/";
		map.put("uploadPath", uploadPath);
		
		System.out.println("PdsFile 이전 map:" + map);
		
		// 별도 클래스 생성해서 처리 : PdsFiles
		PdsFile.save( map, uploadfiles );
		
		System.out.println("PdsFile 이후 map:" + map);
		
		// 2. db 저장 : 자료실 글 쓰기 <- map 정보를 가지고 db 에 저장
	}

}




