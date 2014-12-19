$(function() {
	$(".a-zlist a").click(function(){
		$(this).parent().children("a").each(function(){
			$(this).removeClass("current");
		});
		$(this).addClass("current");
		var current = $(this).text();
		$(".provider-list-content h1").each(function(){
			if($(this).text() == current){
				$('html, body').animate({
					scrollTop: $(this).offset().top-30
				},1000);

			}
		});
		$(".agent-list-content h1").each(function(){
			if($(this).text() == current){
				$('html, body').animate({
					scrollTop: $(this).offset().top
				},1000);

			}
		});
	});
	
});
