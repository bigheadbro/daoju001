$(function() {
	$(".hot-question-listitem").each(function(){
		var tmp = $(this).children(".hidden").html().replace(/\<img.*\B\'\>/ig, "");
		$(this).children(".content").children().children(".text").children("p").html(tmp);
	});
	$(".question-item .info .text img").each(function(){
		
		$(this).replaceWith("");
	});
});