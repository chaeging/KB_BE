package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AccountConnectDTO;
import org.scoula.dto.ChungyakAccountDTO;
import org.scoula.exception.NoAccountException;
import org.scoula.service.CodefApiService;
import org.scoula.security.util.JwtProcessor;
import org.scoula.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/account")
@Log4j2
@RequiredArgsConstructor
public class AccountController {

    private final CodefApiService codefApiService;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    @PostMapping("/connect")
    public ResponseEntity<Map<String, String>> autoConnectAndFetchAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody AccountConnectDTO requestDto
    ) {
        try {
            String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
            int userIdx = userMapper.findUserIdxByUserId(userId);

            codefApiService.autoConnectAndFetchChungyakAccount(
                    requestDto.getId(),
                    requestDto.getPassword(),
                    requestDto.getOrganization(),
                    requestDto.getBankName(),
                    userIdx
            );

            Map<String, String> response = new HashMap<>();
            response.put("message", "계좌 등록이 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("청약 계좌 연결 실패", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "계좌 등록에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    @GetMapping("")
    public ResponseEntity<ChungyakAccountDTO> getUserAccount(@RequestHeader("Authorization") String token) {
        try {
            String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
            int userIdx = userMapper.findUserIdxByUserId(userId);

            ChungyakAccountDTO account = codefApiService.getAccountByUserIdx(userIdx);
            return ResponseEntity.ok(account);

        } catch (NoAccountException e) {
            log.warn("계좌 조회 실패 - 계좌 없음: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("계좌 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
