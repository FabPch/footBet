package fr.arthb.motherrussia.dto;


import javax.validation.constraints.NotNull;

public class PronoDTO {

    private int prono1;

    private int prono2;

    private int gain;

    public PronoDTO(int prono1, int prono2, int gain) {
        this.prono1 = prono1;
        this.prono2 = prono2;
        this.gain = gain;
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

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }
}
