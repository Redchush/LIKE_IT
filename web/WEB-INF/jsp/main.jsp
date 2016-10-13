<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<!DOCTYPE html>
<html>

<head>
  <title>
    ${title}
  </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
  <script src="<c:url value="/resources/js/main.js"/>" > </script>
  <script src="<c:url value="/resources/js/util.js"/>" > </script>
</head>

<body>
<c:choose>
  <c:when test="${not empty sessionScope.role && sessionScope.role != 'anonymous'}">
    <jsp:include page="/WEB-INF/jsp/part/header/header_user.jsp"/>
  </c:when>

  <c:otherwise>
    <jsp:include page="/WEB-INF/jsp/part/header/header_anonimus.jsp">
      <jsp:param name="language" value="${language}"/>
    </jsp:include>
  </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jsp/part/main_block.jsp">
  <jsp:param name="language" value="${language}"/>
</jsp:include>

<footer class="footer"></footer>
</body>

</html>