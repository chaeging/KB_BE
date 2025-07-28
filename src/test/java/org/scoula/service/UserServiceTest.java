package org.scoula.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scoula.mapper.UserMapper;
import org.scoula.security.dto.MemberDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService; // 테스트 대상

    @Mock
    private UserMapper userMapper; // 의존 객체

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void updateUser() {
        MemberDTO dto = new MemberDTO();
        dto.setUserId("test");
        userService.updateUser(dto);
    }
}