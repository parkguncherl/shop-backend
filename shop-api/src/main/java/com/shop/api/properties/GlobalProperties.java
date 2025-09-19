package com.shop.api.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * Description: Global Properties
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@Component
public class GlobalProperties {

    @Value("${jwt.access.token.expiration.time}")
    private String jwtExpireTime;

    @Value("${jwt.refresh.token.remained.day}")
    private Integer jwtRefreshTokenRemainDays;

    @Value("TOP")
    private String  topMenu;

    @Value("S0009")
    private String  otpUpperCode;

    @Value("9000")
    private String  otpCode;

    @Value("#{new Integer('120')}")
    private Integer otpLimitSecond;

    @Value("${slack.send.error.message}")
    private Boolean slackSendErrorMessageYn;

    @Value("${slack.web.hooks.url}")
    private String slackWebHooksUrl;

    @Value("D")
    private String tranTypeDel;
    @Value("M")
    private String tranTypeMod;
    @Value("I")
    private String tranTypeIns;
    @Value("S")
    private String tranTypeSel;
    @Value("L")
    private String tranTypeLogin;

}
