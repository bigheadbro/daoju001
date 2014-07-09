$(function() {
	show_question();
	$(".product-param-li").mouseenter(function(){
		$(this).css("overflow","visible");
		$(this).css("height","inherit");
		$(this).children(".sel").css("border-bottom","2px solid #ff0101");
		$(this).children("ul").css("border","1px solid #e7e7e7");
	}).mouseleave(function(){
		if($(this).children("input").val()>0)
		{
			$(this).children(".sel").css("color","#515151");
			$(this).children(".sel").css("background","none");
		}
		$(this).css("overflow","hidden");
		$(this).css("height","30px");
		$(this).children(".sel").css("border-bottom","2px solid #fff");
	});
	$(".product-param-li ul li").click(function(){
		var value = $(this).index();
		$(this).parent().parent().css("overflow","hidden");
		$(this).parent().parent().css("height","18px");
		$(this).parent().parent().children(".sel").css("color","#fff");
		//$(this).parent().parent().children(".sel").css("background","#0088b5");
		$(this).parent().parent().children(".sel").text($(this).children("a").text());
		$(this).parent().parent().children("input").val(value).trigger('change');
	}).mouseenter(function(){
		$(this).css('background', '#e7e7e7');
	}).mouseleave(function(){
		$(this).removeAttr("style");
	});
});

function show_question(){
	if($('input[name="processMethod"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="processMethod"]').val()) + 1;
		$(".process .sel").text($(".process li:nth-child(" + tmp + ")").text());
		//$(".process").css("background","#0088b5");
		//$(".process .sel").css("color","#fff");
		$(".process .sel").css("background","none");
		$(".process .sel").css("border-bottom","2px solid #ff0101");
	}
	if($('input[name="industry"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="industry"]').val()) + 1;
		$(".industry .sel").text($(".industry li:nth-child(" + tmp + ")").text());
		//$(".industry").css("background","#0088b5");
		//$(".industry .sel").css("color","#fff");
		$(".industry .sel").css("background","none");
		$(".industry .sel").css("border-bottom","2px solid #ff0101");
	}
	if($('input[name="wpHardness"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="wpHardness"]').val()) + 1;
		$(".hardness .sel").text($(".hardness li:nth-child(" + tmp + ")").text());
		//$(".hardness").css("background","#0088b5");
		//$(".hardness .sel").css("color","#fff");
		$(".hardness .sel").css("background","none");
		$(".hardness .sel").css("border-bottom","2px solid #ff0101");
	}
	if($('input[name="wpMaterial"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="wpMaterial"]').val()) + 1;
		$(".material .sel").text($(".material li:nth-child(" + tmp + ")").text());
		//$(".material").css("background","#0088b5");
		//$(".material .sel").css("color","#fff");
		$(".material .sel").css("background","none");
		$(".material .sel").css("border-bottom","2px solid #ff0101");
	}
	$("#ques-cont-editor").html($('input[name="content"]').val());
	
}