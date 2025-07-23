package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.*;
import org.scoula.mapper.SelectedMapper;
import org.scoula.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserSelectedService {

    private final SelectedMapper selectedMapper;
    private final UserMapper userMapper;

    @Transactional
    public void saveAllPreferences(String userId, UserSelectedDTO dto) {
        int usersIdx = userMapper.findUserIdxByUserId(userId);

        // 1. user_info 테이블 hope price update
        selectedMapper.updateHomePrice(usersIdx, dto.getHomePrice());

        // 2. user_info_idx 가져오기
        int userInfoIdx = selectedMapper.findUserInfoIdxByUserIdx(usersIdx);

        // 3. 기존 선택 정보 삭제
        selectedMapper.deleteSelectedRegion(userInfoIdx);
        selectedMapper.deleteSelectedHomeSize(userInfoIdx);
        selectedMapper.deleteSelectedHomeType(userInfoIdx);

        // 4. 새로운 선택 정보 입력
        for (RegionDTO region : dto.getSelectedRegion()) {
            selectedMapper.insertSelectedRegion(userInfoIdx, region);
        }

        for (HomeSizeDTO homesize : dto.getSelectedHomeSize()) {
            selectedMapper.insertSelectedHomeSize(userInfoIdx, homesize);
        }

        for (HomeTypeDTO hometype : dto.getSelectedHomeType()) {
            selectedMapper.insertSelectedHomeType(userInfoIdx, hometype);
        }
    }

    public UserSelectedDTO getUserSelected(String userId) {
        int usersIdx = userMapper.findUserIdxByUserId(userId);
        int userInfoIdx = selectedMapper.findUserInfoIdxByUserIdx(usersIdx);

        HomePriceDTO homePrice = selectedMapper.selectHomePriceByUserIdx(usersIdx);
        List<RegionDTO> regions = selectedMapper.selectSelectedRegion(userInfoIdx);
        List<HomeSizeDTO> homesizes = selectedMapper.selectSelectedHomesize(userInfoIdx);
        List<HomeTypeDTO> hometypes = selectedMapper.selectSelectedHometype(userInfoIdx);

        return new UserSelectedDTO(homePrice, regions, homesizes, hometypes);
    }
}
