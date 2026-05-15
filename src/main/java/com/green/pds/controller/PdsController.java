package com.green.pds.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;
import com.green.paging.dto.Pagination;
import com.green.paging.dto.SearchDto;
import com.green.pds.dto.PdsDto;
import com.green.pds.mapper.PdsMapper;
import com.green.pds.service.PdsService;

@Controller
@RequestMapping("/Pds")
public class PdsController {

	@Autowired
	private   MenuMapper   menuMapper;
	
	@Autowired
	private   PdsMapper    pdsMapper;
	
	@Autowired
	private   PdsService   pdsService;
	
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
	
	
	// 내용보기
	// /Pds/View?idx=127&menu_id=MENU01&nowpage=3
	@RequestMapping("/View")
	public  ModelAndView   view(
		@RequestParam  HashMap<String, Object>  map	) {
		
		// 넘겨줄 pdsDto 정보를 조회 idx
		
		// 넘겨줄 filesDto 정보를 조회 idx
		
		//-----------------------------------
		ModelAndView   mv     =   new ModelAndView();		
		mv.setViewName("pds/view");
		// mv.addObject("menuList",  menuList);
		
		mv.addObject("map",       map);
		return         mv;
		
	}
	
	
}








