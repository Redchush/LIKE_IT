<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resource.frontend.locale" var="loc" />

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.title" var="title" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<fmt:message bundle="${loc}" key="locale.profile.sibscribings" var="subs" />

<fmt:message bundle="${loc}" key="locale.forms.title.edit_subs" var="edit_head"/>

<fmt:message bundle="${loc}" key="locale.tags.sort_name" var="sort_name" />
<fmt:message bundle="${loc}" key="locale.tags.sort_quantity" var="sort_quantity" />
<fmt:message bundle="${loc}" key="locale.tags.sort_popularity" var="sort_popularity" />

<fmt:message bundle="${loc}" key="locale.tags.sort_description" var="tag_chose_descr" />

<fmt:message bundle="${loc}" key="locale.common.more" var="more" />
<fmt:message bundle="${loc}" key="locale.common.submit" var="submit"/>
<fmt:message bundle="${loc}" key="locale.common.f_letter" var="f_letter" />
<fmt:message bundle="${loc}" key="locale.common.tags" var="tags" />

<fmt:message bundle="${loc}" key="locale.tags.sort.first_letter" var="first_letter" />
<fmt:message bundle="${loc}" key="locale.common.letters_all" var="any_letter" />
<fmt:message bundle="${loc}" key="locale.tags.possible" var="possible_header" />
<fmt:message bundle="${loc}" key="locale.common.found" var="found" />

<%--<c:set var="alphabet" value="${fn:split('A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z', ',')}" scope="application" />--%>
<c:set var="alphabet" value='A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z'/>

<jsp:useBean id="entity_bean_loaded" scope="request" class="by.epam.like_it.model.to.impl.TagHistoryTransfer"/>

<script type="text/javascript" src="<c:url value="/resources/js/lib/jquery-3.0.0.min.js"/>" > </script>
<script src="<c:url value="/resources/js/main.js"/>" > </script>
<script src="<c:url value="/resources/js/util.js"/>" > </script>

<c:set var="order" value="${not empty param.sort_tag ? param.sort_tag : 'popularity'}"/>
<script type="text/javascript">
  $(document).ready(function() {
    var value = '${order}';
    UTIL.changeSortBtn(value, "sortTagForm");
  });
</script>

<c:url value="/personal/edit_subscribing" var="responsible"/>

<form id="edit_sub" method="post" action="${responsible}" class="s-form">
  <input type="hidden" name="command" value="edit_subscribing-client" />
  <input type="hidden" name="prev_ids" value="${requestScope.prev_ids}">
  <div class="title">${edit_head}</div>

  <div class="s-field">
    <p><label>${tags}</label></p>
    <p>${tag_chose_descr}</p>

    <div class="chosenTags">
      <ul data-initial>
        <c:forEach var="user_tag" items="${entity_bean_loaded.oldChosen}">
          <li class="tokenWrapper" data-state="chosen" data-place="${user_tag.info.countPostTag}" data-initial="true">
            <div class="token-counter">-${user_tag.info.countPostTag}</div>
            <div class="tokenContainer">
              <span>${user_tag.tag.name}</span>
              <span class="remove"><em></em></span>
            </div>
            <input type="checkbox" name="tag_old" value="${user_tag.tag.id}" title="tag" class="to_wrap" checked>
          </li>
        </c:forEach>
      </ul>
      <ul data-temporal>
        <c:forEach var="user_tag" items="${entity_bean_loaded.newChosen}">
          <li class="tokenWrapper" data-state="chosen" data-place="${user_tag.info.countPostTag}" >
            <div class="token-counter">-${user_tag.info.countPostTag}</div>
            <div class="tokenContainer">
              <span>${user_tag.tag.name}</span>
              <span class="remove"><em></em></span>
            </div>
            <input type="checkbox" name="tag_new" value="${user_tag.tag.id}" title="tag" class="to_wrap" checked></li>
        </c:forEach>
      </ul>
    </div>
  </div>
