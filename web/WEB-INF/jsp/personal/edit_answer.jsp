<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.locale}" scope="request"/>

<c:choose>
  <c:when test="${pageContext.request.method eq 'POST' and empty requestScope.isSend}">
    <c:set var="isSend" value="true" scope="request"/>

    <jsp:useBean id="current_timestamp" class="by.epam.like_it.common_util.TimeUtil" scope="page"/>
    <c:set var="timestamp_update" property="currentTimestamp" target="current_timestamp"> </c:set>

    <fmt:parseNumber var="ent_id" scope="page" value="${param.entity_id}" type="number"/>
    <fmt:parseNumber var="user_id" scope="page" value="${param.owner_id}" type="number"/>
    <jsp:useBean id="entity_bean_loaded" class="by.epam.like_it.model.bean.Answer" scope="request">
      <jsp:setProperty name="entity_bean_loaded" property="id" value="${ent_id}"/>
      <jsp:setProperty name="entity_bean_loaded" property="userId" value="${user_id}"/>
      <jsp:setProperty name="entity_bean_loaded" property="content" value="${param.content}"/>
      <jsp:setProperty name="entity_bean_loaded" property="updatedDate" value="${timestamp_update}"/>
    </jsp:useBean>
    <jsp:forward page="/FrontController">
      <jsp:param name="name" value="value"/>
    </jsp:forward>
  </c:when>

  <c:otherwise>
    <c:if test="${empty requestScope.entity_bean}">
      <fmt:parseNumber var="answer_id" type="number" value="${param.id}"/>
      <jsp:useBean id="entity_bean" class="by.epam.like_it.model.bean.Answer" scope="request">
        <jsp:setProperty name="entity_bean" property="id" value="${answer_id}"/>
        <jsp:setProperty name="entity_bean" property="userId" value="${sessionScope.user_id}"/>
      </jsp:useBean>
    </c:if>
    <jsp:forward page="/WEB-INF/jsp/part/form/form_carcass.jsp">
      <jsp:param name="to_include" value="edit_answer_block.jsp"/>
      <jsp:param name="to_forward" value="load_entity-auth"/>
    </jsp:forward>
  </c:otherwise>

</c:choose>