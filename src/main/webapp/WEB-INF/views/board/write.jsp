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
    <%@include file="/WEB-INF/include/menus.jsp" %>  
  
    <h2>${ menu_name } 새 글 쓰기</h2>
    <form  action="/Board/Write" method="post">
     <input type="hidden" name="menu_id" value="${ menu_id }" />
     <table id="table1">
      <tr>
        <td><span class="red">*</span>제목</td>
        <td>
          <input type="text"     name="title"   />
        </td>
      </tr>
      <tr>
        <td><span class="red">*</span>작성자</td>
        <td><input type="text" name="writer" /></td>        
      </tr>    
      <tr>
        <td>내용</td>
        <td><textarea name="content"></textarea></td>        
      </tr>   
      <tr>
        <td colspan="2">
          <input type="submit"  value="추가" />
          <input type="button"  value="목록" 
            onclick="location.href='/Board/List?menu_id=${menu_id}'"
          />        
        </td>
      </tr>
     </table>    
    </form>
  
  </main>
  
  <!-- Javascript 코딩 : client validation -->
  <script>
  
  </script>
  
  
  
  
</body>
</html>    















