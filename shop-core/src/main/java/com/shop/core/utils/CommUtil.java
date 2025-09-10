package com.shop.core.utils;

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
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


}