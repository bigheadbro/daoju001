$(function() {
	$(".hot-question-listitem").each(function(){
		var img = $(this).children(".hidden").children("img");
		var src;
		if(img.length > 0){
			src = img.attr("src");
			$(this).children(".content").children().children(".text").prepend("<img src=\"" + src + "\"/>");
			$(this).children(".content").children().children(".text").children("img").addClass("fl");
			$(this).children(".pa-answer").children(".answer-cont").children("img").addClass("quesiton-img");
		}
		else{
			src = null;
		}
		$(this).children(".content").children().children(".text").children("p").text($(this).children(".hidden").text());
	});
	$(".index-answer").each(function(){
		var img = $(this).children(".hidden").children("img");
		var src;
		if(img.length > 0){
			src = img.attr("src");
			$(this).children(".pa-answer").children(".answer-cont").prepend("<img src=\"" + src + "\"/>");
			$(this).children(".pa-answer").children(".answer-cont").children("img").addClass("fl");
			$(this).children(".pa-answer").children(".answer-cont").children("img").addClass("quesiton-img");
		}
		else{
			src = null;
		}
		$(this).children(".pa-answer").children(".answer-cont").children("p").text($(this).children(".hidden").text());
	});
});