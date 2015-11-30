<!DOCTYPE html>
<html>
    <head>
        <!--
            ExtentReports Library 2.40.0 | http://relevantcodes.com/extentreports-for-selenium/ | https://github.com/anshooarora/
            Copyright (c) 2015, Anshoo Arora (Relevant Codes) | Copyrights licensed under the New BSD License | http://opensource.org/licenses/BSD-3-Clause
            Documentation: http://extentreports.relevantcodes.com 
        -->
        <meta http-equiv='content-type' content='text/html; charset=<#if report.configurationMap??>${report.configurationMap["encoding"]}<#else>UTF-8</#if>' /> 
        <meta name='description' content='ExtentReports (by Anshoo Arora) is a reporting library for automation testing for .NET and Java. It creates detailed and beautiful HTML reports for modern browsers. ExtentReports shows test and step summary along with dashboards, system and environment details for quick analysis of your tests.' />
        <meta name='robots' content='noodp, noydir' />
        <meta name='viewport' content='width=device-width, initial-scale=1' />
        
        <title>
            <#if report.configurationMap??>
                ${report.configurationMap["documentTitle"]}
            </#if>
        </title>
        
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/css/materialize.min.css' type='text/css'>
        <link href='https://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.css' type='text/css' rel='stylesheet' />
        <link href='http://cdn.rawgit.com/anshooarora/extentreports/c52c8b4c4cf7853d658daeb1cb09207ed5a667c6/cdn/extent.css' type='text/css' rel='stylesheet' />
        
        <style>
            <#if report.configurationMap??>
                ${report.configurationMap["styles"]}
            </#if>
        </style>
    </head>
    <body class='extent'>
        <!-- nav -->
        <nav>
            <ul id='slide-out' class='side-nav fixed'>
                <li class='logo'>
                    <a class='logo-content' href='http://extentreports.relevantcodes.com'><span>ExtentReports</span></a>
                    <a class='menu-toggle right'><i class='mdi-navigation-menu'></i></a>
                </li> 
                <li class='analysis waves-effect active'><a href='#!' class='test-view'><i class='mdi-action-dashboard'></i>Test Details</a></li>
                <li class='analysis waves-effect'><a href='#!' class='categories-view'><i class='mdi-maps-local-offer'></i>Categories</a></li>
                <li class='analysis waves-effect'><a href='#!' class='dashboard-view'><i class='mdi-action-track-changes'></i></i>Analysis</a></li>
                <li class='analysis waves-effect'><a href='#!' class='testrunner-logs-view'><i class='mdi-action-assignment'></i>TestRunner Logs</a></li>
            </ul>
            <a href='#' data-activates='slide-out' class='button-collapse'><i class='mdi-navigation-menu medium'></i></a>
            <span class='report-name'><#if report.configurationMap??>${report.configurationMap["reportName"]}</#if></span> <span class='report-headline'><#if report.configurationMap??>${report.configurationMap["reportHeadline"]}</#if></span>
            <ul class='right hide-on-med-and-down nav-right'>
                <li class='theme-selector' alt='Click to toggle dark theme. To enable by default, use js configuration $(".theme-selector").click();' title='Click to toggle dark theme. To enable by default, use js configuration $(".theme-selector").click();'>
                    <i class='mdi-hardware-desktop-windows'></i>
                </li>
                <li>
                    <span class='suite-started-time'>${.now?datetime?string("yyyy-MM-dd HH:mm:ss")}</span>
                </li>
                <li>
                    <span>v2.40.0</span>
                </li>
            </ul>
        </nav>
        <!-- /nav -->
        
        <!-- container -->
        <div class='container'>
            
            <!-- dashboard -->
            <div id='dashboard-view' class='row'>
                <div class='time-totals'>
                    <div class='col l2 m4 s6'>
                        <div class='card suite-total-tests'> 
                            <span class='panel-name'>Total Tests</span> 
                            <span class='total-tests'> <span class='panel-lead'></span> </span> 
                        </div> 
                    </div>
                    <div class='col l2 m4 s6'>
                        <div class='card suite-total-steps'> 
                            <span class='panel-name'>Total Steps</span> 
                            <span class='total-steps'> <span class='panel-lead'></span> </span> 
                        </div> 
                    </div>
                    <div class='col l4 m4 s12'>
                        <div class='card suite-total-steps'> 
                            <span class='panel-name'>Total Time Taken</span> 
                            <span class='suite-total-time-taken panel-lead'>${report.getRunDuration()}</span> 
                        </div> 
                    </div>
                    <div class='col l2 m6 s6 suite-start-time'>
                        <div class='card green-accent'> 
                            <span class='panel-name'>Start</span> 
                            <span class='panel-lead suite-started-time'>${report.startedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</span> 
                        </div> 
                    </div>
                    <div class='col l2 m6 s6 suite-end-time'>
                        <div class='card pink-accent'> 
                            <span class='panel-name'>End</span> 
                            <span class='panel-lead suite-ended-time'>${.now?datetime?string("yyyy-MM-dd HH:mm:ss")}</span> 
                        </div> 
                    </div>
                </div>
                <div class='charts'>
                    <div class='col s12 m6 l4 fh'> 
                        <div class='card-panel'> 
                            <div>
                                <span class='panel-name'>Tests View</span>
                            </div> 
                            <div class='panel-setting modal-trigger test-count-setting right'>
                                <a href='#test-count-setting'><i class='mdi-navigation-more-vert text-md'></i></a>
                            </div> 
                            <div class='chart-box'>
                                <canvas class='text-centered' id='test-analysis'></canvas>
                            </div> 
                            <div>
                                <span class='weight-light'><span class='t-pass-count weight-normal'></span> test(s) passed</span>
                            </div> 
                            <div>
                                <span class='weight-light'><span class='t-fail-count weight-normal'></span> test(s) failed, <span class='t-others-count weight-normal'></span> others</span>
                            </div> 
                        </div> 
                    </div> 
                    <div class='col s12 m6 l4 fh'> 
                        <div class='card-panel'> 
                            <div>
                                <span class='panel-name'>Steps View</span>
                            </div> 
                            <div class='panel-setting modal-trigger step-status-filter right'>
                                <a href='#step-status-filter'><i class='mdi-navigation-more-vert text-md'></i></a>
                            </div> 
                            <div class='chart-box'>
                                <canvas class='text-centered' id='step-analysis'></canvas>
                            </div> 
                            <div>
                                <span class='weight-light'><span class='s-pass-count weight-normal'></span> step(s) passed</span>
                            </div> 
                            <div>
                                <span class='weight-light'><span class='s-fail-count weight-normal'></span> step(s) failed, <span class='s-others-count weight-normal'></span> others</span>
                            </div> 
                        </div> 
                    </div>
                    <div class='col s12 m12 l4 fh'> 
                        <div class='card-panel'> 
                            <span class='panel-name'>Pass Percentage</span> 
                            <span class='pass-percentage panel-lead'></span> 
                            <div class='progress light-blue lighten-3'> 
                                <div class='determinate light-blue'></div> 
                            </div> 
                        </div> 
                    </div>
                </div>
                <div class='system-view'>
                    <div class='col l4 m12 s12'>
                        <div class='card-panel'>
                            <span class='label info outline right'>Environment</span>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Param</th>
                                        <th>Value</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list report.systemInfoMap?keys as info>
                                        <tr>
                                            <td>${info}</td>
                                            <td>${report.systemInfoMap[info]}</td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class='category-summary-view'>
                    <div class='col l4 m6 s12'>
                        <div class='card-panel'>
                            <span class='label info outline right'>Categories</span>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list report.categoryTestMap?keys as category>
                                        <tr>
                                            <td>
                                                ${category}
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /dashboard -->
            
            <!-- tests -->
            <div id='test-view' class='row'>
                <div class='col s5'>
                    <div class='card-panel heading'>
                        <h5>Tests</h5>
                    </div>
                    <div class='card-panel filters'>
                        <div>
                            <a data-activates='tests-toggle' data-constrainwidth='true' data-beloworigin='true' data-hover='true' href='#' class='dropdown-button button tests-toggle'><i class='mdi-action-subject icon'></i></a>
                            <ul id='tests-toggle' class='dropdown-content'>
                                <li class='pass'><a href='#!'>Pass</a></li>
                                <li class='fail'><a href='#!'>Fail</a></li>
                                <#if report.logStatusList?? && report.logStatusList?seq_contains(LogStatus.FATAL)>
                                    <li class='fatal'><a href='#!'>Fatal</a></li>                                
                                </#if>
                                <#if report.logStatusList?? && report.logStatusList?seq_contains(LogStatus.ERROR)>
                                    <li class='error'><a href='#!'>Error</a></li>
                                </#if>
                                <#if report.logStatusList?? && report.logStatusList?seq_contains(LogStatus.WARNING)>
                                    <li class='warning'><a href='#!'>Warning</a></li>
                                </#if>    
                                <li class='skip'><a href='#!'>Skip</a></li>
                                <#if report.logStatusList?? && report.logStatusList?seq_contains(LogStatus.UNKNOWN)>
                                    <li class='unknown'><a href='#!'>Unknown</a></li>
                                </#if>    
                                <li class='divider'></li>
                                <li class='clear'><a href='#!'>Clear Filters</a></li>
                            </ul>
                        </div>
                        <#if report.categoryTestMap?? && report.categoryTestMap?size != 0>
                            <div>
                                <a data-activates='category-toggle' data-constrainwidth='false' data-beloworigin='true' data-hover='true' href='#' class='category-toggle dropdown-button button'><i class='mdi-image-style icon'></i></a>
                                <ul id='category-toggle' class='dropdown-content'>
                                    <#list report.categoryTestMap?keys as category>
                                        <li class='${category}'><a href='#!'>${category}</a></li>
                                    </#list>
                                    <li class='divider'></li>
                                    <li class='clear'><a href='#!'>Clear Filters</a></li>
                                </ul>
                            </div>
                        </#if>
                        <div>
                            <a id='clear-filters' alt='Clear Filters' title='Clear Filters'><i class='mdi-navigation-close icon'></i></a>
                        </div>
                        <div>&nbsp;&middot;&nbsp;</div>
                        <div>
                            <a id='enableDashboard' alt='Enable Dashboard' title='Enable Dashboard'><i class='mdi-action-track-changes icon'></i></a>
                        </div>
                        <div>
                            <a id='refreshCharts' alt='Refresh Charts on Filter' title='Refresh Charts on Filter' class='enabled'><i class='mdi-navigation-refresh icon'></i></i></a>
                        </div>
                        <div>&nbsp;&middot;</div>
                        <div class='search' alt='Search tests' title='Search tests'>
                            <div class='input-field left'>
                                <input id='searchTests' type='text' class='validate' placeholder='Search tests...'>
                            </div>
                            <i class='mdi-action-search icon'></i>
                        </div>
                    </div>
                    <#assign optionalClass = ''>
                    <#if report.testList?size < 15>
                        <#assign optionalClass = 'vh100'>
                    </#if>
                    <div class='card-panel no-padding-h no-padding-v no-margin-v ${optionalClass}'>
                        <div class='wrapper'>
                            <ul id='test-collection' class='test-collection'>
                                <#list report.testList as extentTest>
                                    <#assign test = extentTest.getTest()>
                                    <li class='collection-item test displayed active ${test.status}'>
                                        <div class='test-head'>
                                            <span class='test-name'>${test.name} <#if test.internalWarning??><i class='tooltipped mdi-alert-error' data-position='top' data-delay='50' data-tooltip='${test.internalWarning}'></i></#if></span>
                                            <span class='test-status label right capitalize outline ${test.status}'>${test.status}</span>
                                            <span class='category-assigned hide <#list test.categoryList as category> ${category.name?lower_case?replace(".", "")?replace("#", "")}</#list>'></span>
                                        </div>
                                        <div class='test-body'>
                                            <div class='test-info'>
                                                <span title='Test started time' class='test-started-time label green lighten-2 text-white'>${test.startedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</span>
                                                <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'><#if test.endedTime??>${test.endedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</#if></span>
                                                <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-3 text-white'><#if test.endedTime??>${test.getRunDuration()}</#if></span>
                                            </div>
                                            <div class='test-desc'>${test.description}</div>
                                            <div class='test-attributes'>
                                                <#if test.categoryList?? && test.categoryList?size != 0>
                                                    <div class='categories'>
                                                        <#list test.categoryList as category>
                                                            <span class='category text-white'>${category.name}</span>
                                                        </#list>
                                                    </div>
                                                </#if>
                                                <#if test.authorsList?? && test.authorsList?size != 0>
                                                    <div class='authors'>
                                                        <#list test.authorsList as author>
                                                            <span class='author text-white'>${author.name}</span>
                                                        </#list>
                                                    </div>
                                                </#if>
                                            </div>
                                            <div class='test-steps'>
                                                <table class='bordered table-results'>
                                                    <thead>
                                                        <tr>
                                                            <th>Status</th>
                                                            <th>Timestamp</th>
                                                            <#if (test.logList[0].stepName)??>
                                                                <th>StepName</th>
                                                            </#if>
                                                            <th>Details</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <#list test.logList as log>
                                                            <tr>
                                                                <td class='status ${log.logStatus}' title='${log.logStatus}' alt='${log.logStatus}'><i class='${Icon.getIcon(log.logStatus)}'></i></td>
                                                                <td class='timestamp'>${log.timestamp?datetime?string("HH:mm:ss")}</td>
                                                                <#if test.logList[0].stepName??>
                                                                    <td class='step-name'>${log.stepName}</td>
                                                                </#if>
                                                                <td class='step-details'>${log.details}</td>
                                                            </tr>
                                                        </#list>
                                                    </tbody>
                                                </table>
                                                <ul class='collapsible node-list' data-collapsible='accordion'>
                                                    <#if test.nodeList?? && test.nodeList?has_content>
                                                        <@recurse_nodes nodeList=test.nodeList depth=1 />
                                                        <#macro recurse_nodes nodeList depth>
                                                            <#list nodeList as node>
                                                                <li class='displayed ${node.status} node-${depth}x'>
                                                                    <div class='collapsible-header test-node ${node.status}'>
                                                                        <div class='right test-info'>
                                                                            <span title='Test started time' class='test-started-time label green lighten-2 text-white'>${node.startedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</span>
                                                                            <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'>${node.endedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</span>
                                                                            <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-2 text-white'>${node.getRunDuration()}</span>
                                                                            <span class='test-status label outline capitalize ${node.status}'>${node.status}</span>
                                                                        </div>
                                                                        <div class='test-node-name'>${node.name}</div>
                                                                    </div>
                                                                    <div class='collapsible-body'>
                                                                        <div class='test-steps'>
                                                                            <table class='bordered table-results'>
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>Status</th>
                                                                                        <th>Timestamp</th>
                                                                                        <#if (node.logList[0].stepName)??>
                                                                                            <th>StepName</th>
                                                                                        </#if>
                                                                                        <th>Details</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <#list node.logList as log>
                                                                                        <tr>
                                                                                            <td class='status ${log.logStatus}' title='${log.logStatus}' alt='${log.logStatus}'><i class='${Icon.getIcon(log.logStatus)}'></i></td>
                                                                                            <td class='timestamp'>${log.timestamp?datetime?string("HH:mm:ss")}</td>
                                                                                            <#if node.logList[0].stepName??>
                                                                                                <td class='step-name'>${log.stepName}</td>
                                                                                            </#if>
                                                                                            <td class='step-details'>${log.details}</td>
                                                                                        </tr>
                                                                                    </#list>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <@recurse_nodes nodeList=node.nodeList depth=depth+1 />
                                                            </#list>
                                                        </#macro>
                                                    </#if>
                                                </ul>
                                            </div>
                                        </div>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id='test-details-wrapper' class='col s7'>
                    <div class='card-panel vh100 details-view pin'>
                        <h5 class='details-name'></h5>
                        <div class='details-container'>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /tests -->
            
            <!-- categories -->
            <div id='categories-view' class='row hide'>
                <div class='col s5'>
                    <div class='card-panel heading'>
                        <h5>Categories</h5>
                    </div>
                    <div class='card-panel filters'>
                        <div class='search' alt='Search tests' title='Search tests'>
                            <div class='input-field left'>
                                <input id='searchTests' type='text' class='validate' placeholder='Search tests...'>
                            </div>
                            <i class='mdi-action-search icon'></i>
                        </div>
                    </div>
                    <div class='card-panel no-padding-h no-padding-v vh100'>
                        <div class='wrapper'>
                            <ul id='cat-collection' class='cat-collection'>
                                <#list report.categoryTestMap?keys as category>
                                    <#assign testList = report.categoryTestMap[category]>
                                    <#assign passed = 0, failed = 0, others = 0>
                                    <#list testList as test>
                                        <#if test.status == "pass">
                                            <#assign passed = passed + 1>
                                        <#elseif test.status == "fail">
                                            <#assign failed = failed + 1>
                                        <#else>
                                            <#assign others = others + 1>
                                        </#if>
                                    </#list>
                                    <li class='category-item displayed'>
                                        <div class='cat-head'>
                                            <span class='category-name'>${category}</span>
                                        </div>
                                        <div class='category-status-counts'>
                                            <#if (passed > 0)>
                                                <span class='pass label dot'>Pass: ${passed}</span>
                                            </#if>
                                            <#if (failed > 0)>
                                                <span class='fail label dot'>Fail: ${failed}</span>
                                            </#if>
                                            <#if (others > 0)>
                                                <span class='other label dot'>Others: ${others}</span>
                                            </#if>
                                        </div>
                                        <div class='cat-body'>
                                            <div class='category-status-counts'>
                                                <div class='button-group'>
                                                    <a href='#!' class='pass label filter'>Pass <span class='icon'>${passed}</span></a>
                                                    <a href='#!' class='fail label filter'>Fail <span class='icon'>${failed}</span></a>
                                                    <a href='#!' class='other label filter'>Others <span class='icon'>${others}</span></a>
                                                </div>
                                            </div>
                                            <div class='cat-tests'>
                                                <table class='bordered'>
                                                    <thead>
                                                        <tr>
                                                            <th>Run Date</th>
                                                            <th>Test Name</th>
                                                            <th>Status</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <#list testList as test>
                                                            <tr class='${test.status}'>
                                                                <td>${test.startedTime?datetime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                                                <td><span class='category-link linked'>${test.name}</span></td>
                                                                <td><div class='status label capitalize ${test.status}'>${test.status}</div></td>
                                                            </tr>
                                                        </#list>
                                                    <tbody>
                                                </table>
                                            </div>
                                        </div> 
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id='cat-details-wrapper' class='col s7'>
                    <div class='card-panel vh100 details-view pin'>
                        <h5 class='cat-name'></h5>
                        <div class='cat-container'>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /categories -->
            
            <!-- testrunner logs -->
            <div id='testrunner-logs-view' class='row hide'>
                <div class='col s12'>
                    <div class='card-panel'>
                        <h5>TestRunner Logs</h5>
                        <#list report.testRunnerLogList as trLog>
                            <p>${trLog}</p>
                        </#list>
                    </div>
                </div>
            </div>
            <!-- /testrunner logs -->
            
        </div>
        <!-- /container -->
        
        <!-- test dashboard counts setting -->
        <div id='test-count-setting' class='modal bottom-sheet'> 
            <div class='modal-content'> 
                <h5>Configure Tests Count Setting</h5> 
                <input name='test-count-setting' type='radio' id='parentWithoutNodes' class='with-gap'> 
                <label for='parentWithoutNodes'>Parent Tests Only (Does not include child nodes in counts)</label> 
                <br> 
                <input name='test-count-setting' type='radio' id='parentWithoutNodesAndNodes' class='with-gap'> 
                <label for='parentWithoutNodesAndNodes'>Parent Tests Without Child Tests + Child Tests</label> 
                <br> 
                <input name='test-count-setting' type='radio' id='childNodes' class='with-gap'> 
                <label for='childNodes'>Child Tests Only</label> 
            </div> 
            <div class='modal-footer'> 
                <a href='#!' class='modal-action modal-close waves-effect waves-green btn'>Save</a> 
            </div> 
        </div>
        <!-- /test dashboard counts setting -->
        
        <!-- filter for step status -->
        <div id='step-status-filter' class='modal bottom-sheet'> 
            <div class='modal-content'> 
                <h5>Select status</h5> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-pass'> 
                <label for='step-dashboard-filter-pass'>Pass</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-fail'> 
                <label for='step-dashboard-filter-fail'>Fail</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-fatal'> 
                <label for='step-dashboard-filter-fatal'>Fatal</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-error'> 
                <label for='step-dashboard-filter-error'>Error</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-warning'> 
                <label for='step-dashboard-filter-warning'>Warning</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-skip'> 
                <label for='step-dashboard-filter-skip'>Skipped</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-info'> 
                <label for='step-dashboard-filter-info'>Info</label> 
                <br> 
                <input checked class='filled-in' type='checkbox' id='step-dashboard-filter-unknown'> 
                <label for='step-dashboard-filter-unknown'>Unknown</label> 
            </div>
            <div class='modal-footer'> 
                <a href='#!' class='modal-action modal-close waves-effect waves-green btn'>Save</a> 
            </div> 
        </div>
        <!-- /filter for step status -->
        
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script> 
        <script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/js/materialize.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js'></script>
        <script src='https://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.js' type='text/javascript' charset='utf-8'></script>
        <script src='http://cdn.rawgit.com/anshooarora/extentreports/005d99e2f2716f6d749c77c65b57ca3c632c35a8/cdn/extent.js' type='text/javascript'></script>
        
        <script>$(document).ready(function() { $('.logo span').html('ExtentReports'); });</script>
        <script>
            <#if report.configurationMap??>
                ${report.configurationMap["scripts"]}
            </#if>
        </script>
    </body>
</html>
