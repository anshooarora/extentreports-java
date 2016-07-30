<#if report.testRunnerLogs?? && report.testRunnerLogs?size != 0>
<div id='testrunner-logs-view' class='view hide'>
	<div class='card-panel transparent np-v'>
		<h5>TestRunner Logs</h5>
		
		<div class='card-panel'>
			<#list report.testRunnerLogs as log>
				${ log }
			</#list>
		</div>
	</div>
</div>
</#if>
<!-- testrunner-logs view -->