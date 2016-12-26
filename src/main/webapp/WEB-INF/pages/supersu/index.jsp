<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:supersu-page>
    <div class="container">
        <div class="jumbotron">
            <h1>Hi, ${sessionScope.user.login}.</h1>
            <p><fmt:message key="index.supersu.hello.message" bundle="${bundle}"/>.</p>
            <p>
                <a class="btn btn-primary" href="/supersu/admin/new" role="button">
                    <fmt:message key="index.supersu.create.admin" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/supersu/admins" role="button">
                    <fmt:message key="index.supersu.view.admins" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/supersu/orders" role="button">
                    <fmt:message key="index.supersu.edit.orders" bundle="${bundle}"/> »
                </a>
            </p>
            <p>
                <a class="btn btn-primary" href="/supersu/requests" role="button">
                    <fmt:message key="index.admin.view.requests" bundle="${bundle}"/> »
                </a>
            </p>
        </div>
    </div>
</t:supersu-page>
