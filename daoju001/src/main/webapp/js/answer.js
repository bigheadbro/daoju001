var answershow = false;
$(function() {
	$(".solution").click(function() {
		$("#qid").val($(this).attr("qid"));
		$("#bid").val($(this).attr("bid"));
		$.ajax( {   
		    type : "POST",   
		    url : "/agent/showanswer", 
		    data : {
		      'query' : 'isAgentLogin',
		     },  
		    dataType: "json",   
		    success : function(data) {   
		    	switch(data.status)
		    	{
		    	case 1:
		    		window.location.href='/agent/log';
		    	  break;
		    	case 2:
		    		showAlert("",data.code,"","确定","");
		    	  break;
		    	case 3:
		    		showAlert("",data.code,"","确定","");
		    	  break;
		    	default:
		    		showanswer(); 
		    	}  
		    }
		});   
		
	});
	$(".close-answer").on("click", function() {
		hideanswer();
	});
});

function hideanswer(){
	$(".answer-win").hide();
	$("#pagemask").hide();
	answershow = false;
}
function showanswer() {
	$("#pagemask").css("width", $(document).width());
	$("#pagemask").css("height", $(document).height());
	$("#pagemask").show();
	$(".log-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".answer-win").css("left", $(document).width() / 2 - 300);
	if (!answershow) {
		$(".answer-win").show();
		answershow = true;
	}
}