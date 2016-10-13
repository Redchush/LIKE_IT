<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:setBundle basename="resource.db.written.validation" var="valid" />

<jsp:useBean id="comment_tip" class="by.epam.like_it.model.vo.page_vo.msg_responsible.FormatResponsible" scope="page"/>

<fmt:message bundle="${valid}" key="Comment.content.min" var="comment_min" />
<fmt:message bundle="${valid}" key="Comment.content.max" var="comment_max" />

<fmt:parseNumber type="number" integerOnly="true" var="comment_min" value="${comment_min}"/>
<fmt:parseNumber type="number" integerOnly="true" var="comment_max" value="${comment_max}"/>

<fmt:message bundle="${loc}" key="locale.tooltip.length_pattern" var="pat"/>
<fmt:message bundle="${loc}" key="locale.tooltip.entity.comment" var="com"/>

<c:set target="${comment_tip}" property="pattern" value="${pat}"/>
<c:set target="${comment_tip}" property="strings" value="${com};${comment_min};${comment_max}"/>

<fmt:message bundle="${loc}" key="locale.forms.full_descr" var="full_desr" />
<fmt:message bundle="${loc}" key="locale.forms.full_descr.tooltip" var="full_desr_tip" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.forms.title.edit_comment" var="edit_title" />
<fmt:message bundle="${loc}" key="locale.tooltip.length.clarification" var="tip_lenght_clar"/>

<fmt:message bundle="${loc}" key="locale.common.undo_changes" var="clear"/>

<form action="<c:url value="/personal/edit_comment?id=${param.id}"/>"  method="post" class="s-form restore_form">
  <input type="hidden" name="command" value="edit_comment-owner">
  <input type="hidden" name="entity_id" value="${requestScope.entity_bean_loaded.id}">
  <input type="hidden" name="owner_id" value="${requestScope.entity_bean_loaded.userId}">
  <h1 class="title">${edit_title}</h1>
  <div class="s-field">
    <p><label for="p_full">${full_desr}</label></p>
    <textarea id="p_full" rows="10" maxlength="${comment_max}" type="text" tabindex="2"
              name="content" required
              class="answerText"><c:out value="${requestScope.entity_bean_loaded.content}"/></textarea>
    <div class="tooltip">
      <div class="icon_info"> </div>
      <div class="tooltip_text">
         <p><c:out value="${comment_tip.result}"/></p>
         <p><c:out value="${tip_lenght_clar}"/></p>
      </div>
    </div>
    <div class="s-error"></div>
  </div>
  <div class="s-buttons">
    <button tabindex="3" class="submit_btn" type="submit">${edit}</button>
  </div>

  <div class="linksContainer">
    <div class="links">
      <button type="reset">${clear}</button>
    </div>
    <%--<div class="links">--%>
    <%--<a class="remind" href="restore">${restore}</a>--%>
    <%--</div>--%>
  </div>

</form>