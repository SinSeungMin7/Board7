<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib  prefix="c"  uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/img/favicon2.png" type="image/x-icon">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
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
    
    <div><a href="/Pds/List?menu_id=MENU01&nowpage=1">자료실</a></div>
    <div><a href="/Pds/WriteForm?menu_id=MENU01&nowpage=1">자료실 등록</a></div>
    <div>&nbsp;</div>
    
    <div>
      ${sessionScope.login.username} 님 환영합니다<br>
      당신의 가입일은 ${sessionScope.login.regdate} 입니다<br>
      <c:choose>
      	<c:when test="${sessionScope.login ne null}">
      	<a href="/Users/Logout">로그아웃</a><br>
      	</c:when>
      	<c:otherwise>
      	<a href="/Users/LoginForm">로그인</a>
      	</c:otherwise>
      </c:choose>
    </div>
    
    <div>
    <input type="text" id="num" value="1"/>
    <a id="btnNate" href="https://www.nate.com" class="btn btn-primary">click</a>
    </div>
    
    
  </main>
  
  <script>
  	const  btnNateEl  = document.querySelector('#btnNate')
  	const  numEl  = document.querySelector('#num')
  	btnNateEl.onclick = function(e) {
  		
				e.preventDefault()   // 기본에빈트 취소
				e.stopPropagation()
				if( numEl.value == "2")
					location.href = this.href // this.gref == e.target.href
			
  			
  		// msgEl.innerHTML = '<h2>하하하</h2>'
  	}
  </script>
  
</body>
</html>








