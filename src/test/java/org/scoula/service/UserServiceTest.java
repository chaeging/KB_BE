package org.scoula.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scoula.mapper.UserMapper;
import org.scoula.security.dto.MemberDTO;
import org.scoula.service.UserService;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void updateUser_필수값없으면_예외_테스트() {
        // 1. userId 없음
        MemberDTO user1 = new MemberDTO();
        user1.setUserName("홍길동");
        user1.setAddress("서울");
        user1.setBirthdate(Date.valueOf("1990-01-01"));

        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user1));
        assertEquals("userId는 필수입니다.", ex1.getMessage());

//        // 2. userName 없음
//        MemberDTO user2 = new MemberDTO();
//        user2.setUserId("user@example.com");
//        user2.setAddress("서울");
//        user2.setBirthdate(Date.valueOf("1990-01-01"));
//
//        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user2));
//        assertEquals("userName은 필수 입력값입니다.", ex2.getMessage());
//
//        // 3. address 없음
//        MemberDTO user3 = new MemberDTO();
//        user3.setUserId("user@example.com");
//        user3.setUserName("홍길동");
//        user3.setBirthdate(Date.valueOf("1990-01-01"));
//
//        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user3));
//        assertEquals("address는 필수 입력값입니다.", ex3.getMessage());
//
//        // 4. birthdate 없음
//        MemberDTO user4 = new MemberDTO();
//        user4.setUserId("user@example.com");
//        user4.setUserName("홍길동");
//        user4.setAddress("서울");
//
//        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user4));
//        assertEquals("birthdate는 필수 입력값입니다.", ex4.getMessage());
    }
}
