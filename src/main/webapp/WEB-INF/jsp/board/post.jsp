<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <title><spring:message code="common.title"/></title>
    <jsp:include page="../include/header.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/navi.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-sm-8 col-md-offset-2">
            <form method="post" action="">
            <div class="form_header">
                <span>게시물 등록</span>
            </div>
            <div class="form-group">
                <select id="category" name="category" class="form-control">
                    <option value="0">카테고리선택</option>
                </select>
            </div>
            <div class="form-group">
                <input type="text" id="title" name="title" placeholder="제목" class="form-control"/>
            </div>
            <div class="form-group">
                <div id="summernote"></div>
            </div>
            <div class="form-group">
                <span>첨부파일</span>
                <div class="row">

                </div>
                <input type="file" title="첨부"/>
            </div>
            <div class="form_button_container">
                <div class="text-right">
                    <a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>
                    <a href="javascript:GetValue()">저장</a>
                </div>
            </div>
            <textarea id="content"></textarea>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $('#summernote').summernote(
            {
                lang:'ko-KR',
                height:350,
                width:720,
                callbacks:{
                    onImageUpload:function(files) {
                        sendFile(files[0],this);
                    }
                }
            });
    });

    function GetValue()
    {
        if ($('#summernote').summernote('isEmpty')) {
            alert('contents is empty');
        }
        $("#content").text($("#summernote").summernote('code'));
    }

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
</script>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>