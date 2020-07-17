<#include "../commons/commons-variables.ftl">
<#include "../commons/commons-macros.ftl">
<#include "macros/attributes.ftl">
<#include "macros/log.ftl">
<#include "macros/recurse_nodes.ftl">

<#assign 
  isbdd=false 
  pageClass="">
<#if report.isBDD()>
  <#assign
    pageClass="bdd" 
    isbdd=true>
</#if>

<!DOCTYPE html>
<html>
<#include "partials/head.ftl">
<#if offline><link rel="stylesheet" href="${config.offlineDirectory}jsontree.js">
<#else><script src="https://cdn.rawgit.com/extent-framework/extent-github-cdn/7cc78ce/spark/js/jsontree.js"></script>
</#if>
<body class="${reportType}-report ${theme}">
  <div class="app">
    <div class="layout">
      <#include "partials/navbar.ftl">
      <#include "partials/sidenav.ftl">
      <div class="page-container">
        <div class="main-content">
          <#include "partials/test.ftl">
        </div>
      </div>
    </div>
  </div>
  <#include "partials/scripts.ftl">
</body>
</html>