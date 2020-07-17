<@media test.media />
<#if test.hasAnyLog()>
<div class="detail-body mt-4">
  <#list test.generatedLog as l>${l.details}</#list>                                            
  <#if test.hasLog()><@log test=test /></#if>
</div>
</#if>
<#if test.hasChildren()>
  <div class="mt-4"><@recurse_nodes test=test /></div>
</#if>