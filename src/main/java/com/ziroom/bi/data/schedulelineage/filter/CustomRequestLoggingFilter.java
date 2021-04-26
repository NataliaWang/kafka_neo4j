package com.ziroom.bi.data.schedulelineage.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class CustomRequestLoggingFilter extends AbstractRequestLoggingFilter {

    protected boolean shouldLog(HttpServletRequest request) {
        return this.logger.isDebugEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String message) {
        this.logger.debug(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String message) {
        this.logger.debug(message);
    }

    @Override
    protected String getMessagePayload(HttpServletRequest request) {
        String messagePayload = super.getMessagePayload(request);
        if(null != messagePayload){
            return messagePayload.replaceAll("[\\n\\r]", " ");
        }
        return null;
    }
}
