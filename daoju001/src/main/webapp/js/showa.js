$(function() {
	$(".showpa").click(function(){
		$(this).parent().parent().parent().children(".answer").show();
		$(this).parent().parent().parent().children(".answer-c").hide();
	});
	$(".showca").click(function(){
		$(this).parent().parent().parent().children(".answer").hide();
		$(this).parent().parent().parent().children(".answer-c").show();
	});
	$(".reply-comment").click(function(){
		$(this).parent().parent().parent().parent().children(".comment").show();
	});
});