package com.green.interceptor;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.green.user.controller.UserController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
 	
	// Interceptor : 페이지가 이동될때 Controller 앞에서 가로채기하는 클래스
	// 1.preHandle 전처리(로그인 체크)	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			  throws Exception {
		
		//System.out.println(  "1.preHandle" );
		
		// 요청 주소
		String  requestURI  = request.getRequestURI();    // /Board/List, /BoardPaging/List
		String  qryStr      = request.getQueryString();   // menu_id=MENU01, menu_id=MENU01&nowpage=1 
		String  loc         = requestURI + "?" + qryStr;  // /BoardPaging/List?menu_id=MENU01&nowpage=1
		//System.out.println("요청 주소:" + loc);
		
		// /Users/LoginForm", "/Users/Login" 제외
		if( requestURI.contains("/Users/LoginForm" )  ) {
			return true;
		}
		if( requestURI.contains("/Users/Login" )  ) {
			return true;
		}
		
		HttpSession  session  =  request.getSession();
		// 사용자 로그인정보를 세션 메모리에 user 저장
		Object       login    = session.getAttribute("login");
		//System.out.println(login);
		
		session.setAttribute("loc", loc);

		if( login == null  )  {
			// 로그인 되어 있지 않다. 로그인페이지로 이동
			response.sendRedirect("/Users/LoginForm");
			return false;
		}
		
		// preHandle 의 return 문의 의미
		// 컨트롤러 요청 url 로 가도되나 안되나 결정
		//  return true : 컨트롤러 url 가게 된다
		
		return true;
	}

	// postHandle() 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		// System.out.println(  "2.postHandle" );
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
}









