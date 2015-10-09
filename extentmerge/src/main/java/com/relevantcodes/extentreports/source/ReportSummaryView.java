package com.relevantcodes.extentreports.source;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ReportSummaryView {
    public static String getSource() {
        return "<table><tr>" +
                    "<td class='goto-report'><i class='mdi-action-launch'></i></td>" +
                    "<td class='report-date'><span class='label date'></span></td>" +
                    "<td class='report-source'><span class='label text-white'></span></td>" +
                    "<td class='run-duration'></td>" +
                    "<td class='tests-count'></td>" +
                    "<td class='report-progress'></td>" +
                "</tr></table>";
    }
    
    public static String getProgressBar(Document reportView) {
        float allTests = reportView.select(".test:not(.hasChildren), .test-node").size();

        Elements passed = reportView.select(""
                + ".test:not(.hasChildren).pass, "
                + ".test-node.pass"
        );
        int passedCount = passed != null ? passed.size() : 0;
        double passedPercentage = passedCount != 0 ? Math.floor((passedCount / allTests) * 100) : passedCount;
        
        Elements failed = reportView.select(""
                + ".test:not(.hasChildren).fail, "
                + ".test-node.fail, "
                + ".test:not(.hasChildren).fatal, "
                + ".test-node.fatal"
        );
        int failedCount = failed != null ? failed.size() : 0;
        double failedPercentage = failed != null ? Math.floor((failedCount / allTests) * 100) : 0; 
        
        Elements skipped = reportView.select(""
                + ".test:not(.hasChildren).skip, "
                + ".test-node.skip"
        );
        int skippedCount = skipped != null ? skipped.size() : 0;
        double skippedPercentage = failed != null ? Math.floor((skippedCount / allTests) * 100) : 0; 
        
        Elements others = reportView.select(""
                + ".test:not(.hasChildren).warning, "
                + ".test-node.warning, "
                + ".test:not(.hasChildren).error, "
                + ".test-node.error, "
                + ".test:not(.hasChildren).unknown, "
                + ".test-node.unknown"
        );
        int othersCount = others != null ? others.size() : 0;
        double othersPercentage = others != null ? Math.floor((othersCount / allTests) * 100) : 0; 
        
        return "<div class='progress2'>" +
                    "<div class='progress-bar2 progress-bar-success progress-bar-striped' style='width: " + passedPercentage + "%'>" +
                        "<span class='sr-only'>" + passedCount +"</span>" +
                    "</div>" +
                    "<div class='progress-bar2 progress-bar-skip progress-bar-striped' style='width: " + skippedPercentage + "%'>" +
                        "<span class='sr-only'>" + skippedCount +"</span>" +
                    "</div>" +
                    "<div class='progress-bar2 progress-bar-warning progress-bar-striped' style='width: " + othersPercentage + "%'>" +
                        "<span class='sr-only'>" + othersCount +"</span>" +
                    "</div>" +
                    "<div class='progress-bar2 progress-bar-danger progress-bar-striped' style='width: " + failedPercentage + "%'>" +
                        "<span class='sr-only'>" + failedCount +"</span>" +
                    "</div>" +
                "</div>";
    }
}
