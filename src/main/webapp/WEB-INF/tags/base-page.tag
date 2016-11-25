<%@ tag description="base page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<html>
<head>
    <jsp:invoke fragment="head"/>
</head>
<body>
    <header>
        <jsp:invoke fragment="header"/>
    </header>

    <main>
        <jsp:doBody/>
    </main>
    <footer>
        <jsp:invoke fragment="footer"/>
    </footer>
    </body>
</html>
