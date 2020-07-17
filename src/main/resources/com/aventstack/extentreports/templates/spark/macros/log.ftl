<#macro log test>
<table class="table table-sm">
  <thead><tr><th class="status-col">Status</th><th class="timestamp-col">Timestamp</th><th class="details-col">Details</th></tr></thead>
  <tbody>
    <#list test.logs as log>
      <tr class="event-row">
        <td><span class="badge log ${log.status.toLower()}-bg">${log.status?string}</span></td>
        <td>${log.timestamp?time?string}</td>
        <td>
          <#if log.exception??><textarea readonly class="code-block">${log.exception.stackTrace}</textarea>
          <#else>${log.details}</#if>
          <#if log.media??><@mediaSingle log.media /></#if>
        </td>
      </tr>
    </#list>
  </tbody>
</table>
</#macro>