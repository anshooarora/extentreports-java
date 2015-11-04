/* default sidenav width */
var menuWidth = 70;

/* default content container (right-side container) width */
var pinWidth = '54.5%';

/* counts */
var totalTests, passedTests, failedTests, fatalTests, warningTests, errorTests, skippedTests, unknownTests;
var totalSteps, passedSteps, failedSteps, fatalSteps, warningSteps, errorSteps, infoSteps, skippedSteps, unknownSteps;

/* global chart instance vars */
var testChart, stepChart;

function hideElement(el) {
	el.removeClass('displayed').addClass('hide');
}

function showElement(el) {
	el.addClass('displayed').removeClass('hide');
}

$(document).ready(function() {
	/* init */
	$('.button-collapse').sideNav({ menuWidth: menuWidth });
	$('select').material_select();
	$('#enableDashboard').prop('checked', false);
	
	/* [WINDOW] */
	$(window).scroll(function() {
		var scrollTop = $('.charts').is(':visible') ? 425 : 100;
		
		if ($(window).scrollTop() > scrollTop) {
			var margin = $('.charts').is(':visible') ? '-335px' : '-45px';
			$('.details-view').css('position', 'fixed').css('margin-top', margin);
		} 
		else {
			$('.details-view').removeAttr('style').css('position', 'absolute');
		}
		
		$('.pin').css('width', pinWidth);
	});
	
	/* [TOPNAV] */
	$('#enableDashboard').click(function() {
		$(this).toggleClass('enabled');
		$('#dashboard-view').toggleClass('hide').children('div').toggleClass('hide').siblings('.charts').toggleClass('hide');
	});
	
	/* menu-toggle [SIDE-NAV] */
	$('.menu-toggle').click(function() {
		menuWidth = menuWidth > 100 ? 70 : 240;
		
		if (pinWidth == '54.5%') { pinWidth = '49.5%'; }
		else { pinWidth = '54.5%'; }
		
		$('.logo .left, .side-nav input, .side-nav label').toggleClass('hide');
		
		$('.side-nav').animate({
			width: menuWidth + 'px'
		}, 200);
		
		$('.container, nav').animate({
			'padding-left': menuWidth + 'px'
		}, 200);
		
		$('.pin').animate({
			'width': pinWidth
		}, 200);
	});
	
	/* side-nav navigation [SIDE-NAV] */
	$('.analysis').click(function() {
		$('.container > .row, .nav-right > .test-view-only').addClass('hide');
		
		var el = $(this);
		var cls = el.children('a').prop('class');
		
		$('#' + cls).removeClass('hide');
		
		if (cls == 'test-view') { 
			$('.nav-right > .test-view-only').removeClass('hide');
			
			if ($('#enableDashboard').hasClass('enabled') && !$('#dashboard-view > .charts').is(':visible')) {
				$('#enableDashboard').click().prop('checked', true).addClass('enabled');
			}
		}
		else {
			$('#dashboard-view > div').removeClass('hide');
		}
		
		$('#slide-out > .analysis').removeClass('active');
		el.addClass('active');
	});
	
	/* test-dashboard settings [DASHBOARD] */
	$('#dashboard-view .test-count-setting').click(function() {
		$('#test-count-setting').openModal();
	});
	
	/* test count setting */
		/* init */
		$('#parentWithoutNodesAndNodes').click();
	$('#test-count-setting input').click(function() {
		$('#test-count-setting').removeClass('parentWithoutNodes parentWithoutNodesAndNodes childNodes');
		$('#test-count-setting').addClass($(this).prop('id'));
	});
	
	/* step-dashboard settings [DASHBOARD] */
	$('#dashboard-view .step-status-filter').click(function() {
		$('#step-status-filter').openModal();
	});
	
	/* refresh charts when chart setting is saved */
	$('.modal-footer').click(function() {
		redrawCharts();
	});
	
	/* check all checkboxes for step-dashboard filter to allow filtering the steps to be displayed [DASHBOARD] */
	$('#step-status-filter input').prop('checked', 'checked');
	$('#step-status-filter input').click(function() {
	   $('#step-status-filter').toggleClass($(this).prop('id').replace('step-dashboard-filter-', ''));
	});
	
	/* hide category menu if no categories exist [CATEGORIES] */
	if ($('.category').length == 0) {
		$('#slide-out > .analysis > .categories-view, .category-summary-view').addClass('hide').css('display', 'none');
	}
	/* view cat info [CATEGORIES] */
	$('.category-item').click(function() {
		if ($('#cat-details-wrapper .cat-container').find('.cat-body').length > 0) {
			$('.category-item').filter(function() {
				return ($(this).find('.cat-body').length == 0);
			}).append($('#cat-details-wrapper .cat-container').find('.cat-body'));
		}
		
		$('#cat-collection .category-item').removeClass('active');
		$('#cat-details-wrapper .cat-container .cat-body').remove();
		
		var el = $(this).addClass('active').find('.cat-body');
		$('#cat-details-wrapper .cat-name').text($(this).find('.category-name').text());
		$('#cat-details-wrapper .cat-container').append($(el));
	});
	$('.category-item').eq(0).click();
	
	/* navigation from cat-view to test-details [CATEGORIES] */
	$('.category-link').click(function() {
		var id = $(this).attr('extentid');
		findTestByNameId($(this).text().trim(), id);
	});
	
	/* view test info [TEST] */
	$('.test').click(function() {
		var t = $(this);
		
		if ($('#test-details-wrapper .test-body').length > 0) {
			$('#test-collection .test').filter(function() {
				return ($(this).find('.test-body').length == 0);
			}).append($('#test-details-wrapper .test-body'));
		}
		
		$('#test-collection .test').removeClass('active');
		$('#test-details-wrapper .test-body').remove();
		
		var el = t.addClass('active').find('.test-body');
		$('#test-details-wrapper .details-name').text(t.find('.test-name').text());
		$('#test-details-wrapper .details-container').append($(el));
	});
	$('.test').eq(0).click();
	
	/* filter tests by text [TEST] */
	$(document).keypress(function(e) {
		if(e.which == 13) {
			if ($('#searchTests').is(':focus')) {
				var txt = $('#searchTests').val().toLowerCase();
				
				hideElement($('#test-collection .test'));
				
				$('.test-name, .test-desc').each(function() {
					var t = $(this);
					
					if (t.text().toLowerCase().indexOf(txt) >= 0) {
						showElement(t.closest('.test'));
					}
				});
				
				$('.test-node-name').each(function() {
					var t = $(this);
					if (t.text().toLowerCase().indexOf(txt) >= 0) {
						showElement(t.closest('.test'));
					}                               
				});
				
				$('.details-container .test-node-name').each(function() {
					if ($(this).text().toLowerCase().indexOf(txt) >= 0) {
						showElement($('.test.active'));
					}
				});
				
				$('.test:visible').eq(0).click();
			}
			if ($('#searchCats').is(':focus')) {
				var txt = $('#searchCats').val().toLowerCase();
				hideElement($('#cat-collection .category-item '));
				
				$('#cat-collection .category-name').each(function() {
					var t = $(this);
					
					if (t.text().toLowerCase().indexOf(txt) >= 0) {
						showElement(t.closest('.category-item'));
					}
				});
				
				$('.category-item:visible').eq(0).click();
			}
		}
	});
	
	$('.clear').click(function() {
		var t = $(this);
		
		var id = t.prev().val('').attr('id');
		
		if (id == 'searchTests') {
			$('#test-collection .test, .test-node').removeClass('hide').addClass('displayed');
		}
		else {
			$('#cat-collection .category-item').removeClass('hide').addClass('displayed');
		}
		t.next().removeClass('active');
		t.prev().removeClass('valid');
	});
	
	/* if only header row is available for test, hide the table [TEST] */
	$('.table-results').filter(function() {
		return ($(this).find('tr').length == 1);
	}).hide(0);
	
	/* clicking a section on pie chart will automatically filter tests by status */
	$('#test-analysis').click(
		function(evt) {
			var label = testChart.getSegmentsAtEvent(evt)[0].label;
			$('.tests-toggle li').filter(
				function() {
					return ($(this).text() == label);
				}
			).click();
		}
	);
	
	/* clicking the category tag will automatically filter tests by category */
	$('.category').click(
		function(evt) {
			var label = $(this).text();
			$('.category-toggle li').filter(
				function() {
					return ($(this).text() == label);
				}
			).click();
		}
	);
	
	/* filter tests by status [TEST] */
	$('.tests-toggle li').click(function() {
		var opt = $(this).text().toLowerCase();
		var opt2 = $('.category-toggle li.active').text().toLowerCase();
		
		if (opt2 == 'choose your option' || opt2 == 'clear filters') {
			opt2 = '';
		}
		
		if (opt != 'choose your option') {
			if (opt == 'clear filters') {
				resetFilters();
			}
			else {
				$('.tests-toggle li').removeClass('active');
				$(this).addClass('active');
				$('.test').hide(0).removeClass('displayed');
				
				if (opt2 != '') {
					$('.test').each(function() {
						var t = $(this);
						
						if (t.find('.category-assigned').hasClass(opt2)) {
							if (t.hasClass(opt) || t.has('.test-node.' + opt).length > 0 || (t.hasClass('active') && $('#test-details-wrapper .test-node.' + opt).length > 0)) {
								t.addClass('displayed').show(0);                                        
							}
						}
					});
				} 
				else {
					var activeTest = '';
					if ($('#test-details-wrapper .test-node.' + opt).length > 0) {
						activeTest = '.test.active,';
					}
					$('.test').hide(0).removeClass('displayed');
					$(activeTest + '.test:has(.test-node.' + opt + '), .test.' + opt).fadeIn(200).addClass('displayed');
				}
				
				redrawCharts();
			}
		}
	});
	
	/* filter tests by category [TEST] */
	$('.category-toggle li').click(function() {
		var opt = $(this).text().toLowerCase();
		var opt2 = $('.tests-toggle li.active').text().toLowerCase();
		
		if (opt2 == 'choose your option' || opt2 == 'clear filters') {
			opt2 = '';
		}
		
		if (opt != 'choose your option') {
			if (opt == 'clear filters') {
				resetFilters();
			} 
			else {
				$('.category-toggle li').removeClass('active');
				$(this).addClass('active');
				$('.test').hide(0).removeClass('displayed');
				
				if (opt2 != '') {
					$('.test').each(function() {
						var t = $(this);
						
						if (t.find('.category-assigned').hasClass(opt)) {
							if (t.hasClass(opt2) || t.has('.test-node.' + opt2).length > 0 || (t.hasClass('active') && $('#test-details-wrapper .test-node.' + opt2).length > 0)) {
								t.addClass('displayed').show(0);                                        
							}
						}
					});
				} 
				else {
					$('.test').each(function() {
						if ($(this).find('.category-assigned').hasClass(opt)) {
							$(this).fadeIn(200).addClass('displayed');
						}
					});
				}
				redrawCharts();
			}
		}                                
	});

	/* hide testrunner-logs view if no logs added [TESTRUNNER-LOGS] */
	if ($('#testrunner-logs-view .card-panel > p').length == 0) {
		$('.analysis > .testrunner-logs-view').addClass('hide');
	}
	
	$('#enableDashboard').click();
	
	/* reset test/category filters on document load */
	resetFilters();
});

