<#macro attributes test>
	<#if test.hasCategory()>
		<span class="category-list attr-x">
		<#list test.categoryContext.all as category>
		<span class="category badge"><i class="fa fa-tag"></i> ${category.name}</span>
		</#list>
		</span>
	</#if>
	<#if test.hasAuthor()>
		<span class="author-list attr-x">
		<#list test.authorContext.all as author>
		<span class="author badge"><i class="fa fa-user"></i> ${author.name}</span>
		</#list>
		</span>
	</#if>
	<#if test.hasDevice()>
		<span class="device-list attr-x">
		<#list test.deviceContext.all as device>
		<span class="device badge"><i class="fa fa-tablet text-sm"></i> ${device.name}</span>
		</#list>
		</span>
	</#if>
</#macro>

<#macro media media>
  <#if media?? && media?is_enumerable><#list media as m><@mediaSingle m /></#list>
  <#else><@mediaSingle media />
  </#if>
</#macro>

<#macro mediaSingle m>
  <#if m??>
    <div class="row mb-3"><div class="col-md-3">
    <#if m.base64??><img src="${m.base64}">
    <#elseif m.resolvedPath??><img data-featherlight='${m.resolvedPath}' src="${m.resolvedPath}">
    <#elseif m.path??><img data-featherlight='${m.path}' src="${m.path}">
    </#if>
    </div></div>
  </#if>
</#macro>
