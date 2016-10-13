(function () {

    $(document).ready(function () {

        $("input[type=file]").on("change", function (e) {
            var path = $(this).val();
            $(this).next().children("span").text(path);
            $(this).next().show();
        });

    });
}());