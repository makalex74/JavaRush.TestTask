package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository repository;

    public PlayerServiceImpl() {
    }

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Player> getPlayersList(String name,
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
                                       Integer pageSize) {

        Sort sort = order == null ? Sort.unsorted() : Sort.by(order.getFieldName());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return repository.find(
                pageable,
                name,
                title,
                race,
                profession,
                after == null ? null : new Date(after),
                before == null ? null : new Date(before),
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }

    @Override
    public Integer getPlayersCount(String name,
                                   String title,
                                   Race race,
                                   Profession profession,
                                   Long after,
                                   Long before,
                                   Boolean banned,
                                   Integer minExperience,
                                   Integer maxExperience,
                                   Integer minLevel,
                                   Integer maxLevel) {
        return repository.getCount(
                name,
                title,
                race,
                profession,
                after == null ? null : new Date(after),
                before == null ? null : new Date(before),
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }

    @Override
    public Player createPlayer(Player player) {
        calculateAndSetLevel(player);
        return repository.save(player);
    }

    private void calculateAndSetLevel(Player player) {
        Integer level = 0;
        player.setLevel(level);
        Integer untilNextLevel = 0;
        player.setUntilNextLevel(untilNextLevel);
    }

    @Override
    public Player getPlayerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    public static void main(String[] args) {

    }
}
