package dev.wooklab.crawl.common;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.utilities.AssertionFailure;

import java.io.*;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private static final String NEW_LINE = "\r\n";

    private FileHandler() {
        throw new AssertionFailure();
    }

    public static String readFileToGetString(String readFilePath) {
        if (readFilePath == null) return null;
        File file = new File(readFilePath);
        return readFileToGetString(file, true);
    }

    public static String readFileToGetString(File readFile, boolean isNewLine) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();

        try {
            reader = new BufferedReader(new FileReader(readFile));
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                sb.append(readLine);
                if (isNewLine != false)
                    sb.append(NEW_LINE);
            }
        } catch (FileNotFoundException e) {
            logger.error("File Not Fount Error in readFileToGetString", e);
        } catch (IOException e) {
            logger.error("IO Error in readFileToGetString", e);
        } finally {
            closeReader(reader);
        }
        return sb.toString();
    }

    private static void closeReader(BufferedReader reader) {
        if (reader == null)
            return;
        try {
            reader.close();
        } catch (IOException e) {
            logger.error("IO Error in closeReader", e);
        }
    }



    /**
     * Method to make result file.
     * - 파일을 생성하는 메소드.
     *
     * @param targetContents    저장할 파일 내용
     * @param storeFilePath     저장할 파일 경로
     * @param storeFileName     저장할 파일 명
     * @param storeFileType     파일 타입(e.g *.json, *.txt 등)
     * @param encoding          파일 인코딩
     * @param newFile           최초 생성 파일인지 여부
     * @return 생성된 파일 경로
     */
    public static String makeResultFile(String targetContents, String storeFilePath,
                                        String storeFileName, String storeFileType, String encoding, boolean newFile) {
        StringBuilder finalFileName = new StringBuilder();

        /* 파일이 저장 될 경로 + 파일명 */
        if(storeFileName.length() == 0) {
            finalFileName.append(storeFilePath).append("/");
            finalFileName.append("result_").append(DateHandler.getRealTime("yyyyMMddHHmmss"));
        } else {
            finalFileName.append(storeFilePath).append("/");
            finalFileName.append(storeFileName);
        }
        // 확장자 붙이는 부분
        if (storeFileType.charAt(0) != '.') finalFileName.append(".");
        finalFileName.append(storeFileType);

        // 하위 경로 표시 갈무리
        StringHandler.stringBuilderReplaceAll(finalFileName, "//", "/");

        logger.debug("Store File Path: {}", finalFileName.toString());

        // 새로운 파일인 경우, 중복여부 확인하여 별도의 중복인 경우 현재 일시를 파일명에 appned
        if (newFile && new File(finalFileName.toString()).exists()) {
            String tmpFileFullPathNonType = finalFileName.toString().split("." + storeFileType)[0];
            finalFileName.setLength(0);
            finalFileName.append(tmpFileFullPathNonType);
            finalFileName.append("_").append(DateHandler.getRealTime("yyyyMMddHHmmss"));
            finalFileName.append(".").append(storeFileType);
        }

        File file = new File(finalFileName.toString());
        try {
            // org.apache.commons.io.FileUtils, true=append(파일에 이어쓰기)
            FileUtils.write(file, targetContents + NEW_LINE, encoding, true);
        } catch (IOException e) {
            logger.error("IO Error in makeResultFile method", e);
        }
        // 확장자를 제외한 저장된 파일명을 리턴
        String saveFileFullPath     = finalFileName.toString();
        String saveFileNameNonType  = saveFileFullPath.substring(saveFileFullPath.lastIndexOf("/") + 1, saveFileFullPath.length())
                .split("." + storeFileType)[0];
        return saveFileNameNonType;
    }
}
