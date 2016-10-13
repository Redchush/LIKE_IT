<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}" />

<fmt:setBundle basename="resource.frontend.locale" var="loc" scope="request" />
<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.invalid.error_pattern" var="incorrect"/>
<fmt:message bundle="${loc}" key="locale.invalid.tooltip_ask" var="unnamed_invalid" />
<fmt:message bundle="${loc}" key="locale.enter.error_msg.all_login" var="er_or" />


<fmt:message bundle="${loc}" key="locale.signIn.title" var="edit_head"/>
<fmt:message bundle="${loc}" key="locale.signIn.email" var="email" />
<fmt:message bundle="${loc}" key="locale.signIn.email.notValid" var="eNotValid" />

<fmt:message bundle="${loc}" key="locale.signIn.login" var="login" />
<fmt:message bundle="${loc}" key="locale.signIn.login.notValid" var="lNotValid" />

<fmt:message bundle="${loc}" key="locale.signIn.password" var="password" />
<fmt:message bundle="${loc}" key="locale.signIn.password.notValid" var="pNotValid" />

<fmt:message bundle="${loc}" key="locale.signIn.confirm" var="confirm" />
<fmt:message bundle="${loc}" key="locale.signIn.confirm.notValid" var="notConfirm" />


<fmt:message bundle="${loc}" key="locale.signIn.link.toLogIn" var="toLogIn" />
<fmt:message bundle="${loc}" key="locale.error.registration.unnamed" var="exist" />
<fmt:message bundle="${loc}" key="locale.signIn.submit" var="submit" />

<fmt:message bundle="${loc}" key="locale.tooltip.password.1p" var="password_tooltip_1" />
<fmt:message bundle="${loc}" key="locale.tooltip.password.2p" var="password_tooltip_2" />
<fmt:message bundle="${loc}" key="locale.tooltip.login.1p" var="login_tooltip_1" />
<fmt:message bundle="${loc}" key="locale.tooltip.login.2p" var="login_tooltip_2" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<c:set var="action" value="registration" scope="request"/>

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
  <script type="text/javascript" src="<c:url value="/resources/js/user_enter_form.js"/>" > </script>
</head>


<body>
<jsp:include page="/WEB-INF/jsp/part/header/header_anonimus.jsp"/>
<div class="outer">
  <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp"/>
  <c:if test="${pageContext.request.method eq 'POST' and empty requestScope.isSend}">
    <c:set var="isSend" value="true" scope="request"/>
    <jsp:forward page="/FrontController"/>
  </c:if>
  <div class="inner">
    <div class="top_box">
      <div class="langs">
        <a href="<c:url value="sign_up?language=en"/>" tabindex="1" class="lang">EN</a>
        <span>|</span>
        <a href="<c:url value="sign_up?language=en"/>" tabindex="2" class="lang">${ru}</a>
      </div>
      <a href="index" class="formicon icon_delete"></a>
    </div>

    <form  id="register_form" name = "regitster_form" action="sign_up"
           novalidate="" data-remote="true" method="post" class="s-form register_form">
      <input type="hidden" name="command" value="${action}" />
      <div class="title">${edit_head}</div>
      <div class="s-field">
        <input type="email" tabindex="3" autofocus=""
               name="email" required placeholder=${email}>
        <div class="formicon shifted icon_email"></div>
        <div class="s-error">${eNotValid}
          <div class="s-error_OK">OK</div>
        </div>
      </div>

      <div class="s-field">
        <input type="text" tabindex="4" autofocus="" name="login" class="tipped"
               required pattern="[a-zA-Z0-9_-]{3,20}" placeholder=${login}>
        <div class="formicon shifted icon_login"></div>

        <div class="tooltip">
          <div class="icon_info"> </div>
          <div class="tooltip_text">
            <p>${login_tooltip_1}<</p>
            <p>${login_tooltip_2}</p>
          </div>
        </div>

        <div class="s-error">${lNotValid}
          <div class="s-error_OK">OK</div>
        </div>
      </div>

      <div class="s-field">
        <input type="password" tabindex="5" name="password" class="tipped"
               required pattern="[a-zA-Z0-9_-]{6,20}" placeholder=${password}>
        <div class="formicon shifted icon_password"></div>

        <div class="tooltip">
          <div class="icon_info"> </div>
          <div class="tooltip_text">
            <p>${password_tooltip_1}</p>
            <p>${password_tooltip_2} </p>
          </div>
        </div>

        <div class="s-error">${pNotValid}<div class="s-error_OK">OK</div></div>
      </div>

      <div class="s-field">
        <input type="password" tabindex="6" autofocus="" name="confirm_pas"
               required pattern="[a-zA-Z0-9_-]{6,20}" placeholder=${confirm}>
        <div class="formicon shifted icon_password"></div>
        <div class="s-error">${notConfirm}<div class="s-error_OK">OK</div></div>
      </div>

      <div class="s-buttons">
        <button tabindex="7" class="submit_btn" name="go" type="submit">${submit}
        </button>
      </div>

    </form>

    <div class="links">
      <a href="log_in" tabindex="8" class="remind" >${toLogIn}</a>
    </div>
  </div>

</div>
</body>

</html>