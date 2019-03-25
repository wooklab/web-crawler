package dev.wooklab.crawl;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import dev.wooklab.crawl.common.FileHandler;
import dev.wooklab.crawl.config.CrawlConfigVO;
import dev.wooklab.crawl.config.GeneralConfigVo;
import dev.wooklab.crawl.config.XmlConfigParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerMain {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerMain.class);


    public static void main(String[] args) {
        String generalConfigFilePath = getGeneralConfigFilePath(args);
        logger.info("General Configurations File Path : {}", generalConfigFilePath);

        GeneralConfigVo generalConfigVo = (GeneralConfigVo) getConfig(generalConfigFilePath, GeneralConfigVo.class);
        if (generalConfigVo == null)
            System.exit(1);
        CrawlConfigVO crawlConfigVO = (CrawlConfigVO) getConfig(generalConfigVo.getCrawlConfigPath(), CrawlConfigVO.class);
        reloadLogbackFile(generalConfigVo.getLogbackPath());

        logger.info("System Terminate...");
        System.exit(0);
    }

    private static String getGeneralConfigFilePath(String[] args) {
        if (args.length == 0 || "".equals(args[0])) {
            logger.info("Argument is empty..\n=== Terminate Process.. ===");
            System.exit(1);
        }
        return args[0];
    }

    /**
     * Gets Configurations
     *
     * @param configPath    Configuration file path
     * @param configClass   The configuration class corresponding to The file
     * @return Unmashalled configuartion class Object
     */
    private static <T> Object getConfig(String configPath, Class<T> configClass) {
        Object configObject = null;
        try {
            configObject = XmlConfigParser.unmashall(FileHandler.readFileToGetString(configPath),
                    configClass.newInstance(), configClass);
        } catch (InstantiationException e) {
            logger.error("Instantiation Error in getConfig", e);
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccess Error in getConfig", e);
        } finally {
            if (configObject != null)
                logger.debug(configObject.toString());
        }
        return configObject;
    }

    /**
     * Reload Logback fiile path
     *
     * @param newLogbackpath New Logback path
     */
    private static void reloadLogbackFile(String newLogbackpath) {
        if (newLogbackpath == null)
            return;

        logger.info("RESET logback.xml file in config directory. Path : {}", newLogbackpath);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(newLogbackpath);
        } catch (JoranException e) {
            e.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }
}
