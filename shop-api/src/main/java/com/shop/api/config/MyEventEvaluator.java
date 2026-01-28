package com.shop.api.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EventEvaluatorBase;

public class MyEventEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    @Override
    public boolean evaluate(ILoggingEvent event) {
        if (event == null) return false;

        String message = event.getFormattedMessage();
        if (message == null) return false;

        return message.contains("selectUserById")
                || message.contains("insertContact")
                || message.contains("selectAuthTokenByUserId")
                || message.contains("selectUserByLoginId")
                || message.contains("selectMenuListForUser")
                || message.contains("selectMenuList")
                || message.contains("selectGridColum")
                || message.contains("selectPartnerById");
    }
}
