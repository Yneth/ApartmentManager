<%@ tag description="Apartment order page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base-page>
    <jsp:attribute name="head">
        <title><fmt:message key="project.name" bundle="${bundle}"/></title>

        <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css">
        <link rel="stylesheet" href="/static/css/main.css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
        <script src="/static/js/main.js"></script>
    </jsp:attribute>

    <jsp:attribute name="header">
        <t:navbar/>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <div class="container">
            <div class="row">
                <a href="/?lang=en">en</a>&nbsp<a href="/?lang=uk">ua</a>
                <span class="copyright right">Copyright © Anton 2016</span>
            </div>
        </div>
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:base-page>
