package com.njucm.cmdh.app.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "healthknowltypeid",
        "healthknowltypename",
        "healthknowltypecode",
        "classifyexplain",
        "healthknowltypereamrks"
})
public class HealthKnowledgeType {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("healthknowltypeid")
    private Integer healthknowltypeid;
    @JsonProperty("healthknowitypename")
    private String healthknowltypename;
    @JsonProperty("healthknowltypecode")
    private Integer healthknowltypecode;
    @JsonProperty("classifyexplain")
    private String classifyexplain;
    @JsonProperty("healthknowltypereamrks")
    private String healthknowltypereamrks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public HealthKnowledgeType() {
    }

    public HealthKnowledgeType(Long id) {
        this.id = id;
    }

    public HealthKnowledgeType(Long id, Integer healthknowltypeid, String healthknowltypename, 
                               String classifyexplain, Integer healthknowltypecode,String healthknowltypereamrks) {
        this.id = id;
        this.healthknowltypeid = healthknowltypeid;
        this.healthknowltypename = healthknowltypename;
        this.classifyexplain = classifyexplain;
        this.healthknowltypecode = healthknowltypecode;
        this.healthknowltypereamrks  = healthknowltypereamrks;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The healthknowltypeid
     */
    @JsonProperty("healthknowltypeid")
    public Integer getHealthknowltypeid() {
        return healthknowltypeid;
    }

    /**
     * @param healthknowltypeid The healthknowltypeid
     */
    @JsonProperty("healthknowltypeid")
    public void setHealthknowltypeid(Integer healthknowltypeid) {
        this.healthknowltypeid = healthknowltypeid;
    }

    /**
     * @return The healthknowltypename
     */
    @JsonProperty("healthknowltypename")
    public String getHealthknowltypename() {
        return healthknowltypename;
    }

    /**
     * @param healthknowltypename The healthknowltypename
     */
    @JsonProperty("healthknowltypename")
    public void setHealthknowltypename(String healthknowltypename) {
        this.healthknowltypename = healthknowltypename;
    }

    /**
     * @return The healthknowltypecode
     */
    @JsonProperty("healthknowltypecode")
    public Integer getHealthknowltypecode() {
        return healthknowltypecode;
    }

    /**
     * @param healthknowltypecode The healthknowltypecode
     */
    @JsonProperty("healthknowltypecode")
    public void setHealthknowltypecode(Integer healthknowltypecode) {
        this.healthknowltypecode = healthknowltypecode;
    }

    /**
     * @return The classifyexplain
     */
    @JsonProperty("classifyexplain")
    public String getClassifyexplain() {
        return classifyexplain;
    }

    /**
     * @param classifyexplain The classifyexplain
     */
    @JsonProperty("classifyexplain")
    public void setClassifyexplain(String classifyexplain) {
        this.classifyexplain = classifyexplain;
    }
    @JsonProperty("healthknowltypereamrks")
    public String getHealthknowltypereamrks() {
        return healthknowltypereamrks;
    }
    @JsonProperty("healthknowltypereamrks")
    public void setHealthknowltypereamrks(String healthknowltypereamrks) {
        this.healthknowltypereamrks = healthknowltypereamrks;
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    

}
