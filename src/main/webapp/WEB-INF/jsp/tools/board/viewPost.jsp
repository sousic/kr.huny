<%@ page import="kr.huny.common.DateTimeHelper" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <title><spring:message code="common.title"/></title>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-sm-8 col-md-offset-2">
            <form method="post" action="<c:url value="/tools/board/post"/>" onsubmit="return Posts.GetValue();">
                <div class="form_header">
                    <div class="form_header_title">
                        <span>[${boardFree.boardCategory.categoryName}]</span> ${boardFree.title}
                    </div>
                    <div class="form_header_date">
                        <span class="timeage" title="${DateTimeHelper.GetDateTime(boardFree.regdate)}">${DateTimeHelper.GetDateTime(boardFree.regdate)}</span>
                    </div>
                    <div class="form_header_count">
                        <i class="fa fa-eye"></i> ${boardFree.readcount}
                    </div>
                </div>
                <div class="form-group">
                    ${boardFree.content}
                </div>
                <div class="form-group">
                    <div class="attacments_header">첨부파일</div>
                    <ul id="files" class="list-group">
                        <c:if test="${attach.size() eq 0}">
                            <li class="list-group-item list-group-item-info">등록된 첨부 파일이 없습니다.</li>
                        </c:if>
                        <c:forEach items="${attach}" var="item">
                            <li class="list-group-item">
                                <i class="fa fa-file-o"></i> <a href="<c:url value="/attachment/"/>${item.attachSeq}">${item.fileName}</a> / ${FileUtils.byteCountToDisplaySize(item.size)} / Downloads: ${item.downloads}
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="form_button_container">
                    <div class="text-right">
                        <input type="submit" value="수정" class="btn btn-default btn-success">
                        <input type="button" value="삭제" class="btn btn-default btn-danger">
                        <a href="<c:url value="/tools/board/list"/>" class="btn btn-default">목록</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        $(".timeage").timeago();
    });
</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>