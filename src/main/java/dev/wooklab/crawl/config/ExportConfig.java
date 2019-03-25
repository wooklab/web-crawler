package dev.wooklab.crawl.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="export")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExportConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String path;
    private String fileName;
    private String fileNameSuffix;
    private String fileNameExtension;
    private String fileEncoding;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }

    public String getFileNameExtension() {
        return fileNameExtension;
    }

    public void setFileNameExtension(String fileNameExtension) {
        this.fileNameExtension = fileNameExtension;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    @Override
    public String toString() {
        return "ExportConfig{" +
                "path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileNameSuffix='" + fileNameSuffix + '\'' +
                ", fileNameExtension='" + fileNameExtension + '\'' +
                ", fileEncoding='" + fileEncoding + '\'' +
                '}';
    }
}
