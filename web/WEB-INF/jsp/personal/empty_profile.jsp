<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.profile.greeting" var="greeting" />
<fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.profile.load_tip" var="load_tip" />
<fmt:message bundle="${loc}" key="locale.profile.load_state.1" var="load_state1" />
<fmt:message bundle="${loc}" key="locale.profile.load_state.2" var="load_state2" />
<fmt:message bundle="${loc}" key="locale.profile.load" var="load" />
<fmt:message bundle="${loc}" key="locale.profile.delete" var="delete" />

<fmt:message bundle="${loc}" key="locale.profile.rate" var="rate" />
<fmt:message bundle="${loc}" key="locale.profile.avg_mark" var="avg_mark" />
<fmt:message bundle="${loc}" key="locale.profile.answers" var="answers" />
<fmt:message bundle="${loc}" key="locale.profile.questions" var="questions" />

<fmt:message bundle="${loc}" key="locale.profile.menu.profile" var="menu_profile" />
<fmt:message bundle="${loc}" key="locale.profile.menu.feed" var="menu_feed" />
<fmt:message bundle="${loc}" key="locale.profile.menu.favorite" var="menu_favorite" />
<fmt:message bundle="${loc}" key="locale.profile.menu.settings" var="settings" />

<fmt:message bundle="${loc}" key="locale.profile.f_name" var="f_name" />
<fmt:message bundle="${loc}" key="locale.profile.l_name" var="l_name" />
<fmt:message bundle="${loc}" key="locale.profile.aboutMe" var="aboutMe" />
<fmt:message bundle="${loc}" key="locale.profile.reg_date" var="reg_date" />
<fmt:message bundle="${loc}" key="locale.profile.sibscribings" var="subs" />

<fmt:message bundle="${loc}" key="locale.profile.not_fulfilled" var="no_info" />



<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
  <script type="text/javascript" src="<c:url value="/resources/js/profile.js"/>" > </script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/part/header/header_user.jsp"/>
<section class="content-wrapper profile_page">
  <div class="center">
    <div class="subheader">
      <div class="subheader_box">
        <h1 class="profile_greeting">${greeting} ${sessionScope.login}</h1>
        <div class="profile_foto">
          <img src="<c:url value="/resources/user_foto/Circled_User_Male-50.png"/>">
          <div class="edit_drop own_user_opt">
            <span class="description action_link">${edit}</span>
            <div class="edit_drop_content">
              <p class="description">${load_tip}</p>
              <form id="edit_foto" method="get" action="FrontController" >
                <div>
                  <label for=user_foto class="load_label">
                    <div class="icon_load"></div>
                    <input id="user_foto" type="file" name="user_foto" accept="image/jpeg,image/png" class="inputfile">
                    <p class="description">${load_state1}<span></span>${load_state2}</p>
                  </label>
                </div>
                <button type="submit" class="btn load_btn" name="command" value="edit_foto">${load}</button>
                <button type="submit" class="btn load_btn" name="command" value="delete_foto">${delete} </button>
              </form>
            </div>
          </div>
        </div>
        <div class="top_attr">
          <ul>
            <li>
              <p>0</p>
              <p>${rate}</p>
            </li>
            <li>
              <p>0</p>
              <p>${avg_mark} </p>
            </li>
            <li>
              <p>0</p>
              <p>${answers}</p>
            </li>
            <li>
              <p>0</p>
              <p>${questions}</p>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="middle">
      <div class="arrow_drop">
        <img class="icon" src="<c:url value="/resources/img/Collapse_Arrow-48.png"/>" >
        <aside class="profile_sidebar column">
          <nav class="profile_menu_box">
            <ul class="profile_menu">
              <li>
                <a href="FrontController?command=menu_profile">${menu_profile}</a>
              </li>
              <li>
                <a href="FrontController?command=menu_feed">${menu_feed}</a>
              </li>
              <li>
                <a href="FrontController?command=menu_favorite">${menu_favorite}</a>
              </li>
              <li>
                <a href="FrontController?command=menu_settings">${settings}</a>
              </li>
            </ul>
          </nav>
        </aside>
      </div>

      <main class="profile_main column">
        <div class="f_name">
          <p>${f_name}</p>
          <p>${no_info}</p>
        </div>
        <div class="l_name">
          <p>${l_name}</p>
          <p>${no_info}</p>
        </div>
        <div class="">
          <p>${aboutMe}</p>
          <p>${no_info}</p>
        </div>
        <div class="reg_date">
          <p>${reg_date}</p>
          <p>${no_info}</p>
        </div>
        <div>
          <p>${subs}</p>
          <p>${no_info}</p>
        </div>
        <div class="profile_bottom">
          <a class="action_link">${edit}</a>
        </div>
      </main>
      <div class="clearfix"></div>
    </div>
  </div>
</section>
<footer class="footer"></footer>
</body>