package com.binblur.core.utils;

import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.enums.EsseType;
import com.binblur.core.enums.GlobalConst;
import com.binblur.core.enums.LangType;
import com.binblur.core.exception.EspRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>문자열을 다룰때 필요한 기능을 모아 놓은 유틸성 클래스. </p>
 *
 * @author luckeey
 * @version 1.0
 */
@Slf4j
public class CommUtil {

    static final String TEMP_FILE_DIR = "./kefico-evcharge-api/downFile/";

    /**
     * <p>지정 문자열에서 특정 문자를 변경함.</p>
     *
     * @param oldChar 찾으려고하는 단어.
     * @param newChar 바꾸고자하는 단어.
     * @param source  대상 문자열.
     * @return 변경된 문자열.
     */
    public static String replace(char oldChar, char newChar, String source) {
        return source.replace(oldChar, newChar);
    }


    /**
     * <p>지정 문자열에서 특정 문자를 변경함.</p>
     *
     * @param source  대상 문자열.
     * @param search  찾으려고하는 단어.
     * @param replace 바꾸고자하는 단어.
     * @return 변경된 문자열.
     */
    public static String replace(String source, String search, String replace) {
        StringBuffer stringbuffer = new StringBuffer(source.length());
        int j = 0;
        for (int i = source.indexOf(search, j); i != -1; i = source.indexOf(search, j)) {
            stringbuffer.append(source.substring(j, i));
            stringbuffer.append(replace);
            j = i + search.length();
        }

        if (j < source.length()) {
            stringbuffer.append(source.substring(j));
        }
        return stringbuffer.toString();
    }


    /**
     * <p>문자열안에 특정 캐릭터가 몇개가 있는지를 반환.</p>
     *
     * @param in      대상문자열.
     * @param lookFor 대상캐릭터
     * @return 갯수.
     */
    public static int charCount(String in, char lookFor) {

        String search = new String(in);
        int index = search.indexOf(lookFor);
        int count = 0;

        while (index > 0) {
            count++;
            search = search.substring(index + 1);
            index = search.indexOf(lookFor);
        }
        return count;
    }


    /**
     * <p>문자열안에 특정 문자열이 몇개가 있는지를 반환.</p>
     *
     * @param str  대상 문자열.
     * @param find 찾고자 하는 문자열.
     * @return 갯수.
     */
    public static int charCount(String str, String find) {

        int i = 0;
        int pos = 0;
        while (true) {
            pos = str.indexOf(find, pos);
            if (pos == -1) {
                break;
            }
            i++;
            pos++;
        }

        return i;
    }

    /**
     * <p>문자열을 지정 구분자로 나눈 뒤 ArrayList로 반환. (join과 반대의 기능을 함)</p>
     *
     * @param data      문자열.
     * @param delimiter 딜리미터.
     * @return 문자열 ArrayList.
     */
    public static ArrayList<String> splitToArray(String data, String delimiter) {
        ArrayList<String> stArr = new ArrayList<>();
        int nPos = 0, nCurPos = 0;
        while ((nCurPos = data.indexOf(delimiter, nPos)) >= 0) {
            stArr.add(data.substring(nPos, nCurPos));
            nPos = nCurPos + delimiter.length();
        }
        if (nPos < data.length()) {
            stArr.add(data.substring(nPos, data.length()));
        }

        return stArr;
    }

    /**
     * <p>문자열을 지정 구분자로 나눈 뒤 배열값으로 반환. (join과 반대의 기능을 함)</p>
     *
     * @param data      문자열.
     * @param delimiter 구분자.
     * @return 문자열 토큰배열.
     */
    public static String[] split(String data, String delimiter) {
        ArrayList stArr = new ArrayList();
        int nPos = 0, nCurPos = 0;
        while ((nCurPos = data.indexOf(delimiter, nPos)) >= 0) {
            stArr.add(data.substring(nPos, nCurPos));
            nPos = nCurPos + delimiter.length(); //20061025(yhkim)  delimiter의 크기만큼 파싱시작 위치 이동
        }
        if (nPos < data.length()) {
            stArr.add(data.substring(nPos, data.length()));
        }

        String array[] = new String[stArr.size()];
        stArr.toArray(array);

        return (array);
    }


