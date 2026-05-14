package com.green.paging.controller;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.BoardApplication;
import com.green.board.dto.BoardDto;
import com.green.interceptor.AuthInterceptor;
import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;
import com.green.paging.dto.Pagination;
import com.green.paging.dto.SearchDto;
import com.green.paging.mapper.BoardPagingMapper;

@Controller
@RequestMapping("/BoardPaging")
public class BoardPagingController {

	@Autowired
	private  MenuMapper         menuMapper;
	
	@Autowired
	private  BoardPagingMapper  boardPagingMapper;

  
	// /BoardPaging/List?menu_id=MENU01&nowpage=1
	// /BoardPaging/List?menu_id=MENU01&nowpage=3&searchType=&keyword=
	// /BoardPaging/List?menu_id=MENU01&nowpage=3&searchType=title&keyword=11
	@RequestMapping("/List")
	public  ModelAndView   list( BoardDto boardDto, int nowpage, 
			String  searchType, String keyword ) {  
		
		// 전체메뉴목록 : menus.jsp 용
		List<MenuDTO>  menuList =  menuMapper.getMenuList();
		
		// 게시물 목록 조회(페이징해서)
		// 해당 메뉴의 자료갯수 : 조회된 
		int            totalCount   
            =  boardPagingMapper.count( boardDto, searchType, keyword );  // menu_id
		System.out.println("totalCount:" + totalCount);
						
		// 페이징을 위한 초기설정		
		SearchDto   searchDto   =  new  SearchDto();
		searchDto.setPageNo( nowpage );  // 현재 페이지 정보
		searchDto.setNumOfRows(10);      // 한페이지에 출력될 자료수
		searchDto.setPageSize(10);       
		  // paging.jsp 에 한줄에 출력될 페이지 번호 수 : 처음 이전 1 2 3 ... 10 다음 마지막
		
		// Pagination 설정
		Pagination   pagination  =  new Pagination(totalCount, searchDto);
		searchDto.setPagination(pagination);
	
			
		int     offset      =  searchDto.getOffset();
		int     numOfRows   =  searchDto.getNumOfRows();
		
		String  menu_id     =  boardDto.getMenu_id(); 
		
		// 페이지 조회
		List<BoardDto>  list = boardPagingMapper.getBoardPagingList(
			menu_id, searchType, keyword, offset, numOfRows	); 
		
		
		ModelAndView   mv       =  new ModelAndView();
		mv.setViewName("boardpaging/list");	 // .jsp
		mv.addObject("menuList",   menuList);
		
		mv.addObject("nowpage",    nowpage);				
		mv.addObject("menu_id",    menu_id);  // 현재 메뉴정보
		
		mv.addObject("bList",      list);
		mv.addObject("searchDto",  searchDto);
		
		mv.addObject("searchType",  searchType);
		mv.addObject("keyword",     keyword);
			
		return  mv;		
	}
	
	// /BoardPaging/View?idx=208&menu_id=MENU01&nowpage=1
	@RequestMapping("/View")
	public   ModelAndView   view( BoardDto boardDto, int nowpage  ) {
		
		// 메뉴목록 조회
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
		
		// idx 에 해당하는 게시글 조회수 1증가
		boardPagingMapper.incHit( boardDto );
		
		// idx 로 게시글 한 개 조회
		BoardDto       board     =  boardPagingMapper.getBoard( boardDto  );
		
		String         menu_id   =  boardDto.getMenu_id();
				
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("boardpaging/view");
		mv.addObject("menuList", menuList );
		
		mv.addObject("menu_id",  menu_id  );
		mv.addObject("nowpage",  nowpage  );
		
		mv.addObject("board",    board );
			
		return  mv;
	}
	
	// /BoardPaging/WriteForm?menu_id=MENU01&nowpage=1
	@RequestMapping("/WriteForm")
	public  ModelAndView  writeForm(BoardDto boardDto, int nowpage) {
		
		// 메뉴 목록 조회
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();  
		// System.out.println("1:" + menuList);
		
		
		String     menu_id       =  boardDto.getMenu_id();
		ModelAndView   mv        =  new ModelAndView();
		mv.setViewName("boardpaging/write");
		mv.addObject("menuList", menuList);
		mv.addObject("menu_id",  menu_id );
		mv.addObject("nowpage",  nowpage );
	
		
		return  mv;
		
	}
	
	// /BoardPaging/Write
	// 넘어온 값들
	//   db 저장 : menu_id=MENU01, title=제목, writer=admin, content=내용,
	//   돌아가기위해 필요한 변수 : menu_id=MENU01, nowpage=1
	@RequestMapping("/Write")
	public  ModelAndView  write( BoardDto boardDto, int nowpage ) {
		
		// 새글 저장 -> db 저장
		boardPagingMapper.insertBoard(  boardDto  );		
		
		// 목록으로 돌아가기
		String   menu_id     =  boardDto.getMenu_id();				
		ModelAndView  mv     =  new ModelAndView();
		String        fmt    =  "redirect:/BoardPaging/List?menu_id=%s&nowpage=%d"; 
		String        loc    =  String.format(fmt, menu_id, 1 );
		mv.setViewName( loc  );
		return        mv;
		
	}
	
	// /BoardPaging/Delete?idx=207&menu_id=MENU01&nowpage=1
	@RequestMapping("/Delete")
	public  ModelAndView   delete( BoardDto boardDto, int nowpage  ) {
		
		// idx 로 board 삭제
		boardPagingMapper.deleteBoard( boardDto  );
		
		// 삭제후 목록으로 이동
		String        menu_id  =  boardDto.getMenu_id(); 
		ModelAndView  mv       =  new ModelAndView();
		String        loc      =  """
				redirect:/BoardPaging/List?menu_id=%s&nowpage=%d
				""".formatted(menu_id, nowpage);		
		mv.setViewName( loc );		
		return   mv;
		
	}
	
	
}


















