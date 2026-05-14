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
import com.green.pds.mapper.PdsMapper;

@Controller
@RequestMapping("/Pds")
public class PdsController {
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private PdsMapper pdsMapper;
	
	// 주소 : /Pds/List?menu_id=MENU01&nowpage=1
	// 주소 : /Pds/List?menu_id=MENU01&nowpage=3&searchType=title&keyword=
	@RequestMapping("/List")
	public ModelAndView list(
			@RequestParam HashMap<String, Object> map) {
		
		System.out.println("map" + map);
		// map:{menu_id=MENU01, nowpage=1}
		// map:{menu_id=MENU01, nowpage=1, searchType=, keyword=}
		
		// 메뉴 목록 조회
		List<MenuDTO> menuList = menuMapper.getMenuList();
		
		// 자료실 목록 조회(10 개씩)
		// 해당 메뉴의 전체 자료수 count()
		int totalcount = pdsMapper.count(map); // menu_id, searchType, keyword 전달예정
		System.out.println("totalcount" + totalcount);
		
		//----------------------------------------------------------
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pds/list"); // psd폴더 밑의 list를 준다
		
		mv.addObject("menuList", menuList);
		// mv.addObject("searchDto", searchDto);
		
		mv.addObject("map", map);
		return mv;
		
	}
}
