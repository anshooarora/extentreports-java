<div class="test-wrapper row view test-view">
  <div class="test-list">
    <#compress>
    <div class="test-list-tools">
      <ul class="tools pull-left">
        <li><a href="#"><span class="font-size-14">Tests</span></a></li>
      </ul>
      <ul class="tools text-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-exclamation-circle"></i></a>
          <ul id="status-toggle" class="dropdown-menu dropdown-md p-v-0">
            <#if report.anyTestHasStatus(Status.PASS)><a class="dropdown-item" status="pass" href="#"><span>Pass</span><span class="status success"></span></a></#if>
            <#if report.anyTestHasStatus(Status.FAIL)><a class="dropdown-item" status="fail" href="#"><span>Fail</span><span class="status danger"></span></a></#if>
            <#if report.anyTestHasStatus(Status.FATAL)><a class="dropdown-item" status="fatal" href="#"><span>Fatal</span><span class="status danger"></span></a></#if>
            <#if report.anyTestHasStatus(Status.ERROR)><a class="dropdown-item" status="error" href="#"><span>Error</span><span class="status warning"></span></a></#if>
            <#if report.anyTestHasStatus(Status.WARNING)><a class="dropdown-item" status="warning" href="#"><span>Warning</span><span class="status warning"></span></a></#if>
            <#if report.anyTestHasStatus(Status.SKIP)><a class="dropdown-item" status="skip" href="#"><span>Skip</span><span class="status warning"></span></a></#if>
            <div class="dropdown-divider"></div>
            <a status="clear" class="dropdown-item" href="#"><span>Clear</span><span class="pull-right"><i class="fa fa-close"></i></span></a>
          </ul>
        </li>
        <#if report.authorCtx.hasItems()>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i></a>
          <ul id="author-toggle" class="dropdown-menu dropdown-md p-v-0">
            <#list report.authorCtx.set as context><a class="dropdown-item" href="#">${context.attr.name}</a></#list>
          </ul>
        </li>
        </#if>
        <#if report.categoryCtx.hasItems()>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-tag"></i></a>
          <ul id="tag-toggle" class="dropdown-menu dropdown-md p-v-0">
            <#list report.categoryCtx.set as context><a class="dropdown-item" href="#">${context.attr.name}</a></#list>
          </ul>
        </li>
        </#if>
        <#if report.deviceCtx.hasItems()>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-tablet"></i></a>
          <ul id="device-toggle" class="dropdown-menu dropdown-md p-v-0">
            <#list report.deviceCtx.set as context><a class="dropdown-item" href="#">${context.attr.name}</a></#list>
          </ul>
        </li>
        </#if>
      </ul>
    </div>
    </#compress>
    <div class="test-list-wrapper scrollable">
      <ul class="test-list-item">
        <#list report.testList as test>
        <li class="test-item"  status="${test.status.toLower()}" test-id="${test.getId()}"
          author="<#list test.authorSet as x>${x.name}<#if x_has_next> </#if></#list>"
          tag="<#list test.categorySet as x>${x.name}<#if x_has_next> </#if></#list>"
          device="<#list test.deviceSet as x>${x.name}<#if x_has_next> </#if></#list>">
          <div class="status-avatar ${test.status.toLower()}-bg">
            <i class="fa fa-${Ico.ico(test.status)} text-white"></i>
          </div>
          <div class="test-detail">
            <span class="meta text-white badge badge-sm"></span>
            <p class="name">${test.name}</p>
            <p class="text-sm"><span>${test.startTime?string("HH:mm:ss a")}</span> / <span>${test.timeTaken()/1000} secs</span></p>
          </div>
          <div class="test-contents d-none">
            <#compress>
            <div class="detail-head">
              <div class="p-v-10 d-inline-block">
                <div class="info">
                  <h5 class="test-status text-${test.status.toLower()}">${test.name}</h5>
                  <span class='badge badge-success'>${test.startTime?string("MM.dd.yyyy HH:mm:ss")}</span>
                  <span class='badge badge-danger'>${test.endTime?string("MM.dd.yyyy HH:mm:ss")}</span>
                  <span class='badge badge-default'>${test.timeTaken()/1000} secs</span>
                </div>
                <#if test.hasAttributes()>
                <div class="m-t-15"><@attributes test=test /></div>
                </#if>
                <#if test.description??>
                <div class="m-t-10 m-l-5">${test.description}</div>
                </#if>
              </div>
            </div>
            </#compress>
            <#if isbdd><#include "bdd-content.ftl">
            <#else><#include "standard-content.ftl">
            </#if>
          </div>
        </li>
        </#list>
      </ul>
    </div>
  </div>
  <#compress>
  <div class="test-content scrollable">
    <div class="test-content-tools">
      <ul><li><a class="back-to-test" href="#"><i class="fa fa-arrow-left"></i></a></li></ul>
    </div>
    <div class="test-content-detail"><div class="detail-body"></div></div>
  </div>
  </#compress>
</div>
