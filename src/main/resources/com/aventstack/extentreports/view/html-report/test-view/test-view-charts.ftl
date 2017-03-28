<#assign sizeLarge='s12 m6 l6'>
<#if bddReport || (report.statusCount.childCount != 0 && report.statusCount.grandChildCount != 0)>
	<#assign sizeLarge='s12 m4 l4'>
</#if>

<div id='test-view-charts' class='subview-full'>
	<div id='charts-row' class='row nm-v nm-h'>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>${ testsViewChartsHeading }</div>
				<div class='chart-box'>
					<canvas id='parent-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span><span class='strong'>${ report.statusCount.parentCountPass }</span> test(s) passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong'>${ report.statusCount.parentCountFail + report.statusCount.parentCountFatal }</span> test(s) failed, <span class='strong'>${ report.statusCount.parentCountError + report.statusCount.parentCountWarning + report.statusCount.parentCountSkip }</span> others
				</div>
			</div>
		</div>
		
		<#if report.statusCount.childCount != 0>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>${ stepsViewChartsHeading }</div>
				<div class='chart-box'>
					<canvas id='child-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span><span class='strong'>${ report.statusCount.childCountPass }</span> step(s) passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong'>${ report.statusCount.childCountFail + report.statusCount.childCountFatal }</span> step(s) failed, <span class='strong'>${ report.statusCount.childCountError + report.statusCount.childCountWarning + report.statusCount.childCountSkip + report.statusCount.childCountInfo }</span> others
				</div>
			</div>
		</div>
		</#if>
		
		<#if report.statusCount.grandChildCount != 0>
		<div class='col ${ sizeLarge } np-h'>
			<div class='card-panel nm-v'>
				<div class='left panel-name'>Steps</div>
				<div class='chart-box'>
					<canvas id='grandchild-analysis' width='100' height='80'></canvas>
				</div>
				<div class='block text-small'>
					<span><span class='strong'>${ report.statusCount.grandChildCountPass }</span> step(s) passed</span>
				</div>
				<div class='block text-small'>
					<span class='strong'>${ report.statusCount.grandChildCountFail + report.statusCount.grandChildCountFatal }</span> step(s) failed, <span class='strong'>${ report.statusCount.grandChildCountSkip + report.statusCount.grandChildCountError + report.statusCount.grandChildCountWarning + report.statusCount.grandChildCountInfo }</span> others
				</div>
			</div>
		</div>
		</#if>
	</div>
</div>
