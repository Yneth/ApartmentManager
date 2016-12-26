<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="index.hello" bundle="${bundle}"/>, ${sessionScope.user.login}</h1>
            <p>
                <fmt:message key="index.admin.hello.message" bundle="${bundle}"/>.
            </p>
            <p>
                <a class="btn btn-primary" href="/admin/orders" role="button">
                    <fmt:message key="index.admin.view.orders" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/admin/requests" role="button">
                    <fmt:message key="index.admin.view.requests" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/admin/apartments" role="button">
                    <fmt:message key="index.admin.view.apartments" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/admin/apartment/new" role="button">
                    <fmt:message key="index.admin.create.apartment" bundle="${bundle}"/> »
                </a>
            </p>
        </div>
    </div>
</t:admin-page>
