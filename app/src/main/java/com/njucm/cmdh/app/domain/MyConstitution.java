package com.njucm.cmdh.app.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Mesogene on 3/30/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "identifyresultid",
    "temp_userid",
    "temp_physiqueinfoid",
    "constituteidentifytime",
    "constituteidentifyremarks",
    "constituteidentifyresult"
})
public class MyConstitution {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("identifyresultid")
    private Integer identifyresultid;

    @JsonProperty("temp_userid")
    private String temp_userid;

    @JsonProperty("temp_physiqueinfoid")
    private Integer temp_physiqueinfoid;

    @JsonProperty("constituteidentifytime")
    private Date constituteidentifytime;

    @JsonProperty("constituteidentifyremarks")
    private String constituteidentifyremarks;

    @JsonProperty("constituteidentifyresult")
    private String constituteidentifyresult;

    //忽略的字段
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MyConstitution(Long id, Integer identifyresultid, String temp_userid, Integer temp_physiqueinfoid, Date constituteidentifytime, String constituteidentifyremarks, String constituteidentifyresult, Map<String, Object> additionalProperties) {
        this.id = id;
        this.identifyresultid = identifyresultid;
        this.temp_userid = temp_userid;
        this.temp_physiqueinfoid = temp_physiqueinfoid;
        this.constituteidentifytime = constituteidentifytime;
        this.constituteidentifyremarks = constituteidentifyremarks;
        this.constituteidentifyresult = constituteidentifyresult;
        this.additionalProperties = additionalProperties;
    }

    public MyConstitution() {
    }

    @JsonProperty("identifyresultid")
    public Integer getIdentifyresultid() {
        return identifyresultid;
    }

    @JsonProperty("temp_userid")
    public String getTemp_userid() {
        return temp_userid;
    }

    @JsonProperty("temp_physiqueinfoid")
    public Integer getTemp_physiqueinfoid() {
        return temp_physiqueinfoid;
    }

    @JsonProperty("constituteidentifytime")
    public Date getConstituteidentifytime() {
        return constituteidentifytime;
    }

    @JsonProperty("constituteidentifyremarks")
    public String getConstituteidentifyremarks() {
        return constituteidentifyremarks;
    }

    @JsonProperty("constituteidentifyresult")
    public String getConstituteidentifyresult() {
        return constituteidentifyresult;
    }


    @JsonProperty("identifyresultid")
    public void setIdentifyresultid(Integer identifyresultid) {
        this.identifyresultid = identifyresultid;
    }

    @JsonProperty("temp_userid")
    public void setTemp_userid(String temp_userid) {
        this.temp_userid = temp_userid;
    }

    @JsonProperty("temp_physiqueinfoid")
    public void setTemp_physiqueinfoid(Integer temp_physiqueinfoid) {
        this.temp_physiqueinfoid = temp_physiqueinfoid;
    }


    @JsonProperty("constituteidentifytime")
    public void setConstituteidentifytime(Date constituteidentifytime) {
        this.constituteidentifytime = constituteidentifytime;
    }

    @JsonProperty("constituteidentifyremarks")
    public void setConstituteidentifyremarks(String constituteidentifyremarks) {
        this.constituteidentifyremarks = constituteidentifyremarks;
    }

    @JsonProperty("constituteidentifyresult")
    public void setConstituteidentifyresult(String constituteidentifyresult) {
        this.constituteidentifyresult = constituteidentifyresult;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }
}
