<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:choose>
  <c:when test="${pageContext.request.method eq 'POST' and empty requestScope.isSend}">
    <c:set var="isSend" value="true" scope="request"/>
    <jsp:forward page="/FrontController"/>
  </c:when>
  <c:otherwise>
    <jsp:forward page="/WEB-INF/jsp/util/part/login_part.jsp"/>
  </c:otherwise>
</c:choose>

