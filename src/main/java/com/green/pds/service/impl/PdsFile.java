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
		
		// 저장될 경로를 가져오기 : uploadPath
		String  uploadPath = String.valueOf( map.get("uploadPath") );
		
		// 파일들을 저장하고 Files Table 에 저장할 정보를 map 에 담는다
		List<FilesDto>  fileList = new ArrayList<>();
		
		// uploadfiles 에 넘어온 파일별로 반복
		for (MultipartFile uploadfile : uploadfiles) {
			if( uploadfile.isEmpty() ) // 전송파일이 내용이 없으면
				continue; 
			
			String  orgName  =  uploadfile.getOriginalFilename();
			// d:\\dev\\springboot\\data\\data.abc.txt	: 업로드된 파일정보	
			// d:/dev/springboot/data/data.abc.txt         	
			String  fileName =  
					( orgName.lastIndexOf("\\") < 0 )   // '\' 못찾으면 -1 
					? orgName
					: orgName.substring( orgName.lastIndexOf("\\") + 1 ) // data.abc.txt
					;
			
			String  fileExt = 
					( orgName.lastIndexOf(".") < 0 )   // 못찾으면 -1 
					? " "
					: orgName.substring( orgName.lastIndexOf(".") )  // .txt
					;
			
			//System.out.println("PdsFile:" + orgName + fileExt);
					
			// 날짜 폴더 생성
			String  folderPath  =  makeFolder(  uploadPath );
			
			// 파일 중복방지 :  같은 폴더에 같은 파일명을 저장하면 마지막 저장된 파일로 변경			
			// 전세계에서 유일한 중복되지 않는 고유한 문자열 생성  : UUID
			String  uuid        =  UUID.randomUUID().toString();    
			
			// 저장할 sfilename 생성
			// File.separator : 윈도우면 "\\", 리눅스, 맥 "/"
			// saveName : 실제 저장될 서버의 경로 + 생성된 날짜형 폴더명 + uuid + 파일명
			String  saveName    =  uploadPath + File.separator
					            +  folderPath + File.separator
					            +  uuid       + "."  + fileName;   // 실제 저장될 파일명
			// saveName : 생성된 날짜형 폴더명 + uuid + 파일명
			String  saveName2   =  folderPath + File.separator
					            +  uuid       + "."  + fileName;   // sfilename
			
			Path    savePath    =  Paths.get( saveName );
			// import  java.nio.Path
			// Paths.get(  ) : 특정 경로의 파일정보를 가져온다
			
			// 파일저장
			try {
				uploadfile.transferTo( savePath );  // 파일저장
			} catch (IllegalStateException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
			// 저장된 파일들의 정보를 FilesDto 에 파일정보로 저장
			FilesDto  dto = new FilesDto(0, 0, fileName, fileExt, saveName2);
			
			// fileList 에 추가
			fileList.add( dto );
		}  // for end
		
		// map 에 fileList 정보를 추가 -> 값을 서비스로 돌려주기 위해 map 에 보관
		map.put("fileList", fileList);
		
	}

	// 날짜로 폴더 생성 d:\\dev\\springboot\\data\\2026\\05\\15	
	private static String makeFolder(String uploadPath) {
		// d:\\dev\\springboot\\data + \\2026\\05\\15
		// uploadPath                + folderPath
		
		String   dateStr     =  LocalDate.now().format(
			DateTimeFormatter.ofPattern("yyyy/MM/dd")
				);
		// System.out.println("dateStr:" + dateStr);  // dateStr: 2026/05/15
		
		//  File.separator : win( "\\" ), linux,mac( "/" ) 
		String    folderPath        =  dateStr.replace("/", File.separator);   // java.io.File  
		
		// 날짜로 폴더를 생성 : d:\\dev\\springboot\\data\\2026\\05\\15	
		File      uploadPathFolder  =  new File(uploadPath, folderPath);         
		if( !uploadPathFolder.exists()  )
			uploadPathFolder.mkdirs();   // make directory
		// mkdir()   : 상위폴더가 없으면 폴더 생성 실패		
		// mkdirs()  : 상위폴더가 없으면 폴더전체를 생성한다		 
		
		return   folderPath;
	}

	// 실제 파일 삭제 : file List 에 있는
	public static void delete(String uploadPath, List<FilesDto> fileList) {
		
		String path = uploadPath; // D:/dev/springboot/data/
		
		fileList.forEach( ( file ) -> {
			String sfile = file.getSfilename();  // 실제 저장된 파일명
			
			File   dfile = new File(path + sfile);
			if(dfile.exists())
				dfile.delete();
		});
		
	}
		
	
 
}











