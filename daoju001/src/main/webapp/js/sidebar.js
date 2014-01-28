var side_show = false;
$(document).ready(function() {
	$(window).scroll(function (){
		var offsetTop = $(window).scrollTop() + 220 +"px";
		$(".scrollsidebar").animate({top : offsetTop },{ duration:500 , queue:false });
	}); 
	$(".show_btn").click(function(){
		if(side_show){
			$(".show_btn").children("img").attr("src", "../img/side1.png");
			$(".side_content").hide("slow");
			side_show = false;
		}
		else{
			$(".show_btn").children("img").attr("src", "../img/side2.png");
			$(".side_content").show("slow");
			side_show = true;
		}
		
		
	});

});