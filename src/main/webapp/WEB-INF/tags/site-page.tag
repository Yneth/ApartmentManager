<%@ tag description="Apartment order page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base-page>
    <jsp:attribute name="head">
        <title>OrderApartment</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/css/main.css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </jsp:attribute>

    <jsp:attribute name="header">
        <t:navbar/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <div class="copyright">Copyright @ Anton 2016</div>
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:base-page>
