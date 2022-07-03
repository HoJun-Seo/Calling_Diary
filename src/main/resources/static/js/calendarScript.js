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