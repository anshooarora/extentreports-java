<#assign sizeLarge='s12 m6 l6'>
<#if bddReport || (report.statusCount.childCount != 0 && report.statusCount.grandChildCount != 0)>
	<#assign sizeLarge='s12 m4 l4'>
</#if>
<#if parentViewChartsHeading=='Suites'>
	<#assign parentLabel='suite(s)' childLabel='test(s)' grandChildLabel='method(s)'>
<#elseif parentViewChartsHeading=='Tests'>
	<#assign parentLabel='test(s)' childLabel='step(s)' grandChildLabel=''>
<#elseif  parentViewChartsHeading=='Classes'>
	<#assign parentLabel='class(es)' childLabel='test(s)' grandChildLabel='log(s)'>
</#if>


<div id='test-view-charts' class='subview-full'>
	<div id='charts-row' class='row nm-v nm-h'>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>${ parentViewChartsHeading }</div>
				<div class='chart-box'>
					<canvas id='parent-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span class='tooltipped' data-position='top' data-tooltip='${report.statusCount.parentPercentagePass}%'><span class='strong'>${ report.statusCount.parentCountPass }</span> ${parentLabel} passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.parentPercentageFail}%'>${ report.statusCount.parentCountFail + report.statusCount.parentCountFatal }</span> ${parentLabel} failed, <span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.parentPercentageOthers}%'>${ report.statusCount.parentCountError + report.statusCount.parentCountWarning + report.statusCount.parentCountSkip }</span> others
				</div>
			</div>
		</div>
		
		<#if report.statusCount.childCount != 0>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>${ childViewChartsHeading }</div>
				<div class='chart-box'>
					<canvas id='child-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span class='tooltipped' data-position='top' data-tooltip='${report.statusCount.childPercentagePass}%'><span class='strong'>${ report.statusCount.childCountPass }</span> ${childLabel} passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.childPercentageFail}%'>${ report.statusCount.childCountFail + report.statusCount.childCountFatal }</span> ${childLabel} failed, <span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.childPercentageOthers}%'>${ report.statusCount.childCountError + report.statusCount.childCountWarning + report.statusCount.childCountSkip + report.statusCount.childCountInfo }</span> others
				</div>
			</div>
		</div>
		</#if>
		
		<#if report.statusCount.grandChildCount != 0>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>${ grandChildViewChartsHeading }</div>
				<div class='chart-box'>
					<canvas id='grandchild-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span class='tooltipped' data-position='top' data-tooltip='${report.statusCount.grandChildPercentagePass}%'><span class='strong'>${ report.statusCount.grandChildCountPass }</span> ${grandChildLabel} passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.grandChildPercentageFail}%'>${ report.statusCount.grandChildCountFail + report.statusCount.grandChildCountFatal }</span> ${grandChildLabel} failed, <span class='strong tooltipped' data-position='top' data-tooltip='${report.statusCount.grandChildPercentageOthers}%'>${ report.statusCount.grandChildCountSkip + report.statusCount.grandChildCountError + report.statusCount.grandChildCountWarning + report.statusCount.grandChildCountInfo }</span> others
				</div>
			</div>
		</div>
		</#if>
	</div>
</div>
