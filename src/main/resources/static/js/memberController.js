$(function(){
    $("#myModal").modal({
        show : true,
        backdrop : false,
        keyboard : false
    });
})
// 약관 동의서 함수
function chk() {
    let req1 = $("#req1").is(":checked");
    let req2 = $("#req2").is(":checked");

    if(req1 == true && req2 == true) {
        window.close();
        opener.location.href = "/move/registerForm";
    }else {
        alert("필수 약관에 동의하셔야 합니다.");
    }
}

function nochk(){
    alert("동의하지 않으면 가입하실 수 없습니다.");
    window.close();
}

// 가입 양식 입력확인용 변수들
let idComplete = false;
let pwdComplete = false;
let pwdRepeatComplete = false;
let nicknameComplete = false;
let phoneComplete = false;

// 아이디 입력 값 규칙준수 여부 확인
function checkId_pattern(){

    let userid = document.getElementById("userId").value;
    
    fetch("/member/checkId_pattern", {
         method:"post",
         headers: {
             "Content-Type": "application/json",
         },
         body: JSON.stringify({
            "userid":userid
         })
     })
     .then((response) => response.text())
     .then((checkStatus) => {
        if(checkStatus === "false"){
            $("#idCheck").text(" - 아이디 형식이 잘못 되었습니다.");
            $("#idCheck").css("color","red");
            idComplete = false;
            formCheck();
        }
        else{
            $("#idCheck").text("");
            idComplete = true;

            // 아이디 중복 여부 확인
            checkId_overlap(userid);
        }
     });
}

function checkId_overlap(userId){
    // DB 접근을 통해 아이디 중복 여부 확인
    formCheck();
}

// 비밀번호 입력값 규칙준수 여부 확인
function checkPwd_pattern(){

    let pwd = document.getElementById("passwd").value;

    fetch("/member/checkPwd_pattern", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
           "passwd":pwd
        })
    })
    .then((response) => response.text())
    .then((checkStatus) =>{
        if(checkStatus === "false"){
            $("#pwdCheck").text(" - 비밀번호 형식이 잘못 되었습니다.");
            $("#pwdCheck").css("color","red");
            const pwdRepeat = document.getElementById("checkPasswd");
            pwdRepeat.disabled = true;
            pwdComplete = false;
            formCheck();
        }
        else{
            $("#pwdCheck").text("");
            const pwdRepeat = document.getElementById("checkPasswd");
            pwdRepeat.disabled = false;
            pwdComplete = true;
            formCheck();
        }
    })
}

// 비밀번호 확인 검증
function checkPwd_repeat(){
    
    let pwd = document.getElementById("passwd").value;
    let pwdRepeat = document.getElementById("checkPasswd").value;

    if(pwd === pwdRepeat){
        $("#pwdCheckRepeat").text(" - 비밀번호를 똑같이 입력해주세요");
        $("#pwdCheckRepeat").css("color", "red");
        pwdRepeatComplete = false;
        formCheck();
    }
    else{
        $("#pwdCheckRepeat").text("");
        pwdRepeatComplete = true;
        formCheck();
    }
}

function formCheck(){
    // 모든 양식들 중 하나라도 규칙을 준수하고 있지 않다면 가입 버튼 비활성화
    if(!idComplete || !pwdComplete || !pwdRepeatComplete || !nicknameComplete || !phoneComplete){
        const btnRegister = document.getElementById("btnRegister");
        btnRegister.disabled = true;
    }
        
    else{
        const btnRegister = document.getElementById("btnRegister");
        btnRegister.disabled = false;
    }
        
}