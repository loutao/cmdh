package com.njucm.cmdh.app.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

/**
 * Created by Mesogene on 4/1/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "userknowledgemappid",
        "healthknowledgetime",
        "userid",
        "username",
        "healthknowltypeid",
        "healthknowltypename",
        "healthknowledgetitle",
        "healthknowledgecontent",
        "healthknowledgecode"
})
public class HealthKnowledgeJoinUserMapp {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userknowledgemappid")
    private Integer userknowledgemappid;

    @JsonProperty("healthknowledgetime")
    private Date healthknowledgetime;

    @JsonProperty("userid")
    private Integer userid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("healthknowltypeid")
    private Integer healthknowltypeid;

    @JsonProperty("healthknowltypename")
    private String healthknowltypename;

    @JsonProperty("healthknowledgetitle")
    private String healthknowledgetitle;

    @JsonProperty("healthknowledgecontent")
    private String healthknowledgecontent;

    @JsonProperty("healthknowledgecode")
    private String healthknowledgecode;

    public HealthKnowledgeJoinUserMapp(Long id, Integer userknowledgemappid, Date healthknowledgetime, Integer userid, String username, Integer healthknowltypeid, String healthknowltypename, String healthknowledgetitle, String healthknowledgecontent) {
        this.id = id;
        this.userknowledgemappid = userknowledgemappid;
        this.healthknowledgetime = healthknowledgetime;
        this.userid = userid;
        this.username = username;
        this.healthknowltypeid = healthknowltypeid;
        this.healthknowltypename = healthknowltypename;
        this.healthknowledgetitle = healthknowledgetitle;
        this.healthknowledgecontent = healthknowledgecontent;
    }

    public HealthKnowledgeJoinUserMapp(Long id, Integer userknowledgemappid, Date healthknowledgetime, Integer userid, String username, Integer healthknowltypeid, String healthknowltypename, String healthknowledgetitle, String healthknowledgecontent, String healthknowledgecode) {
        this.id = id;
        this.userknowledgemappid = userknowledgemappid;
        this.healthknowledgetime = healthknowledgetime;
        this.userid = userid;
        this.username = username;
        this.healthknowltypeid = healthknowltypeid;
        this.healthknowltypename = healthknowltypename;
        this.healthknowledgetitle = healthknowledgetitle;
        this.healthknowledgecontent = healthknowledgecontent;
        this.healthknowledgecode = healthknowledgecode;
    }

    @JsonProperty("healthknowledgecode")
    public String getHealthknowledgecode() {
        return healthknowledgecode;
    }

    @JsonProperty("healthknowledgecode")
    public void setHealthknowledgecode(String healthknowledgecode) {
        this.healthknowledgecode = healthknowledgecode;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("userknowledgemappid")
    public Integer getUserknowledgemappid() {
        return userknowledgemappid;
    }

    @JsonProperty("healthknowledgetime")
    public Date getHealthknowledgetime() {
        return healthknowledgetime;
    }

    @JsonProperty("userid")
    public Integer getUserid() {
        return userid;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("healthknowltypeid")
    public Integer getHealthknowltypeid() {
        return healthknowltypeid;
    }

    @JsonProperty("healthknowltypename")
    public String getHealthknowltypename() {
        return healthknowltypename;
    }

    @JsonProperty("healthknowledgetitle")
    public String getHealthknowledgetitle() {
        return healthknowledgetitle;
    }

    @JsonProperty("healthknowledgecontent")
    public String getHealthknowledgecontent() {
        return healthknowledgecontent;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("userknowledgemappid")
    public void setUserknowledgemappid(Integer userknowledgemappid) {
        this.userknowledgemappid = userknowledgemappid;
    }

    @JsonProperty("healthknowledgetime")
    public void setHealthknowledgetime(Date healthknowledgetime) {
        this.healthknowledgetime = healthknowledgetime;
    }

    @JsonProperty("userid")
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("healthknowltypeid")
    public void setHealthknowltypeid(Integer healthknowltypeid) {
        this.healthknowltypeid = healthknowltypeid;
    }

    @JsonProperty("healthknowltypename")
    public void setHealthknowltypename(String healthknowltypename) {
        this.healthknowltypename = healthknowltypename;
    }

    @JsonProperty("healthknowledgetitle")
    public void setHealthknowledgetitle(String healthknowledgetitle) {
        this.healthknowledgetitle = healthknowledgetitle;
    }

    @JsonProperty("healthknowledgecontent")
    public void setHealthknowledgecontent(String healthknowledgecontent) {
        this.healthknowledgecontent = healthknowledgecontent;
    }
}
