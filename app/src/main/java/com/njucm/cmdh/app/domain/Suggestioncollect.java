package com.njucm.cmdh.app.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Mesogene on 4/18/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "mycollectionid",
        "collectionclass",
        "collectioncode",
        "temp_userid"
})
public class Suggestioncollect {

    @JsonProperty("id")
    private long id;

    @JsonProperty("mycollectionid")
    private int mycollectionid;

    @JsonProperty("collectionclass")
    private String collectionclass;

    @JsonProperty("collectioncode")
    private int collectioncode;

    @JsonProperty("temp_userid")
    private int temp_userid;

    public Suggestioncollect(String collectionclass, int collectioncode, int temp_userid) {
        this.collectionclass = collectionclass;
        this.collectioncode = collectioncode;
        this.temp_userid = temp_userid;
    }

    public Suggestioncollect(int mycollectionid, String collectionclass, int collectioncode, int temp_userid) {
        this.mycollectionid = mycollectionid;
        this.collectionclass = collectionclass;
        this.collectioncode = collectioncode;
        this.temp_userid = temp_userid;
    }

    @JsonProperty("mycollectionid")
    public int getMycollectionid() {
        return mycollectionid;
    }

    @JsonProperty("mycollectionid")
    public void setMycollectionid(int mycollectionid) {
        this.mycollectionid = mycollectionid;
    }

    @JsonProperty("collectionclass")
    public String getCollectionclass() {
        return collectionclass;
    }

    @JsonProperty("collectionclass")
    public void setCollectionclass(String collectionclass) {
        this.collectionclass = collectionclass;
    }

    @JsonProperty("collectioncode")
    public int getCollectioncode() {
        return collectioncode;
    }

    @JsonProperty("collectioncode")
    public void setCollectioncode(int collectioncode) {
        this.collectioncode = collectioncode;
    }

    @JsonProperty("temp_userid")
    public int getTemp_userid() {
        return temp_userid;
    }

    @JsonProperty("temp_userid")
    public void setTemp_userid(int temp_userid) {
        this.temp_userid = temp_userid;
    }
}
