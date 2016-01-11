using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RelevantCodes.ExtentReports.View
{
    internal class Extent
    {
        public static string Source
        {
            get
            {
                return @"
                    @using RelevantCodes.ExtentReports;
                    @using RelevantCodes.ExtentReports.View;
                    @using RelevantCodes.ExtentReports.View.Resources;

                    @{var dateFormat = Model.ConfigurationMap[""dateFormat""];
                        var timeFormat = Model.ConfigurationMap[""timeFormat""];
                        var dateTimeFormat = Model.ConfigurationMap[""dateFormat""] + "" "" + Model.ConfigurationMap[""timeFormat""];

                        var protocol = ""https"";
                        if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""protocol"")) {
                            protocol = Model.ConfigurationMap[""protocol""];
                        }
                    }

                    <!DOCTYPE html>
                    <html>
                        <head>
                            <!--
                                ExtentReports @Localized.head_library 2.40.0 | http://relevantcodes.com/extentreports-for-selenium/ | https://github.com/anshooarora/
                                Copyright (c) 2015, Anshoo Arora (Relevant Codes) | @Localized.head_copyrights | http://opensource.org/licenses/BSD-3-Clause
                                @Localized.head_documentation: http://extentreports.relevantcodes.com 
                            -->
                            <meta http-equiv='content-type' content='text/html; charset=@if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""encoding"")) { @Raw(Model.ConfigurationMap[""encoding""]); };' /> 
                            <meta name='description' content='@Localized.head_metaDescription' />
                            <meta name='robots' content='noodp, noydir' />
                            <meta name='viewport' content='width=device-width, initial-scale=1' />

                            <title>
                                @if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""documentTitle""))
                                {                            
                                    @Raw(Model.ConfigurationMap[""documentTitle""])
                                }
                            </title>

                            <link href='@protocol://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/css/materialize.min.css' type='text/css' rel='stylesheet' />
                            <link href='@protocol://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.css' type='text/css' rel='stylesheet' />
                            <link href='@protocol://cdn.rawgit.com/anshooarora/extentreports/aac91b51dac7509a363ea8f5fb812ed8e47eb636/cdn/extent.css' type='text/css' rel='stylesheet' />

                            <style>
                                @if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""css""))
                                {
                                    @Raw(Model.ConfigurationMap[""styles""])
                                }
                            </style>
                        </head>
                        <body class='extent'>
                            <!-- nav -->
                            <nav>
                                <div class='logo-container'>
                                    <a class='logo-content' href='http://extentreports.relevantcodes.com'>
                                        <span>ExtentReports</span>
                                    </a>
                                    <a class='menu-toggle right' style='display:none;'>
                                        <i class='mdi-navigation-menu'></i>
                                    </a>
                                </div>

                                <!-- sidenav -->
                                <ul id='slide-out' class='side-nav fixed'>
                                    <li class='analysis waves-effect active'><a href='#!' class='test-view' onclick='_updateCurrentStage(0)'><i class='mdi-action-dashboard'></i>@Localized.nav_menu_testDetails</a></li>
                                    @if (Model.CategoryMap != null && Model.CategoryMap.Count > 0) 
                                    {
                                        <li class='analysis waves-effect'><a href='#!' class='categories-view' onclick='_updateCurrentStage(1)'><i class='mdi-maps-local-offer'></i>@Localized.nav_menu_categories</a></li>
                                    }
                                    @if (Model.ExceptionMap != null && Model.ExceptionMap.Count > 0)
                                    {
                                        <li class='analysis waves-effect'><a href='#!' class='exceptions-view' onclick='_updateCurrentStage(2)'><i class='mdi-action-bug-report'></i>@Localized.nav_menu_exceptions</a></li>
                                    }
                                    <li class='analysis waves-effect'><a href='#!' class='dashboard-view'><i class='mdi-action-track-changes'></i></i>@Localized.nav_menu_analysis</a></li>
                                    @if (Model.TestRunnerLogs != null && Model.TestRunnerLogs.Count > 0)
                                    {
                                        <li class='analysis waves-effect'><a href='#!' class='testrunner-logs-view'><i class='mdi-action-assignment'></i>@Localized.nav_menu_testRunnerLogs</a></li>
                                    }
                                </ul>
                                <!-- /sidenav -->

                                <a href='#' data-activates='slide-out' class='button-collapse'><i class='mdi-navigation-menu'></i></a>
                                <span class='report-name'>@if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""reportName"")) { @Raw(Model.ConfigurationMap[""reportName""]) }</span> <span class='report-headline'>@if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""reportHeadline"")) { @Raw(Model.ConfigurationMap[""reportHeadline""]) }</span>

                                <!-- navright -->
                                <ul class='right hide-on-med-and-down nav-right'>
                                    <li class='theme-selector' alt='Click to toggle dark theme. To enable by default, use js configuration $("".theme-selector"").click();' title='Click to toggle dark theme. To enable by default, use js configuration $("".theme-selector"").click();'>
                                        <i class='mdi-hardware-desktop-windows'></i>
                                    </li>
                                    <li>
                                        <span class='suite-started-time'>@Model.StartTime.ToString(dateTimeFormat)</span>
                                    </li>
                                    <li>
                                        <span>v2.40.1-beta-3</span>
                                    </li>
                                </ul>
                                <!-- /navright -->
                            </nav>
                            <!-- /nav -->

                            <div class='container'>
                                <!-- dashboard -->
                                <div id='dashboard-view' class='row'>
                                    <div class='time-totals'>
                                        <div class='col l2 m4 s6'>
                                            <div class='card suite-total-tests'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_totalTests</span> 
                                                <span class='total-tests'> <span class='panel-lead'></span> </span> 
                                            </div> 
                                        </div>
                                        <div class='col l2 m4 s6'>
                                            <div class='card suite-total-steps'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_totalSteps</span> 
                                                <span class='total-steps'> <span class='panel-lead'></span> </span> 
                                            </div> 
                                        </div>
                                        <div class='col l4 m4 s12'>
                                            <div class='card suite-time'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_totalTimeTaken</span> 
                                                <span class='suite-total-time-taken panel-lead'>@Model.GetRunTime()</span> 
                                            </div> 
                                        </div>
                                        <div class='col l2 m6 s6 suite-start-time'>
                                            <div class='card green-accent'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_start</span> 
                                                <span class='panel-lead suite-started-time'>@Model.StartTime.ToString(dateTimeFormat)</span> 
                                            </div> 
                                        </div>
                                        <div class='col l2 m6 s6 suite-end-time'>
                                            <div class='card pink-accent'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_end</span> 
                                                <span class='panel-lead suite-ended-time'>@DateTime.Now.ToString(dateTimeFormat)</span> 
                                            </div> 
                                        </div>
                                    </div>
                                    <div class='charts'>
                                        <div class='col s12 m6 l4 fh'> 
                                            <div class='card-panel'> 
                                                <div>
                                                    <span class='panel-name'>@Localized.dashboard_panel_name_testsView</span>
                                                </div> 
                                                <div class='panel-setting modal-trigger test-count-setting right'>
                                                    <a href='#test-count-setting'><i class='mdi-navigation-more-vert text-md'></i></a>
                                                </div> 
                                                <div class='chart-box'>
                                                    <canvas class='text-centered' id='test-analysis'></canvas>
                                                </div> 
                                                <div>
                                                    <span class='weight-light'><span class='t-pass-count weight-normal'></span> @Localized.dashboard_panel_label_testsPassed</span>
                                                </div> 
                                                <div>
                                                    <span class='weight-light'><span class='t-fail-count weight-normal'></span> @Localized.dashboard_panel_label_testsFailed, <span class='t-others-count weight-normal'></span> @Localized.dashboard_panel_label_others</span>
                                                </div> 
                                            </div> 
                                        </div> 
                                        <div class='col s12 m6 l4 fh'> 
                                            <div class='card-panel'> 
                                                <div>
                                                    <span class='panel-name'>@Localized.dashboard_panel_name_stepsView</span>
                                                </div> 
                                                <div class='panel-setting modal-trigger step-status-filter right'>
                                                    <a href='#step-status-filter'><i class='mdi-navigation-more-vert text-md'></i></a>
                                                </div> 
                                                <div class='chart-box'>
                                                    <canvas class='text-centered' id='step-analysis'></canvas>
                                                </div> 
                                                <div>
                                                    <span class='weight-light'><span class='s-pass-count weight-normal'></span> @Localized.dashboard_panel_label_stepsPassed</span>
                                                </div> 
                                                <div>
                                                    <span class='weight-light'><span class='s-fail-count weight-normal'></span> @Localized.dashboard_panel_label_stepsFailed, <span class='s-others-count weight-normal'></span> @Localized.dashboard_panel_label_others</span>
                                                </div> 
                                            </div> 
                                        </div>
                                        <div class='col s12 m12 l4 fh'> 
                                            <div class='card-panel'> 
                                                <span class='panel-name'>@Localized.dashboard_panel_name_passPercentage</span> 
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
                                                <span class='label info right outline'>@Localized.dashboard_panel_name_environment</span>
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>@Localized.dashboard_panel_th_param</th>
                                                            <th>@Localized.dashboard_panel_th_value</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
									                    @foreach (KeyValuePair<string, string> entry in Model.SystemInfo)
                                                        {
                                                            <tr>
                                                                <td>@entry.Key</td>
                                                                <td>@entry.Value</td>
                                                            </tr>
                                                        }
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='category-summary-view'>
                                        <div class='col l4 m6 s12'>
                                            <div class='card-panel'>
                                                <span class='label info right outline'>@Localized.dashboard_panel_name_categories</span>
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>@Localized.dashboard_panel_th_name</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        @if (Model.CategoryMap != null)
                                                        {
                                                            foreach (KeyValuePair<string, List<RelevantCodes.ExtentReports.Model.Test>> entry in Model.CategoryMap)
                                                            {
                                                                <tr>
                                                                    <td>@entry.Key</td>
                                                                </tr>
                                                            }
                                                        }
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /dashboard -->

                                <!-- tests -->
                                <div id='test-view' class='row _addedTable'>
                                    <div class='col _addedCell1'>
                                        <div class='contents'>
                                            <div class='card-panel heading'>
                                                <h5>Tests</h5>
                                            </div>
                                            <div class='card-panel filters'>
                                                <div>
                                                    <a data-activates='tests-toggle' data-constrainwidth='true' data-beloworigin='true' data-hover='true' href='#' class='dropdown-button button tests-toggle'><i class='mdi-action-subject icon'></i></a>
                                                    <ul id='tests-toggle' class='dropdown-content'>
                                                        <li class='pass'><a href='#!'>Pass</a></li>
                                                        <li class='fail'><a href='#!'>Fail</a></li>
                                                        @if (Model.TestList.Where(x => x.GetTest().Status.Equals(LogStatus.Fatal)).Count() > 0)
                                                        {
                                                            <li class='fatal'><a href='#!'>Fatal</a></li>
                                                        }
                                                        @if (Model.TestList.Where(x => x.GetTest().Status.Equals(LogStatus.Error)).Count() > 0)
                                                        {
                                                            <li class='error'><a href='#!'>Error</a></li>
                                                        }
                                                        @if (Model.TestList.Where(x => x.GetTest().Status.Equals(LogStatus.Warning)).Count() > 0)
                                                        {
                                                            <li class='warning'><a href='#!'>Warning</a></li>
                                                        }
                                                        <li class='skip'><a href='#!'>Skip</a></li>
                                                        @if (Model.TestList.Where(x => x.GetTest().Status.Equals(LogStatus.Unknown)).Count() > 0)
                                                        {
                                                            <li class='unknown'><a href='#!'>Unknown</a></li>
                                                        }
                                                        <li class='divider'></li>
                                                        <li class='clear'><a href='#!'>@Localized.tests_filters_clearFilters</a></li>
                                                    </ul>
                                                </div>
                                                @if (Model.CategoryMap != null && Model.CategoryMap.Count > 0)
                                                {
                                                    <div>
                                                        <a data-activates='category-toggle' data-constrainwidth='false' data-beloworigin='true' data-hover='true' href='#' class='category-toggle dropdown-button button'><i class='mdi-image-style icon'></i></a>
                                                        <ul id='category-toggle' class='dropdown-content'>
                                                            <li class='divider'></li>
                                                            @if (Model.CategoryMap != null)
                                                            {
                                                                foreach (KeyValuePair<string, List<RelevantCodes.ExtentReports.Model.Test>> entry in Model.CategoryMap)
                                                                {
                                                                    <li class='@entry.Key'><a href='#!'>@entry.Key</a></li>
                                                                }
                                                            }
                                                            <li class='divider'></li>
                                                            <li class='clear'><a href='#!'>@Localized.tests_filters_clearFilters</a></li>
                                                        </ul>
                                                    </div>
                                                }
                                                <div>
                                                    <a id='clear-filters' alt='@Localized.tests_filters_clearFilters' title='@Localized.tests_filters_clearFilters'><i class='mdi-navigation-close icon'></i></a>
                                                </div>
                                                <div>&nbsp;&middot;&nbsp;</div>
                                                <div>
                                                    <a id='enableDashboard' alt='@Localized.tests_filters_enableDashboard' title='@Localized.tests_filters_enableDashboard'><i class='mdi-action-track-changes icon'></i></a>
                                                </div> 
                                                <div>
                                                    <a id='refreshCharts' alt='@Localized.tests_filters_refreshCharts' title='@Localized.tests_filters_refreshCharts' class='enabled'><i class='mdi-navigation-refresh icon'></i></i></a>
                                                </div>
                                                <div>&nbsp;&middot;</div>
                                                <div class='search' alt='@Localized.tests_filters_searchTests' title='@Localized.tests_filters_searchTests'>
                                                    <div class='input-field left'>
                                                        <input id='searchTests' type='text' class='validate' placeholder='@Localized.tests_filters_searchTests...'>
                                                    </div>
                                                    <i class='mdi-action-search icon'></i>
                                                </div>
                                            </div>
                                            <div class='card-panel no-padding-h no-padding-v'>
                                                <div class='wrapper'>
                                                    <ul id='test-collection' class='test-collection'>
                                                        @foreach (var extentTest in Model.TestList)
                                                        {
                                                            var test = extentTest.GetTest();

                                                            var cls = """";
                                                            if(test.ContainsChildNodes)
                                                            { 
                                                                cls = ""hasChildren""; 
                                                            }

                                                            <li class='collection-item test displayed active @test.Status.ToString().ToLower() @cls' extentid='@test.ID'>
                                                                <div class='test-head'>
                                                                    <span class='test-name'>@Raw(test.Name) @if (!String.IsNullOrEmpty(test.InternalWarning)) { <i class='tooltipped mdi-alert-error' data-position='top' data-delay='50' data-tooltip='@Raw(test.InternalWarning)'></i> }</span>
                                                                    <span class='test-status right label capitalize outline @test.Status.ToString().ToLower()'>@test.Status.ToString().ToLower()</span>
                                                                    <span class='category-assigned hide @test.GetCombinedCategories().ToLower()'></span>
                                                                </div>
                                                                <div class='test-body'>
                                                                    <div class='test-info'>
                                                                        <span title='@Localized.tests_test_info_testStartTime' alt='@Localized.tests_test_info_testStartTime' class='test-started-time label green lighten-2 text-white'>@test.StartTime.ToString(dateTimeFormat)</span>
                                                                        <span title='@Localized.tests_test_info_testEndTime' alt='@Localized.tests_test_info_testEndTime' class='test-ended-time label red lighten-2 text-white'>@test.EndTime.ToString(dateTimeFormat)</span>
                                                                        <span title='@Localized.tests_test_info_timeTaken' alt='@Localized.tests_test_info_timeTaken' class='test-time-taken label blue-grey lighten-3 text-white'>@test.RunTime</span>
                                                                    </div>
                                                                    <div class='test-desc'>@Raw(test.Description)</div>
                                                                    <div class='test-attributes'>
                                                                        @if (test.CategoryList != null && test.CategoryList.Count() > 0)
                                                                        {
                                                                            <div class='categories'>
                                                                            @foreach (var cat in test.CategoryList)
                                                                            {
                                                                                <span class='category'>@cat.Name</span>
                                                                            }
                                                                            </div>
                                                                        }
                                                                        @if (test.AuthorList != null && test.AuthorList.Count() > 0)
                                                                        {
                                                                            <div class='authors'>
                                                                            @foreach (var author in test.AuthorList)
                                                                            {
                                                                                <span class='author'>@author.Name</span>
                                                                            }
                                                                            </div>
                                                                        }
                                                                    </div>
                                                                    <div class='test-steps'>
                                                                        @if (test.LogList != null && test.LogList.Count() > 0)
                                                                        {
                                                                            <table class='bordered table-results'>
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>@Localized.tests_test_log_th_status</th>
                                                                                        <th>@Localized.tests_test_log_th_timestamp</th>
                                                                                        @if (test.LogList[0].StepName != null)
                                                                                        {
                                                                                            <th>StepName</th>
                                                                                        }
                                                                                        <th>@Localized.tests_test_log_th_details</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    @foreach (var log in test.LogList)
                                                                                    {
                                                                                        <tr>
                                                                                            <td class='status @log.LogStatus.ToString().ToLower()'><i class='@Icon.GetIcon(@log.LogStatus)'></i></td>" +
                                                                                            "<td class='timestamp'>@string.Format(\"{0:HH:mm:ss}\", log.Timestamp)</td>" +
                                                                                            @"@if (test.LogList[0].StepName != null)
                                                                                            {
                                                                                                <td class='step-name'>@Raw(log.StepName)</td>
                                                                                            }
                                                                                            <td class='step-details'>@Raw(log.Details)</td>
                                                                                        </tr>
                                                                                    }
                                                                                </tbody>
                                                                            </table>
                                                                        }
                                                                        @if (test.ContainsChildNodes)
                                                                        {
                                                                            <ul class='collapsible node-list' data-collapsible='accordion'>
                                                                                @foreach (var node in test.NodeList)
                                                                                {
                                                                                    <li class='displayed node-1x @node.Status.ToString().ToLower()' extentid='@node.ID'>
                                                                                        <div class='collapsible-header test-node @node.Status.ToString().ToLower()'>
                                                                                            <div class='right test-info'>
                                                                                                <span title='@Localized.tests_test_info_testStartTime' alt='@Localized.tests_test_info_testStartTime' class='test-started-time label green lighten-2 text-white'>@node.StartTime.ToString(dateTimeFormat)</span>
                                                                                                <span title='@Localized.tests_test_info_testEndTime' alt='@Localized.tests_test_info_testEndTime' class='test-ended-time label red lighten-2 text-white'>@node.EndTime.ToString(dateTimeFormat)</span>
                                                                                                <span title='@Localized.tests_test_info_timeTaken' alt='@Localized.tests_test_info_timeTaken' class='test-time-taken label blue-grey lighten-2 text-white'>@node.RunTime</span>
                                                                                                <span class='test-status label capitalize @node.Status.ToString().ToLower()'>@node.Status.ToString().ToLower()</span>
                                                                                            </div>
                                                                                            <div class='test-node-name'>@Raw(node.Name)</div>
                                                                                            <div class='test-node-desc'>@Raw(node.Description)</div>
                                                                                        </div>
                                                                                        <div class='collapsible-body'>
                                                                                            <div class='test-steps'>
                                                                                                @if (node.LogList != null && node.LogList.Count() > 0)
                                                                                                {
                                                                                                    <table class='bordered table-results'>
                                                                                                        <thead>
                                                                                                            <tr>
                                                                                                                <th>@Localized.tests_test_log_th_status</th>
                                                                                                                <th>@Localized.tests_test_log_th_timestamp</th>
                                                                                                                @if (node.LogList[0].StepName != null)
                                                                                                                {                                                
                                                                                                                    <th>StepName</th>
                                                                                                                }
                                                                                                                <th>@Localized.tests_test_log_th_details</th>
                                                                                                            </tr>
                                                                                                        </thead>
                                                                                                        <tbody>
                                                                                                            @foreach (var log in node.LogList)
                                                                                                            {
                                                                                                                <tr>
                                                                                                                    <td class='status @log.LogStatus.ToString().ToLower()'><i class='@Icon.GetIcon(@log.LogStatus)'></i></td>" +
                                                                                                                    "<td class='timestamp'>@string.Format(\"{0:HH:mm:ss}\", log.Timestamp)</td>" +
                                                                                                                    @"@if (node.LogList[0].StepName != null)
                                                                                                                    {
                                                                                                                        <td class='step-name'>@Raw(log.StepName)</td>
                                                                                                                    }
                                                                                                                    <td class='step-details'>@Raw(log.Details)</td>
                                                                                                                </tr>
                                                                                                            }
                                                                                                        </tbody>
                                                                                                    </table>
                                                                                                }
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
                                                                                }
                                                                            </ul>
                                                                        }
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        }
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id='test-details-wrapper' class='col _addedCell2'>
                                        <div class='contents'>
                                            <div class='card-panel details-view'>
                                                <h5 class='details-name'></h5>
                                                <div class='details-container'>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /tests -->

                                <!-- categories -->
                                @if (Model.CategoryMap != null && Model.CategoryMap.Count > 0)
                                {
                                    <div id='categories-view' class='row _addedTable hide'>
                                        <div class='col _addedCell1'>
                                            <div class='contents'>
                                                <div class='card-panel heading'>
                                                    <h5>@Localized.categories_heading</h5>
                                                </div>
                                                <div class='card-panel filters'>
                                                    <div class='search' alt='Search tests' title='Search tests'>
                                                        <div class='input-field left'>
                                                            <input id='searchTests' type='text' class='validate' placeholder='Search tests...'>
                                                        </div>
                                                        <i class='mdi-action-search icon'></i>
                                                    </div>
                                                </div>
                                                <div class='card-panel no-padding-h no-padding-v'>
                                                    <div class='wrapper'>
                                                        <ul id='cat-collection' class='cat-collection'>
                                                            @if (Model.CategoryMap != null)
                                                            {
                                                                foreach (KeyValuePair<string, List<RelevantCodes.ExtentReports.Model.Test>> entry in Model.CategoryMap)
                                                                {
                                                                    var passed = entry.Value.Where(x => x.Status.Equals(LogStatus.Pass)).Count();
                                                                    var failed = entry.Value.Where(x => x.Status.Equals(LogStatus.Fail)).Count();
                                                                    var others = entry.Value.Count - (passed + failed);

                                                                    <li class='category-item displayed @entry.Key.ToLower()'>
				                                                        <div class='cat-head'>
					                                                        <span class='category-name'>@entry.Key</span>
				                                                        </div>
				                                                        <div class='category-status-counts'>
                                                                            @if (passed > 0)
                                                                            {
					                                                            <span class='pass label dot'>Pass: @passed</span>
                                                                            }
                                                                            @if (failed > 0)
                                                                            {
					                                                            <span class='fail label dot'>Fail: @failed</span>
                                                                            }
                                                                            @if (others > 0)
                                                                            {
					                                                            <span class='other label dot'>Others: @others</span>
                                                                            }
				                                                        </div>
				                                                        <div class='cat-body'>
					                                                        <div class='category-status-counts'>
						                                                        <div class='button-group'>
                                                                                    <a href='#!' class='pass label filter'>Pass <span class='icon'>@passed</span></a>
                                                                                    <a href='#!' class='fail label filter'>Fail <span class='icon'>@failed</span></a>
                                                                                    <a href='#!' class='other label filter'>Others <span class='icon'>@others</span></a>
                                                                                </div>
					                                                        </div>
					                                                        <div class='cat-tests'>
						                                                        <table class='bordered'>
							                                                        <thead>
								                                                        <tr>
									                                                        <th>@Localized.categories_th_runDate</th>
									                                                        <th>@Localized.categories_th_testName</th>
									                                                        <th>@Localized.categories_th_status</th>
								                                                        </tr>
							                                                        </thead>
                                                                                    <tbody>
                                                                                        @foreach (var test in entry.Value)
                                                                                        {
                                                                                            <tr class='@test.Status.ToString().ToLower()'>
                                                                                                <td>@test.StartTime.ToString(dateTimeFormat)</td>
                                                                                                <td><span class='category-link linked' extentid='@test.ID'>@test.Name</span></td>
                                                                                                <td><div class='status label outline capitalize @test.Status.ToString().ToLower()'>@test.Status</div></td>
                                                                                            </tr>
                                                                                        }
                                                                                    </tbody>
						                                                        </table>
					                                                        </div>
				                                                        </div>
			                                                        </li>
                                                                }
                                                            }
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id='cat-details-wrapper' class='col _addedCell2'>
                                            <div class='contents'>
                                                <div class='card-panel details-view'>
                                                    <h5 class='cat-name'></h5>
                                                    <div class='cat-container'>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                }
                                <!-- /categories -->
                                
                                <!-- exceptions -->
                                @if (Model.ExceptionMap != null && Model.ExceptionMap.Count > 0)
                                {
                                    <div id='exceptions-view' class='row _addedTable hide'>
                                        <div class='col _addedCell1'>
                                            <div class='contents'>
                                                <div class='card-panel heading'>
                                                    <h5>@Localized.exceptions_heading</h5>
                                                </div>
                                                <div class='card-panel filters'>
                                                    <div class='search' alt='Search tests' title='Search tests'>
                                                        <div class='input-field left'>
                                                            <input id='searchTests' type='text' class='validate' placeholder='Search tests...'>
                                                        </div>
                                                        <i class='mdi-action-search icon'></i>
                                                    </div>
                                                </div>
                                                <div class='card-panel no-padding-h no-padding-v'>
                                                    <div class='wrapper'>
                                                        <ul id='exception-collection' class='exception-collection'>
                                                            @if (Model.ExceptionMap != null)
                                                            {
                                                                foreach (KeyValuePair<string, List<RelevantCodes.ExtentReports.Model.Test>> entry in Model.ExceptionMap)
                                                                {
                                                                    var count = entry.Value.Count;

                                                                    <li class='exception-item displayed @entry.Key.ToLower()'>
				                                                        <div class='exception-head'>
					                                                        <span class='exception-name'>@entry.Key</span>
				                                                        </div>
				                                                        <div class='exception-body'>
					                                                        <div class='exception-tests'>
						                                                        <table class='bordered'>
							                                                        <thead>
								                                                        <tr>
									                                                        <th>@Localized.exceptions_th_runDate</th>
									                                                        <th>@Localized.exceptions_th_testName</th>
									                                                        <th>@Localized.exceptions_th_exception</th>
								                                                        </tr>
							                                                        </thead>
                                                                                    <tbody>
                                                                                        @foreach (var test in entry.Value)
                                                                                        {
                                                                                            <tr class='@test.Status.ToString().ToLower()'>
                                                                                                <td>@test.StartTime.ToString(dateTimeFormat)</td>
                                                                                                <td><span class='exception-link linked' extentid='@test.ID'>@test.Name</span></td>
                                                                                                <td><pre class='exception-message'>@test.ExceptionList[0].ToString()</pre></td>
                                                                                            </tr>
                                                                                        }
                                                                                    </tbody>
						                                                        </table>
					                                                        </div>
				                                                        </div>
			                                                        </li>
                                                                }
                                                            }
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id='exception-details-wrapper' class='col _addedCell2'>
                                            <div class='contents'>
                                                <div class='card-panel details-view'>
                                                    <h5 class='exception-name'></h5>
                                                    <div class='exception-container'>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                }
                                <!-- /exceptions -->

                                <!-- testrunner logs -->
                                @if (Model.TestRunnerLogs != null && Model.TestRunnerLogs.Count > 0)
                                {
                                    <div id='testrunner-logs-view' class='row hide'>
                                        <div class='col s12'>
                                            <div class='card-panel'>
                                                <h5>@Localized.logs_heading</h5>
                                                @foreach (var log in Model.TestRunnerLogs)
                                                {
                                                    <p>@Raw(log)</p>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                }
                                <!-- /testrunner logs -->
                            </div>

                            <!-- modals -->
                            <div id='test-count-setting' class='modal bottom-sheet'> 
                                <div class='modal-content'> 
                                    <h5>@Localized.modal_heading_testCount</h5> 
                                    <input name='test-count-setting' type='radio' id='parentWithoutNodes' class='with-gap'> 
                                    <label for='parentWithoutNodes'>@Localized.modal_selection_parentTestOnly</label> 
                                    <br> 
                                    <input name='test-count-setting' type='radio' id='parentWithoutNodesAndNodes' class='with-gap'> 
                                    <label for='parentWithoutNodesAndNodes'>@Localized.modal_selection_parentWithoutChildNodes</label> 
                                    <br> 
                                    <input name='test-count-setting' type='radio' id='childNodes' class='with-gap'> 
                                    <label for='childNodes'>@Localized.modal_selection_childTests</label> 
                                </div> 
                                <div class='modal-footer'> 
                                    <a href='#!' class='modal-action modal-close waves-effect waves-green btn'>@Localized.modal_button_save</a> 
                                </div> 
                            </div> 
                            <div id='step-status-filter' class='modal bottom-sheet'> 
                                <div class='modal-content'> 
                                    <h5>@Localized.modal_heading_selectStatus</h5> 
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
                                    <a href='#!' class='modal-action modal-close waves-effect waves-green btn'>@Localized.modal_button_save</a> 
                                </div>
                            </div>
                            <!-- /modals -->

                            <script src='@protocol://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script> 
                            <script src='@protocol://code.jquery.com/ui/1.11.4/jquery-ui.min.js'></script>
                            <script src='@protocol://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.2/js/materialize.min.js'></script>
                            <script src='@protocol://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js'></script>
                            <script src='@protocol://cdn.rawgit.com/noelboss/featherlight/1.3.4/release/featherlight.min.js' type='text/javascript' charset='utf-8'></script>
                            <script src='@protocol://cdn.rawgit.com/anshooarora/extentreports/aac91b51dac7509a363ea8f5fb812ed8e47eb636/cdn/extent.js' type='text/javascript'></script>

                            <script>$(document).ready(function() { $('.logo span').html('ExtentReports'); });</script>
                            <script>@if (Model.ConfigurationMap != null && Model.ConfigurationMap.ContainsKey(""scripts"")) { @Raw(Model.ConfigurationMap[""scripts""]) }</script>
                        </body>
                    </html>
                    ".Replace("    ", "").Replace("\t", "").Replace("\r", "").Replace("\n", "");
            }
        }
    }
}
