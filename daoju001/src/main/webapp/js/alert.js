var alertshow = false;
var alertfirst = true;
var complainshow = false;
var side_show = false;
var complainshow = false;
var requestshow = false;
var stockshow = false;
var searchmaterialshow = false;

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

function hidesearchmaterial() {
	$(".searchmaterial-win").hide();
	$("#pagemask-searchmaterial").hide();
	searchmaterialshow = false;
}
function showsearchmaterial() {
	$("#pagemask-searchmaterial").css("width", $(document).width());
	$("#pagemask-searchmaterial").css("height", $(document).height());
	$("#pagemask-searchmaterial").show();
	$(".searchmaterial-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".searchmaterial-win").css("left", $(document).width() / 2 - 300);
	if (!searchmaterialshow) {
		$(".searchmaterial-win").show();
		searchmaterialshow = true;
	}
}

function hiderequest() {
	$(".quickrequest-win").hide();
	$("#pagemask-quickrequest").hide();
	requestshow = false;
}
function showrequest() {
	$("#pagemask-quickrequest").css("width", $(document).width());
	$("#pagemask-quickrequest").css("height", $(document).height());
	$("#pagemask-quickrequest").show();
	$(".quickrequest-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".quickrequest-win").css("left", $(document).width() / 2 - 300);
	if (!requestshow) {
		$(".quickrequest-win").show();
		requestshow = true;
	}
}
function hidestock() {
	$(".addstock-win").hide();
	$("#pagemask-addstock").hide();
	stockshow = false;
}
function showstock() {
	$("#pagemask-addstock").css("width", $(document).width());
	$("#pagemask-addstock").css("height", $(document).height());
	$("#pagemask-addstock").show();
	$(".addstock-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".addstock-win").css("left", $(document).width() / 2 - 300);
	if (!stockshow) {
		$(".addstock-win").show();
		stockshow = true;
	}
}
$(function() {
	if (document.all) {
		window.location.href = "/browser";
	}
	$(".complain-win").hide();
	$("#pagemask-complain").hide();
	complainshow = false;
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
	$("a.complain").click(function(){
		showcomplain();
	});
	$("a.searchmaterial").click(function(){
		showsearchmaterial();
	});
	$(".code").mouseenter(function(){
		$(this).children("i").show();
	}).mouseleave(function(){
		$(this).children("i").hide();
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
			window.location.href = $(".alert-btn").attr("href");
		}
	});
	$(".close-complain").on("click", function() {
		hidecomplain();
	});
	$(".close-searchmaterial").on("click", function() {
		hidesearchmaterial();
	});
	$(".close-addstock").on("click", function() {
		hidestock();
	});
	$(".close-quickrequest").on("click", function() {
		hiderequest();
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