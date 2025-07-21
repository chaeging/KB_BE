package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AccountConnectDTO;
import org.scoula.dto.ChungyakAccountDTO;
import org.scoula.service.CodefApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@Log4j2
public class AccountController {

    @Autowired
    private CodefApiService codefApiService;

    @PostMapping("/connect")
    public List<ChungyakAccountDTO> autoConnectAndFetchAccounts(@RequestBody AccountConnectDTO requestDto) throws Exception {
        return codefApiService.autoConnectAndFetchChungyakAccounts(
                requestDto.getId(),
                requestDto.getPassword(),
                requestDto.getOrganization(),
                requestDto.getBankName()
        );
    }


}
