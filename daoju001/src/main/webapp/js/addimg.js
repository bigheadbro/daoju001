var addimgshow = false;
$(function() {
	$("#addimg").click(function() {
		showimg();
	});
	$(".close").on("click", function() {
		hideimg();
	});
});

function hideimg(){
	$(".addimg-win").hide();
	$("#pagemask-img").hide();
	addimgshow = false;
}
function showimg() {
	$("#pagemask-img").css("width", $(document).width());
	$("#pagemask-img").css("height", $(document).height());
	$("#pagemask-img").show();
	$(".addimg-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".addimg-win").css("left", $(document).width() / 2 - 250);
	if (!addimgshow) {
		$(".addimg-win").show();
		addimgshow = true;
	}
}