package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PlayerService {

    List<Player> getPlayersList(String name,
                                String title,
                                Race race,
                                Profession profession,
                                Long after,
                                Long before,
                                Boolean banned,
                                Integer minExperience,
                                Integer maxExperience,
                                Integer minLevel,
                                Integer maxLevel,
                                PlayerOrder order,
                                Integer pageNumber,
                                Integer pageSize);

    Integer getPlayersCount(String name,
                                 String title,
                                 Race race,
                                 Profession profession,
                                 Long after,
                                 Long before,
                                 Boolean banned,
                                 Integer minExperience,
                                 Integer maxExperience,
                                 Integer minLevel,
                                 Integer maxLevel);

    Player createPlayer(Player player);

    Player getPlayerById(Long id);

    Player update(Player player);

    void delete(Long id);
}
