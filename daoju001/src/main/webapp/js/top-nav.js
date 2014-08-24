$(function() {
	$(".ctinfo").hover(function() {
		$(".top-user").css("background-color","#007298");
		$(".top-dropdown").show();
	});
	$(".ctinfo").mouseleave(function() {
		$(".top-user").css("background-color","#0099cb");
		$(".top-dropdown").hide();
	});

	
	$(".ctshop").hover(function() {
		$(".ctshop-user").css("background-color","#007298");
		$(".ctshop-dropdown").show();
	});
	$(".ctshop").mouseleave(function() {
		$(".ctshop-user").css("background-color","#0099cb");
		$(".ctshop-dropdown").hide();
	});
	
	$(".ask").click(function() {
		$.ajax( {   
		    type : "POST",   
			url : "/ask", 
		    dataType: "json",   
		    success : function(data) {   
		    	switch(data.status)
		    	{
		    	case 1:
		    		window.location.href='/log';
		    	  break;
				case 2:
		    		window.location.href='/user/newquestion';
		    	  break;
				case 3:
		    		showAlert("",data.code,"","确定","");
		    	  break;
		    	}  
		    }
		});   
	});
	$(".complainus").click(function(){
		showcomplain();
	});
	(function poll(){
		setTimeout(function() {
		    $.ajax({ 
		    	type : "POST",   
		    	url: "/getMsgCount", 
		    	success: function(data){
			        if(data.msgCount>0){
			        	$(".zg-top").show();
			        	$(".zg-noti-number").each(function(){
			        		$(this).text(data.msgCount);
			        	});
			        }
			        else{
			        	$(".zg-top").hide();
			        }
			    }, 
			    dataType: "json", 
			    complete: poll,
			    timeout: 30000 
		    });
		}, 30000);
	})();

});