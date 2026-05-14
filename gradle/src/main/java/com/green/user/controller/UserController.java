package com.green.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.BoardApplication;
import com.green.controller.HomeController;
import com.green.user.dto.UserDto;
import com.green.user.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Users")
public class UserController {

    private final HomeController homeController;

    private final BoardApplication boardApplication;
	
	@Autowired
	private  UserMapper  userMapper;

    UserController(BoardApplication boardApplication, HomeController homeController) {
        this.boardApplication = boardApplication;
        this.homeController = homeController;
    }
	
	// /Users/WriteForm() 
	@RequestMapping("/WriteForm")
	public  ModelAndView  writeForm() {

		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("users/write");
		mv.addObject("msg", "태훈이");
		
		return  mv;

	}
	
	// /Users/Write?userid=&passwd=&username=&email=
	@RequestMapping("/Write")
	public  ModelAndView  write( UserDto  userDto  ) {
		System.out.println( "UserController write() userDto:" + userDto );
		
		// 넘어온 data 로 db 에 저장
		userMapper.insertUser( userDto  );
		
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Users/List");		
		return  mv;		
	}
	
	// /Users/List
	@RequestMapping("/List")
	public  ModelAndView  list() {
		
		// db 에서 사용자 목록을 조회
		List<UserDto> userList = userMapper.getUserList();
		
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("users/list");
		mv.addObject("userList", userList);
		
		return mv;
	}
	
	// http://localhost:8080/Users/Delete?userid=SKY
	@RequestMapping("/Delete")
	public  ModelAndView  delete( UserDto userDto ) {
		
		// 넘겨받은 자료를 출력
		System.out.println( "userDto2:" +  userDto );
		
		// db 의 자료를 삭재
		userMapper.deleteUser( userDto );
		
		// 목록으로 이동
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return        mv;
	}
	
	// http://localhost:8080/Users/UpdateForm?userid=sea
	@RequestMapping("/UpdateForm")
	public  ModelAndView  updateForm( UserDto  userDto  ) {
		// 넘어온 userDto 정보
		System.out.println( "넘어온 userDto : " + userDto );
		
		// 수정을 위해 db 에서 조회한 정보
		UserDto  user =  userMapper.getUser( userDto );
		System.out.println( "조회된 userDto : " + user );
		
		ModelAndView  mv   =  new ModelAndView();
		mv.setViewName("users/update");
		mv.addObject("user", user);
		return  mv;
		
	}
	
	// http://localhost:8080/Users/Update
	   // userid=sea&oldpwd=1234&passwd=12345&username=%EB%B0%94%EB%8B%A4&email=sea%40green.com
	   // Controller 에서 Map 으로 인자를 받을때는 반드시  @RequestParam 를 사용해야한다
	@RequestMapping("/Update")
	public  ModelAndView  update(@RequestParam Map<String, Object> map) {
		System.out.println("map:" + map);
		// map:{userid=SKY, oldpwd=1234, passwd=12345, username=스카이스카이2, email=SKY2@naver.com}
		userMapper.updateUser2( map );
		
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return mv;
	}
	/*
	@RequestMapping("/Update")
	public  ModelAndView  update( UserDto  userDto, String oldpwd ) {
			
		userMapper.updateUser( userDto, oldpwd );
		
		ModelAndView  mv  =  new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return  mv;
	}
	*/
	
	// 아이디 중복확인 - 결과문자열을 리턴 : 
	// <b class="green">사용가능한 아이디입니다</b>
	// <b class="red">사용가능할 수 없는 아이디입니다</b>
	// /Users/IdDupCheck2?userid=aaa
	@GetMapping("/IdDupCheck2")
	@ResponseBody     // return 되는 글자는 jsp가 아니다
	public  UserDto  idCupCheck2( UserDto userDto ) {
		
		UserDto  user    = userMapper.getIdDupCheck(userDto);   
		if(user == null)
			user = new UserDto();
	    return   user;
		
	}
	
	// /Users/DupCheckWindow?first=true
	@GetMapping("/DupCheckWindow")
	public  ModelAndView   dupCheckWindow( boolean first, HttpSession session ) {	
			
		ModelAndView  mv  =  new ModelAndView();
		// ?first=true 활용방법
		/*
		if( first )
			mv.addObject("first", first);
	    */	
		session.setAttribute("first", "true");
		mv.setViewName("users/idcheck");				
		return mv;
		
	}
	
	// 중복확인 
	// /Users/DupCheck?userid=aaa
	@RequestMapping("/DupCheck")
	public  ModelAndView   dupCheck( UserDto  userDto, HttpSession session ) {
		
		session.setAttribute("first", "");
		UserDto        user    =  userMapper.getUser( userDto );
		String         msg     =  "<b class='red'>사용할 수 없는 아이디 입니다</b>";
		if( user == null )
			msg  = "<b class='green'>사용 가능한 아이디 입니다</b>";
		
		ModelAndView   mv    =  new ModelAndView();
		mv.setViewName("users/idcheck");
		mv.addObject("msg",    msg);		
		return  mv;
	}
	
	//----------------------------------------
	// 로그인폼 /Users/LoginForm
	@RequestMapping("/LoginForm")
	public  String  loginForm() {
		return "users/login";
	}
		
	// 로그인 /Users/Login , userid=, passwd=
	@RequestMapping("/Login")
	public  String   login( 
			UserDto             userDto, 
			HttpServletRequest  request ) {
		
		UserDto      user       =  userMapper.getLogin( userDto );
				
		HttpSession  session    =  request.getSession();
		session.setAttribute("login", user);
		
		String       loc        =  session.getAttribute("loc") + "";  
	
		return  "redirect:" + loc;
		
	}
	
	@RequestMapping("/Logout")
	public  String   logout( HttpServletRequest  request  ) {
		
		HttpSession  session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	
	
	
}



















