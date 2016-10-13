$(document).ready(function () {

    var $currentForm = $('form[data-load]');

    var $required = $currentForm.find("input[required], textarea[required]");
    var hasRequired = $required.length > 0;
    var $submit_btn = $currentForm.find("input[type=submit], button[type=submit]");

    $(this).find("#clearer").on('click', function () {
        console.log("me in reset click")
        $currentForm.find("input[type!='hidden']").removeAttr("value");
        $currentForm.find("textarea").removeAttr("value");
        if (hasRequired){
        }
    });

    var $minObserved = $required.find("[data-min]");
    $minObserved.on('submit', checkMin);
    $minObserved.on('change', checkMin);

    function checkMin() {
        console.log("checkMin");
        var _$this = $(this);
        var contentLength = _$this.val().length;
        var minVal =  _$this.data("min");
        if (contentLength < minVal){
            showErrorField(_$this);
        } else {
            hideErrorField(_$this);
        }
    }

    function showErrorField($inputField) {
        var errorField = $inputField.nextAll(".s-error");
        errorField.css("visibility", "visible");
        $inputField.addClass("wrong");
    }

    function hideErrorField($inputField) {
        var errorField = $inputField.nextAll(".s-error");
        $inputField.removeClass("wrong");
        errorField.css("visibility", "hidden");
    }

    function disableSubmit() {
        $submit_btn.addClass("disabled").attr("disabled", true);
    }

    function enableSubmit() {
        $submit_btn.removeClass("disabled");
        $submit_btn.attr("disabled", false);
    }


});
