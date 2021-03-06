$(function(){
    $("#myModal").modal({
        show : true,
        backdrop : false,
        keyboard : false
    });
})


/* 회원가입 관련 함수 */
function chk() {
    let req1 = $("#req1").is(":checked");
    let req2 = $("#req2").is(":checked");

    if(req1 == true && req2 == true) {
        window.close();
        opener.location.href = "/registerForm";
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
let idComplete_new = false;
let pwdComplete = false;
let pwdRepeatComplete = false;
let pwdConfirmComplete = false;
let nicknameComplete = false;
let phoneComplete = false;
let phoneConfirmComplete = false;

// 아이디 입력 값 규칙준수 여부 확인
function checkId_pattern(){

    let userid = document.getElementById("userId").value;
    const btnIdOverlap = document.getElementById("btnIdOverlap");
    const btnfindPwd = document.getElementById("btnfindPwd");

    fetch("/patterns/userid", {
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

            // 회원가입에서 요청이 넘어온 경우
            if(btnIdOverlap !== null){
                btnIdOverlap.disabled = true;
            }
            formCheck();
        }
        else{
            // 회원가입에서 요청이 넘어온 경우
            if(btnIdOverlap !== null){
                btnIdOverlap.disabled = false;
            }
            $("#idCheck").text("");
            idComplete = true;

            // 비밀번호 찾기에서 요청이 넘어온 경우
            if(btnfindPwd !== null){
                formCheck();
            }
        }
     });
}

function checkcurId_pattern(){

    let userid = document.getElementById("curUID").value;

    fetch("/patterns/userid", {
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
           
           $("#curidCheck").text(" - 아이디 형식이 잘못 되었습니다.");
           $("#curidCheck").css("color","red");
           idComplete_new = false;

           formCheck();
       }
       else{
           $("#curidCheck").text("");
           idComplete_new = true;
       }
    });
}

function checkId_overlap(){
    // DB 접근을 통해 아이디 중복 여부 확인
    let userid = document.getElementById("userId").value;

    fetch("/patterns/reduplication/userid", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
           "userid":userid
        })
    })
    .then((response) => response.text())
    .then((data) => {

        if(data === "possbleId"){
            $("#idCheck").text(" - 사용가능한 아이디 입니다.");
            $("#idCheck").css("color", "blue");
            idComplete = true;    
        }
        else if(data === "impossbleId"){
            $("#idCheck").text(" - 이미 존재하는 아이디 입니다.");
            $("#idCheck").css("color", "red");
            idComplete = false;
        }
        formCheck();
    })
}

// 비밀번호 입력값 규칙준수 여부 확인
function checkPwd_pattern(){

    let pwd = document.getElementById("passwd").value;
    const pwdRepeat = document.getElementById("checkPasswd");
    const confirmPwd = document.getElementById("confirmPwd");

    fetch("/patterns/pwd", {
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
            
            if(pwdRepeat !== null){
                pwdRepeat.disabled = true;
            }
            
            pwdComplete = false;

            if(confirmPwd !== null){
                confirmPwd.disabled = true;
            }
            formCheck();
        }
        else{
            $("#pwdCheck").text("");
            if(pwdRepeat !== null){
                pwdRepeat.disabled = false;
            }
            
            pwdComplete = true;

            if(confirmPwd !== null){
                confirmPwd.disabled = false;
            }
            formCheck();
        }
    })
}

// 비밀번호 확인 검증
function checkPwd_repeat(){
    
    let pwd = document.getElementById("passwd").value;
    let pwdRepeat = document.getElementById("checkPasswd").value;

    if(pwd !== pwdRepeat){
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

function checkNickname_pattern(){
    let nickname = document.getElementById("nickname").value;

    fetch("/patterns/nickname", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "nickname":nickname
        })
    })
    .then((response) => response.text())
    .then((checkStatus) => {
        if(checkStatus === "false"){
            $("#nicknameCheck").text(" - 닉네임 형식이 잘못 되었습니다.");
            $("#nicknameCheck").css("color", "red");
            nicknameComplete = false;
            formCheck();
        }
        else{
            $("#nicknameCheck").text("");
            nicknameComplete = true;
            formCheck();
        }
    })
}


let checkNumber = "";
// 본인인증 처리 함수

// 입력 전화번호 패턴 검증 함수
function checkPhoneNumber_pattern(target){

    let phoneNumber = document.getElementById("phoneNumber").value;
    const btnCertificate = document.getElementById("btnCertificate");

    fetch("/patterns/phonenumber", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "phoneNumber":phoneNumber
        })
    })
    .then((response) => response.text())
    .then((checkStatus) => {
        if(checkStatus === "true"){
            checkPhoneNumber_overlap(phoneNumber, target);
            
        }
        else{
            $("#phonNumber_patternCheck").text(" - 전화번호 형식이 잘못 되었습니다.");
            $("#phonNumber_patternCheck").css("color", "red");
            phoneComplete = false;
            btnCertificate.disabled = true;
            formCheck();
        }
    })
}

