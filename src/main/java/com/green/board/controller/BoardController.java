package com.green.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.board.dto.BoardDto;
import com.green.board.mapper.BoardMapper;
import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/Board")
public class BoardController {
	
	@Autowired
	private  MenuMapper   menuMapper;
	
	@Autowired
	private  BoardMapper  boardMapper;
	
	// /Board/List?menu_id=MENU01
	@RequestMapping("/List")
	public  ModelAndView   list( MenuDTO  menuDto  ) {
		
		// 메뉴전체목록 조회 - menus.jsp
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
		log.info("menuList:" + menuList);
				
		// 게시물 목록 조회 - list.jsp (menu_id=MENU01)
		List<BoardDto>  boardList  =  boardMapper.getBoardList( menuDto );		
		log.error("boardList:" + boardList);
		
		// 넘어온 menu_id
		String          menu_id    =  menuDto.getMenu_id(); 
		MenuDTO         menu       =  menuMapper.getMenu( menuDto ); 
		
		ModelAndView    mv         =  new  ModelAndView();
		mv.setViewName("board/list");   // /WEB-INF/views/board/list.jsp
		mv.addObject("menuList",  menuList);
		mv.addObject("bList",     boardList);
		mv.addObject("menu_id",   menu_id);  // 현재 메뉴정보
		mv.addObject("menu",      menu   );  // 현재 메뉴전체정보()
		
		return  mv;
	}
	
	// /Board/View?idx=8&menu_id=MENU01
	@RequestMapping("/View")
	public  ModelAndView  view( BoardDto  boardDto  ) {
		
		// 전체 메뉴 목록 조회
		List<MenuDTO>  menuList = menuMapper.getMenuList();
		
		// idx 글의  조회수를 1 증가
		boardMapper.incHit( boardDto );
		
		
		// idx 로 조회한 게시글
		BoardDto  board  =  boardMapper.getBoard( boardDto  );
		System.out.println("board:" + board );
		// board:BoardDto [idx=1, menu_id=MENU01, title=JAVA Hello, writer=java, regdate=2026-05-04 15:16:57, hit=0]
		
		// content 안에 있는 엔터 \n 를 <br> 변경 -> content
		if( board.getContent() != null ) 
			board.setContent(  board.getContent().replace("\n", "<br>")  );		
		
		ModelAndView  mv   =  new ModelAndView();
		mv.setViewName("board/view" );
		mv.addObject("menuList", menuList );
		mv.addObject("board", board);
		mv.addObject("menu_id", board.getMenu_id() );
		
		return  mv;
	}
	
	// /Board/WriteForm?menu_id=MENU01
	@RequestMapping("/WriteForm")
	public  ModelAndView   writeForm( BoardDto boardDto  ) {
		
		// 메뉴목록
		List<MenuDTO>  menuList  =  menuMapper.getMenuList();
		
		System.out.println("/Board/WriteForm boardDto:" + boardDto );
		
		String        menu_id    =  boardDto.getMenu_id();
		String        menu_name  =  menuMapper.getMenuName(  menu_id  );
		
		ModelAndView  mv         =  new ModelAndView();
		mv.setViewName("board/write");
		mv.addObject("menu_id",   menu_id  );
		mv.addObject("menu_name", menu_name );
		mv.addObject("menuList", menuList );
		return  mv;
		
	}
	
	// /Board/Write?menu_id=MENU01&title=a&content=a&writer=a
	@RequestMapping("/Write")
	public  ModelAndView   write( BoardDto boardDto ) {
		System.out.println( "write boardDto: " + boardDto );
		// write boardDto: BoardDto(idx=0, menu_id=MENU01, title=aaa, content=aaa, writer=aaa, regdate=null, hit=0)
		
		// db 저장
		boardMapper.insertBoard( boardDto  );		
		
		String  menu_id   =  boardDto.getMenu_id();
		
		// 페이지 이동
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id );
		return  mv;
	}
	
	//  게시물 삭제 
	//  ?idx=3           : 삭제할 글 번호
	//  &menu_id=MENU01  : 삭제후 돌아올 메뉴정보
	// /Board/Delete?idx=3&menu_id=MENU01
	@RequestMapping("/Delete")
	public  ModelAndView   delete( BoardDto boardDto ) {
		
		// db idx 에 해당하는 글 삭제
		boardMapper.deleteBoard( boardDto );
				
		String  menu_id   =   boardDto.getMenu_id();
		// menu_id 해당 목록으로 돌아간다
		ModelAndView  mv =  new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id );	
		return  mv;		
	}
	
	// 게시물 수정페이지
	// http://localhost:8080/Board/UpdateForm?idx=8&menu_id=MENU01
	@RequestMapping("/UpdateForm")
	public  ModelAndView   updateForm( BoardDto  boardDto ) {
		
		// 전체 메뉴 목록 조회 : menus.jsp
		List<MenuDTO>  menuList   =  menuMapper.getMenuList();
		
		// 넘어온 데이터(idx)로 수정할 정보(board)를 조회
		BoardDto       board      =  boardMapper.getBoard( boardDto ); 
				
		// 수정할 정보를 입력받는 페이지로 이동 : update.jsp
		String         menu_id    =  boardDto.getMenu_id();
		String         menu_name  =  menuMapper.getMenuName( menu_id );
		ModelAndView   mv         =  new  ModelAndView();
		mv.setViewName("board/update");
		mv.addObject("board",      board      );
		mv.addObject("menuList",   menuList   );
		mv.addObject("menu_id",    menu_id    );
		mv.addObject("menu_name",  menu_name  );
		return        mv;			
		
	}
	
	// board 수정
	// http://localhost:8080/Board/Update?idx=8
	//  menu_id=MENU01, title="", content=""
	@RequestMapping("/Update")
	public  ModelAndView  update( BoardDto  boardDto  ) {
		
		// 넘오온 자료로 board를 수정
		boardMapper.updateBoard( boardDto  );
		
		// 수정 후 목록으로 이동
		String         menu_id   =  boardDto.getMenu_id();
		ModelAndView   mv        =  new ModelAndView();
		mv.setViewName("redirect:/Board/List?menu_id=" + menu_id);
		return         mv;
	}
	
	
}











