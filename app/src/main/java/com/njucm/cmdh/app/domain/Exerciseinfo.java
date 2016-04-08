package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 7/19/15.
 */
public class Exerciseinfo {
    private Long id;
    private Integer exerciseid;
    private String exercisename;
    private String exercisetypename;
    private String energywaste;
    private String exercisetip;
    private String exerciseaffect;
    private String temp_picturelocationid;
    private Integer temp_exercisetypeid;

    public Exerciseinfo(Long id, Integer exerciseid, String exercisename, String exercisetypename, String energywaste, String exercisetip, String exerciseaffect, String temp_picturelocationid, Integer temp_exercisetypeid) {
        this.id = id;
        this.exerciseid = exerciseid;
        this.exercisename = exercisename;
        this.exercisetypename = exercisetypename;
        this.energywaste = energywaste;
        this.exercisetip = exercisetip;
        this.exerciseaffect = exerciseaffect;
        this.temp_picturelocationid = temp_picturelocationid;
        this.temp_exercisetypeid = temp_exercisetypeid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExerciseid(Integer exerciseid) {
        this.exerciseid = exerciseid;
    }

    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }

    public void setExercisetypename(String exercisetypename) {
        this.exercisetypename = exercisetypename;
    }

    public void setEnergywaste(String energywaste) {
        this.energywaste = energywaste;
    }

    public void setExercisetip(String exercisetip) {
        this.exercisetip = exercisetip;
    }

    public void setExerciseaffect(String exerciseaffect) {
        this.exerciseaffect = exerciseaffect;
    }

    public void setTemp_picturelocationid(String temp_picturelocationid) {
        this.temp_picturelocationid = temp_picturelocationid;
    }

    public void setTemp_exercisetypeid(Integer temp_exercisetypeid) {
        this.temp_exercisetypeid = temp_exercisetypeid;
    }

    public Long getId() {
        return id;
    }

    public Integer getExerciseid() {
        return exerciseid;
    }

    public String getExercisename() {
        return exercisename;
    }

    public String getExercisetypename() {
        return exercisetypename;
    }

    public String getEnergywaste() {
        return energywaste;
    }

    public String getExercisetip() {
        return exercisetip;
    }

    public String getExerciseaffect() {
        return exerciseaffect;
    }

    public String getTemp_picturelocationid() {
        return temp_picturelocationid;
    }

    public Integer getTemp_exercisetypeid() {
        return temp_exercisetypeid;
    }
    public int type;
    public Exerciseinfo(int type){
        this.type = type;
    }
}
