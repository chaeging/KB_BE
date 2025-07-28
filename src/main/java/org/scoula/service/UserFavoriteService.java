package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.dto.UserFavoriteDTO;
import org.scoula.mapper.UserFavoriteMapper;
import org.scoula.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFavoriteService {
    private final UserFavoriteMapper userFavoriteMapper;

    // 즐겨찾기 추가
    public boolean addFavorite(int usersIdx, String houseType, int noticeIdx) {
        try {
            UserFavoriteDTO favorite = new UserFavoriteDTO();
            favorite.setUsersIdx(usersIdx);
            if ("APT".equals(houseType) || "신혼희망타운".equals(houseType)) {
                favorite.setAptIdx(noticeIdx);
                favorite.setOffiIdx(null);
            } else {
                favorite.setOffiIdx(noticeIdx);
                favorite.setAptIdx(null);
            }

            if (userFavoriteMapper.insertUserFavorite(favorite) == 1) {
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // 즐겨찾기 해제
    public boolean deleteFavorite(int userFavoriteIdx) {
        try {
            if (userFavoriteMapper.deleteUserFavorite(userFavoriteIdx) == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // 즐겨찾기 목록 조회
    public List<UserFavoriteDTO> getFavorites(int usersIdx) {
        return userFavoriteMapper.findFavoritesByUsersIdx(usersIdx);
    }

    // 즐겨찾기 여부 확인
    public boolean isFavorite(int usersIdx, String houseType, int noticeIdx) {
        if (houseType.equals("APT") || houseType.equals("신혼희망타운")) {
            return userFavoriteMapper.isFavoriteAPT(usersIdx, noticeIdx);
        }
        return userFavoriteMapper.isFavoriteOFFI(usersIdx, noticeIdx);
    }
}