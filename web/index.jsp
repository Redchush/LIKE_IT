<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<body>
    <jsp:forward page="/FrontController">
        <jsp:param name="command" value="load_main"/>
        <jsp:param name="tag_lim_per_page" value="30"/>
        <jsp:param name="post_lim_per_page" value="10"/>
    </jsp:forward>
</body>
</html>
