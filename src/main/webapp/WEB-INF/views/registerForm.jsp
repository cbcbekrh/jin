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
            width:400px;
            height:500px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
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
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }
        .title {
            font-size : 35px;
            margin: 35px 0 25px 0;
        }
        .msg {
            height: 25px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 20px;
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
    </div>
    <label for="">ID</label>
    <input class="input-field" type="text" name="id" placeholder="4~12자리의 영대소문자와 숫자 조합">
    <label for="">Password</label>
    <input class="input-field" type="password" name="pwd" placeholder="4~12자리의 영대소문자와 숫자 조합">
    <label for="">Name</label>
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    <label for="">E-mail</label>
    <input class="input-field" type="text" name="email" placeholder="cbcbekrh@naver.com">
    <button>가입하기</button>
</form:form>
</body>
</html>