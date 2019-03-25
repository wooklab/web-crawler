package dev.wooklab.crawl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler {
    private final static Logger logger = LoggerFactory.getLogger(StringHandler.class);

    private StringHandler() {
        throw new AssertionError();
    }


    /**
     * Method to replace all StringBuilder easy.<br/>
     *
     * @param strBuilder    replaceAll을 수행할 StringBuilder
     * @param oldStr        교체 될 Old String
     * @param newStr        교체 할 New String
     */
    public static void stringBuilderReplaceAll(StringBuilder strBuilder, String oldStr, String newStr) {
        int index = strBuilder.indexOf(oldStr);
        while (index != -1) {
            strBuilder.replace(index, index + oldStr.length(), newStr);
            index += newStr.length();
            index = strBuilder.indexOf(oldStr, index);
        }
    }

    public static boolean isStringEmpty(String inputStr) {
        if (inputStr == null || "".equals(inputStr.trim()) || "null".equalsIgnoreCase(inputStr))
            return true;
        return false;
    }

    /**
     * Gets string inside Tags.<br/>
     *
     * @param repeatPtn   반복되는 패턴
     * @param ptnMatchVal 검색 원문
     * @param startTag    시작 태그
     * @param endTag      종료 태그
     * @return 추출된 문자열
     */
    public static String getStringInsideTags(String repeatPtn, String ptnMatchVal, String startTag, String endTag) {
        Pattern pattern = Pattern.compile(repeatPtn);
        Matcher matcher = pattern.matcher(ptnMatchVal);
        String matchedString = null;

        if (matcher.find()) {
            matchedString = subStringTags(ptnMatchVal, startTag, endTag, matcher.start());
        }
        return matchedString;
    }

    /**
     * Gets string List inside Tags.<br/>
     *
     * @param repeatPtn    반복되는 패턴
     * @param ptnMatchVal  검색 원문
     * @param startTag     시작 태그
     * @param endTag       종료 태그
     * @param strList      반복 횟수 만큼 저장할 문자열 리스트
     */
    public static void setStringListInsideTags(String repeatPtn, String ptnMatchVal, String startTag, String endTag, List<String> strList) {
        Pattern pattern = Pattern.compile(repeatPtn);
        Matcher matcher = pattern.matcher(ptnMatchVal);

        while (matcher.find()) {
            strList.add(subStringTags(ptnMatchVal, startTag, endTag, matcher.start()));
        }
    }

    /**
     * Subtracts string Tags.<br/>
     *
     * @param subTarget substring을 수행할 문자열 대상
     * @param startTag  시작 태그
     * @param endTag    종료 태그
     * @return 추출된 문자열
     */
    public static String subStringTags(String subTarget, String startTag, String endTag) {
        return subStringTags(subTarget, startTag, endTag, 0);
    }

    /**
     * Subtracts string Tags.<br/>
     *
     * @param subTarget substring을 수행할 문자열 대상
     * @param startTag  시작 태그
     * @param endTag    종료 태그
     * @param fromIndex 특정 위치 이후로 수행하기 위한 인덱스(위치 값)
     * @return 추출된 문자열
     */
    public static String subStringTags(String subTarget, String startTag, String endTag, int fromIndex) {
        if (isStringEmpty(subTarget) || isStringEmpty(startTag) || isStringEmpty(endTag) || subTarget.indexOf(startTag, fromIndex) < 0) {
            logger.info("substing 대상 원문과 일치하는 문자열이 없습니다. 조회대상 ==> {}", startTag);
            return "내용없음";
        }
        int startLoc    = subTarget.indexOf(startTag, fromIndex) + startTag.length();
        int endLoc      = subTarget.indexOf(endTag, subTarget.indexOf(startTag, fromIndex));
        return subTarget.substring(startLoc, endLoc);
    }

    /**
     * Removes HTML Tags.<br/>
     *
     * @param text HTML Tag를 제거할 Text(Documents)
     * @return 제거 후 결과 Text
     */
    public static String removeHTMLTags(String text) {
        return text.replaceAll(RegexEnum.REMOVE_COMMENT.getRegex(), "")
                .replaceAll(RegexEnum.REMOVE_HTML.getRegex(), "")
                .replaceAll(RegexEnum.REMOVE_SPECIAL_CHARS.getRegex(), "")
                .replaceAll(RegexEnum.REMOVE_BREAK_CHAR.getRegex(), "");
    }

}