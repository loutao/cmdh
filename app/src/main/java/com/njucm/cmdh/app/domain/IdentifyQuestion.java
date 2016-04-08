package com.njucm.cmdh.app.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mesogene on 3/30/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "identifyissueid",
        "temp_physiqueinfoid",
        "identifyissuecontent",
        "identifyissueremarks",


})
public class IdentifyQuestion {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("identifyissueid")
    private Integer identifyissueid;

    @JsonProperty("temp_physiqueinfoid")
    private Integer temp_physiqueinfoid;

    @JsonProperty("identifyissuecontent")
    private String identifyissuecontent;

    @JsonProperty("identifyissueremarks")
    private String identifyissueremarks;



    //忽略的字段
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public IdentifyQuestion(Long id, Integer identifyissueid, Integer temp_physiqueinfoid,String identifyissuecontent, String identifyissueremarks, Map<String, Object> additionalProperties) {
        this.id = id;
        this.identifyissueid = identifyissueid;
        this.temp_physiqueinfoid = temp_physiqueinfoid;
        this.identifyissuecontent = identifyissuecontent;
        this.identifyissueremarks = identifyissueremarks;
        this.additionalProperties = additionalProperties;

    }


    @JsonProperty("identifyissueid")
    public Integer getIdentifyissueid() {
        return identifyissueid;
    }

    @JsonProperty("temp_physiqueinfoid")
    public Integer getTemp_physiqueinfoid() {
        return temp_physiqueinfoid;
    }

    @JsonProperty("identifyissuecontent")
    public String getIdentifyissuecontent() {
        return identifyissuecontent;
    }

    @JsonProperty("identifyissueremarks")
    public String getIdentifyissueremarks() {
        return identifyissueremarks;
    }

    @JsonProperty("identifyissueid")
    public void setIdentifyissueid(Integer identifyissueid) {
        this.identifyissueid = identifyissueid;
    }


    @JsonProperty("temp_physiqueinfoid")
    public void setTemp_physiqueinfoid(Integer temp_physiqueinfoid) {
        this.temp_physiqueinfoid = temp_physiqueinfoid;
    }

    @JsonProperty("identifyissuecontent")
    public void setIdentifyissuecontent(String identifyissuecontent) {
        this.identifyissuecontent = identifyissuecontent;
    }

    @JsonProperty("identifyissueremarks")
    public void setIdentifyissueremarks(String identifyissueremarks) {
        this.identifyissueremarks = identifyissueremarks;
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
