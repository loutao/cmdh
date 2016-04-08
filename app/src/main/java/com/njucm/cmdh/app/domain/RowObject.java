package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 4/6/15.
 */
public class RowObject {
    private String question;
    private int ID;
    private boolean firstChecked;
    private boolean secondChecked;
    private boolean threeChecked;
    private boolean fourChecked;
    private boolean fiveChecked;

    public RowObject(){}
    public RowObject(int id, boolean firstChecked, boolean secondChecked, boolean threeChecked, boolean fourChecked, boolean fiveChecked){
        super();

        ID = id;
        this.firstChecked =firstChecked;
        this.secondChecked =secondChecked;
        this.threeChecked =threeChecked;
        this.fourChecked= fourChecked;
        this.fiveChecked=fiveChecked;
    }
    public RowObject(String question){
        this.question = question;
    }
    public String getQuestion(){
        return question;
    }
    public void setQuestion(String question){
        this.question = question;
    }
    public boolean isFirstChecked() {
        return firstChecked;
    }

    public void setFirstChecked(boolean firstChecked) {
        this.firstChecked = firstChecked;
    }

    public boolean isSecondChecked() {
        return secondChecked;
    }

    public void setSecondChecked(boolean secondChecked) {
        this.secondChecked = secondChecked;
    }

    public boolean isThreeChecked() {
        return threeChecked;
    }

    public void setThreeChecked(boolean threeChecked) {
        this.threeChecked = threeChecked;
    }

    public boolean isFourChecked() {
        return fourChecked;
    }

    public void setFourChecked(boolean fourChecked) {
        this.fourChecked = fourChecked;
    }

    public boolean isFiveChecked() {
        return fiveChecked;
    }

    public void setFiveChecked(boolean fiveChecked) {
        this.fiveChecked = fiveChecked;
    }
}
