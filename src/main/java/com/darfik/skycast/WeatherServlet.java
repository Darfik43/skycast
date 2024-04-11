package com.darfik.skycast;

import java.io.*;
import java.net.URISyntaxException;

import com.darfik.skycast.utils.OpenWeatherAPIService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/weather-servlet")
public class WeatherServlet extends HttpServlet {

    private ResponseProcessingService responseProcessingService;
    private final String jsonType = "weather";

    public void init() {
        responseProcessingService = new ResponseProcessingService();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String json = null;
        try {
            json = OpenWeatherAPIService.getTemperatureByName("London");
            resp.getWriter().print(responseProcessingService.processJson(json, jsonType));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}