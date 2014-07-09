var side_show = false;
$(document).ready(function() {
	$(".goTab ul li").mouseenter(function(){
		$(this).children("a").animate({width:"145px"});
		$(this).children("a").css("background-color","#0099cb");
	}).mouseleave(function(){
		$(this).children("a").animate({width:"45px"});
		$(this).children("a").css("background-color","#71b6cd");
	});
	
	$(".gTop a").click(function(){
		jQuery('html,body').animate({scrollTop:0},1000);
	});

	$(".complain a").click(function(){
		showcomplain();
	});
	$(".code").mouseenter(function(){
		$(this).children("i").show();
	}).mouseleave(function(){
		$(this).children("i").hide();
	});
});