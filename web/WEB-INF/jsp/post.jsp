<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>

  <c:when test="${pageContext.request.method eq 'POST' and empty requestScope.isSend}">
    <c:set var="isSend" value="true" scope="request"/>
    <jsp:forward page="/FrontController"/>
  </c:when>

  <c:when test="${empty param.id or param.id eq '-2' or
        (not empty requestScope.msg_bean and requestScope.msg_bean.code eq 4)}">
    <jsp:forward page="/WEB-INF/jsp/util/not_found.jsp"/>
  </c:when>

  <c:when test="${not empty param.id and empty requestScope.postVO}">
    <jsp:forward page="FrontController">
      <jsp:param name="command" value="view_single_post"/>
    </jsp:forward>
  </c:when>

  <c:otherwise>
    <jsp:include page="/WEB-INF/jsp/part/post_block.jsp"/>
  </c:otherwise>
</c:choose>
