<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.newPost.head" var="head" />

<fmt:message bundle="${loc}" key="locale.error.logination.unnamed" var="notExist" />
<fmt:message bundle="${loc}" key="locale.newPost.tag" var="p_tags" />
<fmt:message bundle="${loc}" key="locale.newPost.tag_descr" var="tag_descr" />
<fmt:message bundle="${loc}" key="locale.newPost.tag_length_tooltip" var="tag_tip" />

<fmt:message bundle="${loc}" key="locale.newPost.title" var="p_title" />
<fmt:message bundle="${loc}" key="locale.newPost.title_descr" var="title_descr" />
<fmt:message bundle="${loc}" key="locale.newPost.title_tooltip" var="title_tip" />

<fmt:message bundle="${loc}" key="locale.newPost.content" var="p_content"/>
<fmt:message bundle="${loc}" key="locale.newPost.content_descr" var="p_content_descr" />
<fmt:message bundle="${loc}" key="locale.newPost.enter_here" var="enter_here" />

<fmt:message bundle="${loc}" key="locale.newPost.submit" var="submit" />
<fmt:message bundle="${loc}" key="locale.tooltip.length.clarification" var="tip_lenght_clar"/>

<jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible"/>
<jsp:setProperty name="msg_resp" property="lang" value="${sessionScope.language}"/>

<jsp:useBean id="factory_info_post" class="by.epam.like_it.model.vo.validation_vo.factory.MultipleValidationInfoFactory">
  <jsp:setProperty name="factory_info_post" property="beanName" value="Post"/>
  <jsp:setProperty name="factory_info_post" property="fields" value="${fn:split('title,content', ',')}"/>
</jsp:useBean>
<jsp:useBean id="factory_info_tag" class="by.epam.like_it.model.vo.validation_vo.factory.SingleValidationInfoFactory">
  <jsp:setProperty name="factory_info_tag" property="beanName" value="Tag"/>
  <jsp:setProperty name="factory_info_tag" property="field" value="name"/>
</jsp:useBean>

<c:set var="post_info" value="${factory_info_post.info}"/>
<c:set var="tag_info" value="${factory_info_tag.info}"/>
<c:set var="tag_max" value="${tag_info.max}"/>

<jsp:useBean id="entity_bean_loaded" class="by.epam.like_it.model.vo.db_vo.PostVO" scope="request"/>

<script src="<c:url value="/resources/js/util.js"/>" > </script>
<script src="<c:url value="/resources/js/commonForm.js"/>" > </script>
<script type="text/javascript" src="<c:url value="/resources/js/tagsInForm.js"/>" > </script>

<div class="outer authForm">
  <c:if test="${not empty requestScope.msg_bean && requestScope.msg_bean != null}">
    <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp"/>
    <%--<c:set var="order" value="${not empty requestScope.sort_tag ? param.sort_tag : 'popularity'}"/>--%>
    <%--<script type="text/javascript">--%>
      <%--$(document).ready(function() {--%>
        <%--var value = '${order}';--%>
        <%--UTIL.showErrorField(value, "new_post_form");--%>
      <%--});--%>
    <%--</script>--%>
  </c:if>
  <div class="inner">
    <div class="top_box">
      <a href="${sessionScope.prevQuery.backQuery}" class="formicon icon_delete"></a>
    </div>
    <form id="new_post_form" name="new_post" method="post" class="s-form newPost_page"
          action="<c:url value="/personal/new_post"/>" >
      <input type="hidden" name="command" value="create_post-auth" />
      <h1 class="title">${head}</h1>
      <div class="s-field">
        <p><label for="post_tag">${p_tags}</label></p>
        <p>${tag_descr}</p>
        <ul class="tags_input">
          <li class="tags_input_item">
            <input id="post_tag" type="text" maxlength="${tag_max}" class="to_wrap" name="tag" tabindex="2">
          </li>
          <li class="tags_input_item">
            <input type="text" maxlength="${tag_max}" name="tag" class="to_wrap" tabindex="3" title="second tag"
                   pattern="${tag_info.pattern}">
          </li>
          <li class="tags_input_item">
            <input type="text" maxlength="${tag_max}" name="tag" class="to_wrap" tabindex="4" title="third tag"
                   pattern="${tag_info.pattern}" >
          </li>
          <li class="tags_input_item">
            <input type="text" maxlength="${tag_max}" name="tag" class="to_wrap" tabindex="5" title="forth tag"
                   pattern="${tag_info.pattern}" >
          </li>
          <li class="tags_input_item">
            <input type="text" maxlength="${tag_max}" name="tag" class="to_wrap" tabindex="6" title="fifth tag"
                   pattern="${tag_info.pattern}" >
          </li>
        </ul>
        <jsp:setProperty name="msg_resp" property="info" value="${tag_info}"/>
        <div class="tooltip">
          <div class="icon_info"></div>
          <div class="tooltip_text">
            <p><c:out value="${msg_resp.restrictionMsg}"/></p>
            <p><c:out value="${tip_lenght_clar}"/></p>
          </div>
        </div>
        <div class="s-error">${msg_resp.invalidMsg}</div>
      </div>

      <jsp:setProperty name="msg_resp" property="info" value="${post_info.title}"/>
      <div class="s-field">
        <p><label for="p_title">${p_title}</label></p>
        <p>${title_descr}</p>
        <input id="p_title" type="text" name="title" required data-min="${post_info.title.min}"
               maxlength="${post_info.title.max}" value="<c:out value="${entity_bean_loaded.post.title}"/>">
        <div class="tooltip">
          <div class="icon_info"> </div>
          <div class="tooltip_text">
            <p><c:out value="${msg_resp.restrictionMsg}"/></p>
            <p><c:out value="${tip_lenght_clar}"/></p>
          </div>
        </div>
        <div class="s-error">${msg_resp.invalidMsg}</div>
      </div>
      <jsp:setProperty name="msg_resp" property="info" value="${post_info.content}"/>
      <div class="s-field">
        <p><label for="p_full">${p_content}</label></p>
        <p>${p_content_descr}</p>
        <textarea id="p_full" type="text" name="content" required class="answerText" rows="20"
                  data-min="${post_info.title.min}"
                  placeholder="${enter_here}" maxlength="${post_info.content.max}"><c:out
         value="${entity_bean_loaded.post.content}"/></textarea>
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
        <button tabindex="3" class="submit_btn" type="submit">${submit}</button>
      </div>
    </form>
  </div>
</div>
