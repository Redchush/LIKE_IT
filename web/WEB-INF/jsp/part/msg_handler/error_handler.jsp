<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--This page for include.
The locale  and bundle (var loc) attributes must be defined in main page --%>

<c:choose>
  <%-- invalid value --%>
  <c:when test="${requestScope.error_bean.code eq 1}">
    <fmt:message bundle="${requestScope.loc}" key="locale.invalid.unnamed" var="unnamed_invalid"/>
    <fmt:message bundle="${requestScope.loc}" key="locale.invalid.tooltip_ask" var="invalid_tip" />

    <c:set var = "hasMessage" value="false"/>
    <c:forEach var="failedObject" items="${requestScope.error_bean.failedFields}">
      <fmt:message bundle="${requestScope.loc}"
                   key="locale.invalid.${requestScope.error_bean.failedBean}.${failedObject}" var="er_msg"/>
      <c:if test="${not fn:contains(er_msg, '???')}">
        <c:set var="hasMessage" value="true"/>
        <div class="msg_box">
          <p> ${er_msg}.</p>
          <p> ${invalid_tip}</p>
        </div>
      </c:if>
    </c:forEach>
    <c:if test="${not hasMessage}">
      <div class="msg_box">
        <p> ${unnamed_invalid}.</p>
        <p> ${invalid_tip}</p>
      </div>
    </c:if>
  </c:when>

  <%-- error during executing action. Need action variable!!!  --%>
  <c:when test="${requestScope.error_bean.code eq 2}">
    <c:set var = "hasMessage" value="false"/>
    <fmt:message bundle="${requestScope.loc}"
                 key="locale.error.${requestScope.action}.${requestScope.error_bean.failedField}" var="er_msg"/>

    <c:if test="${not fn:contains(er_msg, '???')}">
      <c:set var="hasMessage" value="true"/>
      <div class="msg_box">
        <p> ${er_msg}.</p>
      </div>
    </c:if>
    <c:if test="${not hasMessage}">
      <fmt:message bundle="${requestScope.loc}" key="locale.error.${requestScope.action}.unnamed" var="er_msg"/>
      <c:if test="${not fn:contains(er_msg, '???')}">
        <c:set var="hasMessage" value="true"/>
        <div class="msg_box">
          <p> ${er_msg}.</p>
        </div>
      </c:if>
    </c:if>
    <c:if test="${not hasMessage}">
      <fmt:message bundle="${requestScope.loc}" key="locale.error.unnamed" var="unnamed_action_error"/>
      <div class="msg_box">
        <p> ${unnamed_action_error}.</p>
      </div>
    </c:if>
  </c:when>
</c:choose>