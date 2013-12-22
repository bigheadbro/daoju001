$(function() {
	$(".login").hover(function() {
		$(".top-user").css("background-color","#007298");
		$(".top-dropdown").show();
	});
	$(".login").mouseleave(function() {
		$(".top-user").css("background-color","#0099cb");
		$(".top-dropdown").hide();
	});

});