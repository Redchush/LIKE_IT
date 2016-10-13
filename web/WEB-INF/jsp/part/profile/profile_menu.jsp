<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.profile.menu.profile" var="menu_profile" />
<fmt:message bundle="${loc}" key="locale.profile.menu.feed" var="menu_feed" />
<fmt:message bundle="${loc}" key="locale.profile.menu.favorite" var="menu_favorite" />
<fmt:message bundle="${loc}" key="locale.profile.menu.settings" var="settings" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.common.profile" var="profile" />
<fmt:message bundle="${loc}" key="locale.profile.sibscribings" var="subs" />

<div class="arrow_drop">
  <img class="icon"  src="<c:url value="/resources/img/Collapse_Arrow-48.png"/>" >
  <aside class="profile_sidebar column">
    <nav class="profile_menu_box">
      <ul class="profile_menu">
        <li>
          <a href="personal">${menu_profile}</a>
        </li>
        <li>
          <a href="personal/feed">${menu_feed}</a>
        </li>
        <li>
          <a href="personal/favorite">${menu_favorite}</a>
        </li>
        <li class="dropdown"><a>${edit}</a>
          <ul class="profile_menu dropdown_content">
            <li>
              <a href="personal/edit_profile">${profile}</a>
            </li>
            <li>
              <a href="personal/edit_subscribing">${subs}</a>
            </li>
            <li>
              <a href="personal/edit_settings">${settings}</a>
            </li>
          </ul>
        </li>
      </ul>
    </nav>
  </aside>
</div>