var addimgshow = false;
$(function() {
	$("#addimg").click(function() {
		showimg();
	});
	$(".close").on("click", function() {
		$(".addimg-win").hide();
		$("#pagemask").hide();
		addimgshow = false;
	});
});

function showimg() {
	$("#pagemask").css("width", $(document).width());
	$("#pagemask").css("height", $(document).height());
	$("#pagemask").show();
	$(".addimg-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".addimg-win").css("left", $(document).width() / 2 - 250);
	if (!addimgshow) {
		$(".addimg-win").show();
		addimgshow = true;
	}
}