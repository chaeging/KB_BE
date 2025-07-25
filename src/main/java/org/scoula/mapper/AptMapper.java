package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scoula.dto.AptDTO;
import org.scoula.dto.AptResponseDto;

import java.util.List;

@Mapper
public interface AptMapper {
    void insertApt(AptDTO aptDTO);
}
