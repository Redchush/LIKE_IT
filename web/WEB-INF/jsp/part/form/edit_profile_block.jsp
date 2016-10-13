<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}" />

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:setBundle basename="resource.db.written.validation" var="valid" />

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.profile.f_name" var="f_name" />
<fmt:message bundle="${loc}" key="locale.profile.l_name" var="l_name" />
<fmt:message bundle="${loc}" key="locale.profile.aboutMe" var="aboutMe" />
<fmt:message bundle="${loc}" key="locale.profile.reg_date" var="reg_date" />

<fmt:message bundle="${loc}" key="locale.profile.f_name.description" var="f_name_desr" />
<fmt:message bundle="${loc}" key="locale.profile.l_name.description" var="l_name_desr" />

<fmt:message bundle="${loc}" key="locale.profile.edit.head" var="edit_head"/>
<fmt:message bundle="${loc}" key="locale.profile.aboutMe.description" var="aboutMe_descr" />
<fmt:message bundle="${loc}" key="locale.common.login" var="login" />
<fmt:message bundle="${loc}" key="locale.tooltip.length.clarification" var="tip_lenght_clar"/>

<fmt:message bundle="${loc}" key="locale.common.edit" var="edit"/>

<fmt:message bundle="${loc}" key="locale.tooltip.engl_ru_pattern" var="eng_ru_pat"/>
<fmt:message bundle="${loc}" key="locale.restore.submit" var="restore"/>
<fmt:message bundle="${loc}" key="locale.common.delete_and_save" var="delete_and_save"/>
<fmt:message bundle="${loc}" key="locale.common.undo_changes" var="undo_changes"/>

<jsp:useBean id="factory_info" class="by.epam.like_it.model.vo.validation_vo.factory.MultipleValidationInfoFactory">
  <jsp:setProperty name="factory_info" property="beanName" value="User"/>
  <jsp:setProperty name="factory_info" property="fields" value="${fn:split('firstName,lastName,aboutMe', ',')}"/>
</jsp:useBean>

<c:set var="infos" value="${factory_info.info}"/>

<jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible">
  <jsp:setProperty name="msg_resp" property="lang" value="${sessionScope.language}"/>
  <jsp:setProperty name="msg_resp" property="info" value="${infos.firstName}"/>
</jsp:useBean>

<jsp:useBean id="entity_bean_loaded" class="by.epam.like_it.model.bean.User" scope="request"/>

<form id="clear_all" action="<c:url value="/personal/edit_profile"/>" method="post">
  <input type="hidden" name="command" value="edit_profile-auth">
  <input type="hidden" name="first_name" value="">
  <input type="hidden" name="last_name" value="">
  <input type="hidden" name="about_me" value="">
</form>
<form action="<c:url value="/personal/edit_profile"/>"
      id="edit_profile_f" name="new_post" method="post" class="s-form">
  <input type="hidden" name="command" value="edit_profile-auth">
  <h1 class="title">${edit_head}</h1>
  <div class="s-field">
    <p><label for="f_name">${f_name}</label></p>
    <p>${f_name_desr}</p>
    <input id="f_name" type="text" name="first_name" tabindex="1"
           value="${entity_bean_loaded.firstName}"
           pattern="${infos.firstName.pattern}" maxlength=${infos.firstName.max}>
    <div class="tooltip">
      <div class="icon_info"> </div>
      <div class="tooltip_text">
        <p><c:out value="${msg_resp.restrictionMsg}"/></p>
        <p>${eng_ru_pat}</p>
        <p><c:out value="${tip_lenght_clar}"/></p>
      </div>
    </div>
    <div class="s-error"></div>
  </div>

  <jsp:setProperty name="msg_resp" property="info" value="${infos.lastName}"/>
  <div class="s-field">
    <p><label for="l_name">${l_name}</label></p>
    <p>${l_name_desr}</p>
    <input id="l_name" type="text" name="last_name" tabindex="2"
           value="${entity_bean_loaded.lastName}"
           pattern="${infos.lastName.pattern}" maxlength=${infos.lastName.max}>
    <div class="tooltip">
      <div class="icon_info"></div>
      <div class="tooltip_text">
        <p><c:out value="${msg_resp.restrictionMsg}"/></p>
        <p>${eng_ru_pat}</p>
        <p><c:out value="${tip_lenght_clar}"/></p>
      </div>
    </div>

    <div class="s-error"></div>
  </div>

  <jsp:setProperty name="msg_resp" property="info" value="${infos.aboutMe}"/>
  <div class="s-field">
    <p><label for="p_full">${aboutMe}</label></p>
    <p>${aboutMe_descr}</p>
    <textarea id="p_full" rows="10" tabindex="3" class="answerText" name="about_me"
              maxlength="${infos.aboutMe.max}"
              ><c:out value="${entity_bean_loaded.aboutMe}" escapeXml="true"/></textarea>
    <div class="tooltip">
      <div class="icon_info"> </div>
      <div class="tooltip_text">
        <p><c:out value="${msg_resp.restrictionMsg}"/></p>
        <p><c:out value="${tip_lenght_clar}"/></p>
      </div>
    </div>
    <div class="s-error"> </div>
  </div>
  <div class="s-buttons">
    <button tabindex="4" class="submit_btn" type="submit">${edit}</button>
  </div>

  <div class="linksContainer">
    <div class="links">
      <button type="reset" tabindex="5">${undo_changes}</button>
    </div>
    <div class="links">
      <button form="clear_all" type="submit" tabindex="5">${delete_and_save}</button>
    </div>
 </div>

</form>



