package com.darfik.skycast.weatherapi.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ConditionData {
    @JsonProperty("main")
    private String main;

    @JsonProperty("icon")
    private String icon;
}
