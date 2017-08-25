<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <jsp:include page="../../include/header.jsp"></jsp:include>
    <title><spring:message code="common.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-sm-4 col-md-offset-4">
            <form id="sForm" method="post" role="form" action="<c:url value="/tools/category/write"/>">
                <input type="hidden" name="categorySeq" value="${categoryRegister.categorySeq}"/>
                <div class="form_header">
                    <span>카테고리</span>
                </div>
                <div class="form-group">
                    <input type="text" id="categoryName" name="categoryName" class="form-control" value="${categoryRegister.categoryName}" placeholder="<spring:message code="category.msg.form.placeholder.category"/>" required />
                </div>
                <div class="form-group">
                    <input type="text" id="restName" name="restName" class="form-control restname" value="${categoryRegister.restName}" placeholder="<spring:message code="category.msg.form.placeholder.restname"/>" required <c:if test="${categoryRegister.categorySeq > 0}">readonly="readonly"</c:if>/>
                </div>
                <div class="form-group">
                    <label for="categoryName">사용유무</label>
                    <label class="radio-inline">
                    <input type="radio" name="used" id="isUsedT" class="radio-inline" value="true" ${categoryRegister.used == true ? 'checked': ''} required>사용
                    </label>
                    <label class="radio-inline">
                    <input type="radio" name="used" id="isUsedF" class="radio-inline" value="false" ${categoryRegister.used == false ? 'checked': ''} required>미사용
                    </label>
                </div>
                <c:if test="${restNameError == true}">
                <div>
                    <spring:message code="category.msg.form.placeholder.restname.fail"/>
                </div>
                </c:if>
                <div class="form_button_container">
                    <div class="text-right">
                        <input type="submit" value="등록" class="btn btn-default"/>
                        <input type="reset" value="취소" class="btn btn-default btn-danger"/>
                        <a href="<c:url value="/tools/category/list"/>" class="btn btn-primary">목록</a>
                    </div>
                </div>
                <input type="hidden" id="restChecked" value="" required/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
</script>
<jsp:include page="../../include/footer.jsp"></jsp:include>
</body>
</html>