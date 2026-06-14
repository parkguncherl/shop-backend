package com.shop.api.frontWeb.service;

import com.shop.core.entity.DeliveryAddress;
import com.shop.core.frontWeb.dao.DeliveryAddressDao;
import com.shop.core.frontWeb.vo.request.DeliveryAddressRequest;
import com.shop.core.frontWeb.vo.response.DeliveryAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDao deliveryAddressDao;

    public List<DeliveryAddressResponse.Info> getList(Long socialAccountId) {
        return deliveryAddressDao.selectList(socialAccountId).stream()
                .map(this::toInfo)
                .toList();
    }

    @Transactional
    public DeliveryAddressResponse.Info save(DeliveryAddressRequest.Save request) {
        // 기본 배송지로 저장 시 기존 기본 배송지 해제
        if ("Y".equals(request.getIsDefault())) {
            deliveryAddressDao.clearDefault(request.getSocialAccountId());
        }

        DeliveryAddress entity = DeliveryAddress.builder()
                .socialAccountId(request.getSocialAccountId())
                .alias(request.getAlias())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .memo(request.getMemo())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : "N")
                .build();

        deliveryAddressDao.insert(entity);
        return toInfo(deliveryAddressDao.selectById(entity.getId()));
    }

    @Transactional
    public DeliveryAddressResponse.Info update(DeliveryAddressRequest.Update request) {
        // 기본 배송지로 변경 시 기존 기본 배송지 해제
        if ("Y".equals(request.getIsDefault())) {
            deliveryAddressDao.clearDefault(request.getSocialAccountId());
        }

        DeliveryAddress entity = DeliveryAddress.builder()
                .id(request.getId())
                .socialAccountId(request.getSocialAccountId())
                .alias(request.getAlias())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .memo(request.getMemo())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : "N")
                .build();

        deliveryAddressDao.update(entity);
        return toInfo(deliveryAddressDao.selectById(request.getId()));
    }

    public void delete(Long id) {
        deliveryAddressDao.delete(id);
    }

    private DeliveryAddressResponse.Info toInfo(DeliveryAddress entity) {
        if (entity == null) return null;
        DeliveryAddressResponse.Info info = new DeliveryAddressResponse.Info();
        info.setId(entity.getId());
        info.setSocialAccountId(entity.getSocialAccountId());
        info.setAlias(entity.getAlias());
        info.setReceiverName(entity.getReceiverName());
        info.setReceiverPhone(entity.getReceiverPhone());
        info.setZipCode(entity.getZipCode());
        info.setAddress(entity.getAddress());
        info.setAddressDetail(entity.getAddressDetail());
        info.setMemo(entity.getMemo());
        info.setIsDefault(entity.getIsDefault());
        info.setCreTm(entity.getCreTm());
        info.setUptTm(entity.getUptTm());
        return info;
    }
}
