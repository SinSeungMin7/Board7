<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib  prefix="c"  uri="jakarta.tags.core" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/img/favicon2.png" type="image/x-icon">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link href="/css/common.css" rel="stylesheet" />

<style>
   table { width:100%;  }
   td {
      padding    : 5px;
      text-align : center;
   }
   #list {
	   td:nth-of-type(1)  {width:100px;}	/* 번호    */	  
	   td:nth-of-type(2)  {width:340px;}	/* 제목	   */
	   td:nth-of-type(3)  {width:100px;}	/* 글쓴이  */	 
	   td:nth-of-type(4)  {width:80px;}	    /* 파일수  */
	   td:nth-of-type(5)  {width:100px;}    /* 날짜	   */
	   td:nth-of-type(6)  {width:80px;}     /* 조회수  */
   }
     
   tr:first-of-type {
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
   .title { text-align:left;  } 
 
   
   main {
      margin-bottom : 150px; 
   }
   
   #paging > table  {
      width : 60%;
      margin : 0 auto;
      td {
      	
         border:1px solid red;
         background-color:white;
         color: white;
         a {
            display:block;
         	text-decoration: none;
         }         
      }
   
   }
   
   #search {      
      width      : 30%;       
      margin     : 10px auto;
   }
</style>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>

<body>
	<main>
	  <%@include file="/WEB-INF/include/menuspdspaging.jsp" %> 
	
	  <h2 class="h2"><b id="mname"></b> 자료실</h2>
	  <table id="list" class="table  table-hover">
 
	    <tr>
	      <td>번호</td>
	      <td>제목</td>
	      <td>글쓴이</td>
	      <td>파일수</td>
	      <td>날짜</td>
	      <td>조회수</td>	
	    </tr>

	    <tr>
	      <td  colspan="6">
	       [<a href="/Pds/WriteForm?menu_id=${map.menu_id}&nowpage=${map.nowpage}">
	       새 글 등록
	       </a>]&nbsp;&nbsp;&nbsp; 
	       [<a href="/">Home</a>] 
	      </td>
	    </tr>
	    
	    <c:forEach  var="pds"  items="${ pdsList }">
	    <tr>
	      <td> ${  pds.idx      }  </td>
	      <td class="title"> 
	        <a href ="/Pds/View?idx=${pds.idx}&menu_id=${map.menu_id}&nowpage=${map.nowpage}">
	        ${ pds.title    }
	        </a>  
	      </td>
	      <td> ${ pds.writer      }  </td>
	      <td> ${ pds.filescount  }  </td>
	      <td> ${ pds.regdate     }  </td>
	      <td> ${ pds.hit         }  </td>
	    </tr>
	    </c:forEach>
	  </table>	
	  
	  
	  <form action="/Pds/List" method="get">
	  <input type="hidden" name="menu_id" value="${ map.menu_id }" />	  
	  <input type="hidden" name="nowpage" value="${ map.nowpage }" />	  
	  <div id="search">
	    <select name="searchType">   
	      <option value="title">제목</option>     <!-- searchType=title -->
	      <option value="content">내용</option> 
	      <option value="writer">작성자</option> 
	    </select>
	    <input type="text" name="keyword"  value="${ map.keyword }"/>
	    <input type="submit" value="검색" />	    
	  </div>
	  </form>
	  
	  <%@include file="/WEB-INF/include/pagingpds.jsp" %> 
	  
	</main>
	
	<!--Javascript -->
	<script>
	  const  mnameEl    =  document.querySelector('#mname');
	  let    menunameEl =  document.querySelector('.menu .active')
	  mnameEl.innerHTML =  menunameEl.innerHTML;
	</script>
	
</body>
</html>









