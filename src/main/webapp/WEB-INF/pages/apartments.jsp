<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <c:set var="apartments" value="${page.content}"/>

    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="filter-box clearfix">
                    <form action="/apartments" method="GET">
                        <div class="filter-item dates input-daterange clearfix">
                            <div class="filter-label"><fmt:message key="search.filter.dates" bundle="${bundle}"/></div>
                            <input class="date-input" type="text" name="from"
                                   value="${search.checkIn}"
                                   placeholder="<fmt:message key="search.filter.check.in" bundle="${bundle}"/> "/>
                            <input class="date-input" type="text" name="to"
                                   value="${search.checkOut}"
                                   placeholder="<fmt:message key="search.filter.check.out" bundle="${bundle}"/> "/>
                        </div>
                        <div class="filter-item rooms clearfix">
                            <div class="filter-label"><fmt:message key="search.filter.rooms" bundle="${bundle}"/></div>
                            <select name="roomCount">
                                <c:forEach begin="1" end="16" var="i">
                                    <option value="${i}" ${i == search.roomCount ? 'selected' : '' }>
                                            ${i}
                                        <fmt:message key="${i > 1 ? 'apartment.room.plural' : 'apartment.room'}"
                                                     bundle="${bundle}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="filter-item room-types clearfix">
                            <div class="filter-label">
                                <fmt:message key="search.filter.room.types" bundle="${bundle}"/>
                            </div>

                            <c:set var="ids" value="${search.apartmentTypeIds}"/>
                            <div class="row">
                                <div class="col-sm-12">
                                    <label><input type="checkbox" name="roomType" value="1"
                                        ${not empty ids && fn:contains(ids, '1') ? 'checked' : ''}/>Entire Home</label>
                                </div>
                                <div class="col-sm-12">
                                    <label><input type="checkbox" name="roomType" value="2"
                                        ${not empty ids && fn:contains(ids, '2') ? 'checked' : ''}/>Private Room</label>
                                </div>
                                <div class="col-sm-12">
                                    <label><input type="checkbox" name="roomType" value="3"
                                        ${not empty ids && fn:contains(ids, '3') ? 'checked' : ''}/>Shared Room</label>
                                </div>
                            </div>
                        </div>
                        <div class="filter-item submit">
                            <button class="btn btn-primary" type="submit">
                                <fmt:message key="filter" bundle="${bundle}"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row apartments-box">
                <c:forEach items="${apartments}" var="apartment">
                    <article class="apartment col-sm-4">
                        <header>
                            <img src="https://a1.muscache.com/im/pictures/c74d4eb6-5f4c-48e1-ac3f-e82f80e5e588.jpg?aki_policy=x_medium"/>
                        </header>
                        <footer class="info">
                            <div class="row">
                                <div class="price col-sm-4">
                                    <span>
                                        <fmt:setLocale value="en_US"/>
                                        <fmt:formatNumber type="CURRENCY" value="${apartment.price}"
                                                          maxFractionDigits="0"/>
                                        <fmt:setLocale value="${locale}"/>
                                    </span>
                                </div>
                                <div class="name col-sm-8">
                                    <span>${apartment.name}</span>
                                </div>
                                <div class="type col-sm-6">
                                    <span>${apartment.type.name}</span>
                                </div>
                                <div class="room-count col-sm-6">
                                    <span>
                                        ${apartment.roomCount}
                                        <fmt:message
                                                key="${apartment.roomCount > 1 ? 'apartment.room.plural' : 'apartment.room'}"
                                                bundle="${bundle}"/>
                                    </span>
                                </div>
                            </div>
                        </footer>
                    </article>
                </c:forEach>
            </div>
        </div>
    </div>
</t:site-page>