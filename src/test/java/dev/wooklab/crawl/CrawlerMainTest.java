package dev.wooklab.crawl;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerMainTest {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerMainTest.class);

    @Test
    public void 매개변수_얻어오기() {
        // given
        String[] args = {"/Users/wookmac/workspace/personalProject/JavaProject/WebCrawler/config"};

        // when
        String generalConfigFilePath = CrawlerMain.getGeneralConfigFilePath(args);

        // then
        Assert.assertEquals("/Users/wookmac/workspace/personalProject/JavaProject/WebCrawler/config",
                generalConfigFilePath);
    }
}