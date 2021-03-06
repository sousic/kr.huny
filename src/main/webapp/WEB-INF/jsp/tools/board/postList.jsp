<%@ page import="kr.huny.common.DateTimeHelper" %>
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
    <div class="row">
        <div class="col-md-8 col-sm-8 col-md-offset-2">
            <div class="form_header">
                <span>게시판</span>
            </div>
            <table class="table table-hover table-list">
                <thead>
                <tr>
                    <th>일련번호</th>
                    <th>카테고리</th>
                    <th>제목</th>
                    <th>등록자</th>
                    <th>등록일</th>
                    <th>최종수정일</th>
                    <th>댓글</th>
                    <th>첨부</th>
                    <th>조회수</th>
                    <th>동작</th>
                </tr>
                </thead>
                <tbody id="boardFreeList">
                <c:forEach var="free" items="${boardFreeList.list}">
                    <tr>
                        <td>${free.boardSeq}</td>
                        <td>${free.boardCategory.categoryName}</td>
                        <td><a href="<c:url value="/tools/board/"/>${free.boardSeq}">${free.title}</a></td>
                        <td>${free.username}</td>
                        <td><span class="timeage" title="${DateTimeHelper.GetDateTime(free.regdate)}">${DateTimeHelper.GetDateTime(free.regdate)}</span></td>
                        <td><span class="timeage" title="${DateTimeHelper.GetDateTime(free.lastDateModify)}">${DateTimeHelper.GetDateTime(free.lastDateModify)}</span></td>
                        <td>${free.commentCount}</td>
                        <td>${free.attachmentCount}</td>
                        <td>${free.readcount}</td>
                        <td><a href="#" class="btnDelete btn btn-default">삭제</a></td>
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
                    <a href="<c:url value="/tools/board/post"/>" class="btn btn-default">등록</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="deleteLayer" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">삭제</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="cseq" value=""/>
                <p class="tag"></p>
                <p>삭제 하시겠습니까?</p>
            </div>
            <div class="modal-body-popover"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-danger">삭제</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#boardFreeList .timeage").timeago();
        //$("#boardFreeList").on(".timeage").timeago();

        $("#pageNavi").bootpag({
            total: ${boardFreeList.pageNaviInfo.totalPage},
            page: ${boardFreeList.pageNaviInfo.currentPage+1},
            maxVisible:10
            //href:"<c:url value="/tools/board/list"/>?page={{number}}&size=${boardFreeList.pageNaviInfo.size}"
        }).on('page', function(event,num){
            var url = "<c:url value="/api/tools/board/list"/>?page=" + num + "&size=${boardFreeList.pageNaviInfo.size}";
            refreshList(url);
        });

        $("#categoryList").on("click", ".btnDelete", function() {
            $("#deleteLayer .tag").text('['+$(this).parents("tr").find("td:eq(1)").text()+'] 분류를');
            $("#cseq").val($(this).parents("tr").find("td:eq(0)").text());
            $("#deleteLayer").modal("show");
        });

        /*$("#deleteLayer").on("click", ".btn-danger", function(){
            $.post('<c:url value="/api/tools/category/remove/"/>' + $("#cseq").val(), function (data) {
                if(data.retCode == 1) {
                    $("#cseq").val("");
                    $("#deleteLayer").modal('hide');
                    var url = "<c:url value="/api/tools/category/list"/>?page=1&size=${boardFreeList.pageNaviInfo.size}";
                    refreshList(url);
                } else {
                    alert(data.retMsg);
                    return false;
                }
            }).fail(function() {
               alert('잠시 후 다시 이용해 주세요.')
            });
        });*/
    });

    Handlebars.registerHelper('date', Utils.DateFormate);

    function refreshList(url)
    {
        var template = Handlebars.compile($("#entry-freeboardItem-template").html());
        $("#boardFreeList").html('');

        var tmp = '';

        $.getJSON(url, function(data) {
            $(data.list).each(function() {
                tmp += template(this);
            });
            $("#boardFreeList").html(tmp);
        }).done(function() {
            $("#boardFreeList .timeage").timeago();
        });
    }
</script>
<script type="text/x-handlebars-template" id="entry-freeboardItem-template">
    <tr>
        <td>{{boardSeq}}</td>
        <td>{{boardCategory.categoryName}}</td>
        <td><a href="<c:url value="/tools/board/"/>{{boardSeq}}">{{title}}</a></td>
        <td>{{username}}</td>
        <td><span class="timeage" title="{{date regdate}}">{{date regdate}}</span></td>
        <td><span class="timeage" title="{{date lastDateModify}}">{{date lastDateModify}}</span></td>
        <td>{{commentCount}}</td>
        <td>{{attachmentCount}}</td>
        <td>{{readcount}}</td>
        <td><a href="#" class="btnDelete btn btn-default">삭제</a></td>
    </tr>
</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>