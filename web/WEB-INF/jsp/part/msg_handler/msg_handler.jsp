<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.invalid.tooltip_ask" var="tooltip_ask"/>


<div class="msg_box">
  <div class="msg_box_center">
    <c:choose>
      <c:when test="${not empty requestScope.msg_bean and requestScope.msg_bean.msgKeys != null}">
        <c:forEach var="key" items="${requestScope.msg_bean.msgKeys}">
          <fmt:message bundle="${loc}" key="${key}" var="msg"/>
          <p> ${msg}.</p>
        </c:forEach>
        <c:if test="${not empty param.withTip}">
          <p> ${tooltip_ask}.</p>
        </c:if>
      </c:when>
      <c:otherwise>
        <p></p>
        <p></p>
      </c:otherwise>
    </c:choose>
  </div>
</div>