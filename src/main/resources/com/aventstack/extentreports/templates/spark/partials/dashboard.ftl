<#compress>
<div class="container-fluid p-4 view dashboard-view">
  <div class="row">
    <div class="col-md-3">
      <div class="card"><div class="card-body">
          <p class="m-b-0">Started</p>
          <h3>${report.startTime?datetime?string["${timeStampFormat}"]}</h3>
      </div></div>
    </div>
    <div class="col-md-3">
      <div class="card"><div class="card-body">
          <p class="m-b-0">Ended</p>
          <h3>${report.endTime?datetime?string["${timeStampFormat}"]}</h3>
      </div></div>
    </div>
    <div class="col-md-3">
      <div class="card"><div class="card-body">
          <p class="m-b-0 text-pass">${parentHeading} Passed</p>
          <h3>${report.stats.parent?api.get(Status.PASS)?c}</h3>
      </div></div>
    </div>
    <div class="col-md-3">
      <div class="card"><div class="card-body">
          <p class="m-b-0 text-fail">${parentHeading} Failed</p>
          <h3>${report.stats.parent?api.get(Status.FAIL)?c}</h3>
      </div></div>
    </div>
  </div>
  <div class="row">
    <div class="${boxsize}">
      <div class="card">
        <div class="card-header">
          <h6 class="card-title">${parentHeading}</h6>
        </div>
        <div class="card-body">
          <div class="">
            <canvas id='parent-analysis' width='${chartWidth}' height='${chartHeight}'></canvas>
          </div>
        </div>
        <div class="card-footer">
          <div><small data-tooltip='${report.stats.parentPercentage?api.get(Status.PASS)}%'>
            <b>${report.stats.parent?api.get(Status.PASS)}</b> ${parentHeading?lower_case} passed
            </small>
          </div>
          <div>
            <small data-tooltip='${report.stats.parentPercentage?api.get(Status.FAIL)}%'><b>${report.stats.parent?api.get(Status.FAIL)}</b> ${parentHeading?lower_case} failed, 
            <b>${report.stats.parent?api.get(Status.SKIP)}</b> skipped, <b data-tooltip='${report.stats.parentPercentage?api.get(Status.WARNING)}%'>${report.stats.parent?api.get(Status.WARNING)}</b> others
            </small>
          </div>
        </div>
      </div>
    </div>
    <#if report.stats.sumStat(report.stats.child) != 0>
    <div class="${boxsize}">
      <div class="card">
        <div class="card-header">
          <h6 class="card-title">${childHeading}</h6>
        </div>
        <div class="card-body">
          <div class="">
            <canvas id='child-analysis' width='${chartWidth}' height='${chartHeight}'></canvas>
          </div>
        </div>
        <div class="card-footer">
          <div><small data-tooltip='${report.stats.childPercentage?api.get(Status.PASS)}%'><b>${report.stats.child?api.get(Status.PASS)}</b> ${childHeading?lower_case} passed</small></div>
          <div>
            <small data-tooltip='${report.stats.childPercentage?api.get(Status.FAIL)}%'><b>${report.stats.child?api.get(Status.FAIL)}</b> ${childHeading?lower_case} failed, 
            <b>${report.stats.child?api.get(Status.SKIP)}</b> skipped, <b data-tooltip='%'>${report.stats.child?api.get(Status.WARNING) + report.stats.child?api.get(Status.INFO)}</b> others
            </small>
          </div>
        </div>
      </div>
    </div>
    </#if>
    <#if report.stats.sumStat(report.stats.grandchild) != 0>
    <div class="${boxsize}">
      <div class="card">
        <div class="card-header">
          <h6 class="card-title">${grandChildHeading}</h6>
        </div>
        <div class="card-body">
          <div class="">
            <canvas id='grandchild-analysis' width='${chartWidth}' height='${chartHeight}'></canvas>
          </div>
        </div>
        <div class="card-footer">
          <div><small data-tooltip='${report.stats.grandchildPercentage?api.get(Status.PASS)}%'><b>${report.stats.grandchild?api.get(Status.PASS)}</b> ${grandChildHeading?lower_case} passed</small></div>
          <div>
            <small data-tooltip='${report.stats.grandchildPercentage?api.get(Status.FAIL)}%'><b>${report.stats.grandchild?api.get(Status.FAIL)}</b> ${grandChildHeading?lower_case} failed, 
            <b>${report.stats.grandchild?api.get(Status.SKIP)}</b> skipped, <b data-tooltip='%'>${report.stats.grandchild?api.get(Status.WARNING) + report.stats.grandchild?api.get(Status.INFO)}</b> others
            </small>
          </div>
        </div>
      </div>
    </div>
    </#if>
    <#if report.stats.sumStat(report.stats.log) != 0 && displayEvents>
    <div class="${boxsize}">
      <div class="card">
        <div class="card-header">
          <h6 class="card-title">Log events</h6>
        </div>
        <div class="card-body">
          <div class="">
            <canvas id='events-analysis' width='${chartWidth}' height='${chartHeight}'></canvas>
          </div>
        </div>
        <div class="card-footer">
          <div><small data-tooltip='${report.stats.logPercentage?api.get(Status.PASS)}%'><b>${report.stats.log?api.get(Status.PASS)}</b> events passed</small></div>
          <div>
            <small data-tooltip='${report.stats.logPercentage?api.get(Status.FAIL)}%'><b>${report.stats.log?api.get(Status.FAIL)}</b> events failed, 
            <b data-tooltip='%'>${report.stats.log?api.get(Status.WARNING) + report.stats.log?api.get(Status.SKIP) + report.stats.log?api.get(Status.INFO)}</b> others
            </small>
          </div>
        </div>
      </div>
    </div>
    </#if>
  </div>
  <div class="row">
    <#if report.authorCtx.set?size != 0>
    <div class="col-md-4 author-container">
      <div class="card">
        <div class="card-header"><p>Author</p></div>
        <div class="card-body pb-0 pt-0"><table class="table table-sm table-bordered">
          <thead><tr class="bg-gray"><th>Name</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Others</th><th>Passed %</th></tr></thead>
          <tbody>
            <#list report.authorCtx.set as author>
            <tr>
              <td>${author.attr.name}</td>
              <td>${author.passed}</td>
              <td>${author.failed}</td>
              <td>${author.skipped}</td>
              <td>${author.others}</td>
              <td><#if author.size()!=0>${(author.passed/author.size())*100}%<#else>0%</#if></td>
            </tr>
            </#list>
          </tbody>
        </table></div>
      </div>
    </div>
    </#if>
    <#if report.categoryCtx.set?size != 0>
    <div class="col-md-4 category-container">
      <div class="card">
        <div class="card-header"><p>Tags</p></div>
        <div class="card-body pb-0 pt-0"><table class="table table-sm table-bordered">
          <thead><tr class="bg-gray"><th>Name</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Others</th><th>Passed %</th></tr></thead><tbody>
            <#list report.categoryCtx.set as category>
            <tr>
              <td>${category.attr.name}</td>
              <td>${category.passed}</td>
              <td>${category.failed}</td>
              <td>${category.skipped}</td>
              <td>${category.others}</td>
              <td><#if category.size()!=0>${(category.passed/category.size())*100}%<#else>0%</#if></td>
            </tr>
            </#list>
          </tbody>
        </table></div>
      </div>
    </div>
    </#if>
    <#if report.deviceCtx.set?size != 0>
    <div class="col-md-4 device-container">
      <div class="card">
        <div class="card-header"><p>Device</p></div>
        <div class="card-body pb-0 pt-0"><table class="table table-sm table-bordered">
          <thead><tr class="bg-gray"><th>Name</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Others</th><th>Passed %</th></tr></thead>
          <tbody>
            <#list report.deviceCtx.set as device>
            <tr>
              <td>${device.attr.name}</td>
              <td>${device.passed}</td>
              <td>${device.failed}</td>
              <td>${device.skipped}</td>
              <td>${device.others}</td>
              <td><#if device.size()!=0>${(device.passed/device.size())*100}%<#else>0%</#if></td>
            </tr>
            </#list>
          </tbody>
        </table></div>
      </div>
    </div>
    </#if>
    <#if report.systemEnvInfo?size != 0>
      <div class="col-md-4 sysenv-container">
      <div class="card">
        <div class="card-header"><p>System/Environment</p></div>
        <div class="card-body pb-0 pt-0"><table class="table table-sm table-bordered">
          <thead><tr class="bg-gray"><th>Name</th><th>Value</th></tr></thead>
          <tbody>
            <#list report.systemEnvInfo as info>
            <tr>
              <td>${info.name}</td>
              <td>${info.value}</td>
            </tr>
            </#list>
          </tbody>
        </table></div>
      </div>
    </div>
    </#if>
  </div>
