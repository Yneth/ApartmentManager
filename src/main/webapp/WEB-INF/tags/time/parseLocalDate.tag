<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="value" required="true" type="java.time.LocalDate" %>
<%@ attribute name="pattern" required="false" type="java.lang.String" %>

<fmt:parseDate value="${value}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>

<c:if test="${not empty pattern}">
    <fmt:formatDate value="${parsedDate}" type="date" pattern="${pattern}"/>
</c:if>
<c:if test="${empty pattern}">
    <fmt:formatDate value="${parsedDate}" type="date"/>
</c:if>

