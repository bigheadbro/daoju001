$(function() {
	show_question();
	$(".question-param-li").mouseenter(function(){
		$(this).css("overflow","visible");
		$(this).css("height","inherit");
		$(this).css("background","#fff");
		$(this).children(".sel").css("color","#b8b8b8");
		$(this).children(".sel").css("background","url(../img/dropdown.png) no-repeat 67px 7px");
	}).mouseleave(function(){
		if($(this).children("input").val()>0)
		{
			$(this).css("background","#0088b5");
			$(this).children(".sel").css("color","#fff");
			$(this).children(".sel").css("background","none");
		}
		$(this).css("overflow","hidden");
		$(this).css("height","21px");

	});
	$(".question-param-li ul li").click(function(){
		var value = $(this).index();
		$(this).parent().parent().css("overflow","hidden");
		$(this).parent().parent().css("height","18px");
		$(this).parent().parent().css("background","#0088b5");
		$(this).parent().parent().children(".sel").css("color","#fff");
		$(this).parent().parent().children(".sel").css("background","none");
		$(this).parent().parent().children(".sel").text($(this).children("a").text());
		$(this).parent().parent().children("input").val(value).trigger('change');
	}).mouseenter(function(){
		$(this).css('background', '#7a7a7a');
	}).mouseleave(function(){
		$(this).css('background', 'none');
	});
});

function show_question(){
	if($("#isEdit").val()>0)
	{
		var tmp;
		$(".question-type a").each(function(){
			if($("#type").val() == $(this).attr("value"))
			{
				$(this).css("background-color","#0088b5");
			}
		});
	}
	if($('input[name="processMethod"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="processMethod"]').val()) + 1;
		$(".process .sel").text($(".process li:nth-child(" + tmp + ")").text());
		$(".process").css("background","#0088b5");
		$(".process .sel").css("color","#fff");
	}
	if($('input[name="industry"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="industry"]').val()) + 1;
		$(".industry .sel").text($(".industry li:nth-child(" + tmp + ")").text());
		$(".industry").css("background","#0088b5");
		$(".industry .sel").css("color","#fff");
	}
	if($('input[name="wpHardness"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="wpHardness"]').val()) + 1;
		$(".hardness .sel").text($(".hardness li:nth-child(" + tmp + ")").text());
		$(".hardness").css("background","#0088b5");
		$(".hardness .sel").css("color","#fff");
	}
	if($('input[name="wpMaterial"]').val() > 0 )
	{
		tmp = parseFloat($('input[name="wpMaterial"]').val()) + 1;
		$(".material .sel").text($(".material li:nth-child(" + tmp + ")").text());
		$(".material").css("background","#0088b5");
		$(".material .sel").css("color","#fff");
	}
	$("#ques-cont-editor").html($('input[name="content"]').val());
	
}