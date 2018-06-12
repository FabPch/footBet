package com.yalafoot.bet.dto;

import javax.validation.constraints.NotNull;

public class PronoDTO {

    @NotNull
    private int id;

    @NotNull
    private int gameId;

    private TeamDTO team1;

    private TeamDTO team2;

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

    public TeamDTO getTeam1() {
        return team1;
    }

    public void setTeam1(TeamDTO team1) {
        this.team1 = team1;
    }

    public TeamDTO getTeam2() {
        return team2;
    }

    public void setTeam2(TeamDTO team2) {
        this.team2 = team2;
    }
}
