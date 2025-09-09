package com.shop.core.enums;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * Description: API 결과코드 Entity
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */

public enum ApiResultCode {
    FIRST_LOGIN               (210,     "최초로그인 입니다. \n비밀번호를 본인이 직접 비밀번호를 설정해야 합니다.", "This is the first login. \nYou must set your password yourself."),
    OVER_180                  (211,     "비밀번호 변경180일 지났습니다. \n비밀번호를 수정하시겠습니까?", "Password change is required. \n180 days have passed since the last password change. Do you want to modify your password?"),
    IS_LOCK                   (230,     "해당계정은 로그인하신지 180일이 지나\n로그인 하실 수 없습니다. \n관리자에게 문의하세요. ", "You cannot log in because it has been 180 days since your last login. \nPlease contact the administrator."),
    EMAIL_ERROR               (231,     "이메일 전송에 실패했습니다.", "Failed to send an email."),
    FAIL_UNLOCK               (240,     "잠금해제가 되지 않았습니다.", "Failed to unlock."),
    COUNTRY_CODE_VALID        (240,     "국가코드는 2자리대문자로 입력하셔야 합니다.", "The country code must be two uppercase letters."),
    FAIL_UNUSE                (240,     "미사용계정입니다.", "Unused account."),
    FAIL_PASS_INIT            (250,     "비밀번호 초기화가 되지 않았습니다.", "Password initialization failed."),
    FAIL_DELETE_ME            (260,     "본인의 계정은 삭제 할 수 없습니다.", "You cannot delete your own account."),
    SUCCESS_CHANGE_PASSWORD   (270,     "비밀번호 변경 성공", "Password change successful"),
    SUCCESS_CHANGE_USER_INFO  (280,     "사용자 정보 변경 성공", "User information change successful"),
    DATA_NOT_FOUND            (404,     "데이터가 존재하지 않습니다.", "Data not found."),
    USER_NOT_FOUND            (404,     "사용자가 존재하지 않습니다.", "User not found."),

