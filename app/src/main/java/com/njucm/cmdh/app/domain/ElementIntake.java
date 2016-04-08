package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 5/23/15.
 */
public class ElementIntake {
    private String uptime;
    private int intake;
    private String kCal;

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    public String getkCal() {
        return kCal;
    }

    public void setkCal(String kCal) {
        this.kCal = kCal;
    }
}
