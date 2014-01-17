$(function() {
	$(".hot-question-listitem").each(function(){
		var img = $(this).children(".hidden").children("img");
		var src;
		if(img.length > 0){
			src = img.attr("src");
			$(this).children(".content").children().children(".text").prepend("<img src=\"" + src + "\"/>");
		}
		else{
			src = null;
		}
		$(this).children(".content").children().children(".text").children("p").text($(this).children(".hidden").text());
	});
});