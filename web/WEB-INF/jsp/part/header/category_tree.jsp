<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${param.language}"/>
<fmt:setBundle basename="resource.frontend.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.header.search_in.all" var="in_all" />



<c:set var="current_form" value="${param.for_form}"/>
<script src="<c:url value="/resources/js/category_tree.js"/>" > </script>
<style>
  .cat_item_list input[name=cat_level]{
    display: none;
  }
</style>
<div class="cat_box cat_item drop"><output name="chosenCat">${in_all}</output>
  <ul class="cat_item_list level1 drop_content">
    <input type=radio name = "cat" form="${current_form}" value="all" checked>
    <label>${in_all}</label>
    <c:forEach var="catLevel1" items="${sessionScope.categories}">       <%--level1--%>
      <c:choose>
        <c:when test="${catLevel1.hasChildren}">
          <li class="cat_item drop">
        </c:when>
        <c:otherwise>
          <li class="cat_item">
        </c:otherwise>
      </c:choose>

      <input type=radio name ="cat" form="${current_form}"  value="${catLevel1.id}" title="category">
      <label title="${catLevel1.title}">${catLevel1.title}</label>
      <input type="radio" name="cat_level" form="${current_form}" value="1" title="category">

      <c:if test="${catLevel1.hasChildren}">
        <div class="formicon level_icon icon_down"></div>
        <ul class="cat_item_list level2 drop_content">

          <c:forEach var="catLevel2" items="${catLevel1.subCategories}"> <%--level2--%>
            <c:choose>
              <c:when test="${catLevel1.hasChildren}">
                <li class="cat_item drop">
              </c:when>
              <c:otherwise>
                <li class="cat_item">
              </c:otherwise>
            </c:choose>
            <input type=radio name="cat" form="${current_form}"  value="${catLevel2.id}">
            <label>${catLevel2.title}</label>
            <input type="radio" name="cat_level" form="${current_form}"  value="2">

            <c:if test="${catLevel2.hasChildren}">
              <div class="formicon level_icon icon_down"></div>
              <ul class="cat_item_list level2 drop_content">

                <c:forEach var="catLevel3" items="${catLevel2.subCategories}"> <%--level3--%>
                  <c:choose>
                    <c:when test="${catLevel1.hasChildren}">
                      <li class="cat_item drop">
                    </c:when>
                    <c:otherwise>
                      <li class="cat_item">
                    </c:otherwise>
                  </c:choose>
                  <input type=radio name = "cat" form="${current_form}"  value="${catLevel3.id}" title="">
                  <label>${catLevel3.title}</label>
                  <input type="radio" name="cat_level" form="${current_form}"  value="3" title="">
                  <c:if test="${catLevel3.hasChildren}">
                    <div class="formicon level_icon icon_down"></div>
                    <ul class="cat_item_list level2 drop_content">

                      <c:forEach var="catLevel4" items="${catLevel3.subCategories}"> <%--level3--%>
                        <c:choose>
                          <c:when test="${catLevel1.hasChildren}">
                            <li class="cat_item">
                          </c:when>
                          <c:otherwise>
                            <li class="cat_item drop">
                          </c:otherwise>
                        </c:choose>
                        <input type=radio name = "cat" form="${current_form}"  value="${catLevel4.id}">
                        <label>${catLevel4.title}</label>
                        <input type="radio" name="cat_level" form="${current_form}"  value="4">
                        </li>
                      </c:forEach>
                    </ul>
                  </c:if>
                  </li>
                </c:forEach>

              </ul>
            </c:if>

            </li>
          </c:forEach>
        </ul>
      </c:if>

      </li>
    </c:forEach>

  </ul>
</div>


