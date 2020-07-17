<#compress>
<div class="header navbar">
  <div class="vheader">
    <div class="nav-logo">
      <a href="#">
        <#if offline><div class="logo" style="background-image: url('spark/logo.png')"></div>
        <#else><div class="logo" style="background-image: url('https://${cdnURI}${iconcommit}/commons/img/logo.png')"></div>
        </#if>
      </a>
    </div>
    <ul class="nav-left">
      <li class="search-box">
        <a class="search-toggle" href="#">
          <i class="search-icon fa fa-search"></i>
          <i class="search-icon-close fa fa-close"></i>
        </a>
      </li>
      <li class="search-input"><input id="search-tests" class="form-control" type="text" placeholder="Search..."></li>
    </ul>
    <ul class="nav-right">
      <li class="m-r-10">
        <a href="#"><span class="badge badge-primary">${config.reportName}</span></a>
      </li>
        <li class="m-r-10">
          <a href="#"><span class="badge badge-primary">${report.startTime?datetime?string["${timeStampFormat}"]}</span></a>
        </li>
    </ul>
  </div>
</div>
</#compress>