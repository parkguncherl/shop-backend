package com.shop.core.utils;

import com.microsoft.azure.sdk.iot.provisioning.device.*;
import com.microsoft.azure.sdk.iot.provisioning.security.SecurityProviderSymmetricKey;
import com.microsoft.azure.sdk.iot.provisioning.service.ProvisioningServiceClient;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.IndividualEnrollment;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.ProvisioningStatus;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.SymmetricKeyAttestation;
import com.microsoft.azure.sdk.iot.provisioning.service.exceptions.ProvisioningServiceClientBadUsageException;
import com.microsoft.azure.sdk.iot.provisioning.service.exceptions.ProvisioningServiceClientException;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubNotFoundException;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodRequestOptions;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodResponse;
import com.microsoft.azure.sdk.iot.service.methods.DirectMethodsClient;
import com.microsoft.azure.sdk.iot.service.registry.RegistryClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

/**
 * <pre>
 * Description:
 * Date: 2023/03/30 12:39 PM
 * Company: smart90
 * Author: hyong
 * </pre>
 */
@Slf4j
public class AzureIoTUtil {

    /**
     * Azure IoT Device Provisioning Service(DPS) 에서 장비를 제거
     * @param provisionConnectionString
     * @param registrationId
     * @return
     */
    public static boolean deleteDeviceOnDeviceProvisioningService(String provisionConnectionString, String registrationId) {
        try {
            ProvisioningServiceClient provisioningServiceClient = ProvisioningServiceClient.createFromConnectionString(provisionConnectionString);
            provisioningServiceClient.deleteIndividualEnrollment(registrationId);
        } catch (ProvisioningServiceClientException err) {
            String msg = err.getMessage();
            msg = msg.substring(msg.indexOf("{"));
            JSONObject jsnobject = new JSONObject(msg);
            if(jsnobject.getInt("errorCode") == 404201){ // com.microsoft.azure.sdk.iot.provisioning.service.exceptions.ProvisioningServiceClientNotFoundException: Device Provisioning Service not found! {"errorCode":404201
                return true;
            }

            log.error(err.getMessage(), err);
            return false;
        }

        log.info(">>>>>> AzureIoTUtil::deleteDeviceOnDeviceProvisioningService => Delete Device Success!!! (DPS)");

        return true;
    }

