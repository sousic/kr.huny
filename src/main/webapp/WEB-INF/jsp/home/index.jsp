<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    Test
    <jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
  </div>
  </body>
</html>