    NO_REQUIRED_VALUE         (490,     "필수값이 없습니다.", "Required value not provided."),
    UPPER_AUTH_INVALIE        (491,     "본인보다 상위권한자는 생성할 수 없습니다.", "Higher authority cannot be created."),
    DUPLICATE_VALUE           (491,     "이미 존재하는 값이 있습니다.", "Duplicate value already exists."),
    DUPLICATE_MENU            (491,     "이미 존재하는 메뉴(코드)", "Duplicate menu(code)"),
    DUPLICATE_URI             (491,     "이미 존재하는 메뉴URI가 있습니다.", "Duplicate menu URI already exists."),
    DUPLICATE_MENU_NM         (491,     "메뉴명은 중복될 수 없습니다.", "Menu name cannot be duplicated."),
    DUPLICATE_CODE_CD         (491,     "코드는 중복될 수 없습니다.", "Code name cannot be duplicated."),
    DUPLICATE_LOCATION         (491,     "창고,ZONE코드 그룹내 위치값은 중복될 수 없습니다.", "Code name cannot be duplicated."),
    BIG_MENU_CHECK            (493,     "대메뉴의 메뉴코드는 영문대문자 2자리 입니다.", "Main menu code should be 2 uppercase letters."),
    MENU_CODE_CHECK           (494,     "메뉴코드는 영문대문자와 숫자만 사용할 수 있습니다.", "Menu code should only contain uppercase letters and numbers."),
    CHECK_URI                 (495,     "하위메뉴에는 URI 정보가 반드시 필요합니다.", "Submenus must have URI information."),
    MENU_CAP_CHECK            (496,     "메뉴코드는 영문대문자(포", "Menu code should be 4 uppercase letters."),
    DUPLICATE_ID              (492,     "이미 존재하는 ID가 있습니다.", "Duplicate ID already exists."),
    BAD_REQUEST               (600,     "입력값이 유효하지 않습니다.", "Invalid input value."),
    NOT_FOUND_TOKEN           (990,     "인증토큰을 찾을 수 없습니다.", "Authentication token not found."),
    TOKEN_UNAVAILABLE         (991,     "인증토큰 값이 유효하지 않습니다.", "Invalid authentication token value."),
    TOKEN_NOT_FOUND           (991,     "로그아웃 되었습니다.", "Logged out."),
    ACCESS_TOKEN_EXPIRED      (992,     "인증토큰 값이 만료되었습니다.(Access 토큰 만", "Access token value has expired."),
    TOKEN_EXPIRED             (993,     "인증토큰 값이 만료되었습니다.(Access & Refresh 토큰 만", "Access and refresh tokens have expired."),
    NOT_AVAILABLE_ACCESS      (994,     "비정상적인 접근입니다.", "Unauthorized access."),
    NOT_FOUND_USER            (904,     "사용자 정보가 존재하지 않습니다.", "User information not found."),
    NOT_FOUND_CMPY            (904,     "거래처 정보가 존재하지 않습니다.", "Company information not found."),
    NOT_FOUND_CODE            (904,     "코드 정보가 존재하지 않습니다.", "Code information not found."),
    NOT_FOUND_CONTACT         (904,     "정의되지 않은 거래종류가 있습니다.", "Undefined transaction type."),
    NOT_FOUND_MENU            (904,     "메뉴 정보가 존재하지 않습니다.", "Menu information not found."),
    OTP_FAIL                  (919,     "OTP 실패", "OTP failed."),
    ENC_ERROR                 (920,     "암복호화 에러", "Encryption/decryption error."),
    NOT_MATCHED_USER          (921,     "일치하는 아이디가 존재하지 않습니다.", "No matching ID found."),
    NOT_MATCHED_OTP           (922,     "OTP번호 불일치", "OTP number does not match."),
    TIME_OUT_OTP              (923,     "OTP 입력시간 초과", "OTP input time exceeded."),
    NOT_MATCHED_NOW_PASS      (924,     "현재 비밀번호가 일치하지 않습니다.", "Current password does not match."),
    NOT_RE_PASS               (925,     "현재 비밀번호를 입력하지 않았습니다.", "Current password not entered."),
    NOT_MOD_PASS              (926,     "새 비밀번호를 입력하지 않았습니다.", "New password not entered."),
    NOT_RE_MOD_PASS           (927,     "새 비밀번호 확인을 입력하지 않았습니다.", "New password confirmation not entered."),
    NOT_MATCH_PASS            (928,     "새 비밀번호 와 비밀번호 확인이 일치하지 않습니다.", "New password and password confirmation do not match."),
    PASS_FORMAT_ERR           (929,     "비밀번호포맷확인(영문대문자,영문소문자,특수문자,숫자 포함 8자 이", "Password format error (must contain uppercase letters, lowercase letters, special characters, and numbers, at least 8 character"),
    NOT_LOGINID               (930,     "아이디를 입력하세요.", "Enter your ID."),
    NOT_PHONENO               (931,     "시스템에 등록된 연락처를 입력하세요.", "Enter the contact information registered in the system."),
    NOT_REG_LOGINID           (931,     "등록된 ID(이메일주", "The ID (email address is not registered with the selected country."),
    NOT_REG_PHONENO           (931,     "시스템에 등록된 연락처가 존재하지 않습니다.", "Contact information is not registered in the system."),
    NOT_MATCH_REG_PHONENO     (931,     "입력하신 연락처가 시스템에 등록된 연락처와 다릅니다.", "The entered contact information does not match the registered contact information."),
    NOT_FORM_PHONENO          (931,     "전화번호 형식이 올바르지 않습니다.", "Invalid phone number format."),
    FAIL_CHANGE_PASS          (932,     "패스워드 변경실패", "Password change failed."),
    FAIL_SEND_MAIL            (932,     "이메일 발송 실패", "Failed to send an email."),
    FAIL_SEND_AUTH_NUMBER     (933,     "이메일 인증번호 불일치", "Invalid email authentication number."),
    FAIL_SEND_SMS             (934,     "SMS 인증번호 전송 실패", "Failed to send an SMS authentication number."),
    FAIL_SEND_AUTH_SMS        (935,     "SMS 인증번호 불일치", "Invalid SMS authentication number."),
    FAIL_CREATE               (936,     "등록 실패", "Failed to register."),
    FAIL_UPDATE               (937,     "수정 실패", "Failed to update."),
    FAIL_UPDATE_UNIT_HIS      (937,     "부품이력 교환사유 수정에 실패하였습니다.", "Failed to update part history exchange reason."),
    FAIL_UPDATE_UNIT_POST     (937,     "신규 부품을 포스트로 연결 중 에러가 발생하였습니다.", "Error occurred during connection of new part to post."),
    FAIL_UPDATE_CLAIM         (937,     "클레임 수정에 실패하였습니다.", "Failed to update claim."),
    FAIL_SAVE                 (937,     "저장 실패", "Save failed."),
    FAIL_DELETE               (938,     "삭제 실패", "Deletion failed."),
    FAIL_UPLOAD_FILE          (939,     "업로드 파일 처리 중 에러가 발생하였습니다.", "Error occurred during file upload."),
    FAIL_UPLOAD_FILE_EXTSN    (940,     "허용된 확장자가 아닙니다.", "Invalid file extension."),
    FAIL_UPLOAD_FILE_MAXSIZE  (941,     "허용된 용량을 초과하였습니다.", "Exceeded maximum allowed file size."),
    FAIL_EXCEL_UPLOAD_NODATA  (945,     "업로드할 데이터가 없습니다.", "No data to upload."),
    FAIL_SEARCH_DATA          (945,     "조회가능한 데이터가 없습니다. 조회기간을 맞게 입력하세요", "No data available for the requested search. Please input the correct search period."),
    FAIL_EXCEL_UPLOAD_NOTMATCH(946,     "컬럼건수가 일치하지 않습니다.", "Column count does not match."),
    FAIL_EXCEL_UPLOAD         (943,     "확장자가 xlsx(대소문자 구분안", "Only Excel files with the extension xlsx can be uploaded."),
    NOT_CHANGE                (944,     "변경된 건이 없습니다.", "No changes were made."),
    SHORTAGE_INVEN            (945,     "재고가 부족합니다.", "Inven was shortage."),

