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
                <button class="btn btn-default" id="addAttach">파일첨부</button>
            </div>
            <div class="form-group">
                <span>첨부파일</span>
                <ul id="files" class="list-inline">
                    <li>
                        <h5><a href="#;" target="_blank">선수스테미너변경요청.csv</a> <button class="btn btn-danger btn-xs">삭제</button></h5>
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
                    </li>
                </ul>
            </div>

            <div class="form_button_container">
                <div class="text-right">
                    <a href="<c:url value="/tools/category/write"/>" class="btn btn-default">등록</a>
                    <a href="javascript:Posts.GetValue()">저장</a>
                </div>
            </div>
            <textarea id="content" style="display:none;"></textarea>
            <input type="text" id="galleryQueueList"/>
            </form>
        </div>
    </div>
</div>
<div id="popLayer" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">첨부파일</h4>
            </div>
            <div class="modal-body">
                <p><input type="file" id="addFiles" name="file" multiple/></p>
                <p>최대 10MB까지 올리수 있습니다.</p>
                <h3>선택된 파일들</h3>
                <ul id="file-list">
                    <li class="no-items">(파일이 선택되지 않음)</li>
                </ul>
            </div>
            <div class="modal-body-popover"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
            </div>
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
                        Posts.sendImageFile(files,this);
                    },
                    onMediaDelete : function($target) {
                        Posts.removeGalleryQueueItem($target.attr("src").replace("/gallery/",""));
                        console.log($target.attr("src"));
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

        $("#addAttach").on("click", function(e) {
            $("#popLayer").modal('show');
            e.preventDefault();
        });
        
        
        $("#addFiles").on("change", function(e) {
            $("#file-list").empty();
            Posts.traverseFiles(this.files);
            e.preventDefault();
        });
    });
    var Posts = {
        galleryQueueList : [],
        sendImageFile:function(files, el)
        {
            var form_data = new FormData();
            var textType = /image.*/;
            for(var i = 0; i < files.length;i++) {
                var file = files[i];
                if(file.type.match(textType))
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
                        data.result.forEach(function (item) {
                            $(el).summernote('editor.insertImage', item.urlPath + item.fseq);
                            this.Posts.addGalleryQueueItem(item.fseq);
                        });
                    } else {
                        alert('이미지 업로드에 실패 했습니다.');
                        return false;
                    }
                }
            });
        },
        addGalleryQueueItem:function(seq) {
            if(!this.galleryQueueList.contains(seq)) {
                this.galleryQueueList.push(seq);
                console.log(this.galleryQueueList);
            }
        },
        removeGalleryQueueItem:function(seq) {
            //if(this.galleryQueueList.contains(seq)) {
            this.galleryQueueList.delete(parseInt(seq));// = this.galleryQueueList.filter(function (item) {  return item.toString() !== seq.toString() });
            //}
            console.log(this.galleryQueueList);
        },
        traverseFiles:function(files)
        {
            var form_data = new FormData();
            form_data.append(files);
            /*$.ajax({
                data:form_data,
                type:"POST",
                url:'<c:url value="/api/gallery/add"/>',
                cache:false,
                contentType:false,
                enctype:'multipart/form-data',
                processData:false,
                success:function(data) {
                    if(data.retCode == 1) {
                        if(data.result.fSeq > 0) {
                            data.result.forEach(function (item) {
                                $(el).summernote('editor.insertImage', item.urlPath + item.fSeq);
                            });
                        }
                        else
                        {
                            alert('이미지만 올릴수 있습니다.');
                            return false;
                        }
                    } else {
                        alert('파일 업로드에 실패 했습니다.');
                        return false;
                    }
                }
            });*/
        },
        GetValue:function()
        {
            if ($('#summernote').summernote('isEmpty')) {
                alert('contents is empty');
            }
            $("#content").text($("#summernote").summernote('code'));
            $("#galleryQueueList").val(this.galleryQueueList);
        }
    };

</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>