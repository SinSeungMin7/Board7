<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib  prefix="c"  uri="jakarta.tags.core" %>    
    
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
      padding    : 5px;
      text-align : center;
   }
   tr:first-child {
      background-color: black;
      color :  white;      
      td  {
         border : 1px solid white;
      } 
   }
   tr:nth-of-type(2) td {
	  text-align :right;
	  padding-right: 20px;
   }
   
   main {
      margin-bottom : 150px; 
   }
</style>

</head>
<body>
	<main>
	  <h2>메뉴 목록 ${msg} </h2>
	  <table>
	    <tr>
	      <td>Menu_id</td>
	      <td>Menu_name</td>
	      <td>Menu_seq</td>
	      <td>삭제</td>
	      <td>수정</td>
	    </tr>
	    <tr>
	      <td  colspan="5">
	       [<a href="/Menus/WriteForm">메뉴등록</a>]&nbsp;&nbsp;&nbsp; 
	       [<a href="/Menus/WriteForm2">메뉴등록2</a>]&nbsp;&nbsp;&nbsp; 
	       [<a href="/">Home</a>] 
	      </td>
	    </tr>
	    
	    <c:forEach  var="menu"  items="${ menuList }">
	    <tr>
	      <td> ${ menu.menu_id   }  </td>    <!-- menu.getMenu_id() -->
	      <td> ${ menu.menu_name }  </td>
	      <td> ${ menu.menu_seq  }  </td>
	      <td> <a href="/Menus/Delete?menu_id=${menu.menu_id}">삭제</a> </td>	      
	      <td> <a href="/Menus/UpdateForm?menu_id=${menu.menu_id}">수정</a>  </td>
	    </tr>
	    </c:forEach>
	  </table>	
	</main>
</body>
</html>









