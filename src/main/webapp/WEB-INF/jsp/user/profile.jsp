<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
    <title><spring:message code="user.msg.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<div class="container">
        <form id="joinForm" method="post" role="form" data-toggle="validator" action="${contextPath}/user/profile">
            <h2>가입하기</h2>
            <div class="form-group <c:if test="${failEmail == true}">has-error has-danger</c:if>">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_email">아이디</span>
                    <input type="text" value="${userProfile.email}" class="form-control"  readonly="readonly">
                    <input type="hidden" id="email" name="email" value="${userProfile.email}">
                </div>
                <div class="help-block with-errors"><c:if test="${failEmail == true}"><spring:message code="user.msg.form.error.failEmal"/></c:if></div>
            </div>
            <div class="form-group" <c:if test="${failUsername == true}">has-error has-danger</c:if>">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_username">닉네임</span>
                    <input type="text" id="username" name="username" value="${userProfile.username}" class="form-control" placeholder="<spring:message code="user.msg.form.static.username"/>" data-error="<spring:message code="user.msg.form.static.username"/>" aria-describedby="addon_username" required/>
                </div>
                <div class="help-block with-errors"><c:if test="${failUsername == true}"><spring:message code="user.msg.form.error.faileUsername"/></c:if></div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="addon_about">소개글</span>
                    <textarea id="about" name="about" class="form-control" placeholder="<spring:message code="user.msg.form.static.about"/>" data-error="소개글 <spring:message code="user.msg.form.static.about"/>"  aria-describedby="addon_about">${userProfile.about}</textarea>
                </div>
                <div class="help-block with-errors"></div>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-xs-6 col-md-6">
                        <input type="submit" value="수정하기" class="btn btn-primary btn-block"/>
                    </div>
                    <div class="col-xs-6 col-md-6">
                        <a href="${contextPath}/user/list" class="btn btn-default btn-block" >취소</a>
                    </div>
                </div>
            </div>
        </form>
</div>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
<script type="text/javascript">
</script>
</body>
</html>