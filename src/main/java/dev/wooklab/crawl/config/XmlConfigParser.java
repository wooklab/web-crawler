package dev.wooklab.crawl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.utilities.AssertionFailure;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class XmlConfigParser {
    private static final Logger logger = LoggerFactory.getLogger(XmlConfigParser.class);

    private XmlConfigParser() {
        throw new AssertionFailure();
    }

    public static <T> Object unmashall(String xmlStr, Object obj, Class<T> clazz) {
        if (xmlStr == null) return null;
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            Reader reader = new StringReader(xmlStr);
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(reader);

            obj = unmarshaller.unmarshal(xmlStreamReader);
        } catch (JAXBException e) {
            logger.error("JAXB Error in unmashall", e);
        } catch (XMLStreamException e) {
            logger.error("XML Stream Error in unmashall", e);
        }

        return obj;
    }
}
