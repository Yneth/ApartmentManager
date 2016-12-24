<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:admin-page>
    <div class="container">
        <div class="jumbotron">
            <h1><fmt:message key="apartment" bundle="${bundle}"/></h1>
            <form class="form-group col-sm-5 center" method="post" action="/admin/apartment/new">
                <label for="name-input"><fmt:message key="apartment.name" bundle="${bundle}"/>:</label>
                <input class="form-control" id="name-input" type="text" name="name" value="${apartment.name}"/>
                <label for="room-count-input"><fmt:message key="apartment.roomCount" bundle="${bundle}"/>:</label>
                <input class="form-control" id="room-count-input" type="text" name="roomCount"
                       value="${apartment.roomCount}"/>
                <label for="price-input"><fmt:message key="apartment.price" bundle="${bundle}"/>:</label>
                <input class="form-control" id="price-input" type="text" name="price" value="${apartment.price}"/>
                <label for="apartment-type-select"><fmt:message key="apartment.type" bundle="${bundle}"/>:</label>
                <select class="form-control" id="apartment-type-select" name="apartmentTypeId">
                    <c:forEach items="${apartmentTypes}" var="apartmentType">
                        <option ${apartment.type.id == apartmentType.id}
                                value="${apartmentType.id}">
                            <fmt:message key="apartment.type.${apartmentType.name}" bundle="${bundle}"/>
                        </option>
                    </c:forEach>
                </select>
                <br>
                <input class="form-control btn btn-success" type="submit"
                       value="<fmt:message key="create" bundle="${bundle}"/>"/>
            </form>
            <p:error-partial/>
        </div>
    </div>
</t:admin-page>