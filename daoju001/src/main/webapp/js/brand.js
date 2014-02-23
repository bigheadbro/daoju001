var brandshow = false;
$(function() {
	$(".close-brand").on("click", function() {
		hidebrand();
	});
    $("#selbrand").click(function(){
    	showbrand();
    });
    $("a.brand-item").click(function(){
    	$("#brand").val($(this).attr("key"));
    	$("#selbrand").text($(this).children(".name").text());
    	hidebrand();
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