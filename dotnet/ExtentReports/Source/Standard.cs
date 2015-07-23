// ***********************************************************************
// Copyright (c) 2015, Anshoo Arora (Relevant Codes). All rights reserved.
//
// Copyrights licensed under the New BSD License.
//
// See the accompanying LICENSE file for terms.
// ***********************************************************************

namespace RelevantCodes.ExtentReports.Source
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    internal class Standard
    {
        public static string GetSource()
        {
            return @"<!DOCTYPE html>
<html>
    <head>
        <!--
            ExtentReports Library 2.02 | http://relevantcodes.com/extentreports-for-selenium/ | https://github.com/anshooarora/
            Copyright (c) 2015, Anshoo Arora (Relevant Codes) | Copyrights licensed under the New BSD License | http://opensource.org/licenses/BSD-3-Clause
            Documentation: http://extentreports.relevantcodes.com
        --> 
        <meta name='description' content='ExtentReports (by Anshoo Arora) is a reporting library for automation testing, written in Java. It creates detailed and beautiful HTML reports for modern browsers. ExtentReports shows test and step summary along with dashboards, system and environment details for quick analysis of your tests.' />
        <meta name='robots' content='noodp, noydir' />
        <meta name='viewport' content='width=device-width, initial-scale=1' />
        <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:400|Roboto:400,500' rel='stylesheet' type='text/css'>
        <link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css' type='text/css'>
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.96.1/css/materialize.min.css' type='text/css'>
        <title>ExtentReports 2.0</title>
        <style type='text/css'>
            body {
                background-color: #f1f4f8;
                font-family: Roboto, Nunito, 'Source Sans Pro', Arial;
                font-size: 13px;
                font-weight: 400;
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
                height: auto;
                margin-top: 15px;
                width: 50%;
            }
            table {
                border: 1px solid #ddd;
            }
            th, td {
                border-bottom: 1px solid #ddd !important;
                color: #222 !important;
            }
            th {
            	font-family: Roboto;
                font-weight: 500 !important;
                padding: 10px;
            }
            td {
                font-size: 15px;
                font-weight: 400;
                padding: 7px 10px;
                word-break: break-all;
            }
            label {
                font-size: 13px;
            }
                    
            /* -- [ global structure ] -- */
            .container {
                padding-top: 20px;
            }
            .tabs {
                margin-bottom: 20px;
            }
            .card-panel {
                box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.16);
            }
            .panel-name { }
            .panel-setting {
                float: right;
            }
            .panel-setting a {
                color: #bbb;
            }
            .panel-lead {
                display: block;
                font-size: 20px;
                margin-top: 45px;
                text-align: center;
            }
            .tabs .tab a {
                color: #26a69a;
                font-size: 12px;
            }
            .tabs .tab a:hover {
                color: #43c3b7;
            }    
            .chart-o {
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
            .percent {
                display: inline-block;
                line-height: 100px;
                z-index: 2;
            }
            .percent.sign:after {
                content: '%';
            }
            .chart-o > div {
                display: inline-block;
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
            .select-wrapper input.select-dropdown, .input-field label {
                font-size: 0.9rem;
            }
            .material-tooltip {
                font-size: 13px;
            }
                        
            /* -- [ side-nav ] -- */
            nav {
                box-shadow: none;
            }
            .show-on-large {
            	display: inline-block !important;
            }
            nav, nav .nav-wrapper i, nav a.button-collapse, nav a.button-collapse i {
                height: 40px;
                line-height: 40px;
            }
            nav, .side-nav {
                background-color: #1a2a42;
            }
            .side-nav {
                width: 60px;
            }
            .side-nav li {
                padding: 0 7px;
            }
            .side-nav .active, .side-nav li:hover {
                background-color: #293951 !important;
            }
            .side-nav a {
                color: #e1ecfe !important;
                font-size: 13px !important;
            }
            #sidenav-overlay {
                background: transparent;
            }
            .report-name {
                padding-left: 40px;
            }
            nav .right {
                color: #c8c8c8;
                padding-right: 50px;
            }
            
            /* -- [ views ] -- */
            .views > div {
                display: none;
            }
            .views > div:first-child {
                display: block;
            }
            .images-view .row, .runinfo-view .row, .system-view .row, .test-list .row {
                margin-bottom: 0;
            }
            
                /* -- [ dashboard ] -- */
                .chart > div {
                    text-align: center;
                }
                .chart-o + span {
                    display: block;
                }
                .dashboard-view .card-panel {
                    height: 285px;
                    max-height: 285px;
                    min-height: 285px;
                }
                .dashboard-view .panel-lead {
                    margin-top: 80px;
                }
                .dashboard-view .progress {
                    margin-top: 100px;
                }
            
                /* -- [ runinfo, system ] -- */
                .runinfo-view .card-panel, .system-view .card-panel {
                    min-height: 194px;
                }
                
                /* -- [ media ] -- */
                .images-view .card-panel, {
                    height: 160px;
                    max-height: 160px;
                    min-height: 160px;
                }
                .videos-view .card-panel {
                    min-height: 180px;
                }
                .images-view img {
                    margin: 15px auto 0;
                    width: 90%;
                }
                .panel-object #video {
                    display: block;
                    margin: 15px auto 0;
                    text-align: center;
                }
            
            /* -- [ filters ] -- */
            .filters {
                margin-left: 5px;
            }
            .dropdown-content li > a, .dropdown-content li > span {
                font-size: 0.85rem;
                line-height: 1.2rem;
                padding: 0.5rem 1rem;
            }

            /* -- [ main ] -- */
            .main {
                padding-bottom: 200px;
            }

            /* -- [ texts-quick-view] -- */
            .tests-quick-view th:first-child, .test-quick-view td:first-child {
                min-width: 40%;
            }
            .tests-quick-view td {
                font-size: 14px;
                padding: 10px;
            }
            .quick-test-summary {
                margin-bottom: 20px;
            }
            .tests-quick-view tbody tr:hover {
                background-color: #f6f7fa;
            }
            .tests-quick-view .label {
                font-size: 11px;
                font-weight: 400;
            }
            .quick-view-test {
                color: #039be5;
                cursor: pointer;
            }
                        
            /* -- [ test-list ] -- */
            .test {
                color: #222 !important;
                cursor: pointer;
                margin-bottom: 5px;
            }
            .test-warning {
                color: red;
                cursor: pointer;
                font-size: 12px;
                margin-left: 5px;
                vertical-align: top;
            }
            .test div, .test span, .test td  {
                font-family: 'Source Sans Pro';
            }
            .test td {
                vertical-align: top;
            }
            .is-expanded {
                border: 1px solid #999;
            }
            .test-head { }
            .test-head .label {
                font-size: 13px;
                text-transform: uppercase;
            }
            .test-info {
            	margin-left: 100px;
            }
            .test-time-taken {
                border-left: 2px solid #bbb;
                text-transform: lowercase !important;
            }
            .test-head .right {
                margin-top: 6px;
            }
            .test-head .right span {
                background-color: #f6f7fa;
                padding: 4px 10px;
            }
            .test-name {
                font-size: 20px;
                font-weight: 400;
            }
            .test-desc {
                color: #222;
                font-size: 16px;
                font-weight: 400;
                padding-top: 7px;
            }
            .test-attributes .category {
                background-color: #e1f2fa;
                border-radius: 2px;
                color: #5992aa;
                margin-right: 3px;
                padding: 2px 4px;
            }
            .test-body {
                cursor: auto !important;
                display: none;
                padding-top: 25px;
            }
            .test-started-time, .test-ended-time {
                border-left: 2px solid;
            }
            .test-started-time {
                border-left-color: #5fc29d;
            }
            .test-ended-time {
                border-left-color: #eea236;
            }
            .test th:first-child, .test td:first-child {
                max-width: 90px;
                width: 90px;
            }
            .test th:nth-child(2), .test td:nth-child(2) {
                font-size: 13px;
                text-align: left !important;
                max-width: 65px;
                width: 65px;
            }
            td.step-name { 
                white-space: nowrap; 
            }
            td.status {
                padding-left: 20px;
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
            .label {
                border-radius: 2px;
                font-size: 14px;
                padding: 2px 5px;
                text-transform: none;
            }
            .label.success, .label.failure, .label.info, .label.warn, .label.pass, .label.fail, .label.warning, .label.fatal, .label.skip, .label.error, .label.unknown {
                color: #fff;
            }
            .label.success, .label.pass {
                background-color: #7fbb00 !important;
            }
            .label.fatal {
                background-color: #d50000 !important;
            }
            .label.failure, .label.fail {
                background-color: #f44336 !important;
            }
            .label.error {
                background-color: #ec407a !important;
            }
            .label.info {
                background-color: #55bad8  !important;
            }
            .label.warn, .label.warning {
                background-color: #fdba5b  !important;
            }
            .label.skip, .label.skipped {
                background-color: #2196f3 !important;
            }
            .label.unknown {
                background-color: #222 !important;
            }
            
            /* -- [ media queries ] -- */
            @media all and (max-width: 1550px) {
                .container {
                    width: 85%;
                }
            }
            @media all and (max-width: 1400px) {
                .container {
                    width: 90%;
                }
            }
            @media all and (max-width: 992px) {
                .test th:first-child, .test td:first-child, .test th:nth-child(2), .test td:nth-child(2) {
                    max-width: inherit;
                    width: inherit;
                }
                th, td {
                	padding: 10px;
                }
            }
            @media all and (max-width: 800px) {
                .test-head .left, .test-head .right {
                    float: none !important;
                    margin-left: 0;
                }
                .test-info {
                	margin-bottom: 10px;
                }
                .test-desc {
                    padding-top: 10px;
                }
            }
            @media all and (max-width: 575px) {
                .test-info span {
                    display: block;
                }
            }
        </style>
        <!--%%CUSTOMCSS%%-->
    </head>
    <body>
        <nav>
            <ul id='slide-out' class='side-nav'>
                <li class='dashboard-view active tooltipped' data-delay='0' data-position='right' data-tooltip='Dashboard view'><a href='#!'><i class='fa fa-dashboard'></i></a></li>
                <li class='runinfo-view tooltipped' data-delay='0' data-position='right' data-tooltip='Run Info view'><a href='#!'><i class='fa fa-circle-o-notch'></i></a></li>
                <li class='system-view tooltipped' data-delay='0' data-position='right' data-tooltip='System Info view'><a href='#!'><i class='fa fa-desktop'></i></a></li>
                <li class='images-view tooltipped' data-delay='0' data-position='right' data-tooltip='Images view'><a href='#!'><i class='fa fa-image'></i></a></li>
                <li class='videos-view tooltipped' data-delay='0' data-position='right' data-tooltip='Screencast view'><a href='#!'><i class='fa fa-video-camera'></i></a></li>
            </ul>
            <a href='#' data-activates='slide-out' class='button-collapse show-on-large'><i class='mdi-navigation-menu'></i></a>
            <span class='report-name'><!--%%LOGO%%-->Report Name<!--%%LOGO%%--> <!--%%HEADLINE%%-->[Report Headline]<!--%%HEADLINE%%--></span>
            <span class='right'>ExtentReports</span>
        </nav>
        <div class='main'>
            <div class='container'>
                <div class='views'>
                    <div class='dashboard-view'>
                        <div class='row'>
                            <div class='col s12 m6 l4'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Tests View</span>
                                    <div class='chart-o text-centered' id='ts-status-dashboard'></div>
                                    <span class='weight-light'><span class='t-pass-count weight-normal'></span> test(s) passed</span>
                                    <span class='weight-light'><span class='t-fail-count weight-normal'></span> test(s) failed, <span class='t-others-count weight-normal'></span> others</span>
                                </div>
                            </div>
                            <div class='col s12 m6 l4'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Steps View</span>
                                    <span class='panel-setting modal-trigger step-dashboard-status-filter'><a href='#step-dashboard-status-filter'><i class='fa fa-gear'></i></a></span>
                                    <div class='chart-o text-centered' id='step-status-dashboard'></div>
                                    <span class='weight-light'><span class='s-pass-count weight-normal'></span> step(s) passed</span>
                                    <span class='weight-light'><span class='s-fail-count weight-normal'></span> step(s) failed, <span class='s-others-count weight-normal'></span> others</span>
                                </div>
                            </div>
                            <div class='col s12 m12 l4'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Pass Percentage</span>
                                    <span class='pass-percentage panel-lead'></span>
                                    <div class='progress'>
                                         <div class='determinate'></div>
                                     </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class='runinfo-view'>
                        <div class='row'>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Started At</span>
                                    <span class='panel-lead suite-started-time'><!--%%SUITESTARTTIME%%--></span>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Total Tests</span>
                                    <div class='chart total-tests' data-percent=''>
                                        <span class='percent'></span>
                                    </div>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Tests Passed [<span class='t-pass-count'></span>]</span>
                                    <div class='chart tests-passed' data-percent=''>
                                        <span class='percent sign'></span>
                                    </div>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Tests Failed + Fatal [<span class='t-fail-count'></span>]</span>
                                    <div class='chart tests-failed' data-percent=''>
                                        <span class='percent sign'></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class='row'>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Ended At</span>
                                    <span class='panel-lead suite-ended-time'><!--%%SUITEENDTIME%%--><!--%%SUITEENDTIME%%--></span>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Total Steps</span>
                                    <div class='chart total-steps' data-percent=''>
                                        <span class='percent'></span>
                                    </div>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Steps Passed [<span class='s-pass-count'></span>]</span>
                                    <div class='chart steps-passed' data-percent=''>
                                        <span class='percent sign'></span>
                                    </div>
                                </div>
                            </div>
                            <div class='col s12 m6 l3'>
                                <div class='card-panel'>
                                    <span class='panel-name'>Steps Failed + Fatal [<span class='s-fail-count'></span>]</span>
                                    <div class='chart steps-failed' data-percent=''>
                                        <span class='percent sign'></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class='system-view'>
                        <div class='row'>
                            <!--%%SYSTEMINFOVIEW%%-->
                        </div>
                    </div>
                    <div class='videos-view'>
                        <div class='row'>
                            <!--%%VIDEOSVIEW%%-->
                        </div>
                    </div>
                    <div class='images-view'>
                        <div class='row'>
                            <!--%%IMAGESVIEW%%-->
                        </div>
                    </div>
                </div>
                <div class='row'>
                    <div class='col s12'>
                        <ul class='tabs'>
                            <li class='tab col s3'><a class='active' href='#tests-quick-view'>Summary</a></li>
                            <li class='tab col s3'><a href='#tests-details-view'>Details</a></li>
                        </ul>
                    </div>
                    <div class='tests-quick-view' id='tests-quick-view'>
                        <div class='col s12 m12 l12 selected-test'>
                            <div class='card-panel'>
                                <div class='panel-view panel-name quick-test-summary'>
                                    Quick Test Summary
                                </div>
                                <table class='bordered responsive-table'>
                                    <thead>
                                        <tr>
                                            <th>Test Name</th>
                                            <th>Passed</th>
                                            <th>Failed</th>
                                            <th>Fatal</th>
                                            <th>Error</th>
                                            <th>Warning</th>
                                            <th>Info</th>
                                            <th>Skipped</th>
                                            <th>Unknown</th>
                                            <th>RunStatus</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!--%%QUICKTESTSUMMARY%%-->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class='tests-details-view' id='tests-details-view'>
                        <div class='filters'>
                            <div class='col l12'>
                                <div class='input-field col l3 m6 s12 tests-toggle'>
                                    <select>
                                        <option value='0' selected>Choose your option</option>
                                        <option value='1'>Pass</option>
                                        <option value='2'>Fatal</option>
                                        <option value='3'>Fail</option>
                                        <option value='4'>Error</option>
                                        <option value='5'>Warning</option>
                                        <option value='6'>Skipped</option>
                                        <option value='7'>Unknown</option>
                                        <option value='8'>Clear Filters</option>
                                    </select>
                                    <label>Filter Tests</label>
                                </div>
                                <!-- #12: Ability to add categories and category-filters to tests -->
                                <div class='input-field col l3 m6 s12 category-toggle'>
                                    <select>
                                        <option value='0' selected>Choose your option</option>
                                        <!--%%CATEGORYLISTOPTIONS%%-->
                                        <option value='8'>Clear Filters</option>
                                    </select>
                                    <label>Filter Category</label>
                                </div>
                                <!--%%CATEGORYADDED%%-->
                                <div class='input-field col s12 m6 l3 find-tests'>
                                    <input id='test-name-filter' type='text' class='validate'>
                                    <label for='test-name-filter'>Find Test(s)</label>
                                </div>
                            </div>
                        </div>
                        <div class='test-list'>
                            <!--%%TEST%%-->
                        </div>
                    </div>
                </div>
            </div>
            <!-- #10: checkboxes for charts -->
            <div id='step-dashboard-status-filter' class='modal bottom-sheet'>
                <div class='modal-content'>
                    <h5>Select status</h5>
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-pass'  />
                    <label for='step-dashboard-filter-pass'>Pass</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-fail'  />
                    <label for='step-dashboard-filter-fail'>Fail</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-fatal'  />
                    <label for='step-dashboard-filter-fatal'>Fatal</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-error'  />
                    <label for='step-dashboard-filter-error'>Error</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-warning'  />
                    <label for='step-dashboard-filter-warning'>Warning</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-skip'  />
                    <label for='step-dashboard-filter-skip'>Skipped</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-info'  />
                    <label for='step-dashboard-filter-info'>Info</label>
                    <br />
                    <input checked='checked' class='filled-in' type='checkbox' id='step-dashboard-filter-unknown'  />
                    <label for='step-dashboard-filter-unknown'>Unknown</label>
                </div>
                <div class='modal-footer'>
                    <a href='#!' class='modal-action modal-close waves-effect waves-green btn'>Save</a>
                </div>
            </div>
        </div>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script> 
        <script type='text/javascript' src='https://www.google.com/jsapi'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.96.1/js/materialize.min.js'></script>
        <script type='text/javascript'>
            /**!
             * easyPieChart
             * Lightweight plugin to render simple, animated and retina optimized pie charts
             *
             * @license 
             * @author Robert Fleischmann <rendro87@gmail.com> (http://robert-fleischmann.de)
             * @version 2.1.4
             **/
            !function(a,b){'object'==typeof exports?module.exports=b(require('jquery')):'function'==typeof define&&define.amd?define(['jquery'],b):b(a.jQuery)}(this,function(a){var b=function(a,b){var c,d=document.createElement('canvas');a.appendChild(d),'undefined'!=typeof G_vmlCanvasManager&&G_vmlCanvasManager.initElement(d);var e=d.getContext('2d');d.width=d.height=b.size;var f=1;window.devicePixelRatio>1&&(f=window.devicePixelRatio,d.style.width=d.style.height=[b.size,'px'].join(''),d.width=d.height=b.size*f,e.scale(f,f)),e.translate(b.size/2,b.size/2),e.rotate((-0.5+b.rotate/180)*Math.PI);var g=(b.size-b.lineWidth)/2;b.scaleColor&&b.scaleLength&&(g-=b.scaleLength+2),Date.now=Date.now||function(){return+new Date};var h=function(a,b,c){c=Math.min(Math.max(-1,c||0),1);var d=0>=c?!0:!1;e.beginPath(),e.arc(0,0,g,0,2*Math.PI*c,d),e.strokeStyle=a,e.lineWidth=b,e.stroke()},i=function(){var a,c;e.lineWidth=1,e.fillStyle=b.scaleColor,e.save();for(var d=24;d>0;--d)d%6===0?(c=b.scaleLength,a=0):(c=.6*b.scaleLength,a=b.scaleLength-c),e.fillRect(-b.size/2+a,0,c,1),e.rotate(Math.PI/12);e.restore()},j=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(a){window.setTimeout(a,1e3/60)}}(),k=function(){b.scaleColor&&i(),b.trackColor&&h(b.trackColor,b.lineWidth,1)};this.getCanvas=function(){return d},this.getCtx=function(){return e},this.clear=function(){e.clearRect(b.size/-2,b.size/-2,b.size,b.size)},this.draw=function(a){b.scaleColor||b.trackColor?e.getImageData&&e.putImageData?c?e.putImageData(c,0,0):(k(),c=e.getImageData(0,0,b.size*f,b.size*f)):(this.clear(),k()):this.clear(),e.lineCap=b.lineCap;var d;d='function'==typeof b.barColor?b.barColor(a):b.barColor,h(d,b.lineWidth,a/100)}.bind(this),this.animate=function(a,c){var d=Date.now();b.onStart(a,c);var e=function(){var f=Math.min(Date.now()-d,b.animate.duration),g=b.easing(this,f,a,c-a,b.animate.duration);this.draw(g),b.onStep(a,c,g),f>=b.animate.duration?b.onStop(a,c):j(e)}.bind(this);j(e)}.bind(this)},c=function(a,c){var d={barColor:'#ef1e25',trackColor:'#f9f9f9',scaleColor:'#dfe0e0',scaleLength:5,lineCap:'round',lineWidth:3,size:110,rotate:0,animate:{duration:1e3,enabled:!0},easing:function(a,b,c,d,e){return b/=e/2,1>b?d/2*b*b+c:-d/2*(--b*(b-2)-1)+c},onStart:function(){},onStep:function(){},onStop:function(){}};if('undefined'!=typeof b)d.renderer=b;else{if('undefined'==typeof SVGRenderer)throw new Error('Please load either the SVG- or the CanvasRenderer');d.renderer=SVGRenderer}var e={},f=0,g=function(){this.el=a,this.options=e;for(var b in d)d.hasOwnProperty(b)&&(e[b]=c&&'undefined'!=typeof c[b]?c[b]:d[b],'function'==typeof e[b]&&(e[b]=e[b].bind(this)));e.easing='string'==typeof e.easing&&'undefined'!=typeof jQuery&&jQuery.isFunction(jQuery.easing[e.easing])?jQuery.easing[e.easing]:d.easing,'number'==typeof e.animate&&(e.animate={duration:e.animate,enabled:!0}),'boolean'!=typeof e.animate||e.animate||(e.animate={duration:1e3,enabled:e.animate}),this.renderer=new e.renderer(a,e),this.renderer.draw(f),a.dataset&&a.dataset.percent?this.update(parseFloat(a.dataset.percent)):a.getAttribute&&a.getAttribute('data-percent')&&this.update(parseFloat(a.getAttribute('data-percent')))}.bind(this);this.update=function(a){return a=parseFloat(a),e.animate.enabled?this.renderer.animate(f,a):this.renderer.draw(a),f=a,this}.bind(this),this.disableAnimation=function(){return e.animate.enabled=!1,this},this.enableAnimation=function(){return e.animate.enabled=!0,this},g()};a.fn.easyPieChart=function(b){return this.each(function(){var d;a.data(this,'easyPieChart')||(d=a.extend({},b,a(this).data()),a.data(this,'easyPieChart',new c(this,d)))})}});
        </script>
        <script type='text/javascript'>
            google.load('visualization', '1', {packages:['corechart']});
            $(document).ready(function() {
                $('.button-collapse').sideNav({ closeOnClick: false, menuWidth: 60 });
                $('select').material_select();
                $('ul.tabs').tabs();
                $('.materialboxed').materialbox();
                $('.step-dashboard-status-filter').click(function() {
                    $('#step-dashboard-status-filter').openModal();
                });
                if ($('.category-toggle option').length == 2) {
                  $('.category-toggle').hide();
                }
                $('#step-dashboard-status-filter input').prop('checked', 'checked');
                $('.indicator').addClass('teal lighten-4');
                var totalTests = $('.test').length;
                var passedTests = $('.test.pass').length;
                var failedTests = $('.test.fail').length;
                var fatalTests = $('.test.fatal').length;
                var warningTests = $('.test.warning').length;
                var errorTests = $('.test.error').length;
                var skippedTests = $('.test.skip').length;
                var unknownTests = $('.test.unknown').length;
                var totalSteps = $('td.status').length;
                var passedSteps = $('td.status.pass').length;
                var failedSteps = $('td.status.fail').length;
                var fatalSteps = $('td.status.fatal').length;
                var warningSteps = $('td.status.warning').length;
                var errorSteps = $('td.status.error').length;
                var infoSteps = $('td.status.info').length;
                var skippedSteps = $('td.status.skip').length;
                var unknownSteps = $('td.status.unknown').length;
                var passedPercentage = Math.round((passedTests * 100) / (passedTests + failedTests + fatalTests + warningTests + errorTests)) + '%';
                $('.t-pass-count').text(passedTests);
                $('.t-fail-count').text(failedTests + fatalTests);
                $('.t-warning-count').text(warningTests);
                $('.t-fatal-count').text(fatalTests);
                $('.t-error-count').text(errorTests);
                $('.t-skipped-count').text(skippedTests);
                $('.s-pass-count').text(passedSteps);
                $('.s-fail-count').text(failedSteps + fatalSteps);
                $('.s-warning-count').text(warningSteps);
                $('.s-fatal-count').text(fatalSteps);
                $('.s-error-count').text(errorSteps);
                $('.s-skipped-count').text(skippedSteps);
                $('.t-others-count').text(warningTests + errorTests + skippedTests + unknownTests);
                $('.s-others-count').text(warningSteps + errorSteps + skippedSteps + unknownSteps + infoSteps);
                $('.dashboard-view .panel-lead').text(passedPercentage);
                $('.dashboard-view .determinate').attr('style', 'width:' + passedPercentage);
                $('nav li').click(function() {
                    if (!$(this).hasClass('active')) {
                        var cls = $(this).prop('class').split(' ')[0];
                        $('nav li').removeClass('active');
                        $(this).addClass('active');
                        $('.views > div').not('.' + cls).slideUp().parent().children('.' + cls).slideDown();
                        if (cls == 'runinfo-view') 
                            showRunInfo();
                    }
                    //$('.button-collapse').sideNav('hide');
                });
                // #11: Link Test Case in Summary section to Details
                $('.quick-view-test').click(function() {
                    var index = $(this).closest('tr').index();
                    var el = $('.test-list .test:eq(' + index + ')');
                    $('.tabs .tab:nth-child(2) a').click();
                    $('html, body').animate({
                        scrollTop: el.offset().top
                    }, 1000);
                    if (!el.hasClass('is-expanded'))
                        el.toggleClass('is-expanded').find('.test-body').slideToggle(300);
                });
                $('.test-list .test').click(function(evt) {
                    if (evt.target.className.toLowerCase() == 'category') {
                        $('.category-toggle li').each(function() {
                            if ($(this).text() == $(evt.target).text()) {
                                $(this).click();
                                return;
                            }
                        });
                    }
                    else if (evt.target.nodeName == 'DIV' || evt.target.nodeName == 'SPAN') {
                        $(this).toggleClass('is-expanded').find('.test-body').slideToggle(300);
                    }
                });
                $('#step-dashboard-status-filter input').click(function() {
                    if ($(this).prop('checked') == false) {
                        $('#step-dashboard-status-filter').addClass($(this).prop('id').replace('step-dashboard-filter-', ''));
                    }
                    else {
                        $('#step-dashboard-status-filter').removeClass($(this).prop('id').replace('step-dashboard-filter-', ''));
                    }
                });
                // #10: checkboxes for charts
                $('.modal-footer').click(function() {
                    passedSteps = $('td.status.pass').length;
                    failedSteps = $('td.status.fail').length;
                    fatalSteps = $('td.status.fatal').length;
                    warningSteps = $('td.status.warning').length;
                    errorSteps = $('td.status.error').length;
                    infoSteps = $('td.status.info').length;
                    skippedSteps = $('td.status.skip').length;
                    unknownSteps = $('td.status.unknown').length;
                    if ($('#step-dashboard-status-filter').hasClass('pass')) { passedSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('fail')) { failedSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('fatal')) { fatalSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('warning')) { warningSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('error')) { errorSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('info')) { infoSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('skip')){ skippedSteps = 0; }
                    if ($('#step-dashboard-status-filter').hasClass('unknown')) { unknownSteps = 0; }
                    testsChart();
                });
                function showRunInfo() {
                    $('.total-tests > .percent').text(totalTests).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#9c27b0', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.total-tests').data('easyPieChart').update('100');
                    $('.tests-passed > .percent').text(Math.round((passedTests / totalTests) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#53b657', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.tests-passed').data('easyPieChart').update((passedTests / totalTests) * 100);
                    $('.tests-failed > .percent').text(Math.round(((failedTests + fatalTests) / totalTests) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#f8576c', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.tests-failed').data('easyPieChart').update(((failedTests + fatalTests) / totalTests) * 100);
                    $('.total-steps > .percent').text(totalSteps).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#1366d7', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.total-steps').data('easyPieChart').update('100');
                    $('.steps-passed > .percent').text(Math.round((passedSteps / totalSteps) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#53b657', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.steps-passed').data('easyPieChart').update((passedSteps / totalSteps) * 100);
                    $('.steps-failed > .percent').text(Math.round(((failedSteps + fatalSteps) / totalSteps) * 100)).parent().easyPieChart({ lineWidth: 12,  trackColor: '#f1f2f3', barColor: '#f8576c', lineCap: 'butt', scaleColor: '#fff', size: 100 });
                    $('.steps-failed').data('easyPieChart').update(((failedSteps + fatalSteps) / totalSteps) * 100);
                }
                $('.categories').each(function() {
                    if ($(this).children().length > 0) {
                        $(this).css('margin-top', '10px');                    
                    }
                });
                $(document).keypress(function(e) {
                    if(e.which == 13) {
                        if ($('#test-name-filter').is(':focus')) {
                            var txt = $('#test-name-filter').val();
                            $('.test').hide(0);
                            $('.test-name, .test-desc').each(function() {
                                if ($(this).text().toLowerCase().indexOf(txt) >= 0) {
                                    $(this).closest('.test').show();
                                }
                            });
                        }
                    }
                });
                $('.tests-toggle li').click(function() {
                    var opt = $(this).text().toLowerCase();
                    if (opt != 'choose your option') {
                        if (opt == 'clear filters') {
                            resetFilters();
                        } else {
                            $('.test, .test-section').hide(0);
                            $('.test.' + opt).fadeIn(200).closest('.test-section').fadeIn(400);
                        }
                    }                                
                });
                // #12: Ability to add categories and category-filters to tests
                $('.category-toggle li').click(function() {
                    var opt = $(this).text();
                    if (opt != 'Choose your option') {
                        if (opt == 'Clear Filters') {
                            resetFilters();
                        } else {
                            $('.test, .test-section').hide(0);
                            $('.test .category').each(function() {
                                if ($(this).text() == opt) {
                                    $(this).closest('.test').fadeIn(200).closest('.test-section').fadeIn(400);
                                }
                            });
                        }
                    }                                
                });
                function resetFilters() {
                    $('.test, .test-section').show(0);
                }
            });
            google.setOnLoadCallback(testSetChart);
            google.setOnLoadCallback(testsChart);
            function testSetChart() {
                var data = google.visualization.arrayToDataTable([
                  ['Test Status', 'Count'],
                  ['Pass', $('.test.pass').length],
                  ['Error', $('.test.error').length],
                  ['Warning', $('.test.warning').length],
                  ['Fail', $('.test.fail').length],
                  ['Fatal', $('.test.fatal').length],
                  ['Skipped', $('.test.skip').length],
                  ['Unknown', $('.test.unknown').length],
                ]);
                var options = {
                  backgroundColor: { fill:'transparent' },
                  chartArea: {'width': '92%', 'height': '100%'},
                  colors: ['#00af00', 'tomato', 'orange', 'red', 'darkred', '#999', '#000'],
                  fontName: 'Roboto',
                  fontSize: '11',
                  titleTextStyle: { color: '#1366d7', fontSize: '14' },
                  pieHole: 0.55,
                  height: 180,
                  pieSliceText: 'value', 
                  width: 220
                };
                var chart = new google.visualization.PieChart(document.getElementById('ts-status-dashboard'));
                chart.draw(data, options);
              }  
            function testsChart() {
                var data = google.visualization.arrayToDataTable([
                  ['Test Status', 'Count'],
                  ['Pass', $('td.status.pass').length],
                  ['Fail', $('td.status.fail').length],
                  ['Fatal', $('td.status.fatal').length],
                  ['Error', $('td.status.error').length],
                  ['Warning', $('td.status.warning').length],
                  ['Info', $('td.status.info').length],
                  ['Skipped', $('td.status.skip').length],
                  ['Unknown', $('td.status.unknown').length]
                ]);
                var options = {
                  backgroundColor: { fill:'transparent' },
                  chartArea: {'width': '92%', 'height': '100%'},
                  colors: ['#00af00', 'red', 'darkred', 'tomato', 'orange', 'dodgerblue', '#999', '#000'],
                  fontName: 'Roboto',
                  fontSize: '11',
                  titleTextStyle: { color: '#1366d7', fontSize: '14' },
                  pieHole: 0.55,
                  height: 180,
                  pieSliceText: 'value', 
                  width: 220
                };
                var chart = new google.visualization.PieChart(document.getElementById('step-status-dashboard'));
                chart.draw(data, options);
              }
        </script>
        <!--%%CUSTOMSCRIPT%%-->
    </body>
</html>";
        }
    }
}
