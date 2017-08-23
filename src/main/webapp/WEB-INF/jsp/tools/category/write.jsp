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
<div class="container content">
    <div class="row">
        <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
            <div class="category-form">
                <form id="sForm" method="post" role="form" action="<c:url value="/tools/category/write"/>">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon" id="addmon_categoryName">카테고리</span>
                            <input type="text" id="categoryName" name="categoryName" class="form-control" value="${categoryRegister.categoryName}" placeholder="" data-error="" required/>
                        </div>
                    </div>
                    <div class="form-inline">
                        <div class="input-group">
                            <span class="input-group-addon" id="addmon_restName">rest명</span>
                            <input type="text" id="restName" name="restName" class="form-control" value="${categoryRegister.restName}" placeholder="" data-error="" required/>
                        </div>
                        <button type="button" class="btn btn-default btn-success">중복체크</button>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <label class="radio-inline">
                                <span class="input-group">사용유무</span>
                            </label>
                            <%--<input type="text" class="form-control" value=""/>--%>
                            <label class="radio-inline">
                            <input type="radio" name="used" id="isUsedT" class="radio-inline" value="true" ${categoryRegister.used == true ? 'checked': ''} required>사용
                            </label>
                            <label class="radio-inline">
                            <input type="radio" name="used" id="isUsedF" class="radio-inline" value="false" ${categoryRegister.used == false ? 'checked': ''} required>미사용
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
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
</div>
<script type="text/javascript">
</script>
<jsp:include page="../../include/footer.jsp"></jsp:include>
</body>
</html>