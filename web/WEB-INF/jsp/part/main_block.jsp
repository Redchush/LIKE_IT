<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${param.language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.index.all_questions" var="all_questions" />
<fmt:message bundle="${loc}" key="locale.index.sort.creation" var="sort_creation" />
<fmt:message bundle="${loc}" key="locale.index.sort.views" var="sort_views" />
<fmt:message bundle="${loc}" key="locale.index.sort.answers" var="sort_answers" />
<fmt:message bundle="${loc}" key="locale.index.sort.update" var="sort_update" />
<fmt:message bundle="${loc}" key="locale.index.sort.favorite" var="sort_fav" />

<fmt:message bundle="${loc}" key="locale.index.post_box.answers" var="post_box_answers" />
<fmt:message bundle="${loc}" key="locale.tags.header" var="sidebar_tags" />
<fmt:message bundle="${loc}" key="locale.index.sidebar_tooltip-1" var="sidebar_tooltip1" />
<fmt:message bundle="${loc}" key="locale.index.sidebar_tooltip-2" var="sidebar_tooltip2" />
<fmt:message bundle="${loc}" key="locale.index.sidebar_tooltip-3" var="sidebar_tooltip3" />
<fmt:message bundle="${loc}" key="locale.index.subheader.msg" var="results" />
<fmt:message bundle="${loc}" key="locale.index.no_result" var="no_results" />

<c:set var="order" value="${not empty param.sort_value ? param.sort_value : 'views'}"/>
<section class="content-wrapper main_page">
  <div class="center">
    <div class="subheader">
      <div class="title">
        <h1>${all_questions}</h1>
        <p>${requestScope.total_posts} ${results}</p>
      </div>
      <%--action="<c:url value="/FrontController"/>"--%>
      <form id="tabs" class="tabs" method="get" action="<c:url value="/FrontController"/>">
        <input type="hidden" name="command" value="search_posts_by_order">
        <input type="hidden" name="direction" value="${param.direction}">
        <button  type="submit" name="sort_value" value="create" class="sort-btn">
          <span>${sort_creation}</span>
          <span class="icon"></span>
        </button>
        <button  type="submit" name="sort_value" value="update" class="sort-btn">
          <span>${sort_update}</span>
          <span class="icon"></span>
        </button>
        <button  type="submit" name="sort_value" value="answers" class="sort-btn">
          <span>${sort_answers}</span>
          <span class="icon"></span>
        </button>
        <button type="submit" name="sort_value" value="favorite" class="sort-btn">
          <span>${sort_fav}</span>
          <span class="icon"></span>
        </button>
        <button  type="submit" name="sort_value" value="views" class="sort-btn">
          <span>${sort_views}</span>
          <span class="icon"></span>
        </button>
      </form>
    </div>
    <script type="text/javascript">
      var value = '${order}';
      UTIL.changeSortBtn(value, "tabs");
    </script>

    <div class="middle-wrapper">
      <div class="middle">
        <main class="column_main column">
          <jsp:include page="post_view.jsp"/>
        </main>
        <aside class="column_sidebar column">
          <div class="tags">
            <div title="" class="tooltip">
              <div class="icon_info"> </div>
              <div class="tooltip_text">
                <p>${sidebar_tooltip1}</p>
                <p>${sidebar_tooltip2}</p>
                <p>${sidebar_tooltip3}</p>
              </div>
            </div>
            <h2>${sidebar_tags}</h2>
            <form id="tag_filter" action="FrontController" method="get">
              <input type="hidden" name="command" value="search_posts_by_add_constriction">
              <div class="chosenTags">
                <ul data-temporal>
                  <c:if test="${not empty sessionScope.chosen_tags}">
                    <c:forEach var="entry" items="${sessionScope.chosen_tags}">
                      <li class="tokenWrapper" data-state="possible" data-place="${entry.key}">
                        <div class="token-counter"> -${entry.value.info.countPostTag} </div>
                        <div class="tokenContainer">
                          <span data-tag="4"> ${entry.value.tag.name} </span>
                          <span class="remove"><em></em></span>
                        </div>
                        <input form="tag_filter" type="checkbox" name="tag_id" checked value="${entry.value.tag.id}">
                        <input form="tag_filter" type="checkbox" name="index" checked value="${entry.key}">
                      </li>
                    </c:forEach>
                  </c:if>
                </ul>
                <button type="submit" class="btn">ОК</button>
              </div>
              <div class="refColumn possibleTags">
                <ul>
                  <c:if test="${not empty sessionScope.tags}">
                    <c:forEach var="entry" items="${sessionScope.tags}">
                      <li class="tokenWrapper" data-state="possible" data-place="${entry.key}">
                        <div class="token-counter">-${entry.value.info.countPostTag}</div>
                        <div class="tokenContainer">
                          <span data-tag="4">${entry.value.tag.name}</span>
                          <span class="remove"><em></em></span>
                        </div>
                        <input form="tag_filter" type="checkbox" name="tag_id" value="${entry.value.tag.id}" title="tag_filter">
                        <input form="tag_filter" type="checkbox" name="index" value="${entry.key}">
                      </li>
                    </c:forEach>
                  </c:if>
                </ul>
              </div>
            </form>
          </div>
        </aside>
        <div class="clearfix"></div>
      </div>
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
    </div>
  </div>
</section>


<script type="text/javascript">
  $(document).ready(function() {
    var value = '${order}';
    UTIL.changeSortBtn(value, "tabs");
  });
</script>