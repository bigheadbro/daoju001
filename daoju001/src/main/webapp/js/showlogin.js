var loginshow = false;
$(function() {
	$("#login").click(function() {
		showlogin();
	});
	$(".closealert").on("click", function() {
		$(".log-win").hide();
		$("#pagemask").hide();
		loginshow = false;
	});
});

function showlogin() {
	$("#pagemask").css("width", $(document).width());
	$("#pagemask").css("height", $(document).height());
	$("#pagemask").show();
	$(".log-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".log-win").css("left", $(document).width() / 2 - 250);
	if (!loginshow) {
		$(".log-win").show();
		loginshow = true;
	}
}