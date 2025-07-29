package org.scoula.dto.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Refresh Token 요청 DTO")
public class SwaggerRefreshTokenRequestDTO {

    @ApiModelProperty(value = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
}