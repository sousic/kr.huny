<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <title><spring:message code="common.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <ul>
    <c:forEach var="user" items="${users.content}">
        <li>
            ${user.seq} /
            ${user.email} /
            ${user.username} /
            ${user.providerId} /
            ${user.providerUserId} /
            ${user.regDate} /
            ${user.about}
        </li>
    </c:forEach>
    </ul>
</div>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>