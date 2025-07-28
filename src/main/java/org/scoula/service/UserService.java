package org.scoula.service;
import lombok.RequiredArgsConstructor;
import org.scoula.mapper.UserMapper;
import org.scoula.security.dto.AuthDTO;
import org.scoula.security.dto.MemberDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO getUser(String email) {
        return userMapper.findById(email);
    }

    public List<MemberDTO> getAllUsers() {
        return userMapper.findAll();
    }


    @Transactional
    public void signUp(MemberDTO memberDTO) {
        // 1. 사용자 등록
        String originalPassword = memberDTO.getPassword();
        String newPassword = passwordEncoder.encode(originalPassword);
        memberDTO.setPassword(newPassword);
        userMapper.insertUser(memberDTO);

        // 2. 권한 등록
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsersIdx(memberDTO.getUsersIdx());
        authDTO.setAuth("ROLE_MEMBER");
        userMapper.insertAuth(authDTO);
    }

    public void updateUser(MemberDTO user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(int userIdx) {
        userMapper.deleteUser(userIdx);
    }

    public MemberDTO findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}


