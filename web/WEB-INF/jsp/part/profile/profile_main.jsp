<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.profile.f_name" var="f_name" />
<fmt:message bundle="${loc}" key="locale.profile.l_name" var="l_name" />
<fmt:message bundle="${loc}" key="locale.profile.aboutMe" var="aboutMe" />
<fmt:message bundle="${loc}" key="locale.profile.reg_date" var="reg_date" />
<fmt:message bundle="${loc}" key="locale.profile.sibscribings" var="subs" />

<fmt:message bundle="${loc}" key="locale.profile.not_fulfilled" var="no_info" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />

<div class="f_name">
  <p>${f_name}</p>
  <p><c:out value="${requestScope.userVO.user.firstName}" default="${no_info}" escapeXml="true"/> </p>
</div>
<div class="l_name">
  <p>${l_name}</p>
  <p><c:out value="${requestScope.userVO.user.lastName}" default="${no_info}" escapeXml="true"/></p>
</div>
<div>
  <p>${aboutMe}</p>
  <p><c:out value="${requestScope.userVO.user.aboutMe}" default="${no_info}" escapeXml="true"/></p>
</div>
<div class="reg_date">
  <p>${reg_date}</p>
  <p><c:out value="${requestScope.userVO.user.createdDate}" default="${no_info}" escapeXml="true"/></p>
</div>
<div>
  <p>${subs}</p>
  <div>
    <ul>
      <c:forEach var="favTag" items="${requestScope.userVO.favoriteTags}">
        <li class="tokenWrapper">
          <div class="tokenContainer">
            <span data-id="${favTag.id}">${favTag.name}</span>
          </div>
        </li>
      </c:forEach>
    </ul>
  </div>
</div>
