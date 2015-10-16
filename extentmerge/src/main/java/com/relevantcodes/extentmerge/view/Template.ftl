<!DOCTYPE html>
<html>
    <head> 
        <!--
            ExtentReports Library 2.10 | http://relevantcodes.com/extentreports-for-selenium/ | https://github.com/anshooarora/
            Copyright (c) 2015, Anshoo Arora (Relevant Codes) | Copyrights licensed under the New BSD License | http://opensource.org/licenses/BSD-3-Clause
            Documentation: http://extentreports.relevantcodes.com
        --> 
        <meta http-equiv='content-type' content='text/html; charset=utf-8'> 
        <meta name='description' content=''> 
        <meta name='robots' content='noodp, noydir'> 
        <meta name='viewport' content='width=device-width, initial-scale=1'> 
        <title>Extent Merge Trends</title>
        <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css' type='text/css'> 
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css' type='text/css'> 
        <style type='text/css'>
            body {
                background: #eff2f8;
                font-size: 13px;
            }
            pre {
                border: 1px solid #ebedef;
                border-radius: 4px;
                background-color: #f8f9fa;
                color: #222 !important;
                font-family: Consolas, monospace;
                font-size: 13px;
                margin: 0;
                padding: 7px 10px;
                white-space: pre-wrap;
            }
            img {
                border: 4px solid #f6f7fa;
                display: block;
                margin-top: 15px;
                width: 150px;
            }
            th, td {
                border-bottom: 1px solid #ddd !important;
                color: #222 !important;
            }
            th {
                font-family: Roboto, Arial;
                font-size: 11px;
                font-weight: 500 !important;
                padding: 7px 10px;
                text-transform: uppercase;
            }
            td {
                font-size: 13px;
                font-weight: 400;
                padding: 7px 10px;
                word-break: break-all;
            }
            
            /* -- [ containers ] -- */
            .container {
                max-width: none;
                padding-left: 220px;
                padding-top: 8px;
                width: 98%;
            }
            .wrapper {
                background: #fff;
                height: 100%;
                overflow: auto;
            }
            
            /* -- [ material overrides ] -- */
            .z-depth-1, nav, .card-panel, .card, .toast, .btn, .btn-large, .btn-floating, .dropdown-content, .collapsible, .side-nav {
                box-shadow: none;
            }
            [type='checkbox']:checked + label::before {
                border-color: transparent #eee #eee transparent;
            }
            [type='checkbox'] + label::before {
                margin-top: 4px;
            }
            .select-wrapper input.select-dropdown, .input-field label {
                font-size: 0.8rem;
            }
            .material-tooltip, label {
                font-size: 13px !important;
            }
            .input-field label {
                font-size: 11px !important;
                left: 0;
            }
            table thead tr:first-child {
                border-bottom: 2px solid #cacaca;
            }
            
            /* -- [ global structure ] -- */
            .vh100 {
                height: 100vh;
            }
            .capitalize {
                text-transform: capitalize !important;
            }
            .upper {
                text-transform: uppercase !important;
            }
            .small {
                font-size: 11px;
            }
            .weight-light {
                font-weight: 400;
            }
            .weight-normal {
                font-weight: 500;
            }
            .weight-bold {
                font-weight: 700;
            }
            .text-white {
                color: #fff !important;
            }
            .no-padding {
                padding: 0 !important;
            }
            .no-padding-h {
                padding-left: 0 !important;
                padding-right: 0 !important;
            }
            .no-padding-v {
                padding-bottom: 0 !important;
                padding-top: 0 !important;
            }
            .no-margin-v {
                margin-bottom: 0 !important;
                margin-top: 0 !important;
            }
            .no-box-shadow {
                box-shadow: none !important;
            }
            .card {
                height: 100px;
                padding: 12px 15px;
            }
            .card-panel .panel-lead {
                margin-top: 70px;
            }
            .panel-lead {
                display: block;
                font-size: 20px;
                text-align: center;
            }
            .card .panel-lead {
                bottom: 10px;
                font-size: 15px;
                position: absolute;
                right: 15px;
            }
            .panel-setting {
                margin-top: -20px;
                padding: 0 5px;
            }
            .panel-setting:hover {
                background-color: #eee;
                border-radius: 5px;
                cursor: pointer;
            }
            .panel-setting i {
                color: #444;
            }
            img.active {
                max-width: inherit !important;
            }
            .report-img.active {
                border-radius: 0 !important;
                height: 80% !important;
                left: 10% !important;
                position: fixed !important;
                top: 10% !important;
                width: 80% !important;
            }
            .linked {
                color: #039be5;
                cursor: pointer;
            }
            
            /* -- [ top-nav ] -- */
            nav {
                padding-left: 70px;
            }
            nav, nav .nav-wrapper i, nav a.button-collapse, nav a.button-collapse i, nav label {
                background: #29ccf7;
                color: #eee;
                height: 51px;
                line-height: 49px;
            }
            nav ul li:hover {
                background: none;
            }
            .nav-right {
                margin-right: 35px;
            }
            .report-name {
                display: inline-block;
                padding-left: 25px;
            }
            
            /* -- [ side-nav ] -- */
            .side-nav {
                box-shadow: 0 6px 5px 0 rgba(0, 0, 0, 0.16);
                width: 220px;
            }
            .side-nav > li {
                color: #444;
                display: block;
            }
            .side-nav li.header {
                height: 37px;
                margin-top: 15px;
                padding-left: 30px;
            }
            .report-item, .side-nav .collapsible-body, .side-nav .collapsible-header.active, .side-nav .collapsible-body li.active, .side-nav.fixed .collapsible-body li.active {
                background: inherit;
            }
            .side-nav a, .side-nav label, .analysis i {
                font-size: 13px !important;
            }
            .side-nav i {
                padding-right: 15px;
            }
            .logo {
                background: #18bbe6 !important;
            }
            .logo a {
                color: #fff !important;
            }
            .side-nav .collapsible {
                margin: 0 -15px;
            }
            .side-nav .collapsible-header {
                margin: 0 !important;
            }
            .side-nav a, .side-nav .collapsible-header {
                height: 51px;
                line-height: 51px;
            }
            .side-nav .collapsible-header i {
                margin-right: 5px !important;
            }
            .side-nav .collapsible-body li {
                padding-left: 40px;
                width: 100%;
            }
                        
            /* -- [ run summary] -- */
            #run-summary-view td:first-child, #run-summary-view td:nth-child(2), #run-summary-view td:nth-child(3) {
                max-width: 150px;
            }
            #run-summary-view td:last-child {
                min-width: 150px;
            }
            
            /* -- [ collections ] -- */
            .test-collection {
                margin-bottom: 0;
                position: relative;
            }
            .test-collection li, .cat-collection li {
                border-bottom: 1px solid #e4e7e9;
                padding: 20px;
            }
            .cat-collection > li:first-child, .test-collection > li:first-child {
                margin-top: -13px;
            }
            
            /* -- [ filters ] -- */
            .filters {
                padding-bottom: 1px;
            }
            .dropdown-content li > a, .dropdown-content li > span {
                font-size: 0.85rem;
                line-height: 1.2rem;
                padding: 0.5rem 1rem;
            }
            
            /* -- [ dashboard, trends, report-dashboard ] -- */
            #report-dashboard.displayed + #report-view {
                margin-top: -20px;
            }
            #report-dashboard .card-panel {
                height: 283px;
            }
            #report-analysis, #test-analysis, #step-analysis {
                height: 130px;
                margin: 30px auto 0;
                text-align: center;
                width: 160px;
            }
            #report-trends-status-test, #report-trends-status-step {
                height: 200px;
                margin: 30px auto 0;
                text-align: center;
                width: 95%;
            }
            .trend-fh {
                height: 315px;
            }
            .chart-box {
                display: block;
                margin: 0 auto 20px;
                text-align: center;
            }
            .chart {
                height: 100px;
                margin: 10px auto 25px;
                position: relative;
                text-align: center;
                width: 100px;
            }
            .chart canvas {
                position: absolute;
                top: 0;
                left: 0;
            }
            .doughnut-legend li span {
                border-radius: 2px;
                display: block;
                float: left;
                height: 10px;
                margin-right: 8px;
                margin-top: 4px;
                width: 10px;
            }
            .doughnut-legend {
                display: inline-block;
                list-style: none;
                margin: 10px 0 0 -35px;
                font-size: 12px;
            }
            .doughnut-legend li {
                text-align: left;
            }
            .doughnut-legend li:first-letter {
                text-transform: capitalize;
            }
            #report-dashboard .progress {
                margin-top: 105px;
            }
            
            /* -- [ test ] -- */
            .test.active, .category-item.active {
                border-left: 3px solid #dd4f6a;
                color: #444 !important;
                /*background: #e8eefa !important;*/
            }
            .test .test-body, .cat-collection .cat-body {
                display: none;
            }
            .test-info {
                margin: 5px 0 10px;
            }
            .test-name {
                display: inline-block;
                width: 70% !important;
                word-break: break-all;
            }
            .test-desc {
                margin-bottom: 10px;
            }
            .test-steps, .cat-tests {
                margin-top: 50px;
            }
            .collapsible-body .test-steps {
                margin-top: 30px;
            }
            .test-steps th:nth-child(2), .test-steps td:nth-child(2) {
                max-width: 90px;
                width: 90px;
            }
            .test-steps th:first-child, .test-steps td:first-child {
                text-align: left !important;
                max-width: 65px;
                width: 35px;
            }
            .test-body .collapsible-header {
                background-color: inherit;
                border-bottom: none;
                height: auto;
                line-height: inherit;
                padding: 1.2rem 1rem;
            }
            .test-body table {
                margin-top: 10px;
            }
            .test-body .collapsible-body {
                padding: 0 15px 30px;
                width: 100%;
            }
            .test-body .collapsible-body table {
                margin-top: -20px;
            }
            .test-body .collapsible {
                border: none !important;
                box-shadow: none !important;
            }
            .test-body .collapsible > li.active {
                border: 1px solid #999;
                background: #fff;
                box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
            }
            .collapsible .test-info {
                margin: 0 0 0 20px;
            }
            .test-body .collapsible > li {
                background: #edf3ff;
                box-shadow: none;
                margin-bottom: 2px;
            }
            .test-node-name {
                display: inline-block;
            }
            .test-node .test-started-time, .test-node  .test-ended-time, .test-node  .test-time-taken {
                display: none;
            }
            .test-node.active .test-started-time, .test-node.active  .test-ended-time, .test-node.active  .test-time-taken {
                display: inline-block;
            }
            .node-2x {
                margin-left: 20px !important;
            }
            .node-3x {
                margin-left: 35px  !important;
            }
            .node-4x {
                margin-left: 50px  !important;
            }
            .node-5x {
                margin-left: 65px !important;
            }
            .node-6x {
                margin-left: 80px !important;
            }
            .node-7x {
                margin-left: 95px !important;
            }
            .node-8x {
                margin-left: 110px !important;
            }
            
            /* -- [ test details ] -- */
            .test-details-container .test-steps {
                display: block !important;
            }
            .details-container, .cat-container {
                background: #fff none repeat scroll 0 0;
                margin: 0 -20px;
                padding: 0 20px 75px;
            }
            .details-view {
                position: absolute;
            }
            .pin {
                height: 96%;
                right: 1%;
                overflow-y: auto;
                width: 50.3%;
            }
            
            /* -- [ test-attributes ] -- */
            span.category {
                border: 1px solid #00aeef;
                color: #00aeef !important;
                border-radius: 25px;
                font-size: 12px;
                padding: 3px 8px;
            }
            
            /* -- [ test-history modal ] -- */
            .history {
                cursor: pointer;
                margin-left: 10px;
            }
            .history i {
                color: #999 !important;
            }
            #test-history-modal .test-body {
                display: block !important;
            }
            #test-history-modal .modal-box {
                background: #edf3ff;
                margin-bottom: 2px;
                padding: 10px 20px;
            }
            #test-history-modal .test-history {
                margin-top: 30px;
            }
            #test-history-modal .modal-test-name {
                display: inline-block;
                font-size: 16px;
                margin-right: 20px;
            }
            #test-history-modal .modal-test-status {
                text-transform: capitalize;
            }
            #test-history-modal .modal-test-info {
                margin-top: 10px;
            }
            .history-navigation {
                float: right;
            }
            .mdi-action-launch {
                cursor: pointer;
                font-size: 20px;
            }
            
            /* -- [ progress bar ] -- */
            .progress2 {
                background-color: #f5f5f5;
                border-radius: 4px;
                box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1) inset;
                height: 15px;
                margin: 10px 0;
                max-width: 350px;
                overflow: hidden;
            }
            .progress-bar-success {
                background-color: #5cb85c !important;
            }
            .progress-bar-warning {
                background-color: #ffa81c !important;
            }
            .progress-bar-danger {
                background-color: #d9534f !important;
            }
            .progress-bar-skip {
                background-color: #46BFBD !important;
            }
            .progress-bar2 {
                background-color: #337ab7;
                box-shadow: 0 -1px 0 rgba(0, 0, 0, 0.15) inset;
                color: #fff;
                float: left;
                font-size: 11px;
                font-weight: 500;
                height: 100%;
                line-height: 15px;
                text-align: center;
                transition: width 0.6s ease 0s;
                width: 0;
            }
            .progress-striped .progress-bar, .progress-bar-striped {
                background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.15) 50%, rgba(255, 255, 255, 0.15) 75%, transparent 75%, transparent);
                background-size: 40px 40px;
            }
            .sr-only {
                border: 0 none;
                clip: rect(0px, 0px, 0px, 0px);
                height: 1px;
                margin: -1px;
                padding: 0;
                position: relative;
                width: 1px;
            }

            /* -- [ status styles ] -- */
            .status.fail, .fail i {
                color: #eb4549;
            } 
            .status.fatal, .fatal > i {
                color: darkred;
            }
            .status.error, .error > i {
                color: tomato;
            } 
            .status.warning, .warning > i {
                color: orange;
            }
            .status.pass, .pass > i {
                color: #32CD32;
            }                
            .status.info, .info > i {
                color: #22a1c4;
            } 
            .status.skip, .skip > i {
                color: #999;
            }
            .status.unknown, .unknown > i {
                color: #222;
            }
            
            /* -- [ labels ] -- */
            .test-status.label, .status.label {
                text-align: center;
                width: 60px;
            }
            .label {
                border: 1px solid transparent;
                border-radius: 2px;
                display: inline-block;
                font-size: 11px;
                font-weight: 500;
                padding: 2px 5px;
                text-transform: none;
            }
            .label.success, .label.pass {
                border-color: #60b963;
                color: #60b963;
            }
            .cat-label {
                border: 1px solid transparent;
            }
            .cat-pass {
                border-color: #46be8a !important;
                color: #46be8a;
            }
            .label.fatal {
                border-color: rgb(139, 0, 0);
                color: rgb(139, 0, 0);
            }
            .label.failure, .label.fail {
                border-color: #c64444;
                color: #c64444;
            }
            .cat-fail {
                border-color: #f96868 !important;
                color: #f96868;
            }
            .label.error {
                border-color: #ec407a;
                color: #ec407a;
            }
            .label.info {
                border-color: #46BFBD;
                color: #46BFBD;
            }
            .label.warn, .label.warning {
                border-color: #d88519;
                color: #d88519;
            }
            .cat-other {
                border-color: #f2a654 !important;
                color: #f2a654;
            }
            .label.skip, .label.skipped {
                border-color: #2196f3;
                color: #2196f3
            }
            .label.unknown {
                border-color: #444;
                color: #444;
            }
            .label.html, .label.db {
                background: #2196f3;
            }
            .label.date {
                background: #eee;
            }
            /* -- [ media queries ] -- */
            @media all and (max-width: 992px) {
                nav {
                    padding-left: 1%;
                }
                header, main, footer, .container {
                    padding-left: 0;
                }
            }
        </style>
        <#if (customizer.inlineCss)??> 
            <style type='text/css'>
                ${customizer.inlineCss}
            </style>
        </#if>
        <#if (customizer.stylesheet)??>
            <link rel='stylesheet' href='${customizer.stylesheet}' type='text/css'>
        </#if>
    </head>
    <body class='extent-merge'>
        <nav>
            <ul id='slide-out' class='side-nav fixed'>
                <li class='logo'>
                    <a href='http://extentreports.relevantcodes.com'><span>ExtentMerge</span></a>
                </li>
                <li class='upper small weight-normal header'>Analysis</li>
                <li class='analysis waves-effect active'><a href='#!' class='dashboard-view'><i class='fa fa-bar-chart'></i>Dashboard</a></li>
                <li class='analysis waves-effect'><a href='#!' class='trends-view'><i class='fa fa-line-chart'></i>Trends</a></li>
                <li class='upper small weight-normal header'>Reports</li>
                <span class='placeholder reports-placeholder hide'></span>
                <#list reportList as report>
                    <li class='report-item waves-effect' id='${report.id.toString()}'>
                        <a href='#!'>
                            <i class='mdi-action-assignment'></i>
                            <span class='report-date'>
                                <span class='report-date'>${report.formattedDate}</span>
                                <span class='report-time'>${report.formattedTime}</span>                            
                            </span>
                        </a>
                    </li>
                </#list>
                <li class='upper small weight-normal header'>Misc</li>
                <li class='analysis waves-effect'><a href='#!' class='logs-view'><i class='mdi-action-assignment'></i>Logs</a></li>
            </ul>
            <a href='#' data-activates='slide-out' class='button-collapse'><i class='fa fa-bars fa-2x'></i></a>
            <ul class="right hide-on-med-and-down nav-right hide"> 
                <li> <input type="checkbox" id="enableDashboard" class="enabled"> <label for="enableDashboard">Enable Dashboard</label> </li> 
            </ul>
        </nav>
        
        <div class='container'>
            <div id='dashboard-view' class='row'>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Tests</span> 
                        <span class='total-tests'><span class='panel-lead'>${mergedData.overallTestCount}</span></span> 
                    </div>
                </div>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Tests Passed</span> 
                        <span class='total-tests-passed'><span class='panel-lead'>${mergedData.overallTestPassedCount}</span></span>
                    </div>
                </div>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Tests Failed</span> 
                        <span class='total-tests-failed'><span class='panel-lead'>${mergedData.overallTestFailedCount}</span></span>
                    </div>
                </div>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Steps</span> 
                        <span class='total-steps'><span class='panel-lead'>${mergedData.overallStepCount}</span></span>
                    </div>
                </div>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Steps Passed</span> 
                        <span class='total-steps-passed'><span class='panel-lead'>${mergedData.overallStepPassedCount}</span></span>
                    </div>
                </div>
                <div class='col l2 s6'>
                    <div class='card'>
                        <span class='panel-name'>Total Steps Failed</span> 
                        <span class='total-steps-failed'><span class='panel-lead'>${mergedData.overallStepFailedCount}</span></span>
                    </div>
                </div>
                <div id='run-summary-view'>
                    <div class='col l8 m12 s12'>
                        <div class='card-panel'>
                            <table class='bordered'>
                                <thead>
                                    <th></th>
                                    <th>Date</th>
                                    <th>Source</th>
                                    <th>Duration</th>
                                    <th>Test Count</th>
                                    <th>Summary</th>
                                </thead>
                                <tbody>
                                    <#list reportList as report>
                                        <tr>
                                            <td class='goto-report'><i class='mdi-action-launch'></i></td>
                                            <td class='report-date'><span class='label date'>${report.formattedDate} ${report.formattedTime}</span></td>
                                            <td class='report-source'><span class='label text-white ${report.sourceType.toString()?lower_case}'>${report.sourceType.toString()}</span></td>
                                            <td class='run-duration'>${report.formattedRunDuration}</td>
                                            <td class='tests-count'>${report.testList?size}</td>
                                            <td class='report-progress'></td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class='col l4 m8 s12'>
                    <div class='card-panel'>
                        <div>
                            <span class='panel-name'>Overall Pass Rate</span>
                        </div> 
                        <div class='chart-box'>
                            <canvas class='text-centered' id='report-analysis'></canvas>
                        </div>
                        <div>
                            <span class='weight-light'>
                                <span class='master-test-count weight-normal'>${mergedData.overallTestCount}</span> tests merged, 
                                <span class='master-test-count-passed weight-normal'>${mergedData.overallTestPassedCount}</span> test(s) passed
                            </span>
                        </div> 
                        <div>
                            <span class='weight-light'>
                                <span class='master-test-failed-count weight-normal'>${mergedData.overallTestFailedCount}</span> test(s) failed, 
                                <span class='master-test-others-count weight-normal'>${mergedData.overallTestOthersCount}</span> others
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div id='trends-view' class='row'>
                <div class='col l6 m12 s12'>
                    <div class='card-panel'>
                        <div>
                            <span class='panel-name'>Report Trends By Status - Test</span>
                        </div> 
                        <div class='chart-box'>
                            <canvas class='text-centered' id='report-trends-status-test'></canvas>
                        </div> 
                    </div>
                </div>
                <div class='col l6 m12 s12'>
                    <div class='card-panel'>
                        <div>
                            <span class='panel-name'>Report Trends By Status - Step</span>
                        </div> 
                        <div class='chart-box'>
                            <canvas class='text-centered' id='report-trends-status-step'></canvas>
                        </div> 
                    </div>
                </div>
                <div class='col l6 m12 s12'>
                    <div class='card-panel'>
                        <span class='panel-name'>Top Passed (Showing Top 10 Only)</span>
                        <div><br /></div>
                        <table class='bordered pass-trends'>
                            <thead>
                                <tr>
                                    <th>Test Name</th>
                                    <th>Times Passed</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list topPassed?keys as entry>
                                    <#if entry_index < 10>
                                    <tr>
                                        <td>${entry}</td>
                                        <td>${topPassed[entry]}</td>
                                    </tr>
                                    </#if>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class='col l6 m12 s12'>
                    <div class='card-panel'>
                        <span class='panel-name'>Top Failed (Showing Top 10 Only)</span>
                        <div><br /></div>
                        <table class='bordered fail-trends'>
                            <thead>
                                <tr>
                                    <th>Test Name</th>
                                    <th>Times Failed</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list topFailed?keys as entry>
                                    <#if entry_index < 10>
                                        <tr>
                                            <td>${entry}</td>
                                            <td>${topFailed[entry]}</td>
                                        </tr>
                                    </#if>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div id='report-dashboard' class='row'>
                <div class='col s4'>
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
                <div class='col s4'>
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
                <div class='col s4'>
                    <div class='card-panel'>
                        <span class="panel-name">Pass Percentage</span> 
                        <span class="pass-percentage panel-lead"></span> 
                        <div class="progress light-blue lighten-3">
                            <div class="determinate light-blue" style="width:52%"></div> 
                        </div> 
                    </div>
                </div>
            </div>
            <div id='report-view' class='row hide'>
                <#list reportList as report>
                    <div class='col s5 hide report-view ${report.id.toString()} ${report.endedStatus}'>
                        <span class='hide report-date' id='${report.formattedDate} ${report.formattedTime}'></span>
                        <div class='card-panel filters'>
                            <div class='input-field no-margin-v'>
                                <input id='searchTests' type='text' class='validate'>
                                <label class='active' for='searchTests'>Search Tests..</label>
                            </div>
                            <div class='row'>
                                <div class='col s6'>
                                    <div class='input-field tests-toggle'>
                                        <select>
                                            <option value='0' selected>Choose your option</option>
                                            <option value='1'>Pass</option>
                                            <option value='2'>Fatal</option>
                                            <option value='3'>Fail</option>
                                            <option value='4'>Error</option>
                                            <option value='5'>Warning</option>
                                            <option value='6'>Skip</option>
                                            <option value='7'>Unknown</option>
                                            <option value='8'>Clear Filters</option>
                                        </select>
                                        <label>Filter By Status</label>
                                    </div>
                                </div>
                                <div class='col s6'>
                                    <div class='input-field category-toggle'>
                                        <select disabled>
                                            <option value='0' selected>Choose your option</option>
                                            <option value='8'>Clear Filters</option>
                                        </select>
                                        <label>Filter By Category</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class='card-panel no-padding-h no-padding-v'>
                            <div class='wrapper'>
                                <ul class='test-collection'>
                                    <#list report.testList as test>
                                        <#assign hasChildren = ''>
                                        <#if (test.nodeList)?? && test.nodeList?has_content>
                                            <#assign hasChildren = 'hasChildren'>
                                        </#if>
                                        <li class='collection-item test displayed active ${hasChildren} ${test.status}' extentid='${test.id.toString()}'>
                                            <div class='test-head'>
                                                <span class='test-name'>${test.name}</span>
                                                <span class='history right modal-trigger' href='#test-history-modal'><i class='fa fa-history'></i></span>
                                                <span class='test-status right label capitalize ${test.status.toString()}'>${test.status.toString()}</span>
                                                <span class='category-assigned hide ${test.categoriesAsTags?join(' ')}'></span>
                                            </div>
                                            <div class='test-body'>
                                                <div class='test-info'>
                                                    <span title='Test started time' class='test-started-time label green lighten-2 text-white'>${test.formattedStartedTime}</span>
                                                    <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'>${test.formattedEndedTime}</span>
                                                    <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-3 text-white'>${test.formattedTimeDiff}</span>
                                                </div>
                                                <div class='test-desc'>${test.description}</div>
                                                <div class='test-attributes'>
                                                    <div class='categories'>
                                                        <#list test.categoriesAsTags as category>
                                                            <span class="category text-white">${category}</span>
                                                        </#list>
                                                    </div>
                                                </div>
                                                <div class='test-steps'>
                                                    <table class='bordered table-results'>
                                                        <thead>
                                                            <tr>
                                                                <#if (test.log?first)??>
                                                                    <#if (test.log[0].stepName)??>
                                                                        <th>Status</th>
                                                                        <th>Timestamp</th>
                                                                        <th>StepName</th>
                                                                        <th>Details</th>
                                                                    <#else>
                                                                        <th>Status</th>
                                                                        <th>Timestamp</th>
                                                                        <th>Details</th>
                                                                    </#if>
                                                                </#if>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <#list test.log as log>
                                                                <tr>
                                                                    <td class='status ${log.logStatus.toString()}'><i class='fa fa-${log.icon}'></i></td>
                                                                    <td class='timestamp'>${log.formattedTimestamp}</td>
                                                                    <#if (test.log[0].stepName)??>
                                                                        <td class='step-name'>
                                                                            <#if (log.stepName)??>
                                                                                ${log.stepName}
                                                                            </#if>
                                                                        </td>
                                                                    </#if>
                                                                    <td class='step-details'>${log.details}</td>
                                                                </tr>
                                                            </#list>
                                                        </tbody>
                                                    </table>
                                                    <ul class='collapsible node-list' data-collapsible='accordion'>
                                                        <#if (test.nodeList)?? && test.nodeList?has_content>
                                                            <#list test.nodeList as node>
                                                                <li extentid='${node.id}'>
                                                                    <div class='collapsible-header test-node ${node.status.toString()}'>
                                                                        <div class='right test-info'>
                                                                            <span title='Test started time' class='test-started-time label green lighten-2 text-white'>${node.formattedStartedTime}</span>
                                                                            <span title='Test ended time' class='test-ended-time label red lighten-2 text-white'>${node.formattedEndedTime}</span>
                                                                            <span title='Time taken to finish' class='test-time-taken label blue-grey lighten-2 text-white'>${node.formattedTimeDiff}</span>
                                                                            <span class='test-status label capitalize ${node.status.toString()}'>${node.status.toString()}</span>
                                                                        </div>
                                                                        <div class='test-node-name'>${node.name}</div>
                                                                    </div>
                                                                    <div class='collapsible-body'>
                                                                        <div class='test-steps'>
                                                                            <table class='bordered table-results'>
                                                                                <thead>
                                                                                    <tr>
                                                                                        <#if (node.log?first)??>
                                                                                            <#if (node.log[0].stepName)??>
                                                                                                <th>Status</th>
                                                                                                <th>Timestamp</th>
                                                                                                <th>StepName</th>
                                                                                                <th>Details</th>
                                                                                            <#else>
                                                                                                <th>Status</th>
                                                                                                <th>Timestamp</th>
                                                                                                <th>Details</th>
                                                                                            </#if>
                                                                                        </#if>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <#list node.log as log>
                                                                                        <tr>
                                                                                            <td class='status ${log.logStatus.toString()}'><i class='fa fa-${log.icon}'></i></td>
                                                                                            <td class='timestamp'>${log.formattedTimestamp}</td>
                                                                                            <#if (node.log[0].stepName)??>
                                                                                                <td class='step-name'>
                                                                                                    <#if (log.stepName)??>
                                                                                                        ${log.stepName}
                                                                                                    </#if>
                                                                                                </td>
                                                                                            </#if>
                                                                                            <td class='step-details'>${log.details}</td>
                                                                                        </tr>
                                                                                    </#list>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </#list>
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
                </#list>
                <div id='test-details-wrapper' class='col s7'>
                    <div class='card-panel vh100 details-view pin'>
                        <h5 class='details-name'></h5>
                        <div class='details-container'>
                        </div>
                    </div>
                </div>
            </div>
            <div id='logs-view' class='row hide'>
                <div class='col s12'>
                    <div class='card-panel'>
                        <#list logs as log>
                            <p>
                                [${log.formattedTime}] [${log.logStatus.toString()?upper_case}] ${log.details}
                            </p>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
        <div id='test-history-modal' class='modal'>
            <div class='modal-content'>
                <h5>Run History - <span></span></h5>
                <div class='test-history'></div>
            </div>
            <div class='modal-footer test-history-modal'>
                <a href='#!' class=' modal-action modal-close waves-effect waves-green btn-flat'>Close</a>
            </div>
        </div>
        <div id="test-count-setting" class="modal bottom-sheet"> 
            <div class="modal-content"> 
                <h5>Configure Tests Count Setting</h5> 
                <input name="test-count-setting" type="radio" id="parentWithoutNodes" class="with-gap"> 
                <label for="parentWithoutNodes">Parent Tests Only (Does not include child nodes in counts)</label> 
                <br>
                <input name="test-count-setting" type="radio" id="parentWithoutNodesAndNodes" class="with-gap"> 
                <label for="parentWithoutNodesAndNodes">Parent Tests Without Child Tests + Child Tests</label> 
                <br> 
                <input name="test-count-setting" type="radio" id="childNodes" class="with-gap"> 
                <label for="childNodes">Child Tests Only</label> 
            </div> 
            <div class="modal-footer"> 
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn">Save</a> 
            </div>
        </div>
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
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script> 
        <script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js'></script> 
        <script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js'></script> 
        <script type='text/javascript'>
            $('.modal-trigger').leanModal();
            
            /* report data - this is for a single report and updated each time a report is viewed */
            var totalTests, passedTests, failedTests, fatalTests, warningTests, errorTests, skippedTests, unknownTests;
            var totalSteps, passedSteps, failedSteps, fatalSteps, warningSteps, errorSteps, infoSteps, skippedSteps, unknownSteps;
            var testChart, stepChart;
            
            $(document).ready(function() {
                /* init */
                $('.button-collapse').sideNav({ menuWidth: 220 });
                $('select').material_select();
                $('#enableDashboard').prop('checked', false);
                
                /* sizing for pinned content */
                $(document).ready(sizing);
                $(window).resize(sizing);
                function sizing() {
                    var t = $('#test-details-wrapper .pin');
                    var width = $(window).width();
                    if (width > 1850) { t.css('width', '50%'); } 
                    else if (width > 1550 && width < 1850) { t.css('width', '49%'); }
                    else if (width > 1350 && width < 1550) { t.css('width', '48%'); }
                    else if (width < 1350 && width > 1000) { t.css('width', '47%'); }
                    else if (width <= 992) { t.css('width', '57%'); }
                }
                
                /* [TOPNAV] */
                $('#enableDashboard').click(function() {
                    var t = $(this);
                    t.toggleClass('enabled');
                    $('#report-dashboard').toggleClass('hide');
                    if (t.prop('checked')) { $('#report-dashboard').addClass('displayed'); redrawCharts(); }
                    else { $('#report-dashboard').removeClass('displayed'); }
                });
                
                /* side-nav navigation [SIDE-NAV] */
                $('.report-item').click(function() {
                    $('#dashboard-view, #report-view, #report-view .s5, #trends-view, #logs-view').addClass('hide');
                    $('.analysis, .report-item').removeClass('active');
                    $(this).addClass('active');
                    $('#report-view, nav .nav-right').removeClass('hide');
                    $('#report-view .' + $(this).attr('id')).removeClass('hide').find('.test').eq(0).click();
                    if ($('#enableDashboard').prop('checked')) { $('#report-dashboard').removeClass('hide').addClass('displayed'); redrawCharts(); }
                });
                $('.analysis').click(function() {
                    $('.container > .row, nav .nav-right').addClass('hide').removeClass('displayed');
                    var cls = $(this).children('a').prop('class');
                    $('#' + cls).removeClass('hide');
                    $('.analysis, .report-item').removeClass('active');
                    $(this).addClass('active');
                });
                
                /* hide progress with zeros [RUN SUMMARY] */
                $('#run-summary-view .sr-only').each(function() {
                    var t = $(this);
                    if (t.text() == '0') { t.parent().addClass('hide'); }
                });
                
                /* test-dashboard settings [REPORT DASHBOARD] */
                $('#report-dashboard .test-count-setting').click(function() {
                    $('#test-count-setting').openModal();
                });
                /* test count setting */
                    /* init */
                    $('#parentWithoutNodesAndNodes').click();
                $('#test-count-setting input').click(function() {
                    $('#test-count-setting').removeClass('parentWithoutNodes parentWithoutNodesAndNodes childNodes');
                    $('#test-count-setting').addClass($(this).prop('id'));
                });
                /* refresh charts when chart setting is saved */
                $('.modal-footer').click(function() {
                    if (!$(this).hasClass('test-history-modal')) {
                        redrawCharts();
                        $('.lean-overlay').each(function() {
                            $(this).remove();
                        });
                    }
                });
                /* step-dashboard settings [DASHBOARD] */
                $('#report-dashboard .step-status-filter').click(function() {
                    $('#step-status-filter').openModal();
                });
                /* check all checkboxes for step-dashboard filter to allow filtering the steps to be displayed [DASHBOARD] */
                $('#step-status-filter input').prop('checked', 'checked');
                $('#step-status-filter input').click(function() {
                   $('#step-status-filter').toggleClass($(this).prop('id').replace('step-dashboard-filter-', ''));
                });
                /* navigate from dashboard (summary) to the report [DASHBOARD] */
                $('#run-summary-view .goto-report').click(function() {
                    var index = $(this).parent().index();
                    $('#slide-out .report-item').eq(index).click();
                });
                
                /* view test info [TEST] */
                $('.test').click(function() {
                    var t = $(this);
                    if ($('#test-details-wrapper .test-body').length > 0) {
                        $('.test').filter(function() {
                            return ($(this).find('.test-body').length == 0);
                        }).append($('#test-details-wrapper .test-body'));
                    }
                    $('.test').removeClass('active');
                    $('#test-details-wrapper .test-body').remove();
                    var el = t.addClass('active').find('.test-body');
                    $('#test-details-wrapper .details-name').text(t.find('.test-name').text());
                    $('#test-details-wrapper .details-container').append($(el));
                });
                $('.test').eq(0).click();
                /* history functionality [TEST] */
                function findTestByName(name) {
                    $el = $('.test-name').filter(function() {
                        return ($(this).text() == name);
                    }).closest('.test');
                    $('#test-history-modal h5 > span').text(name);
                    $('#test-history-modal .test-history').empty();
                    $el.each(function() {
                        var name = $(this).find('.test-name').text();
                        var status = $(this).find('.test-head  > .test-status').text();
                        var testInfo = $(this).find('.test-info').html() == null ? $('.details-container .test-info').html() : $(this).find('.test-info').html();
                        $('.test-history').append(
                            '<div class=\'modal-box\' extentid=\'' + $(this).attr('extentid') + '\' reportid=\'' + $(this).closest('.report-view').attr('reportid') + '\'>' +  
                                '<div class=\'modal-test-name\'>' + name + '</div>' +
                                '<div class=\'history-navigation\'>' +
                                    '<i class=\'mdi-action-launch\'></i>' +
                                '</div>' + 
                                '<div class=\'modal-test-info\'>' +
                                    '<span class=\'modal-test-status label ' + status + '\'>' + status + '</span>' + 
                                    testInfo +
                                '</div>' +
                            '</div>');
                    });
                }
                /* history functionality [TEST] */
                $('.modal-trigger.history').click(function() {
                    var testName = $(this).closest('.test').find('.test-name').text();
                    findTestByName(testName);
                });
                $('.test-history').click(function(evt) {
                    var cls = evt.target.className;
                    if (cls == 'mdi-action-launch' || cls == 'history-navigation') {
                        $('#test-history-modal').closeModal();
                        var t = $(evt.target);
                        var modalBox = t.closest('.modal-box');
                        var testId = modalBox.attr('extentid');
                        var reportId = modalBox.attr('reportid');
                        $('#' + reportId).click();
                        $('.test').filter(function() {
                            return ($(this).attr('extentid') == testId)
                        }).click();
                    }
                });
                /* filter tests by text [TEST] */
                $(document).keypress(function(e) {
                    if(e.which == 13) {
                        if ($('#searchTests').is(':focus')) {
                            var txt = $('#searchTests').val().toLowerCase();
                            $('.test').removeClass('displayed').hide(0);
                            $('.test-name, .test-desc').each(function() {
                                if ($(this).text().toLowerCase().indexOf(txt) >= 0) {
                                    $(this).closest('.test').addClass('displayed').show();
                                }
                            });
                            if ($('.details-container .test-node-name').text().toLowerCase().indexOf(txt) >= 0) {
                                $('.test.active').show();
                            }
                            $('.test:visible').eq(0).click();
                        }
                        if ($('#searchCats').is(':focus')) {
                            var txt = $('#searchCats').val().toLowerCase();
                            $('.category-item').removeClass('displayed').hide(0);
                            $('.category-name').each(function() {
                                if ($(this).text().toLowerCase().indexOf(txt) >= 0) {
                                    $(this).closest('.category-item').addClass('displayed').show();
                                }
                            });
                            $('.category-item:visible').eq(0).click();
                        }
                    }
                });
                /* if only header row is available for test, hide the table [TEST] */
                $('.table-results').filter(function() {
                    return ($(this).find('tr').length == 1);
                }).hide(0);
                /* filter tests by status [TEST] */
                $('.tests-toggle li').click(function() {
                    var opt = $(this).text().toLowerCase();
                    var opt2 = $('.category-toggle li.active').text().toLowerCase();
                    if (opt2 == 'choose your option' || opt2 == 'clear filters') opt2 = '';
                    if (opt != 'choose your option') {
                        if (opt == 'clear filters') {
                            resetFilters();
                        } 
                        else {
                            $('.tests-toggle li').removeClass('active');
                            $(this).addClass('active');
                            $('.test').hide(0).removeClass('displayed');
                            if (opt2 != '') {
                                $('.test').each(function() {
                                    if (($(this).hasClass(opt) || $(this).has('.test-node.' + opt).length > 0) && $(this).find('.category-assigned').hasClass(opt2)) {
                                        $(this).addClass('displayed').show(0);
                                    }
                                });
                            } else {
                                $('.test').hide(0).removeClass('displayed');
                                $('.test:has(.test-node.' + opt + '), .test.' + opt).fadeIn(200).addClass('displayed');
                            }
                            redrawCharts();
                        }
                    }
                });
                /* filter tests by category [TEST] */
                $('.category-toggle li').click(function() {
                    var opt = $(this).text().toLowerCase();
                    var opt2 = $('.tests-toggle li.active').text().toLowerCase();
                    if (opt2 == 'choose your option' || opt2 == 'clear filters') opt2 = '';
                    if (opt != 'choose your option') {
                        if (opt == 'clear filters') {
                            resetFilters();
                        } else {
                            $('.category-toggle li').removeClass('active');
                            $(this).addClass('active');
                            $('.test').hide(0).removeClass('displayed');
                            if (opt2 != '') {
                                $('.test').each(function() {
                                    if (($(this).hasClass(opt2) || $(this).has('.test-node.' + opt2).length > 0)) {
                                        if ($(this).find('.category-assigned').hasClass(opt)) {
                                            $(this).addClass('displayed').show(0);
                                        }
                                    }
                                });
                            } else {
                                $('.test').each(function() {
                                    if ($(this).find('.category-assigned').hasClass(opt)) {
                                        $(this).fadeIn(200).addClass('displayed');
                                    }
                                });
                            }
                            redrawCharts();
                        }
                    }                                
                });
                
                /* action to perform when 'Clear Filters' option is selected [TEST] */
                function resetFilters() {
                    $('.dropdown-content li').removeClass('active');
                    $('.test').addClass('displayed').show(0);
                    redrawCharts();
                    $('.dropdown-content li:first-child').addClass('active').click();
                }
            })
            
            /* formats date in mm-dd-yyyy hh:mm:ss [UTIL] */
            function formatDt(d) {
                return d.getFullYear() + '-' +
                    ('00' + (d.getMonth() + 1)).slice(-2) + '-' + 
                    ('00' + d.getDate()).slice(-2) + ' ' + 
                    ('00' + d.getHours()).slice(-2) + ':' + 
                    ('00' + d.getMinutes()).slice(-2) + ':' + 
                    ('00' + d.getSeconds()).slice(-2);
            }
            /* finds test by its name and extentId  [UTIL] */
            function findTestByNameId(name, id) {
                $('.test').each(function() {
                    if ($(this).find('.test-name').text().trim() == name && $(this).attr('extentid') == id) {
                        $('.analysis > .test-view').click();
                        $('html, body').animate({
                            scrollTop: $('.details-name').offset().top
                        }, 400);
                        $(this).click();
                        return;
                    }
                });
            }
            
            /* refresh and redraw charts [DASHBOARD] */
            function redrawCharts() {
                refreshData();
                testChart.segments[0].value = passedTests;
                testChart.segments[1].value = failedTests;
                testChart.segments[2].value = fatalTests;
                testChart.segments[3].value = errorTests;
                testChart.segments[4].value = warningTests;
                testChart.segments[5].value = skippedTests;
                testChart.segments[6].value = unknownTests;
                stepChart.segments[0].value = passedSteps;
                stepChart.segments[1].value = infoSteps;
                stepChart.segments[2].value = failedSteps;
                stepChart.segments[3].value = fatalSteps;
                stepChart.segments[4].value = errorSteps;
                stepChart.segments[5].value = warningSteps;
                stepChart.segments[6].value = skippedSteps;
                stepChart.segments[7].value = unknownSteps;
                $('#test-analysis, #step-analysis').html('');
                $('ul.doughnut-legend').remove();
                testsChart();
                stepsChart();
                $('ul.doughnut-legend').addClass('right');
            }
            /* update data for dashboard [DASHBOARD] */
            function refreshData() {
                var el = $('#test-count-setting');
                totalTests = $('#report-view .test:not(:has(.test-node)), .test-node').length;
                passedTests = $('#test-details-wrapper .details-container .test-node.pass, .test:visible .test-node.pass, .test:visible.pass:not(.hasChildren)').length;
                failedTests = $('#test-details-wrapper .details-container .test-node.fail, .test:visible .test-node.fail, .test:visible.fail:not(.hasChildren)').length;
                fatalTests = $('#test-details-wrapper .details-container .test-node.fatal, .test:visible .test-node.fatal, .test:visible.fatal:not(.hasChildren)').length;
                warningTests = $('#test-details-wrapper .details-container .test-node.warning, .test:visible .test-node.warning, .test:visible.warning:not(.hasChildren)').length;
                errorTests = $('#test-details-wrapper .details-container .test-node.error, .test:visible .test-node.error, .test:visible.error:not(.hasChildren)').length;
                skippedTests = $('#test-details-wrapper .details-container .test-node.skip, .test:visible .test-node.skip, .test:visible.skip:not(.hasChildren)').length;
                unknownTests = $('#test-details-wrapper .details-container .test-node.unknown, .test:visible .test-node.unknown, .test:visible.unknown:not(.hasChildren)').length;
                if (el.hasClass('parentWithoutNodes')) {
                    totalTests = $('.test:visible').length;
                    passedTests = $('.test:visible.pass').length;
                    failedTests = $('.test:visible.fail').length;
                    fatalTests = $('.test:visible.fatal').length;
                    warningTests = $('.test:visible.warning').length;
                    errorTests = $('.test:visible.error').length;
                    skippedTests = $('.test:visible.skip').length;
                    unknownTests = $('.test:visible.unknown').length;
                }
                else if (el.hasClass('childNodes')) {
                    totalTests = $('.test-node').length;
                    passedTests = $('.test:visible .test-node.pass, .details-container .test-node.pass').length;
                    failedTests = $('.test:visible .test-node.fail, .details-container .test-node.fail').length;
                    fatalTests = $('.test:visible .test-node.fatal, .details-container .test-node.fatal').length;
                    warningTests = $('.test:visible .test-node.warning, .details-container .test-node.warning').length;
                    errorTests = $('.test:visible .test-node.error, .details-container .test-node.error').length;
                    skippedTests = $('.test:visible .test-node.skip, .details-container .test-node.skip').length;
                    unknownTests = $('.test:visible .test-node.unknown, .details-container .test-node.unknown').length;
                }
                totalSteps = $('.test:visible td.status').length;
                passedSteps = $('.test:visible td.status.pass').length;
                failedSteps = $('.test:visible td.status.fail').length;
                fatalSteps = $('.test:visible td.status.fatal').length;
                warningSteps = $('.test:visible td.status.warning').length;
                errorSteps = $('.test:visible td.status.error').length;
                infoSteps = $('.test:visible td.status.info').length;
                skippedSteps = $('.test:visible td.status.skip').length;
                unknownSteps = $('.test:visible td.status.unknown').length;
                $('.t-pass-count').text(passedTests);
                $('.t-fail-count').text(failedTests + fatalTests);
                $('.t-warning-count').text(warningTests);
                $('.t-fatal-count').text(fatalTests);
                $('.t-error-count').text(errorTests);
                $('.t-skipped-count').text(skippedTests);
                $('.t-others-count').text(warningTests + errorTests + skippedTests + unknownTests);
                var percentage = Math.round((passedTests * 100) / (passedTests + failedTests + fatalTests + warningTests + errorTests + unknownTests + skippedTests)) + '%';
                $('.pass-percentage.panel-lead').text(percentage);
                $('#dashboard-view .determinate').attr('style', 'width:' + percentage);
                if ($('#step-status-filter').hasClass('pass')) { passedSteps = 0; }
                if ($('#step-status-filter').hasClass('fail')) { failedSteps = 0; }
                if ($('#step-status-filter').hasClass('fatal')) { fatalSteps = 0; }
                if ($('#step-status-filter').hasClass('warning')) { warningSteps = 0; }
                if ($('#step-status-filter').hasClass('error')) { errorSteps = 0; }
                if ($('#step-status-filter').hasClass('info')) { infoSteps = 0; }
                if ($('#step-status-filter').hasClass('skip')){ skippedSteps = 0; }
                if ($('#step-status-filter').hasClass('unknown')) { unknownSteps = 0; }
                $('.s-pass-count').text(passedSteps);
                $('.s-fail-count').text(failedSteps + fatalSteps);
                $('.s-warning-count').text(warningSteps);
                $('.s-error-count').text(errorSteps);
                $('.s-skipped-count').text(skippedSteps);
                $('.s-others-count').text(warningSteps + errorSteps + skippedSteps + unknownSteps + infoSteps);
                $('#report-dashboard .total-tests > .panel-lead').text(totalTests);
                $('#report-dashboard .total-steps > .panel-lead').text(totalSteps);
                var percentage = Math.round((passedTests * 100) / (passedTests + failedTests + fatalTests + warningTests + errorTests + unknownTests + skippedTests)) + '%';
                $('.pass-percentage.panel-lead').text(percentage);
                $('#report-dashboard .determinate').attr('style', 'width:' + percentage);
            }
            /* dashboard chart options [DASHBOARD] */
            var options = {
                segmentShowStroke : true, 
                segmentStrokeColor : '#fff', 
                segmentStrokeWidth : 1, 
                percentageInnerCutout : 55, 
                animationSteps : 30, 
                animationEasing : 'easeOutBounce', 
                animateRotate : true, 
                animateScale : false,
                legendTemplate : '<ul class=\'<%=name.toLowerCase()%>-legend\'><% for (var i=0; i<segments.length; i++){%><li><span style=\'background-color:<%=segments[i].fillColor%>\'></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>'
            };
            
            var trendOptions = {
                scaleShowGridLines : true,
                scaleGridLineColor : "rgba(0,0,0,.05)",
                scaleGridLineWidth : 1,
                scaleFontSize: 10,
                scaleShowHorizontalLines: true,
                scaleShowVerticalLines: true,
                bezierCurve : true,
                bezierCurveTension : 0.4,
                pointDot : true,
                pointDotRadius : 4,
                pointDotStrokeWidth : 1,
                pointHitDetectionRadius : 20,
                datasetStroke : true,
                datasetStrokeWidth : 2,
                datasetFill : true,
                legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
            };
            
            function getTrendData(passed, failed) {
                var labels = []
                
                $('#slide-out li.report-item').each(function() {
                    labels.push($(this).find('a > .report-date > .report-date').text() + $(this).find('a > .report-date > .report-time').text());
                });
                
                var data = {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Passed',
                            fillColor: 'rgba(181,214,109,0.2)',
                            strokeColor: 'rgba(181,214,109,.5)',
                            pointColor: 'rgba(181,214,109,.5)',
                            pointStrokeColor: '#fff',
                            pointHighlightFill: '#fff',
                            pointHighlightStroke: 'rgba(220,220,220,1)',
                            data: passed
                        },
                        {
                            label: 'Failed',
                            fillColor: 'rgba(255,90,94,.2)',
                            strokeColor: 'rgba(255,90,94,.5)',
                            pointColor: 'rgba(255,90 94,.5)',
                            pointStrokeColor: '#fff',
                            pointHighlightFill: '#fff',
                            pointHighlightStroke: 'rgba(151,187,205,1)',
                            data: failed
                        }
                    ]
                };
                
                return data;
            }
            
            /* tests view chart [TRENDS] */
            function testTrendsChart() {
                var passed = [], failed = [];

                $('#report-view .report-view').each(function() {
                    var t = $(this);
                    passed.push(t.find('.test.pass').length);
                    failed.push(t.find('.test.fail, .test.fatal').length);
                });
                
                var data = getTrendData(passed, failed);
                
                var ctx = $('#report-trends-status-test').get(0).getContext('2d');
                new Chart(ctx).Line(data, trendOptions);
            }
            
            /* step view chart [TRENDS] */
            function stepTrendsChart() {
                var passed = [], failed = [];

                $('#report-view .report-view').each(function() {
                    var t = $(this);
                    passed.push(t.find('td.pass').length);
                    failed.push(t.find('td.fail, td.fatal').length);
                });
                
                var data = getTrendData(passed, failed);
                
                var ctx = $('#report-trends-status-step').get(0).getContext('2d');
                new Chart(ctx).Line(data, trendOptions);
            }
            
            /* dashboard counts - overall data for all the reports */
            function reportsChart() {
                var passedTests = parseInt($('.total-tests-passed > .panel-lead').text());
                var failedTests = parseInt($('.total-tests-failed > .panel-lead').text());
                var errorTests = $('.node-list > li.error, .test:not(.hasChildren).error').length;
                var warningTests = $('.node-list > li.warning, .test:not(.hasChildren).warning').length;
                var skippedTests = $('.node-list > li.skip, .test:not(.hasChildren).skip').length;
                var unknownTests = $('.node-list > li.unknown, .test:not(.hasChildren).unknown').length;

                var data = [
                    { value: passedTests, color: '#00af00', highlight: '#32bf32', label: 'Pass' },
                    { value: failedTests, color:'#F7464A', highlight: '#FF5A5E', label: 'Fail' },
                    { value: errorTests, color:'#ff6347', highlight: '#ff826b', label: 'Error' },
                    { value: warningTests, color: '#FDB45C', highlight: '#FFC870', label: 'Warning' },
                    { value: skippedTests, color: '#1e90ff', highlight: '#4aa6ff', label: 'Skip' },
                    { value: unknownTests, color: '#222', highlight: '#444', label: 'Unknown' }
                ];
                var ctx = $('#report-analysis').get(0).getContext('2d');
                var reportChart = new Chart(ctx).Doughnut(data, options);
                drawLegend(reportChart, 'report-analysis');
            }
            
            /* tests view chart - local to each report [DASHBOARD] */
            function testsChart() {
                var data = [
                        { value: passedTests, color: '#00af00', highlight: '#32bf32', label: 'Pass' },
                        { value: failedTests, color:'#F7464A', highlight: '#FF5A5E', label: 'Fail' },
                        { value: fatalTests, color:'#8b0000', highlight: '#a23232', label: 'Fatal' },
                        { value: errorTests, color:'#ff6347', highlight: '#ff826b', label: 'Error' },
                        { value: warningTests, color: '#FDB45C', highlight: '#FFC870', label: 'Warning' },
                        { value: skippedTests, color: '#1e90ff', highlight: '#4aa6ff', label: 'Skip' },
                        { value: unknownTests, color: '#222', highlight: '#444', label: 'Unknown' }
                    ];
                var ctx = $('#test-analysis').get(0).getContext('2d');
                testChart = new Chart(ctx).Doughnut(data, options);
                drawLegend(testChart, 'test-analysis');
              }
              
            /* steps view chart - local to each report [DASHBOARD] */
            function stepsChart() {
                var data = [
                    { value: passedSteps, color: '#00af00', highlight: '#32bf32', label: 'Pass' },
                    { value: infoSteps, color: '#46BFBD', highlight: '#5AD3D1', label: 'Info' },
                    { value: failedSteps, color:'#F7464A', highlight: '#FF5A5E', label: 'Fail' },
                    { value: fatalSteps, color:'#8b0000', highlight: '#a23232', label: 'Fatal' },
                    { value: errorSteps, color:'#ff6347', highlight: '#ff826b', label: 'Error' },
                    { value: warningSteps, color: '#FDB45C', highlight: '#FFC870', label: 'Warning' },
                    { value: skippedSteps, color: '#1e90ff', highlight: '#4aa6ff', label: 'Skip' },
                    { value: unknownSteps, color: '#222', highlight: '#444', label: 'Unknown' }
                ];
                var ctx = $('#step-analysis').get(0).getContext('2d');
                stepChart = new Chart(ctx).Doughnut(data, options);
                drawLegend(stepChart, 'step-analysis');
            }
              
            /* draw legend for test and step charts [DASHBOARD] */
            function drawLegend(chart, id) {
                var helpers = Chart.helpers;
                var legendHolder = document.getElementById(id);
                legendHolder.innerHTML = chart.generateLegend();
                helpers.each(legendHolder.firstChild.childNodes, function(legendNode, index) {
                    helpers.addEvent(legendNode, 'mouseover', function() {
                        var activeSegment = chart.segments[index];
                        activeSegment.save();
                        activeSegment.fillColor = activeSegment.highlightColor;
                        chart.showTooltip([activeSegment]);
                        activeSegment.restore();
                    });
                });
                Chart.helpers.addEvent(legendHolder.firstChild, 'mouseout', function() {
                    chart.draw();
                });
                $('#' + id).after(legendHolder.firstChild);
              }
              
              testTrendsChart(); stepTrendsChart();
              reportsChart(); testsChart(); stepsChart();
              redrawCharts();
              
              $('ul.doughnut-legend').addClass('right');
              $('#report-dashboard, #trends-view').addClass('hide'); 
        </script>
        <#if (customizer.inlineScript)??> 
            <style type='text/javascript'>
                ${customizer.inlineScript}
            </style>
        </#if>
        <#if (customizer.scriptFile)??>
            <link rel='stylesheet' href='${customizer.scriptFile}' type='text/css'>
        </#if>
    </body>
</html>