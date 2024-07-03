package com.smart.core.utils;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.*;
import com.smart.core.enums.GlobalConst;
import com.smart.core.enums.MailType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description : 메일 유틸리티
 * Date : 2023/02/10 15:10 PM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Slf4j
@Component
public class MailUtil {

    @Value("${azure.communication.services.connectionString}")
    private String connectionString;

    @Value("${azure.communication.services.domain.mailFrom}")
    private String mailFrom;

    @Value("${cors.endpoint.url}")
    private String corsUrls;
    @Value("KR")
    private String myCountry;

    /**
     * 메일 발송
     *
     * @param gTitle
     * @param recipientEmail
     * @param gRecipientDisplayName
     * @return
     */
    public void sendMail(GlobalConst gTitle, String recipientEmail, GlobalConst gRecipientDisplayName, String content, String languageCode) {
        String title = gTitle.getCode();
        String recipientDisplayName = gRecipientDisplayName.getCode();
        if(StringUtils.isEmpty(languageCode) || !StringUtils.equals(myCountry,languageCode)){
            title = gTitle.getMessage();
            recipientDisplayName = gRecipientDisplayName.getMessage();
        }

        EmailClient emailClient = new EmailClientBuilder().connectionString(connectionString).buildClient();
        EmailContent emailContent = new EmailContent(title).setHtml(content);

        List<EmailAddress> emailAddress = new ArrayList<>();
        emailAddress.add(new EmailAddress(recipientEmail).setDisplayName(recipientDisplayName));

        EmailRecipients emailRecipients = new EmailRecipients(emailAddress);
        EmailMessage emailMessage = new EmailMessage(mailFrom, emailContent).setRecipients(emailRecipients);

        try {
            SendEmailResult sendEmailResult = emailClient.send(emailMessage);
            String messageId = sendEmailResult.getMessageId();

            if (!messageId.isEmpty() && messageId != null) {
                log.debug("Email sent, MessageId = {}", messageId);
            } else {
                log.debug("Failed to send email.");
                return;
            }

            long waitTime = 120 * 1000;
            boolean timeout = true;
            while (waitTime > 0) {
                SendStatusResult sendStatus = emailClient.getSendStatus(messageId);
                log.debug("Send mail status for MessageId : <{}>, Status: [{}]", messageId, sendStatus.getStatus());

                if (!sendStatus.getStatus().toString().toLowerCase().equals(SendStatus.QUEUED.toString())) {
                    timeout = false;
                    break;
                }
                Thread.sleep(100);
                waitTime = waitTime - 100;
            }

            if (timeout) {
                log.debug("Looks like we timed out for email");
            }
        } catch (Exception ex) {
            log.error("Error in sending email, {}", ex);
        }

        log.debug("END to send email.============");

    }

    /**
     * 메일 보내기 휴면예고 제외
     */
    public String makeEmailCntt(MailType mailType, String mailAddr, String otpNo, String languageCode) {
        return makeEmailCntt(mailType, mailAddr, otpNo, null, languageCode);
    }

    /**
     * 메일 보내기 휴면,휴면예고만
     */
    public String makeEmailCnttForExp(MailType mailType, String mailAddr, String inDate, String languageCode) {
        return makeEmailCntt(mailType, mailAddr, null, inDate, languageCode);
    }

