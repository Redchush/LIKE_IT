<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="utf-8"%>
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
    <%--<jsp:getProperty name="current_timestamp" property="currentTimestamp"/>--%>

    <fmt:parseNumber var="lang_id" scope="page" value="${param.language}" type="number"/>

    <jsp:useBean id="entity_bean_loaded" class="by.epam.like_it.model.bean.User" scope="request">
      <jsp:setProperty name="entity_bean_loaded" property="id" value="${sessionScope.user_id}"/>
      <jsp:setProperty name="entity_bean_loaded" property="login" value="${param.login}"/>
      <jsp:setProperty name="entity_bean_loaded" property="email" value="${param.email}"/>
      <jsp:setProperty name="entity_bean_loaded" property="password" value="${param.password}"/>
      <jsp:setProperty name="entity_bean_loaded" property="languageId" value="${lang_id}"/>
      <jsp:setProperty name="entity_bean_loaded" property="updatedDate" value="${timestamp_update}"/>
    </jsp:useBean>

    <jsp:forward page="/FrontController"/>
  </c:when>

  <c:otherwise>

    <c:if test="${empty requestScope.entity_bean}">
      <jsp:useBean id="entity_bean" class="by.epam.like_it.model.bean.User" scope="request">
        <jsp:setProperty name="entity_bean" property="id" value="${sessionScope.user_id}"/>
      </jsp:useBean>
    </c:if>

    <jsp:forward page="/WEB-INF/jsp/part/form/form_carcass.jsp">
      <jsp:param name="to_include" value="edit_settings_block.jsp"/>
      <jsp:param name="to_forward" value="load_entity-auth"/>
    </jsp:forward>
  </c:otherwise>

</c:choose>
