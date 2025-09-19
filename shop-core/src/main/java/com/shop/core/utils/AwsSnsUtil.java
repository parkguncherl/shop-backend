package com.shop.core.utils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Description: Amazon SNS 유틸리티
 * Date: 2023/04/27 9:32 AM
 * Company: smart90
 * Author: hyong
 * </pre>
 */

@Slf4j
public class AwsSnsUtil {


    /**
     * SMS 발송
     * @param awsAccessKey
     * @param awsSecretKey
     * @param message
     * @param phoneNo
     * @return
     */
    public static boolean sendSmsMessage(String awsAccessKey, String awsSecretKey, String message, String phoneNo) {

        try
        {
            AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(awsAccessKey, awsSecretKey));

            PublishResult result = snsClient.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phoneNo));

            log.info(">>>>>> AwsSnsUtil::sendSmsMessage call. || result = {}", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
