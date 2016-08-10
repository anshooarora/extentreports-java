<#assign config=report.getConfigContext()>

<#assign theme=config.containsKey('theme')?then(config.getValue('theme')?lower_case, 'standard')>
<#assign testViewChartLocation=config.containsKey('chartLocation')?then(config.getValue('chartLocation')?lower_case, 'top')>
<#assign chartVisibleOnOpen=config.containsKey('chartVisibilityOnOpen')?then(config.getValue('chartVisibilityOnOpen'), 'false')>

<#assign systemAttributeContext=report.getSystemAttributeContext().getSystemAttributeList()>
<#assign categoryContext=report.getCategoryContextInfo().getCategoryTestContextList()>
<#assign exceptionContext=report.getExceptionContextInfo().getExceptionTestContextList()>

<#assign bddReport=false>
<#assign bddClass=''>
<#if report.testList?? && report.testList?size != 0>
	<#assign firstTest=report.testList[0]>
	<#assign bddReport = (firstTest.hasChildren() && firstTest.nodeContext.get(0).isBehaviorDrivenType())?then(true, false)>
</#if>
<#assign testsViewChartsHeading='Tests' stepsViewChartsHeading='Steps'>
<#if bddReport>
	<#assign testsViewChartsHeading='Features' stepsViewChartsHeading='Scenarios'>
	<#assign bddClass='bdd-report'>
</#if>

<#assign parentCount=report.statusCount.parentCount>
<#assign childCount=report.statusCount.childCount>
<#assign grandChildCount=report.statusCount.grandChildCount>

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
				passParent: ${ report.statusCount.parentCountPass },
				failParent: ${ report.statusCount.parentCountFail },
				fatalParent: ${ report.statusCount.parentCountFatal },
				errorParent: ${ report.statusCount.parentCountError },
				warningParent: ${ report.statusCount.parentCountWarning },
				skipParent: ${ report.statusCount.parentCountSkip },
				unknownParent: ${ report.statusCount.parentCountUnknown },
				
				passChild: ${ report.statusCount.childCountPass },
				failChild: ${ report.statusCount.childCountFail },
				fatalChild: ${ report.statusCount.childCountFatal },
				errorChild: ${ report.statusCount.childCountError },
				warningChild: ${ report.statusCount.childCountWarning },
				skipChild: ${ report.statusCount.childCountSkip },
				unknownChild: ${ report.statusCount.childCountUnknown },
				infoChild: ${ report.statusCount.childCountInfo },
				
				passGrandChild: ${ report.statusCount.grandChildCountPass },
				failGrandChild: ${ report.statusCount.grandChildCountFail },
				fatalGrandChild: ${ report.statusCount.grandChildCountFatal },
				errorGrandChild: ${ report.statusCount.grandChildCountError },
				warningGrandChild: ${ report.statusCount.grandChildCountWarning },
				skipGrandChild: ${ report.statusCount.grandChildCountSkip },
				unknownGrandChild: ${ report.statusCount.grandChildCountUnknown },
				infoGrandChild: ${ report.statusCount.grandChildCountInfo }
			};
		</script>

		<script src='${ config.getValue('protocol') }://cdn.rawgit.com/anshooarora/extentreports-java/3.0.0-dev/dist/js/extent.js' type='text/javascript'></script>
		
		<#assign hide=(chartVisibleOnOpen=='true')?then(false, true)>
		<#if hide>
		<script type='text/javascript'>
			$(document).ready(function() {
				$('#test-view-charts').addClass('hide');
			});
		</script>
		</#if>
	</body>
	
</html>