package com.green.pds.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.green.pds.dto.FilesDto;

public class PdsFile {

	// uploadfiles 에 넘어온 파일들을 저장
	public static void save(
			HashMap<String, Object> map,
			MultipartFile[] uploadfiles) {
		
		// 저장될 경로를 가져오기
		String uploadPath = String.valueOf(map.get("uploadPath"));
		
		// 파일들을 저장하고 Files Table 에 저장할 정보를 map 에 담는다
		List<FilesDto> fileList = new ArrayList<>();
		
		// uploadfiles 에 넘어온 파일별로 반복
		for (MultipartFile uploadfile : uploadfiles) {
			if(uploadfile.isEmpty()) // 전송파일이 내용이 없으면
				continue;
			
			String orgName  = uploadfile.getOriginalFilename();
			// d:\\dev\\springboot\data\\data.abc.txt : 업로드된 파일 정보
			// d:/dev/springboot/data/data.abc.txt 
			String fileName = 
				   ( orgName.lastIndexOf("\\") < 0 )  // 찾았는데 0보다 작다? : 못찾으면 -1
				   ? orgName
				   : orgName.substring( orgName.lastIndexOf("\\") + 1 )  // substring : 시작 위치 (잘라야할 위치) 0 부터 시작하기에 + 1 한다
				   ;
			
			String fileExt  = 
				   ( orgName.lastIndexOf(".") < 0 )  // 찾았는데 0보다 작다? : 못찾으면 -1
				   ? " "
				   : orgName.substring( orgName.lastIndexOf(".") )  // .txt 를 저장하기위해  + 1 을 넣지 않는다
				   ;
			
			// System.out.println("PdsFile:" + orgName + fileExt);
			
			// 날짜 폴더 생성
			String folderPath = makeFolder( uploadPath );
			
			// 파일 중복방지
			// 중복되지 않는 고유한 문자열 생성 : UUID
			String uuid       = UUID.randomUUID().toString();
			
			// 저장할 sfilename 생성
			String saveName   = uploadPath + File.separator
					          + folderPath + File.separator
					          + uuid       + "." + fileName; // 실제 저장될 파일명
			
			String saveName2  = folderPath + File.separator
			                  + uuid       + "." + fileName; // 실제 sfilename
			
			Path savePath     = Paths.get( saveName ); // 실제 saveName 안에있는 변수를 뽑아내는 문장
			// import java.nio.path 
			// Paths.get( ) : 특정 경로의 파일정보를 가져온다
			
			// 파일저장
			try {
				uploadfile.transferTo( savePath ); // 파일저장
			} catch (IllegalStateException e) {			
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 저장된 파일들의 정보를 FilesDto 에 파일정보를 저장
			FilesDto  dto = new FilesDto(0, 0, fileName, fileExt, saveName2); // 경로를 뺀 실제 저장된 파일명을 추
			                                   // 파일명  파일확장자   실제 이름
			// fileList 에 추가			
			fileList.add(dto);
		}  // for end
		
			// map 에 fileList 정보를 추가 ->값을 서비스로 돌려주기 위해 map 에 보관
			map.put("fileList", fileList);
	}
	
	// 날짜로 폴더 생성 d:\\dev\\springboot\\data\\2026\\05\\15
	private static String makeFolder(String uploadPath) {
		// d:\\dev\\springboot\\data + \\2026\\05\\15
		// uploadPath                + folderPath
		
		String dateStr    = LocalDate.now().format(
			DateTimeFormatter.ofPattern("YYYY/MM/dd")
				);
		// System.out.println("dateStr" + dateStr); // dateStr: 2026/05/15
		
		// File.separator : win( "\\" ), linux, mac("/")  
		String folderPath = dateStr.replace("/", File.separator); // java.io.File 로 import 해야한다
		
		// 날짜로 폴더를 생성 : d:\\dev\\springboot\\data\\2026\\05\\15
		File uploadPathFolder  = new File(uploadPath, folderPath);
		if( !uploadPathFolder.exists() ) // uploadPathFolder 가 존재하지않는다면
			 uploadPathFolder.mkdirs(); // 폴더 생성  // make directory와 같다
		// mkdir()  : 상위폴더가 없으면 폴더 생성 실패
		// mkdirs() : 상위폴더가 없으면 폴더전체를 생성한다
		
		return folderPath;
	}

}
