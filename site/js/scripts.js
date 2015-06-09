$(document).ready(function() {
	$(document).ready(sizing);
	$(window).resize(sizing);
	function sizing() {
		if ($(window).width() > 1650) {
			$('.top-unit').removeClass('unit-80').addClass('unit-70');
		} else if ($(window).width() < 1120) {
			$('.top-unit').removeClass('unit-70').addClass('unit-80');
		} else {
			$('.top-unit').removeClass('unit-70, unit-80').addClass('unit-90');
		}
	}
});
