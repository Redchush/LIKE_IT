var UTIL = (function() {

    return {
        changeSortBtn: function(sortValue, formId) {
            var idString = '#' + formId;
            var valueString = "[value='" + sortValue + "']";
            $(idString).find(valueString).addClass("active");
        },

        showErrorField : function (nameValue, formId) {
            var idString = '#' + formId;
            var valueString = "[name='" + nameValue + "']";
            $(idString).find(valueString).css("visibility", "visible");
        }
    };

}());
