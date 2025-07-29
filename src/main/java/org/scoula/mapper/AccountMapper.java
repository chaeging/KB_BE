package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.ChungyakAccountDTO;

@Mapper
public interface AccountMapper {

    void insertChungyakAccount(@Param("dto") ChungyakAccountDTO dto,
                               @Param("userIdx") int userIdx,
                               @Param("isPayment") boolean isPayment);

    ChungyakAccountDTO findAccountByUserIdx(@Param("userIdx") int userIdx);

    String findEarliestAccountStartDate(@Param("userIdx") int userIdx);
}

