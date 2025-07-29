package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.scoula.dto.ChungyakAccountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper {

    void insertChungyakAccount(@Param("dto") ChungyakAccountDTO dto,
                               @Param("userIdx") int userIdx,
                               @Param("isPayment") boolean isPayment);

    // 단일 계좌 반환용 (최신 등록된 계좌 하나)
    ChungyakAccountDTO findAccountByUserIdx(@Param("userIdx") int userIdx);

    // 최초 계좌 개설일 조회
    String findEarliestAccountStartDate(@Param("userIdx") int userIdx);
}

