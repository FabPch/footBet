package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_1")
    @NotNull
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_2")
    @NotNull
    private Team team2;

    @Column
    private Date date;

    @OneToMany(mappedBy = "game")
    private Set<Score> scores;

    @OneToMany(mappedBy = "game")
    private Set<Pronostic> pronostics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Pronostic> getPronostics() {
        return pronostics;
    }

    public void setPronostics(Set<Pronostic> pronostics) {
        this.pronostics = pronostics;
    }
}
