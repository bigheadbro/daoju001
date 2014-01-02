$(function() {
	$(".question-param-li .sel").hover(function(){
		$(this).parent().children("ul").show();
	}).mouseleave(function(){
		$(this).parent().children("ul").hide();
	});
	$(".question-param-li ul li").click(function(){
		alert($(this).index());
		$(this).parent().parent().children("input").val();
	});
});