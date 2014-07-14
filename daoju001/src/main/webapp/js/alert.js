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
	if (document.all) {
		window.location.href = "/browser";
	}
	$("img").lazyload({
		placeholder : "../img/grey.gif",
		event : "click"
	});
	$(".agent-info .logo").mouseDelay(150).hover(function(){
		var aid = $(this).attr("aid");
		var top2 = $(this).offset().top - $(window).scrollTop();
		var top = $(this).offset().top;
		var height = $(this).height();
		var left = $(this).offset().left - 10;
		$.ajax({   
		    type : "POST",   
		    url : "/getangetinfo", 
		    data : {
		      'id' : aid,
		     },  
		    dataType: "json",   
		    success : function(data) { 
		    	if(data.name){
		    		$(".card-win .agent-name").text(data.name);
		    		$(".card-win .agent-name").attr("href","/agents/"+aid);
		    		if(data.logo){
		    			$(".card-win .card-logo").attr("src","../logo/"+data.logo);
		    			$(".card-win .agent-logo a").attr("href","/agents/"+aid);
		    		}
		    		else{
		    			$(".card-win .card-logo").attr("src","../img/avatar.png");
		    		}
		    		if(data.brandname){
		    			$(".card-win .agent-detail h4").text(data.brandname);
		    		}
		    		if(data.brandlink){
		    			$(".card-win .card-brand").attr("src","../img/logo/"+data.brandlink);
		    		}
		    		if(data.isverify){
		    			$(".card-win .vip").show();
		    		}
		    		else{
		    			$(".card-win .vip").hide();
		    		}
		    		if(data.phone){
		    			$(".card-win .phone-value").text(data.phone);
		    		}
		    		if(data.qq){
		    			$(".card-win .qq-value").text(data.qq);
		    		}
		    		if(data.cntAnswer){
		    			$(".card-win .answer").text(data.cntAnswer);
		    		}
		    		if(data.cntSample){
		    			$(".card-win .sample").text(data.cntSample);
		    		}
		    		else{
		    			$(".card-win .sample").text("0");
		    		}
		    		if(top2 > 210){
		    			var tmp = top - 210;
		    			$(".card-win").css("top",tmp+"px");
		    			$(".card-win").css("left",left+"px");
		    		}else{
		    			var tmp = top + height + 10;
		    			$(".card-win").css("top",tmp+"px");
		    			$(".card-win").css("left",left+"px");
		    		}
		    		$(".card-win").show();
		    	}
		    	//alert(data.name+data.logo+data.brandname+data.brandlink+data.isverify+data.cntAnswer+data.cntSample+data.phone+data.qq);
		    }
		}); 
		$(this).stop().animate();
	},function(){
		$(this).stop().animate();
		$(".card-win").hide();
	});
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
	$("body").on("click",".closealert", function() {
		$(".alert-wrapper").hide();
		$("#pagemask").hide();
		alertshow = false;
	});
	$("body").on("click", ".alert-btn",function() {
		if ($(".alert-btn").attr("href") == "") {
			$(".alert-wrapper").hide();
			$("#pagemask").hide();
			alertshow = false;
		}else if($(".alert-btn").attr("href") == "#"){
			$(".alert-wrapper").hide();
			$("#pagemask").hide();
			alertshow = false;
			window.location.reload();
		}else{
			$(".alert-wrapper").hide();
			$("#pagemask").hide();
			alertshow = false;
			window.location.href = "/products";
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
						"<div id=\"pagemask\"></div><div class=\"alert-wrapper\"><div class=\"alert-layer clearfix\"><h3><em href=\"\" class=\"closealert\">x</em>系统提示</h3><div class=\"alert-content clearfix\">					<div class=\"alert-content-left\">						<h1></h1>						<h4></h4>					</div>					<div class=\"alert-content-right\">						<em href=\"\" class=\"alert-btn\"></em>					</div>				</div>				<div class=\"alert-footer\">									</div>							</div>		</div>");
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