</div>
<script>
  var statusGroup = {
    parentCount: ${ report.stats.parent?size?c },
    <#if report.stats.parent?size != 0>
    passParent: ${ report.stats.parent?api.get(Status.PASS)?c },
    failParent: ${ report.stats.parent?api.get(Status.FAIL)?c },
    warningParent: ${ report.stats.parent?api.get(Status.WARNING)?c },
    skipParent: ${ report.stats.parent?api.get(Status.SKIP)?c },
    </#if>
    childCount: ${ report.stats.child?size?c },
    <#if report.stats.child?size != 0>
    passChild: ${ report.stats.child?api.get(Status.PASS)?c },
    failChild: ${ report.stats.child?api.get(Status.FAIL)?c },
    warningChild: ${ report.stats.child?api.get(Status.WARNING)?c },
    skipChild: ${ report.stats.child?api.get(Status.SKIP)?c },
    infoChild: ${ report.stats.child?api.get(Status.INFO)?c },
    </#if>
    grandChildCount: ${ report.stats.grandchild?size?c },
    <#if report.stats.grandchild?size != 0>
    passGrandChild: ${ report.stats.grandchild?api.get(Status.PASS)?c },
    failGrandChild: ${ report.stats.grandchild?api.get(Status.FAIL)?c },
    warningGrandChild: ${ report.stats.grandchild?api.get(Status.WARNING)?c },
    skipGrandChild: ${ report.stats.grandchild?api.get(Status.SKIP)?c },
    infoGrandChild: ${ report.stats.grandchild?api.get(Status.INFO)?c },
    </#if>
    eventsCount: ${ report.stats.log?size?c },
    <#if report.stats.log?size != 0 && displayEvents>
    passEvents: ${ report.stats.log?api.get(Status.PASS)?c },
    failEvents: ${ report.stats.log?api.get(Status.FAIL)?c },
    warningEvents: ${ report.stats.log?api.get(Status.WARNING)?c },
    skipEvents: ${ report.stats.log?api.get(Status.SKIP)?c },
    infoEvents: ${ report.stats.log?api.get(Status.INFO)?c }
    </#if>
  };
</script>
</#compress>