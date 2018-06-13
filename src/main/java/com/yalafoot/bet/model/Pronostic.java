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
    private int prono1;

    @Column
    @NotNull
    private int prono2;

    @ManyToOne
    @JoinColumn(name = "game_id")
//    @JsonBackReference
    private Game game;

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

    public int getProno1() {
        return prono1;
    }

    public void setProno1(int prono1) {
        this.prono1 = prono1;
    }

    public int getProno2() {
        return prono2;
    }

    public void setProno2(int prono2) {
        this.prono2 = prono2;
    }

}
