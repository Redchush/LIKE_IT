<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<c:choose>
  <c:when test="${pageContext.request.method eq 'GET' and empty requestScope.isLoaded}">
    <c:set var="isLoaded" value="true" scope="request"/>
    <jsp:forward page="/FrontController">
      <jsp:param name="command" value="${param.to_forward}"/>
    </jsp:forward>
  </c:when>

  <c:otherwise>
    <!DOCTYPE html>
    <html>
    <head>
      <title>${title}</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
      <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
    </head>
    <body>
    <jsp:include page="/WEB-INF/jsp/part/header/header_user.jsp"/>
    <div class="outer authForm">
      <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp"/>
      <div class="inner">
        <div class="top_box">
          <c:url value="${sessionScope.prevQuery.backQuery}" var="back"/>
          <c:url value="/index" var="back_ind"/>
          <a href="<c:out value="${back}" default="${back_ind}"/> "
             class="formicon icon_delete">
          </a>
        </div>
        <jsp:include page="${param.to_include}"/>
      </div>
    </div>
    </body>

    </html>
  </c:otherwise>
</c:choose>


