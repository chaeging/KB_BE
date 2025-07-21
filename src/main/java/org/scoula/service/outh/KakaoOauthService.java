package org.scoula.service.outh;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOauthService {
    private final RestTemplate restTemplate = new RestTemplate();   // Spring에서 제공하는 HTTP 통신용 클라이언트 클래스, Rest API 서버와 GET, POST, PUT DELETE 등 요청을 주고 받을때 사용
    private final ObjectMapper objectMapper = new ObjectMapper();   // Java 객체 ↔ JSON 문자열 변환을 담당

    @Value("5a743890c223b81e0e2ca139881c8cb7")
    private String REST_API_KEY;

    @Value("http://localhost:8080/oauth/kakao/callback")
    private String REDIRECT_URL;

    public String getAccessToken(String authorizationCode) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();    // HTTP 요청/응답 헤더를 다루기 위한 객체
        headers.setContentType((MediaType.APPLICATION_FORM_URLENCODED));    // 서버에 데이터를 보낼때, 데이터가 어떤형식인지 알려주는 것,
                                                                            // 브라우저에서 <form>을 사용해 데이터를 전송할때, application/x-www-form-urlencoded 방식이 사용됨
                                                                            // application/x-www-form-urlencoded는 key=value&key2=value2 이 형식이라는 의미
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // MultiValueMap은 하나의 key에 여러 개의 value를 저장할 수 있는 자료구조
        params.add("grant_type", "authorization_code");     // 인가 코드 방식을 사용한다는 의미
        params.add("client_id", REST_API_KEY);
        params.add("redirect_uri", REDIRECT_URL);
        params.add("code", authorizationCode);              // 카카오가 준 1회성 인가 코드, 이 코드를 사용해 Access Token 교환


    }
}
