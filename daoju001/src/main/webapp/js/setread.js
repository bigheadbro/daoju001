var link;
$(function() {
	$(".setread").click(function() {
		link = $(this).attr("link");
		$.ajax( {   
		    type : "POST",   
		    url : "/setread", 
		    data : {
		      'msgid' : $(this).attr("msgid")
		     },  
		    dataType: "json",
		    success:function(){window.location.href=link;}
		});  
	});
});