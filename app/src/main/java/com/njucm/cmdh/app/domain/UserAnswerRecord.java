package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 4/18/15.
 */
public class UserAnswerRecord {
    private int temp_identifyissueid;
    private int temp_identifychoiceid;
    private String temp_userid;
    private int getscore;
    private  int answerflag;

    private String answertime;

    public UserAnswerRecord(int temp_identifyissueid, int temp_identifychoiceid, String temp_userid, int getscore, int answerflag, String timestamp) {
        this.temp_identifyissueid = temp_identifyissueid;
        this.temp_identifychoiceid = temp_identifychoiceid;
        this.temp_userid = temp_userid;
        this.getscore = getscore;
        this.answerflag = answerflag;
        this.answertime = timestamp;
    }

    public int getTemp_identifyissueid() {
        return temp_identifyissueid;
    }

    public void setTemp_identifyissueid(int temp_identifyissueid) {
        this.temp_identifyissueid = temp_identifyissueid;
    }

    public int getTemp_identifychoiceid() {
        return temp_identifychoiceid;
    }

    public void setTemp_identifychoiceid(int temp_identifychoiceid) {
        this.temp_identifychoiceid = temp_identifychoiceid;
    }

    public String getTemp_userid() {
        return temp_userid;
    }

    public void setTemp_userid(String temp_userid) {
        this.temp_userid = temp_userid;
    }

    public int getGetscore() {
        return getscore;
    }

    public void setGetscore(int getscore) {
        this.getscore = getscore;
    }

    public int getAnswerflag() {
        return answerflag;
    }

    public void setAnswerflag(int answerflag) {
        this.answerflag = answerflag;
    }

    public String getAnswertime() {
        return answertime;
    }

    public void setAnswertime(String answertime) {
        this.answertime = answertime;
    }

    public UserAnswerRecord() {

    }


}
