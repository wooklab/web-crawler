package dev.wooklab.crawl.common;

public enum RegexEnum {
    /** 제거 위한 정규식 - 특정문자 */
    REMOVE_SPECIAL_CHARS("\t|&nbsp|  |^[0-9]*\">|&gt;|&lt;|;|&#8203"),
    /** 제거 위한 정규식 - HTML TAG */
    REMOVE_HTML("<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z0-9]*=[^>]*)?(\\s)*(/)?>"),
    /** 제거 위한 정규식 - 주석 */
    REMOVE_COMMENT("<!-{2,}.*?-{2,}>"),
    /** 제거 위한 정규식 - 깨진 문자 */
    REMOVE_BREAK_CHAR("&#[0-9]{5}(;)?");

    private final String regex;

    public String getRegex() {
        return regex;
    }
    RegexEnum(String regex) {
        this.regex = regex;
    }
}
