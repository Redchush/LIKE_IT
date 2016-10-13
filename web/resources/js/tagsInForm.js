$(document).ready(function () {

    $(".tags_input_item").slice(1).hide();
    $(".tags_input_item").css("border-color", "transparent");
    $(".tags_input").css("max-height", "auto");

    $(document).ready(function () {

        $(".tags_input_item").bind("enterKey", function (e) {
            _$this = $(this);
            var tagName = _$this.find("input").val();
            var element_code = '<div class="tokenWrapper"><div class="tokenContainer"><span>' + tagName.toLocaleLowerCase() + '</span><span class="remove visible"><em></em></span></div></div>';
            _$this.prepend(element_code);
            _$this.next().find("input").focus();

            _$this.find(".remove").on('click', function () {
                _$this.find(".tokenWrapper").remove();
                _$this.find("input").val("").show();
            });
        });

        $(".tags_input_item").find("input").on("keypress", function (e) {
            if (e.keyCode == 13) {
                $(this).trigger("enterKey");
                var _$next = $(this).parent().next().show();
                $(this).hide();
                e.stopPropagation();
            }
        });

    });

});

