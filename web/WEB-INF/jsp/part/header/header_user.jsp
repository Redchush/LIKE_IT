<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${param.language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.title" var="title" />
<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.index.login" var="login" />

<fmt:message bundle="${loc}" key="locale.header.search" var="search" />
<fmt:message bundle="${loc}" key="locale.header.log_in" var="log_in" />
<fmt:message bundle="${loc}" key="locale.header.sign_up" var="sign_up" />
<fmt:message bundle="${loc}" key="locale.header.log_out" var="log_out" />

<fmt:message bundle="${loc}" key="locale.header.advanced_search" var="advanced_search" />
<fmt:message bundle="${loc}" key="locale.header.all_tags" var="all_tags" />
<fmt:message bundle="${loc}" key="locale.header.all_users" var="all_users" />
<fmt:message bundle="${loc}" key="locale.header.all_categories" var="all_categories" />

<fmt:message bundle="${loc}" key="locale.header.search_by" var="search_by" />
<fmt:message bundle="${loc}" key="locale.header.search_by.title" var="by_title" />
<fmt:message bundle="${loc}" key="locale.header.search_by.tag" var="by_tag" />
<fmt:message bundle="${loc}" key="locale.header.search_by.content" var="by_content" />
<fmt:message bundle="${loc}" key="locale.header.search_in" var="search_in" />
<fmt:message bundle="${loc}" key="locale.header.search_in.all" var="in_all" />

<fmt:message bundle="${loc}" key="locale.header.feed" var="feed" />



<header class="header">
    <div class="headerContainer">
      <div class="col1">
        <div class="nav_menu dropdown">
          <div class="icon_btn menu_icon_white"></div>
          <nav class="dropdown-content site_nav">
            <ul>
                <%--<li class="site_nav_item">--%>
                  <%--<a href="FrontController?command=advanced_search">${advanced_search}</a>--%>
                <%--</li>--%>
                <%--<li class="site_nav_item">--%>
                  <%--<a href="FrontController?command=view_all_tags">${all_tags}</a>--%>
                <%--</li>--%>
                <%--<li class="site_nav_item">--%>
                  <%--<a href="FrontController?command=view_all_users">${all_users}</a>--%>
                <%--</li>--%>
                <%--<li class="site_nav_item">--%>
                  <%--<a href="FrontController?command=view_all_categories">${all_categories}</a>--%>
                <%--</li>--%>
              </ul>
             </nav>
        </div>
        <div class="logo"><a href="<c:url value="/index"/>">LikeIT</a></div>
      </div>
      <div class="col3">
        <ul class="rightHeader">

          <li class="dropdown content">
            <div class="smallScreen btn iconed_btn leftRounded">
              <div class="icon_btn search_icon"></div>
            </div>
            <div class="allScreenForm dropdown-content wide">
              <form class="searchForm" id="search_form" name="search" action="<c:url value="/FrontController"/>"  method="GET">
                <input type="hidden" name="command" value="search_posts_by_constriction">
                <input type="text" class="field" name="text" placeholder=${search} autocomplete="off">
                <div class="seachicon"></div>
              </form>
            </div>
          </li>

          <li class="content dropdown">
            <div class="smallScreen btn iconed_btn content rightRounded">
              <div class="icon_btn login_icon"></div>
            </div>

            <div class="mediumScreen icon btn bothRounded">
              <div class="icon_btn login_icon"> </div>
            </div>

            <div class="allScreenButtons dropdown-content wide">
              <ul class="rightHeader">
                <li class="content dropdown">
                  <div class="btn btn-log leftRounded">
                    <a href="<c:url value="/FrontController?command=log_out"/>">${log_out}</a>
                  </div>
                </li>
                <li class="content">
                  <div class="btn btn-log profile ">
                    <a href="<c:url value="/personal"/>">
                      <img alt="${sessionScope.login}" class="icon profile rounded"
                           src="<c:url value='${sessionScope.photoPath}'/>"/>
                      <span>${sessionScope.login}</span>
                    </a>
                  </div>
                </li>
                <li class="content dropdown post">
                  <a href="<c:url value="/personal/new_post"/>" class="btn iconed_btn content rightRounded">
                    <div class="icon_btn newpost_icon"></div>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="clearfix"></li>
        </ul>

      </div>
      <div class="clearfix"></div>
    </div>
  </header>

  <div class="header_extention">
    <div class="toggle_nav_btn close"></div>
    <ul class="nav_options">
      <li class="header_ext_item">
        <%--<a href="<c:url value="/personal/feed"/>" class="opt">${feed}</a>--%>
      </li>

      <li class="header_ext_item">
        <label for="searchType_select">${search_by}</label>
        <select id="searchType_select" size="1" name="by" form="search_form">
          <option value="title" selected>${by_title}</option>
          <option value="tag">${by_tag}</option>
          <option value="content">${by_content}</option>
        </select>
      </li>
      <%--category deleted--%>
      <%--<li class="header_ext_item">--%>
        <%--<label>${search_in}</label>--%>
        <%--<jsp:include page="category_tree.jsp">--%>
          <%--<jsp:param name="for_form" value="search_form"/>--%>
        <%--</jsp:include>--%>
      <%--</li>--%>

      <li class="clearfix"></li>
    </ul>
  </div>