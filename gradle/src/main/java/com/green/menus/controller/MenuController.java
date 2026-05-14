package com.green.menus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.menus.dto.MenuDTO;
import com.green.menus.mapper.MenuMapper;

@Controller
public class MenuController {
	
	// @Autowired: Spring Container 에 미리 new 된 Component 를 찾아서
	//  menuMapper 변수에 저장해라
	// @Autowired()  - 객체 타입으로 찾아서 연결
	// @Qulified()   - 객체 이름으로 찾아서 연결
	// 최근기법은 생성자를 이용한 방식으로  변화 
	@Autowired
	private  MenuMapper  menuMapper;
	
	// 메뉴 목록 조회  http://localhost:9090/Menus/List
	@RequestMapping("/Menus/List")   
	public   String   list( Model model  ) {
		// 조회한결과를 ArrayList 로 돌려준디
		List<MenuDTO> menuList = menuMapper.getMenuList(); // ArrayList 결과를받는다
		System.out.println(menuList);
		
		model.addAttribute("msg", "하하");
		model.addAttribute("menuList", menuList);
		
		return "menus/list";   // /WEB-INF/views/menus/list.jsp
	}
	
	// /Menus/WriteForm
	@RequestMapping("/Menus/WriteForm")
	public  String  writeForm() {		
		return  "menus/write";  // write.jsp 로 이동
	}
	
	// /Menus/Write?menu_id=MENU09&menu_name=GIT&menu_seq=7
	// http://localhost:8080/Menus/Write?menu_id=MENU09&menu_name=GIT&menu_seq=7
	@RequestMapping("/Menus/Write")
	//public  String  write(String menu_id, String menu_name, int menu_seq) {
	public  String  write( MenuDTO  menuDTO, Model model ) {
		
		// 넘어온 값
		System.out.println("넘어온값(menuDTO):" + menuDTO);
		//System.out.println( "menu_id="    + menuDTO.getMenu_id() );
		//System.out.println( "menu_name="  + menuDTO.getMenu_name() );
		//System.out.println( "menu_seq="   + menuDTO.getMenu_seq() );
			
		// db 에 저장
		menuMapper.insertMenu( menuDTO );
		
		return  "redirect:/Menus/List";
		/*
		// 다시 조회 -> menuList
		List<MenuDTO> menuList = menuMapper.getMenuList();		
		model.addAttribute("menuList", menuList);
		 
		return  "menus/list";
		*/
		
	}
	
	// http://localhost:8080/Menus/Delete?menu_id=MENU06
	@RequestMapping("/Menus/Delete")
	public  String   delete( MenuDTO menuDTO ) {	
		System.out.println("삭제 대상:" + menuDTO );		
		// db 에서 삭제
		menuMapper.deleteMenu( menuDTO );  // mybatis mapper에는 DTO전달
		
		return  "redirect:/Menus/List";
	}
	/*
	@RequestMapping("/Menus/Delete")
	public  String   delete( String menu_id  ) {
		System.out.println( "삭제할 menu_id=" + menu_id );
		
		MenuDTO menuDTO = new MenuDTO(menu_id, null, 0);
		// db 에서 삭제
		menuMapper.deleteMenu( menuDTO );  // mybatis mapper에는 DTO전달
		
		return  "redirect:/Menus/List";
	} 
	*/
	
	// http://localhost:8080/Menus/UpdateForm?menu_id=MENU09
	@RequestMapping("/Menus/UpdateForm")
	public  String  updateForm( MenuDTO  menuDTO, Model model  ) {
		System.out.println("넘어온 menuDTO : " + menuDTO );
		
		// 수정할 자료를 db 에서 검색 : 수정할 정보가 담긴 조회된 menu
		MenuDTO  menu   =   menuMapper.getMenu( menuDTO );
		model.addAttribute("menu", menu);		
		System.out.println("조회한 menuDTO : " + menu );
		
		return "menus/update";		
	}
	
	// /Menus/Update
	@RequestMapping("/Menus/Update")
	public  String  update( MenuDTO  menuDTO ) {
		
		// 넘어온 정보로 db 를 수정한다
		menuMapper.updateMenu( menuDTO );
		
		return  "redirect:/Menus/List";
	}
	
	// /Menus/WriteForm2 - 메뉴이름으로만 추가하시
	@RequestMapping("/Menus/WriteForm2")
	public  String  writeForm2() {
		
		return "menus/write2";
		
	}
	
	// /Menus/Write2?menu_name=JAVA
	@RequestMapping("/Menus/Write2")
	public  String  write2( MenuDTO menuDTO  ) {
		
		menuMapper.insertMenu2( menuDTO  );
		
		return "redirect:/Menus/List";
	}
	
	
	
	
}



