async function checkPhoneNumber_overlap(phoneNumber, target){

    await fetch("/patterns/reduplication/phonenumber", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "phoneNumber":phoneNumber
        })
    })
    .then((response) => response.text())
    .then((checkStatus) => {

        if(checkStatus === "false"){
            if(target === "register"){
                $("#phonNumber_patternCheck").text("");
                phoneComplete = true;
                btnCertificate.disabled = false;
            }
            else if(target === "findAccount"){
                $("#phonNumber_patternCheck").text(" - 전화번호가 등록되어 있지 않습니다.");
                $("#phonNumber_patternCheck").css("color", "red");
                phoneComplete = false;
                btnCertificate.disabled = true;
                formCheck();
            }
            
        }
        else{
            if(target === "register"){
                $("#phonNumber_patternCheck").text(" - 이미 등록되어 있는 전화번호 입니다.");
                $("#phonNumber_patternCheck").css("color", "red");
                phoneComplete = false;
                btnCertificate.disabled = true;
                formCheck();
            }
            else if(target === "findAccount"){
                $("#phonNumber_patternCheck").text("");
                btnCertificate.disabled = false;
            }
        }
    })

    
}
function certificate(){

    let phoneNumber = document.getElementById("phoneNumber").value;

    fetch("/members/sms/phonenumber", {
        method:"post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "phoneNumber":phoneNumber
        })
    })
    .then((response) => response.text())
    .then((returnNumber) => {
        $("#phonNumber_patternCheck").text("");
        alert("문자가 발송 되었습니다. 인증번호를 입력해주세요");
        checkNumber = returnNumber;

        // 프로젝트 완성 때까지 인증번호 자동 입력, 프로젝트 완성 이후 삭제할 것
        $("#checkNumber").attr("value", checkNumber);
    })
}

function checkNumberConfirm(){

    let inputNumber = document.getElementById("checkNumber").value;

    if(inputNumber === checkNumber){
        $("#phoneNumberCheck").text("");
        $("#phoneNumber").attr("readonly", true);
        $("#checkNumber").attr("readonly", true);
        alert("인증이 완료 되었습니다.");
        phoneConfirmComplete = true;

        formCheck();
    }
    else{
        $("#phoneNumberCheck").text(" - 인증번호가 일치하지 않습니다.");
        $("#phoneNumberCheck").after("<br>");
        $("#phoneNumberCheck").text(" - 인증번호를 다시 요청하거나, 전화번호를 잘못 입력하지는 않았는지 확인해주세요");
        $("#phoneNumberCheck").css("color", "red");
        phoneConfirmComplete = false;
        formCheck();
    }

}

function formCheck(){

    if(!idComplete || !idComplete_new || !pwdComplete || !pwdRepeatComplete || !nicknameComplete || !phoneComplete || !phoneConfirmComplete || !pwdConfirmComplete){
        const btnRegister = document.getElementById("btnRegister");
        const btnfindId = document.getElementById("btnfindId");
        const btnfindPwd = document.getElementById("btnfindPwd");
        const btnnewPwd = document.getElementById("btnnewPwd");
        const btnIdUpdate = document.getElementById("btnIdUpdate");
        const btnNicknameUpdate = document.getElementById("btnNicknameUpdate");
        const btnPhoneNumberUpdate = document.getElementById("btnPhoneNumberUpdate");
        const btnsecession = document.getElementById("btnsecession");

        if(btnRegister !== null){
            if(!idComplete || !pwdComplete || !pwdRepeatComplete || !nicknameComplete || !phoneComplete || !phoneConfirmComplete){
                btnRegister.disabled = true;
            }
            else{
                btnRegister.disabled = false;
            }
            
        }
        // 아이디 찾기에서 요청이 들어온 경우(아이디, 비밀번호, 닉네임측 요청이 존재하지 않기 때문에 이 셋은 반드시 fasle 이므로 해당 영역에 코드를 작성한다.)
        if(btnfindId !== null){
            // 전화번호 형식이 잘못 되었거나 입력한 인증번호가 일치하지 않는 경우
            
            if(!phoneComplete){
                btnfindId.disabled = true;
            }
            else{
                btnfindId.disabled = false;
            }
        }
        // 비밀번호 찾기에서 요청이 들어온 경우
        if(btnfindPwd !== null){
            if(!idComplete || !phoneComplete){
                btnfindPwd.disabled = true;
            }
            else{
                btnfindPwd.disabled = false;
            }
        }
        
        if(btnnewPwd !== null){
            if(!pwdComplete || !pwdRepeatComplete){
                btnnewPwd.disabled = true;
            }
            else{
                btnnewPwd.disabled = false;
            }
        }

        // 아이디 변경에서 요청이 들어온 경우
        if(btnIdUpdate !== null){
            if(!idComplete || !idComplete_new){
                btnIdUpdate.disabled = true;
            }
            else{
                btnIdUpdate.disabled = false;
            }
        }

        // 닉네임 변경에서 요청이 들어온 경우
        if(btnNicknameUpdate !== null){
            if(!nicknameComplete){
                btnNicknameUpdate.disabled = true;
            }
            else{
                btnNicknameUpdate.disabled = false;
            }
        }

        // 전화번호 변경에서 요청이 들어온 경우
        if(btnPhoneNumberUpdate !== null){
            if(!phoneComplete || !phoneConfirmComplete){
                btnPhoneNumberUpdate.disabled = true;
            }
            else{
                btnPhoneNumberUpdate.disabled = false;
            }
        }

        if(btnsecession !== null){
            if(!pwdConfirmComplete){
                btnsecession.disabled = true;
            }
        }
    }
}

/* 로그인 함수 */
function login(){

    let userid = $("#userId").val();
    let passwd = $("#passwd").val();
    if(userid === ""){
        alert("아이디를 입력해주세요");
    }
    else if(passwd === ""){
        alert("비밀번호를 입력해주세요");
    }
    else{
        // 가입되어 있는 계정인지 아닌지 판별 후 결과에 따라 기능 수행
        $("#loginForm").submit();
    }
}/* 로그아웃 함수 */

function logout(){
    fetch('/members/logout', {
        method:'delete'
    })
    .then((response) => response.text())
    .then((data) => {
        if(data === "logoutSuccess"){
            location.href="/";
        }
    });
}