/* action to perform when 'Clear Filters' option is selected [TEST] */
function resetFilters() {
	$('.dropdown-content li').removeClass('active');
	$('.test').addClass('displayed').show(0);
	redrawCharts();
	$('.dropdown-content li:first-child').addClass('active').click();
}

/* formats date in mm-dd-yyyy hh:mm:ss [UTIL] */
function formatDt(d) {
	return d.getFullYear() + '-' +
		('00' + (d.getMonth() + 1)).slice(-2) + '-' + 
		('00' + d.getDate()).slice(-2) + ' ' + 
		('00' + d.getHours()).slice(-2) + ':' + 
		('00' + d.getMinutes()).slice(-2) + ':' + 
		('00' + d.getSeconds()).slice(-2);
}

/* finds test by its name and extentId  [UTIL] */
function findTestByNameId(name, id) {
	$('.test').each(function() {
		if ($(this).find('.test-name').text().trim() == name && $(this).attr('extentid') == id) {
			$('.analysis > .test-view').click();
			$('html, body').animate({
				scrollTop: $('.details-name').offset().top
			}, 400);
			$(this).click();
			return;
		}
	});
}

/* refresh and redraw charts [DASHBOARD] */
function redrawCharts() {
	if (!$('#dashboard-view .charts').is(':visible')) {
		return;
	}
	refreshData();
	testChart.segments[0].value = passedTests;
	testChart.segments[1].value = failedTests;
	testChart.segments[2].value = fatalTests;
	testChart.segments[3].value = errorTests;
	testChart.segments[4].value = warningTests;
	testChart.segments[5].value = skippedTests;
	testChart.segments[6].value = unknownTests;
	stepChart.segments[0].value = passedSteps;
	stepChart.segments[1].value = infoSteps;
	stepChart.segments[2].value = failedSteps;
	stepChart.segments[3].value = fatalSteps;
	stepChart.segments[4].value = errorSteps;
	stepChart.segments[5].value = warningSteps;
	stepChart.segments[6].value = skippedSteps;
	stepChart.segments[7].value = unknownSteps;
	$('#test-analysis, #step-analysis').html('');
	$('ul.doughnut-legend').remove();
	testsChart();
	stepsChart();
	$('ul.doughnut-legend').addClass('right');
}

