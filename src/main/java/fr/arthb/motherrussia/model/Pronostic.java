package fr.arthb.motherrussia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer prono1;

    @Column
    @NotNull
    private Integer prono2;

    @Column
    private Integer prono1p;

    @Column
    private Integer prono2p;

    @Column
    private Integer winner;

    @Column
    private Integer gain;

    @ManyToOne
    @JoinColumn(name = "game_id")
//    @JsonBackReference
    private Game game;

    @ManyToOne
    @JoinColumn(name = "gambler_id")
//    @JsonBackReference
    @JsonIgnore
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

    public Integer getProno1() {
        return prono1;
    }

    public void setProno1(Integer prono1) {
        this.prono1 = prono1;
    }

    public Integer getProno2() {
        return prono2;
    }

    public void setProno2(Integer prono2) {
        this.prono2 = prono2;
    }

    public Integer getProno1p() {
        return prono1p;
    }

    public void setProno1p(Integer prono1p) {
        this.prono1p = prono1p;
    }

    public Integer getProno2p() {
        return prono2p;
    }

    public void setProno2p(Integer prono2p) {
        this.prono2p = prono2p;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getGain() {
        return gain;
    }

    public void setGain(Integer gain) {
        this.gain = gain;
    }
}
