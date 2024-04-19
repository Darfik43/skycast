package com.darfik.skycast.servlet;


import com.darfik.skycast.location.LocationDTO;
import com.darfik.skycast.location.LocationService;
import com.darfik.skycast.location.LocationServiceFactory;
import com.darfik.skycast.usersession.UserSessionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/add")
public class LocationServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // To be tested

        try {
            LocationService locationService = LocationServiceFactory.build();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("locationName"));
            UserSessionDTO userSessionDTO = new UserSessionDTO(req.getSession().getId());
            locationService.addLocationForUser(locationDTO, userSessionDTO);
        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        }
    }
}