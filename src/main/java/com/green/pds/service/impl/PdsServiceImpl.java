package com.green.pds.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.FilesDto;
import com.green.pds.dto.PdsDto;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Service
public class PdsServiceImpl implements PdsService {

	// @Value 가 application.properties 에 있는
	// part1.upload-path=D:/dev/springboot/data/
	// import org.springframework.beans.factory.annotation.Value;
	@Value("${part1.upload-path}")
	private   String   uploadPath;
		
	@Autowired
	private   PdsMapper  pdsMapper;
	
	@Override
	public List<PdsDto> getPdsList(HashMap<String, Object> map) {
		
		List<PdsDto> pdsList =  pdsMapper.getPdsList( map ); 
		
		return       pdsList;
		
	}

	@Override
	public void setWrite(
			HashMap<String, Object>  map, 
			MultipartFile[]          uploadfiles) {
		// 파일저장 + db 저장
		// 1. 파일저장 : uploadfiles [] -> uploadPath : d:/dev/springboot/data/ 
		
		//String  uploadPath = "d:/dev/springboot/data/";
		map.put("uploadPath", uploadPath);
		
		System.out.println("PdsFile 이전 map:" + map);
		// map {menu_id=MENU01, nowpage=1,	title=aa, writer=aa, 
		//    content=aaa, uploadPath=d:/dev/springboot/data/ }
		
		// 별도 클래스 생성해서 처리 : PdsFile
		// 넘어온 정보로 파일을 저장
		PdsFile.save( map,  uploadfiles );
		
		System.out.println("PdsFile 이후 map:" + map);
		// { 
		//   menu_id=MENU01, nowpage=1, title=abcd, writer=abcdd, content=asdlf, 
		//   uploadPath=D:/dev/springboot/data/, 
		//   fileList=[
		//      FilesDto(file_num=0, idx=0, filename=한국환경공단 에어코리아 OpenAPI 기술문서.zip,
		//         fileext=.zip, sfilename=2026\05\18\09dc9ab6-528a-4822-862c-87cf676fea4c.한국환경공단 에어코리아 OpenAPI 기술문서.zip), 
		//      FilesDto(file_num=0, idx=0, filename=demo.zip, 
		//         fileext=.zip, sfilename=2026\05\18\b3b17466-d851-4093-a71e-0da3c80c8462.demo.zip)
		//   ]
		// }
		
		// 2. db 저장 : 자료실 글 쓰기 <- map
		//     Board 테이블에 저장
		pdsMapper.setWrite( map  );    // insertBoard   
		
		// 3.  Files 에 저장
		List<FilesDto>  fileList = (List<FilesDto>) map.get("fileList");
		if( fileList.size() > 0  )
			pdsMapper.setFileWriter( map );  // insertFiles
		
		
	}

	// map(idx) 에 해당하는 조회수 증가
	@Override
	public void setReadCountUpdate(HashMap<String, Object> map) {
		
		pdsMapper.setReadCountUpdate( map );
		
	}

	// 자료실 게시글(Pds) 을 조회한다 : map(idx)
	@Override
	public PdsDto getPds(HashMap<String, Object> map) {
		
		PdsDto    pdsDto  =  pdsMapper.getPds( map );
		
		return    pdsDto;
	}

	// idx 에 해당하는 Files table 의 정보
	@Override
	public List<FilesDto> getFileList(HashMap<String, Object> map) {
		
		List<FilesDto> fileList =  pdsMapper.getFileList( map );
		
		return         fileList;
	}

	// file_num 로 조회할 파일 정보를 조회
	@Override
	public FilesDto getFileInfo(Long file_num) {
		
		FilesDto  fileInfo  =  pdsMapper.getFileInfo( file_num );    
	
		return    fileInfo;
	}

	// 자료실 자료 삭제
	// /Pds/Delete?idx=817&menu_id=MENU03&nowpage=1
	@Override
	public void setDelete(HashMap<String, Object> map) {
		
		// 0. 해당파일 정보 조회
		List<FilesDto>  fileList  = pdsMapper.getFileList( map );
		
		// 1.실제 파일도 삭제 : D:/dev/springboot/data 에 있는  idx  관련 파일 삭제
		PdsFile.delete( uploadPath,  fileList  );
		
		// 2.idx 에 해당하는 파일을 삭제 : Files table 에 실제 삭제된 정보를 지운다
		// 외래키가 설정된 관계에서 삭제는 자식레크드를 먼저 삭제해야 한다
		pdsMapper.deleteUploadFile( map );
		
		// 3. idx 에 해당하는 자료실 글 삭제 : Board
		pdsMapper.setDelete( map );
		
		
	}

	// 자료실 수정
	@Override
	public void setUpdate(HashMap<String, Object> map, MultipartFile[] uploadfiles) {
		
		// 업로드될 경로를 map 에 추가
		map.put("uploadPath", uploadPath);
				
		// 업로드된 파일 저장
		PdsFile.save(map, uploadfiles);  // map ( fileList 가 추가됨)
		
		// Files table 정보저장 <- map <- fileList
		List<FilesDto>  fileList  =  (List<FilesDto>) map.get("fileList");
		if ( fileList.size() > 0 )
			pdsMapper.setFileWriter( map );
		
		// Board table 필요한 정보를 수정
		pdsMapper.setUpdate(map);
		
		
	}

}
















