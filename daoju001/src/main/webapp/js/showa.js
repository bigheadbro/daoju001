$(function() {
	$(".comments").click(function(){
		if($(this).parent().parent().parent().parent().children(".comment").is(":hidden")){
			$(this).parent().parent().parent().parent().children(".comment").show();
		}
		else{
			$(this).parent().parent().parent().parent().children(".comment").hide();
		}
	});
	$(".reply-comment").click(function(){
		$("#commentId").val($(this).attr("commentid"));
		var name = $(this).parent().parent().parent().children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text();
		$("#commentContent").val("回复" + name + "：");
		$("#commentContent").focus();
	});
	$(".reply-comment-anser").click(function(){
		$("#answerCommentId").val($(this).attr("commentid"));
		var name = $(this).parent().parent().parent().children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text();
		$("#answerCommentContent").val("回复" + name + "：");
		$("#answerCommentContent").focus();
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
	$("#form_comment").before("<ul id=\"new-comment\" class=\"comment-item clearfix\"><li class=\"agent-info clearfix\"><div class=\"logo\"><a><img src=\"../img/avatar.png\" /></a></div><div class=\"agent-detail\"><h2 class=\"clearfix\"><a class=\"agent-name\" href=\"\"></a></h2><h4></h4></div></li><li class=\"comment-cont\"><p> </p></li><li class=\"info\"><p class=\"clearfix\"><span class=\"time\"></span><a class=\"reply-comment\">回复</a></p></li></ul>");
	$("#new-comment").children(".comment-cont").children("p").text(data.comment);
	$("#new-comment").children(".info").children("p").children(".time").text(data.time);
	$("#new-comment").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text(data.userName);
	if(data.logo != undefined){
		$("#new-comment").children(".agent-info").children(".logo").children("a").children("img").attr("src","../logo/"+data.logo);
	}
	if(data.verifiedLink != undefined){
		$("#new-comment").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").after("<a class=\"fancybox-v\" title=\"+data.userName+\" 的代理证书\" href=\"+comment.verifiedLink+\"><img src=\"../img/v.png\" /></a>");
	}
	if(data.brandName != undefined){
		$("#new-comment").children(".agent-info").children(".agent-detail").children("h4").text(data.brandName);
	}
}
function processAnswerComment(data) { 
	var count = $("#answer-comments").text();
	count = parseFloat(count) + 1;
	$("#answer-comments").text(count);
	$("#form_comment_answer").before("<ul id=\"new-comment-answer\" class=\"comment-item clearfix\"><li class=\"agent-info clearfix\"><div class=\"logo\"><a><img src=\"../img/avatar.png\" /></a></div><div class=\"agent-detail\"><h2 class=\"clearfix\"><a class=\"agent-name\" href=\"\"></a></h2><h4></h4></div></li><li class=\"comment-cont\"><p> </p></li><li class=\"info\"><p class=\"clearfix\"><span class=\"time\"></span><a class=\"reply-comment\">回复</a></p></li></ul>");
	$("#new-comment-answer").children(".comment-cont").children("p").text(data.comment);
	$("#new-comment-answer").children(".info").children("p").children(".time").text(data.time);
	$("#new-comment-answer").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").text(data.userName);
	if(data.logo != undefined){
		$("#new-comment-answer").children(".agent-info").children(".logo").children("a").children("img").attr("src","../logo/"+data.logo);
	}
	if(data.verifiedLink != undefined){
		$("#new-comment-answer").children(".agent-info").children(".agent-detail").children("h2").children(".agent-name").after("<a class=\"fancybox-v\" title=\"+data.userName+\" 的代理证书\" href=\"+comment.verifiedLink+\"><img src=\"../img/v.png\" /></a>");
	}
	if(data.brandName != undefined){
		$("#new-comment-answer").children(".agent-info").children(".agent-detail").children("h4").text(data.brandName);
	}
}