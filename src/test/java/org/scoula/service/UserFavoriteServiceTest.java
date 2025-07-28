package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserFavoriteService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class UserFavoriteServiceTest {
    @Autowired
    private UserFavoriteService userFavoriteService;
    @Test
    void addFavorite() {
        userFavoriteService.addFavorite();

    }

    @Test
    void deleteFavorite() {
    }

    @Test
    void getFavorites() {
    }

    @Test
    void isFavorite() {
    }
}