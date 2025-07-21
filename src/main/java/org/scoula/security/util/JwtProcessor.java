package org.scoula.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProcessor {
    static private final long TOKEN_VALID_MILISECOND=1000L*60*5; //5분

    private String secretKey = "충분히 긴 임의의(랜덤한) 비밀키 문자열 배정";
    private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    //private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //JWT 생성
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+TOKEN_VALID_MILISECOND))
                .signWith(key)
                .compact();
    }

    // JWT Subject(username) 추출- 해석불가인경우예외발생
    //JWT(JSON Web Token)에서 사용자 이름(username)을 추출하는 기능을 수행합니다.
    // 예외ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,IllegalArgumentException
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 서명 검증을 위한 키 설정
                .build()  // JWT 파서 생성
                .parseClaimsJws(token) // JWT 토큰 파싱
                .getBody() // JWT의 payload(내용) 부분 가져오기
                .getSubject();// subject 클레임 값 추출

    }

    // JWT 검증(유효기간검증)-해석불가인경우예외발생
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;
    }


}
