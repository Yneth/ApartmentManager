<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<div class="container">
    <div class="jumbotron">
        <form class="form-group col-sm-5 center" method="post" action="/user/request/new">
            <label for="room-count-input">
                <fmt:message key="request.roomCount" bundle="${locale}"/> :
            </label>
            <input class="form-control" id="room-count-input" type="text" name="roomCount"/>

            <label for="apartment-type-select">
                <fmt:message key="apartment.type" bundle="${locale}"/>:
            </label>
            <select class="form-control" id="apartment-type-select" name="apartmentTypeId">
                <c:forEach items="${apartmentTypes}" var="apartmentType">
                    <option value="${apartmentType.id}">
                        <fmt:message key="apartment.type.${fn:toLowerCase(apartmentType.name)}" bundle="${locale}"/>
                    </option>
                </c:forEach>
            </select>
            <label for="from-datetime-input">
                <fmt:message key="request.from" bundle="${locale}"/>:
            </label>
            <input class="form-control" id="from-datetime-input" type="datetime-local" name="from"/>

            <label for="to-datetime-input">
                <fmt:message key="request.to" bundle="${locale}"/>:
            </label>
            <input class="form-control" id="to-datetime-input" type="datetime-local" name="to"/>
            <br>
            <input class="form-control btn btn-primary" type="submit"
                   value="<fmt:message key="create" bundle="${locale}"/> "/>
        </form>
        <p:error-partial/>
    </div>
</div>