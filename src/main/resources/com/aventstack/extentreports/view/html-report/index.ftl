<#assign config=report.getConfigContext()>

<#assign theme=config.containsKey('theme')?then(config.getValue('theme')?lower_case, 'standard')>
<#assign testViewChartLocation=config.containsKey('chartLocation')?then(config.getValue('chartLocation')?lower_case, 'top')>
<#assign chartVisibleOnOpen=config.containsKey('chartVisibilityOnOpen')?then(config.getValue('chartVisibilityOnOpen'), 'false')>
<#assign extentxUrl=config.containsKey('extentx-url')?then(config.getValue('extentx-url'), '')>

<#assign systemAttributeContext=report.getSystemAttributeContext().getSystemAttributeList()>
<#assign categoryContext=report.getCategoryContextInfo().getCategoryTestContextList()>
<#assign exceptionContext=report.getExceptionContextInfo().getExceptionTestContextList()>

<#assign parentCount=report.statusCount.parentCount>
<#assign childCount=report.statusCount.childCount>
<#assign grandChildCount=report.statusCount.grandChildCount>

<#assign bddReport=false>
<#assign bddClass=''>
<#if report.testList?? && report.testList?size != 0>
	<#assign firstTest=report.testList[0]>
	<#assign bddReport = (firstTest.hasChildren() && firstTest.nodeContext.get(0).isBehaviorDrivenType())?then(true, false)>
</#if>
<#assign parentViewChartsHeading='Classes' childViewChartsHeading='Tests' grandChildViewChartsHeading='Steps'>
<#assign parentLabel='class(es)' childLabel='test(s)' grandChildLabel='log(s)'>
<#if bddReport>
	<#assign parentViewChartsHeading='Features' childViewChartsHeading='Scenarios' grandChildViewChartsHeading='Steps'>
	<#assign parentLabel='feature(s)' childLabel='scenario(s)' grandChildLabel='step(s)'>
	<#assign bddClass='bdd-report'>
<#else>
	<#if (childCount == 0 || grandChildCount == 0)>
		<#assign parentViewChartsHeading='Tests' childViewChartsHeading='Steps' grandChildViewChartsHeading=''>
		<#assign parentLabel='test(s)' childLabel='step(s)' grandChildLabel=''>
	</#if>
	<#if report.analysisStrategy?string == 'SUITE'>
		<#assign parentViewChartsHeading='Suites' childViewChartsHeading='Tests' grandChildViewChartsHeading='Test Methods'>
		<#assign parentLabel='suite(s)' childLabel='test(s)' grandChildLabel='method(s)'>
	</#if>
</#if>

<!DOCTYPE html>
<html>

	<#include 'head.ftl'>

	<body class='extent ${ theme } default hide-overflow ${ bddClass }'>
		<div id='theme-selector' alt='Click to toggle theme. To enable by default, use theme configuration.' title='Click to toggle theme. To enable by default, use theme configuration.'>
			<span><i class='material-icons'>desktop_windows</i></span>
		</div>

		<#include 'nav.ftl'>

		<!-- container -->
		<div class='container'>

			<#include 'test-view/test-view.ftl'>
			<#include 'category-view/category-view.ftl'>
			<#include 'exception-view/exception-view.ftl'>			
			<#include 'dashboard-view/dashboard-view.ftl'>
			<#include 'logs-view/testrunner-logs-view.ftl'>

		</div>
		<!-- container -->

		<script>
			var statusGroup = {
				passParent: ${ report.statusCount.parentCountPass?c },
				failParent: ${ report.statusCount.parentCountFail?c },
				fatalParent: ${ report.statusCount.parentCountFatal?c },
				errorParent: ${ report.statusCount.parentCountError?c },
				warningParent: ${ report.statusCount.parentCountWarning?c },
				skipParent: ${ report.statusCount.parentCountSkip?c },
				exceptionsParent: ${ report.statusCount.parentCountExceptions?c },
				
				passChild: ${ report.statusCount.childCountPass?c },
				failChild: ${ report.statusCount.childCountFail?c },
				fatalChild: ${ report.statusCount.childCountFatal?c },
				errorChild: ${ report.statusCount.childCountError?c },
				warningChild: ${ report.statusCount.childCountWarning?c },
				skipChild: ${ report.statusCount.childCountSkip?c },
				infoChild: ${ report.statusCount.childCountInfo?c },
				exceptionsChild: ${ report.statusCount.childCountExceptions?c },
				
				passGrandChild: ${ report.statusCount.grandChildCountPass?c },
				failGrandChild: ${ report.statusCount.grandChildCountFail?c },
				fatalGrandChild: ${ report.statusCount.grandChildCountFatal?c },
				errorGrandChild: ${ report.statusCount.grandChildCountError?c },
				warningGrandChild: ${ report.statusCount.grandChildCountWarning?c },
				skipGrandChild: ${ report.statusCount.grandChildCountSkip?c },
				infoGrandChild: ${ report.statusCount.grandChildCountInfo?c },
				exceptionsGrandChild: ${ report.statusCount.grandChildCountExceptions?c },
			};
		</script>
		
		<#assign cdn = config.getValue('cdn')>
		<#if cdn == 'extentreports'>
			<script src='http://extentreports.com/resx/dist/js/extent.js' type='text/javascript'></script>
		<#else>
			<script src='${ config.getValue('protocol') }://cdn.rawgit.com/anshooarora/extentreports-java/29d2d3ec024c953e6341cb3e19e31b1035a8f556/dist/js/extent.js' type='text/javascript'></script>
		</#if>
		
		<#assign hideChart=(chartVisibleOnOpen=='true')?then(false, true)>
		<#if hideChart>
		<script type='text/javascript'>
			$(document).ready(function() {
				$('#test-view-charts').addClass('hide');
			});
		</script>
		</#if>
		
		<#if config.containsKey('js')>
 		<script type='text/javascript'>
 			${ config.getValue('js') }
 		</script>
 		</#if>
	</body>
	
</html>
