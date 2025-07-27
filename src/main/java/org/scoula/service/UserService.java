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
        String originalPassword = memberDTO.getPassword();
        String newPassword = passwordEncoder.encode(originalPassword);
        memberDTO.setPassword(newPassword);
        userMapper.insertUser(memberDTO);
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsersIdx(memberDTO.getUsersIdx());
        authDTO.setAuth("ROLE_MEMBER");
        userMapper.insertAuth(authDTO);
    }

    public void updateUser(MemberDTO user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(String userId) {
        userMapper.deleteUser(userId);
    }

    public MemberDTO findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public Integer findUserIdxByUserId (String usersId) {
        return userMapper.findUserIdxByUserId(usersId);
    }

    public void updatePassword(String userId, String oldPassword, String newPassword) {
        // 1. 기존 사용자 조회
        MemberDTO member = userMapper.findById(userId);
        if (member == null) {
            throw new IllegalStateException("비밀번호 변경 실패: 해당 사용자가 존재하지 않음");
        }

        // 2. 기존 비밀번호 비교
        if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 3. 새 비밀번호 암호화 후 업데이트
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedNewPassword);

        int updated = userMapper.updatePasswordByUsername(member);
        if (updated == 0) {
            throw new IllegalStateException("비밀번호 변경 실패: 업데이트 실패");
        }
    }



}


