
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.profile.greeting" var="greeting" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />
<fmt:message bundle="${loc}" key="locale.success.logination" var="success" />
<fmt:message bundle="${loc}" key="locale.success.fill" var="fill" />
<fmt:message bundle="${loc}" key="locale.success.home" var="home" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title>
    ${title}
  </title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
</head>

<body class="success_page">
  <div class="outer">
    <div class="msg_box">
      <h1>${greeting} ${sessionScope.login}</h1>
      <h4>${success}</h4>
      <div class="linksContainer">
        <a class="links" href="personal?state=empty">${fill}</a>
        <a class="links" href="index">${home}</a>
      </div>
    </div>
  </div>
  
</body>

</html>