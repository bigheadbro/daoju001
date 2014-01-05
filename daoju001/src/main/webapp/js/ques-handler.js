$(function() {
	$(".question-param-li").mouseenter(function(){
		$(this).css("overflow","visible");
		$(this).css("height","inherit");
		$(this).css("background","#fff");
		$(this).children(".sel").css("color","#b8b8b8");
	}).mouseleave(function(){
		$(this).css("overflow","hidden");
		$(this).css("height","21px");
	});
	$(".question-param-li ul li").click(function(){
		$(this).parent().parent().children("input").val($(this).index());
		$(this).parent().parent().children(".sel").text(this.innerText);
		$(this).parent().parent().css("overflow","hidden");
		$(this).parent().parent().css("height","18px");
		$(this).parent().parent().css("background","#0088b5");
		$(this).parent().parent().children(".sel").css("color","#fff");
	}).mouseenter(function(){
		$(this).css('background', '#7a7a7a');
	}).mouseleave(function(){
		$(this).css('background', 'none');
	});
});