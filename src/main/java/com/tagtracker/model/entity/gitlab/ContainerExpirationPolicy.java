
package com.tagtracker.model.entity.gitlab;

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
    "cadence",
    "enabled",
    "keep_n",
    "older_than",
    "name_regex",
    "name_regex_keep",
    "next_run_at"
})
public class ContainerExpirationPolicy {

    @JsonProperty("cadence")
    private String cadence;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("keep_n")
    private Object keepN;
    @JsonProperty("older_than")
    private Object olderThan;
    @JsonProperty("name_regex")
    private Object nameRegex;
    @JsonProperty("name_regex_keep")
    private Object nameRegexKeep;
    @JsonProperty("next_run_at")
    private String nextRunAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cadence")
    public String getCadence() {
        return cadence;
    }

    @JsonProperty("cadence")
    public void setCadence(String cadence) {
        this.cadence = cadence;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("keep_n")
    public Object getKeepN() {
        return keepN;
    }

    @JsonProperty("keep_n")
    public void setKeepN(Object keepN) {
        this.keepN = keepN;
    }

    @JsonProperty("older_than")
    public Object getOlderThan() {
        return olderThan;
    }

    @JsonProperty("older_than")
    public void setOlderThan(Object olderThan) {
        this.olderThan = olderThan;
    }

    @JsonProperty("name_regex")
    public Object getNameRegex() {
        return nameRegex;
    }

    @JsonProperty("name_regex")
    public void setNameRegex(Object nameRegex) {
        this.nameRegex = nameRegex;
    }

    @JsonProperty("name_regex_keep")
    public Object getNameRegexKeep() {
        return nameRegexKeep;
    }

    @JsonProperty("name_regex_keep")
    public void setNameRegexKeep(Object nameRegexKeep) {
        this.nameRegexKeep = nameRegexKeep;
    }

    @JsonProperty("next_run_at")
    public String getNextRunAt() {
        return nextRunAt;
    }

    @JsonProperty("next_run_at")
    public void setNextRunAt(String nextRunAt) {
        this.nextRunAt = nextRunAt;
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
