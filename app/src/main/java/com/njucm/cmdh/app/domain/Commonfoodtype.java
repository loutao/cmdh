package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 7/19/15.
 */
public class Commonfoodtype {
    private Long id;
    private Integer commonfoodtypeid;
    private String commonfoodtypename;
    private Integer commonfoodtypecode;
    private String foodtypeexplain;
    private Integer foodwidecategoryid;
    public Commonfoodtype(Long id,Integer commonfoodtypeid,String commonfoodtypename,Integer commonfoodtypecode,String foodtypeexplain,Integer foodwidecategoryid){
        this.id = id;
        this.commonfoodtypeid = commonfoodtypeid;
        this.commonfoodtypename =commonfoodtypename;
        this.commonfoodtypecode = commonfoodtypecode;
        this.foodtypeexplain = foodtypeexplain;
        this.foodwidecategoryid = foodwidecategoryid;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Integer getCommonfoodtypeid(){
        return  commonfoodtypeid;
    }
    public void setCommonfoodtypeid(Integer commonfoodtypeid){
        this.commonfoodtypeid = commonfoodtypeid;
    }
    public String getCommonfoodtypename(){
        return commonfoodtypename;
    }
    public void setCommonfoodtypename(String commonfoodtypename){
        this.commonfoodtypename = commonfoodtypename;
    }
    public Integer getCommonfoodtypecode(){
        return commonfoodtypecode;
    }
    public void setCommonfoodtypecode(Integer commonfoodtypecode){
        this.commonfoodtypecode = commonfoodtypecode;
    }
    public String getFoodtypeexplain(){
        return foodtypeexplain;
    }
    public void setFoodtypeexplain(String foodtypeexplain){
        this.foodtypeexplain = foodtypeexplain;
    }
    public Integer getFoodwidecategoryid(){
        return foodwidecategoryid;
    }
    public void setFoodwidecategoryid(Integer foodwidecategoryid){
        this.foodwidecategoryid = foodwidecategoryid;
    }
    public static final int TYPE_CHECKED = 1;
    public static final int TYPE_NOCHECKED = 0;
    public int type;
    public Commonfoodtype(int type){
        this.type = type;
    }
}
