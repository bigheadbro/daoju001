$(function() {
	$(".hot-question-listitem").each(function(){
		var tmp = $(this).children(".hidden").html();
		$(this).children(".content").children().children(".text").children("p").html(tmp);
	});
	$(".question-item").each(function(){
		var tmp = $(this).children(".hidden").html();
		$(this).children(".info").children().children(".text").children("p").html(tmp);
	});
	$(".index-answer").each(function(){
		var img = $(this).children(".hidden").children("img");
		var src;
		if(img.length > 0){
			src = img.attr("src");
			$(this).children(".pa-answer").children(".answer-cont").append("<a class=\"fancybox-v\" href=\"" + src + "\"><img \" src=\"" + src + "\"/></a>");
			$(this).children(".pa-answer").children(".answer-cont").children("a").addClass("fl");
			$(this).children(".pa-answer").children(".answer-cont").children().children("img").addClass("quesiton-img");
		}
		else{
			src = null;
		}
		$(this).children(".pa-answer").children(".answer-cont").children("p").text($(this).children(".hidden").text());
	});
});