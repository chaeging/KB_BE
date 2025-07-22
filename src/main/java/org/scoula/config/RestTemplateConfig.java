package org.scoula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // ✅ 4xx, 5xx 에러 응답도 body 읽게 error handler 커스텀
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(org.springframework.http.client.ClientHttpResponse response) throws IOException {
                // 아무것도 안 함: 예외 던지지 않고 body 읽게 만듦
            }
        });

        return restTemplate;
    }
}
