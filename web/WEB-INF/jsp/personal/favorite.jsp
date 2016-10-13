<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:choose>
  <c:when test="${empty requestScope.isLoaded}">
    <c:set var="isLoaded" value="true" scope="request"/>
    <jsp:forward page="/FrontController">
      <jsp:param name="command" value="load_favorite_posts-client"/>
    </jsp:forward>
  </c:when>
  <c:otherwise>
    <c:set var="checkedOwner" value="true" scope="request"/>
    <jsp:forward page="/WEB-INF/jsp/part/profile/user_block.jsp">
      <jsp:param name="to_include" value="/WEB-INF/jsp/part/post_view.jsp"/>
    </jsp:forward>
  </c:otherwise>
</c:choose>

