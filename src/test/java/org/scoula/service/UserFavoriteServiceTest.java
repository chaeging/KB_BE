package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserFavoriteService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class UserFavoriteServiceTest {
    UserFavoriteService userFavoriteService;
    @Test
    void addFavorite() {
        userFavoriteService.addFavorite(1, );
    }

    @Test
    void deleteFavorite() {
        userFavoriteService.deleteFavorite();
    }

    @Test
    void getFavorites() {
        userFavoriteService.getFavorites();
    }

    @Test
    void isFavorite() {
        userFavoriteService.isFavorite();
    }
}