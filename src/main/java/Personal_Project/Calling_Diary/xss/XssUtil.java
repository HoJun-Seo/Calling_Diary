package Personal_Project.Calling_Diary.xss;

public class XssUtil {

    // 스크립트 입력 방지용(XSS 방지) 문자열 변환 메소드
    public static String cleanXSS(String value) {
        String returnVal = value;
        returnVal = returnVal.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        returnVal = returnVal.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        returnVal = returnVal.replaceAll("'", "&#39;");
        returnVal = returnVal.replaceAll("eval\\((.*)\\)", "");
        returnVal = returnVal.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        returnVal = returnVal.replaceAll("script", "");
        returnVal = returnVal.replaceAll("iframe", "");
        returnVal = returnVal.replaceAll("embed", "");

        return returnVal;
    }
}
