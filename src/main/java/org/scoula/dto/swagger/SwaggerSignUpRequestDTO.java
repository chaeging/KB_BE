package org.scoula.dto.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "회원가입 요청 DTO")
public class SwaggerSignUpRequestDTO {

    @ApiModelProperty(value = "사용자 ID (이메일)", example = "test@example.com")
    private String userId;

    @ApiModelProperty(value = "사용자 이름", example = "테스트이름입니다")
    private String userName;

    @ApiModelProperty(value = "비밀번호", example = "1234")
    private String password;

    @ApiModelProperty(value = "주소", example = "서울특별시 강남구")
    private String address;
}