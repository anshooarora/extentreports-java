<#if report.logs?size != 0>
<div class="test-wrapper row view log-view">
  <div class="col-md-12 mt-3">
  	<div class="card"><div class="card-body">
  	<#list report.logs as log>${ log }</#list>
  	</div></div>
  </div>
</div>
</#if>