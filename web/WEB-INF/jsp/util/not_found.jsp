
<%@ page contentType="text/html;charset=UTF-8" language="java"
         isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />
<fmt:message bundle="${loc}" key="locale.security.not_found" var="not_found" />

<html>
<head>
  <title>${title}</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
</head>
<body>
<c:choose>
  <c:when test="${not empty sessionScope.role && sessionScope.role != 'anonymous'}">
    <jsp:include page="/WEB-INF/jsp/part/header/header_user.jsp">
      <jsp:param name="language" value="${language}"/>
    </jsp:include>
  </c:when>

  <c:otherwise>
    <jsp:include page="/WEB-INF/jsp/part/header/header_anonimus.jsp">
      <jsp:param name="language" value="${language}"/>
    </jsp:include>
  </c:otherwise>
</c:choose>

<section class="content-wrapper">
      <div class="center">
        <div class="subheader">
          <div class="title">
            <h1> ${not_found}</h1>
          </div>
        </div>

        <main class="middle-full">
          <div class="content-list_item column">
            <c:choose>
              <c:when test="${not empty requestScope.msg_bean}">
                <c:forEach var="key" items="${requestScope.msg_bean.msgKeys}">
                  <fmt:message bundle="${loc}" key="${key}" var="notFoundMsg" />
                  <c:out value="${notFoundMsg}"/>
                </c:forEach>
              </c:when>
              <c:otherwise>
              </c:otherwise>
            </c:choose>
          </div>
        </main>
      </div>
    </section>
</body>
</html>
