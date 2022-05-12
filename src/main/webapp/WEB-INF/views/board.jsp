<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'Logout'}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>myspring</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        .container {
            width : 50%;
            margin : auto;
        }
        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }
        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }
        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }
        .btn:hover {
            text-decoration: underline;
        }
        #logo{
            color:white;
            font-size:18px;
            padding-left:40px;
            padding-top:10px;
            margin-right:auto;
            display:flex;
        }
        ul > li > a:hover{
            color:white;
            border-bottom:3px solid rgb(209, 209, 209);
        }
        .ul{
            list-style-type: none;
            height: 48px;
            width: 100%;
            background-color: #30426E;
            display: flex;
        }
        a{text-decoration: none;}
        .li{
            color:lightgray;
            height:100%;
            width:90px;
            display:flex;
            align-items:center;
        }
        .a{
            color: lightgray;
            margin:auto;
            padding: 10px;
            font-size:20px;
            align-items: center;
        }
        .comment-box{
            width : 50%;
            margin : auto;
        }
        .c-box{
            width : 50%;
            margin : auto;
        }
        .d-box{
            width : 50%;
            margin : auto;
            background-color: rgb(236, 236, 236);
        }
        .c-box > button{
            float:right;
            font-size:13px;
        }
        .d-box > button{
            float:right;
            font-size:13px;
        }
        button:hover {
            text-decoration: underline;
        }
        .comment{
            font-size:20px;
        }
        .up_date{
            font-size:13px;
        }



    </style>
