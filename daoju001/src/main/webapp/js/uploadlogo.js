var logoshow = false;
$(function() {
	$("#uploadlogo").click(function() {
		showlogo();
	});
	$(".close").on("click", function() {
		$(".uploadlogo-win").hide();
		$("#pagemask").hide();
		logoshow = false;
	});
});

function showlogo() {
	$("#pagemask").css("width", $(document).width());
	$("#pagemask").css("height", $(document).height());
	$("#pagemask").show();
	$(".log-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".uploadlogo-win").css("left", $(document).width() / 2 - 250);
	if (!logoshow) {
		$(".uploadlogo-win").show();
		logoshow = true;
	}
}