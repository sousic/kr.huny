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
        <div class="col-md-8 col-sm-8 col-md-offset-2">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th>일련번호</th>
                    <th>카테고리</th>
                    <th>rest명</th>
                    <th>등록글수</th>
                    <th>삭제글수</th>
                    <th>사용유무</th>
                    <th>등록일</th>
                </tr>
                </thead>
                <tbody id="categoryList">
                <c:forEach var="category" items="${categoryList.list}">
                    <tr>
                        <td>${category.categorySeq}</td>
                        <td>${category.categoryName}</td>
                        <td>${category.restName}</td>
                        <td>${category.createCount}</td>
                        <td>${category.removeCount}</td>
                        <td>${category.used}</td>
                        <td>${category.regdate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="text-center">
                <div id="pageNavi" class="center">
                </div>
            </div>
            <div class="text-right">
                <a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>
            </div>

        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){
        $("#pageNavi").bootpag({
            total: ${categoryList.pageNaviInfo.totalPage},
            page: ${categoryList.pageNaviInfo.currentPage+1},
            maxVisible:10
        }).on('page', function(event,num){
            var url = "<c:url value="/api/tools/category/list"/>?page=" + num + "&size=${categoryList.pageNaviInfo.size}";
            refreshList(url);
        });
    });

    function refreshList(url)
    {
        var template = Handlebars.compile($("#entry-categoryItem-template").html());
        //$("#categoryList").html('');

        $.getJSON(url, function(data) {
            $(data.list).each(function() {
                var html = template(this);
                $("#categoryList").html(html);
            });
        });
    }
</script>
<script type="text/x-handlebars-template" id="entry-categoryItem-template">
    <tr>
        <td>{{categorySeq}}</td>
        <td>{{categoryName}}</td>
        <td>{{restName}}</td>
        <td>{{createCount}}</td>
        <td>{{removeCount}}</td>
        <td>{{used}}</td>
        <td>{{regdate}}</td>
    </tr>
</script>
<jsp:include page="../../include/footer.jsp"></jsp:include>
</body>
</html>