    NOT_ALLOWED_FILE_MAX      (997,     "최대 허용 파일 사이즈가 초과되었습니다.", "Exceeded maximum allowed file size."),


    SUCCESS                   (200,  "SUCCESS"),
    LOGIN_FAIL_CNT            (220,  "로그인 5회 실패로 계정이 잠겼습니다.\n시스템 관리자에게 문의해 주시기 바랍니다. \n", "Your ID has been locked due to 5 failed login attempts.\n please contact your system administrator\n "),

    NO_REQUIRED_OPTION        (490,  "OPTION is required value"),
    NO_REQUIRED_VALUE2        (490,  "VALUE is required value"),

    FAIL                      (900,  "FAILURE"),
    NOT_REG_LOCK_USER         (931,  "계정이 잠겼으므로 비밀번호 찾기가 불가합니다. 시스템 관리자에게 문의해주시기 바랍니다.", "Your account is locked, so it is impossible to retrieve your password. Please contact your system administrator"),
    DB_ERROR                  (998,  "조회/수정시 오류가 발생하였습니다.", "Data Search or Modify error"),
    INTERNAL_SERVER_ERROR     (999,  "INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR"),
    ;
    private final int resultCode;
    private final String resultMessage;
    private final String resultEnMessage;

    ApiResultCode(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultEnMessage = null;
    }

    ApiResultCode(int resultCode, String resultMessage, String resultEnMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultEnMessage = resultEnMessage;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == Locale.ENGLISH) {
            return resultEnMessage != null ? resultEnMessage : resultMessage;
        }
        return resultMessage;
    }

    private static final Map<Integer, ApiResultCode> LOOKUP = new HashMap<Integer, ApiResultCode>();

    static {
        for (ApiResultCode elem : EnumSet.allOf(ApiResultCode.class)) {
            LOOKUP.put(elem.getResultCode(), elem);
        }
    }

    public static ApiResultCode get(int code) {
        return LOOKUP.get(code);
    }

    public static int getResultCode(int code) {
        return get(code).resultCode;
    }

    public static String getResultMessage(int code) {
        return get(code).resultMessage;
    }
}
