<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
    <title><spring:message code="common.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <div class="col-md-8 col-sm-8 col-md-offset-2">
        <div class="form_header">
            <span>회원</span>
        </div>
        <table class="table table-hover table-list">
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
        <div class="text-center">
            <div id="pageNavi" class="center">
            </div>
        </div>
        <div class="form_button_container">
            <div class="text-right">
                <%--<a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>--%>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>