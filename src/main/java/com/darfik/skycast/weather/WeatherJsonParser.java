package com.darfik.skycast.weather;
import com.darfik.skycast.commons.services.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Optional;

public class WeatherJsonParser implements JsonParser<WeatherResponse> {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Override
    public Optional<WeatherResponse> parse(String json) {
        try {
            WeatherResponse result = jsonMapper.readValue(json, WeatherResponse.class);
            return Optional.ofNullable(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing weather JSON", e);
        }
    }
}