$(function() {
	$(".question-param-li").mouseenter(function(){
		$(this).css("overflow","visible");
		$(this).css("height","inherit");
		$(this).css("background","#fff");
		$(this).children(".sel").css("color","#b8b8b8");
	}).mouseleave(function(){
		$(this).css("overflow","hidden");
		$(this).css("height","21px");
	});
	$(".question-param-li ul li").click(function(){
		$(this).parent().parent().children("input").val($(this).index());
		$(this).parent().parent().children(".sel").text(this.innerText);
		$(this).parent().parent().css("overflow","hidden");
		$(this).parent().parent().css("height","18px");
		$(this).parent().parent().css("background","#0088b5");
		$(this).parent().parent().children(".sel").css("color","#fff");
	}).mouseenter(function(){
		$(this).css('background', '#7a7a7a');
	}).mouseleave(function(){
		$(this).css('background', 'none');
	});
});

function show_question(){
	if($("#isEdit").val()>0))
	{
		if($("#type").val() == $(".ques-type a").attr("value"))
		{
			$(this).css("background-color","#0088b5");
		}
		if($('input[name="processMethod"]').val() >= 0 )
		{
			$(".process .sel").text($(".process li:nth-child($('input[name="processMethod"]').val())").text());
			$(".process").css("background","#0088b5");
			$(".process .sel").css("color","#fff");
		}
		if($('input[name="industry"]').val() >= 0 )
		{
			$(".industry .sel").text($(".industry li:nth-child($('input[name="industry"]').val())").text());
			$(".industry").css("background","#0088b5");
			$(".industry .sel").css("color","#fff");
		}
		if($('input[name="wpHardness"]').val() >= 0 )
		{
			$(".hardness .sel").text($(".hardness li:nth-child($('input[name="wpHardness"]').val())").text());
			$(".hardness").css("background","#0088b5");
			$(".hardness .sel").css("color","#fff");
		}
		if($('input[name="wpMaterial"]').val() >= 0 )
		{
			$(".material .sel").text($(".material li:nth-child($('input[name="wpMaterial"]').val())").text());
			$(".material").css("background","#0088b5");
			$(".material .sel").css("color","#fff");
		}
		$("#ques-cont-editor").html($('input[name="content"]').val());
	}
}