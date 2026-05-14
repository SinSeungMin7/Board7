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
  table { width:100%; }
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
    
  input[type=text], input[type=number] {
     width : 100%;
  }
  input[type=submit], input[type=button] {
     width : 100px;
  }
  
</style>
<body> 
  <main>
    <h2>메뉴 등록</h2>
    <form  action="/Menus/Write2" method="get">
     <table>      
      <tr>
        <td>메뉴 이름</td>
        <td><input type="text" name="menu_name" /></td>        
      </tr>      
      <tr>
        <td colspan="2">
          <input type="submit"  value="추가" />
          <input type="button"  value="목록" 
            onclick="window.location.href='/Menus/List'"
          />        
        </td>
      </tr>
     </table>    
    </form>
  
  </main>
</body>
</html>    















