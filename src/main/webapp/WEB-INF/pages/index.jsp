<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags/partials" %>

<t:site-page>
    <script>var dates = [];</script>

    <div class="center-vertical">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="search-form clearfix">
                        <form action="/apartments" method="get">
                            <div class="search-item location">
                                <label for="location-text">
                                    <fmt:message key="search.filter.where" bundle="${bundle}"/>
                                </label>
                                <input id="location-text" type="text" name="location"
                                       placeholder="<fmt:message key="search.filter.where.placeholder" bundle="${bundle}"/>"/>
                            </div>
                            <div class="search-item dates input-daterange">
                                <label>
                                    <fmt:message key="search.filter.when" bundle="${bundle}"/>
                                </label>
                                <div class="date">
                                    <input class="date-input" id="from" type="text" name="from"
                                           placeholder="<fmt:message key="search.filter.check.in" bundle="${bundle}"/>"/>
                                </div>
                                <div class="date-arrow">
                                    <svg viewBox="0 0 1000 1000">
                                        <path d="M694.4 242.4l249.1 249.1c11 11 11 21 0 32L694.4 772.7c-5 5-10 7-16 7s-11-2-16-7c-11-11-11-21 0-32l210.1-210.1H67.1c-13 0-23-10-23-23s10-23 23-23h805.4L662.4 274.5c-21-21.1 11-53.1 32-32.1z">
                                        </path>
                                    </svg>
                                </div>
                                <div class="date">
                                    <input class="date-input" id="to" type="text" name="to"
                                           placeholder="<fmt:message key="search.filter.check.out" bundle="${bundle}"/>"/>
                                </div>
                            </div>
                            <div class="search-item guests">
                                <label for="guest-select-box">
                                    <fmt:message key="search.filter.rooms" bundle="${bundle}"/>
                                </label>
                                <select id="guest-select-box" name="roomCount">
                                    <c:forEach begin="1" end="16" var="i">
                                        <option value="${i}">${i}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="search-item submit">
                                <input class="btn btn-primary" type="submit"
                                       value="<fmt:message key="search" bundle="${bundle}"/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:site-page>