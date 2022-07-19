var member = memberSession;

$(function(){

    $('.eventDesc').on('keyup', function() {
        $('.wordCount').html("("+$(this).val().length+" / 100)");

        if($(this).val().length > 100) {
            $(this).val($(this).val().substring(0, 100));
            $('.wordCount').html("(100 / 100)");
        }
    });
})


let titleComplete = false;
let descComplete = false;

function titleSpaceCheck(){

    const reg = /\s/g;

    let title = $("#eventTitle").val();

    title = title.replace(reg, "");

    if(title.length === 0){
        titleComplete = false;
        eventFormCheck();
    }
    else{
        titleComplete = true;
        eventFormCheck();
    }
}

function descSpaceCheck(){

    const reg = /\s/g;  

    let desc = $("#eventDesc").val();

    desc = desc.replace(reg, "");

    if(desc.length === 0){
        descComplete = false;
        eventFormCheck();
    }
    else{
        descComplete = true;
        eventFormCheck();
    }
}

function eventFormCheck(){

    const btnEventCreate = document.getElementById("btnCreateEvent");

    if(!titleComplete || !descComplete){

        if(btnEventCreate !== null){
            btnEventCreate.disabled = true;
        }
    }
    else{
        btnEventCreate.disabled = false;
    }
}

function callEvent(){

    fetch("/events/" + member.uid)
        .then((response) => response.text())
        .then((data) => {
            if(data === "eventNotExist"){
                $(".eventPrint").html("<h4 class=\"title\" style=\"color:yellow\">작성한 일정이 없습니다.</h4>");
            }
            else{
                let event = JSON.parse(data);

                $(".eventPrint").append("<ul>");
                if(event.length > 5){
                    for(let i = event.length-1; i >= event.length-5; i--){
                        $(".eventPrint").append("<li class=\"eventList\"><div class=\"panel panel-default eventBody\"><div class=\"panel-body\" id=\"eventPanel" + (event[i].eventid).toString() + "\">" + event[i].start + " ~ " + event[i].end + " <br>" + event[i].title + "</div></div></li>");
                        $("#eventPanel"+(event[i].eventid).toString()).click(()=>{
                            $("#eventModal").modal('show');
                            $("#startDate").val(event[i].start);
                            $("#endDate").val(event[i].end);
                            $("#eventTitle").val(event[i].title);
                            $("#eventDesc").val(event[i].eventdesc);
                        });
                    }
                    $(".eventPrint").append("</ul>");
                }
                else if(event.length <= 5){
                    for(let i = event.length-1; i >= 0; i--){
                        $(".eventPrint").append("<li class=\"eventList\"><div class=\"panel panel-default eventBody\"><div class=\"panel-body\" id=\"eventPanel" + (event[i].eventid).toString() + "\">" + event[i].start + " ~ " + event[i].end + " <br>" + event[i].title + "</div></div></li>");
                        $("#eventPanel"+(event[i].eventid).toString()).click(()=>{
                            $("#eventModal").modal('show');
                            $("#startDate").val(event[i].start);
                            $("#endDate").val(event[i].end);
                            $("#eventTitle").val(event[i].title);
                            $("#eventDesc").val(event[i].eventdesc);
                        });
                    }
                    $(".eventPrint").append("</ul>");
                }
                       
            }
        })
}

function callEventMypage() {

    fetch("/events/" + member.uid)
        .then((response) => response.text())
        .then((data) => {
            if(data === "eventNotExist"){
                $(".eventPrint").html("<h4 class=\"title\">작성한 일정이 없습니다.</h4>");
            }
            else{
                let event = JSON.parse(data);

                $(".eventPrint").append("<ul>");
                if(event.length > 5){
                    for(let i = event.length-1; i >= event.length-5; i--){
                        $(".eventPrint").append("<li class=\"eventListMypage\"><div class=\"panel panel-default\"><div class=\"panel-body\" id=\"eventPanel" + (event[i].eventid).toString() + "\">" + event[i].start + " ~ " + event[i].end + "<br>" + event[i].title + "</div></div></li>");
                        $("#eventPanel"+(event[i].eventid).toString()).click(()=>{
                            $("#eventModal").modal('show');
                            $("#startDate").val(event[i].start);
                            $("#endDate").val(event[i].end);
                            $("#eventTitle").val(event[i].title);
                            $("#eventDesc").val(event[i].eventdesc);
                        });
                    }
                    $(".eventPrint").append("</ul>");
                }
                else if(event.length <= 5){
                    for(let i = event.length-1; i >= 0; i--){
                        $(".eventPrint").append("<li class=\"eventListMypage\"><div class=\"panel panel-default\"><div class=\"panel-body\" id=\"eventPanel" + (event[i].eventid).toString() + "\">" + event[i].start + " ~ " + event[i].end + "<br>" + event[i].title + "</div></div></li>");
                        $("#eventPanel"+(event[i].eventid).toString()).click(()=>{
                            $("#eventModal").modal('show');
                            $("#startDate").val(event[i].start);
                            $("#endDate").val(event[i].end);
                            $("#eventTitle").val(event[i].title);
                            $("#eventDesc").val(event[i].eventdesc);
                        });
                    }
                    $(".eventPrint").append("</ul>");
                }
                       
            }
        })
}

