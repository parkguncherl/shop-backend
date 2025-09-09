package com.shop.core.utils;

import com.shop.core.entity.Inven;
import com.shop.core.entity.User;
import com.shop.core.enums.*;
import com.shop.core.exception.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

/**
 * <p>문자열을 다룰때 필요한 기능을 모아 놓은 유틸성 클래스. </p>
 *
 * @author luckeey
 * @version 1.0
 */
@Slf4j
public class CommUtil {

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

    public static String getFileName2(String name) throws UnsupportedEncodingException {
        String fileName = java.net.URLEncoder.encode(name, "UTF-8");
        fileName = fileName.replaceAll("\\+", "%20");
        return fileName + ".xlsx";
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
     * 대문자 인지 확인
     */
    public static boolean isStringUpperCase(String str) {
        if (!str.equals(str.toUpperCase())) {
            return false;
        }
        return true;
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

    public static void charValidate(EsseType essenType, LangType type, String str, Integer len, String name) {

        String nameText = "";

        if (StringUtils.isNotEmpty(name)) {
            nameText = "[" + name + "] 항목은";
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

    public static boolean isExistSheet(XSSFWorkbook xssfWorkbook, String sheetName) {
        int sheetCount = xssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            if (StringUtils.equals(xssfWorkbook.getSheetAt(i).getSheetName(), sheetName)) {
                return true;
            }
        }
        return false;
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

    /**
     * 파일 확장자 가져오기
     * @param file
     */
    public static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            Path path = Paths.get(originalFilename);
            String fileName = path.getFileName().toString();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }
        return "";
    }


    /**
     * 파일 확장자 가져오기
     * @param originalFilename
     */
    public static String getFileExtension(String originalFilename) {
        if (originalFilename != null) {
            Path path = Paths.get(originalFilename);
            String fileName = path.getFileName().toString();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }
        return "";
    }


    public static void checkSearchTerm(LocalDate fromDay, LocalDate toDay, int termMonth){
        if(toDay.isBefore(fromDay)){
            throw new CustomRuntimeException(ApiResultCode.FAIL, "검색시작일자가 검색종료일자보다 작습니다.");
        } else if(fromDay.plusMonths(termMonth).isBefore(toDay)){
            throw new CustomRuntimeException(ApiResultCode.FAIL, "검색 기간이 ["+termMonth+" 개월] 이내에만 가능합니다.");
        }
    }

    public static String judgePayMethod(BigDecimal accountAmt, BigDecimal cashAmt) {
        if (accountAmt.compareTo(BigDecimal.ZERO) > 0) {
            return PayMethodCd.ACCOUNT.getCode();
        } else { // 순수현금만 cash
            return PayMethodCd.CASH.getCode();
        }
    }

    /**
     * 리스트에 두번째 인자가 존재하는지
     * */
    public static boolean isExistInArray(List<String> lists, String findString) {
        for (String str : lists) {
            if (Objects.equals(str, findString)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 리스트에 두번째 인자가 존재하는지
     * */
    public static boolean isExistInIntegerArray(List<Integer> lists, Integer findNumber) {
        for (Integer str : lists) {
            if (Objects.equals(str,findNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 컴마 딜리미터를 Integer list 로 만들기
     * */
    public static List<Integer> makeArrayListInteger(String listVal) {
        if(StringUtils.isEmpty(listVal) || !listVal.contains(",")){
            return null;
        }
        return Arrays.stream(listVal.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * 시간 정보를 포함하는 문자열을 LocalDate 로 변환하기
     * dateTimeString : 2024-12-04T10:15:30
     */
    public static LocalDate dateTimeStringToLocalDate(String dateTimeString) {
        try {
           if (StringUtils.isEmpty(dateTimeString) || dateTimeString.length() < 10 || !dateTimeString.contains("-")) {
              return null;
           }

           return LocalDate.parse(dateTimeString.substring(0, 10));

        } catch (Exception e) {
           return null;
        }
    }

    public static int nullToZero(Integer value) {
        return value == null ? 0 : value;
    }

    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static Integer makeDoubleToInteger (Double val){
        if(val == null){
            return 0;
        }

        return val.intValue();
    }

    public static BigDecimal makeDoubleToBigDecimal (Double val){
        if(val == null){
            return BigDecimal.ZERO;
        }

        return new BigDecimal(val);
    }


    /* 빅데시말 equal*/
    public static boolean equalBigDecimal (BigDecimal b1, BigDecimal b2) {
        if(b1 == null){b1 = BigDecimal.ZERO;}
        if(b2 == null){b2 = BigDecimal.ZERO;}
        return b1.compareTo(b2) == 0 ;
    }

    /* Integer equal*/
    public static boolean equalInteger (Integer b1, Integer b2) {
        if(b1 == null){b1 = 0;}
        if(b2 == null){b2 = 0;}
        return b1.compareTo(b2) == 0 ;
    }

    public static String makeDoubleToString (Double val){
        if(val == null){
            return "";
        }

        return new BigDecimal(val).toPlainString();
    }

    public static String makeObjectToString (Object val){
        if(val == null){
            return "";
        }

        if(val instanceof String){
            return (String)val;
        } else if(val instanceof Integer){
            return val.toString();
        } else if(val instanceof BigDecimal){
            return ((BigDecimal) val).toPlainString();
        } else if(val instanceof Double){
            return makeDoubleToString((Double)val);
        } else if(val instanceof Float){
            return val.toString();
        } else if(StringUtils.isNumeric(val.toString())){
            return new BigDecimal(val.toString()).toPlainString();
        } else {
            return val.toString();
        }

    }

    public static String makeObjectToYN (Object val){
        if(val == null){
            return "N";
        }

        if(val instanceof String){
            if(StringUtils.equalsAnyIgnoreCase((String)val,"true")){
                return "Y";
            } else if(StringUtils.equalsAnyIgnoreCase((String)val,"false")){
                return "N";
            } else {
                return "N";
            }
        } else if (val instanceof Boolean){
            return (Boolean)val ? "Y" : "N";
        } else {
            return "N";
        }

    }


    public static String makeMigSkuNm(String prodNm, String color, String size){
        String rtnString = "";
        String colorMk = StringUtils.isNotEmpty(color) ? color : "기본";
        String sizeMk = StringUtils.isNotEmpty(size) ? size : "FREE";
        if (StringUtils.equalsAny(sizeMk, "F", "f") ) {
            sizeMk = "FREE";
        }
        rtnString = prodNm + "." + colorMk + "." + sizeMk;
        return rtnString;
    }

    public static LocalDate makeLocalDate(String dateStr){
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e){
            return LocalDate.now();
        }
    }

    // 재고데이타 생성
    public static Inven buildInven(int skuId, int logisId, int partnerId,  int stockId, int locId, User user, String invenStatCd, LocalDateTime now) {
        return Inven.builder()
                .stockId(stockId)
                .skuId(skuId)
                .logisId(logisId)
                .partnerId(partnerId)
                .locId(locId)
                .invenStatCd(invenStatCd)
                .invenYmd(LocalDate.now())
                .creUser(user.getLoginId())
                .updUser(user.getLoginId())
                .creTm(now)
                .updTm(now)
                .build();
    }


    /* 검색시 기간체크 */
    public static void checkMaxDay(LocalDate startDate, LocalDate endDate, int maxDays){
        if(startDate != null && endDate != null){
            if(startDate.plusDays(maxDays).isBefore(endDate)){
                throw new CustomRuntimeException(ApiResultCode.FAIL, "조회 가능기간은 ["+maxDays+"]일 입니다.");
            }
        }
    }


    // 얇은 테두리 적용 헬퍼 메서드
    public static void applyThinBorders(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }

    // 굵은 테두리 적용 헬퍼 메서드
    public static void applyThickBorders(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, region, sheet);
    }


    /**
     * source → target으로 복사하되, null 값은 무시
     */
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * null인 프로퍼티 이름 배열 반환
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    /**
     * source → target으로 복사
     */
    public static void makeOditInfo(User jwtUser, Object target) {
        // 복사할 필드 이름
        String loginUserId = jwtUser.getLoginId();
        String[] oditFields = {"creUser", "updUser"}; // 예: 이 필드만 복사
        for (String fieldName : oditFields) {
            try {
                PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), fieldName);
                if (pd != null && pd.getWriteMethod() != null) {
                    pd.getWriteMethod().invoke(target, loginUserId);
                    log.info("Copied field '{}' with value '{}'", fieldName, loginUserId);
                } else {
                    log.warn("No write method for field '{}'", fieldName);
                }
            } catch (Exception e) {
                log.error("Failed to copy field '{}': {}", fieldName, e.getMessage(), e);
            }
        }
    }

    public static String getErrorLog(String tranMessage) {
        if (tranMessage.length() > 10000) {
            tranMessage = tranMessage.substring(0, 10000); // 너무크면 자른다.
        }
        return tranMessage;
    }
}