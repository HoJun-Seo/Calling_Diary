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