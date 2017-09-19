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
                    <span>[${boardFree.boardCategory.categoryName}]</span> ${boardFree.title}
                </div>
                <div class="form-group">
                    ${boardFree.content}
                </div>
                <div class="form-group">
                    <span>첨부파일</span>
                    <ul id="files" class="list-inline">
                        <c:forEach items="${attach}" var="item">
                            <li>
                                <h5><i class="fa fa-file" aria-hidden="true"></i> <a href="<c:url value="/attachment/"/>${item.attachSeq}" target="_blank">${item.fileName}</a></h5>
                            </li>
                        </c:forEach>
                        <%--<li>
                            <h5><a href="#;" target="_blank">선수스테미너변경요청.csv</a> <button class="btn btn-danger btn-xs delFile" data-fseq="1">삭제</button></h5>
                        </li>
                        <li>
                            <h5><a href="#;">선수스테미너변경요청222.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
                        </li>
                        <li>
                            <h5><a href="#;">선수스테미너변경요청333.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
                        </li>
                        <li>
                            <h5><a href="#;">선수스테미너변경요청444.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
                        </li>
                        <li>
                            <h5><a href="#;">선수스테미너변경요청55.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
                        </li>
                        <li>
                            <h5><a href="#;">선수스테미너변경요청6666.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
                        </li>--%>
                    </ul>
                </div>

                <div class="form_button_container">
                    <div class="text-right">
                        <input type="submit" value="저장" class="btn btn-default btn-success">
                        <a href="<c:url value="/tools/board/list"/>" class="btn btn-default">목록</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {

    });
</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>