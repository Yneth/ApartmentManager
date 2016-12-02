<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <c:if test="${not empty request}">
                <form class="form-group" method="post" action="/admin/request/confirm">
                    <input type="hidden" name="id" value="${request.id}"/>
                    <t:request-partial request="${request}"/>
                    <label for="cost-text">Price</label>
                    <input class="form-control" id="cost-text" type="text" name="cost"/>
                    <c:forEach items="${apartments}" var="apartment">
                        <div class="radio">
                            <label><input type="radio" name="id" value="${apartment.id}"/>
                                    ${apartment.type.name}
                            </label>
                        </div>
                    </c:forEach>
                    <input class="form-control btn btn-success" type="submit" value="Confirm"/>
                </form>
            </c:if>
            <c:if test="${empty request}">
                <p>No such request.</p>
            </c:if>
        </div>
    </div>
</t:admin-page>