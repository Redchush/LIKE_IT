<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}" />

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<html>
<head>
  <title>Title</title>
</head>
<body>
<c:set var="toCompare" scope="page">${sessionScope.user_id}</c:set>
<c:set var ="compareTest" value="${toCompare eq param.id}"/>

<c:choose>
  <c:when test="${empty param.id or param.id eq '-2'}">
    <jsp:forward page="/WEB-INF/jsp/util/not_found.jsp"/>
  </c:when>
  <c:when test="${not empty param.id and compareTest}">
      <c:redirect url="personal"/>
  </c:when>
  <c:when test="${not empty param.id and not empty requestScope.msg_bean and requestScope.msg_bean.code eq 4}">
    <jsp:forward page="/WEB-INF/jsp/util/not_found.jsp"/>
  </c:when>
  <c:when test="${not empty param.id and not compareTest and empty requestScope.userVO}">
    <jsp:forward page="FrontController">
      <jsp:param name="command" value="view_single_user"/>
    </jsp:forward>
  </c:when>
  <c:when test="${not compareTest and not empty requestScope.userVO}">
    <jsp:include page="/WEB-INF/jsp/part/profile/user_block.jsp"/>
    <c:out value="<style>
      .arrow_drop,
      .profile_sidebar {
        display: none;
      }
      .profile_main{
       margin-left: 0;
      }

      </style>" escapeXml="false"/>
  </c:when>
</c:choose>
</body>
</html>
