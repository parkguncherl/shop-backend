package com.shop.api;

import com.shop.core.utils.CryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <pre>
 * Description: Common 테스트
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("local")
@SpringBootTest
public class CryptTest {

    @Test
    void encLoginPass() {
        String loginId = "hyong";
        String password = "12345678";

        String encPass = null;
        try {
            encPass = CryptUtil.getHash(password, loginId.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug(">>>>>> enc Pass = [{}]", encPass);
    }

}
