package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AccountConnectDTO;
import org.scoula.dto.ChungyakAccountDTO;
import org.scoula.service.CodefApiService;
import org.scoula.security.util.JwtProcessor;
import org.scoula.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
@Log4j2
public class AccountController {

    @Autowired
    private CodefApiService codefApiService;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/connect")
    public List<ChungyakAccountDTO> autoConnectAndFetchAccounts(
            @RequestHeader("Authorization") String token,
            @RequestBody AccountConnectDTO requestDto
    ) throws Exception {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);

        return codefApiService.autoConnectAndFetchChungyakAccounts(
                requestDto.getId(),
                requestDto.getPassword(),
                requestDto.getOrganization(),
                requestDto.getBankName(),
                userIdx
        );
    }

    @GetMapping("")
    public List<ChungyakAccountDTO> getUserAccounts(@RequestHeader("Authorization") String token) {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);

        return codefApiService.getChungyakAccountsByUserIdx(userIdx);
    }
}