/* update data for dashboard [DASHBOARD] */
function refreshData() {
	var el = $('#test-count-setting');
	
	totalTests = $('.test:not(:has(.test-node)), .test-node').length;
	passedTests = $('.details-container .test-node.pass, .test.displayed .test-node.pass, .test.displayed.pass:not(.hasChildren)').length;
	failedTests = $('.details-container .test-node.fail, .test.displayed .test-node.fail, .test.displayed.fail:not(.hasChildren)').length;
	fatalTests = $('.details-container .test-node.fatal, .test.displayed .test-node.fatal, .test.displayed.fatal:not(.hasChildren)').length;
	warningTests = $('.details-container .test-node.warning, .test.displayed .test-node.warning, .test.displayed.warning:not(.hasChildren)').length;
	errorTests = $('.details-container .test-node.error, .test.displayed .test-node.error, .test.displayed.error:not(.hasChildren)').length;
	skippedTests = $('.details-container .test-node.skip, .test.displayed .test-node.skip, .test.displayed.skip:not(.hasChildren)').length;
	unknownTests = $('.details-container .test-node.unknown, .test.displayed .test-node.unknown, .test.displayed.unknown:not(.hasChildren)').length;
	
	if (el.hasClass('parentWithoutNodes')) {
		totalTests = $('.test.displayed').length;
		passedTests = $('.test.displayed.pass').length;
		failedTests = $('.test.displayed.fail').length;
		fatalTests = $('.test.displayed.fatal').length;
		warningTests = $('.test.displayed.warning').length;
		errorTests = $('.test.displayed.error').length;
		skippedTests = $('.test.displayed.skip').length;
		unknownTests = $('.test.displayed.unknown').length;
	}
	else if (el.hasClass('childNodes')) {
		totalTests = $('.test-node').length;
		passedTests = $('.test.displayed .test-node.pass, .details-container .test-node.pass').length;
		failedTests = $('.test.displayed .test-node.fail, .details-container .test-node.fail').length;
		fatalTests = $('.test.displayed .test-node.fatal, .details-container .test-node.fatal').length;
		warningTests = $('.test.displayed .test-node.warning, .details-container .test-node.warning').length;
		errorTests = $('.test.displayed .test-node.error, .details-container .test-node.error').length;
		skippedTests = $('.test.displayed .test-node.skip, .details-container .test-node.skip').length;
		unknownTests = $('.test.displayed .test-node.unknown, .details-container .test-node.unknown').length;
	}
	
	totalSteps = $('td.status').length;
	passedSteps = $('td.status.pass').length;
	failedSteps = $('td.status.fail').length;
	fatalSteps = $('td.status.fatal').length;
	warningSteps = $('td.status.warning').length;
	errorSteps = $('td.status.error').length;
	infoSteps = $('td.status.info').length;
	skippedSteps = $('td.status.skip').length;
	unknownSteps = $('td.status.unknown').length;
	
	$('.t-pass-count').text(passedTests);
	$('.t-fail-count').text(failedTests + fatalTests);
	$('.t-warning-count').text(warningTests);
	$('.t-fatal-count').text(fatalTests);
	$('.t-error-count').text(errorTests);
	$('.t-skipped-count').text(skippedTests);
	$('.t-others-count').text(warningTests + errorTests + skippedTests + unknownTests);
	
	var percentage = Math.round((passedTests * 100) / (passedTests + failedTests + fatalTests + warningTests + errorTests + unknownTests + skippedTests)) + '%';
	$('.pass-percentage.panel-lead').text(percentage);
	$('#dashboard-view .determinate').attr('style', 'width:' + percentage);
	
	if ($('#step-status-filter').hasClass('pass')) { passedSteps = 0; }
	if ($('#step-status-filter').hasClass('fail')) { failedSteps = 0; }
	if ($('#step-status-filter').hasClass('fatal')) { fatalSteps = 0; }
	if ($('#step-status-filter').hasClass('warning')) { warningSteps = 0; }
	if ($('#step-status-filter').hasClass('error')) { errorSteps = 0; }
	if ($('#step-status-filter').hasClass('info')) { infoSteps = 0; }
	if ($('#step-status-filter').hasClass('skip')){ skippedSteps = 0; }
	if ($('#step-status-filter').hasClass('unknown')) { unknownSteps = 0; }
	
	$('.s-pass-count').text(passedSteps);
	$('.s-fail-count').text(failedSteps + fatalSteps);
	$('.s-warning-count').text(warningSteps);
	$('.s-error-count').text(errorSteps);
	$('.s-skipped-count').text(skippedSteps);
	$('.s-others-count').text(warningSteps + errorSteps + skippedSteps + unknownSteps + infoSteps);
	$('.total-tests > .panel-lead').text(totalTests);
	$('.total-steps > .panel-lead').text(totalSteps);
}

