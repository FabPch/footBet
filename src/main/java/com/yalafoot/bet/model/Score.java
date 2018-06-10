package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_id")
    @NotNull
    private int gameId;

    @Column(name = "res_team_1")
    @NotNull
    private int resTeam1;

    @Column(name = "res_team_2")
    @NotNull
    private int resTeam2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getResTeam1() {
        return resTeam1;
    }

    public void setResTeam1(int resTeam1) {
        this.resTeam1 = resTeam1;
    }

    public int getResTeam2() {
        return resTeam2;
    }

    public void setResTeam2(int resTeam2) {
        this.resTeam2 = resTeam2;
    }
}
