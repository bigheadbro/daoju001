$(function() {
	$(".login").hover(function() {
		$(".top-user").css("background-color","#007298");
		$(".top-dropdown").show();
	});
	$(".login").mouseleave(function() {
		$(".top-user").css("background-color","#0099cb");
		$(".top-dropdown").hide();
	});
	$(".login").hover(function() {
		$(".top-dropdown-reg").show();
		$(this).children(".auth-reg").css("background-color","#007298");
	}).mouseleave(function() {
		$(".top-dropdown-reg").hide();
		$(this).children(".auth-reg").css("background-color","#0099cb");
	});

});