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
<<<<<<< HEAD
        // 1. 사용자 등록
=======
>>>>>>> upstream/develop
        String originalPassword = memberDTO.getPassword();
        String newPassword = passwordEncoder.encode(originalPassword);
        memberDTO.setPassword(newPassword);
        userMapper.insertUser(memberDTO);
<<<<<<< HEAD

        // 2. 권한 등록
=======
>>>>>>> upstream/develop
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsersIdx(memberDTO.getUsersIdx());
        authDTO.setAuth("ROLE_MEMBER");
        userMapper.insertAuth(authDTO);
    }

    public void updateUser(MemberDTO user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    public MemberDTO findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public Integer findUserIdxByUserId (String usersId) {
        return userMapper.findUserIdxByUserId(usersId);
    }
}


