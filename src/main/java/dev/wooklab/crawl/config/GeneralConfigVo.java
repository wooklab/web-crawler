package dev.wooklab.crawl.config;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "generalConfigurations")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class GeneralConfigVo {
    private String logbackPath;
    private String crawlConfigPath;
    private ExportConfig exportConfig;
    private String searchKeywords;
    private String maxCrawlingDocumentsCount;
}
