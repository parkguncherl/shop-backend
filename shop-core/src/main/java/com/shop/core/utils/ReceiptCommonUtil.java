package com.shop.core.utils;
import org.springframework.stereotype.Component;

import com.shop.core.entity.PartnerPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.s3.S3Client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ReceiptCommonUtil {

    private static final Logger log = LoggerFactory.getLogger(ReceiptCommonUtil.class);

    private static S3Client s3Client;
    private static JdbcTemplate jdbcTemplate;
    private static String bucketName;

    private static final int MAX_WIDTH = 384;
    private static final Set<String> SUPPORTED_EXTENSIONS = new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));

    /**
     * ESC 코드 및 프린터 제어 명령어
     */
    public static final String ESC = "\u001B";  // ESC 문자
    public static final String GS = "\u001D";   // GS 문자
    public static final String INITIALIZE = ESC + "@";  // 프린터 초기화
    public static final String ALIGN_LEFT = ESC + "a" + "\u0000";  // 왼쪽 정렬
    public static final String ALIGN_CENTER = ESC + "a" + "\u0001";  // 가운데 정렬
    public static final String ALIGN_RIGHT = ESC + "a" + "\u0002";  // 오른쪽 정렬
    public static final String BOLD_ON = ESC + "E" + "\u0001";  // 굵은 글씨 켜기
    public static final String BOLD_OFF = ESC + "E" + "\u0000";  // 굵은 글씨 끄기
    public static final String PARTIAL_CUT = GS + "V" + "\u0001";  // 부분 컷팅
    public static final String LINE_FEED = "\n";  // 줄바꿈

    // 글자 크기 변경 명령어
    public static final String NORMAL_SIZE = GS + "!" + "\u0000";  // 기본 크기
    public static final String DOUBLE_HEIGHT = GS + "!" + "\u0001";  // 높이 2배
    public static final String DOUBLE_WIDTH = GS + "!" + "\u0010";  // 너비 2배
    public static final String DOUBLE_SIZE = GS + "!" + "\u0011";  // 높이, 너비 2배

    /**
     * 날짜 및 시간 포맷
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String getCurrentDate() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }


    /**
     * 이미지를 프린터 포맷으로 변환합니다.
     * @param image 변환할 이미지
     * @return 프린터 포맷으로 변환된 이미지 데이터
     */
    private static byte[] convertImageToPrinterFormat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteArrayOutputStream imageData = new ByteArrayOutputStream();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x += 8) {
                byte b = 0;
                for (int i = 0; i < 8; i++) {
                    if (x + i < width) {
                        int pixel = image.getRGB(x + i, y);
                        if ((pixel & 0xFF) < 128) {  // 픽셀이 검은색에 가까우면
                            b |= (128 >> i);
                        }
                    }
                }
                imageData.write(b);
            }
        }

        return imageData.toByteArray();
    }

    /**
     * 영수증 끝 부분 여백 및 컷팅 생성 공통
     * @param extraLineFeeds 추가할 줄바꿈 수
     * @return 생성된 영수증 끝 부분 문자열
     */
    public static String generateReceiptEnding(int extraLineFeeds) {
        StringBuilder ending = new StringBuilder();
        ending.append(INITIALIZE).append(ALIGN_CENTER);

        // 기본 줄바꿈 2개 + 추가 줄바꿈
        for (int i = 0; i < 2 + extraLineFeeds; i++) {
            ending.append(LINE_FEED);
        }

        ending.append(INITIALIZE).append(PARTIAL_CUT);
        return ending.toString();
    }

    /**
     * 화주 정보 푸터 공통
     * @param partnerPrint 화주 인쇄 정보
     * @return 생성된 화주 정보 푸터 문자열
     */
    public static String generatePartnerFooter(PartnerPrint partnerPrint) {
        StringBuilder footer = new StringBuilder();

        if (partnerPrint != null && "Y".equalsIgnoreCase(partnerPrint.getBottomYn())) {
            footer.append(INITIALIZE)
                    .append(ALIGN_CENTER)
                    .append(partnerPrint.getBottomNor())
                    .append(LINE_FEED);
        }

        return footer.toString();
    }

    /**
     * 문자열을 지정된 길이로 잘라내기
     * @param str 원본 문자열
     * @param length 잘라낼 길이
     * @return 잘라낸 문자열
     */
    public static String truncateString(String str, int length) {
        if (str == null || str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }

    /**
     * BigDecimal 값을 포맷팅
     * @param value BigDecimal 값
     * @return 포맷팅된 문자열
     */
    public static String formatBigDecimal(BigDecimal value) {
        return value != null ? String.format("%,.0f", value) : "0";
    }

    /**
     * 구분선 그리기
     * @param c 구분선에 사용할 문자
     * @param length 구분선의 길이
     * @return 생성된 구분선 문자열
     */
    public static String drawLine(char c, int length) {
        return INITIALIZE + ALIGN_CENTER + new String(new char[length]).replace('\0', c);
    }

    /**
     * 구분선 그리기2
     * @param c 구분선에 사용할 문자
     * @param length 구분선의 길이
     * @return 생성된 구분선 문자열
     */
    public static String drawLine2(char c, int length) {
        return INITIALIZE + new String(new char[length]).replace('\0', c);
    }
}
