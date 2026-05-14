<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/img/favicon2.png" type="image/x-icon">
<link href="/css/common.css" rel="stylesheet" />

<style>
  table { width:100%;  }
  td {
     padding:5px 10px;
     text-align : center;
     &:nth-of-type(1) {
	     background: black;
	     color : white;
	     border:1px solid white;
	 } 
  }
  tr:last-child > td {
      background: white;
      border : 1px solid black; 
  }
    
  input[type="text"], input[type=number], input[type=password]  {
     width : 100%;
  }
  input[type=submit], input[type=button] {
     width : 100px;
  }
  input[name=userid] {
     width : 65%;
  }
  
  textarea {
  	width  : 100%;
  	height : 300px;
  }
  
  #table1 {  margin-bottom : 150px; }
  
</style>
<body> 
  <main>
    <%@include file="/WEB-INF/include/menuspaging.jsp" %>  
  
    <h2 class="h2"><b id="mname">${ menu_name }</b> 새 글 쓰기</h2>
    <form  action="/BoardPaging/Write" method="post">
     <input type="hidden" name="menu_id" value="${ menu_id }" />
     <input type="hidden" name="nowpage" value="${ nowpage }" />
     <table id="table1">
      <tr>
        <td><span class="red">*</span>제목</td>
        <td>
          <input type="text"     name="title"   />
        </td>
      </tr>
      <tr>
        <td><span class="red">*</span>작성자</td>
        <td><input type="text"   name="writer" 
             value="${ sessionScope.login.userid }" /></td>        
      </tr>    
      <tr>
        <td>내용</td>
        <td><textarea name="content"></textarea></td>        
      </tr>   
      <tr>
        <td colspan="2">
          <input type="submit"  value="추가" />
          <input type="button"  value="목록" 
            onclick="location.href='/BoardPaging/List?menu_id=${menu_id}&nowpage=${nowpage}'"
          />        
        </td>
      </tr>
     </table>    
    </form>
  
  </main>
  
    <script>
	  const  mnameEl    =  document.querySelector('#mname');
	  let    menunameEl =  document.querySelector('.menu .active')
	  mnameEl.innerHTML =  menunameEl.innerHTML;
	  
	  // Javascript 코딩 : client validation
	  
	</script>
  
  
  
  
</body>
</html>    















