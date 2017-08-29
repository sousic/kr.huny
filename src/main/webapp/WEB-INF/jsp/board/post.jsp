<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <title><spring:message code="common.title"/></title>
    <jsp:include page="../include/header.jsp"></jsp:include>
    <link href="/resources/css/summernote/summernote.css" rel="stylesheet">
    <script src="/resources/js/summernote/summernote.min.js"></script>
    <script src="/resources/js/summernote/lang/summernote-ko-KR.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-sm-8 col-md-offset-2">
            <div class="form_header">
                <span>게시물 등록</span>
            </div>
            <div>
                <div id="summernote"></div>
            </div>
            <script>
                $(document).ready(function() {
                    $('#summernote').summernote(
                    {
                        lang:'ko-KR',
                        height:350,
                        width:750,
                        callbacks:{
                            onImageUpload:function(files) {
                                sendFile(files[0],this);
                            }
                        }
                    });
                });
            </script>
            <div class="form_button_container">
                <div class="text-right">
                    <a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function sendFile(file, el)
    {
        var form_data = new FormData();
        form_data.append('file', file);
        $.ajax({
            data:form_data,
            type:"POST",
            url:'<c:url value="/api/gallery/add"/>',
            cache:false,
            contentType:false,
            enctype:'multipart/form-data',
            processData:false,
            success:function(data) {
                if(data.retCode == 1) {
                    $(el).summernote('editor.insertImage', data.result.urlPath + data.result.gallerySeq);
                } else {
                    alert('이미지 업로드에 실패 했습니다.');
                    return false;
                }
            }
        });
    }

    /*$(function(){
        $("#deleteLayer").on("click", ".btn-danger", function(){
            $.post('<c:url value="/api/tools/category/remove/"/>' + $("#cseq").val(), function (data) {
                if(data.retCode == 1) {
                    $("#cseq").val("");
                    $("#deleteLayer").modal('hide');
                    var url = "<c:url value="/api/tools/category/list"/>?page=1&size=${categoryList.pageNaviInfo.size}";
                    refreshList(url);
                } else {
                    alert(data.retMsg);
                    return false;
                }
            }).fail(function() {
                alert('잠시 후 다시 이용해 주세요.')
            });
        });
    });*/
</script>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>