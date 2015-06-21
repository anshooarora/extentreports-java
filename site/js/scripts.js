$(document).ready(function() {
	hljs.initHighlightingOnLoad();
	$(document).ready(sizing);
	$(window).resize(sizing);
	function sizing() {
		if ($(window).width() > 1650) {
			$('.top-unit').removeClass('unit-80').addClass('unit-70');
		} 
		else {
			$('.top-unit').removeClass('unit-70').addClass('unit-80');
		}
	}
});
var sc_project=7899149; var sc_invisible=1; var sc_security="75b043e9"; 
var scJsHost = (("https:" == document.location.protocol) ? "https://secure." : "http://www.");
document.write("<sc"+"ript type='text/javascript' src='" + scJsHost+ "statcounter.com/counter/counter.js'></"+"script>");