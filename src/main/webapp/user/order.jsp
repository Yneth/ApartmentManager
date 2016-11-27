<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:user-page>
    <div class="container">
        <div class="jumbotron">
            <t:order-partial order="${order}"/>
            <form class="form-group" action="/users/orders/delete" method="post">
                <input type="hidden" name="id" value="${order.id}"/>
                <input class="form-control btn btn-danger" type="submit" value="Delete"/>
            </form>
        </div>
    </div>
</t:user-page>