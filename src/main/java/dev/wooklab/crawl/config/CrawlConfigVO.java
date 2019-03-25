package dev.wooklab.crawl.config;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="crawlConfigurations")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CrawlConfigVO {
    private String listSeedUrl;
    private String listSeedRefererUrl;
    private String listStartTag;
    private String listEndTag;
    private String blogSummaryStartPoint;
    private String blogRealUrlStartTag;
    private String blogRealUrlEndTag;
    private String blogTitleStartTag;
    private String blogTitleEndTag;
    private String blogContentsStartTag;
    private String blogContentsEndTag;
    private String blogDateStartTag1;
    private String blogDateEndTag1;
    private String blogDateStartTag2;
    private String blogDateEndTag2;

    @Override
    public String toString() {
        return "CrawlConfigVO{" +
                "listSeedUrl='" + listSeedUrl + '\'' +
                ", listSeedRefererUrl='" + listSeedRefererUrl + '\'' +
                ", listStartTag='" + listStartTag + '\'' +
                ", listEndTag='" + listEndTag + '\'' +
                ", blogSummaryStartPoint='" + blogSummaryStartPoint + '\'' +
                ", blogRealUrlStartTag='" + blogRealUrlStartTag + '\'' +
                ", blogRealUrlEndTag='" + blogRealUrlEndTag + '\'' +
                ", blogTitleStartTag='" + blogTitleStartTag + '\'' +
                ", blogTitleEndTag='" + blogTitleEndTag + '\'' +
                ", blogContentsStartTag='" + blogContentsStartTag + '\'' +
                ", blogContentsEndTag='" + blogContentsEndTag + '\'' +
                ", blogDateStartTag1='" + blogDateStartTag1 + '\'' +
                ", blogDateEndTag1='" + blogDateEndTag1 + '\'' +
                ", blogDateStartTag2='" + blogDateStartTag2 + '\'' +
                ", blogDateEndTag2='" + blogDateEndTag2 + '\'' +
                '}';
    }
}