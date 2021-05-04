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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Player> findAll(HttpServletRequest request) {
        Object[] attributes = getParameterOfPlayer(request);

        String pageNumberS = request.getParameter("pageNumber");
        Integer pageNumber = pageNumberS == null ? 0 : Integer.parseInt(pageNumberS);
        String pageSizeS = request.getParameter("pageSize");
        Integer pageSize = pageSizeS == null ? 3 : Integer.parseInt(pageSizeS);

        String orderS = request.getParameter("order");
        PlayerOrder order = orderS == null ? PlayerOrder.ID : PlayerOrder.valueOf(orderS);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return repository.getAll((String) attributes[0], (String) attributes[1], (Race) attributes[2],
                (Profession) attributes[3], (Date) attributes[4], (Integer) attributes[5], (Integer) attributes[6],
                (Integer) attributes[7], (Integer) attributes[8], pageable);
    }

    @Override
    public Integer getCount(HttpServletRequest request) {
        Object[] attributes = getParameterOfPlayer(request);

        return repository.getCount((String) attributes[0], (String) attributes[1], (Race) attributes[2],
                (Profession) attributes[3], (Date) attributes[4], (Integer) attributes[5], (Integer) attributes[6],
                (Integer) attributes[7], (Integer) attributes[8]);
    }

    private static Object[] getParameterOfPlayer(HttpServletRequest request) {
        Object[] result = new Object[9];

        result[0] = request.getParameter("name");
        result[1] = request.getParameter("title");
        String race = request.getParameter("race");
        result[2] = race == null ? null : Race.valueOf(race);
        String profession = request.getParameter("profession");
        result[3] = profession == null ? null : Profession.valueOf(profession);
        String birthday = request.getParameter("birthday");
        result[4] = birthday == null ? null : new Date(Long.parseLong(birthday));
        String banned = request.getParameter("banned");
        result[5] = banned == null ? null : Integer.parseInt(banned);
        String experience = request.getParameter("experience");
        result[6] = experience == null ? null : Integer.parseInt(experience);
        String level = request.getParameter("level");
        result[7] = level == null ? null : Integer.parseInt(level);
        String untilNextLevel = request.getParameter("untilNextLevel");
        result[8] = untilNextLevel == null ? null : Integer.parseInt(untilNextLevel);

        return result;
    }

    @Override
    public Player addShip(Player player) {
        return null;
    }

    @Override
    public Player getById(Long id) {
        return null;
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
