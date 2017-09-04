<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<html>
<head>
    <title><spring:message code="common.title"/></title>
    <jsp:include page="/WEB-INF/jsp/include/header.jsp"></jsp:include>
    <link href="<%=request.getContextPath()%>/resources/css/jquery-file-upload/jquery.fileupload.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/resources/css/jquery-file-upload/style.css" rel="stylesheet">

    <script src="<%=request.getContextPath()%>/resources/js/jquery-file-upload/jquery.ui.widget.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery-file-upload/jquery.iframe-transport.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery-file-upload/jquery.fileupload.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery-file-upload/jquery.fileupload-process.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery-file-upload/jquery.fileupload-validate.js"></script>
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
            <div class="form_button_container">
                <div class="text-right">
                    <a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>
                    <a href="javascript:GetValue()">저장</a>
                </div>
            </div>
            <textarea id="content"></textarea>
            </form>
            <%--<div class="form-group">
                <span>첨부파일</span>
                <div class="row preview">

                </div>
                <input type="file" title="첨부" name="files[]" multiple id="attatchFile"/>
            </div>--%>
            <div class="form-group">
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Add files...</span>
                    <!-- The file input field used as target for the file upload widget -->
                    <input id="fileupload" type="file" name="file" multiple>
                </span>
                <br>
                <br>
                <!-- The global progress bar -->
                <div id="progress" class="progress">
                    <div class="progress-bar progress-bar-success"></div>
                </div>
                <!-- The container for the uploaded files -->
                <div id="files" class="files"></div>
            </div>
        </div>
    </div>
</div>
<script>
    /*jslint unparam: true, regexp: true */
    /*global window, $ */
    $(function () {
        'use strict';
        // Change this to the location of your server-side upload handler:
        var url = '<c:url value="/api/attachment/add"/>';
            /*uploadButton = $('<button/>')
                .addClass('btn btn-primary')
                .prop('disabled', true)
                .text('Processing...')
                .on('click', function () {
                    var $this = $(this),
                        data = $this.data();
                    $this
                        .off('click')
                        .text('Abort')
                        .on('click', function () {
                            $this.remove();
                            data.abort();
                        });
                    data.submit().always(function () {
                        $this.remove();
                    });
                });*/
        $('#fileupload').fileupload({
            url: url,
            dataType: 'json',
            autoUpload: true,
            //acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
            maxFileSize: 999000,
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            previewMaxWidth: 100,
            previewMaxHeight: 100,
            previewCrop: true
        }).on('fileuploadadd', function (e, data) {
            data.context = $('<div class="row"/>').appendTo('#files');
            $.each(data.files, function (index, file) {
                var node = $('<p/>')
                    .append($('<span/>').text(file.name));
                /*if (!index) {
                    node
                        .append('<br>')
                        .append(uploadButton.clone(true).data(data));
                }*/
                node.appendTo(data.context);
            });
        }).on('fileuploadprocessalways', function (e, data) {
            var index = data.index,
                file = data.files[index],
                node = $(data.context.children()[index]);
            if (file.preview) {
                node
                    .prepend('<br>')
                    .prepend(file.preview);
            }
            if (file.error) {
                node
                    .append('<br>')
                    .append($('<span class="text-danger"/>').text(file.error));
            }
            if (index + 1 === data.files.length) {
                data.context.find('button')
                    .text('Upload')
                    .prop('disabled', !!data.files.error);
            }
        }).on('fileuploadprogressall', function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
        }).on('fileuploaddone', function (e, data) {
            $.each(data.result.result, function (index, file) {
                if (file.urlPath) {
                    var link = $('<a>')
                        .attr('target', '_blank')
                        .prop('href', file.urlPath + file.attachSeq)
                        .prop('class', 'addFiles')
                        .attr('data-fseq', file.attachSeq);
                    var delLink = $("<spna>")
                        .text('[X]')
                        .prop('class','btn-sm btn-dangder');
                    $(data.context.children()[index])
                        .wrap(link);
                    $(data.context.children()[index]).parent().append(delLink);
                } else if (file.error) {
                    var error = $('<span class="text-danger"/>').text(file.error);
                    $(data.context.children()[index])
                        .append('<br>')
                        .append(error);
                }
            });
        }).on('fileuploadfail', function (e, data) {
            $.each(data.files, function (index) {
                var error = $('<span class="text-danger"/>').text('File upload failed.');
                $(data.context.children()[index])
                    .append('<br>')
                    .append(error);
            });
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    });
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#summernote').summernote(
            {
                lang:'ko-KR',
                height:350,
                width:720,
                callbacks:{
                    onImageUpload:function(files) {
                        sendFile(files,this);
                    }
                }
            });
        $("#attatchFile").on("change", function() {
            var fileList = this.files ;
            // 읽기
            var reader = new FileReader();
            reader.readAsText(fileList[0]);

            //로드 한 후
            reader.onload = function  () {
                document.querySelector('.preview').src += reader;
            };
        });
    });

    function GetValue()
    {
        if ($('#summernote').summernote('isEmpty')) {
            alert('contents is empty');
        }
        $("#content").text($("#summernote").summernote('code'));
    }

    function sendFile(files, el)
    {
        var form_data = new FormData();
        for(var i = 0; i < files.length;i++) {
            form_data.append('file', files[i]);
        }
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
                    data.result.forEach(function(item) {
                        $(el).summernote('editor.insertImage', item.urlPath + item.attachSeq);
                    });
                } else {
                    alert('이미지 업로드에 실패 했습니다.');
                    return false;
                }
            }
        });
    }
</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>