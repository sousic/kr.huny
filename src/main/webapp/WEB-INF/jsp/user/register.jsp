<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <title><spring:message code="user.msg.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
        <form id="joinForm" method="post" role="form" data-toggle="validator" action="${contextPath}/user/register">
            <h2>가입하기</h2>
            <div class="form-group <c:if test="${failEmail == true}">has-error has-danger</c:if>">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_email">아이디</span>
                    <input type="text" id="email" name="email" value="${userRegister.email}" class="form-control" placeholder="<spring:message code="user.msg.form.static.email"/>" data-error="<spring:message code="user.msg.form.static.email"/>" aria-describedby="addon_email" required >
                </div>
                <div class="help-block with-errors"><c:if test="${failEmail == true}"><spring:message code="user.msg.form.error.failEmal"/></c:if></div>
            </div>
            <div class="form-group" <c:if test="${failUsername == true}">has-error has-danger</c:if>">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_username">닉네임</span>
                    <input type="text" id="username" name="username" value="${userRegister.username}" class="form-control" placeholder="<spring:message code="user.msg.form.static.username"/>" data-error="<spring:message code="user.msg.form.static.username"/>" aria-describedby="addon_username" required/>
                </div>
                <div class="help-block with-errors"><c:if test="${failUsername == true}"><spring:message code="user.msg.form.error.faileUsername"/></c:if></div>
            </div>
            <div class="form-group" <c:if test="${pwdConfirm == true}">has-error has-danger</c:if>>
                <div class="input-group">
                    <span class="input-group-addon" id="addon_password">비밀번호</span>
                    <input type="password" id="password" name="password" class="form-control" placeholder="<spring:message code="user.msg.form.error.password.confirm"/>" data-error="<spring:message code="user.msg.form.error.password.confirm"/>" aria-describedby="addon_password" required/>
                </div>
                <div class="help-block with-errors"></div>
            </div>
            <div class="form-group" <c:if test="${pwdConfirm == true}">has-error has-danger</c:if>>
                <div class="input-group">
                    <span class="input-group-addon" id="addon_passwordConfirm">비밀번호확인</span>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" class="form-control" placeholder="<spring:message code="user.msg.form.error.password.confirm"/>" data-error="<spring:message code="user.msg.form.error.password.confirm"/>"  aria-describedby="addon_passwordConfirm" required/>
                </div>
                <div class="help-block with-errors"><c:if test="${pwdConfirm == true}"><spring:message code="user.msg.form.error.password.confirm"/> </c:if></div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_about">소개글</span>
                    <textarea id="about" name="about" class="form-control" placeholder="<spring:message code="user.msg.form.static.about"/>" data-error="소개글 <spring:message code="user.msg.form.static.about"/>"  aria-describedby="addon_about">${userRegister.about}</textarea>
                </div>
                <div class="help-block with-errors"></div>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-xs-6 col-md-6">
                        <input type="submit" value="가입하기" class="btn btn-primary btn-block"/>
                    </div>
                    <div class="col-xs-6 col-md-6">
                        <input type="submit" value="취소" class="btn btn-default btn-block"/>
                    </div>
                </div>
            </div>
        </form>
</div>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>