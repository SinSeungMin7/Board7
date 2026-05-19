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

@Service  // 미리 창고에 new 해서 담아놔라 라는뜻
public class PdsServiceImpl implements PdsService {  

	// @Value 가 application.properties 에 있는
	// part1.upload-path=D:/dev/springboot/data/
	// import org.springframework.beans.factory.annotation.Value;
	@Value("${part1.upload-path}")
	private  String   uploadPath;  // db 에는 업로드하는 파일을 저장해서는 안된다
	// private  String   uploadPath = "D:/dev/springboot/data/"; -> 원래는 이렇게 적어야한다
	
	@Autowired
	private   PdsMapper  pdsMapper;
	
	@Override
	public List<PdsDto> getPdsList(HashMap<String, Object> map) {
		
		List<PdsDto> pdsList =  pdsMapper.getPdsList( map ); 
		
		return       pdsList;
		
	}

	@Override
	public void setWrite(
			HashMap<String, Object> map, 
			MultipartFile[]         uploadfiles) { // map 에다 계속 input 해서 돌려바든다
		// 파일저장 + db 저장
		// 1. 파일저장 : uploadfiles [] -> uploadPath : d:/dev/springboot/data/ 
		
		//String  uploadPath = "d:/dev/springboot/data/";
		map.put("uploadPath", uploadPath);
		
		System.out.println("PdsFile 이전 map:" + map);
		// map:{menu_id=MENU01, nowpage=1 title=aa, writer=aa,
		// content=aaa, uploadPath=d:/dev/springboot/data/
		
		// 별도 클래스 생성해서 처리 : PdsFile
		// 넘어온 정보로 파일을 저장
		PdsFile.save( map,  uploadfiles );
		
		System.out.println("PdsFile 이후 map:" + map);
		// {menu_id=MENU01, nowpage=1, title=abcdeasdf, writer=abcd, content=abcd,
		// uploadPath=D:/dev/springboot/data/, 
		// fileList=[
		//		  FilesDto(file_num=0, idx=0, filename=서버 클라이언트에 대해.txt,
		//            fileext=.txt, sfilename=2026\05\18\1aadb922-f5e1-4383-81bd-168acdc92743.서버 클라이언트에 대해.txt), 
		//        FilesDto(file_num=0, idx=0, filename=data.go.kr.txt, fileext=.txt, 
		//            sfilename=2026\05\18\ca2af292-0739-4fe2-843f-1cff0c56ef88.data.go.kr.txt)
		
		// 2. db 저장 : 자료실 글 쓰기 <- map
		//    Board 테이블에 저장
		pdsMapper.setWrite( map ); // insertBoard
		
		// 3. Files 에 저장
		List<FilesDto> fileList = (List<FilesDto>) map.get("fileList");
		if( fileList.size() > 0 )
			pdsMapper.setFileWriter( map );  // insertfile
		
		
	}

	// map(idx) 에 해당하는 조회수 증가
	@Override
	public void setReadConuntUpdate(HashMap<String, Object> map) {
		
		pdsMapper.setReadConuntUpdate(map);
	}

	// 자료실 게시글(Pds) 을 조회한다 : map(idx)
	@Override
	public PdsDto getPds(HashMap<String, Object> map) {
		
		PdsDto pdsDto = pdsMapper.getPds(map);
		
		return pdsDto;
	}

	// idx 에 해당하는 Files table 의 정보
	@Override
	public List<FilesDto> getFileList(HashMap<String, Object> map) {
		
		List<FilesDto> fileList = pdsMapper.getFileList(map);
		
		return fileList;
	}

	// file_num 로 조회할 파일 정보를 조회
	@Override
	public FilesDto getFileInfo(long file_num) {
		
		FilesDto filesInfo = pdsMapper.getFileInfo(file_num); 
		
		return filesInfo;
	}

	// 자료실 자료 삭제
	// /Pds/Delete?idx=4818&menu_id=MENU01&nowpage=1
	@Override
	public void setDelete(HashMap<String, Object> map) {
		
		// 0. 해당파일 정보 조회
		List<FilesDto> fileList = pdsMapper.getFileList( map );
		// 1. 실제 파일도 삭제 : D:/dev/springboot/data 에 있는 idx 관련 파일을 삭제
		PdsFile.delete(uploadPath, fileList);
		
		// 2. idx 에 해당하는 파일을 삭제 : Files table 에 실제 삭제된 정보를 지운다
		// 외래키가 설정된 관계에서 삭제는 자식레코드를 먼저 삭제해야한다
		pdsMapper.deleteUploadFile( map );
		
		// 3. idx 에 해당하는 자료실 글 삭제 : Board
		pdsMapper.setDelete(map);
		
		
	}

}
















