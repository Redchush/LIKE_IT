<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<c:set var="language"
       value="${not empty param.language ? param.language : not empty sessionScope.language ?
       sessionScope.language : pageContext.request.locale}" scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" scope="request" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />

<fmt:message bundle="${loc}" key="locale.common.signUp" var="sign_up" />
<fmt:message bundle="${loc}" key="locale.common.or" var="_or" />
<fmt:message bundle="${loc}" key="locale.common.logIn" var="log_in" />

<fmt:message bundle="${loc}" key="locale.post.sort.mark" var="mark" />
<fmt:message bundle="${loc}" key="locale.post.sort.comments" var="comments_sort"/>
<fmt:message bundle="${loc}" key="locale.post.sort.creation" var="creation_sort"/>
<fmt:message bundle="${loc}" key="locale.post.sort.update" var="renovation_sort"/>

<fmt:message bundle="${loc}" key="locale.post.btn.comment" var="comment_msg"/>
<fmt:message bundle="${loc}" key="locale.post.btn.answer" var="answer_msg"/>

<fmt:message bundle="${loc}" key="locale.post.sidebar.title" var="side_title" />
<fmt:message bundle="${loc}" key="locale.post.answer-attr.all" var="all" />
<fmt:message bundle="${loc}" key="locale.post.answer-attr.avg" var="avg" />
<fmt:message bundle="${loc}" key="locale.post.answer-attr.count_rate" var="count_rate" />

<fmt:message bundle="${loc}" key="locale.post.comment_opt.unavailiable" var="no_comments" />
<fmt:message bundle="${loc}" key="locale.post.answer_opt.unavailiable" var="no_answer_opt" />


<fmt:message bundle="${loc}" key="locale.post.no_answers_msg" var="no_answers_msg" />
<fmt:message bundle="${loc}" key="locale.post.no_marks" var="no_marks" />

<fmt:message bundle="${loc}" key="locale.common.you" var="you" />

<fmt:message bundle="${loc}" key="locale.tooltip.length.clarification" var="tip_lenght_clar"/>


<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
  <script type="text/javascript" src="<c:url value="/resources/js/main.js"/>" > </script>
  <script type="text/javascript" src="<c:url value="/resources/js/post.js"/>" > </script>
</head>
<body>
<c:set var="isAuth" value="${sessionScope.user_id gt 0}" scope="request"/>

<c:choose>
  <c:when test="${isAuth}">
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

<fmt:setBundle basename="resource.db.written.validation" var="valid" />
<c:set var="defaultFotoPath" value="/resources/user_foto/Circled_User_Male-50.png"/>

<%--<c:set var="postVO" value="${requestScope.postVO}" scope="page"/>--%>
<jsp:useBean id="postVO" class="by.epam.like_it.model.vo.db_vo.PostVO" scope="request">
</jsp:useBean>

<c:set var="post_owner_id" value = "${requestScope.postVO.author.id}" scope="page"/>
<c:set var="current_post_id" value="${requestScope.postVO.post.id}" scope="page"/>


<c:set var="current_user_id" value = "${sessionScope.user_id}" scope="page"/>
<c:set var="isPostOwner" value="${post_owner_id eq current_user_id}" scope="page"/>

<jsp:useBean id="comment_tip" class="by.epam.like_it.model.vo.page_vo.msg_responsible.FormatResponsible" scope="page"/>
<jsp:useBean id="answer_tip" class="by.epam.like_it.model.vo.page_vo.msg_responsible.FormatResponsible" scope="page"/>
<fmt:message bundle="${valid}" key="Comment.content.max" var="comment_max" />
<fmt:message bundle="${valid}" key="Answer.content.max" var="answer_max" />

<c:if test="${isAuth}">
  <fmt:message bundle="${valid}" key="Comment.content.min" var="comment_min" />
  <fmt:message bundle="${valid}" key="Answer.content.min" var="answer_min" />

  <fmt:parseNumber type="number" integerOnly="true" var="comment_min" value="${comment_min}"/>
  <fmt:parseNumber type="number" integerOnly="true" var="comment_max" value="${comment_max}"/>
  <fmt:parseNumber type="number" integerOnly="true" var="answer_min" value="${answer_min}"/>
  <fmt:parseNumber type="number" integerOnly="true" var="answer_max" value="${answer_max}"/>

  <fmt:message bundle="${loc}" key="locale.tooltip.length_pattern" var="pat"/>
  <fmt:message bundle="${loc}" key="locale.tooltip.entity.answer" var="an"/>
  <fmt:message bundle="${loc}" key="locale.tooltip.entity.comment" var="com"/>


  <c:set target="${comment_tip}" property="pattern" value="${pat}"/>
  <c:set target="${comment_tip}" property="strings" value="${com};${comment_min};${comment_max}"/>

  <c:set target="${answer_tip}" property="pattern" value="${pat}"/>
  <c:set target="${answer_tip}" property="strings" value="${an};${answer_min};${answer_max}"/>
