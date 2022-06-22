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
                    $(".writeDesc").click(function(){
                        $(".memberDesc").html("<textarea class=\"writeDescArea\" style=\"width: 100%; height:70px;\" placeholder=\"최대 100자까지 작성할 수 있습니다.\">"
                                                +"</textarea><div class=\"wordCount\">(0 / 100)</div>"
                                                +"<div class=\"descBtn\"><button class=\"btn btn-primary\" style=\"width: 47%\" onclick=\"writeDesc()\">작성하기</button>&nbsp;<button class=\"btn btn-primary\" style=\"width: 47%\" onclick=\"writeDescCancel()\">취소</button></div>");
                        $('.writeDescArea').on('keyup', function() {
                                $('.wordCount').html("("+$(this).val().length+" / 100)");

                                if($(this).val().length > 100) {
                                    $(this).val($(this).val().substring(0, 100));
                                    $('.wordCount').html("(100 / 100)");
                                }
                        });
                    });
                }
                else if(data === "getSessionFail"){
                    alert("로그인하셔야 이용 가능합니다.");
                }
                else{
                    $(".memberDesc").html("<a href=\"#\" class=\"writeDesc\">"+data+"</a>");
                }               
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

