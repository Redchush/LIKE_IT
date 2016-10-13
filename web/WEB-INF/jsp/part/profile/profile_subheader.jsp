<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.profile.greeting" var="greeting" />

<fmt:message bundle="${loc}" key="locale.profile.rate" var="rate" />
<fmt:message bundle="${loc}" key="locale.profile.avg_mark" var="avg_mark" />
<fmt:message bundle="${loc}" key="locale.profile.answers" var="answers" />
<fmt:message bundle="${loc}" key="locale.profile.questions" var="questions" />
<fmt:message bundle="${loc}" key="locale.profile.not_fulfilled" var="no_info" />

<c:set var="currentFotoPath" value="${requestScope.userVO.user.fotoPath}"/>
<c:if test="${empty requestScope.userVO}">
  <c:set var="currentFotoPath" value="${sessionScope.photoPath}"/>
</c:if>

<div class="subheader">
  <div class="subheader_box">
    <h1 class="profile_greeting"><c:if test="${requestScope.isOwner}">${greeting}</c:if>
      ${requestScope.userVO.user.login}</h1>
    <div class="profile_foto">
      <img src="<c:url value='${currentFotoPath}'/>"
           class="rounded"><c:if test="${requestScope.isOwner}">
        <fmt:message bundle="${loc}" key="locale.common.edit" var="edit" />
        <fmt:message bundle="${loc}" key="locale.profile.load_tip" var="load_tip" />
        <fmt:message bundle="${loc}" key="locale.profile.load_tip_2" var="load_tip_2" />
        <fmt:message bundle="${loc}" key="locale.profile.load_state.1" var="load_state1" />
        <fmt:message bundle="${loc}" key="locale.profile.load_state.2" var="load_state2" />
        <fmt:message bundle="${loc}" key="locale.profile.load" var="load" />
        <fmt:message bundle="${loc}" key="locale.profile.delete" var="delete" />
        <fmt:setBundle basename="resource.db.written.validation" var="validation" />
        <fmt:message bundle="${validation}" key="User.fotoPath.default"
                     var="default_foto"/><div class="edit_drop own_user_opt">
          <span class="action_link">${edit}</span>
          <div class="edit_drop_content">
            <p class="description">${load_tip}</p>
            <p class="description">${load_tip_2}</p>
            <form id="delete_photo" method="post" action="personal">
              <input type="hidden" name="command" value="delete_photo-auth">
            </form>
            <form action="personal" id="edit_photo" method="post" enctype="multipart/form-data">
              <div>
                <div class="load_label">
                  <div class="icon_load"></div>
                  <input id="user_foto" type="file" name="user_foto" accept="image/jpeg,image/png" class="inputfile">
                  <p class="description">${load_state1} <span> </span> ${load_state2}</p>
                </div>
              </div>
             </form>
            <c:if test="${not (default_foto eq requestScope.userVO.user.fotoPath)}">
              <button type="submit" form="delete_photo" class="btn load_btn">${delete} </button>
            </c:if>
            <button type="submit" form="edit_photo" class="btn load_btn">${load}</button>
          </div>
        </div>
      </c:if>
    </div>
    <%--this.answersCount = answersCount;--%>
    <%--this.postsCount = postsCount;--%>
    <%--this.avgRating = avgRating;--%>
    <%--this.totalRating = totalRating;--%>
    <div class="top_attr">
      <ul>
        <li>
          <p><c:out value="${requestScope.userVO.info.totalRating}" default="0"/> </p>
          <p>${rate}</p>
        </li>
        <li>
          <p><c:out value="${requestScope.userVO.info.avgRating}" default="0"/></p>
          <p>${avg_mark} </p>
        </li>
        <li>
          <p><c:out value="${requestScope.userVO.info.answersCount}" default="0"/></p>
          <p>${answers}</p>
        </li>
        <li>
          <p><c:out value="${requestScope.userVO.info.postsCount}" default="0"/></p>
          <p>${questions}</p>
        </li>
      </ul>
    </div>
  </div>
</div>