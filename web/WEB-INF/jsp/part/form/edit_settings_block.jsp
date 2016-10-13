<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />

<fmt:message bundle="${loc}" key="locale.forms.title.edit_settings" var="edit_head"/>
<fmt:message bundle="${loc}" key="locale.forms.settings.lang_descr" var="lang_descr"/>
<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.common.language" var="lang_title" />

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

<fmt:message bundle="${loc}" key="locale.tooltip.password.1p" var="password_tooltip_1" />
<fmt:message bundle="${loc}" key="locale.tooltip.password.2p" var="password_tooltip_2" />

<fmt:message bundle="${loc}" key="locale.tooltip.login.1p" var="login_tooltip_1" />
<fmt:message bundle="${loc}" key="locale.tooltip.login.2p" var="login_tooltip_2" />

<fmt:message bundle="${loc}" key="locale.signIn.email" var="email" />
<fmt:message bundle="${loc}" key="locale.signIn.email.notValid" var="eNotValid" />
<fmt:message bundle="${loc}" key="locale.signIn.confirm" var="confirm" />
<fmt:message bundle="${loc}" key="locale.signIn.confirm.notValid" var="notConfirm" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible"/>
<jsp:setProperty name="msg_resp" property="lang" value="${sessionScope.language}"/>

<jsp:useBean id="factory_info" class="by.epam.like_it.model.vo.validation_vo.factory.MultipleValidationInfoFactory">
  <jsp:setProperty name="factory_info" property="beanName" value="User"/>
  <jsp:setProperty name="factory_info" property="fields" value="${fn:split('login,password,email', ',')}"/>
</jsp:useBean>

<c:set var="infos" value="${factory_info.info}"/>

<jsp:useBean id="entity_bean_loaded" class="by.epam.like_it.model.bean.User" scope="request"/>
<c:set var="isRussial" value="false"/>
<c:set var="isEnglish" value="false"/>
<c:if test="${not empty entity_bean_loaded.languageId}">
  <c:if test="${entity_bean_loaded.languageId eq 1}">
    <c:set var="isRussial" value="true"/>
  </c:if>
  <c:if test="${entity_bean_loaded.languageId eq 2}">
    <c:set var="isEnglish" value="true"/>
  </c:if>
</c:if>
<script type="text/javascript" src="<c:url value="/resources/js/user_enter_form.js"/>" > </script>
<form id="edit_settings" action="<c:url value="/personal/edit_settings"/>"
      name="new_post" method="post" class="s-form">
  <input type="hidden" name="command" value="edit_settings-auth" />
  <div class="title">${edit_head}</div>
  <div class="s-field ">
    <div>
      <p><label for="language">${lang_title}</label></p>
      <p class="description">${lang_descr}</p>
    </div>
    <label class="inline">EN<input id="language" type="radio" value="1" name="language" class="to_wrap" tabindex="2"
                                   <c:if test="${isEnglish}">checked</c:if> ></label>
    <label class="inline">${ru}<input type="radio" value="2" name="language" class="to_wrap" tabindex="3"
                                      <c:if test="${isRussial}">checked</c:if> ></label>
  </div>

  <div class="s-field">
    <p><label for="login">${login}</label></p>
    <div class="wrapper">
      <input id="login" type="text" name="login" required placeholder="${login}" tabindex="4" autofocus
             pattern="${infos.login.pattern}" maxlength="${infos.login.max}"
             value="${entity_bean_loaded.login}">
      <div class="formicon shifted icon_login"></div>
    </div>
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

  <div class="s-field">
    <p><label for="email">${email}</label></p>
    <div class="wrapper">
      <input id="email" type="email"  autofocus=""  name="email" required tabindex="3"
             pattern="${infos.email.pattern}" maxlength="${infos.email.max}"
             value="${entity_bean_loaded.email}">
      <div class="formicon shifted icon_email"></div>
    </div>
    <div class="s-error">${eNotValid}<div class="s-error_OK">OK</div></div>
  </div>

  <div class="s-field">
    <p><label for="password">${password}</label></p>
    <div class="wrapper">
      <input id="password" type="password" tabindex="5" name="password" required
             maxlength="${infos.password.max}" pattern="${infos.password.pattern}"
             value="${entity_bean_loaded.password}">
      <div class="formicon shifted icon_password"></div>
    </div>
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

  <div class="s-field">
    <p><label for="confirm">${confirm}</label></p>
    <div class="wrapper">
      <input id="confirm" type="password" tabindex="6" name="confirm_pas" required
             pattern="[a-zA-Z0-9_-]{6,20}" value="${entity_bean_loaded.password}">
      <div class="formicon shifted icon_password"></div>
    </div>
    <div class="s-error">${notConfirm}<div class="s-error_OK">OK</div></div>
  </div>
  <div class="s-buttons">
    <button tabindex="7" class="submit_btn disabled=" type="submit">${edit}</button>
  </div>
</form>