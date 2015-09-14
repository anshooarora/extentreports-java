/*
* Copyright (c) 2015, Anshoo Arora (Relevant Codes).  All rights reserved.
* 
* Copyrights licensed under the New BSD License.
* 
* See the accompanying LICENSE file for terms.
*/

package com.relevantcodes.extentreports;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.relevantcodes.extentreports.model.Media;
import com.relevantcodes.extentreports.source.ObjectEmbedHtml;

class MediaViewBuilder {    
    public static Elements getSource(ArrayList<? extends Media> mediaList) {
        Element divParent = Jsoup.parseBodyFragment("<div></div>");
        Element divMedia = null;
        
        for (Media media : mediaList) {
            divMedia = Jsoup.parseBodyFragment(ObjectEmbedHtml.getColumn()).select("div").first();
            
            divMedia.select(".panel-name").first().text(media.getTestName()).attr("extentId", media.getTestId().toString());
            divMedia.select(".panel-object").first().append(media.getSource());
            
            divParent.appendChild(divMedia);
        }
        
        return divParent.select("div.col");
    }
}
