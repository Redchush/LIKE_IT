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

<!DOCTYPE html>
<html>

<head>
  <title>${title}</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
  <script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>" > </script>
</head>
<body>
<c:choose>
  <c:when test="${not empty sessionScope.role && sessionScope.role != 'anonymous'}">
    <jsp:include page="/WEB-INF/jsp/part/header/header_user.jsp"/>
  </c:when>
  <c:otherwise>
    <jsp:include page="/WEB-INF/jsp/part/header/header_anonimus.jsp"/>
  </c:otherwise>
</c:choose>
<c:set var="isOwner" value="${sessionScope.user_id eq requestScope.userVO.user.id}" scope="request"/>
<c:if test="${not empty requestScope.checkedOwner}">
   <c:set var="isOwner" value="${requestScope.checkedOwner}" scope="request"/>
</c:if>

<section class="content-wrapper profile_page">
  <div class="center">
    <c:if test="${not empty requestScope.msg_bean and requestScope.msg_bean != null}">
      <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp"/>
    </c:if>
    <jsp:include page="profile_subheader.jsp"/>
    <div class="middle">
      <c:if test="${isOwner}">
        <jsp:include page="profile_menu.jsp"/>
      </c:if>
      <main class="profile_main column">
        <c:choose>
          <c:when test="${empty param.to_include}">
            <jsp:include page="profile_main.jsp"/>
          </c:when>
          <c:otherwise>
            <jsp:include page="${param.to_include}"/>
          </c:otherwise>
        </c:choose>
      </main>
      <div class="clearfix"></div>
    </div>
  </div>
</section>
</body>
