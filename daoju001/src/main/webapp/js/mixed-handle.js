$(function() {
  $(".mixed").each(function(){
	  $text = $(this).text();
	  $(this).html($text);
  });

});