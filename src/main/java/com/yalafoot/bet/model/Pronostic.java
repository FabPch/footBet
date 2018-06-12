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

    @Column
    @NotNull
    private int prono;

    @ManyToOne
    @JoinColumn(name = "game_id")
//    @JsonBackReference
    private Game game;

    @ManyToOne
    @JoinColumn(name = "team_id")
//    @JsonBackReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "gambler_id")
//    @JsonBackReference
    private Gambler gambler;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getProno() {
        return prono;
    }

    public void setProno(int prono) {
        this.prono = prono;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
