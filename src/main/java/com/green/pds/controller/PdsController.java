package com.green.pds.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.green.board.dto.BoardDto;
import com.green.interceptor.AuthInterceptor;
import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;
import com.green.paging.dto.Pagination;
import com.green.paging.dto.SearchDto;
import com.green.pds.dto.FilesDto;
import com.green.pds.dto.PdsDto;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Pds")
public class PdsController {

    private final AuthInterceptor authInterceptor;
	
	@Value("${part1.upload-path}")
	private   String       uploadPath; 

	@Autowired
	private   MenuMapper   menuMapper;
	
	@Autowired
	private   PdsMapper    pdsMapper;
	
	@Autowired
	private   PdsService   pdsService;

    PdsController(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }
	
	// /Pds/List?menu_id=MENU01&nowpage=1
	// /Pds/List?menu_id=MENU01&nowpage=3&searchType=title&keyword=11
	@RequestMapping("/List")
	public  ModelAndView   list(
			@RequestParam  HashMap<String, Object> map) {
		
		System.out.println("map:"  + map);
		// map:{menu_id=MENU01, nowpage=1}
		// map:{menu_id=MENU01, nowpage=1, searchType=, keyword=}
		
		// 메뉴 목록 조회
		List<MenuDTO>  menuList      =  menuMapper.getMenuList();    
		
		//  자료실 목록 조회 (10 개씩) - 페이징 처리 준비작업 시작		
		//  해당 메뉴의 전체 자료수
		int            totalCount    =  pdsMapper.count( map );  // menus_id, searchType, keyword    
		System.out.println("totalCount:" + totalCount);
		
		// 현재 페이지 정보 : map { nowpage=1 }  Object -> String -> int
		int         nowpage   =  Integer.parseInt( String.valueOf( map.get("nowpage") ) );  
				
		// 페이징을 위한 설정
		SearchDto      searchDto     =  new SearchDto();
		searchDto.setPageNo( nowpage );   // 현재 페이지 설정
		searchDto.setNumOfRows( 10 );     // 한페이지에 10줄의 자료
		searchDto.setPageSize( 10 );      // 페이지 번호 목록 1 2 3 4 5 .. 9 10  > >>
		
		// Pagination 설정
		Pagination    pagination  =  new Pagination(totalCount, searchDto);
		searchDto.setPagination(pagination);	
		
		int           offset      =  searchDto.getOffset();
		int           numOfRows   =  searchDto.getNumOfRows();
		
		map.put("offset",    offset);
		map.put("numOfRows", numOfRows);
		// 페이징 처리 준비작업 종료
		
		System.out.println("map2:"+ map);
		
		// 자료 조회
		List<PdsDto>  pdsList     =  pdsService.getPdsList( map );  
				
		//------------------------------------------------------
		ModelAndView   mv        =   new ModelAndView();
		mv.setViewName("pds/list");
		
		mv.addObject("menuList",   menuList);		
		mv.addObject("searchDto",  searchDto);		
		mv.addObject("pdsList",    pdsList);			
		
		mv.addObject("map",        map);
		return        mv;		
		
	}
	
	// 글 쓰기
	// /Pds/WriteForm?menu_id=MENU01&nowpage=1
	@RequestMapping("/WriteForm")
	public  ModelAndView  writeForm(
		@RequestParam  HashMap<String, Object> map	) {
		
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
				
		//--------------------------
		ModelAndView   mv        =  new ModelAndView();
		mv.setViewName("pds/write");
		mv.addObject("menuList",  menuList);
		mv.addObject("map",       map);
		return         mv;
	}
	
	// /Pds/Write
	// text   : menu_id=MENU01, nowpage=1,	title=aa, writer=aa, content=aaa -> map
	// binary : upfile=(binary), upfile=(binary), upfile=(binary)            -> uploadfiles
	@RequestMapping("/Write")
	public  ModelAndView   write(
		@RequestParam                  HashMap<String, Object>  map,
		@RequestParam(value="upfile")  MultipartFile []         uploadfiles
			) {
		System.out.println("map:"         + map);
		System.out.println("uploadfiles:" + uploadfiles);
		
		//  넘어온 정보를 파일과 db 에 저장한다
		pdsService.setWrite( map,  uploadfiles  );	
		
		// 저장후 돌아가기 
		String  menu_id      =  String.valueOf( map.get("menu_id") );
		int     nowpage      =  Integer.parseInt(String.valueOf(map.get("nowpage") ) );
		
		ModelAndView   mv    =  new ModelAndView();
		String         loc   =  """
				redirect:/Pds/List?menu_id=%s&nowpage=%d
				""".formatted( menu_id, nowpage ); 
		mv.setViewName( loc );
		return         mv;
		
	}
	
	
	
	// 내용보기
	// /Pds/View?idx=127&menu_id=MENU01&nowpage=3
	@RequestMapping("/View")
	public  ModelAndView   view(
		@RequestParam  HashMap<String, Object>  map	) {
		
		// 메뉴목록 조회
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
		
		//  조회수 증가
		pdsService.setReadCountUpdate( map );  //  map : idx, inChit
		
		// 넘겨줄 pdsDto 정보를 조회 idx
		PdsDto         pdsDto    =  pdsService.getPds( map );
		
		// 내용보기 줄바꿈 변경 \n -> <br>
		if( pdsDto.getContent() != null ) { 
			String  content = pdsDto.getContent().replace("\n", "<br>");
			pdsDto.setContent(content);
		}
		
		// 넘겨줄 filesDto 정보를 조회 idx
		List<FilesDto> fileList  =  pdsService.getFileList( map );
		
		//-----------------------------------
		ModelAndView   mv     =   new ModelAndView();		
		mv.setViewName("pds/view");
		mv.addObject("menuList",  menuList);
		
		mv.addObject("pds",       pdsDto   );  // 게시물 정보
		mv.addObject("fileList",  fileList );  // 게시물 정보
		
		
		mv.addObject("map",       map);
		return         mv;
		
	}
	
