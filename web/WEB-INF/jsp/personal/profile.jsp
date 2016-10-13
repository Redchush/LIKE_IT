<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<html>
<head>
  <title>${title}</title>
</head>
<body>
<c:if test="${not empty sessionScope.user_id and sessionScope.user_id gt 0}">
  <c:choose>
    <c:when test="${pageContext.request.method eq 'POST' and empty requestScope.isSend}">
      <c:set var="isSend" value="true" scope="request"/>
      <%--handling multipart request--%>
      <c:if test="${empty param.command}">
        <c:set var="command" value="edit_photo-auth" scope="request"/>
      </c:if>
      <jsp:forward page="/FrontController"/>
    </c:when>

    <c:when test="${not empty param.state}">
      <jsp:forward page="/WEB-INF/jsp/personal/empty_profile.jsp"/>
    </c:when>
    <c:when test="${empty param.state and empty requestScope.userVO}">
      <jsp:forward page="/FrontController">
        <jsp:param name="command" value="view_single_user"/>
      </jsp:forward>
    </c:when>

    <c:when test="${empty param.state and not empty requestScope.userVO}">
      <jsp:include page="/WEB-INF/jsp/part/profile/user_block.jsp"/>
    </c:when>
 </c:choose>
</c:if>
</body>
</html>
