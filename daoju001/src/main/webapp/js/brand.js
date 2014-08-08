var brandshow = false;
var bid = 0;
$(function() {
	$(".close-brand").on("click", function() {
		hidebrand();
	});
	$("#selbrand").click(function(){
    	showbrand();
    });
    $(".selbrand").click(function(){
    	showbrand();
    	bid = parseInt($(this).attr("bid"));
    });
    $("a.brand-item").click(function(){
    	if(bid == 1){
	    	$(".brand1").val($(this).attr("key"));
	    	$(".bid1").text($(this).children(".name").text());
    	}else{
    		$(".brand2").val($(this).attr("key"));
	    	$(".bid2").text($(this).children(".name").text());
    	}
    	$("#brand").val($(this).attr("key"));
    	$("#selbrand").text($(this).children(".name").text());
    	hidebrand();
    	if(parseInt($(this).attr("key")) ==  100){
    		$("#otherbrand").show();
    	}
    	else
    	{
    		$("#otherbrand").hide();
    	}
    });
});
function hidebrand(){
	$(".brand-win").hide();
	$("#pagemask-brand").hide();
	brandshow = false;
}
function showbrand() {
	$("#pagemask-brand").css("width", $(document).width());
	$("#pagemask-brand").css("height", $(document).height());
	$("#pagemask-brand").show();
	$(".brand-win").css("top",
			100);
	$(".brand-win").css("left", $(document).width() / 2 - 392);
	if (!brandshow) {
		$(".brand-win").show();
		brandshow = true;
	}
}