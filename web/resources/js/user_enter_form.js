$(document).ready(function() {

    var $currentForm = $('form[data-load]');
    var contentKey = $currentForm.data("load");

    var $submit_btn = $currentForm.find("input[type=submit], button[type=submit]");

    $submit_btn.addClass("disabled");

    var $email_field = $currentForm.find("input[type=email]");
    var $pas_field =  $currentForm.find("input[type=password]");
    var $login_field = $currentForm.find("input[type=text]");

    var $required = $currentForm.find("input[required]");
    var hasRequired = $required.length > 0;

    $(this).find("#clearer").on('click', function () {
        console.log("me in reset click");
        $currentForm.find("input[type!='hidden']").removeAttr("value");
        $currentForm.find("textarea").removeAttr("value");
        if (hasRequired){
        }
    });

    $email_field.on("change", checkEmail);
    function checkEmail() {
//        console.log("checking email");
        var _$this = $(this);
        var $email = _$this.val();
        var isValid = isEmailValid($email);
        processValidationResult(isValid, _$this);
    }

    var emailReg = /^([-.\w]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    function isEmailValid(email){
        return emailReg.test(email);
    }

    var loginReg = /^[\w-]{3,20}$/;

    $login_field.on('change', checkLogin);

    function checkLogin() {
        var login = $(this).val();
        console.log(login);
        console.log(loginReg.test(login));
        var isValid = isLoginValid(login);
        processValidationResult(isValid, $(this));
    }

    function isLoginValid (login) {
        return login !== undefined && login.length > 2 && loginReg.test(login);
    }

    var passReg = /^[\w-]{3,20}$/;
    $pas_field.on("change", checkPassword);
    function checkPassword() {
        var password = $(this).val();
        var isValid = isPasswordValid(password);
        return processValidationResult(isValid, $(this));
    }

    function isPasswordValid(password){
        return password.length > 5 && passReg.test(password);
    }

    function processValidationResult(isValid, _$this){
        if (!isValid) {
            showErrorField(_$this)
        } else {
            hideErrorField(_$this)
        }
        return isValid;
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

    $currentForm.on("submit", function(evt) {
        console.log("here");
        var isValid = true;
        $email_field.each(function(){
            var is = isEmailValid( $(this).val().toString());
            if (!is) {
                isValid = false;
            }
        });
        $pas_field.each(function(){
            var is = isPasswordValid($(this).val().toString());
            if (!is) {
                isValid = false;
            }
        });
        if (!isValid) {
            evt.stopPropagation();
            return false;
        }
    });

    $currentForm.on("change", checkForm);
    $currentForm.on("keyup", checkForm);

    function checkForm(){
        var isAll = true;
        $required.each(function() {
            var len = $(this).val().toString();
            if (len < 1) {
                isAll = false;
            }
            if ($(this).hasClass("wrong")) {
                isAll = false;
            }
        });
        if (isAll) {
            enableSubmit();
        } else {
            disableSubmit();
        }
    }

    function disableSubmit() {
        $submit_btn.addClass("disabled").attr("disabled", true);
    }

    function enableSubmit() {
        $submit_btn.removeClass("disabled");
        $submit_btn.attr("disabled", false);
    }

});

