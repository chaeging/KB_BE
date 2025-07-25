package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.scoula.security.dto.AuthDTO;
import org.scoula.security.dto.MemberDTO;

import java.util.List;

@Mapper
public interface UserMapper {

    int findUserIdxByUserId(@Param("userId") String userId);

    MemberDTO findById(@Param("userId") String userId);
    List<MemberDTO> findAll();
    void insertUser(MemberDTO user);
    void updateUser(MemberDTO user);
    void deleteUser(int userIdx);
    MemberDTO findByUsername(@Param("username") String username);
    int countUserByIdx(@Param("userIdx") int userIdx);  //  users_auth 테이블에 해당 유저 정보가 저장되어있는지 확인
//    void insertUserAuth(@Param("usersIdx") Long usersIdx);
    void insertAuth(AuthDTO authDTO);
}