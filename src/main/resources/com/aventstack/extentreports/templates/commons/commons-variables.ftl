<#assign 
  config=this.config()
  theme=config.theme?lower_case
  timeStampFormat=config.timeStampFormat
  offline=config.offlineMode
  reportType=""
  parentHeading="Tests"
  childHeading="Steps"
  grandChildHeading=""
  displayEvents=true
  chartWidth="115"
  chartHeight="90"
  chartBoxHeight="94"
  displayEvents=true>
<#if report.stats.analysisStrategy=="SUITE">
  <#assign
    parentHeading="Suite" 
    childHeading="Class" 
    grandChildHeading="Test">
</#if>
<#if report.stats.analysisStrategy=="CLASS">
  <#assign 
    parentHeading="Class"
    childHeading="Methods"
    grandChildHeading="">
</#if>
<#assign chartCount=2>
<#if report.stats.sumStat(report.stats.child) != 0><#assign chartCount=3></#if>
<#if report.stats.sumStat(report.stats.grandchild) != 0><#assign chartCount=4></#if>
<#if report.isBDD()>
  <#assign 
    reportType="bdd" 
    parentHeading="Features" 
    childHeading="Scenarios" 
    grandChildHeading="Steps"
    chartCount=3
    displayEvents=false>
</#if>
<#assign
	boxsize="col-md-"+(12/chartCount)>