</c:if>

<c:if test="${not empty requestScope.msg_bean}">
  <jsp:include page="/WEB-INF/jsp/part/msg_handler/msg_handler.jsp"/>
</c:if>
<c:set var="current_url" value="post?id=${param.id}"/>

<section class="content-wrapper post_page">
  <div class="center">
    <div class="subheader">
      <div class="subheader_content">
        <div class="leftContainer">
          <div class="post_title subUp"><h1 class="user_text">${requestScope.postVO.post.title}</h1>
          </div>
          <div class="post_tags subDown">
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
          </div>

          <div class="clearfix"></div>
        </div>

        <div class="rightContainer">
          <div class="subUp username">
            <a href="user?id=${postVO.author.id}" class="usernameLink">
              <c:choose>
                <c:when test="${isPostOwner}">
                  <c:set var="realLogin" value="${you}"/>
                </c:when>
                <c:otherwise>
                  <c:set var="realLogin" value="${requestScope.postVO.author.login}"/>
                </c:otherwise>
              </c:choose>
              <img class="icon profile-big rounded" alt="${postVO.author.login}"
                   src="<c:url value='${postVO.author.fotoPath}'/>"/><span>${realLogin}</span>
            </a>
          </div>

          <div class="subDown">
            <img class="icon icon-micro" src="<c:url value="/resources/img/Date%20To-52.png"/>" >
            <time class="created_date" datetime="${postVO.post.createdDate}">${postVO.post.createdDate}</time>
          </div>
        </div>
      </div>

    </div>
    <div class="middle-wrapper">
      <div class="middle">
        <main class="column_main column">
          <div class="post_box">
            <div class="left_attr">

              <div class="common_btn">
                <c:choose>
                  <c:when test="${sessionScope.role eq 'client'}">
                      <c:choose>
                        <c:when test="${not empty postVO.currentUserInfo && not empty postVO.currentUserInfo.favoriteUserPost}">
                          <form class="addFavorite" action="${current_url}" method="post">
                          <input type="hidden" name="command" value="delete_favorite-client">
                          <input type="hidden" name="action" value="delete">
                          <input type="hidden" name="entity_id" value="${postVO.currentUserInfo.favoriteUserPost.id}">
                          <button type="submit" class="icon_btn icon_star_marked"></button>
                        </c:when>
                        <c:otherwise><form class="addFavorite" action="${current_url}" method="post">
                          <input type="hidden" name="command" value="create_favorite-client">
                          <input type="hidden" name="action" value="create">
                          <input type="hidden" name="parent_id" value="${current_post_id}">
                          <button type="submit" class="icon_btn icon_star_unmarked"></button></c:otherwise>
                      </c:choose>
                    </form>
                  </c:when>
                  <c:otherwise><div class="icon_btn icon_star_unmarked"></div></c:otherwise>
                </c:choose>
                <div class="info">
                  <span class="counter"> ${postVO.info.favoriteCount} </span>
                </div>
              </div>
              <c:if test="${sessionScope.role eq 'admin' or isPostOwner}">
                <div class="admin_opt">
                  <form action="${current_url}" method="post">
                    <input type="hidden" name="command" value="ban_post-responsible">
                    <input type="hidden" name="owner_id" value="${post_owner_id}">
                    <button type="submit" name="entity_id" value="${current_post_id}" class="icon">
                      <img class="icon" src="<c:url value="/resources/img/Delete_Message-64.png"/>" >
                    </button>
                  </form>
                </div>
              </c:if>
              <c:if test="${sessionScope.id eq post_owner_id}">
                <div class="own_user_opt">
                  <a href="personal/edit_post?id=${current_post_id}">
                    <img class="icon" src="<c:url value="/resources/img/Edit-50_padded.png"/>" >
                  </a>
                </div>
              </c:if>
            </div>

            <div class="question_body_box">
              <div class="question_body user_text">
                <p><c:out value="${postVO.post.content}" escapeXml="true"/> </p>
              </div>
              <ul class="post_attrs">
                <li class="question_attrs_item">
                  <img class="icon icon-micro"  src="<c:url value="/resources/img/Visible-48.png"/>" >
                  <span class="views-count"> ${postVO.info.readedCount} </span>
                </li>
                <li class="question_attrs_item">
                  <img class="icon icon-micro" src="<c:url value="/resources/img/Available%20Updates-52.png"/>" >
                  <time class="created_date" datetime=${postVO.post.createdDate}> ${postVO.post.createdDate} </time>
                </li>
              </ul>
              <div id="answerForm_btn" class="btn newAnswer-btn">
                <span>${answer_msg}</span>
              </div>
              <c:choose>
                <c:when test="${!(sessionScope.role eq 'anonymous')}">
                  <form class="answerForm" id="answerForm" action="${current_url}"  method="post">
                    <div class="formicon icon_delete"></div>
                    <input type="hidden" name="command" value="create_answer-auth">
                    <input type="hidden" name="parent_id" value="${current_post_id}">

                    <div class="s-field">
                      <div class="textarea_container">
                        <div class="button_bar">
                          <ul class="button_row">
                            <li class="button_item">
                              <div class="tooltip">
                                <div class="icon_info"> </div>
                                <div class="tooltip_text">
                                  <p><c:out value="${answer_tip.result}"/></p>
                                  <p><c:out value="${tip_lenght_clar}"/></p>
                                </div>
                              </div>
                            </li>
                          </ul>
                        </div>
                        <textarea required rows ="15" name="content" class="answerText"
                                  maxlength="<c:out value="${answer_max}"/>"  title="answer content"></textarea>
                      </div>
                        <%--@todo--%>
                      <div class="s-error">Enter the valid login<div class="s-error_OK">OK</div></div>
                    </div>
                    <button type="submit" class="btn answer-btn">OK</button>
                  </form>
                </c:when>
                <c:otherwise>
                  <div class="answerForm msg">
                    <div  class="formicon icon_delete"></div>
                    <p class="attention">${no_answer_opt}</p>
                    <a class="action_link" href="sign_up">${sign_up}</a>
                    <c:out value="${_or}"/>
                    <a class="action_link" href="log_in">${log_in}</a>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
          <c:choose>
            <c:when test="${empty requestScope.current_answers or fn:length(requestScope.current_answers) eq 0}">
              <div class="answer_box">
                <div class="answer_body_box">${no_answers_msg}</div>
              </div>
            </c:when>
            <c:otherwise>

              <c:forEach var="answerVO" items="${requestScope.current_answers}">
                <c:set var="answer_owner" value="${answerVO.author.id}"/>
                <c:set var="answer_id" value="${answerVO.answer.id}" />
                <c:set var="isAnswerOwner" value="${answer_owner eq current_user_id}"/>
                <c:choose>
                  <c:when test="${isAnswerOwner}">
                    <c:set var="realLogin_answer" value="${you}"/>
                  </c:when>
                  <c:otherwise>
                    <c:set var="realLogin_answer" value="${answerVO.author.login}"/>
                  </c:otherwise>
                </c:choose>
                <div class="answer_box">
                  <div class="top_attr">
                    <c:choose>
                      <c:when test="${empty answerVO.countRatings or answerVO.countRatings eq 0}">
                        <div>
                          <p>${no_marks}</p>
                        </div>
                      </c:when>
                      <c:otherwise>
                        <div>
                          <p>${answerVO.totalRate}</p>
                          <p>${all}</p>
                        </div>
                        <div>
                          <p>${answerVO.avgRate}</p>
                          <p>${avg}</p>
                        </div>
                        <div>
                          <p>${answerVO.countRatings}</p>
                          <p>${count_rate}</p>
                        </div>
                      </c:otherwise>
                    </c:choose>
                    <div class="clearfix"></div>
                  </div>

                  <div class="left_attr">
                    <div class="common_btn">
                      <a href="user?id=${answerVO.author.id}">
                        <img class="icon profile-medium rounded" alt="${answerVO.author.login}"
                             src="<c:url value='${answerVO.author.fotoPath}'/>"/>
                          <%--src=${answer.author.fotoPath}--%>
                      </a>
                    </div>

                    <c:if test="${sessionScope.role eq 'client'}">
                      <c:set var="hasRating" value="${not empty answerVO.currentUserInfo.rating}"/>

                      <div class="user_opt">
                        <div class="dropdown">
                          <img src="<c:url value="/resources/img/Like%20It-50.png"/>" class="icon icon_mark">
                          <form class="mark_up dropdown-content" action="${current_url}" method="post">
                            <input type="submit" name="rating" value="1">
                            <input type="submit" name="rating" value="2">
                            <input type="submit" name="rating" value="3">
                            <input type="submit" name="rating"  value="4">
                            <input type="submit" name="rating" value="5">
                              <%--     don't complexChangeMap place -> css based on order--%>
                            <input type="hidden" name="command" value="toggle_rating-client">
                            <input type="hidden" name="has_previous" value="${hasRating}">
                            <input type="hidden" name="parent_id" value="${answerVO.answer.id}">
                          </form>
                        </div>
                        <c:if test="${hasRating and answerVO.currentUserInfo.rating.banned != true}">
                          <div class="you_mark dropdown">
                            <form class="mark" action="${current_url}" method="post">
                              <input type="hidden" name="command" value="ban_rating-responsible">
                              <input type="hidden" name="entity_id" value="${answerVO.currentUserInfo.rating.id}">
                              <button type="button" name="owner_id" value="${answerVO.currentUserInfo.rating.rating}">
                                  ${answerVO.currentUserInfo.rating.rating}</button>
                              <button type="submit" class="dropdown-content">&times;</button>
                            </form>
                          </div>
                        </c:if>
                        <div class="dropdown">
                          <img src="<c:url value="/resources/img/Like%20It-50.png"/>" class="icon icon_mark rotated">
                          <form class="mark_down dropdown-content" method="post" action="${current_url}">
                            <input type="submit" name="rating" value="-1">
                            <input type="submit" name="rating" value="-2">
                            <input type="submit" name="rating" value="-3">
                            <input type="submit" name="rating" value="-4">
                            <input type="submit" name="rating" value="-5">
                              <%--     don't complexChangeMap place -> css based on order--%>
                            <input type="hidden" name="command" value="toggle_rating-client">
                            <input type="hidden" name="has_previous" value="${hasRating}">
                            <input type="hidden" name="parent_id" value="${answer_id}">
                          </form>
                        </div>
                      </div>
                    </c:if>

                    <c:if test="${sessionScope.role eq 'admin' or isAnswerOwner}">
                      <div class="admin_opt">
                        <form method="post" action="${current_url}">
                          <input type="hidden" name="command" value="ban_answer-responsible">
                          <input type="hidden" name="owner_id" value="${answerVO.author.id}">
                          <input type="hidden" name="entity_id" value="${answer_id}">
                          <button type="submit" class="icon">
                            <img class="icon"  src="<c:url value="/resources/img/Delete_Message-64.png"/>" >
                          </button>
                        </form>
                      </div>
                    </c:if>

                    <c:if test="${isAnswerOwner}">
                      <div class="own_user_opt">
                        <a href="personal/edit_answer?id=${answer_id}">
                          <img class="icon" src="<c:url value="/resources/img/Edit-50_padded.png"/>">
                        </a>
                      </div>
                    </c:if>

                  </div>
                  <div class="answer_body_box">
                    <a href="user?id=${answer_owner}" class="usernameLink">${realLogin_answer}</a>
                    <div class="answer_body user_text">
                      <p><c:out value="${answerVO.answer.content}" escapeXml="true"/> </p>
                    </div>
                    <ul class="post_attrs">
                      <li class="question_attrs_item">

                        <img class="icon icon-micro" src="<c:url value="/resources/img/Date%20To-52.png"/>">
                        <time class="created_date" datetime="${answerVO.answer.createdDate}">${answerVO.answer.createdDate}</time>
                      </li>

                      <li class="question_attrs_item">
                        <img class="icon icon-micro" src="<c:url value="/resources/img/Available%20Updates-52.png"/>">
                        <time class="created_date"  datetime=" ${answerVO.answer.updatedDate}">${answerVO.answer.updatedDate}</time>
                      </li>
                    </ul>
                    <div class="btn newComment-btn">
                      <span>${comment_msg}</span>
                    </div>

                    <c:choose>
                      <c:when test="${sessionScope.user_id gt 0}">
                        <form class="commentForm" action="${current_url}"  method="post">
                          <input type="hidden" name="command" value="create_comment-auth">
                          <input type="hidden" name="parent_id" value="${answer_id}">
                          <div class="formicon icon_delete"></div>
                          <div class="s-field">
                            <div class="textarea_container">
                              <div class="button_bar">
                                <ul class="button_row">
                                  <li class="button_item">
                                    <div class="tooltip">
                                      <div class="icon_info"> </div>
                                      <div class="tooltip_text">
                                        <p><c:out value="${comment_tip.result}"/></p>
                                        <p><c:out value="${tip_lenght_clar}"/></p>
                                      </div>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                              <textarea required rows=10 name="content" maxlength="<c:out value="${comment_max}"/>"
                                        class="commentText" title="comment form">
                              </textarea>
                              <p class="tip_msg"></p>
                            </div>
                              <%--@todo--%>
                            <div class="s-error">Enter the valid login<div class="s-error_OK">OK</div></div>
                          </div>
                          <button type="submit" class="btn answer-btn">OK</button>
                        </form>
                      </c:when>
                      <c:otherwise>
                        <div class="commentForm msg">
                          <div  class="formicon icon_delete"></div>
                          <p class="attention">${no_comments}</p>
                          <a class="action_link" href="sign_up">${sign_up}</a>
                            ${_or}
                          <a class="action_link" href="log_in">${log_in}</a>
                        </div>
                      </c:otherwise>
                    </c:choose>

                    <c:forEach var="commentVO" items="${answerVO.comments}">
                      <c:set var="comment_id" value = "${commentVO.comment.id}"/>
                      <c:set var="comment_owner" value="${commentVO.author.id}" scope="page"/>
                      <c:set var="isCommentOwner" value="${comment_owner eq current_user_id}" scope="page"/>
                      <c:choose>
                        <c:when test="${isCommentOwner}">
                          <c:set var="realLogin_comment" value="${you}"/>
                        </c:when>
                        <c:otherwise>
                          <c:set var="realLogin_comment" value="${commentVO.author.login}"/>
                        </c:otherwise>
                      </c:choose>

                      <div class="comment_body">
                        <span class="comment_text">${commentVO.comment.content}</span>
                        <div class="comment_attrs">
                          <a class="comment_author"
                             href="user?id=${comment_owner}">${realLogin_comment}</a><span>,</span>
                          <span class="comment_date">
                         <em>
                        <time class="created_date" datetime="${commentVO.comment.createdDate}">${commentVO.comment.createdDate}</time>
                        </em>
                     </span>
                          <c:if test="${isCommentOwner}">
                            <a href="personal/edit_comment?id=${comment_id}">
                              <img class="icon icon-micro" src="<c:url value="/resources/img/Edit-50_padded.png"/>">
                            </a>
                          </c:if>
                          <c:if test="${sessionScope.role eq 'admin' or isCommentOwner}">
                            <form action="${current_url}" method="post">
                              <input type="hidden" name="command" value="ban_comment-responsible">
                              <input type="hidden" name ="entity_id" value="${comment_id}">
                              <input type="hidden" name="owner_id" value="${comment_owner}">
                              <button type="submit">
                                <img class="icon icon-micro" src="<c:url value="/resources/img/Delete_Message-64.png"/>">
                              </button>
                            </form>
                          </c:if>
                        </div>
                      </div>
                    </c:forEach>
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
          <div class="pagination_wrapper">
            <ul class="pagination">
              <c:if test="${not empty requestScope.first_dot}">
                <li><a href="FrontController?command=view_posts_list&current_page=${requestScope.current_page - 1}">«</a></li>
                <li><a href="FrontController?command=view_posts_list&current_page=1">1</a></li>
                <li><span class="dots">...</span></li>
              </c:if>

              <c:forEach var="pageNumber" items="${requestScope.accessible_pages}">
                <c:choose>
                  <c:when test="${pageNumber eq requestScope.current_page}">
                    <li><a href="#" class="active">${pageNumber}</a></li>
                  </c:when>
                  <c:otherwise>
                    <li>
                      <a href="FrontController?command=view_posts_list&current_page=${pageNumber}">${pageNumber}</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>

              <c:if test="${not empty requestScope.last_dot}">
                <li><span class="dots">...</span></li>
              </c:if>
              <c:if test="${not empty requestScope.last_page}">
                <li><a href="FrontController?command=view_posts_list&current_page=${requestScope.last_page}">${requestScope.last_page}</a></li>
                <li><a href="FrontController?command=view_posts_list&current_page=${requestScope.current_page + 1}">»</a></li>
              </c:if>
            </ul>
          </div>
        </main>
        <aside class="column_sidebar column">
          <div class="relatedPosts">
            <h2>${side_title}</h2>
            <ul class="similarPost user_text">
              <c:forEach var="rel" items="${requestScope.related}">
                <li class="similarPost_item">
                  <a href="post?id=${rel.id}">${rel.title}</a>
                </li>
              </c:forEach>
            </ul>
          </div>
        </aside>
        <div class="clearfix"></div>
      </div>
    </div>
  </div>
</section>
<footer class="footer"></footer>
</body>
</html>