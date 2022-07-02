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