</head>
<body>
<div id="menu">
    <ul class="ul">
        <li id="logo">MySpring</li>
        <li class="li"><a class="a" href="<c:url value='/'/>">Home</a></li>
        <li class="li"><a class="a" href="<c:url value='/board/list'/>">Board</a></li>
        <li class="li"><a class="a" href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li class="li"><a class="a" href="<c:url value='/register/add'/>">Sign in</a></li>
        <li class="li"><a class="a" href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<script>
    let msg =${msg}
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="DEL_ERR") alert("게시물 삭제에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="UPD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
    <h2 class="writing-header">${mode=="new" ? "게시물 쓰기" : "게시물 읽기"}</h2>
    <form action="" class="frm" id="form" method="post">
        <input type="hidden" name="bno" value="${boardDto.bno}">
        <input type="text" name="title" value="${boardDto.title}" ${mode=="new" ? "" : "readonly='readonly'"}>
        <textarea name="content" id="" cols="30" rows="10" ${mode=="new" ? "" : "readonly='readonly'"}>${boardDto.content}</textarea>

        <c:if test="${mode eq 'new'}">
            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i>등록</button>
        </c:if>
        <c:if test="${mode ne 'new'}">
            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
        </c:if>

        <c:if test="${boardDto.writer eq loginId}">
            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i>수정</button>
            <button type="button" id="deleteBtn" class="btn btn-delete"><i class="fa fa-trash"></i>삭제</button>
        </c:if>
        <button type="button" id="listBtn" class="btn"><i class="fa fa-bars"></i>목록</button>
    </form>
</div>
<script>
    $(document).ready(function(){
        let formCheck = function(){
            let form = document.getElementById("form");
            if(form.title.value==""){
                alert("제목을 입력해 주세요.");
                form.title.focus();
                return false;
            }
            if(form.content.value==""){
                alert("내용을 입력해 주세요.");
                form.content.focus();
                return false;
            }
            return true;
        }

        $("#modifyBtn").on("click", function(){
            let form = $("#form");
            let isReadOnly = $("input[name=title]").attr('readonly');

            if(isReadOnly=='readonly'){
                $("input[name=title]").attr('readonly', false);
                $("textarea").attr('readonly',false);
                $("#modifyBtn").html("등록");
                $("h2").html("게시물 수정");
                return;
            }
            form.attr("action", "<c:url value='/board/update'/>?page=${page}&pageSize=${pageSize}");
            form.attr("method", "post");
            form.submit();
        });

        $("#writeNewBtn").on("click", function(){
            location.href="<c:url value='/board/write'/>";
        });


        $('#listBtn').on("click", function(){
            location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
        });


        $("#writeBtn").on("click", function(){
            let form = $("#form");
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();

        });

        $("#deleteBtn").on("click", function(){
            if(!confirm('정말로 삭제하시겠습니까?')) return;
            let form = $("#form");
            form.attr("action", "<c:url value='/board/delete'/>?page=${page}&pageSize=${pageSize}");
            form.attr("method", "post");
            form.submit();
        });
    });
</script>


<!----- 댓글 ----->

<c:if test="${mode ne 'new'}">
<div class="comment-box">
</br></br>comment <textarea name="comment" placeholder="내용을 입력해 주세요." rows="3"></textarea><br>
<button id="sendBtn" type="button" class="btn btn-send">등록</button>
<button id="modBtn" type="button" class="btn btn-mod" style="display:none">수정</button>
<button id="canBtn" type="button" class="btn btn-can" style="display:none">취소</button><hr>
</div>

<div id="commentList"></div>
<div id="replyForm" style="display:none; width:50%; margin:0 auto;">
    <textarea name="replyComment" placeholder="답글 내용을 입력해 주세요." rows="2"></textarea>
    <button id="wrtRepBtn" type="button">등록</button>
</div>
</c:if>


<script>
    let bno = ${boardDto.bno};

    let showList = function(bno){
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/jin/comments?bno='+bno,  // 요청 URI
            success : function(result){
                $('#commentList').html(toHtml(result));
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }


    $(document).ready(function(){
        showList(bno);

        $("#canBtn").click(function(){
            $("textarea[name=comment]").val('')
            $("#sendBtn").css("display", "block");
            $("#modBtn").css("display", "none");
            $("#canBtn").css("display", "none");
        });

        $("#modBtn").click(function(){
            let cno = $(this).attr("data-cno");
            let comment = $("textarea[name=comment]").val();

            if(comment.trim()==''){
                alert("댓글을 입력해주세요.");
                $("textarea[name=comment]").focus()
                return;
            }

            $.ajax({
                type:'PATCH',
                url: '/jin/comments/'+cno,
                headers : { "content-type": "application/json"},
                data : JSON.stringify({cno:cno, comment:comment}),
                success : function(result){
                    alert("수정완료")
                    showList(bno);
                },
                error : function(){alert("error")}
            })
            $("textarea[name=comment]").val('')
            $("#sendBtn").css("display", "block");
            $("#modBtn").css("display", "none");
            $("#canBtn").css("display", "none");
        });

        $("#wrtRepBtn").click(function(){
            let comment = $("textarea[name=replyComment]").val();
            let pcno = $("#replyForm").parent().attr("data-pcno");

            if(comment.trim()==''){
                alert("댓글을 입력해주세요.");
                $("textarea[name=replyComment]").focus()
                return;
            }

            $.ajax({
                type:'POST',       // 요청 메서드
                url: '/jin/comments?bno='+bno,  // 요청 URI  // /jin/comments?bno=1085 POST
                headers : { "content-type": "application/json"}, // 요청 헤더
                data : JSON.stringify({pcno:pcno, bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success : function(result){
                    alert("등록완료")
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()

            $("#replyForm").css("display", "none")
            $("textarea[name=replyComment]").val('')
            $("#replyForm").appendTo("body");
        });

        $("#sendBtn").click(function(){
            let comment = $("textarea[name=comment]").val();

            if(comment.trim()==''){
                alert("댓글을 입력해주세요.");
                $("textarea[name=comment]").focus()
                return;
            }
            $.ajax({
                type:'POST',
                url: '/jin/comments?bno='+bno,
                headers : { "content-type": "application/json"},
                data : JSON.stringify({bno:bno, comment:comment}),
                success : function(result){
                    alert("등록완료")
                    showList(bno);
                },
                error : function(){alert("error")}
            })
            $("textarea[name=comment]").val('')
        });

        $("#commentList").on("click", ".modBtn", function() {
            let cno = $(this).parent().parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent().parent()).text();

            // 1. comment의 내용을 input에 뿌려주기
            $("textarea[name=comment]").val(comment);
            $("textarea[name=comment]").focus()
            // 2. cno 전달하기
            $("#modBtn").attr("data-cno", cno);
            $("#sendBtn").css("display", "none");
            $("#modBtn").css("display", "inline-block");
            $("#canBtn").css("display", "inline-block");
            $("#replyForm").css("display", "none");
        });


        $("#commentList").on("click", ".replyBtn", function(){
            // 1. replyForm을 옮기고
            $("#replyForm").appendTo($(this).parent().parent())
            $("textarea[name=replyComment]").val('')

            // 2. 답글을 입력할 폼을 보여주고
            $("#replyForm").css("display", "block");
        });



        // $(".delBtn").click(function(){
        $("#commentList").on("click", ".delBtn", function(){
            if(!confirm('정말로 삭제하시겠습니까?')) return;
            let cno = $(this).parent().parent().attr("data-cno");
            let bno = $(this).parent().parent().attr("data-bno");

            $.ajax({
                type:'DELETE',       // 요청 메서드
                url: '/jin/comments/'+cno+'?bno='+bno,  // 요청 URI
                success : function(result){
                    alert("삭제완료")
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });
    });

    let toHtml = function(comments){
        let tmp = "<ul>";
        let loginId = '${sessionScope.id}';

        comments.forEach(function (comment){
            tmp += '<li data-cno='+ comment.cno
            tmp += ' data-pcno=' + comment.pcno
            tmp += ' data-bno=' + comment.bno
                + '>'
            if(comment.cno!=comment.pcno){
                tmp += '<div class="d-box">ㄴ'+comment.commenter
                tmp += '<br><span class="comment">'+comment.comment+'</span><br>'
                tmp += '<span class="up_date">'+comment.up_date+'</span>'
                if(comment.commenter==loginId){
                tmp += '<button class="delBtn">삭제</button>'
                tmp += '<button class="modBtn">수정</button>'
                }
                tmp += '<button class="replyBtn">답글</button><hr></div>'
            }else{
            tmp += '<div class="c-box">'+comment.commenter
            tmp += '<br><span class="comment">'+comment.comment+'</span><br>'
            tmp += '<span class="up_date">'+comment.up_date+'</span>'
                if(comment.commenter==loginId) {
                    tmp += '<button class="delBtn">삭제</button>'
                    tmp += '<button class="modBtn">수정</button>'
                }
            tmp += '<button class="replyBtn">답글</button><hr></div>'
            tmp += '</div>'
            tmp += '</li>'
            }
        })

        return tmp + "</ul>";
    }
</script>
</body>
</html>