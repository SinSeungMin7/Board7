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
  
#table1 {
     margin-bottom : 150px;
     td {
        &:nth-of-type(1) {
           width : 150px;  
           background: black;
	       color : white;         
        }
        &:nth-of-type(2) {
           width : 150px;
           background: white;
	       color : black;           
        }
        &:nth-of-type(3) {
           width : 150px;
           background: black;
	       color : white; 
	       border-bottom : 1px solid white;          
        }
        &:nth-of-type(4) {
           width : 150px;
           background: white;
	       color : black;                  
        }
     }
  }
  
  #table1  tr:last-of-type > td {
      background: white;
      border : 1px solid black; 
  }  
  #table1  tr:nth-of-type(3) td:nth-of-type(2) { 
      text-align: left;
  }
  #table1  tr:nth-of-type(4) {
      height : 400px;
      td:nth-of-type(2) {
         text-align:left;
         vertical-align: baseline;
      }
  }
  
  textarea {
     width:100%;
     height : 400px;     
  }
  
  input[name="title"]
  ,textarea {
     padding:5px;
  }  
</style>
<body> 
  <main>
    <%@include file="/WEB-INF/include/menuspaging.jsp" %>  
  
    <h2 class="h2"><b id="mname">${ menu_name }</b>게시글 수정</h2>
    <form  action="/BoardPaging/Update" method="post">
     <input type="hidden" name="idx" value="${ board.idx }" />
     <input type="hidden" name="menu_id" value="${ menu_id }" />
     <input type="hidden" name="nowpage" value="${ nowpage }" />
     <table id="table1">
     <tr>
     	<td>번호</td>
     	<td>${board.idx}</td>
     	<td>조회수</td>
     	<td>${board.hit}</td>
     </tr>
     <tr>
     	<td>작성자</td>
     	<td>${ board.writer }</td>
     	<td>작성일</td>
     	<td>${ board.regdate }</td>
     </tr>
      <tr>
        <td><span class="red">*</span>제목</td>
        <td colspan="3">
          <input type="text"     name="title"  value="${board.title}" />
        </td>
      </tr>  
      <tr>
        <td><span class="red">*</span>내용</td>
        <td colspan="3">
        <textarea name="content">${ board.content }</textarea>
        </td>        
      </tr>   
      <tr>
        <td colspan="4">
          <input type="submit"  value="수정" />
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