    /**
     * Azure IoT Hub 에서 장비를 제거
     * @param iotHubConnectionString
     * @param deviceId
     * @return
     */
    public static boolean deleteDeviceOnIoTHub(String iotHubConnectionString, String deviceId) {
        try {
            RegistryClient registryClient = new RegistryClient(iotHubConnectionString);
            registryClient.removeDevice(deviceId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String msg = e.getMessage();
            if(msg.indexOf("DeviceNotFound") > -1){ // ErrorCode:DeviceNotFound;KR-CHG-121212121212 Tracking ID:235fc178ac904f60915eb4361dbc852b-TimeStamp:06/28/2023 01:02:23
                return true;
            }

            return false;
        }

        log.info(">>>>>> AzureIoTUtil::deleteDeviceOnIoTHub => Delete Device Success!!! (Hub)");

        return true;
    }

    /**
     * Azure IoT Device Provisioning Service(DPS) 에 장비를 Service Provisioning
     * @param provisionConnectionString
     * @param registrationId
     * @param iotHubHostName
     * @param iotHubDeviceId
     * @param symmetricKey
     * @param isReProvisioning
     * @return
     */
    public static boolean registDevice4ProvisioningServiceOnDPS(String provisionConnectionString
            , String registrationId
            , String iotHubHostName
            , String iotHubDeviceId
            , String symmetricKey
            , boolean isReProvisioning) {
        IndividualEnrollment individualEnrollmentResult =  null;
        try
        {
            ProvisioningServiceClient provisioningServiceClient = ProvisioningServiceClient.createFromConnectionString(provisionConnectionString);

            // 디바이스 인증유형 (대칭키) -> 장비의 대칭키 PrimaryKey & SecondaryKey 이 두 값은 동일한 값으로 세팅
            SymmetricKeyAttestation attestation = new SymmetricKeyAttestation(symmetricKey, symmetricKey);

            IndividualEnrollment individualEnrollment = new IndividualEnrollment(registrationId, attestation);
            individualEnrollment.setIotHubHostName(iotHubHostName);
            individualEnrollment.setDeviceId(iotHubDeviceId);
            individualEnrollment.setProvisioningStatus(ProvisioningStatus.ENABLED);

            // 장비를 다시 프로비저닝 (대칭키 갱신) 할때만 적용 -> 다시 프로비저닝 하는 시간 주기는 30분이 지나야 되는 것 같음.
            if(isReProvisioning) {
                individualEnrollment.setEtag("*");
            }

            individualEnrollmentResult = provisioningServiceClient.createOrUpdateIndividualEnrollment(individualEnrollment);
            log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningServiceOnDPS => IndividualEnrollmentResult = {}", individualEnrollmentResult);
        } catch (ProvisioningServiceClientBadUsageException err) {

            String msg = err.getMessage();
            JSONObject jsnobject = new JSONObject(msg);
            if(jsnobject.getInt("errorCode") == 409201){ // Enrollment already exist
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("e.message==>",e.getMessage());
            log.debug("e.message2==>",e.toString());
            return false;
        }

        log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningServiceOnDPS => Regist Device Success!!! (DPS Service Provisioning)");

        return true;
    }

    /**
     * Azure IoT Device Provisioning Service(DPS) 에 장비를 Device Provisioning
     * @param provisionGlobalDeviceEndPoint
     * @param provisionScopeId
     * @param registrationId
     * @param deviceSymmetricKey
     * @return
     */
    public static boolean registDevice4ProvisioningDeviceOnDPS(String provisionGlobalDeviceEndPoint
            , String provisionScopeId
            , String registrationId
            , String deviceSymmetricKey) {
        log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningDeviceOnDPS start====================================>");
        try {
            SecurityProviderSymmetricKey securityClientSymmetricKey = new SecurityProviderSymmetricKey(deviceSymmetricKey.getBytes(StandardCharsets.UTF_8), registrationId);

            ProvisioningDeviceClient provisioningDeviceClient = ProvisioningDeviceClient.create(provisionGlobalDeviceEndPoint
                    , provisionScopeId, ProvisioningDeviceClientTransportProtocol.HTTPS, securityClientSymmetricKey);

            provisioningDeviceClient.registerDevice(new ProvisioningDeviceClientRegistrationCallbackImpl(), null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningDeviceOnDPS => Regist Device Success!!! (DPS Device Provisioning)");

        return true;
    }

    /**
     * Azure IoT Hub 역방향 메시지 전송 (by Direct Message)
     * @param iotHubConnectionString
     * @param deviceId
     * @param methodName
     * @param payloadMessage
     * @param connectTimeout
     * @param responseTimeout
     * @return
     */
    public static boolean sendMessageServerToHmiDevice(String iotHubConnectionString
            , String deviceId
            , String methodName
            , Map<String,Object> payloadMessage
            , int connectTimeout
            , int responseTimeout) {

        DirectMethodsClient methodClient = new DirectMethodsClient(iotHubConnectionString);
        DirectMethodRequestOptions options = DirectMethodRequestOptions.builder()
                        .payload(payloadMessage)
                        .methodConnectTimeoutSeconds(connectTimeout)
                        .methodResponseTimeoutSeconds(responseTimeout)
                        .build();

        DirectMethodResponse response;
        try {
            response = methodClient.invoke(deviceId, methodName, options);
        } catch (IotHubNotFoundException iotHubNotFoundException) {
            log.error(iotHubNotFoundException.getMessage(), iotHubNotFoundException);
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        log.info(">>>>>> AzureIoTUtil::sendMessageServerToHmiDevice => DirectMethodResponse => Status = {} || Payload = {}"
                , response.getStatus()
                , response.getPayloadAsJsonString()
        );

        return true;
    }


    /**
     * Azure IoT Hub 역방향 메시지 전송 후 결과 리턴 (by Direct Message)
     * @param iotHubConnectionString
     * @param deviceId
     * @param methodName
     * @param payloadMessage
     * @param connectTimeout
     * @param responseTimeout
     * @return
     */
    public static DirectMethodResponse sendMessageServerToHmiDeviceResult(String iotHubConnectionString
            , String deviceId
            , String methodName
            , Map<String,Object> payloadMessage
            , int connectTimeout
            , int responseTimeout
    ) throws IOException, IotHubException {

        DirectMethodsClient methodClient = new DirectMethodsClient(iotHubConnectionString);
        DirectMethodRequestOptions options = DirectMethodRequestOptions.builder()
                        .payload(payloadMessage)
                        .methodConnectTimeoutSeconds(connectTimeout)
                        .methodResponseTimeoutSeconds(responseTimeout)
                        .build();

        DirectMethodResponse response = methodClient.invoke(deviceId, methodName, options);

        log.info(">>>>>> AzureIoTUtil::sendMessageServerToHmiDevice => DirectMethodResponse => Status = {} || Payload = {}"
                , response.getStatus()
                , response.getPayloadAsJsonString()
        );

        return response;
    }

    /**
     * Azure IoT Device Provisioning Service(DPS) 에 장비를 Service Provisioning 결과를 받는 Callback
     */
    private static class ProvisioningDeviceClientRegistrationCallbackImpl implements ProvisioningDeviceClientRegistrationCallback
    {
        @Override
        public void run(ProvisioningDeviceClientRegistrationResult registrationResult, Exception error, Object context)
        {
            if(error != null) {
                log.error(error.getMessage(), error);
                return;
            }

            if(registrationResult.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ASSIGNED) {
                log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningDeviceOnDPS (DPS Device Provisioning callback) Result => SUCCESS");
            } else {
                log.info(">>>>>> AzureIoTUtil::registDevice4ProvisioningDeviceOnDPS (DPS Device Provisioning callback) Result => FAILURE || Status = {}", registrationResult.getProvisioningDeviceClientStatus());
            }
        }
    }

    /**
     * 장비의 대칭키를 생성
     * 이 값을 Azure IoT Device Provisioning Service(DPS) 에 장비를 Service Provisioning 할 때
     * "primaryKey", "secondaryKey" 동일하게 적용하면 됨
     * @param deviceMacAddress 장비의 Mac 주소 (대문자) : (예) "0433C2DBB0B5"
     * @return
     */
    public static String genDeviceSymmetricKey(String deviceMacAddress) {
        String symmetricKey = null;

        String macAddress = deviceMacAddress.replaceAll("-","");

        if(!StringUtils.hasLength(macAddress) || macAddress.length() != 12) {
            return null;
        }

        String salt = "BLUEPLUG";
        String inputString = macAddress.toUpperCase().concat(salt);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(inputString.getBytes("utf8"));
            symmetricKey = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

        return symmetricKey;
    }

}
