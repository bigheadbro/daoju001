$(function() {
	$(".comments").click(function(){
		if($(this).parent().parent().parent().parent().children(".comment").is(":hidden")){
			$(this).parent().parent().parent().parent().children(".comment").show();
		}
		else{
			$(this).parent().parent().parent().parent().children(".comment").hide();
		}
	});
	$("body").on("click",  ".reply-comment", function(){
		$("#commentId").val($(this).attr("commentid"));
		var name = $(this).parent().parent().parent().children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text();
		$("#commentContent").val("回复" + name + "：");
		$("#commentContent").focus();
	});
	$("body").on("click", ".reply-comment-answer", function(){
		$(this).parent().parent().parent().parent().children(".form_comment_answer").children(".answerCommentId").val($(this).attr("commentid"));
		var name = $(this).parent().parent().parent().children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text();
		$(this).parent().parent().parent().parent().children(".form_comment_answer").children(".reply-cont").children(".answerCommentContent").val("回复" + name + "：");
		$(this).parent().parent().parent().parent().children(".form_comment_answer").children(".reply-cont").children(".answerCommentContent").focus();
	});
});

function processComment(data) { 
	if(data.comment == undefined){
		showAlert(data.title,data.content,data.info,data.btn,data.link);
		return;
	}
	var count = $("#question-comments").text();
	count = parseFloat(count) + 1;
	$("#question-comments").text(count);
	var d=new Date();
	var time = d.getTime();
	$("#form_comment").before("<ul newcomment=\""+time+"\" class=\"comment-item clearfix\"><li class=\"agent-info clearfix\"><div class=\"logo\"><a><img src=\"../img/avatar.png\" /></a></div><div class=\"agent-detail\"><h2 class=\"clearfix\"><a class=\"agent-name\" href=\"\"></a></h2><h4></h4></div></li><li class=\"comment-cont\"><p> </p></li><li class=\"info\"><p class=\"clearfix\"><span class=\"time\"></span><a class=\"reply-comment\" commentid=\""+data.commentid+"\">回复</a></p></li></ul>");
	$("ul[newcomment="+time+"]").children(".comment-cont").children("p").text(data.comment);
	$("ul[newcomment="+time+"]").children(".info").children("p").children(".time").text(data.time);
	$("ul[newcomment="+time+"]").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text(data.userName);
	if(data.logo != undefined){
		$("ul[newcomment="+time+"]").children(".agent-info").children(".logo").children("a").children("img").attr("src","../logo/"+data.logo);
	}
	if(data.verifiedLink != undefined){
		$("ul[newcomment="+time+"]").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").after("<a class=\"fancybox-v\" title=\"+data.userName+\" 的代理证书\" href=\"+comment.verifiedLink+\"><img src=\"../img/v.png\" /></a>");
	}
	if(data.brandName != undefined){
		$("ul[newcomment="+time+"]").children(".agent-info").children(".agent-detail").children("h4").text(data.brandName);
	}
}
function processAnswerComment(data) { 
	if(data.comment == undefined){
		showAlert(data.title,data.content,data.info,data.btn,data.link);
		return;
	}
	var count,answercomments;
	answercomments = $(".form_comment_answer[current-answer=1]").parent().parent().children("li.info").children("p").children("span.answer").children("a.comments").children("span.answer-comments");
	count = parseFloat(answercomments.text()) + 1;
	answercomments.text(count);

	var d=new Date();
	var time = d.getTime();
	$(".form_comment_answer[current-answer=1]").before("<ul newcomment-answer=\""+time+"\" class=\"comment-item clearfix\"><li class=\"agent-info clearfix\"><div class=\"logo\"><a><img src=\"../img/avatar.png\" /></a></div><div class=\"agent-detail\"><h2 class=\"clearfix\"><a class=\"agent-name\" href=\"\"></a></h2><h4></h4></div></li><li class=\"comment-cont\"><p> </p></li><li class=\"info\"><p class=\"clearfix\"><span class=\"time\"></span><a class=\"reply-comment-answer\" commentid=\""+data.commentid+"\">回复</a></p></li></ul>");
	$("ul[newcomment-answer="+time+"]").children(".comment-cont").children("p").text(data.comment);

	$("ul[newcomment-answer="+time+"]").children(".info").children("p").children(".time").text(data.time);

	$("ul[newcomment-answer="+time+"]").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text(data.userName);
	if(data.logo != undefined){
		$("ul[newcomment-answer="+time+"]").children(".agent-info").children(".logo").children("a").children("img").attr("src","../logo/"+data.logo);
	}
	if(data.verifiedLink != undefined){
		$("ul[newcomment-answer="+time+"]").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").after("<a class=\"fancybox-v\" title=\"+data.userName+\" 的代理证书\" href=\"+comment.verifiedLink+\"><img src=\"../img/v.png\" /></a>");
	}
	if(data.brandName != undefined){
		$("ul[newcomment-answer="+time+"]").children(".agent-info").children(".agent-detail").children("h4").text(data.brandName);
	}
	$(".form_comment_answer[current-answer=1]").attr("current-answer","0");
}