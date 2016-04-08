package com.njucm.cmdh.app.domain;

/**
 * Created by Mesogene on 4/18/15.
 */
public class MyCollectionDomain {

    private int mycollectionid;
    private String collectionclass;
    private int collectioncode;
    private int temp_userid;

    public MyCollectionDomain(int mycollectionid, String collectionclass, int collectioncode, int temp_userid) {
        this.mycollectionid = mycollectionid;
        this.collectionclass = collectionclass;
        this.collectioncode = collectioncode;
        this.temp_userid = temp_userid;
    }

    public MyCollectionDomain(String collectionclass, int collectioncode, int temp_userid) {
        this.collectionclass = collectionclass;
        this.collectioncode = collectioncode;
        this.temp_userid = temp_userid;
    }

    public int getMycollectionid() {
        return mycollectionid;
    }

    public void setMycollectionid(int mycollectionid) {
        this.mycollectionid = mycollectionid;
    }

    public String getCollectionclass() {
        return collectionclass;
    }

    public void setCollectionclass(String collectionclass) {
        this.collectionclass = collectionclass;
    }

    public int getCollectioncode() {
        return collectioncode;
    }

    public void setCollectioncode(int collectioncode) {
        this.collectioncode = collectioncode;
    }

    public int getTemp_userid() {
        return temp_userid;
    }

    public void setTemp_userid(int temp_userid) {
        this.temp_userid = temp_userid;
    }
}