    /**
     * <p>문자열 토큰배열에 구분자를 넣어서 합친 문자열 반환. (spilit와 반대의 기능을 함)</p>
     *
     * @param array     토큰배열.
     * @param delimiter 구분자.
     * @return 합쳐진 문자열.
     */
    public final static String join(String array[], String delimiter) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(array[i]);
        }

        return (sb.toString());
    }


    /**
     * <p>특정문자로 둘러싸인 알맹이 문자 얻기</p>
     * <p>ex) getLapOutText("<!Text!>", "<!", "!>") --> Text</p>
     *
     * @param str      문자열.
     * @param startTag 시작문자열.
     * @param endTag   끝문자열.
     * @return 알맹이 문자열.
     */
    public static String getLapOutText(String str, String startTag, String endTag) {

        return getLapOutText(str, startTag, endTag, 1);
    }


    /**
     * <p>특정문자로 둘러싸인 알맹이 문자 얻기</p>
     * <p>ex) getLapOutText("<!Text!>", "<!", "!>") --> Text</p>
     *
     * @param str      문자열.
     * @param startTag 시작문자열.
     * @param endTag   끝문자열.
     * @param index    시작문자열 부터 작업할 것인지 Index (1부터 시작).
     * @return 알맹이 문자열.
     */
    public static String getLapOutText(String str, String startTag, String endTag, int index) {

        String l_String = str;
        int l_posStartTag = 0;
        int l_lookUpPoint = 0;
        int l_findCnt = 0;
        //---------------------------------------------------------------------
        // 시작 Tag 위치 알아 내기
        //---------------------------------------------------------------------
        do {
            //-- 시작 Tag의 위치 알아 내기
            l_posStartTag = l_String.indexOf(startTag, l_lookUpPoint);
            //-- 찾은 수 증가
            l_findCnt++;
            //-- 다음 검색 위치조정
            l_lookUpPoint = l_posStartTag + 1;
            //-- 찾은 수가 찾을 번째 수와 같을때 까지 Loop
        } while (l_findCnt < index && l_posStartTag >= 0);

        if (l_posStartTag < 0) {
            return "";
        }
        //---------------------------------------------------------------------
        // 종료 Tag의 위치 알아 내기
        //---------------------------------------------------------------------
        int l_posEndTag = l_String.indexOf(endTag, l_posStartTag);

        if (l_posEndTag < 0) {
            return "";
        }

        //---------------------------------------------------------------------
        // Column Name 가져오기
        //---------------------------------------------------------------------
        l_String = l_String.substring(l_posStartTag + startTag.length(), l_posEndTag).trim();

        return l_String;
    }

    /**
     * <p>':'로 구분된 문자열 중 지정 인덱스에 해당하는 문자열를 얻음.</p>
     *
     * @param sSource 전체 문자열.
     * @param iNo     위치(몇 번째 문자열 인지)
     * @return 지정 인덱스 문자열.
     */
    public static String getField(String sSource, int iNo) {
        return getField(sSource, iNo, ":");
    }


    /**
     * <p>지정 구분자로 구분된 문자열 중 지정 인덱스에 해당하는 문자열를 얻음.</p>
     *
     * @param sSource 전체 문자열.
     * @param iNo     위치(몇 번째 문자열 인지)
     * @param sDelim  구분자.
     * @return 지정 인덱스 문자열.
     */
    public static String getField(String sSource, int iNo, String sDelim) {

        ArrayList arr = new ArrayList();
        int nPos = 0, nCurPos = 0;

        if (iNo < 1) {
            return "Error : iNo가 0보가 커야 합니다.";
        }

        while ((nCurPos = sSource.indexOf(sSource, nPos)) >= 0) {
            arr.add(sSource.substring(nPos, nCurPos));
            nPos = ++nCurPos;
        }
        if (nPos <= sSource.length()) {
            arr.add(sSource.substring(nPos, sSource.length()));
        }

        String sTmp = "";

        if (arr.size() >= iNo - 1) {
            sTmp = (String) arr.get(iNo - 1);
        }

        return sTmp;
    }


    /**
     * <p>문자열이 숫자를 포함하고 있을 경우 첫숫자의 위치를 얻음.</p>
     *
     * @param sVal 입력값.
     * @return 첫번째 숫자 위치.
     */
    public static int indexOfFirstNumber(String sVal) {

        String s = "";
        int iPos = 0;
        boolean b = false;
        for (int i = 0; i < sVal.length(); i++) {
            s = sVal.substring(i, i + 1);
            try {
                iPos = Integer.parseInt(s);
                b = true;
            } catch (Exception e) {
                b = false;
            }
            if (b) {
                iPos = i;
                break;
            }
        }
        return iPos;
    }


    /**
     * <p>문자열을 원하는 길이로 자른 후 나머지 문자열을
     * 지정된 문자로 변경함.</p>
     *
     * @param str 전체 문자열.
     * @param len 원하는 길이.
     * @param par 지정된 문자.
     * @return 변경된 문자열.
     */
    public static String cut(String str, int len, String par) {
        int iStart, iEnd;
        String original = str;
        String str1 = "";

        if (str == null) {
            return str;
        }
        if (str.length() <= len) {
            return str;
        }

        iStart = str.indexOf("<");
        iEnd = str.indexOf(">");

        while (iStart >= 0 && iEnd >= 0) {
            /*--
            if (iStart > 0) iStart = iStart;
            --*/
            if (iEnd < str.length()) {
                iEnd = iEnd + 1;
            }

            str1 += str.substring(0, iStart);
            str = str.substring(iEnd);
            iStart = str.indexOf("<");
            iEnd = str.indexOf(">");

        }
        if (original.length() < len) {
            return original;
        } else if (str1.length() == 0) {
            return original.substring(0, len) + par;
        } else if (str1.length() > 0 && str1.length() < len) {
            return original;
        } else {
            return replace(str1, str1.substring(0, len) + par, original);
        }
    }


    /**
     * <p>원본 문자열에 지정한 포맷으로 문자열을 삽입.</p>
     * ex) insertStr("12345678", "/", "3_2_3")   ==>   return 123/45/678
     *
     * @param orgStr 전체 문자열.
     * @param insStr 삽입 문자열.
     * @param format 삽입 포맷.
     * @return 변경된 문자열.
     */
    public static String insertStr(String orgStr, String insStr, String format) {

        String convStr = "";
        StringTokenizer st = new StringTokenizer(format, "_");

        // 모두 공백일 경우 원본 넘김
        boolean isallSpace = true;
        for (int i = 0; i < orgStr.length(); i++) {
            if (orgStr.charAt(i) != ' ') {
                isallSpace = false;
                break;
            }
        }
        if (isallSpace) {
            return orgStr;
        }

        int i = 0;
        try {
            while (st.hasMoreTokens()) {
                int j = Integer.parseInt(st.nextToken());
                convStr += orgStr.substring(i, i + j);
                i += j;
                if (st.hasMoreTokens()) {
                    convStr += insStr;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return convStr;
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여줌.</p>
     *
     * @param s     원본 문자열.
     * @param limit 잘라야 될 길이.
     * @return 변경된 문자열.
     */
    public static String fixLength(String s, int limit) {
        return fixLength(s, limit, "...");
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여줌.</p>
     *
     * @param s       문자열.
     * @param limit   될 길이.
     * @param postfix 문자열.
     * @return 변경된 문자열.
     */
    public static String fixLength(String s, int limit, String postfix) {
        char[] charArray = s.toCharArray();

        if (limit >= charArray.length) {
            return s;
        }
        return new String(charArray, 0, limit).concat(postfix);
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여줌.</p>
     * <p>단 fixLength와의 차이는 제한길이의 기준이 char가 아니라 byte로
     * 처리함으로해서 한글문제를 해결할수 있다.</p>
     *
     * @param s         문자열.
     * @param limitByte 될 길이 (byte).
     * @return 변경된 문자열.
     */
    public static String fixUnicodeLength(String s, int limitByte) {
        return fixUnicodeLength(s, limitByte, "...");
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여줌.</p>
     *
     * @param s         문자열.
     * @param limitByte 될 길이 (byte).
     * @param postfix   문자열.
     * @return 변경된 문자열.
     */
    public static String fixUnicodeLength(String s, int limitByte, String postfix) {

        // Cut empty string
        s = s.trim();

        byte[] outputBytes = s.getBytes();
        String output = null;

        if (outputBytes.length <= limitByte) {
            output = s;
        } else {
            int han_count = 0;

            for (int i = 0; i < limitByte; i++) {
                if (outputBytes[i] < 0) {
                    han_count++;
                }
            }

            if (han_count % 2 == 1) {
                output = new String(outputBytes, 0, limitByte - 1);
            } else {
                output = new String(outputBytes, 0, limitByte);
            }

            // output = new String( outputBytes, 0, limitByte );

            //if(output.length() == 0)
            //    output = new String( outputBytes, 0, limitByte-1 );
            //output.concat( postfix );
            //Minkoo : upper code do not work. I don't know exatly why...
            output += postfix;
        }
        return output;
    }


    /**
     * <p>Throwable 객체에 대한 예외추적 문자열을 얻음.</p>
     *
     * @param e 예외객체.
     * @return 예외추적 문자열.
     */
    public static String getStackTrace(Throwable e) {
        log.debug(e.getMessage());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(bos);
        writer.flush();
        return bos.toString();
    }


    /**
     * <p>HTML 태그를 모두 제거한다.</p>
     *
     * @param source
     * @return
     */
    public static String removeHTMLtag(String source) {

        Pattern p = Pattern.compile("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>");
        Matcher m = p.matcher(source);

        return m.replaceAll("");
    }

    /**
     * 이메일의 도메인 부분을 얻어온다.
     *
     * @param email 이메일 주소
     * @return 도메인 파트
     */
    public static String getDomain(String email) {
        if (email.trim().length() == 0) {
            return "";
        }

        int pos = email.indexOf("@");
        if (pos == -1) {
            return "";
        }

        String domain = email.substring(pos + 1, email.length());
        return domain;
    }

    /**
     * - 두 인자의 값이 같으면 checked 를 반환한다. - 같지 않으면 빈값을 반환
     *
     * @param source , target
     * @return 체크 여부
     */
    public static String getChecked(String source, String target) {
        if (source.trim().equals(target.trim())) {
            return "checked";
        } else {
            return "";
        }
    }


    /**
     * 문자열의 UTF-8 BOM 없앤 후 문자열 반환
     *
     * @param str
     * @return
     */
    public static String getStripBOM(String str) {
        String line = str;

        try {
            byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
            byte[] lineByte = str.getBytes("UTF-8");

            if (lineByte[0] == bom[0] && lineByte[1] == bom[1] && lineByte[2] == bom[2]) {
                line = new String(lineByte, 3, lineByte.length - 3, "UTF-8");
            }

            if (line.startsWith(new String(bom))) {
                line = CommUtil.replace(line, new String(bom), "");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return line;
    }


    /**
     * <p>문자열을 일정길이 만큼만 보여주고
     * 그 길이에 초과되는 문자열일 경우 특정문자로 대체하여 보여줌.</p>
     *
     * @param s      문자열. 	ex) admin
     * @param length 될 길이.	ex) 3
     * @param rChar  문자.		ex) *
     * @return 변경된 문자열.	ex) adm**
     */
    public static String fixLengthReplaceChar(String s, int length, String rChar) {
        String rtnStr = s;
        StringBuffer sb = new StringBuffer();

        if (s.length() > 0 && length != 0 && length < s.length()) {
            rtnStr = s.substring(0, length);

            for (int ix = 0; ix < (s.length() - length); ix++) {
                sb.append(rChar);
            }

        }
        return rtnStr.concat(sb.toString());
    }

    /**
     * - Prefix 와 Surfix 사이의 문자 리스트를 구한다.
     *
     * @param source , prefix, surfix
     * @return 체크 여부
     */
    public static String[] getItemNo(String source, String prefix, String surfix) {
        HashMap result = new HashMap();
        int start = source.indexOf(prefix);
        while (start > -1) {
            int end = source.indexOf(surfix, start);
            if (start > -1 && end > -1) {
                String item_no = source.substring(start + prefix.length(), end);
                result.put(item_no, item_no);
                start = source.indexOf(prefix, end);
            }
        }
        String[] str = {};
        return (String[]) result.keySet().toArray(str);
    }

    /**
     * 문자 배열 내에 찾으려눈 문자와 일치하는 문자가 있는지 여부를 반환한다.
     *
     * @param arr 문자 배열
     * @param str 찾으려는 문자열
     * @return 동일한 문자열 있으면 true, 아니면 false
     */
    public static boolean contains(String[] arr, String str) {
        if (arr == null || str == null) {
            return false;
        }
        boolean b = false;
        for (String s : arr) {
            if (s.equals(str)) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * s가 null 이거나 비어있는지 검사한다.
     *
     * @param s
     * @return null 이거나 비어있으면 true, 내용이 있으면 false
     */
    public static boolean isEmpty(String s) {
        if (s == null || s.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 문자열중 숫자만 추출하여 리턴
     *
     * @param s 원본 문자열
     * @return 숫자로 구성된 문자열
     */
    public static String getNumeral(String s) {
        if (isEmpty(s)) {
            return "";
        }
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    result += c;
                    break;
            }
        }
        return result;
    }

    /**
     * sql select 구문 내에 인자들을 파싱하여 배열로 반환
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static ArrayList<String> sqlSelectParser(String s) throws Exception {
        ArrayList<String> stArr = new ArrayList<>();

        // '과 ' 사이의 문자 모두 치환
        String temp = s;
        String data = "";
        int spos = temp.indexOf("'");
        int epos = -1;
        while (spos > -1) {
            data += temp.substring(0, spos + 1);
            temp = temp.substring(spos + 1);
            epos = temp.indexOf("'");
            if (epos > -1) {
                for (int i = 0; i < epos; i++) {
                    data += "_";
                }
                data += "'";
                temp = temp.substring(epos + 1);
                spos = temp.indexOf("'");
            } else {
                throw new Exception("Can not parse string. cause : need more '");
            }
        }
        data += temp;
        // (과 ) 사이의 문자 모두 치환
        temp = data;
        data = "";
        spos = temp.indexOf("(");
        epos = -1;
        while (spos > -1) {
            data += temp.substring(0, spos + 1);
            temp = temp.substring(spos + 1);
            epos = temp.indexOf(")");
            if (epos > -1) {
                for (int i = 0; i < epos; i++) {
                    data += "_";
                }
                data += ")";
                temp = temp.substring(epos + 1);
                spos = temp.indexOf("(");
            } else {
                throw new Exception("Can not parse string. cause : need )");
            }
        }
        data += temp;

        if (data.length() != s.length()) {
            throw new Exception("Data length error. original : " + s.length() + ", change" + data.length());
        }

        // delimiter위치는 치환된 data로 확인하고 실제 자르는 데이터는 원본을 자른다.
        // split 을 사용하면 안됨.. 절대...
        String delimiter = ",";
        int nPos = 0, nCurPos = 0;
        while ((nCurPos = data.indexOf(delimiter, nPos)) >= 0) {
            stArr.add(s.substring(nPos, nCurPos));
            nPos = nCurPos + delimiter.length(); //20061025(yhkim)  delimiter의 크기만큼 파싱시작 위치 이동
        }
        if (nPos < data.length()) {
            stArr.add(s.substring(nPos, s.length()));
        }

        return stArr;
    }

    /**
     * 오늘일자가 그 달의 첫번째 월요일인지 체크한다.
     *
     * @return
     */
    // 한달 달력(주차별 통계용)
    public static ArrayList<HashMap<String, Object>> getCalander() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        // 첫번째 주
        HashMap<String, Object> firstItem = new HashMap<>();
        cal.set(Calendar.DATE, 1);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        firstItem.put("firstDay", formatter.format(cal.getTime()));
        cal.add(Calendar.DATE, 7 - dayNum);
        firstItem.put("lastDay", formatter.format(cal.getTime()));
        result.add(firstItem);

        // 마지막 전주차까지 순회
        int week = cal.get(Calendar.WEEK_OF_MONTH);

        while (week < cal.getActualMaximum(Calendar.WEEK_OF_MONTH) - 1) {
            HashMap<String, Object> item = new HashMap<>();
            cal.add(Calendar.DATE, 1);
            item.put("firstDay", formatter.format(cal.getTime()));
            cal.add(Calendar.DATE, 6);
            item.put("lastDay", formatter.format(cal.getTime()));

            result.add(item);

            week++;
        }

        // 마지막 주
        HashMap<String, Object> lastItem = new HashMap<>();
        cal.add(Calendar.DATE, 1);
        lastItem.put("firstDay", formatter.format(cal.getTime()));
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastItem.put("lastDay", formatter.format(cal.getTime()));

        result.add(lastItem);

        return result;
    }

    // 오늘 일자 구하기(int)
    public static int getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = formatter.format(cal.getTime());

        String tempTodayDate = today.substring(8);
        if (tempTodayDate.substring(0, 1).equals("0")) {
            return Integer.parseInt(tempTodayDate.substring(1));
        } else {
            return Integer.parseInt(tempTodayDate);
        }
    }

    // 금일 날짜(YYYY-MM-DD) Query용
    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return formatter.format(cal.getTime());
    }

    // 금일 날짜(YYYY년 MM월 DD일 월요일)
    public static String getFullToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        String calFormat = formatter.format(cal.getTime());
        String year = calFormat.substring(0, 4);
        String month = "";
        if (calFormat.substring(5, 6).equals("0")) {
            month = calFormat.substring(6, 7);
        } else {
            month = calFormat.substring(5, 7);
        }
        String day = calFormat.substring(8);
        if (calFormat.substring(8, 9).equals("0")) {
            day = calFormat.substring(9);
        }
        String dayOfWeek = "";
        switch (dayNum) {
            case 1:
                dayOfWeek = "일요일";
                break;
            case 2:
                dayOfWeek = "월요일";
                break;
            case 3:
                dayOfWeek = "화요일";
                break;
            case 4:
                dayOfWeek = "수요일";
                break;
            case 5:
                dayOfWeek = "목요일";
                break;
            case 6:
                dayOfWeek = "금요일";
                break;
            case 7:
                dayOfWeek = "토요일";
                break;

        }

        return year + "년 " + month + "월 " + day + "일 " + dayOfWeek;
    }

    // 당월 첫번째 날(YYYY-MM-DD)
    public static String getFirstDayOfMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return formatter.format(cal.getTime());
    }

    // 최대 주차(2022년 8월 > return 5)
    public static int getMaxWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    // 최대 일자(8월 > return 31)
    public static int getMaxDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * otp 6자리 번호 생성
     */
    public static String makeCert6Digit() {
        int rtn01 = (int) ((Math.random() * 10000) % 10);
        int rtn02 = (int) ((Math.random() * 10000) % 10);
        int rtn03 = (int) ((Math.random() * 10000) % 10);
        int rtn04 = (int) ((Math.random() * 10000) % 10);
        int rtn05 = (int) ((Math.random() * 10000) % 10);
        int rtn06 = (int) ((Math.random() * 10000) % 10);
        return rtn01 + "" + rtn02 + "" + rtn03 + "" + rtn04 + "" + rtn05 + "" + rtn06;
    }


    /**
     * 아이피 가져오기
     *
     * @return
     */
    public static String getIp() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = null;

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            try {
                ip = request.getHeader("X-Forwarded-For");

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-RealIP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("REMOTE_ADDR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } catch (Exception e) {
                return "0.0.0.1";
            }
        } else {
            return "0.0.0.0";
        }
        return ip;
    }


    /**
     * uri 가져오기
     *
     * @return
     */
    public static String getUri() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String uri = null;

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            try {
                uri = request.getRequestURI();
            } catch (Exception e) {
                return "/";
            }
        } else {
            return "/";
        }
        return uri;
    }

    public static String getFileName(String name) throws UnsupportedEncodingException {
        Date date = new Date();
        String dateText = new SimpleDateFormat("yyyyMMdd").format(date);
        String fileName = java.net.URLEncoder.encode(name, "UTF-8");
        fileName = fileName.replaceAll("\\+", "%20");
        return fileName + "_" + dateText + ".xlsx";
    }

    public static String getCookie(HttpServletRequest req, String cName) {
        Cookie[] cookies = req.getCookies(); // 모든 쿠키 가져오기
        if (cookies != null) {
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기
                if (StringUtils.equals(name, cName)) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 맥어드래스 체크 예 C8-58-C0-B8-A5-0B
     */
    public static boolean checkMacAddress(String deviceMacAddress) {

        if (StringUtils.isEmpty(deviceMacAddress) && deviceMacAddress.length() != 17) {
            return false;
        }

        String macAddress = deviceMacAddress.replaceAll("-", "");

        if (macAddress.length() != 12) {
            return false;
        }

        return true;

    }

    /**
     * 대문자 인지 확인
     */
    public static boolean isStringUpperCase(String str) {
        if (!str.equals(str.toUpperCase())) {
            return false;
        }
        return true;
    }

    /**
     * 한글포함여부
     */
    public static boolean isIncludeKorLang(String str) {
        if (str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return true;
        }
        return false;
    }

    /**
     * 비밀번호 체크
     */
    public static boolean isBadPassword(String str) {

        // 비밀번호 포맷 확인(영문, 영문대문자, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/])[A-Za-z\\d`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/]{8,20}$");
        Matcher passMatcher = passPattern.matcher(str);

        if (!passMatcher.find()) {
            return true;
        }

        return false;
    }

    /**
     * 전화번호 체크
     */
    public static boolean isBadPhoneNo(String str) {

        if (StringUtils.isNotEmpty(str)) {
            str = str.trim().replaceAll("-", "");
        }

        Pattern passPattern = Pattern.compile("^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$");
        Matcher passMatcher = passPattern.matcher(str);

        if (passMatcher.find()) {
            return false;
        }

        return true;
    }


    /**
     * 비밀번호 자동생성 10 자리
     */
    public static String getRamdomPassword() {
        int size = 10;
        char[] charSet = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    /**
     * 한글 입력 체크
     */
    public static void korFieldCheck(EsseType essenType, String str, Integer len, String name) {
        charValidate(essenType, LangType.ENG, str, len, name);
    }

    /**
     * 이메일 체크
     */
    public static void mailFieldCheck(EsseType essenType, String str, Integer len, String name) {
        charValidate(essenType, LangType.MAIL, str, len, name);
    }

    /**
     * 핸드폰번호
     */
    public static void phoneFieldCheck(EsseType essenType, String str, Integer len, String name) {
        charValidate(essenType, LangType.PHONE, str, len, name);
    }

    /**
     * 다양한 입력 체크
     */
    public static void fieldCheck(EsseType essenType, String str, Integer len, String name) {
        charValidate(essenType, LangType.ALL, str, len, name);
    }

    /**
     * 필수,자리수 체크
     */
    public static void fieldCheck(String str, Integer len, String name) {
        charValidate(EsseType.ESS, LangType.ALL, str, len, name);
    }

    /**
     * 필수만 체크
     */
    public static void fieldCheck(String str, String name) {
        charValidate(EsseType.ESS, LangType.ALL, str, 0, name);
    }

    public static void charValidate(EsseType essenType, LangType type, String str, Integer len, String name) {

        String nameText = "";

        if (StringUtils.isNotEmpty(name)) {
            nameText = "[" + name + "] 항목은";
        }

        // 필수인경우
        if (EsseType.ESS.equals(essenType)) {
            if (StringUtils.isEmpty(str)) {
                throw new EspRuntimeException(ApiResultCode.FAIL, nameText + (CommUtil.isKor() ? " 필수 값입니다." : " is essential"));
            }
        }

        if (StringUtils.isNotEmpty(str)) {
            if (LangType.ENG.equals(type) && isIncludeKorLang(str)) {
                throw new EspRuntimeException(ApiResultCode.FAIL, nameText + (CommUtil.isKor() ? " 한글을 입력할 수 없습니다." : " Do not enter Korean"));
            } else if (LangType.MAIL.equals(type) && isBadEmail(str)) {
                throw new EspRuntimeException(ApiResultCode.FAIL, nameText + (CommUtil.isKor() ? " 잘못된 형식의 이메일 주소입니다." : "This is a malformed email address."));
            } else if (LangType.PHONE.equals(type)) {
                /* 전화번호는 자리수만 체크 11 자리 혹은 10 자리 성유환 책임 요청 2023-04-04 */
                if (str.trim().length() != 10 && str.trim().length() != 11) {
                    throw new EspRuntimeException(ApiResultCode.FAIL_TEL_DIGITS);
                }

                if (!StringUtils.isNumeric(str)) {
                    throw new EspRuntimeException(ApiResultCode.FAIL_TEL_DIGITS);
                }
            }

            if (len > 0 && str.trim().length() > len) {
                throw new EspRuntimeException(ApiResultCode.FAIL, nameText +  (CommUtil.isKor() ? " 크기가 " + len + "글자를 초과할 수 없습니다." : "digits is not over " + len ));
            }
        }
    }

    /**
     * 오늘 날짜를 원하는 형식으로 변경한다. getNowDate("yyyyMMdd")
     *
     * @return String
     */
    public static String getNowDate(String format) {
        Date date = new Date();
        String dateText = new SimpleDateFormat(format).format(date);
        return dateText;
    }


    /**
     * Comment  : 정상적인 이메일 인지 검증.
     */
    public static boolean isBadEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email.replaceAll("-", "").toLowerCase()); // - 는 검사에 포함시키지 않는다. 대소문자도 검사하지 않는다.
        if (m.matches()) {
            log.debug("match ==>" + email);
            return false;
        } else {
            log.debug("not match ==>" + email);
            return true;
        }
    }

    /**
     * 헥사값 변환 이진수로 128 바이트
     */
    public static String hexToBin(String inStr) {
        String rtnVal = "";
        try {
            for (String charactor : inStr.split("")) {
                String tempStr = Integer.toBinaryString(Integer.parseInt(charactor, 16));
                rtnVal += lPad(tempStr, 4, "0");
            }
        } catch (Exception e) {
            return "";
        }
        return rtnVal;
    }


    /**
     * 컴마 딜리미터로 변경
     */
    public static List<Integer> changeErrorPosArray(String inStr) {
        String rtnVal = inStr;
        List<Integer> rtnArray = new ArrayList<>();
        try {
            rtnVal = rtnVal.replaceAll("]", "").replaceAll(" ", "").replaceAll("\\[", "");
            for (String val : rtnVal.split(",")) {
                rtnArray.add(Integer.parseInt(val));
            }
        } catch (Exception e) {
            return null;
        }
        return rtnArray;
    }


    /**
     * lpad 함수
     */
    public static String lPad(String strContext, int iLen, String strChar) {
        String strResult = "";
        StringBuilder sbAddChar = new StringBuilder();
        for (int i = strContext.length(); i < iLen; i++) {  // iLen길이 만큼 strChar문자로 채운다.
            sbAddChar.append(strChar);
        }
        strResult = sbAddChar + strContext;  // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
        return strResult;
    }

    public static boolean isBetween(String strNum, String endNum, String targetNum) {
        BigDecimal bigStrNum = new BigDecimal(strNum);
        BigDecimal bigEndNum = new BigDecimal(endNum);
        BigDecimal bigTargetNum = new BigDecimal(targetNum);

        if (bigStrNum.compareTo(bigTargetNum) <= 0 && bigTargetNum.compareTo(bigEndNum) <= 0) {
            return true;
        }

        return false;
    }

    public static String convertTelNo(String src) {
        if (src == null) {
            return "";
        }
        if (src.length() == 8) {
            return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (src.length() == 12) {
            return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }

    public static String getCountryCookie() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String cookieVal = getCookie(request, GlobalConst.COUNTRY_COOKIE.getCode());
            if (StringUtils.isNotEmpty(cookieVal)) {
                return cookieVal;
            } else {
                return "KR";
            }
        } catch (Exception e) {
            return "KR";
        }
    }

    /**
     * 한국어 지원로그인인지 쿠키정보로 확인
     */
    public static boolean isKor() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String cookieVal = getCookie(request, GlobalConst.COUNTRY_COOKIE.getCode());

            if (StringUtils.equals("KR", cookieVal)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 엑셀쉬트 지우기
     */
    public static XSSFWorkbook delDataSheet(XSSFWorkbook xssfWorkbook) {
        if (xssfWorkbook != null) {
            int sheetCount = xssfWorkbook.getNumberOfSheets();
            int totalCount = xssfWorkbook.getNumberOfSheets();
            try {
                for (int i = 0; i < totalCount; i++) {
                    for (int j = 0; j < sheetCount; j++) {
                        if (StringUtils.equals(xssfWorkbook.getSheetAt(i).getSheetName(), GlobalConst.SHEET_100.getCode())) {
                            xssfWorkbook.removeSheetAt(i);
                            sheetCount--;
                        } else if (StringUtils.equals(xssfWorkbook.getSheetAt(i).getSheetName(), GlobalConst.SHEET_1000.getCode())) {
                            xssfWorkbook.removeSheetAt(i);
                            sheetCount--;
                        } else if (StringUtils.equals(xssfWorkbook.getSheetAt(i).getSheetName(), GlobalConst.SHEET_10000.getCode())) {
                            xssfWorkbook.removeSheetAt(i);
                            sheetCount--;
                        }
                    }
                }
            } catch (Exception e) {
                log.debug("엑셀 쉬트 삭제 에러");
            }
        }
        return xssfWorkbook;
    }

    public static boolean isExistSheet(XSSFWorkbook xssfWorkbook, String sheetName) {
        int sheetCount = xssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            if (StringUtils.equals(xssfWorkbook.getSheetAt(i).getSheetName(), sheetName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 엑셀쉬트 지우기
     */
    public static XSSFWorkbook initSheet(XSSFWorkbook xssfWorkbook) {
        FormulaEvaluator formulaEval = xssfWorkbook.getCreationHelper().createFormulaEvaluator();
        if (isExistSheet(xssfWorkbook, GlobalConst.SHEET_100.getCode())) {
            XSSFSheet sheet100 = xssfWorkbook.getSheet(GlobalConst.SHEET_100.getCode());
            for (int i = 0; i <= sheet100.getLastRowNum(); i++) {
                XSSFRow row = sheet100.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    String cellStringValue = CommUtil.getStringData(cell, formulaEval);
                    if (j == 0 && StringUtils.isEmpty(cellStringValue)) {
                        break;
                    } else if (cell != null) {
                        cell.setCellValue("");
                    }
                }
            }
        }

        if (isExistSheet(xssfWorkbook, GlobalConst.SHEET_1000.getCode())) {
            XSSFSheet sheet1000 = xssfWorkbook.getSheet(GlobalConst.SHEET_1000.getCode());
            for (int i = 0; i <= sheet1000.getLastRowNum(); i++) {
                XSSFRow row = sheet1000.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    String cellStringValue = CommUtil.getStringData(cell, formulaEval);
                    if (j == 0 && StringUtils.isEmpty(cellStringValue)) {
                        break;
                    } else if (cell != null) {
                        cell.setCellValue("");
                    }
                }
            }
        }

        if (isExistSheet(xssfWorkbook, GlobalConst.SHEET_10000.getCode())) {
            XSSFSheet sheet10000 = xssfWorkbook.getSheet(GlobalConst.SHEET_10000.getCode());
            for (int i = 0; i <= sheet10000.getLastRowNum(); i++) {
                XSSFRow row = sheet10000.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    String cellStringValue = CommUtil.getStringData(cell, formulaEval);
                    if (j == 0 && StringUtils.isEmpty(cellStringValue)) {
                        break;
                    } else if (cell != null) {
                        cell.setCellValue("");
                    }
                }
            }
        }

        return xssfWorkbook;
    }

    /**
     * 알람모델엑셀파일에 특정 쉬트가 있는지 확인
     */
    public static boolean ckeckAlertModelExcel(MultipartFile multipartFile, String[] inputSheet) throws Exception {
        if (multipartFile != null) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet worksheet = xssfWorkbook.getSheet(GlobalConst.SHEET_OUTPUT.getCode());
            if (worksheet == null) {
                return false;
            } else {
                if (StringUtils.equals("Y", inputSheet[0])) {
                    XSSFSheet sheet100 = xssfWorkbook.getSheet(GlobalConst.SHEET_100.getCode());
                    if (sheet100 == null) {
                        return false;
                    }
                }

                if (StringUtils.equals("Y", inputSheet[1])) {
                    XSSFSheet sheet1000 = xssfWorkbook.getSheet(GlobalConst.SHEET_1000.getCode());
                    if (sheet1000 == null) {
                        return false;
                    }
                }

                if (StringUtils.equals("Y", inputSheet[2])) {
                    XSSFSheet sheet10000 = xssfWorkbook.getSheet(GlobalConst.SHEET_10000.getCode());
                    if (sheet10000 == null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 알람모델엑셀파일에 특정 쉬트 내용이 있는지 확인
     */
    public static boolean ckeckCntnAlertModelExcel(MultipartFile multipartFile, String[] inputSheet) throws Exception {
        if (multipartFile != null) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet worksheet = xssfWorkbook.getSheet(GlobalConst.SHEET_OUTPUT.getCode());
            FormulaEvaluator formulaEval = xssfWorkbook.getCreationHelper().createFormulaEvaluator();
            if (worksheet == null) {
                return false;
            } else {
                if (StringUtils.equals("Y", inputSheet[0])) {
                    if(!isSheetNull(xssfWorkbook.getSheet(GlobalConst.SHEET_100.getCode()), formulaEval)){
                        return false;
                    }
                }

                if (StringUtils.equals("Y", inputSheet[1])) {
                    if(!isSheetNull(xssfWorkbook.getSheet(GlobalConst.SHEET_1000.getCode()), formulaEval)){
                        return false;
                    }
                }

                if (StringUtils.equals("Y", inputSheet[2])) {
                    if(!isSheetNull(xssfWorkbook.getSheet(GlobalConst.SHEET_10000.getCode()), formulaEval)){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 엑셀 Cell data 를 string 으로 리턴
     */
    public static String getStringData(Cell cell, FormulaEvaluator formulaEval) {
        String returnValue = "";
        if (cell != null) {
            CellType cellType = cell.getCellType();

            log.debug("cellType = {}", cellType);
            if (cellType == CellType.BLANK) {
                returnValue = "";
            } else if (cellType == CellType.BOOLEAN) {
                returnValue = String.valueOf(cell.getBooleanCellValue());
            } else if (cellType == CellType.FORMULA) {
                CellValue evaluate = formulaEval.evaluate(cell);
                if (evaluate.getCellType() == CellType.BOOLEAN) {
                    returnValue = String.valueOf(evaluate.getBooleanValue());
                } else {
                    returnValue = evaluate.getStringValue();
                }
            } else if (cellType == CellType.NUMERIC) {
                returnValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
            } else if (cellType == CellType.STRING) {
                returnValue = cell.getStringCellValue();
            }
        }
        return returnValue;
    }

    /**
     * 2023-06-07T10:27:41.289+00:00 입력 들어오면 +00:00 이면 9를 리턴(서울시) +01:00  => 8 을 리턴( 서울시로 변환을 보정할 값을 리턴한다)
     */
    public static LocalDateTime adaptGmtHoure(LocalDateTime inDateTm, String strTm) {
        BigDecimal NINE = new BigDecimal(9);
        String adjTm = strTm.substring(strTm.length() - 6);
        String adjChgTm = adjTm.substring(1);
        String houre = adjChgTm.split(":")[0];
        String minute = adjChgTm.split(":")[1];
        BigDecimal rtnAdjHour = BigDecimal.ZERO;
        LocalDateTime rtnTm = inDateTm;

        if (adjTm.startsWith("+")) {
            rtnAdjHour = NINE.subtract(new BigDecimal(houre)); // gmt 시간을빼준다.
            if (!StringUtils.equals("00", minute)) { // 00 이 아니면 해당분을 더해준다.(한시간을 뺀후 더해줘야 보정이된다.)
                rtnAdjHour = rtnAdjHour.subtract(BigDecimal.ONE);
                rtnTm = rtnTm.plusMinutes(new BigDecimal(minute).longValue());
            }

            if (rtnAdjHour.compareTo(BigDecimal.ZERO) > 0) {
                rtnTm = rtnTm.plusHours(rtnAdjHour.longValue());
            } else if (rtnAdjHour.compareTo(BigDecimal.ZERO) < 0) {
                rtnTm = rtnTm.minusHours(rtnAdjHour.longValue());
            }

        } else {

            rtnAdjHour = NINE.add(new BigDecimal(houre)); // gmt 시간을더해준다.

            if (!StringUtils.equals("00", minute)) { // 00 이 아니면 해당분을 더해준다.
                rtnTm = rtnTm.plusMinutes(new BigDecimal(minute).longValue());
            }
            rtnTm = rtnTm.plusHours(rtnAdjHour.longValue()); // 마이너스 부호의 경우 무조건 plus 시간값이 나온다.
        }

        return rtnTm; // 서울시 기준이 9시 이니 보정할 값을 9에서 부족한 값으로 한다.
    }

    /**
     * 사용메모리 프린트
     */
    public static void printMemory() {
        Runtime.getRuntime().gc();
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        log.debug("MMMMMM ============================================================>" + usedMemory + " bytes");
    }

    public static boolean isSheetNull(XSSFSheet sheet, FormulaEvaluator formulaEval){
        try {
            XSSFRow row = sheet.getRow(1);
            XSSFCell cell = row.getCell(0);
            if (sheet.getPhysicalNumberOfRows() == 0) {
                return true;
            } else {
                if(cell == null ){
                    return true;
                }
                if(StringUtils.isEmpty(CommUtil.getStringData(cell, formulaEval))){
                    return true;
                }
            }
        }catch (Exception e){
            return true;
        }
        return false;
    }


    public static String getIotInfo(String findVal, String provisionConnectionString){
        for(String split1 : provisionConnectionString.split(";")){
            if(split1.indexOf(findVal) > -1){
                return split1.split("=")[1];
            }
        }

        return null;
    }

    public static XSSFRow makeSampletRow(String[] headerInfo, XSSFRow row){
        int dataIndex = 0;
        for (String colunmNm : headerInfo) {
            if(colunmNm.indexOf("(Num)") > -1 ){
                XSSFCell cell = row.createCell(dataIndex);
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(0);
            } else {
                XSSFCell cell = row.createCell(dataIndex);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("Text");
            }
            dataIndex ++;
        }
        return row;
    }

    public static String dubRemove(String inVal, String dil){

        if(StringUtils.isEmpty(inVal)){
            return inVal;
        }

        String rtnVal = "";

        for(String val :  inVal.split(dil)){
            if(StringUtils.isEmpty(rtnVal)){
                rtnVal = val;
            } else {
                if(rtnVal.indexOf(val) < 0){
                    rtnVal = rtnVal+dil+val;
                }
            }
        }
        return rtnVal;
    }


    public static Integer isDupNumber(List<BigDecimal> inVal){

        if(inVal == null || inVal.size() < 1){
            return 0;
        }

        Integer index = 0;
        for(BigDecimal val :  inVal){
            index++;
            Integer matchCnt = 0;
            for(BigDecimal val2 :  inVal){
                if(val.compareTo(val2) == 0){
                    matchCnt ++;
                }

                if(matchCnt > 1){
                    return  index;
                }
            }
        }

        return 0;
    }

    public static String removeSpecialChar(String inVal){
        return inVal.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }

    /**
     * spring locale 설정
     * @param request
     * @param response
     * @param loginLanCode
     */
    public static void setLocale(HttpServletRequest request, HttpServletResponse response, String loginLanCode) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        // 기본 locale은 ko
        Locale locale = Locale.KOREAN;
        if (loginLanCode != null && localeResolver != null) {
            if (loginLanCode.toLowerCase().equals(Locale.US.getLanguage())) {
                localeResolver = RequestContextUtils.getLocaleResolver(request);
                locale = Locale.ENGLISH;
            }
            localeResolver.setLocale(request, response, locale);
        }
    }
}
