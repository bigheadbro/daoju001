var alertshow = false;
var alertfirst = true;
var complainshow = false;

function hidecomplain() {
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
$(function() {
	/*if (document.all) {
		window.location.href = "/browser";
	}*/
	$('.fancybox-v').fancybox({
		openEffect : 'elastic',
		closeEffect : 'elastic',
		helpers: {
            overlay: {
              locked: false
            }
          }
	});
	$("#alert").click(
			function() {
				showAlert("您的余额不足", "不能使用该服务，请去充值！现在要说的话有一点点长，来测试格式是否合理",
						"充值请至：企业中心>>消费记录", "充 值", "");
			});
	$(".closealert").on("click", function() {
		$(".alert-wrapper").hide();
		$("#pagemask").hide();
		alertshow = false;
	});
	$(".alert-btn").on("click", function() {
		if ($(".alert-btn").attr("href") == "") {
			$(".alert-wrapper").hide();
			$("#pagemask").hide();
			alertshow = false;
		}
	});
	$(".close-complain").on("click", function() {
		hidecomplain();
	});
});

function showAlert(title, content, info, btn, link) {
	if (alertfirst) {
		alertfirst = false;
		$("body")
				.append(
						"<div id=\"pagemask\"></div><div class=\"alert-wrapper\"><div class=\"alert-layer clearfix\"><h3><a href=\"\" class=\"closealert\">x</a>系统提示</h3><div class=\"alert-content clearfix\">					<div class=\"alert-content-left\">						<h1></h1>						<h4></h4>					</div>					<div class=\"alert-content-right\">						<a href=\"\" class=\"alert-btn\"></a>					</div>				</div>				<div class=\"alert-footer\">									</div>							</div>		</div>");
	}
	$("#pagemask").css("width", $(document).width());
	$("#pagemask").css("height", $(document).height());
	$("#pagemask").show();
	$(".alert-wrapper").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 120);
	$(".alert-wrapper").css("left", $(document).width() / 2 - 250);
	if (!alertshow) {
		$(".alert-wrapper").show();
		alertshow = true;
	}
	if (title != "")
		$(".alert-content-left h1").text(title);
	if (content != "")
		$(".alert-content-left h4").text(content);
	if (btn != "")
		$(".alert-btn").text(btn);
	if (info != "")
		$(".alert-footer").text(info);
	if (link != "")
		$(".alert-btn").attr("href", link);
}