<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" scope="request"/>

<fmt:message bundle="${loc}" key="locale.title" var="title" />
<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.logIn.enter" var="edit_head"/>
<fmt:message bundle="${loc}" key="locale.common.login" var="login" />

<fmt:message bundle="${loc}" key="locale.logIn.login.notValid" var="loginNotValid" />

<fmt:message bundle="${loc}" key="locale.logIn.password" var="password" />
<fmt:message bundle="${loc}" key="locale.logIn.password.notValid" var="pNotValid" />
<fmt:message bundle="${loc}" key="locale.logIn.link.signUp" var="signUp" />
<fmt:message bundle="${loc}" key="locale.logIn.link.restore" var="restore" />

<fmt:message bundle="${loc}" key="locale.logIn.submit" var="submit" />

<fmt:message bundle="${loc}" key="locale.tooltip.password.2p" var="password_tooltip_2" />
<fmt:message bundle="${loc}" key="locale.tooltip.login.2p" var="login_tooltip_2" />
<fmt:message bundle="${loc}" key="locale.common.clear" var="clear" />



<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
  <script type="text/javascript" src="<c:url value="/resources/js/user_enter_form.js"/>" > </script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/part/header/header_anonimus.jsp"/>
<div class="outer">
  <c:choose>
    <c:when test="${not empty param.illegal_msg}">
      <c:set var="msg" scope="page" value="locale.security.illegal_msg${param.illegal_msg}"/>
      <fmt:message bundle="${loc}" key="${msg}" var="full_error"/>
      <div class="msg_box">
        <div class="msg_box_center">
          <c:choose>
            <c:when test="${not empty param.to}">
              ${fn:replace(full_error, "?", param.to)}
            </c:when>
            <c:otherwise>
              ${full_error}
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp">
        <jsp:param name="withTip" value="true"/>
      </jsp:include>
    </c:otherwise>
  </c:choose>

  <jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible"/>
  <jsp:setProperty name="msg_resp" property="lang" value="${language}"/>

  <jsp:useBean id="factory_info" class="by.epam.like_it.model.vo.validation_vo.factory.MultipleValidationInfoFactory">
    <jsp:setProperty name="factory_info" property="beanName" value="User"/>
    <jsp:setProperty name="factory_info" property="fields" value="${fn:split('login,password', ',')}"/>
  </jsp:useBean>

  <c:set var="infos" value="${factory_info.info}"/>

  <div class="inner">
    <div class="top_box">
      <div class="langs">
        <a href="<c:url value="log_in?language=en"/>" class="lang">EN</a>
        <span>|</span>
        <a href="<c:url value="log_in?language=ru"/>" class="lang">${ru}</a>
      </div>
      <a href="index" class="formicon icon_delete"></a>
    </div>

    <form id="login_form" name="login_form"
          action="<c:url value="/log_in"/>"
          method="post" class="s-form login_form" data-load="login_form">
      <input type="hidden" name="command" value="logination"/>
      <div class="title">${edit_head}</div>

      <div class="s-field">
        <div class="text-wrapper">
          <input type="text" tabindex="1" name="login" required class="tipped" autofocus
                 value="<c:out value="${requestScope.prev_bean.login}"/>"
                 maxlength="${infos.login.max}" pattern="${infos.login.pattern}"  placeholder="${login}" >
          <div class="formicon shifted icon_login"></div>
          <div class="tooltip">
            <div class="icon_info"> </div>
            <div class="tooltip_text">
              <jsp:setProperty name="msg_resp" property="info" value="${infos.login}"/>
              <p>${msg_resp.restrictionMsg}</p>
              <p>${login_tooltip_2}</p>
            </div>
          </div>

          <div class="s-error">${loginNotValid}<div class="s-error_OK">OK</div></div>
        </div>
      </div>

      <div class="s-field">
        <div class="text-wrapper">
          <input type="password" tabindex="2" name="password" required class="tipped"
                 maxlength="${infos.password.max}" pattern="${infos.password.pattern}" placeholder="${password}">
          <div class="formicon shifted icon_password"></div>
          <div class="tooltip">
            <div class="icon_info"></div>
            <div class="tooltip_text">
              <jsp:setProperty name="msg_resp" property="info" value="${infos.password}"/>
              <p>${msg_resp.restrictionMsg}</p>
              <p>${password_tooltip_2}</p>
            </div>
          </div>
          <div class="s-error">${pNotValid}<div class="s-error_OK">OK</div></div>
        </div>
      </div>
      <div class="s-buttons">
        <button tabindex="3" class="submit_btn" type="submit">${submit}</button>
      </div>
      <div class="linksContainer">
        <div class="links">
          <a class="remind" href="sign_up">${signUp}</a>
        </div>
        <div class="links">
          <button id="clearer" form="login_form" type="reset">${clear}</button>
        </div>
        <%--<div class="links">--%>
        <%--<a class="remind" href="restore">${restore}</a>--%>
        <%--</div>--%>
      </div>
    </form>
  </div>
</div>
</body>

</html>