/* dashboard chart options [DASHBOARD] */
var options = {
	segmentShowStroke : true, 
	segmentStrokeColor : '#eee', 
	segmentStrokeWidth : 1, 
	percentageInnerCutout : 55, 
	animationSteps : 30, 
	animationEasing : 'easeOutBounce', 
	animateRotate : true, 
	animateScale : false,
	legendTemplate : '<ul class=\'<%=name.toLowerCase()%>-legend\'><% for (var i=0; i<segments.length; i++){%><li><span style=\'background-color:<%=segments[i].fillColor%>\'></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>'
};

/* tests view chart [DASHBOARD] */
function testsChart() {
	var data = [
		{ value: passedTests, color: '#00af00', highlight: '#32bf32', label: 'Pass' },
		{ value: failedTests, color:'#F7464A', highlight: '#FF5A5E', label: 'Fail' },
		{ value: fatalTests, color:'#8b0000', highlight: '#a23232', label: 'Fatal' },
		{ value: errorTests, color:'#ff6347', highlight: '#ff826b', label: 'Error' },
		{ value: warningTests, color: '#FDB45C', highlight: '#FFC870', label: 'Warning' },
		{ value: skippedTests, color: '#1e90ff', highlight: '#4aa6ff', label: 'Skip' },
		{ value: unknownTests, color: '#222', highlight: '#444', label: 'Unknown' }
	];
		
	var ctx = $('#test-analysis').get(0).getContext('2d');
	testChart = new Chart(ctx).Doughnut(data, options);
	var legendHolder = drawLegend(testChart, 'test-analysis');
}
  
