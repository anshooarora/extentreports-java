<div id='dashboard-view' class='view hide'>
	<div class='card-panel transparent np-v'>
		<h5>Dashboard</h5>

		<div class='row'>
			<div class='col s2'>
				<div class='card-panel r'>
					${ parentViewChartsHeading }
					<div class='panel-lead'>${ parentCount }</div>
				</div>
			</div>
			<div class='col s2'>
				<div class='card-panel r'>
					${ childViewChartsHeading }
					<div class='panel-lead'>${ childCount  }</div>
				</div>
			</div>
			<#if grandChildViewChartsHeading != ''>
			<div class='col s2'>
				<div class='card-panel r'>
					${ grandChildViewChartsHeading }
					<div class='panel-lead'>${ grandChildCount }</div>
				</div>
			</div>
			</#if>
			<div class='col s2'>
				<div class='card-panel r'>
					Start
					<div class='panel-lead'>${ report.startTime?datetime?string["${timeStampFormat}"] }</div>
				</div>
			</div>
			<div class='col s2'>
				<div class='card-panel r'>
			 		End
			 		<div class='panel-lead'>${ report.endTime?datetime?string["${timeStampFormat}"] }</div>
				</div>
			</div>
			<div class='col s2'>
				<div class='card-panel r'>
					Time Taken
					<div class='panel-lead'>${ report.runDuration }ms</div>
				</div>
			</div>
			<#if systemAttributeContext?size != 0>
			<div class='col s4'>
				<div class='card-panel environment'>
					<span class='right label cyan white-text'>Environment</span><p>&nbsp;</p>
					
					<table>
						<tr>
							<th>Name</th>
							<th>Value</th>
						</tr>
						<#list systemAttributeContext as sa>
							<#if sa?? && sa.name?? && sa.value??>
							<tr>
								<td>${ sa.name }</td>
								<td>${ sa.value }</td>
							</tr>
							</#if>
						</#list>
					</table>
				</div>
			</div>
			</#if>
			<#if categoryContext?? && categoryContext?size != 0>
			<div class='col s4'>
				<div class='card-panel'>
					<span class='right label cyan white-text'>Categories</span><p>&nbsp;</p>
					
					<table>
						<tr>
							<th>Name</th>
							<th>Passed</th>
							<th>Failed</th>
							<th>Others</th>
						</tr>
						<#list categoryContext as category>
						<tr>
							<td>${ category.name }</td>
							<td>${ category.passed }</td>
							<td>${ category.failed }</td>
							<td>${ category.others }</td>
						</tr>
						</#list>
					</table>
				</div>
			</div>
			</#if>
		</div>
	</div>
</div>
<!-- dashboard view -->