function callEventMypageFull(){

    fetch("/events/" + member.uid)
        .then((response) => response.text())
        .then((data) => {

            if(data === "eventNotExist"){
                $(".eventPrintFullTab").html("<h4 class=\"title\">작성한 일정이 없습니다.</h4>");
            }
            else{

                let event = JSON.parse(data);

                // 배열의 길이에 따라서 페이지의 갯수가 달라져야 한다.
                // 각 번호를 클릭 했을 때 기존의 링크는 비활성화 되고 클릭한 링크는 활성화 되어야 한다.
                // 작성되어 있는 일정은 모두 가져온 상태이므로, 배열의 길이에 따라 각 페이지에서 다르게 출력 시켜줘야 한다.
                let pageCount = event.length/5;
                if(pageCount <= 1)
                    pageCount = 1;
                else
                    pageCount = Math.ceil(pageCount);

                $(".eventPrintFull").append("<li><a href=\"#\" onclick=\"eventList("+(1).toString()+")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
                for(let index = 1; index <= pageCount; index++){
                    // a 태그의 링크를 통해 각 페이지에 맞는 일정들을 출력해줘야 한다.
                    $(".eventPrintFull").append("<li class=\""+index.toString()+"\"><a href=\"javascript:eventList("+index.toString()+");\">" + index.toString() + "</a></li>")
                }

                eventList("1");
                $(".eventPrintFull").append("<li><a href=\"#\" onclick=\"eventList("+pageCount.toString()+")\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
            }
        })
}

function eventList(index){

    fetch("/events/" + member.uid)
        .then((response) => response.text())
        .then((data) => {

            if(data === "eventNotExist"){
                $(".eventPrintFullTab").html("<h4 class=\"title\">작성한 일정이 없습니다.</h4>");
            }
            else{
                fetch("/events/pagination/",{
                    method:"post",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        "event":data,
                        "index":Number(index)
                    })
                })
                .then((response) => response.text())
                .then((data) => {
                    $(".eventPrintFullTab").html(data);
                });
            }
        })
}

function callEventMypageFav(){

    fetch("/events/" + member.uid + "/favorite")
        .then((response) => response.text())
        .then((data) => {

            if(data === "eventNotExist"){
                $(".eventPrintFavTab").html("<h4 class=\"title\">작성한 일정이 없습니다.</h4>");
            }
            else{

                let event = JSON.parse(data);

                let pageCount = event.length/5;
                if(pageCount <= 1)
                    pageCount = 1;
                else
                    pageCount = Math.ceil(pageCount);

                $(".eventPrintFav").append("<li><a href=\"#\" onclick=\"eventList("+(1).toString()+")\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
                for(let index = 1; index <= pageCount; index++){
                    // a 태그의 링크를 통해 각 페이지에 맞는 일정들을 출력해줘야 한다.
                    $(".eventPrintFav").append("<li class=\""+index.toString()+"\"><a href=\"javascript:eventListFav("+index.toString()+");\">" + index.toString() + "</a></li>")
                }

                eventListFav("1");
                $(".eventPrintFav").append("<li><a href=\"#\" onclick=\"eventList("+pageCount.toString()+")\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
            }
        })
}

function eventListFav(index){

    fetch("/events/" + member.uid + "/favorite")
        .then((response) => response.text())
        .then((data) => {
            if(data === "eventNotExist"){
                $(".eventPrintFavTab").html("<h4 class=\"title\">작성한 일정이 없습니다.</h4>");
            }
            else{
                fetch("/events/pagination/favorite",{
                    method:"post",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        "event":data,
                        "index":Number(index)
                    })
                })
                .then((response) => response.text())
                .then((data) => {
                    $(".eventPrintFavTab").html(data);
                });
            }
        })
}

