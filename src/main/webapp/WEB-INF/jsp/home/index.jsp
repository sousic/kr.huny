<%@ page import="kr.huny.authentication.BasicPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%
  Authentication auth = (Authentication)request.getUserPrincipal();
  BasicPrincipal user = null;
  if(auth == null) {
    user = new BasicPrincipal();
  } else {
    Object principal = auth.getPrincipal();
    if(principal != null && principal instanceof BasicPrincipal) {
      user = ((BasicPrincipal)principal);
    }
  }
%>
<html>
  <head>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
    <title><spring:message code="common.title"/></title>
  </head>
  <body>
  <jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
  <div class="container">
    Test
    로그인 계정 정보 : <%=user.toString()%>
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
  </div>
  </body>
</html>
