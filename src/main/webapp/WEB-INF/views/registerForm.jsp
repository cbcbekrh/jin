<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <style>
        * { box-sizing:border-box; }
        form {
            width:500px;
            height:600px;
            display : flex;
            flex-direction: column;
            position : absolute;
            padding-left:50px;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(89,117,196);
            border-radius: 10px;
        }
        .input-field {
            width: 300px;
            height: 40px;
            border : 1px solid rgb(89,117,196);
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 10px;
        }
        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }
        button {
            background-color: rgb(89,117,196);
            color : white;
            width:105px;
            height:40px;
            font-size: 15px;
            border : none;
            border-radius: 5px;
            margin : 0 0 10px 0;
        }
        .SUBMIT {
            background-color: rgb(89,117,196);
            color : white;
            width:400px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 5px 0 10px 0;
        }
        .title {
            text-align:center;
            font-size : 35px;
            margin: 35px 0 25px 0;
            padding-right:50px;

        }
        .msg {
            height: 25px;
            text-align:center;
            font-size:17px;
            color:red;
            padding-right:50px;
        }
        .id_ok{color:red; display: none;}
        .id_already{color:red; display: none;}
        .ID{
            display: inline-block;
        }
        .PASS{
            display: inline-block;
        }
        .NAME{
            display: inline-block;
        }
        .NUMBER{
            display: inline-block;
        }
    </style>
    <title>Register</title>
</head>
<script>
    let msg1 = "${msg1}"
    if(msg1=="Id_ERR") alert("이미 존재하는 아이디 입니다.");
</script>
<body>
<form:form modelAttribute="userDto">
    <div class="title">회원가입</div>
    <div id="msg" class="msg">
        <form:errors path="id"/>
        <form:errors path="pwd"/>
        <span class="id_ok">사용 가능한 아이디입니다.</span>
        <span class="id_already">누군가 이 아이디를 사용하고 있어요.</span>
    </div>


    <label for="">ID</label>
    <div class="ID">
    <input class="input-field" type="text" name="id" placeholder="4~12자리의 영대소문자와 숫자 조합">
    <button id="idBtn" type="button">중복확인</button>
    </div>


    <label for="">Password</label>
    <input class="input-field" type="password" name="pwd" placeholder="4~12자리의 영대소문자와 숫자 조합">

    <label for="">Name</label>
    <div class="NAME">
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    </div>

    <label for="">E-mail</label>
<div class="PASS">
    <input class="input-field" type="text" name="email" placeholder="cbcbekrh@naver.com">
    <button type="button" id="mailAuth">메일인증하기</button>
</div>

    <label for="">인증번호</label>
    <div class="NUMBER">
    <input class="input-field" type="text" name="memMailN">
    <button type="button" id="mailAuthN">인증번호확인</button>
    </div>

    <button type="button" class="SUBMIT" id="SUB">가입하기</button>
</form:form>




</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    $(document).ready(function(){
        $("#SUB").click(function(){
            let mail = $("input[name='email']").val();
            let id = $("input[name=id]").val();

            if(id.trim()==''){
                alert("아이디를 입력해주세요.");
                $("input[name=id]").focus()
                return;
            }else if(id.length < 4 || id.length > 12){
                alert("아이디 : 4~12자리까지 입력해주세요");
                return;
            }



           $.ajax({
               url : "<c:url value='/Cnt?mail=' />"+mail,
               type:'GET',
               headers : { "content-type": "application/json"},
               success:function(Cnt){
                   if(Cnt != 1){
                       alert('이메일인증을 해주세요');
                   }else{
                       $("form").submit();
                   }
               }
           })
        });


        $("#idBtn").click(function(){
            let id = $("input[name=id]").val();

            if(id.trim()==''){
                alert("아이디를 입력해주세요.");
                $("input[name=id]").focus()
                return;
            }else if(id.length < 4 || id.length > 12){
                alert("아이디 : 4~12자리까지 입력해주세요");
                return;
            }

            $.ajax({
                url: '/jin/idCheck/',
                type:'POST',
                headers : { "content-type": "application/json"},
                data : (id),
                success : function(Cnt){
                    if(Cnt != 1){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디
                        alert("사용가능한 아이디 입니다");
                    } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                        alert("누군가 이 아이디를 사용하고 있어요")
                        $("input[name=id]").focus()
                    }
                },
                error   : function(){ alert("에러") }
            })
        });

        //mail인증하기 버튼 클릭
        $("#mailAuth").on("click",function(e){
            isMailAuthed=true;
            $.ajax({
                url : "<c:url value='/mailAuth.wow' />"
                ,data : {"mail" : $("input[name='email']").val()}
                ,success: function(){
                    alert("인증번호를 보냈습니다.");
                },error : function(){
                    alert("에러");
                }
            });//ajax
        });//mailCheck

        // 인증번호 확인
        $("#mailAuthN").on("click",function(e){
            let Number = $("input[name='memMailN']").val();
            let mail = $("input[name='email']").val();

            if(Number.trim()=='') {
                alert("인증번호를 입력해주세요");
                $("input[name=memMailN]").focus()
                return;
            }else if(Number.length != 6){
                alert("인증번호는 6자리 입니다");
                return;
            }

            $.ajax({
                url : "<c:url value='/mailAuthN.wow' />"
                ,data : ({"Number" : Number, "mail" : mail})
                ,success: function(result){
                    if(result == "OK"){
                        alert("인증성공");
                    }else{
                        alert("인증번호를 확인해주세요");
                    }
                },error : function(){
                    alert("error 다시시도해주세요");
                }
            });//ajax
        });//mailCheck
    });
</script>
</html>