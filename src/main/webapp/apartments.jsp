<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <script>var dates = [];</script>

    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="filter-box clearfix">
                    <form action="/apartments.jsp" method="GET">
                        <div class="filter-item dates input-daterange clearfix">
                            <div class="filter-label"><fmt:message key="search.filter.dates" bundle="${bundle}"/></div>
                            <input class="date-input" type="text" name="from"
                                   placeholder="<fmt:message key="search.filter.check.in" bundle="${bundle}"/> "/>
                            <input class="date-input" type="text" name="to"
                                   placeholder="<fmt:message key="search.filter.check.out" bundle="${bundle}"/> "/>
                        </div>
                        <div class="filter-item rooms clearfix">
                            <div class="filter-label"><fmt:message key="search.filter.rooms" bundle="${bundle}"/></div>
                            <select name="roomCount">
                                <c:forEach begin="1" end="16" var="i">
                                    <option value="${i}">
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
                            <div class="row">
                                <div class="col-sm-5">
                                    <label><input type="checkbox" name="roomType" value="1"/>Entire Home</label>
                                </div>
                                <div class="col-sm-5">
                                    <label><input type="checkbox" name="roomType" value="2"/>Private Room</label>
                                </div>
                                <div class="col-sm-5">
                                    <label><input type="checkbox" name="roomType" value="3"/>Shared Room</label>
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
            <div class="col-sm-8">
                <div class="row apartments-box">
                    <article class="apartment col-sm-6">
                        <header>
                            <img src="https://a1.muscache.com/im/pictures/c74d4eb6-5f4c-48e1-ac3f-e82f80e5e588.jpg?aki_policy=x_medium"/>
                        </header>
                        <footer class="info">
                            <div class="row">
                                <div class="col-sm-2 price">
                                    <fmt:setLocale value="en_US"/>
                                    <fmt:formatNumber type="CURRENCY" value="100"/>
                                    <fmt:setLocale value="${locale}"/>
                                </div>
                                <div class="col-sm-10 row details">
                                    <div class="col-sm-8 name">
                                        Some name
                                    </div>
                                    <div class="col-sm-4 room-count">
                                        2 rooms
                                    </div>
                                </div>
                            </div>
                        </footer>
                    </article>
                </div>
            </div>
        </div>
    </div>
</t:site-page>