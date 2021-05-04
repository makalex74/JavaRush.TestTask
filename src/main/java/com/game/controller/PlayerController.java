package com.game.controller;

import com.game.model.Player;
import com.game.service.PlayerService;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PlayerController {

    private final PlayerServiceImpl service;

    //GETTING ALL
    @GetMapping("/rest/players")
    @ResponseBody
    public List<Player> index(HttpServletRequest request) {
        return service.findAll(request);
    }

    @Autowired
    public PlayerController(PlayerServiceImpl service) {
        this.service = service;
    }
}
