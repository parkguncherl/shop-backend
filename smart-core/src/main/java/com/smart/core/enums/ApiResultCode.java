package com.smart.core.enums;

import com.smart.core.utils.CommUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * Description : API 결과코드 Entity
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
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
    NOT_FOUND_ALERT_MODEL     (300,     "시뮬레이션 가능한 알람모델이 존재하지 않습니다.", "No simulation-ready alarm model found."),
    NOT_FOUND_PROFILE_DET     (300,     "시뮬레이션요청 정보와 실제 로그정보(100,1000,10000 로그정", "Simulation request information does not match actual log information (100, 1000, 10000 log informatio"),
    NOT_MATCH_PARAMS          (300,     "시뮬레이션 요청 변수정보가 로그정보의 변수정보와 일치하지 않습니다.", "Simulation request parameter information does not match log information."),
    DATA_NOT_FOUND            (404,     "데이터가 존재하지 않습니다.", "Data not found."),
    USER_NOT_FOUND            (404,     "사용자가 존재하지 않습니다.", "User not found."),

    NO_REQUIRED_VALUE         (490,     "필수값이 없습니다.", "Required value not provided."),
    MENU_ORDER_LIMIT          (490,     "메뉴의 순서는 10000 이하로 입력해 주세요.", "Menu order should be below 10000."),
    CODE_ORDER_LIMIT          (490,     "순서는 10000 이하로 입력해 주세요.", "Code order should be below 10000."),
    UPPER_AUTH_INVALIE        (491,     "본인보다 상위권한자는 생성할 수 없습니다.", "Higher authority cannot be created."),
    DUP_COUNTRY               (490,     "동일한 사업자와 국가로 등록된 CPO 정보가 존재합니다.", "CPO information registered with the same company and country already exists."),
    DUP_IOT_HUB               (490,     "동일한 IOT 허브 정보가 존재합니다.", "Duplicate IOT hub information."),
    DUP_CPO_NAME              (490,     "동일한 국가코드로 동일한 CPO명이 존재합니다.", "Duplicate CPO name with the same country code."),
    DUPLICATE_VALUE           (491,     "이미 존재하는 값이 있습니다.", "Duplicate value already exists."),
    DUPLICATE_MENU            (491,     "이미 존재하는 메뉴(코드)", "Duplicate menu(code)"),
    DUPLICATE_URI             (491,     "이미 존재하는 메뉴URI가 있습니다.", "Duplicate menu URI already exists."),
    DUPLICATE_MENU_NM         (491,     "메뉴명은 중복될 수 없습니다.", "Menu name cannot be duplicated."),
    DUPLICATE_POST_MODEL_NM   (491,     "충전포스트모델명은 중복될 수 없습니다.", "Duplicate charging post model name."),
    DUPLICATE_BANK_MODEL_NM   (491,     "파워뱅크모델명은 중복될 수 없습니다.", "Duplicate power bank model name."),
    DUPLICATE_PARAM_MODEL_NM  (491,     "변수모델명은 중복될 수 없습니다.", "Duplicate variable model name."),
    DUPLICATE_ERRCODE_MODEL_NM(491,   "고장코드 모델명은 중복될 수 없습니다.", "Duplicate error code model name."),
    DUPLICATE_ERRCODE_POS     (491,     "고장코드 위치정보(코드", "Duplicate error code position information(code"),
    DUPLICATE_PART_NO         (491,     "업체품번은 중복될 수 없습니다.", "Duplicate part number is not allowed."),
    DUPLICATE_PART_NO_KIND_TYPE(491,     "업체품번&부품종류구분은 중복될 수 없습니다.", "Duplicate part number and part type classification are not allowed."),
    DUPLICATE_KEFICO_NO       (491,     "케피코품번은 중복될 수 없습니다.", "Duplicate KEFICO part number is not allowed."),
    DUPLICATE_KEFICO_NO_KIND_TYPE(491,     "케피크품번&부품종류구분은 중복될 수 없습니다.", "Duplicate KEFICO part number and part type classification are not allowed."),
    DUPLICATE_ALERT_NM        (491,     "알람명은 중복될 수 없습니다.", "Duplicate alarm name is not allowed."),
    DUPLICATE_UNIT_SN         (491,     "부품 S/N은 중복될 수 없습니다.", "Duplicate part S/N is not allowed."),
    BIG_MENU_CHECK            (493,     "대메뉴의 메뉴코드는 영문대문자 2자리 입니다.", "Main menu code should be 2 uppercase letters."),
    MENU_CODE_CHECK           (494,     "메뉴코드는 영문대문자와 숫자만 사용할 수 있습니다.", "Menu code should only contain uppercase letters and numbers."),
    CHECK_URI                 (495,     "하위메뉴에는 URI 정보가 반드시 필요합니다.", "Submenus must have URI information."),
    MENU_CAP_CHECK            (496,     "메뉴코드는 영문대문자(포", "Menu code should be 4 uppercase letters."),
    DUPLICATE_ID              (492,     "이미 존재하는 ID가 있습니다.", "Duplicate ID already exists."),
    REG_COUNTRY_CODE          (492,     "국가코드는 한건이상 등록되어야 합니다.", "At least one country code must be registered."),
    REMAIN_STATION            (492,     "해당 CPO로 등록된 충전소가 존재합니다.", "There are charging stations registered with this CPO."),
    REMAIN_CHECKING_CHECK_REPORT (492,     "해당 점검 리포트 양식으로 등록된 점검 리포트가 존재합니다.", "There are inspection reports registered with this inspection report format."),
    REMAIN_POST_MODEL_PARAM   (492,     "해당 변수모델로 등록된 충전포스트모델이 존재합니다.", "There are charging post models registered with this variable model."),
    REMAIN_POST_MODEL_ERRCODE (492,     "해당 고장코드모델로 등록된 충전포스트모델이 존재합니다.", "There are charging post models registered with this error code model."),
    REMAIN_POST_MODEL_GROUP   (492,     "해당 부품그룹으로 등록된 충전포스트모델이 존재합니다.", "There are charging post models registered with this part group."),
    REMAIN_BANK_MODEL_GROUP   (492,     "해당 부품그룹으로 등록된 파워뱅크모델이 존재합니다.", "There are power bank models registered with this part group."),
    REMAIN_UNIT_GROUP_MODEL   (492,     "해당 부품모델로 등록된 부품그룹이 존재합니다.", "There are part groups registered with this part model."),
    REMAIN_ALERT_MODEL_PARAM  (492,     "해당 변수모델로 등록된 알람모델이 존재합니다.", "There are alarm models registered with this variable model."),
    REMAIN_ALERT_GROUP_MODEL  (492,     "해당 알람모델로 등록된 알람그룹이 존재합니다.", "There are alarm groups registered with this alarm model."),
    REMAIN_POST_POST_MODEL    (492,     "해당 충전포스트모델로 등록된 충전포스트가 존재합니다.", "There are charging posts registered with this charging post model."),
    REMAIN_POST_ALERT_GROUP   (492,     "해당 알람그룹으로 등록된 충전포스트가 존재합니다.", "There are charging posts registered with this alarm group."),
    REMAIN_BANK_BANK_MODEL    (492,     "해당 파워뱅크모델로 등록된 파워뱅크가 존재합니다.", "There are power banks registered with this power bank model."),
    REMAIN_UNIT_UNIT_MODEL    (492,     "해당 부품모델로 등록된 부품이 존재합니다.", "There are parts registered with this part model."),
    POI_ALERT_NAME_NOTEXIST   (492,     "OUTPUT SHEET 타이틀 2번째 값은 알람명이어야 합니다.", "The second value of the OUTPUT SHEET title should be an alarm name."),
    POI_RSLT_NOTEXIST         (492,     "OUTPUT SHEET 타이틀 3번째 값은 결과값이어야 합니다.", "The third value of the OUTPUT SHEET title should be a result value."),
    POI_FILE_PROBLEM          (492,     "엑셀 파일에 문제가 있습니다.", "ALERT MODEL EXCEL FILE HAVE A PROBLEM"),
    POI_OUTPUT_NOTEXIST       (492,     "OUTPUT SHEET 가 존재하지 않습니다.", "The OUTPUT SHEET does not exist."),
    POI_OUTPUT_ONLY_TITLE     (492,     "시뮬레이션 OUTPUT 대상건이 한건도 존재하지 않습니다.", "No data exists for the simulation OUTPUT."),
    BATCH_SUCCESS             (500,     "배치 성공", "Batch successful"),
    BATCH_FAIL                (500,     "배치 실패", "Batch failed"),
    FAIL_NOT_POST             (900,     "선택된 POST가 없습니다.", "No selected POST."),
    FAIL_TEL_DIGITS           (900,     "전화번호는 10자리 혹은 11자리 숫자만 가능합니다.", "Phone number should be 10 or 11 digits."),
    BAD_REQUEST               (600,     "입력값이 유효하지 않습니다.", "Invalid input value."),
    BAD_REQUEST_UNIT_001      (610,     "번째 줄, 위치(구", "Invalid value for location(classificatio"),
    BAD_REQUEST_UNIT_002      (610,     "번째 줄, 케피코품번 값이 유효하지 않습니다. 문자열로 입력하세요.", "Invalid value for KEFICO part number. Enter it as a string."),
    BAD_REQUEST_UNIT_003      (610,     "번째 줄, S/N 값이 유효하지 않습니다. 문자열로 입력하세요.", "Invalid value for S/N. Enter it as a string."),
    BAD_REQUEST_UNIT_004      (610,     "번째 줄, 필수값 위치(구", "Missing required value for location(classificatio"),
    BAD_REQUEST_UNIT_005      (610,     "번째 줄, 위치(구", "Invalid location(classificatio"),
    BAD_REQUEST_UNIT_006      (610,     "번째 줄, 필수값 케피코품번이 없습니다.", "Missing required value for KEFICO part number."),
    BAD_REQUEST_UNIT_007      (610,     "번째 줄, 케피코품번에 해당되는 부품 모델을 찾을 수 없습니다.", "Cannot find a part model for the specified KEFICO part number."),
    BAD_REQUEST_UNIT_008      (610,     "번째 줄, 필수값 S/N이 없습니다.", "Missing required value for S/N."),
    BAD_REQUEST_UNIT_009      (610,     "번째 줄, 이미 존재하는 부품 S/N입니다.", "The provided part S/N already exists."),
    BAD_VALUE                 (600,     "잘 못 된 값입니다.", "Invalid value."),
    DELETE_NOT_AVALIABLE      (601,     "데이터 삭제가 불가능합니다.", "Data deletion is not available."),
    NOT_FOUND_TOKEN           (990,     "인증토큰을 찾을 수 없습니다.", "Authentication token not found."),
    TOKEN_UNAVAILABLE         (991,     "인증토큰 값이 유효하지 않습니다.", "Invalid authentication token value."),
    TOKEN_NOT_FOUND           (991,     "로그아웃 되었습니다.", "Logged out."),
    ACCESS_TOKEN_EXPIRED      (992,     "인증토큰 값이 만료되었습니다.(Access 토큰 만", "Access token value has expired."),
    TOKEN_EXPIRED             (993,     "인증토큰 값이 만료되었습니다.(Access & Refresh 토큰 만", "Access and refresh tokens have expired."),
    NOT_AVAILABLE_ACCESS      (994,     "비정상적인 접근입니다.", "Unauthorized access."),
    NOT_FOUND_USER            (904,     "사용자 정보가 존재하지 않습니다.", "User information not found."),
    NOT_FOUND_CMPY            (904,     "거래처 정보가 존재하지 않습니다.", "Company information not found."),
    NOT_FOUND_CODE            (904,     "코드 정보가 존재하지 않습니다.", "Code information not found."),
    NOT_FOUND_KEY             (904,     "포스트에 등록된 IOT ACCESS KEY가 없습니다.", "No registered IOT ACCESS KEY in the post."),
    NOT_FOUND_POST_CPO        (904,     "포스트에 CPO가 연결이 안되어 있습니다.", "The post is not connected to any CPO."),
    NOT_FOUND_IOT             (904,     "CPO에 IOT 정보가 없습니다.", "No IOT information found in the CPO."),
    NOT_FOUND_CONTACT         (904,     "정의되지 않은 거래종류가 있습니다.", "Undefined transaction type."),
    NOT_FOUND_MENU            (904,     "메뉴 정보가 존재하지 않습니다.", "Menu information not found."),
    NOT_FOUND_CHECK_REPORT    (904,     "점검 리포트 양식 정보가 존재하지 않습니다.", "Inspection report format not found."),
    NOT_FOUND_CHECK_REPORT_DET(904,     "점검 리포트 양식 상세 정보가 존재하지 않습니다.", "Inspection report format details not found."),
    NOT_FOUND_POST_MODEL      (904,     "충전기포스트 모델 정보가 존재하지 않습니다.", "Charging post model information not found."),
    NOT_FOUND_BANK_MODEL      (904,     "파워뱅크 모델 정보가 존재하지 않습니다.", "Power bank model information not found."),
    NOT_FOUND_UNIT_MODEL      (904,     "부품 모델 정보가 존재하지 않습니다.", "Part model information not found."),
    DUP_MODEL_GROUP_NM        (904,     "모델그룹명이 중복되었습니다.", "Duplicate model group name."),
    DUP_ALERT_GROUP_NM        (904,     "알람 그룹명이 중복되었습니다.", "Duplicate alert group name."),
    NOT_FOUND_UNIT_GROUP      (904,     "부품 그룹 정보가 존재하지 않습니다.", "Part group information not found."),
    NOT_FOUND_UNIT_GROUP_MODEL(904,     "부품 그룹 모델 정보가 존재하지 않습니다.", "Part group model information not found."),
    NOT_FOUND_TB_ALERT_MODEL  (904,     "알람 모델 정보가 존재하지 않습니다.", "Alarm model information not found."),
    NOT_FOUND_ALERT_GROUP     (904,     "알람 그룹 정보가 존재하지 않습니다.", "Alarm group information not found."),
    NOT_FOUND_ALERT_GROUP_MODEL(904,     "알람 그룹 모델 정보가 존재하지 않습니다.", "Alarm group model information not found."),
    NOT_FOUND_PARAM_MODEL     (904,     "변수 모델 정보가 존재하지 않습니다.", "Variable model information not found."),
    NOT_FOUND_PARAM_MODEL_DET (904,     "변수 모델 상세 정보가 존재하지 않습니다.", "Variable model details not found."),
    NOT_FOUND_ERRCODE_MODEL   (904,     "고장코드 모델 정보가 존재하지 않습니다.", "Error code model information not found."),
    NOT_FOUND_ERRCODE         (904,     "고장코드 정보가 존재하지 않습니다.", "Error code information not found."),
    NOT_FOUND_CHECK           (904,     "점검리포트 정보가 존재하지 않습니다.", "Inspection report information not found."),
    NOT_FOUND_CHECK_CNTN      (904,     "점검리포트 내용 정보가 존재하지 않습니다.", "Inspection report content information not found."),
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
    NOT_COUNTRY_CODE          (931,     "국가코드를 선택하세요.", "Select the country code."),
    NOT_REG_LOGINID           (931,     "해당국가로 등록된 ID(이메일주", "The ID (email address is not registered with the selected country."),
    NOT_REG_PHONENO           (931,     "시스템에 등록된 연락처가 존재하지 않습니다.", "Contact information is not registered in the system."),
    NOT_MATCH_REG_PHONENO     (931,     "입력하신 연락처가 시스템에 등록된 연락처와 다릅니다.", "The entered contact information does not match the registered contact information."),
    NOT_FORM_PHONENO          (931,     "전화번호 형식이 올바르지 않습니다.", "Invalid phone number format."),
    FAIL_CHANGE_PASS          (932,     "패스워드 변경실패", "Password change failed."),
    FAIL_SEND_MAIL            (932,     "이메일 발송 실패", "Failed to send an email."),
    FAIL_SEND_AUTH_NUMBER     (933,     "이메일 인증번호 불일치", "Invalid email authentication number."),
    FAIL_SEND_SMS             (934,     "SMS 인증번호 전송 실패", "Failed to send an SMS authentication number."),
    FAIL_SEND_AUTH_SMS        (935,     "SMS 인증번호 불일치", "Invalid SMS authentication number."),
    FAIL_CREATE               (936,     "등록 실패", "Failed to register."),
    COUNTRY_CODE_IOT_INFO     (936,     "국가코드에 등록된 접속정보를 겨져오지 못했습니다.", "Failed to retrieve connection information registered with the country code."),
    FAIL_CREATE_CLAIM         (936,     "클레임 등록에 실패하였습니다.", "Failed to register claim."),
    FAIL_CREATE_SHEET         (936,     "시트명이 올바르지 않습니다.", "Invalid sheet name."),
    FAIL_CREATE_POST_BANK     (936,     "충전포스트 - 파워뱅크 연결 중 오류가 발생했습니다.", "Error occurred during charging post - power bank connection."),
    FAIL_CREATE_STATION       (936,    "충전소 생성 중 오류가 발생했습니다.", "Error occurred during charging station creation."),
    FAIL_CREATE_STATION_POST  (936,    "충전소 - 포스트 연결 중 오류가 발생했습니다.", "Error occurred during charging station - post connection."),
    FAIL_CREATE_STATION_BANK  (936,    "충전소 - 파워뱅크 연결 중 오류가 발생했습니다.", "Error occurred during charging station - power bank connection."),
    FAIL_CREATE_STATION_USER  (936,    "충전소 - 담당자 연결 중 오류가 발생했습니다.", "Error occurred during charging station - user connection."),
    FAIL_CREATE_POST_UNIT     (936,    "충전포스트 등록 / 부품 생성 중 오류가 발생했습니다.", "Error occurred during charging post registration / part creation."),
    FAIL_CREATE_POST_HMI      (936,    "충전포스트의 HMI 맥어드레스가 존재하지 않습니다.", "The HMI MAC address of the charging post does not exist."),
    FAIL_CREATE_POST          (936,     "충전포스트 생성 중 오류가 발생했습니다.", "Error occurred during charging post creation."),
    FAIL_CREATE_UNIT_HIS      (936,     "부품 이력 생성시 오류가 발생하였습니다.", "Error occurred during part history creation."),
    FAIL_CREATE_BANK          (936,     "파워뱅크 생성 중 오류가 발생했습니다.", "Error occurred during power bank creation."),
    FAIL_UPDATE               (937,     "수정 실패", "Failed to update."),
    FAIL_UPDATE_UNIT_HIS      (937,     "부품이력 교환사유 수정에 실패하였습니다.", "Failed to update part history exchange reason."),
    FAIL_UPDATE_UNIT_POST     (937,     "신규 부품을 포스트로 연결 중 에러가 발생하였습니다.", "Error occurred during connection of new part to post."),
    FAIL_UPDATE_CLAIM         (937,     "클레임 수정에 실패하였습니다.", "Failed to update claim."),
    FAIL_UPDATE_STATION       (937,     "충전소 수정 중 오류가 발생했습니다.", "Error occurred during charging station update."),
    FAIL_UPDATE_STATION_POST  (937,     "충전소 - 포스트 연결 중 오류가 발생했습니다.", "Error occurred during charging station - post connection update."),
    FAIL_UPDATE_STATION_BANK  (937,     "충전소 - 파워뱅크 연결 중 오류가 발생했습니다.", "Error occurred during charging station - power bank connection update."),
    FAIL_UPDATE_STATION_USER  (937,     "충전소 - 담당자 수정 중 오류가 발생했습니다.", "Error occurred during charging station - user update."),
    FAIL_UPDATE_POST          (937,     "충전포스트 수정 중 오류가 발생했습니다.", "Error occurred during charging post update."),
    FAIL_UPDATE_POST_UNIT     (937,     "충전포스트 수정 / 부품 상태 변경 중 오류가 발생했습니다.", "Error occurred during charging post update / part status change."),
    FAIL_UPDATE_BANK          (937,     "파워뱅크 수정 중 오류가 발생했습니다.", "Error occurred during power bank update."),
    FAIL_UPDATE_BANK_UNIT     (937,     "파워뱅크 수정 / 부품 상태 변경 중 오류가 발생했습니다.", "Error occurred during power bank update / part status change."),
    FAIL_SAVE                 (937,     "저장 실패", "Save failed."),
    FAIL_SAVE_POST_BANK       (937,     "기 등록된 연결입니다.", "Connection is already registered."),
    FAIL_DELETE               (938,     "삭제 실패", "Deletion failed."),
    FAIL_DELETE_UNIT_HIS      (938,     "부품 이력 이관 중 오류가 발생하였습니다.", "Error occurred during part history transfer."),
    FAIL_DELETE_CLAIM         (938,     "잘 못 된 클레임입니다.", "Invalid claim."),
    FAIL_DELETE_STATION_POST  (938,     "충전소 - 포스트 삭제 중 오류가 발생했습니다.", "Error occurred during charging station - post deletion."),
    FAIL_DELETE_STATION_BANK  (938,     "충전소 - 파워뱅크 삭제 중 오류가 발생했습니다.", "Error occurred during charging station - power bank deletion."),
    FAIL_DELETE_UNIT          (938,     "부품 삭제 중 오류가 발생했습니다.", "Error occurred during part deletion."),
    FAIL_DELETE_POST          (938,     "충전포스트 삭제 중 오류가 발생했습니다.", "Error occurred during charging post deletion."),
    FAIL_DELETE_POST_UNIT     (938,     "충전포스트 삭제 / 부품 상태 변경 중 오류가 발생했습니다.", "Error occurred during charging post deletion / part status change."),
    FAIL_DELETE_BANK          (938,     "파워뱅크 삭제 중 오류가 발생했습니다.", "Error occurred during power bank deletion."),
    FAIL_DELETE_BANK_UNIT     (938,     "파워뱅크 삭제 / 부품 상태 변경 중 오류가 발생했습니다.", "Error occurred during power bank deletion / part status change."),
    FAIL_UPLOAD_FILE          (939,     "업로드 파일 처리 중 에러가 발생하였습니다.", "Error occurred during file upload."),
    FAIL_UPLOAD_FILE_EXTSN    (940,     "허용된 확장자가 아닙니다.", "Invalid file extension."),
    FAIL_UPLOAD_FILE_MAXSIZE  (941,     "허용된 용량을 초과하였습니다.", "Exceeded maximum allowed file size."),
    FAIL_UPLOAD_FILE_NOTALLOW (942,     "표준엑셀파일에는 OUTPUT SHEET 와 등록된 interval (IN_100ms,IN_1s,IN_10) 쉬트가 존재해야 합니다.", "Standard Excel files must have both OUTPUT SHEET and registered interval (IN_100ms, IN_1s, IN_10s)"),
    FAIL_UPLOAD_FILE_NOTEMPTY (942,     "등록된 interval (IN_100ms,IN_1s,IN_10", "The interval (IN_100ms, IN_1s, IN_10s  SHEET should not have any data except for the title (first ro"),
    FAIL_EXCEL_UPLOAD         (943,     "확장자가 xlsx(대소문자 구분안", "Only Excel files with the extension xlsx can be uploaded."),
    FAIL_EXCEL_UPLOAD_NODATA  (945,     "업로드할 데이터가 없습니다.", "No data to upload."),
    FAIL_SEARCH_DATA          (945,     "조회가능한 데이터가 없습니다. 조회기간을 맞게 입력하세요", "No data available for the requested search. Please input the correct search period."),
    FAIL_EXCEL_UPLOAD_NOTMATCH(946,     "컬럼건수가 일치하지 않습니다.", "Column count does not match."),
    NOT_CHANGE                (944,     "변경된 건이 없습니다.", "No changes were made."),
    FAIL_SEND_REMOTE_CTRL     (945,     "역방향 제어문 전송에 실패하였습니다.", "Failed to send a reverse control command."),
    FAIL_AZURE_FILE_DELETE    (996,     "Azure 파일 삭제 중 오류가 발생했습니다.", "Error occurred during Azure file deletion."),
    NOT_ALLOWED_FILE_MAX      (997,     "최대 허용 파일 사이즈가 초과되었습니다.", "Exceeded maximum allowed file size."),


    SUCCESS                   (200,  "SUCCESS"),
    LOGIN_FAIL_CNT            (220,  "로그인 5회 실패로 계정이 잠겼습니다.\n시스템 관리자에게 문의해 주시기 바랍니다. \n(blueplug-service@hyundai-kefico.com)", "Your ID has been locked due to 5 failed login attempts.\n please contact your system administrator\n (blueplug-service@hyundai-kefico.com)"),

    NO_REQUIRED_OPTION        (490,  "OPTION is required value"),
    NO_REQUIRED_VALUE2        (490,  "VALUE is required value"),

    FAIL                      (900,  "FAILURE"),
    NOT_REG_LOCK_USER         (931,  "계정이 잠겼으므로 비밀번호 찾기가 불가합니다. 시스템 관리자에게 문의해주시기 바랍니다.(blueplug-service@hyundai-kefico.com)", "Your account is locked, so it is impossible to retrieve your password. Please contact your system administrator"),
    FAIL_CREATE_IOT_DPS       (936,  "post - Azure IOT registDevice4ProvisioningDeviceOnDPS error"),
    FAIL_CREATE_IOT_HUB       (936,  "cpo iotHubInfo is Empty"),
    FAIL_DELETE_IOT_DPS       (938,  "Charge Post - Azure IoT deleteDevice4ProvisioningServiceOnDPS (DPS ) ERROR."),

    DUP_CONTAINER_INFO        (490,  "SAME AZURE CONTAINER INFO EXISTS"),
    DB_ERROR                  (998,  "조회/수정시 오류가 발생하였습니다.", "Data Search or Modify error"),
    INTERNAL_SERVER_ERROR     (999,  "INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR"),
    NOT_SELECTED              (492,  "No items selected"),

    NOT_MATCH_INTERVAL        (936,  "There are no graph variables in the profile"),
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