function eventUpdate(){

    let title = document.getElementById("eventTitle").value;
    let desc = document.getElementById("eventDesc").value;
    let start = document.getElementById("startDate").value;
    let end = document.getElementById("endDate").value;
    let eventid = document.getElementById("eventid").value;
    const favorite = document.getElementById("favorite");
    const isChecked = favorite.checked;

    // 각 필드에 대한 입력 여부는 서버 단에서 판단
    // 버튼 비활성화는 다루기가 까다로워 방향 변경
    fetch("/events/"+member.uid, {
        method:"PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "eventid":eventid,
            "title":title,
            "eventdesc":desc,
            "start":start,
            "end":end,
            "favoriteCheck":isChecked
        })
    })
    .then((response) => response.text())
    .then((data) => {

        // 입력이 올바르지 않은 경우
        if(data === "textInputError"){
            alert("제목, 설명은 필수로 입력하세야 합니다!");
        }
        else if(data === "dateinputError"){
            alert("날짜 입력이 잘못 되었습니다!");
        }
        else if(data === "updateSuccess"){
            alert("일정이 정상적으로 수정 완료 되었습니다.");
            location.reload();
        }
        else if(data === "sessionNotExist"){
            alert("로그인 하신 후 이용할 수 있습니다. 로그인 페이지로 이동합니다.");
            moveLogin();
        }
    })
}

function eventDelete(){

    if(confirm("정말로 삭제 하시겠습니까?") === true){
        let eventid = document.getElementById("eventid").value;

        fetch("/events/"+member.uid+"/"+eventid, {
            method:'delete'
        })
        .then((response) => response.text())
        .then((data) => {
            if(data === "deleteSuccess"){
            alert("일정이 성공적으로 삭제 되었습니다.");
            location.reload();
            }
            else if(data === "sessionNotExist"){
            alert("로그인 후 이용해주세요");
            moveLogin();
            }
            else if(data === "eventNotExist"){
            alert("해당 일정은 이미 존재하지 않습니다.");
            }
        })
    }
}

function smsReservationPage(){

    fetch("/events/" + member.uid + "/favorite")
    .then((response) => response.text())
    .then((data) => {

        const event = JSON.parse(data);

        for(let i = event.length-1; i >= 0 ; i--){
            let start = event[i].start;
            $(".smsReservation").append("<li>"+event[i].title + " ("+start + "~" + event[i].end + ")</li>");
            $(".smsReservation").append("<li class=\"smsDesc\">"+event[i].eventdesc + "</li><br>");
            if(event[i].sms === "yes"){
                $(".smsReservation").append("<li><input type=\"button\" class=\"btn btn-primary\" value=\"알림 보기\" onclick=\"smsDetail()\"></li><hr>");
            }
            else{
                $(".smsReservation").append("<li><input type=\"button\" class=\"btn btn-primary\" value=\"설정\" onclick=\"smsModalOpen("+ event[i].eventid + ")\"></li><hr>");
            }
        }
    });
}

function smsModalOpen(eventid){
    $("#smsModal").modal("show");
    $("#smsEventid").val(eventid);
}

function smsRegister(){

    const eventid = $("#smsEventid").val();
    const reservationTime = $("#reservationTime").val();
    const messageText = $("#messageText").val();

    console.log(eventid);

    if(reservationTime.length === 0){
        alert("알림 시간은 필수로 입력 하셔야 합니다.")
    }
    if(messageText.length == 0){
        alert("메시지 내용은 필수로 입력 하셔야 합니다.")
    }

    if(reservationTime.length !== 0 && messageText.length !== 0){

        fetch("/sms/" + member.uid , {
            method:"post",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "eventid":eventid,
                "reservationTime":reservationTime,
                "messageText":messageText
            })
        })
        .then((response) => response.text())
        .then((data) => {
            if(data === "sessionNotExist"){
                alert("로그인 후 이용하실 수 있습니다. 로그인 창으로 이동합니다.");
                moveLogin();
            }
            else if(data === "eventNotExist"){
                alert("존재하지 않는 일정입니다.");
                location.reload();
            }
            else if(data === "reservationError"){
                alert("문자 발송 예약이 정상적으로 수행되지 않았습니다.");
                location.reload();
            }
            else{
                fetch("/events/sms", {
                    method:"PATCH",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        "eventid":eventid,
                    })
                })
                .then((response) => response.text())
                .then((data) => {
                    if(data === "updateSuccess"){
                        alert("알림이 정상적으로 등록 되었습니다.");
                        $("#smsModal").modal("hide");
                    }
                    else if(data === "eventNotExist"){
                        alert("존재하지 않는 일정입니다.");
                        location.reload();
                    }
                })
            }
        })
    }
}

function smsCancel(){

}