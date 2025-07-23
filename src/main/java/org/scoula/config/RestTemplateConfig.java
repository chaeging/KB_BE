//package org.scoula.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestTemplateConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        // Timeout 설정
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(5000); // 연결 Timeout (ms)
//        factory.setReadTimeout(5000);    // 응답 대기 Timeout (ms)
//
//        return new RestTemplate(factory);
//    }
//}

/// ///////////////////////////////////////////////////////////////////////

//@Configuration
//public class RestTemplateConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // ✅ 4xx, 5xx 에러 응답도 body 읽게 error handler 커스텀
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
//            @Override
//            public void handleError(org.springframework.http.client.ClientHttpResponse response) throws IOException {
//                // 아무것도 안 함: 예외 던지지 않고 body 읽게 만듦
//            }
//        });
//
//        return restTemplate;
//    }
//}