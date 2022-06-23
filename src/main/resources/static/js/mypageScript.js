var member = memberSession;

$(function(){

    if(member !== null){
        fetch("/member/login")
            .then((response) => response.text())
            .then((data) => {
                $(".headerDIV").html(data);
            });
        
        fetch("/member/desc")
            .then((response) => response.text())
            .then((data) => {
                if(data === "notExistDesc"){         
                    $('[data-toggle="popover"]').popover();
                    $(".memberDesc").html("<a href=\"#\" class=\"writeDesc\">이곳을 클릭하면 자신을 간략하게 표현할 수 있습니다</a>");
                }
                else if(data === "getSessionFail"){
                    alert("로그인하셔야 이용 가능합니다.");
                }
                else{
                    $(".memberDesc").html("<a href=\"#\" class=\"writeDesc\">"+data+"</a>");
                }
                
                $(".writeDesc").click(function(){
                    $(".memberDesc").html("<textarea class=\"writeDescArea\" style=\"width: 100%; height:70px;\" placeholder=\"최대 100자까지 작성할 수 있습니다.\">"
                                            +"</textarea><div class=\"wordCount\">(0 / 100)</div>"
                                            +"<div class=\"descBtn\"><button class=\"btn btn-primary\" style=\"width: 47%\" onclick=\"writeDesc()\">작성하기</button>&nbsp;"
                                            +"<button class=\"btn btn-primary\" style=\"width: 47%\" onclick=\"writeDescCancel()\">취소</button></div>");
                    $('.writeDescArea').on('keyup', function() {
                            $('.wordCount').html("("+$(this).val().length+" / 100)");
    
                            if($(this).val().length > 100) {
                                $(this).val($(this).val().substring(0, 100));
                                $('.wordCount').html("(100 / 100)");
                            }
                    });
                });
            })

            
    }
})

function writeDescCancel(){
    location.reload();
}

function writeDesc(){
    let desc = $(".writeDescArea").val();
    member.memberdesc = desc;

    fetch('/member/writedesc', {
        method:"put",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "userid":member.userid,
            "memberdesc":member.memberdesc
        })
    })
    .then((response) => response.text())
    .then((data) => {
        alert("수정이 완료 되었습니다.");
        location.reload();
    });
}

function updateId(){
    let curUID = $("#curUID").val();
    let userid = $("#userId").val();

    fetch("/member/updateId", {
        method:"put",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "curUID":curUID,
            "userid":userid
        })
    })
    .then((response) => response.text())
    .then((data) => {
        if(data === "curIdNotcorrect"){
            alert("현재 로그인한 아이디와 입력하신 현재 아이디가 일치하지 않습니다.");
            moveUidChange();
        }
        else if(data === "updateIdSuccess"){
            alert("아이디가 변경 되었습니다. 변경된 아이디로 다시 로그인해주세요");
            logout();
        }
        else if(data === "sessionNotExist"){
            alert("로그인 하신 후 이용할 수 있습니다. 로그인 페이지로 이동합니다.");
            moveLogin();
        }
    })
}

function updatePwd(){

    let userid = $("#userid").val();
    let passwd = $("#passwd").val();

    console.log(userid);
    console.log(passwd);
    fetch("/member/newPwdSet", {
        method:"put",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "userid":userid,
            "passwd":passwd
        })
    })
    .then((response) => response.text())
    .then((data) => {
        if(data === "findPwdSucces"){
            if(member === null){
                location.href="/move/findPwdSuccesPage";
            }
            else if(member !== null){
                alert("비밀번호 변경이 완료 되었습니다. 다시 로그인 해주세요");
                logout();
            }
        }
    })
}

