$(document).ready(function() {
    hljs.initHighlightingOnLoad();
	
    $('.logo').mouseover(function() {
        $(this).addClass('animated hinge');
        setTimeout(function() {
            $('.logo').removeClass('animated hinge').show();
        }, 2000);
    });
	
	$('.index a').click(function() {
		$('h1, h2, h3, h4').removeClass('active-index');
		$($(this).attr('href')).addClass('active-index');
	});
});

var sc_project=7899149; var sc_invisible=1; var sc_security="75b043e9"; 
var scJsHost = (("https:" == document.location.protocol) ? "https://secure." : "http://www.");
document.write("<sc"+"ript type='text/javascript' src='" + scJsHost+ "statcounter.com/counter/counter.js'></"+"script>");