	// /Pds/Delete?idx=817&menu_id=MENU03&nowpage=1
	@RequestMapping("/Delete")
	public  ModelAndView   delete(
		@RequestParam  HashMap<String, Object> map	) {
		System.out.println( "delete map:" + map );
		
		// db 에서 자료 삭제
		pdsService.setDelete( map );
		
		
		// 삭제 이후에 목록조회로 돌아가기
		ModelAndView   mv   =  new ModelAndView();
		String         loc  =  "redirect:/Pds/List"
			+	"?menu_id="  + map.get("menu_id")
			+   "&nowpage="  + map.get("nowpage"); 
		mv.setViewName( loc );		
		return         mv;		
	}
	
	// /Pds/UpdateForm?idx=818&menu_id=MENU01&nowpage=1
	@RequestMapping("/UpdateForm")
	public   ModelAndView   updateForm(
		@RequestParam  HashMap<String, Object> map	) {
		
		// 메뉴목록
		List<MenuDTO>    menuList  =  menuMapper.getMenuList();  
		
		// 수정할 Board 정보 idx 로 검색
		PdsDto           pds       =  pdsService.getPds( map );
		
		// 수정할 Files 정보 idx 로 검색
		List<FilesDto>   fileList  =  pdsService.getFileList( map );
		
		ModelAndView  mv        = new ModelAndView();
		mv.setViewName( "pds/update" );
		mv.addObject("menuList",   menuList );
		mv.addObject("pds",        pds      );
		mv.addObject("fileList",   fileList );		
		
		mv.addObject("map",        map);
		return   mv;
	}
	
	// /Pds/Update
	// map {idx=818, menu_id=MENU01, nowpage=1, title=지울글 수정, content=asdfas ㅁㄴㅇㄹ }
	// MultipartFile [] { upfile=(binary), upfile=(binary) } 
	@RequestMapping("/Update")
	public  ModelAndView  update(
		@RequestParam    HashMap<String, Object>  map,
		@RequestParam(value="upfile")  MultipartFile []  uploadfiles
			) {
		
		// 필요한 정보 수정
		pdsService.setUpdate(map, uploadfiles);
		
		// 돌아갈 주소
		ModelAndView  mv      =  new ModelAndView();
		String        loc     =  "redirect:/Pds/List"
				 + "?menu_id=" + map.get("menu_id")
				 + "&nowpage=" + map.get("nowpage");
 		mv.setViewName(loc);
		return     mv;
		
	}
	
	
	//-----------------------------------------------------------------
	// 파일다운로드
	// 서버에서 바이너리데이터를 다운받는다 : daya 덩어리
	//-----------------------------------------------------------------
	// http://localhost:8080/Pds/filedownload/1  
	@GetMapping("/filedownload/{file_num}")     // ?file_num=1
	@ResponseBody     // 내려주는 것은 data 다
	public   void   downloadFile(
		HttpServletResponse                       res,
		@PathVariable(value="file_num")   Long    file_num
			) throws UnsupportedEncodingException {
		// HttpServletResponse객체를 사용하면 return 문 없이도 data를 서버
		//  ->클라이언트로 보낼수 있다
		
		FilesDto   fileInfo   =  pdsService.getFileInfo( file_num );
		
		// 파일경로 : 다운로드할 파일의 경로 생성
		// import java.nio.file.Path;
		Path   saveFilePath   =  Paths.get(
				uploadPath
				+ File.separator
				+ fileInfo.getSfilename()
				);      
		
		//  http 헤더 설정 : 클라이언트 브라우저에게 주는 정보
		setFileHeader( res, fileInfo ); 
		
		//  파일 복사 -> 함수 ( 서버 -> 클라이언트 ) : 실제 다운로드
		fileCopy( res, saveFilePath  );
		
	}

	// 실제 파일 다운로드 부분 : binary 데이터를 다운로드
	private void fileCopy(HttpServletResponse response, Path saveFilePath) {
		
		FileInputStream   fis = null;
		try {
			fis = new FileInputStream( saveFilePath.toFile()  );
			FileCopyUtils.copy( fis, response.getOutputStream() );
			response.getOutputStream().flush();  // 남아있는 버퍼초기화
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 다운로드 받을 파일의 header 정보 설정
	private void setFileHeader(HttpServletResponse response, FilesDto fileInfo)
			throws UnsupportedEncodingException {
		
		response.setHeader("Content-Disposition", 
				"attachment; filename=\"" +
				URLEncoder.encode( 
					(String) fileInfo.getFilename(), "UTF-8" ) + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary" );
		// response.setHeader("Content-Type", "application/download; utf-8" );  // hwp 연결프로그램작동
		response.setHeader("Content-Type", "application/octet-stream; utf-8" ); // hwp 연결프로그램작동
		response.setHeader("Pragma", "no-cache;" );
		response.setHeader("Expires", "-1" );		
		
	}
	
	
	
}



















