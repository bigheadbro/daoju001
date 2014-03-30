$(function() {
	$(".event-listitem li.value").mouseenter(function(){
		$(this).children("ul.param").show();
		$(this).children("ul.param").css("border","1px solid #ccc").css("border-top","none").css("border-bottom-left-radius","4px").css("border-bottom-right-radius","4px");
	}).mouseleave(function(){
		$(this).children("ul.param").hide();
	});
	$(".event-listitem li.value li").click(function(){
		var value = $(this).index();
		$(this).parent().parent().children("a.sel").text($(this).text());
		$(this).parent().parent().children("input").val(value);
		$(this).parent().hide();
	});

});
