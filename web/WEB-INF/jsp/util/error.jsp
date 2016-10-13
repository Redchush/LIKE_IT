<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.error_page.h1" var="h1"/>
<fmt:message bundle="${loc}" key="locale.error_page.msg" var="msg" />
<fmt:message bundle="${loc}" key="locale.success.home" var="home"/>

<fmt:message bundle="${loc}" key="locale.error_page.try_back" var="back"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>${title}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
</head>

<body class="error_page">
   <div class="outer">
    <h1 class="center_text">${h1}</h1>
    <p class="msg_box">${msg}</p>
    <div class="linksContainer">
      <a class="links action_link" href="<c:url value="${sessionScope.prevQuery.backQuery}"/>">${back}</a>
      <a class="links action_link" href="<c:url value="index"/>">${home}</a>
    </div>
  </div>
</body>

</html>