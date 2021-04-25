package com.game.service;

import com.game.model.Player;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PlayerService {
    List<Player> findAll(HttpServletRequest request);
    Integer getCount(HttpServletRequest request);
    Player addShip(Player player);
    Player getById(Long id);
    Player update(Player player);
    void delete(Long id);
}
