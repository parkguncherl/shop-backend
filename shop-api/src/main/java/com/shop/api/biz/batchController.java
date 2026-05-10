package com.shop.api.biz;

import com.shop.core.frontWeb.dao.GuestRateLimitDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class batchController {


    @Component
    @RequiredArgsConstructor
    public class GuestRateLimitCleanupBatch {

        private final GuestRateLimitDao guestRateLimitDao;
        private final GuestTokenDao guestTokenDao;

        // 매 시간 정각에 1시간 이전 데이터 삭제
        @Scheduled(cron = "0 0 * * * *")
        @Transactional
        public void cleanupOldRateLimits() {
            guestRateLimitDao.deleteOldRateLimits();
        }

        // 매일 새벽 2시 만료 토큰 삭제
        @Scheduled(cron = "0 0 2 * * *")
        public void cleanupGuestToken() {
            guestTokenDao.deleteExpiredGuestToken();
        }
    }
}
