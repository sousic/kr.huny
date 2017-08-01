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
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>일련번호</th>
            <th>이메일</th>
            <th>닉네임</th>
            <th>회원구분</th>
            <th>소셜ID</th>
            <th>가입일</th>
            <th>소개글</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users.content}">
            <tr>
                <td>${user.seq}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.providerId}</td>
                <td>${user.providerUserId}</td>
                <td>${user.regDate}</td>
                <td>${user.about}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>