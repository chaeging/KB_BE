package org.scoula.service;

import lombok.RequiredArgsConstructor;
import org.scoula.mapper.UserMapper;
import org.scoula.security.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public MemberDTO getUser(String email) {
        return userMapper.findById(email);
    }

    public List<MemberDTO > getAllUsers() {
        return userMapper.findAll();
    }

    public void addUser(MemberDTO  user) {
        userMapper.insertUser(user);
    }

    public void updateUser(MemberDTO  user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    public MemberDTO  findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}

