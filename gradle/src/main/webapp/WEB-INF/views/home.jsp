<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/img/favicon2.png" type="image/x-icon">
<link href="/css/common.css" rel="stylesheet" />
</head>
<body>
  <main>
    <h2>Home</h2>
    <a href="/test">Test</a>
   
    <div><a href="/Menus/WriteForm">새 메뉴추가</a></div>
    <div><a href="/Menus/WriteForm2">새 메뉴추가2</a></div>
    <div><a href="/Menus/List">메뉴목록</a></div> 
    <div>&nbsp;</div> 
   
    <div><a href="/Users/List">사용자 목록</a></div>
    <div><a href="/Users/WriteForm">사용자 추가</a></div>
    <div><a href="/Users/IdDupCheck2?userid=aaa" target="_blank">아이디 중복 테스트</a></div>
    <div>&nbsp;</div>
   
    <div><a href="/Board/List?menu_id=MENU01">게시글 목록</a></div>
    <div><a href="/Board/WriteForm?menu_id=MENU01">게시글 추가</a></div>
    <div>&nbsp;</div>
    
    <div><a href="/BoardPaging/List?menu_id=MENU01&nowpage=1">게시글 목록(페이징)</a></div>
    <div><a href="/BoardPaging/WriteForm?menu_id=MENU01&nowpage=1">게시글 추가(페이징)</a></div>
    <div>&nbsp;</div>
    
    <div>
      ${ sessionScope.login.username } 님 환영합니다<br>
      당신의 가입일은 ${ sessionScope.login.regdate } 입니다<br>
      <a href="/Users/Logout">로그아웃</a><br>
      <a href="/Users/LoginForm">로그인</a>
    </div>
    
  </main>
  
</body>
</html>








