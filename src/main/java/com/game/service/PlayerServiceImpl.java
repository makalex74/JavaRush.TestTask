package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.Player;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.repository.PlayerRepository;
import com.google.protobuf.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
        Integer exp = player.getExperience();
        Double lvlDouble = (Math.sqrt(2500 + 200 * exp) - 50) / 100;
        Integer lvl = lvlDouble.intValue();
        player.setLevel(lvl);
        Integer untilNextLevel = 50 * (lvl + 1) * (lvl + 2) - exp;
        player.setUntilNextLevel(untilNextLevel);
    }

    @Override
    public Player getPlayerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deletePlayer(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Player updatePlayer(Long id, Player newPlayer) {

        Player oldPlayer = getPlayerById(id);

        if (newPlayer.getName() != null) {
            oldPlayer.setName(newPlayer.getName());
            validateName(newPlayer.getName());
        }
        if (newPlayer.getTitle() != null) {
            oldPlayer.setTitle(newPlayer.getTitle());
            validateTitle(newPlayer.getTitle());
        }
        if (newPlayer.getRace() != null) {
            oldPlayer.setRace(newPlayer.getRace());
            if (newPlayer.getRace() == null) throw new BadRequestException("Wrong player race!");
        }
        if (newPlayer.getProfession() != null) {
            oldPlayer.setProfession(newPlayer.getProfession());
            if (newPlayer.getProfession() == null) throw new BadRequestException("Wrong player profession!");
        }
        if (newPlayer.getBirthday() != null) {
            oldPlayer.setBirthday(newPlayer.getBirthday());
            validateBirthday(newPlayer.getBirthday());
        }
        if (newPlayer.getExperience() != null) {
            oldPlayer.setExperience(newPlayer.getExperience());
            validateExperience(newPlayer.getExperience());
        }

        if(newPlayer.getBanned() != null) {
            oldPlayer.setBanned(newPlayer.getBanned());
        }

        calculateAndSetLevel(oldPlayer);

        repository.save(oldPlayer);

        return oldPlayer;
    }

    private void validateId(Long id) {
        if (id <= 0) {
            throw new BadRequestException("Player id is invalid!");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 12)
            throw new BadRequestException("Wrong player name!");
    }

    private void validateTitle(String title) {
        if (title == null || title.isEmpty() || title.length() > 30)
            throw new BadRequestException("Wrong player title!");
    }

    private void validateExperience(Integer experience) {
        if (experience == null || experience < 0 || experience > 10000000)
            throw new BadRequestException("Wrong player experience!");
    }

    private void validateBirthday(Date birthday) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        Date from = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, 3000);
        Date to = calendar2.getTime();

        if (birthday.getTime() <= 0 || birthday.before(from) || birthday.after(to)) {
            throw new BadRequestException("Wrong player birthday!");
        }
    }

}
