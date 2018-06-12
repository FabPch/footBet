package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    private String name;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private Set<Pronostic> pronostics;
    @OneToMany(mappedBy = "team1")
    private Set<Game> games1;

    @OneToMany(mappedBy = "team2")
    private Set<Game> games2;

    public Set<Game> getGames1() {
        return games1;
    }

    public void setGames1(Set<Game> games1) {
        this.games1 = games1;
    }

    public Set<Game> getGames2() {
        return games2;
    }

    public void setGames2(Set<Game> games2) {
        this.games2 = games2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pronostic> getPronostics() {
        return pronostics;
    }

    public void setPronostics(Set<Pronostic> pronostics) {
        this.pronostics = pronostics;
    }
}
