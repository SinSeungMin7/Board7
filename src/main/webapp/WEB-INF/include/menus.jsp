<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <%@taglib  prefix="c"  uri="jakarta.tags.core" %>

  <table class="menu">
    <tr>
    
      <c:forEach var="menu"  items="${ menuList }">
        <td>
         <a href="/Board/List?menu_id=${menu.menu_id}"
   			class="${menu.menu_id eq menu_id ? 'active' : ''}">
   				${menu.menu_name}
		 </a>
        </td>
      </c:forEach>
          
    </tr>
  </table>
    
   
   
   
   
   
   
    