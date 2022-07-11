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