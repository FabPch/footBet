package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Pronostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @NotNull
    @JsonBackReference
    private Game game;

    @Column(name = "res_team_1")
    @NotNull
    private int resTeam1;

    @Column(name = "res_team_2")
    @NotNull
    private int resTeam2;

    @ManyToOne
    @JoinColumn(name = "gambler_id")
    @JsonBackReference
    private Gambler gambler;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }
}
