<!--
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.restore.title" var="edit_head" />
<fmt:message bundle="${loc}" key="locale.restore.email" var="email" />
<fmt:message bundle="${loc}" key="locale.restore.email.notValid" var="eNotValid" />

<fmt:message bundle="${loc}" key="locale.restore.link.toEnter" var="toEnter" />
<fmt:message bundle="${loc}" key="locale.restore.notExist" var="notExist" />
<fmt:message bundle="${loc}" key="locale.restore.submit" var="submit" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />
-->

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
  <script src="../../../resources/js/user_enter_form.js"></script>
</head>

<body>
  <div class="showMask"></div>
  <div class="outer">
    <div class="msg_box">${notExist}</div>
    <div class="inner">
      <div class="top_box">
        <a href="index" class="formicon icon_delete"></a>
      </div>

      <form id="restore_form" novalidate="" method="post" class="s-form restore_form">
        <input type="hidden" name="command" value="restoration" />

        <div class="title">${edit_head}</div>
        <div class="s-field">

          <input type="email" tabindex="1" autofocus="" name="email" required placeholder=${email}>
          <div class="formicon shifted icon_email"></div>
          <div class="s-error">${eNotValid}</div>
        </div>

        <div class="s-buttons">
          <button tabindex="2" class="submit_btn" name="go" type="submit">${submit}</button>
        </div>
        <div class="links">
          <a href="log_in"> ${toEnter} </a>
        </div>
      </form>
    </div>
  </div>
</body>

</html>