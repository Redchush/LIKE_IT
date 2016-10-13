$(document).ready(function () {

    $('.tokenWrapper').find("input[type=checkbox]")
        .css("visibility","hidden");

    var _$initialChosen = $(".chosenTags").find("li");
    var _$initialPossible = $(".possibleTags").find("li");

    var init_id = $(this).find("[name='prev_ids']").val();
    var init_id_array = [];
    if (init_id !== undefined){
        init_id_array = init_id.split(",");
    }
    _$initialPossible.each(function () {
        var _$currentBox = $(this).find("input[type='checkbox']");
        var value = _$currentBox.val();
        if (  init_id_array.some(containsVal)  ){
            _$currentBox.attr("name", "tag_old");
            $(this).attr("data-initial", "true");
        }
       function containsVal(element, index, array) {
           return element === value;
       }
    });

    var _$submit_btn =$("#tag_filter")
        .find("button[type=submit]");
    var _$chosen = $(".chosenTags").find("ul");


    $(".remove").on("click", function(evt) {
        var _$possible = $(".possibleTags").find("ul");
        var _$tag = $(this).parent().parent().data("state", "possible");
        _$tag.find("input").prop("checked", false);
        searchPlace(_$possible, _$tag);
        var children_count = _$chosen.children().length;
        evt.stopPropagation();
    });

    _$initialChosen.on("click", moveToChosen);

    _$initialPossible.on("click", moveToChosen);
    function moveToChosen(){
        var _$this = $(this);
        var  isInitial = _$this.data("initial") === true;
        var _$chosen = isInitial ? $(".chosenTags").find("ul[data-initial]") : $(".chosenTags").find("ul[data-temporal]");
        var state = _$this.data("state");
        var pattern = /possible/;
        console.log("moved");
        if (pattern.test(state)) {
            _$this.find("input").prop("checked", true);
            searchPlace(_$chosen, _$this);
            _$this.data("state", "chosen");
        }
    }

    $(".saveChosen").on("submit", function () {
        $(".chosenTags").find("input[type='checkbox']").clone().appendTo($(this));
    });

    function searchPlace(_$parent, _$tag){
        var position = _$tag.data("place");
        var allLI = _$parent.find("li");
        var result = -1;
        allLI.each(function(){
            var tagPlace = $(this).data("place");
            console.log(tagPlace + " seach place for " + position);
            if (tagPlace < position) {
                result = $(this);
                return false;
            }
        });
        if (result === -1) {
            _$parent.append(_$tag);
        } else{
            result.before(_$tag);
        }
    }

    $('.toggle_nav_btn').on('click', function(){
        $(this)
            .toggleClass("open")
            .toggleClass("close");
        $(".nav_options").fadeToggle();
    });
});