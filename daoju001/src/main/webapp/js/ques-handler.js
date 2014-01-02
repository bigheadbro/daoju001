$(function() {
	$(".question-param-li").mouseenter(function(){
		$(this).children("ul").show();
	}).mouseleave(function(){
		$(this).children("ul").hide();
	});
	$(".question-param-li ul li").click(function(){
		$(this).parent().parent().children("input").val($(this).index());
		$(this).parent().parent().children(".sel").text(this.innerText);
		$(this).parent().hide();
	}).mouseenter(function(){
		$(this).css('background', '#f2f2f2');
	}).mouseleave(function(){
		$(this).css('background', 'none');
	});
});