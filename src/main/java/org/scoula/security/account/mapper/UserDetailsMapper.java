package org.scoula.security.account.mapper;

import org.scoula.security.dto.LoginDTO;
import org.scoula.security.dto.MemberDTO;
import org.scoula.security.dto.RefreshTokenDTO;

public interface UserDetailsMapper {
    public MemberDTO get(String username);
}