google.load('visualization', '1', {packages:['corechart']});
$(document).ready(function() {
	$('.button-collapse').sideNav({ menuWidth: 60 });
	$('select').material_select();
	$('ul.tabs').tabs();
	$('.materialboxed').materialbox();
	$('.step-dashboard-status-filter').click(function() {
		$('#step-dashboard-status-filter').openModal();
	});
	if ($('.category-toggle option').length == 2) {
	  $('.category-toggle').hide();
	}
	$('.table-results').filter(function() {
		return ($(this).find('tr').length == 1);
	}).hide(0);
	if ($('.category-view').length > 0) {
		$('.no-categories-message').hide();
	}
	$('#step-dashboard-status-filter input').prop('checked', 'checked');
	$('.indicator').addClass('teal lighten-4');
	var totalTests = $('.test').length;
	var passedTests = $('.test.pass').length;
	var failedTests = $('.test.fail').length;
	var fatalTests = $('.test.fatal').length;
	var warningTests = $('.test.warning').length;
	var errorTests = $('.test.error').length;
	var skippedTests = $('.test.skip').length;
	var unknownTests = $('.test.unknown').length;
	var totalSteps = $('td.status').length;
	var passedSteps = $('td.status.pass').length;
	var failedSteps = $('td.status.fail').length;
	var fatalSteps = $('td.status.fatal').length;
	var warningSteps = $('td.status.warning').length;
	var errorSteps = $('td.status.error').length;
	var infoSteps = $('td.status.info').length;
	var skippedSteps = $('td.status.skip').length;
	var unknownSteps = $('td.status.unknown').length;
	$('nav li').click(function() {
		if (!$(this).hasClass('active')) {
			var cls = $(this).prop('class').split(' ')[0];
			$('nav li').removeClass('active');
			$(this).addClass('active');
			$('.views > div').not('.' + cls).slideUp().parent().children('.' + cls).slideDown();
			if (cls == 'runinfo-view') 
				showRunInfo();
		}
	});
	$('.quick-view-test').click(function() {
		var index = $(this).closest('tr').index();
		var el = $('.test-list .test:eq(' + index + ')');
		$('.tabs .tab:nth-child(3) a').click();
		$('html, body').animate({
			scrollTop: el.offset().top
		}, 1000);
		if (!el.hasClass('is-expanded'))
			el.toggleClass('is-expanded').find('.test-body').slideToggle(300);
	});
	$('.category-link').click(function() {
		var name = $(this).text();
		var ts = $(this).parent().prev().text();
		var index = -1;
		$('.test').each(function() {
			if ($(this).find('.test-name').text() == name && $(this).find('.test-started-time:eq(0)').text() == ts) {
				$('.tabs .tab:nth-child(3) a').click();
				$('html, body').animate({
					scrollTop: $(this).offset().top
				}, 1000);
				if (!$(this).hasClass('is-expanded'))
					$(this).toggleClass('is-expanded').find('.test-body').slideToggle(300);
				return false;
			}
		});
	});
	$('.test-list .test').click(function(evt) {
		var cls = evt.target.className.toLowerCase();
		if (cls == 'category') {
			$('.category-toggle li').each(function() {
				if ($(this).text() == $(evt.target).text()) {
					$(this).click();
					return;
				}
			});
		}
		else if (cls.indexOf('collapsible') > -1 || cls.indexOf('node') > -1) { }
		else if (evt.target.nodeName == 'DIV' || evt.target.nodeName == 'SPAN') {
			$(this).toggleClass('is-expanded').find('.test-body').slideToggle(300);
		}
	});
	$('#step-dashboard-status-filter input').click(function() {
		if ($(this).prop('checked') == false) {
			$('#step-dashboard-status-filter').addClass($(this).prop('id').replace('step-dashboard-filter-', ''));
		}
		else {
			$('#step-dashboard-status-filter').removeClass($(this).prop('id').replace('step-dashboard-filter-', ''));
		}
	});
	$('.modal-footer').click(function() {
		redrawCharts();
	});
	function showRunInfo() {
		$('.total-tests > .percent').text(totalTests).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#9c27b0', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.total-tests').data('easyPieChart').update('100');
		$('.tests-passed > .percent').text(Math.round((passedTests / totalTests) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#53b657', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.tests-passed').data('easyPieChart').update((passedTests / totalTests) * 100);
		$('.tests-failed > .percent').text(Math.round(((failedTests + fatalTests) / totalTests) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#f8576c', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.tests-failed').data('easyPieChart').update(((failedTests + fatalTests) / totalTests) * 100);
		$('.total-steps > .percent').text(totalSteps).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#1366d7', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.total-steps').data('easyPieChart').update('100');
		$('.steps-passed > .percent').text(Math.round((passedSteps / totalSteps) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#53b657', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.steps-passed').data('easyPieChart').update((passedSteps / totalSteps) * 100);
		$('.steps-failed > .percent').text(Math.round(((failedSteps + fatalSteps) / totalSteps) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#f8576c', lineCap: 'butt', scaleColor: '#fff', size: 100 });
		$('.steps-failed').data('easyPieChart').update(((failedSteps + fatalSteps) / totalSteps) * 100);
	}
	$('.categories').each(function() {
		if ($(this).children().length > 0) {
			$(this).css('margin-top', '10px');                    
		}
	});
	$(document).keypress(function(e) {
		if(e.which == 13) {
			if ($('#test-name-filter').is(':focus')) {
				var txt = $('#test-name-filter').val();
				$('.test').hide(0);
				$('.test-name, .test-desc').each(function() {
					if ($(this).text().toLowerCase().indexOf(txt) >= 0) {
						$(this).closest('.test').show();
					}
				});
			}
		}
	});
	$('.tests-toggle li').click(function() {
		var opt = $(this).text().toLowerCase();
		var opt2 = $('.category-toggle li.active').text();
		if (opt2 == 'Choose your option' || opt2 == 'Clear Filters') opt2 = '';
		if (opt != 'choose your option') {
			if (opt == 'clear filters') {
				resetFilters();
			} else {
				$('.tests-toggle li').removeClass('active');
				$(this).addClass('active');
				$('.test').hide(0).removeClass('displayed');
				if (opt2 != '') {
					$('.test').each(function() {
						if ($(this).hasClass(opt) && $(this).find('.category').length > 0) {
							for (var i = 0; i < $(this).find('.category').length; i++) {
								if ($(this).find('.category').eq(i).text() == opt2) {
									$(this).addClass('displayed').show(0);
								}
							}
						}
					});
				} else {
					$('.test').hide(0).removeClass('displayed');
					$('.test.' + opt).fadeIn(200).addClass('displayed');
				}
				redrawCharts();
			}
		}
	});
	$('.category-toggle li').click(function() {
		var opt = $(this).text();
		var opt2 = $('.tests-toggle li.active').text().toLowerCase();
		if (opt2 == 'choose your option' || opt2 == 'clear filters') opt2 = '';
		if (opt != 'Choose your option') {
			if (opt.toLowerCase() == 'clear filters') {
				resetFilters();
			} else {
				$('.category-toggle li').removeClass('active');
				$(this).addClass('active');
				$('.test').hide(0).removeClass('displayed');
				if (opt2 != '') {
					$('.test').each(function() {
						if ($(this).hasClass(opt2) && $(this).find('.category').length > 0) {
							for (var i = 0; i < $(this).find('.category').length; i++) {
								if ($(this).find('.category').eq(i).text() == opt) {
									$(this).addClass('displayed').show(0);
								}
							}
						}
					});
				} else {
					$('.test .category').each(function() {
						if ($(this).text() == opt) {
							$(this).closest('.test').fadeIn(200).addClass('displayed');
						}
					});
				}
				redrawCharts();
			}
		}                                
	});
	function resetFilters() {
		$('.dropdown-content li').removeClass('active');
		$('.test').addClass('displayed').show(0);
		redrawCharts();
		$('.dropdown-content li:first-child').addClass('active').click();
	}
	function updateTotalTimeTaken() {
		var dates=[];
		$('.test-started-time, .test-ended-time').each(function() { 
			dates.push(new Date($(this).text().replace('-', '/').replace('-', '/'))); 
		});
		var maxDate=new Date(Math.max.apply(null,dates));
		var minDate=new Date(Math.min.apply(null,dates));
		$('.suite-started-time').text(minDate.toLocaleFormat('%Y-%m-%d %H:%M:%S'));
		$('.suite-ended-time').text(maxDate.toLocaleFormat('%Y-%m-%d %H:%M:%S'));
		var ended = $('.suite-ended-time').text().replace('-', '/').replace('-', '/');
		var started = $('.suite-started-time').text().replace('-', '/').replace('-', '/');
		var diff = new Date(new Date(ended) - new Date(started));
		var hours = Math.floor(diff / 36e5),minutes = Math.floor(diff % 36e5 / 60000),seconds = Math.floor(diff % 60000 / 1000);
		$('.suite-total-time-taken').text(hours + 'h ' + minutes + 'm ' + seconds + 's');
	}
	resetFilters();
	updateTotalTimeTaken();
});
function redrawCharts() {
	testsChart();
	testSetChart();
}
google.setOnLoadCallback(testSetChart);
google.setOnLoadCallback(testsChart);
function testSetChart() {
	var pass = $('.test.displayed.pass').length;
	var error = $('.test.displayed.error').length;
	var warn = $('.test.displayed.warning').length;
	var fail = $('.test.displayed.fail').length;
	var fatal = $('.test.displayed.fatal').length;
	var skip = $('.test.displayed.skip').length;
	var unknown = $('.test.displayed.unknown').length;
	$('.t-pass-count').text(pass);
	$('.t-fail-count').text(fail + fatal);
	$('.t-warning-count').text(warn);
	$('.t-fatal-count').text(fatal);
	$('.t-error-count').text(error);
	$('.t-skipped-count').text(skip);
	$('.t-others-count').text(warn + error + skip + unknown);
	var percentage = Math.round((pass * 100) / (pass + fail + fatal + warn + error + unknown + skip)) + '%';
	$('.dashboard-view .panel-lead').text(percentage);
	$('.dashboard-view .determinate').attr('style', 'width:' + percentage);
	var data = google.visualization.arrayToDataTable([
	  ['Test Status', 'Count'],
	  ['Pass', pass],
	  ['Error', error],
	  ['Warning', warn],
	  ['Fail', fail],
	  ['Fatal', fatal],
	  ['Skipped', skip],
	  ['Unknown', unknown],
	]);
	var options = {
	  backgroundColor: { fill:'transparent' },
	  chartArea: {'width': '92%', 'height': '100%'},
	  colors: ['#00af00', 'tomato', 'orange', 'red', 'darkred', '#999', '#000'],
	  fontName: 'Roboto',
	  fontSize: '11',
	  titleTextStyle: { color: '#1366d7', fontSize: '14' },
	  pieHole: 0.45,
	  height: 180,
	  pieSliceText: 'value', 
	  width: 220
	};
	var chart = new google.visualization.PieChart(document.getElementById('ts-status-dashboard'));
	chart.draw(data, options);
}
function testsChart() {
   var pass = $('.test.displayed td.status.pass').length;
	var fail = $('.test.displayed td.status.fail').length;
	var fatal = $('.test.displayed td.status.fatal').length;
	var warn = $('.test.displayed td.status.warning').length;
	var error = $('.test.displayed td.status.error').length;
	var info = $('.test.displayed td.status.info').length;
	var skip = $('.test.displayed td.status.skip').length;
	var unknown = $('.test.displayed td.status.unknown').length;
	if ($('#step-dashboard-status-filter').hasClass('pass')) { passed = 0; }
	if ($('#step-dashboard-status-filter').hasClass('fail')) { fail = 0; }
	if ($('#step-dashboard-status-filter').hasClass('fatal')) { fatal = 0; }
	if ($('#step-dashboard-status-filter').hasClass('warning')) { warn = 0; }
	if ($('#step-dashboard-status-filter').hasClass('error')) { error = 0; }
	if ($('#step-dashboard-status-filter').hasClass('info')) { info = 0; }
	if ($('#step-dashboard-status-filter').hasClass('skip')){ skipped = 0; }
	if ($('#step-dashboard-status-filter').hasClass('unknown')) { unknown = 0; }
	$('.s-pass-count').text(pass);
	$('.s-fail-count').text(fail + fatal);
	$('.s-warning-count').text(warn);
	$('.s-error-count').text(error);
	$('.s-skipped-count').text(skip);
	$('.s-others-count').text(warn + error + skip + unknown + info);
	var data = google.visualization.arrayToDataTable([
	  ['Test Status', 'Count'],
	  ['Pass', pass],
	  ['Fail', fail],
	  ['Fatal', fatal],
	  ['Error', error],
	  ['Warning', warn],
	  ['Info', info],
	  ['Skipped', skip],
	  ['Unknown', unknown]
	]);
	var options = {
	  backgroundColor: { fill:'transparent' },
	  chartArea: {'width': '92%', 'height': '100%'},
	  colors: ['#00af00', 'red', 'darkred', 'tomato', 'orange', 'dodgerblue', '#999', '#000'],
	  fontName: 'Roboto',
	  fontSize: '11',
	  titleTextStyle: { color: '#1366d7', fontSize: '14' },
	  pieHole: 0.45,
	  height: 180,
	  pieSliceText: 'value', 
	  width: 220
	};
	var chart = new google.visualization.PieChart(document.getElementById('step-status-dashboard'));
	chart.draw(data, options);
}