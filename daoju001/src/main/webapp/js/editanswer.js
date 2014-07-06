var answershow = false;
$(function() {
	$(".editanswer").click(function() {
		$("#isEdit").val(1);
		$("#aid").val($(this).attr("answerid"));
		$.ajax( {   
		    type : "POST",   
		    url : "/user/editanswer", 
		    data : {
		      'answerid' : $(this).attr("answerid"),
		     },  
		    dataType: "json",   
		    success : function(data) {   
		    	$("#qid").val(data.qid);
		    	$("#bid").val(data.bid);
		    	$("#state").val(data.state);
		    	$("#hasPic").val(data.hasPic);
		    	
		    	if(data.freeuse){
		    		$("#freeuse").attr("checked","true");
		    	}
		    	$("#answer-editor").html(data.content);
		    	$("#bargin").text(data.price);
		    }
		});  
		showanswer(); 
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
	$(".answer-win").css("top",
			$(window).height() / 2 + $(document).scrollTop() - 220);
	$(".answer-win").css("left", $(document).width() / 2 - 300);
	if (!answershow) {
		$(".answer-win").show();
		answershow = true;
	}
}