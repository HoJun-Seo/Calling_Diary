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
$(function(){
    $("#myModal").modal({
        show : true,
        backdrop : false,
        keyboard : false
    });
})