</form>
<div class="tags">
  <div class="title">
    <h2>${possible_header}</h2>
    <p class="description">${found} <c:out value="${entity_bean_loaded.totalRemain}"/> </p>
  </div>

  <jsp:useBean id="beanPair" class="by.epam.like_it.model.vo.page_vo.BeanPair"/>
  <jsp:setProperty name="beanPair" property="key" value="sort_tag"/>
  <jsp:setProperty name="beanPair" property="value" value="name"/>

  <jsp:useBean id="changer"  class="by.epam.like_it.model.vo.system_vo.prev_query.PrevQueryChanger">
    <jsp:setProperty name="changer" property="prevQuery" value="${sessionScope.prevQuery}"/>
    <jsp:setProperty name="changer" property="simpleChange" value="${beanPair}"/>
  </jsp:useBean>
  <jsp:setProperty name="beanPair" property="value" value="popularity"/>
  <jsp:setProperty name="changer" property="simpleChange" value="${beanPair}"/>

  <form id="sortTagForm" action="${responsible}" method="get" class="saveChosen">
    <div id="tabs" class="tabs">
      <input type="hidden" name="letter" value="<c:out value="${param.letter}"/>"/>
      <input type="hidden" name="direction" value="<c:out value="${requestScope.direction}" default="down"/>"/>
      <input type="hidden" name="prev_ids" value="${requestScope.prev_ids}">
      <button type="submit" name="sort_tag" class="sort-btn" value="name">
        <span>${sort_name}</span>
        <span class="icon"></span>
      </button>
      <button type="submit" name="sort_tag" class="sort-btn" value="popularity">
        <span>${sort_popularity}</span>
        <span class="icon"></span>
      </button>
      <script type="text/javascript">var value = '${order}';UTIL.changeSortBtn(value, "sortTag");</script>
    </div>
  </form>

  <form action="${responsible}" method="get" class="saveChosen">
    <input type="hidden" name="prev_ids" value="${requestScope.prev_ids}">
    <div class="first_letters_wrapper">
      <h4>${first_letter}</h4>
      <div class="first_letters">
        <c:forTokens items="${alphabet}" delims="," var="letter">
          <%--<a  href="<c:url value="/personal/edit_subscribing?letter=${letter}"/>">${letter}</a>--%>
          <button type="submit" name="letter" value="${letter}">${letter}</button>
        </c:forTokens>
      </div>
      <button type="submit" class="btn load_btn saveChosen"><span>${any_letter}</span></button>
    </div>
  </form>
  <div class="possibleTags">
    <ul>
      <c:forEach var="user_tag" items="${entity_bean_loaded.remain}">
        <li class="tokenWrapper" data-state="possible" data-place="${user_tag.info.countPostTag}">
          <div class="token-counter">-${user_tag.info.countPostTag}</div>
          <div class="tokenContainer">
            <span>${user_tag.tag.name}</span>
            <span class="remove"><em></em></span>
          </div>
          <input type="checkbox" name="tag_new" value="${user_tag.tag.id}" title="tag" class="to_wrap">
        </li>
      </c:forEach>
    </ul>
    <div class="more_tags_wrapper">
      <c:set var="hasMore" value="${entity_bean_loaded.totalRemain gt fn:length(entity_bean_loaded.remain)}"/>
      <c:if test="${hasMore}">
        <c:choose>
          <c:when test="${empty param.tag_lim}">
            <c:set var="current_lim" value="20"/>
          </c:when>
          <c:otherwise>
            <fmt:parseNumber integerOnly="true" value="${param.tag_lim}" var="current_lim"/>
          </c:otherwise>
        </c:choose>
        <button form="sortTagForm" type="submit" name="tag_lim"
                value="<c:out value="${current_lim + 20}" default="40"/>" class="sort-btn">
        <span>${more}</span>
        </button>
        <%--<a href="FrontController?command=more_tags_for_user_edit" class="sort-btn saveChosen"></a>--%>
      </c:if>
    </div>
  </div>
</div>

<div class="s-buttons">
  <button form="edit_sub" class="submit_btn disabled=" type="submit">${submit}</button>
</div>