/* steps view chart [DASHBOARD] */
function stepsChart() {
	var data = [
		{ value: passedSteps, color: '#00af00', highlight: '#32bf32', label: 'Pass' },
		{ value: infoSteps, color: '#46BFBD', highlight: '#5AD3D1', label: 'Info' },
		{ value: failedSteps, color:'#F7464A', highlight: '#FF5A5E', label: 'Fail' },
		{ value: fatalSteps, color:'#8b0000', highlight: '#a23232', label: 'Fatal' },
		{ value: errorSteps, color:'#ff6347', highlight: '#ff826b', label: 'Error' },
		{ value: warningSteps, color: '#FDB45C', highlight: '#FFC870', label: 'Warning' },
		{ value: skippedSteps, color: '#1e90ff', highlight: '#4aa6ff', label: 'Skip' },
		{ value: unknownSteps, color: '#222', highlight: '#444', label: 'Unknown' }
	];
	
	var ctx = $('#step-analysis').get(0).getContext('2d');
	stepChart = new Chart(ctx).Doughnut(data, options);
	drawLegend(stepChart, 'step-analysis');
}

/* draw legend for test and step charts [DASHBOARD] */
function drawLegend(chart, id) {
	var helpers = Chart.helpers;
	var legendHolder = document.getElementById(id);
	legendHolder.innerHTML = chart.generateLegend();
	
	helpers.each(legendHolder.firstChild.childNodes, function(legendNode, index) {
		helpers.addEvent(legendNode, 'mouseover', function() {
			var activeSegment = chart.segments[index];
			activeSegment.save();
			activeSegment.fillColor = activeSegment.highlightColor;
			chart.showTooltip([activeSegment]);
			activeSegment.restore();
		});
	});
	
	Chart.helpers.addEvent(legendHolder.firstChild, 'mouseout', function() {
		chart.draw();
	});
	$('#' + id).after(legendHolder.firstChild);
  }
  
  testsChart(); stepsChart();
  $('ul.doughnut-legend').addClass('right');
  redrawCharts();
  
  $('#dashboard-view').addClass('hide');