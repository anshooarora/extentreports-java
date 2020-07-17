<script>
	function drawChart(ctx, config) {
	    ctx.width = 100;
	    ctx.height = 80;
	    new Chart(ctx, config);
	}
	$(document).ready(function() {
		(function drawParentChart() {
			var config = {
		        type: 'doughnut',
		            data: {
		                datasets: [{
		                    borderColor: 'transparent',
		                    data: [
		                    	${report.reportStatusStats.parentCountPass?c}, ${report.reportStatusStats.parentCountFail?c}, ${report.reportStatusStats.parentCountFatal?c}, ${report.reportStatusStats.parentCountError?c}, ${report.reportStatusStats.parentCountWarning?c}, ${report.reportStatusStats.parentCountSkip?c}
		                    ],
		                    backgroundColor: [
		                        "#00af00", "#F7464A", "#8b0000", "#ff6347", "#FDB45C", "#1e90ff"
		                    ]
		                }],
		                labels: [ "Pass", "Fail", "Fatal", "Error", "Warning", "Skip" ]
		            },
		            options: {
		            	responsive: true,
		            	  maintainAspectRatio: false,
		            	  legend: {
		            	      position: "right",
		            	      labels: {
		            	    	  boxWidth: 7,
		            	          fontSize: 12,
		            	          fontColor: "#999"
		            	      }
		            	  },
		            	  cutoutPercentage: 70
		            }
		        };
	
		        var ctx = document.getElementById("pc").getContext('2d');
		        drawChart(ctx,config);
		})();
		(function drawChildChart() {
			if (document.getElementById("cc")!=null) {
				var config = {
			        type: 'doughnut',
			            data: {
			                datasets: [{
			                    borderColor: 'transparent',
			                    data: [
			                    	${report.reportStatusStats.childCountPass?c}, ${report.reportStatusStats.childCountFail?c}, ${report.reportStatusStats.childCountFatal?c}, ${report.reportStatusStats.childCountError?c}, ${report.reportStatusStats.childCountWarning?c}, ${report.reportStatusStats.childCountSkip?c}
			                    ],
			                    backgroundColor: [
			                        "#00af00", "#F7464A", "#8b0000", "#ff6347", "#FDB45C", "#1e90ff"
			                    ]
			                }],
			                labels: [ "Pass", "Fail", "Fatal", "Error", "Warning", "Skip" ]
			            },
			            options: {
			            	responsive: true,
			            	  maintainAspectRatio: false,
			            	  legend: {
			            	      position: "right",
			            	      labels: {
			            	    	  boxWidth: 7,
			            	          fontSize: 12,
			            	          fontColor: "#999"
			            	      }
			            	  },
			            	  cutoutPercentage: 70
			            }
			        };
	
		        var ctx = document.getElementById("cc").getContext('2d');
		        drawChart(ctx,config);
			}
		})();
		(function drawGrandChildChart() {
			if (document.getElementById("gcc")!=null) {
				var config = {
			        type: 'doughnut',
			            data: {
			                datasets: [{
			                    borderColor: 'transparent',
			                    data: [
			                    	${report.reportStatusStats.grandChildCountPass?c}, ${report.reportStatusStats.grandChildCountFail?c}, ${report.reportStatusStats.grandChildCountFatal?c}, ${report.reportStatusStats.grandChildCountError?c}, ${report.reportStatusStats.grandChildCountWarning?c}, ${report.reportStatusStats.grandChildCountSkip?c}
			                    ],
			                    backgroundColor: [
			                        "#00af00", "#F7464A", "#8b0000", "#ff6347", "#FDB45C", "#1e90ff"
			                    ]
			                }],
			                labels: [ "Pass", "Fail", "Fatal", "Error", "Warning", "Skip" ]
			            },
			            options: {
			            	responsive: true,
			            	  maintainAspectRatio: false,
			            	  legend: {
			            	      position: "right",
			            	      labels: {
			            	    	  boxWidth: 7,
			            	          fontSize: 12
			            	      }
			            	  },
			            	  cutoutPercentage: 70
			            }
			        };
		
		        var ctx = document.getElementById("gcc").getContext('2d');
		        drawChart(ctx,config);
			}
		})();
	})
	<#macro listTestNameDuration testList><#if report.testList??><#list report.testList as t>"${t.name}":${(t.timeTaken()/1000)?c?replace(",","")}<#if t_has_next>,</#if></#list></#if></#macro>
    var timeline = {
        <@listTestNameDuration testList=report.testList />
    };
    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
        	color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }
    (function drawTimelineChart() {
        var datasets = [];
        for (var key in timeline) {
            datasets.push({ label:key, data: [ timeline[key] ], backgroundColor: getRandomColor(), borderWidth: 1 });
        }
        var ctx = document.getElementById('timeline').getContext('2d');
        new Chart(ctx, {
            type: 'horizontalBar',
            data: {
                datasets: datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                tooltips: {
                    mode: 'point'
                },
                scales: {
                    xAxes: [{
                        stacked: true,
                        gridLines: false
                    }],
                    yAxes: [{
                        stacked: true,
                        gridLines: false,
                        barThickness: 15
                    }]
                },
                legend: {
                    display: false
                }
            }
        });
    })();
</script>