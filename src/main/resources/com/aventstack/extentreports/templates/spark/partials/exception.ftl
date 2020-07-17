<#if report.exceptionInfoCtx.set?size != 0>
<#assign
	exceptionInfoCtx=report.exceptionInfoCtx.set>
<#compress>
<div class="test-wrapper row view exception-view attributes-view">
  <div class="test-list">
    <div class="test-list-tools">
      <ul class="tools pull-left">
        <li>
          <a href="">
          <span class="font-size-14">Exception</span>
          </a>
        </li>
      </ul>
      <ul class="tools text-right">
        <li>
          <a href="#">
          <span class="badge badge-primary">${exceptionInfoCtx?size}</span>
          </a>
        </li>
      </ul>
    </div>
    <div class="test-list-wrapper scrollable">
      <ul class="test-list-item">
        <#list exceptionInfoCtx as context>
        <li class="test-item">
          <div class="open-test">
            <div class="test-detail">
              <p class="name">${context.attr.name}</p>
              <p class="duration text-sm">${context.testList?size} tests</p>
            </div>
          </div>
          <div class="test-contents d-none">
            <div class="info">
              <h4>${context.attr.name}</h4>
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
                <#list context.testList as test>
                <tr>
                  <td><span class="badge log ${test.status.toLower()}-bg">${test.status?string}</span></td>
                  <td>${test.startTime?string[("HH:mm:ss a")]}</td>
                  <td>
                    <a href="#" class="linked" test-id='${test.getAncestor().getId()}'>${test.name}</a>
                    <#if test.parent??>
                    <div class="">
                      <span class="badge badge-default">${test.parent.name}</span>
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
          <a class="back-to-test" href="javascript:void(0)">
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