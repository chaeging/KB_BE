package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.GaScoreDTO;

@Mapper
public interface GaScoreMapper {

    void updateScore(@Param("userIdx") int userIdx,
                     @Param("gaScoreDTO") GaScoreDTO gaScoreDTO);

    void updateTotalScore(@Param("userIdx") int userIdx,
                          @Param("totalScore") int totalScore);

    GaScoreDTO getScoresByUserIdx(@Param("userIdx") int userIdx);

    int getTotalScoreByUserIdx(@Param("userIdx") int userIdx);
}
