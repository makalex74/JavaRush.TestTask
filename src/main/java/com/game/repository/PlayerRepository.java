package com.game.repository;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("Select p from Player p where (:name is null or p.name LIKE %:name%) " +
            "and (:title is null or p.title LIKE %:title%) " +
            "and (:race is null or p.race = :race)" +
            "and (:profession is null or p.profession = :profession)" +
            "and (:birthday is null or p.birthday >= :birthday) " +
            "and (:banned is null or p.banned = :banned)" +
            "and (:experience is null or p.experience = :experience)" +
            "and (:level is null or p.level = :level)" +
            "and (:untilNextLevel is null or p.untilNextLevel <= :untilNextLevel)")
    List<Player> getAll(@Param("name") String name,
                        @Param("title") String title,
                        @Param("race") Race race,
                        @Param("profession") Profession profession,
                        @Param("birthday") Date birthday,
                        @Param("banned") Integer banned,
                        @Param("experience") Integer experience,
                        @Param("level") Integer level,
                        @Param("untilNextLevel") Integer untilNextLevel,
                        Pageable pageable);

}
