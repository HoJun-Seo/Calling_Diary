function chk() {
    var form = document.form;
    var req1 = form.req1.checked;
    var req2 = form.req2.checked;
    var flag = 0;
    if(req1 == true && req2 == true) {
        flag = 1;
    }
    if(flag == 1) {
        window.close();
        form.method = "get";
        form.action = "/move/registerForm";
        form.target = "self";
        form.submit();

    }else {
        alert("필수 약관에 동의하셔야 합니다.");
    }
}

function nochk(){
    alert("동의하지 않으면 가입하실 수 없습니다.");
    window.close();
}