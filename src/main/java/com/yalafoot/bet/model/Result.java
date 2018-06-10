package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "gambler_id")
    private Gambler gambler;

    @ManyToOne
    @JoinColumn(name = "pronostic_id")
    private Pronostic pronostic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gambler getGambler() {
        return gambler;
    }

    public void setGambler(Gambler gambler) {
        this.gambler = gambler;
    }

    public Pronostic getPronostic() {
        return pronostic;
    }

    public void setPronostic(Pronostic pronostic) {
        this.pronostic = pronostic;
    }
}
