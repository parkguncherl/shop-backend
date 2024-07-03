package com.binblur.api.biz.system.service;

import com.google.gson.GsonBuilder;
import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.ContactDao;
import com.binblur.core.biz.system.vo.request.ContactRequest;
import com.binblur.core.biz.system.vo.response.ContactResponse;
import com.binblur.core.entity.Contact;
import com.binblur.core.entity.User;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.enums.TranType;
import com.binblur.core.exception.EspRuntimeException;
import com.binblur.core.utils.CommUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * <pre>
 * Description : 접속로그 Service
 * Date : 2023/02/06 11:57 AM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class ContactService {

    @Autowired
    private ContactDao contactDao;

    /**
     * 메뉴_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PageResponse<ContactResponse.Paging> selectContactListPaging(PageRequest<ContactRequest.PagingFilter> pageRequest) {
        PageResponse<ContactResponse.Paging> pageResponse = contactDao.selectContactListPaging(pageRequest);

        // 거래코드명 추가
        if (pageResponse != null && pageResponse.getRows() != null && !pageResponse.getRows().isEmpty()) {
            pageResponse.getRows().forEach(contact -> {
                if (contact.getTranType() != null) {
                    contact.setTranTypeNm(contact.getTranType().getMessage());
                } else {
                    throw new EspRuntimeException(ApiResultCode.NOT_FOUND_CONTACT);
                }
            });
        }

        return pageResponse;
    }


    /**
     * 로깅 남기기
     *
     * @param
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void logging(User user, String serviceName, Object objParam) {
        try {
            // 접속정보 추출
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String ip = "0.0.0.0";
            String uri = "/";
            String method = "GET";
            TranType tranType = TranType.GET;

            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                if (request != null) {
                    ip = CommUtil.getIp();
                    uri = request.getRequestURI();
                    method = request.getMethod();
                }
            }

            // 사용자 정보가 없으면 빠져나온다.
            if (user == null || StringUtils.isEmpty(user.getLoginId())) {
                log.warn("<======= 접속로그 저장시 사용자 정보가 없습니다. ip: {}, uri: {}, method: {}", ip, uri, method);
                return;
            }

            String jsonPram = "";
            if (objParam != null) {
                if (objParam instanceof Map) {
                    jsonPram = objParam.toString();
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
                    gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
                    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
                    jsonPram = gsonBuilder.setPrettyPrinting().create().toJson(objParam);

                    // 너무 긴 input 은 다 남기지 않는다.
                    if (jsonPram.length() > 2000) {
                        jsonPram = jsonPram.substring(0, 2000);
                    }
                }
            }

            // method 별 거래타입
            if (StringUtils.equals("GET", method)) {
                tranType = TranType.GET;
                if (StringUtils.indexOf(uri, "/auth/logout") > -1) {
                    tranType = TranType.LOGOUT;
                }
            } else if (StringUtils.equals("POST", method)) {
                tranType = TranType.INSERT;
                if (StringUtils.indexOf(uri, "/auth/login") > -1) {
                    tranType = TranType.LOGIN;
                }
            } else if (StringUtils.equals("PUT", method)) {
                tranType = TranType.MODIFY;
            } else if (StringUtils.equals("DELETE", method)) {
                tranType = TranType.DELETE;
            }

            Contact contact = new Contact();
            contact.setContactIp(ip);
            contact.setUserId(user.getId());
            contact.setTranType(tranType);
            contact.setUri(uri);
            contact.setUriNm(serviceName);
            contact.setInputParamCntn(jsonPram);
            contact.setCreateUser(user.getLoginId());
            contact.setUpdateUser(user.getLoginId());
            contactDao.insertContact(contact);
        } catch (Exception e) {
            // 접속로그는 에러 무시 처리
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 접속로그_조회 (by Id)
     *
     * @param contactId
     * @return
     */
    public Contact selectContactById(Integer contactId) {
        return contactDao.selectContactById(contactId);
    }

    /**
     * 접속로그_조회 (by Id)
     *
     * @param contactId
     * @return
     */
    public String isOverTime(Integer contactId) {
        return contactDao.isOverTime(contactId);
    }

}
