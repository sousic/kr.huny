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
                <span>게시물 등록</span>
            </div>
            <div class="form-group">
                <select id="categorySeq" name="categorySeq" class="form-control" required="required">
                    <option value="0">카테고리선택</option>
                    <c:forEach var="cate" items="${category}">
                    <option value="${cate.categorySeq}" <c:if test="${cate.categorySeq eq post.categorySeq}">selected="selected"</c:if>> ${cate.categoryName}</option>
                    </c:forEach>
                </select>
                <c:if test="${categoryNotFound == true}">
                <span class="text-danger"><spring:message code="post.msg.form.category.notfound"/></span>
                </c:if>
            </div>
            <div class="form-group">
                <input type="text" id="title" name="title" placeholder="<spring:message code="post.msg.form.placeholder.title"/>" value="${post.title}" class="form-control" required="required"/>
            </div>
            <div class="form-group">
                <div id="summernote">${post.content}</div>
            </div>

            <div class="form-group">
                <button class="btn btn-default" id="addAttach">파일첨부</button>
            </div>
            <div class="form-group">
                <span>첨부파일</span>
                <ul id="files" class="list-inline">
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
            <textarea id="content" style="display:none;" name="content"></textarea>
            <input type="text" id="galleryQueueList" name="galleryQueueList" value="${post.galleryQueueList}"/>
            <input type="text" id="attachQueueList" name="attachQueueList" value="${post.attachQueueList}"/>
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
        $('#summernote').summernote({
            lang:'ko-KR',
            height:350,
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

        Posts.InitGalleryQueue();
        Posts.InitAttachQueue();

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

        $("#files").on("click",".delFile", function(e) {
           Posts.removeAttachment($(this));
           e.preventDefault();
        });
    });
    var Posts = {
        galleryQueueList : [],
        attachmentQueueList : [],
        InitGalleryQueue:function(e) {
            var strQueue = $("#galleryQueueList").val();
            if ($.trim(strQueue) != '') {
                var queue = strQueue.split(",");
                for (var i = 0; i < queue.length; i++) {
                    this.galleryQueueList.push(parseInt(queue[i]));
                }
            }
        },
        InitAttachQueue:function(e)
        {
            var strQueue = $("#attachQueueList").val();
            if ($.trim(strQueue) != '') {
                var queue = strQueue.split(",");
                for (var i = 0; i < queue.length; i++) {
                    this.attachmentQueueList.push(parseInt(queue[i]));
                }

                $.post("<c:url value="/api/attachment/attachments"/>", { fseq : strQueue }, function (data) {
                    if(data.retCode == 1) {
                        Posts.updateAttachementList(data.result);
                    } else {
                        alert(data.retMsg);
                        return false;
                    }
                });
            }
        },
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
            }
        },
        removeGalleryQueueItem:function(seq) {
            if(this.galleryQueueList.contains(seq)) {
                this.galleryQueueList.delete(parseInt(seq));
                $.getJSON('<c:url value="/api/gallery/remove/"/>'+seq);
            }
        },
        traverseFiles:function(files)
        {
            var form_data = new FormData();
            for(var i = 0; i < files.length;i++) {
                form_data.append('file', files[i]);
            }
            $.ajax({
                data:form_data,
                type:"POST",
                url:'<c:url value="/api/attachment/add"/>',
                cache:false,
                contentType:false,
                enctype:'multipart/form-data',
                processData:false,
                success:function(data) {
                    if(data.retCode == 1) {
                        Posts.updateAttachementList(data.result);
                    } else {
                        alert('파일 업로드에 실패 했습니다.');
                        return false;
                    }
                }
            });
        },
        addAttachmentQueueItem:function(seq) {
            if(!this.attachmentQueueList.contains(seq)) {
                this.attachmentQueueList.push(seq);
            }
        },
        removeAttachmentQueueItem:function(seq) {
            if(this.attachmentQueueList.contains(seq)) {
                this.attachmentQueueList.delete(parseInt(seq));
            }
        },
        updateAttachementList:function(list)
        {
            //$("#files")
            for(var i = 0; i < list.length;i++)
            {
                var linkBtn = $("<a/>")
                    .attr("href","/attachment/" + list[i].fseq).attr("target","_blank").text(list[i].fileName + '(' + list[i].fileSize + ') ');
                var delBtn = $("<button/>").attr("class","btn btn-danger btn-xs delFile").text("삭제").attr("data-fseq", list[i].fseq);
                var wrap = $("<h5/>").html(linkBtn).append(delBtn);
                var li = $("<li/>").html(wrap);
                $("#files").append(li);
                Posts.addAttachmentQueueItem(list[i].fseq);
            }
            $("#popLayer").modal('hide');
        },
        removeAttachment:function (target) {
            var fseq = parseInt(target.data("fseq"));
            $.getJSON('<c:url value="/api/attachment/remove/"/>'+ fseq, function (data) {
                if(data.retCode == 1) {
                    Posts.removeAttachmentQueueItem(fseq);
                    target.parents("li").remove();
                }
            });
        },
        GetValue:function()
        {
            if ($('#summernote').summernote('isEmpty')) {
                alert('내용을 입력해주세요.');
                return false;
            }
            $("#content").text($("#summernote").summernote('code'));
            $("#galleryQueueList").val(this.galleryQueueList);
            $("#attachQueueList").val(this.attachmentQueueList);
        }
    };

</script>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp"></jsp:include>
</body>
</html>