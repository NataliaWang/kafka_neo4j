package com.ziroom.bi.data.schedulelineage.config;

import com.ziroom.bi.data.schedulelineage.filter.CustomRequestLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CustomRequestLoggingFilter logFilter() {
        CustomRequestLoggingFilter loggingFilter = new CustomRequestLoggingFilter();
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(10000);
        loggingFilter.setIncludeHeaders(true);
//        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return loggingFilter;
    }
}
