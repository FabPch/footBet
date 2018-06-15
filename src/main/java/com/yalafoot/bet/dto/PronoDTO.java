package com.yalafoot.bet.dto;

import javax.validation.constraints.NotNull;

public class PronoDTO {

    private int prono1;

    private int prono2;

    public PronoDTO(int prono1, int prono2) {
        this.prono1 = prono1;
        this.prono2 = prono2;
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
