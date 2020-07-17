<#compress>
<div class="side-nav">
  <div class="side-nav-inner">
    <ul class="side-nav-menu">
      <#list viewOrder as order>
    	<#if order.toString()!="logs">
    	  <#if 
    	  	order.toString()=="exception" && report.exceptionInfoCtx.hasItems()
    	  	|| order.toString()=="category" && report.categoryCtx.hasItems()
    	  	|| order.toString()=="device" && report.deviceCtx.hasItems()
    	  	|| order.toString()=="author" && report.authorCtx.hasItems()
    	  	|| order.toString()=="log" && report.logs?size != 0
    	  	|| order.toString()=="test" || order.toString()=="dashboard">
    	  <#assign ico="list">
    	  <#if order.toString()=="category"><#assign ico="tag">
    	  <#elseif order.toString()=="exception"><#assign ico="bug">
    	  <#elseif order.toString()=="device"><#assign ico="tablet">
    	  <#elseif order.toString()=="author"><#assign ico="user">
    	  <#elseif order.toString()=="dashboard"><#assign ico="bar-chart">
    	  <#elseif order.toString()=="log"><#assign ico="clipboard">
    	  </#if>
    	  <li class="nav-item dropdown" onclick="toggleView('${order.toString()}-view')">
            <a id="nav-${order.toString()}" class="dropdown-toggle" href="#">
              <span class="ico"><i class="fa fa-${ico}"></i></span>
            </a>
          </li>
          </#if>
        </#if>
      </#list>
    </ul>
  </div>
</div>
</#compress>