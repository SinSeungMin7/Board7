package com.green.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.green.interceptor.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthInterceptor  authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// authInterceptor 를 동작시킬때 모든 페이지(/**)를 대상으로 한다
		// http://localhost:9090 밑의 모든 파일
		// 제외 "/css/**", "/img/**", "/js/**" 경로는 interceptor의 대상아님
		// .addPathPatterns("/Board/**")  
		//  -> http://localhost:9090/Board 밑의 모든 파일
		//  로그인 대상 페이지를 설정 "/**"  : ** 는 하위폴더포함
		registry.addInterceptor( authInterceptor )
		   // .addPathPatterns("/**")
		   //.addPathPatterns("/Board/**")
		   .addPathPatterns("/Board/**", "/BoardPaging/**")
		    .excludePathPatterns("/css/**", "/img/**", "/js/**");
				
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
}







