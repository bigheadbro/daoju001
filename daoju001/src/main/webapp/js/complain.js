var complainshow = false;
$(function() {
	$(".close-complain").on("click", function() {
		hidecomplain();
	});
});
function hidecomplain(){
	$(".complain-win").hide();
	$("#pagemask-complain").hide();
	complainshow = false;
}
function showcomplain() {
	$("#pagemask-complain").css("width", $(document).width());
	$("#pagemask-complain").css("height", $(document).height());
	$("#pagemask-complain").show();
	$(".complain-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".complain-win").css("left", $(document).width() / 2 - 300);
	if (!complainshow) {
		$(".complain-win").show();
		complainshow = true;
	}
}