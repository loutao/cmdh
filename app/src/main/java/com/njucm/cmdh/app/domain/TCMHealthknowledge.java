package com.njucm.cmdh.app.domain;

import java.util.Date;
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
        "healthknowledgeid",
        "healthknowledgetitle",
        "healthknowledgecontent",
        "exersuggtime",
        "healthknowledgeremarks",
        "temp_healthknowltypeid",
        "healthknowledgecode",
        "healthknowledgeflag"
})
public class TCMHealthknowledge {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("temp_healthknowltypeid")
    private Integer temp_healthknowltypeid;
    @JsonProperty("healthknowledgeid")
    private Integer healthknowledgeid;
    @JsonProperty("healthknowledgetitle")
    private String healthknowledgetitle;
    @JsonProperty("healthknowledgecontent")
    private String healthknowledgecontent;
    @JsonProperty("exersuggtime")
    private Date exersuggtime;
    @JsonProperty("healthknowledgeremarks")
    private String healthknowledgeremarks;
    @JsonProperty("healthknowledgecode")
    private Integer healthknowledgecode;
    @JsonProperty
    private Integer healthknowledgeflag;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public TCMHealthknowledge() {
    }

    public TCMHealthknowledge(Long id) {
        this.id = id;
    }

   

    public TCMHealthknowledge(Long id, Integer healthknowledgeid, String healthknowledgetitle, String healthknowledgecontent, Date exersuggtime, Integer healthknowledgecode,
                              Integer temp_healthknowltypeid, String healthknowledgeremarks,Integer healthknowledgeflag) {
        this.id = id;
        this.healthknowledgeid = healthknowledgeid;
        this.healthknowledgetitle = healthknowledgetitle;
        this.healthknowledgecontent = healthknowledgecontent;
        this.exersuggtime = exersuggtime;
        this.healthknowledgecode = healthknowledgecode;
        this.temp_healthknowltypeid = temp_healthknowltypeid;
        this.healthknowledgeremarks = healthknowledgeremarks;
        this.healthknowledgeflag = healthknowledgeflag;
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
     * @return The healthknowledgeid
     */
    @JsonProperty("healthknowledgeid")
    public Integer getHealthknowledgeid() {
        return healthknowledgeid;
    }

    /**
     * @param healthknowledgeid The healthknowledgeid
     */
    @JsonProperty("healthknowledgeid")
    public void setHealthknowledgeid(Integer healthknowledgeid) {
        this.healthknowledgeid = healthknowledgeid;
    }

    public TCMHealthknowledge withHealthknowledgeid(Integer healthknowledgeid) {
        this.healthknowledgeid = healthknowledgeid;
        return this;
    }

    /**
     * @return The healthknowledgetitle
     */
    @JsonProperty("healthknowledgetitle")
    public String getHealthknowledgetitle() {
        return healthknowledgetitle;
    }

    /**
     * @param healthknowledgetitle The healthknowledgetitle
     */
    @JsonProperty("healthknowledgetitle")
    public void setHealthknowledgetitle(String healthknowledgetitle) {
        this.healthknowledgetitle = healthknowledgetitle;
    }

    public TCMHealthknowledge withHealthknowledgetitle(String healthknowledgetitle) {
        this.healthknowledgetitle = healthknowledgetitle;
        return this;
    }

    /**
     * @return The healthknowledgecontent
     */
    @JsonProperty("healthknowledgecontent")
    public String getHealthknowledgecontent() {
        return healthknowledgecontent;
    }

    /**
     * @param healthknowledgecontent The healthknowledgecontent
     */
    @JsonProperty("healthknowledgecontent")
    public void setHealthknowledgecontent(String healthknowledgecontent) {
        this.healthknowledgecontent = healthknowledgecontent;
    }

    public TCMHealthknowledge withHealthknowledgecontent(String healthknowledgecontent) {
        this.healthknowledgecontent = healthknowledgecontent;
        return this;
    }

    /**
     * @return The exersuggtime
     */
    @JsonProperty("exersuggtime")
    public Date getExersuggtime() {
        return exersuggtime;
    }

    /**
     * @param exersuggtime The exersuggtime
     */
    @JsonProperty("exersuggtime")
    public void setExersuggtime(Date exersuggtime) {
        this.exersuggtime = exersuggtime;
    }

    public TCMHealthknowledge withExersuggtime(Date exersuggtime) {
        this.exersuggtime = exersuggtime;
        return this;
    }


    /**
     * @return The healthknowledgeremarks
     */
    @JsonProperty("healthknowledgeremarks")
    public String getHealthknowledgeremarks() {
        return healthknowledgeremarks;
    }

    /**
     * @param healthknowledgeremarks The healthknowledgeremarks
     */
    @JsonProperty("healthknowledgeremarks")
    public void setHealthknowledgeremarks(String healthknowledgeremarks) {
        this.healthknowledgeremarks = healthknowledgeremarks;
    }

    public TCMHealthknowledge withHealthknowledgeremarks(String healthknowledgeremarks) {
        this.healthknowledgeremarks = healthknowledgeremarks;
        return this;
    }

    /**
     * @return The tempHealthknowltypeid
     */

    /**
     * @return The healthknowledgecode
     */
    @JsonProperty("healthknowledgecode")
    public Integer getHealthknowledgecode() {
        return healthknowledgecode;
    }

    /**
     * @param healthknowledgecode The healthknowledgecode
     */
    @JsonProperty("healthknowledgecode")
    public void setHealthknowledgecode(Integer healthknowledgecode) {
        this.healthknowledgecode = healthknowledgecode;
    }

    public TCMHealthknowledge withHealthknowledgecode(Integer healthknowledgecode) {
        this.healthknowledgecode = healthknowledgecode;
        return this;
    }

    @JsonProperty("temp_healthknowltypeid")
    public Integer getTemp_healthknowltypeid() {
        return temp_healthknowltypeid;
    }

    @JsonProperty("temp_healthknowltypeid")
    public void setTemp_healthknowltypeid(Integer temp_healthknowltypeid) {
        this.temp_healthknowltypeid = temp_healthknowltypeid;
    }
    @JsonProperty("healthknowledgeflag")
    public Integer getHealthknowledgeflag() {
        return healthknowledgeflag;
    }
    @JsonProperty("healthknowledgeflag")
    public void setHealthknowledgeflag(Integer healthknowledgeflag) {
        this.healthknowledgeflag = healthknowledgeflag;
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public TCMHealthknowledge withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
