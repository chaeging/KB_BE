package org.scoula.dto.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "로그인 요청 DTO")
public class SwaggerLoginRequestDTO {

    @ApiModelProperty(value = "사용자 ID (이메일)", example = "admin@naver.com")
    private String userId;

    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String password;
}