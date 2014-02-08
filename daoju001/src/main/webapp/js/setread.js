$(function() {
	$(".setread").click(function() {
		$.ajax( {   
		    type : "POST",   
		    url : "/setread", 
		    data : {
		      'msgid' : $(this).attr("msgid")
		     },  
		    dataType: "json"
		});  
		showanswer(); 
	});
});