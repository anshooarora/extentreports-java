<#macro attributes test>
<#compress>
  <#if test.hasAuthor()>
    <#list test.authorSet as author>
      <span class="badge badge-pill badge-default"> ${author.name}</span>
    </#list>
  </#if>
  <#if test.hasCategory()>
    <#list test.categorySet as category>
      <span class="badge badge-pill badge-default"> ${category.name}</span>
    </#list>
  </#if>
  <#if test.hasDevice()>
    <#list test.deviceSet as device>
      <span class="badge badge-pill badge-default"> ${device.name}</span>
    </#list>
  </#if>
</#compress>
</#macro>