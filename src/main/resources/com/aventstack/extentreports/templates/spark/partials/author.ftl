<#if report.authorCtx.set?size != 0>
<#assign
	authorCtx=report.authorCtx.set>
<#compress>
<div class="test-wrapper row view author-view attributes-view">
  <div class="test-list">
    <div class="test-list-tools">
      <ul class="tools pull-left"><li><a href=""><span class="font-size-14">Authors</span></a></li></ul>
      <ul class="tools text-right"><li><a href="#"><span class="badge badge-primary">${authorCtx?size}</span></a></li></ul>
    </div>
    <div class="test-list-wrapper scrollable">
      <ul class="test-list-item">
        <#list authorCtx as ctx>
        <li class="test-item">
          <div class="test-detail">
            <span class="meta">
            <#if ctx.passed!=0><span class='badge log pass-bg'>${ctx.passed}</span></#if>
            <#if ctx.failed!=0><span class='badge log badge-danger'>${ctx.failed}</span></#if>
            <#if ctx.skipped!=0><span class='badge log badge-skip'>${ctx.skipped}</span></#if>
            <#if ctx.others!=0><span class='badge log badge-warning'>${ctx.others}</span></#if>
            </span>
            <p class="name">${ctx.attr.name}</p>
            <p class="duration text-sm">${ctx.size()} tests</p>
          </div>
          <div class="test-contents d-none">
            <div class="info">
              <h4>${ctx.attr.name}</h4>
              <#if ctx.passed!=0><span status="pass" class='badge log pass-bg'>${ctx.passed} passed</span></#if>
              <#if ctx.failed!=0><span status="fail" class='badge log badge-danger'>${ctx.failed} failed</span></#if>
              <#if ctx.skipped!=0><span status="skip" class='badge log badge-skip'>${ctx.skipped} skipped</span></#if>
              <#if ctx.others!=0><span status="skip" class='badge log badge-warning'>${ctx.others} others</span></#if>
            </div>
            <table class='table table-sm mt-4'>
              <thead>
                <tr>
                  <th class="status-col">Status</th>
                  <th class="timestamp-col">Timestamp</th>
                  <th>TestName</th>
                </tr>
              </thead>
              <tbody>
                <#list ctx.testList as test>
                <tr class="tag-test-status" status="${test.status.toLower()}">
                  <td><span class="badge log ${test.status.toLower()}-bg">${test.status?string}</span></td>
                  <td>${test.startTime?string[("HH:mm:ss a")]}</td>
                  <td class='linked' test-id='${test.getId()}'>
                    ${test.name}
                    <#if test.parent??>
                    <div class="">
                      <span class="badge badge-default">${test.getFullName()}</span>
                    </div>
                    </#if>
                  </td>
                </tr>
                </#list>
              </tbody>
            </table>
          </div>
        </li>
        </#list>
      </ul>
    </div>
  </div>
  <div class="test-content scrollable">
    <div class="test-content-tools">
      <ul>
        <li>
          <a class="back-to-test" href="#">
          <i class="fa fa-arrow-left"></i>
          </a>
        </li>
      </ul>
    </div>
    <div class="test-content-detail">
      <div class="detail-body"></div>
    </div>
  </div>
</div>
</#compress>
</#if>