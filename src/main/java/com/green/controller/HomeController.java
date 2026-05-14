package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	// http://localhost:8080
	@RequestMapping("/")
	public  String  home( ) {
		
		//System.out.println("첫페이지");
		//System.out.println("고쳐졌다");
		return  "home";   // jsp 파일을 찾는다
		                  // /WEB-INF/views/home.jsp
	}

	
	// http://localhost:8080/test
	@RequestMapping("/test")
	@ResponseBody              // 서버가 data(html) 을 내려보낸다
	public  String  test() {
		return  "<h2>Test 입니다</h2>";
	}
	
	/*
	// 	클라이언트 부를때는 fetch() 
	@RequestMapping("/test2")
	@ResponseBody              // 서버가 boardList를 json으로 변경해서 내려보낸다
	public  List<BoardDto>  test2() {
		List<BoardDto> boardList = boardMapper.gertBoardList()
		return  boardList;
	}
	*/
	
}






