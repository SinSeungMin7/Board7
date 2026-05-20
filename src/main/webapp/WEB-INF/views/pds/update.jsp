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
  #btnAddFile {
     width  :  300px;
  }
  
  textarea {
  	width  : 100%;
  	height : 300px;
  }
  
  #table1 {  margin-bottom : 150px; }
  
</style>
<body> 
  <main>
    <%@include file="/WEB-INF/include/menuspdspaging.jsp" %>  
  
    <h2 class="h2"><b id="mname"></b> 자료실 수정</h2>
    <form  action="/Pds/Update" method="post"
          enctype = "multipart/form-data" > 
     <input type="hidden" name="idx"     value="${ map.idx }" />
     <input type="hidden" name="menu_id" value="${ map.menu_id }" />
     <input type="hidden" name="nowpage" value="${ map.nowpage }" />
     <table id="table1">
      <tr>
        <td>글번호</td>
        <td>${ pds.idx }</td>
        <td>조회수</td>
        <td>${ pds.hit }</td>
      </tr>
      <tr>
        <td>작성자</td>
        <td>${ pds.writer }</td>
        <td>작성일</td>
        <td>${ pds.regdate }</td>
      </tr>
      <tr>
        <td><span class="red">*</span>제목</td>
        <td colspan="3">
          <input type="text"     name="title"  value="${ pds.title }" />
        </td>
      </tr>  
      <tr>
        <td>내용</td>
        <td colspan="3"><textarea name="content">${ pds.content }</textarea></td>        
      </tr> 
      <tr> 
        <td>파일</td>  
        <td id = "tdfile" colspan="3">
          <c:forEach  var="file"  items="${ fileList }">
            <div  class="text-start">  
               <a  class = "aDelete" 
                style = "text-decoration:none;"
                href  = "/deleteFile/${ file.file_num }">❌</a>
               
               <a href="/Pds/filedownload/${file.file_num}">
               ${ file.filename }
               </a>
            </div> 
          </c:forEach> 
        
          <hr />
          <!-- 새 파일을 추가 -->
          <div id="addNewFile">
          <input type="button"  id="btnAddFile"  value="파일추가(최대 100MByte)" /><br>
          <input type="file"    name="upfile"    class="upfile"  multiple /><br>
          </div>             
        </td>  
      </tr>  
      <tr>
        <td colspan="4">
          <input type="submit"  value="추가" />
          <input type="button"  value="목록" id="goList" />    
        </td>
      </tr>
     </table>    
    </form>
  
  </main>
  
    <script>
      // 메뉴제목출력
	  const  mnameEl    =  document.querySelector('#mname');
	  let    menunameEl =  document.querySelector('.menu .active')
	  mnameEl.innerHTML =  menunameEl.innerHTML;
	  
	  // 목록으로 이동
	  const  goListEl  = document.querySelector('#goList')
	  goListEl.onclick = function() {
		  location.href='/Pds/List?menu_id=${map.menu_id}&nowpage=${map.nowpage}'
	  }	  
	  
	  // 파일입력창 추가
	  const  btnAddFileEl  =  document.querySelector('#btnAddFile')
	  const  tdfileEl      =  document.querySelector('#tdfile')
	  const  addNewFileEl  =  document.querySelector('#addNewFile')
	  let    tag           =  '<input type="file" name="upfile" class="upfile" multiple /><br>'  
	  let    html          =  addNewFileEl.innerHTML 
	  // js 에서 실행할때 새로 추가된 버튼은 이벤트가 한번만 작동 btnAddFileEl
	  // 해결 : 이벤트를 부모 element 에 설정
	  tdfileEl.addEventListener('click', function( e ) {
		  console.dir( e.target )  // #btnAddFile, .upfile
		  if( e.target.id == 'btnAddFile' ) {
			  html                    +=  tag
			  addNewFileEl.innerHTML   = html 		 
		  }
	  })
	  	  	  
	  // 입력항목 체크 : title 은 필수입력
	  
	  // ❌ 을 클릭하면
	  const  aDeleteEls  =  document.querySelectorAll('.aDelete')
	  aDeleteEls.forEach( function( aDeleteEl, index  ) {
		  aDeleteEl.addEventListener('click', function( e ) {
			  // 이동금지
			  e.preventDefault();
			  e.stopPropagation();
			  //  alert('❌ 을 클릭')
			  const  aEl  =  e.target
			  console.dir( aEl )
			  //const  parentDiv = document.querySelector('div:has(.aDelete)')
	          // console.dir(parentDiv)
			  let    loc  =  aEl.href   // "http://localhost:8080/deleteFile/11"
			  // 비동기호출 서버명령을 실행하고 돌아돈다
			  
			  fetch( loc )
				  .then((response) => response.json())
				  .then((json) => {
					  console.log(json)
					  // alert(json.status)
					 
					  aEl.parentElement.remove();					  
				  })
			      .catch((error) => {
			    	  console.dir(error)
			    	  alert(error)			    	  
			      } )
			  
			
		  } )  
	  })
	  
	  
 	  
	</script>
  
  
  
  
</body>
</html>    