    /**
     * 이메일 내용 만들기 mailType 1 : 계정 생성 2 : 로그인 (2차 인증) 3 : 계정 삭제 4 : 휴면 계정 1달전 예고 5 : 휴면 잠김처리
     */
    private String makeEmailCntt(MailType mailType, String mailAddr, String otpNo, String inDate, String languageCode) {
        String stdDate = CommUtil.getNowDate("yyyy-MM-dd");

        if(StringUtils.isEmpty(languageCode)){
            languageCode = myCountry;
        }

        if (MailType.PRE_EXP.equals(mailType) && StringUtils.isNotEmpty(inDate)) {
            stdDate = inDate;
        }

        String hostUrl = "http://ecms.evblueplug.com";
        if (corsUrls.indexOf("localhost") >= 0) {
            hostUrl = "http://dev-ecms.evblueplug.com";
        } else if (corsUrls.indexOf("dev-ecms") >= 0) {
            hostUrl = "http://dev-ecms.evblueplug.com";
        }

        String logoHtm = "<img src='" + hostUrl + "/_next/static/media/emailLogo.1e6d956e.png' alt='로고' style='display: inline-block; width: 238px; height: 21px;'>";
        if (mailAddr.toLowerCase().indexOf("gmail") >= 0) {
            logoHtm = "<strong style='font-size: 28px; letter-spacing: -1px; font-weight: 700; color: #ffffff;'>BLUE PLUG</strong>";
            logoHtm += "<span style='margin-left: 5px; font-size: 13px; color: #ffffff;'>E-Mail Service</span>";
        }

        StringBuffer stringbuffer = new StringBuffer();

        stringbuffer.append("<html>");
        stringbuffer.append("<head>");
        stringbuffer.append("    <meta charset='UTF-8'>");
        stringbuffer.append("    <meta http-equiv='X-UA-Compatible' content='IE=edge'>");
        stringbuffer.append("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        stringbuffer.append("    <title>BLUE PLUG EMAIL</title>");
        stringbuffer.append("</head>");
        stringbuffer.append("<body>");
        stringbuffer.append("<table border=0 cellpadding=0 cellspacing=0 style='width: 100%; border-radius: 10px; max-width: 600px; padding: 25px 25px 25px 25px; background-color: #091b47;'>");
        stringbuffer.append("    <tbody>");
        stringbuffer.append("        <tr>");
        stringbuffer.append("            <td style='padding-bottom: 10px; vertical-align: middle;'>");
        stringbuffer.append("            " + logoHtm + "");
        stringbuffer.append("             </td>");
        stringbuffer.append("        </tr>");
        stringbuffer.append("        <tr>");
        stringbuffer.append("            <td>");
        stringbuffer.append("                <table border=0 cellpadding=0 cellspacing=0 style='width: 600px; padding: 25px 15px 25px 15px; border-radius: 5px; background-color: #ffffff;'>");
        stringbuffer.append("                    <tbody>");
        stringbuffer.append("                        <tr>");
        stringbuffer.append("                            <td style='font-family: dotum, 돋움; font-size: 13px; color: black;'>");
        if (MailType.CREATE_ID.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                안녕하세요.<br/>");
                stringbuffer.append("                                블루플러그를 이용해 주셔서 감사합니다.");
                stringbuffer.append("                                관리자 계정 등록이 완료 되었습니다.<br/>");
                stringbuffer.append("                                최초 로그인 시에는 아래의 임시 비밀번호로 로그인 하신 후,<br/>");
                stringbuffer.append("                                사용하실 비밀번호로 변경해 주시기 바랍니다.<br/>");
            } else {
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/><br/>");
                stringbuffer.append("                                Your Administrator ID registration is complete.<br/><br/>");
                stringbuffer.append("                                When you log in for the first time, please log in with the temporary password below, Please change the password to the one you want to use.<br/><br/>");
            }
        } else if (MailType.OTP_2FACTOR.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                안녕하세요.<br/>");
                stringbuffer.append("                                블루플러그를 이용해 주셔서 감사합니다.");
                stringbuffer.append("                                <br/>");
                stringbuffer.append("                                관리자웹 로그인을 위한 인증번호를 안내드립니다.");
            } else {
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/><br/>");
                stringbuffer.append("                                Here is the authentication number for the administrator login.<br/><br/>");
            }
        } else if (MailType.DEL_ID.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                안녕하세요.<br/>");
                stringbuffer.append("                                블루플러그를 이용해 주셔서 감사합니다.<br/>");
                stringbuffer.append("                                관리자 계정 삭제가 완료되었습니다.<br/>");
                stringbuffer.append("                                그동안 블루플러그 서비스를 이용해 주셔서 감사합니다.<br/>");
                stringbuffer.append("                                더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.<br/>");
            } else {
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/><br/>");
                stringbuffer.append("                                Your administrator ID deletion is complete.<br/><br/>");
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/>");
                stringbuffer.append("                                We will always do our best to provide more convenient service.<br/><br/>");
            }
        } else if (MailType.PRE_EXP.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                안녕하세요.<br/>");
                stringbuffer.append("                                블루플러그를 이용해 주셔서 감사합니다.<br/>");
                stringbuffer.append("                                장기간 서비스 미이용으로 관리자 계정이 휴면계정으로 전환될 예정입니다.<br/>");
                stringbuffer.append("                                휴면 처리를 원하지 않으시는 경우, 전환 예정일 이전에 로그인 하여 주시기 바랍니다.<br/>");
            } else {
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/><br/>");
                stringbuffer.append("                                Your administrator ID will be converted to a dormant ID due to long-term non-use of the service.<br/><br/>");
                stringbuffer.append("                                If you don't want to be dormant, please log in before the scheduled date of conversion.<br/>");
            }
        } else if (MailType.EXP_FINISH.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                안녕하세요.<br/>");
                stringbuffer.append("                                블루플러그를 이용해 주셔서 감사합니다.<br/>");
                stringbuffer.append("                                180일 간 서비스 미이용으로 관리자 계정이 휴면계정으로 전환되었습니다.<br/><br/>");
                stringbuffer.append("                                본 서비스 이용을 원하시면, 시스템 관리자에게 문의해 주시기 바랍니다. (" + GlobalConst.REP_EMAIL.getCode() + ")<br/><br/>");
            } else {
                stringbuffer.append("                                Thank you for using BLUEPLUG.<br/><br/>");
                stringbuffer.append("                                Your administrator ID has been converted to a dormant ID due to long-term(180 days) non-use of the service.<br/><br/>");
                stringbuffer.append("                                If you want to use this service, please contact your system administrator.(" + GlobalConst.REP_EMAIL.getCode() + ")<br/><br/>");

            }
        }
        stringbuffer.append("                                <br/>");
        stringbuffer.append("                                <br/>");
        stringbuffer.append("                            </td>");
        stringbuffer.append("                        </tr>");
        stringbuffer.append("                        <tr>");
        stringbuffer.append("                            <td style='width: 100%; padding: 25px 25px 25px 25px; border-radius: 5px; background-color: #f1f1f1; font-family: 돋움, dotum; font-size: 18px; font-weight: bold; color: black;'>");
        if (MailType.CREATE_ID.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                아이디 : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                임시 비밀번호 : " + otpNo + "<br/><br/>");
            } else {
                stringbuffer.append("                                ID : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                Temporary password : " + otpNo + "<br/><br/>");
            }
        } else if (MailType.OTP_2FACTOR.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                인증번호 : " + otpNo + "<br/><br/>");
            } else {
                stringbuffer.append("                                Authentication number : " + otpNo + "<br/><br/>");
            }
        } else if (MailType.DEL_ID.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                아이디 : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                계정삭제일 : " + stdDate + "<br/>");
            } else {
                stringbuffer.append("                                ID : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                Date of deletion : " + stdDate + "<br/><br/>");
            }
        } else if (MailType.PRE_EXP.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                아이디 : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                휴면전환예정일 : " + stdDate + "<br/><br/>");
            } else {
                stringbuffer.append("                                ID : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                Scheduled date of conversion : " + stdDate + "<br/><br/>");
            }
        } else if (MailType.EXP_FINISH.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                아이디 : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                휴면전환일 : " + stdDate + "<br/><br/>");
            } else {
                stringbuffer.append("                                ID : " + mailAddr + "<br/><br/>");
                stringbuffer.append("                                Date of conversion : " + stdDate + "<br/><br/>");
            }
        }
        stringbuffer.append("                            </td>");
        stringbuffer.append("                        </tr>");
        stringbuffer.append("                        <tr>");
        stringbuffer.append("                            <td style='font-family: dotum, 돋움; font-size: 13px;'>");
        stringbuffer.append("                                <br/>");
        stringbuffer.append("                                <br/>");
        if (MailType.OTP_2FACTOR.equals(mailType)) {
            if(StringUtils.equals(myCountry, languageCode)) {
                stringbuffer.append("                                로그인 화면에서 인증번호를 올바르게 입력해 주세요.<br/>");
                stringbuffer.append("                                ※ 인증번호는 1회성으로 3분 동안만 유효합니다.<br/><br/>");
            } else {
                stringbuffer.append("                                Please enter the correct authentication number on the login page.<br/>");
                stringbuffer.append("                                ※ The authentication number is only valid for 3 minutes for one-time use.<br/><br/>");
            }
        }
        if(StringUtils.equals(myCountry, languageCode)) {
            stringbuffer.append("                                감사합니다.<br/><br/>");
        } else {
            stringbuffer.append("                                Thank you.<br/><br/>");
        }
        stringbuffer.append("                            </td>");
        stringbuffer.append("                        </tr>");
        stringbuffer.append("                    </tbody>");
        stringbuffer.append("                </table>");
        stringbuffer.append("            </td>");
        stringbuffer.append("        </tr>");
        stringbuffer.append("        <tr>");
        stringbuffer.append("            <td style='padding-left: 10px; font-family: dotum, 돋움; font-size: 13px; color: #8896b7;'>");
        stringbuffer.append("                <br/>");
        if(StringUtils.equals(myCountry, languageCode)) {
            stringbuffer.append("                본메일은 발신 전용이므로 회신되지 않습니다.<br/>");
            stringbuffer.append("                문의사항은 고객센터( <a href='mailto:" + GlobalConst.REP_EMAIL.getCode() + "' style='font-family: dotum, 돋움; font-size: 13px; color: #8896b7;'>" + GlobalConst.REP_EMAIL.getCode() + "</a> )로 문의해 주시기 바랍니다.<br/><br/>");
            stringbuffer.append("                (주)현대케피코 ㅣ 경기도 군포시 고산로 102(당정동 310) ㅣ Tel. 031-450-9015<br/>");
            stringbuffer.append("                Copyright 2023 ⓒHYUNDAI KEFICO Corporation All rights reserved.");
        } else {
            stringbuffer.append("                This email is for sending only and will not be replied to.<br/>");
            stringbuffer.append("                For inquiries, please contact the customer center(<a href='mailto:" + GlobalConst.REP_EMAIL.getCode() + "' style='font-family: dotum, 돋움; font-size: 13px; color: #8896b7;'>"+ GlobalConst.REP_EMAIL.getCode() + "</a>).<br/><br/>");
            stringbuffer.append("                Hyundai Kefico Co., Ltd. ㅣ 102 Gosan-ro (310 Dangjeong-dong), Gunpo-si, Gyeonggi-do ㅣ Tel. 031-450-9015<br/>");
            stringbuffer.append("                Copyright 2023 ⓒHYUNDAI KEFICO Corporation All rights reserved.");
        }
        stringbuffer.append("            </td>");
        stringbuffer.append("        </tr>");
        stringbuffer.append("    </tbody>");
        stringbuffer.append("</table>");
        stringbuffer.append("</body>");
        stringbuffer.append("</html>");

        return stringbuffer.toString();

    }
}
