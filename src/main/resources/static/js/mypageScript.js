$(function(){
    let member = memberSession;
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
                    $(".memberDesc").html("<i>이곳을 클릭하면 내용을 작성하러 갈 수 있습니다</i>");
                }
                else if(data === "getSessionFail"){
                    alert("로그인하셔야 이용 가능합니다.");
                }
                else{
                    $(".memberDesc").text(data);
                }               
            })
    }
})

