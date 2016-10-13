<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.logIn.enter" var="edit_head"/>
<fmt:message bundle="${loc}" key="locale.logIn.email" var="email" />
<fmt:message bundle="${loc}" key="locale.logIn.email.notValid" var="eNotValid" />
<fmt:message bundle="${loc}" key="locale.logIn.password" var="password" />
<fmt:message bundle="${loc}" key="locale.logIn.password.notValid" var="pNotValid" />
<fmt:message bundle="${loc}" key="locale.logIn.link.signUp" var="signUp" />
<fmt:message bundle="${loc}" key="locale.logIn.link.restore" var="restore" />
<fmt:message bundle="${loc}" key="locale.error.logination.unnamed" var="notExist" />
<fmt:message bundle="${loc}" key="locale.logIn.submit" var="submit" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<fmt:message bundle="${loc}" key="locale.newPost.tag" var="p_tags" />
<fmt:message bundle="${loc}" key="locale.newPost.tag_descr" var="tag_descr" />
<fmt:message bundle="${loc}" key="locale.newPost.tag_length_tooltip" var="tag_tip" />

<fmt:message bundle="${loc}" key="locale.newPost.title" var="p_title" />
<fmt:message bundle="${loc}" key="locale.newPost.title_descr" var="title_descr" />
<fmt:message bundle="${loc}" key="locale.newPost.title_tooltip" var="title_tip" />

<fmt:message bundle="${loc}" key="locale.newPost.content" var="p_full_desc" />
<fmt:message bundle="${loc}" key="locale.newPost.enter_here" var="enter_here" />


<c:set var="alphabet" value="${fn:split('A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z', ',')}" scope="application" />

<jsp:useBean id="comment_tip" class="by.epam.like_it.model.vo.page_vo.msg_responsible.FormatResponsible" scope="page"/>


<jsp:useBean id="factory_info" class="by.epam.like_it.model.vo.validation_vo.factory.MultipleValidationInfoFactory">
  <jsp:setProperty name="factory_info" property="beanName" value="User"/>
  <jsp:setProperty name="factory_info" property="fields" value="${['firstName', 'lastName', 'aboutMe']}"/>
</jsp:useBean>
<c:set var="infos" value="${factory_info.info}"/>

<jsp:useBean id="msg_resp" class="by.epam.like_it.model.vo.validation_vo.ValidationMsgResponsible"/>
<jsp:setProperty name="msg_resp" property="lang" value="${sessionScope.language}"/>
<jsp:setProperty name="msg_resp" property="info" value="${infos.firstName}"/>

<form id="new_post_form" name="new_post" method="get" class="s-form" >
  <input type="hidden" name="command" value="new_post" />
  <div class="title">${edit_head}</div>
  <div class="s-field">
    <div>
      <p>${p_tags}</p>
      <p>${tag_descr}</p>
    </div>
    <!--          <input id="p_tags" type="hidden" tabindex="1" name="tags" begin="" required style="display:none">-->
    <ul class="tags_input">
      <li class="tags_input_item">
        <input type="text" maxlength="50" class="to_wrap" name="tag" tabindex="2" required>
      </li>
      <li class="tags_input_item">
        <input type="text" maxlength="50" name="tag" class="to_wrap" tabindex="3">
      </li>
      <li class="tags_input_item">
        <input type="text" maxlength="50" name="tag" class="to_wrap" tabindex="4">
      </li>
      <li class="tags_input_item">
        <input type="text" maxlength="50" name="tag" class="to_wrap" tabindex="5">
      </li>
      <li class="tags_input_item">
        <input type="text" maxlength="50" name="tag" class="to_wrap" tabindex="6">
      </li>
    </ul>
    <div class="tooltip">
      <div class="icon_info"> </div>
      <div class="tooltip_text">
        <p>${tag_tip}</p>
      </div>
    </div>
    <div class="s-error"></div>
  </div>

  <div class="s-field">
    <div>
      <p><label for="p_title">${p_title}</label></p>
      <p>${title_descr}</p>
    </div>
    <input id="p_title" type="text" name="title" required maxlength=256>

    <div class="tooltip">
      <div class="icon_info"> </div>
      <span class="tooltip_text">
              <p>${title_tip}</p>
            </span>
    </div>
    <div class="s-error">${title}</div>
  </div>
  <div class="s-field">
    <label for="p_full">
      <p>${p_full_desc}</p>
    </label>
    <textarea id="p_full" type="text" tabindex="2" name="full" required placeholder=${enter_here} class="answerText">
          </textarea>
  </div>
  <div class="s-buttons">
    <button tabindex="3" class="submit_btn disabled=" type="submit">${submit}</button>
  </div>
</form>