package org.scoula.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private int usersIdx;
    @JsonProperty("user_id")//users.user_idx
    private String userId;             // users.user_id
    @JsonProperty("user_name")
    private String userName;           // users.user_name
    private String password;           // users.password
    private String address;
    private Date birthdate;            // users.birthdate
    private List<AuthDTO> authList;
    private String kakaoUserId;
}
