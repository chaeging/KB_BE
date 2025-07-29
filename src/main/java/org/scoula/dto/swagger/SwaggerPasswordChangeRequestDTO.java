package org.scoula.dto.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "비밀번호 변경 요청 DTO")
public class SwaggerPasswordChangeRequestDTO {

    @ApiModelProperty(value = "기존 비밀번호", example = "oldpass123")
    private String oldPassword;

    @ApiModelProperty(value = "새 비밀번호", example = "NewPass123!")
    private String newPassword;
}