<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:setBundle basename="resource.db.written.validation" var="valid" />

<jsp:useBean id="answer_tip" class="by.epam.like_it.model.vo.page_vo.msg_responsible.FormatResponsible" scope="page"/>

<fmt:message bundle="${valid}" key="Answer.content.min" var="answer_min" />
<fmt:message bundle="${valid}" key="Answer.content.max" var="answer_max" />

<fmt:parseNumber type="number" integerOnly="true" var="answer_min" value="${answer_min}"/>
<fmt:parseNumber type="number" integerOnly="true" var="answer_max" value="${answer_max}"/>

<fmt:message bundle="${loc}" key="locale.tooltip.length_pattern" var="pat"/>
<fmt:message bundle="${loc}" key="locale.tooltip.entity.answer" var="an"/>

<c:set target="${answer_tip}" property="pattern" value="${pat}"/>
<c:set target="${answer_tip}" property="strings" value="${an};${answer_min};${answer_max}"/>

<fmt:message bundle="${loc}" key="locale.forms.full_descr" var="full_desr" />
<fmt:message bundle="${loc}" key="locale.forms.full_descr.tooltip" var="full_desr_tip" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />

<fmt:message bundle="${loc}" key="locale.forms.title.edit_answer" var="edit_title" />
<fmt:message bundle="${loc}" key="locale.tooltip.length.clarification" var="tip_lenght_clar"/>


<jsp:useBean id="factory_info" class="by.epam.like_it.model.vo.validation_vo.factory.SingleValidationInfoFactory">
  <jsp:setProperty name="factory_info" property="beanName" value="Answer"/>
  <jsp:setProperty name="factory_info" property="field" value="content"/>
</jsp:useBean>
<c:set var="infos" value="${factory_info.info}"/>

<jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible"/>
<jsp:setProperty name="msg_resp" property="lang" value="${sessionScope.language}"/>
<jsp:setProperty name="msg_resp" property="info" value="${infos}"/>
<jsp:setProperty name="msg_resp" property="what" value="comment"/>

<form id="restore_form" method="post" class="s-form restore_form">
  <input type="hidden" name="command" value="edit_answer-owner">
  <input type="hidden" name="entity_id" value="${requestScope.entity_bean.id}">
  <input type="hidden" name="owner_id" value="${requestScope.entity_bean.userId}">
  <h1 class="title">${edit_title}</h1>
  <div class="s-field">
    <p><label for="p_full">${full_desr}</label></p>
    <textarea id="p_full" rows="15" maxlength="${infos.max}" type="text" tabindex="2" name="content"
              required class="answerText"><c:out value="${requestScope.entity_bean_loaded.content}"/></textarea>
    <div class="tooltip">
      <div class="icon_info"> </div>
      <div class="tooltip_text">
        <p><c:out value="${msg_resp.restrictionMsg}"/></p>
        <p><c:out value="${tip_lenght_clar}"/></p>
      </div>
    </div>
    <div class="s-error">${msg_resp.invalidMsg}</div>
  </div>
  <div class="s-buttons">
    <button tabindex="3" class="submit_btn disabled=" type="submit">${edit}</button>
  </div>
</form>