$(document).ready(function () {

    $("input[type=radio]").hide();
    var _$output = $("output");
    $(".cat_box").find("label").on("click", function () {
        $(this).prev().prop("checked", true);
        var text =  $(this).text();
        _$output.text(text);
    });
});