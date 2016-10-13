$(document).ready(function () {

  var _$answerForm = $(".answerForm");
  var _$commentForm = $(".commentForm");

  _$answerForm.hide();
  _$commentForm.hide();

  $(".newAnswer-btn").on("click", function(){
    _$answerForm.show();
  });

  $(".newComment-btn").on("click", function(){
    $(this).next().show();
  });

  _$answerForm.find(".icon_delete").on("click", function(){
    $(this).parent().hide();
  });

  _$commentForm.find(".icon_delete").on("click", function(){
    $(this).parent().hide();
  });

});