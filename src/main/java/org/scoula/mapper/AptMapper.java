package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scoula.dto.AptDTO;
import org.scoula.dto.AptResponseDto;
import org.scoula.dto.AptTypeResponseDTO;

import java.util.List;

@Mapper
public interface AptMapper {
    void insertApt(AptDTO aptDTO);
    List<AptDTO> getIdxAndHouseMangeNo(); // apt 테이블에서  house_manage_no 와 apt_idx 만 가져옴
    void insertAptType(AptTypeResponseDTO aptTypeResponseDTO);
}
