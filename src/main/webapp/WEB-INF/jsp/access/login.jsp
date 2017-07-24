<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div class="container">
      <form id="loginForm" method="post" role="form" data-toggle="validator" action="/login">
        <h2>로그인</h2>
        <div class="form-group">
          <input type="text" id="loginID" name="loginID" value="${loginID}" class="form-control" placeholder="아이디를 넣어주세요." data-error="아이디를 넣어주세요." required>
          <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
          <input type="password" id="loginPWD" name="loginPWD" class="form-control" placeholder="암호를 넣어주세요." data-error="암호를 넣어주세요." required/>
          <div class="help-block with-errors"></div>
        </div>
        <c:if test="${not empty param.fail}">
          <h5>${message}</h5>
        </c:if>
        <div class="form-group">
          <input type="submit" value="로그인" class="btn btn-primary btn-block"/>
        </div>
        <div class="form-group">
          <a href="/user/register" class="btn btn-primary btn-sm btn-block">회원가입</a>
        </div>
        <div class="form-group">
          <a href="<c:url value="/oauth/callback?type=facebook"/>" class="btn btn-default btn-sm btn-block">login as facebook</a>
        </div>
      </form>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
  </div>
  </body>
</html>
