<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.index.no_result" var="no_results" />
<fmt:message bundle="${loc}" key="locale.index.post_box.answers" var="post_box_answers" />


<ul class="content_list">
  <c:if test="${not empty requestScope.posts or fn:length(requestScope.posts) eq 0}">
    <c:set var="listSize" value="${fn:length(requestScope.posts)}"/>
    <c:if test="${listSize eq 0}">
      <li class="content-list_item"><h3>${no_results}</h3></li>
    </c:if>
    <c:forEach var="postVO" items="${requestScope.posts}" varStatus="postStatus">
      <li class="content-list_item">
        <div class="question_box">
          <div class="question_content">
            <nav class="question_tags">
              <ul>
                <c:forEach var="post_tag" items="${postVO.tags}">
                  <li class="tokenWrapper">
                    <div class="tokenContainer">
                      <span>${post_tag.name}</span>
                    </div>
                  </li>
                </c:forEach>
              </ul>
            </nav>
            <h3 class="question_title">
              <a class="question_title-link"
                 href="post?id=${postVO.post.id}">${postVO.post.title}
              </a>
            </h3>
          </div>
          <c:set var="info" value="${postVO.info}"/>
          <div class="question_info">
            <div class="question_info_box">
              <div class="info">
                <span class="counter">${info.answersCount}</span>
              </div>
              <div class="answers info">${post_box_answers}</div>
            </div>
          </div>
          <ul class="question_attrs">
            <li class="question_attrs_item">
              <img class="icon icon-micro" src="<c:url value="/resources/img/StarFilled-48.png"/>">
              <span class="followers_count">${info.favoriteCount}</span>
            </li>
            <li class="question_attrs_item">
              <img class="icon icon-micro" src="<c:url value="/resources/img/Visible-48.png"/>">
              <span class="views-count">${info.readedCount}</span>
            </li>
            <li class="question_attrs_item">
              <img class="icon icon-micro" src="<c:url value="/resources/img/Available%20Updates-52.png"/>">
              <time datetime="${postVO.post.createdDate}">
                  ${postVO.post.createdDate}</time>
            </li>

            <li class="question_attrs_item">
              <img class="icon icon-micro" src="<c:url value="/resources/img/Date%20To-52.png"/>">
              <time datetime="${postVO.post.updatedDate}">${postVO.post.createdDate}</time>
            </li>
          </ul>
          <%--<div class="clearfix"></div>--%>
        </div>
      </li>
    </c:forEach>
  </c:if>
</ul>
