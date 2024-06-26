package com.darfik.skycast.servlet.authentication;

import com.darfik.skycast.enums.SkycastURL;
import com.darfik.skycast.model.dto.UserSessionDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends BaseAuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserSessionDTO userSessionDTO = new UserSessionDTO(req.getSession().getId());
            userService.logout(userSessionDTO);

            session.invalidate();
            Cookie cookie = new Cookie("sessionID", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + SkycastURL.HOME_URL.getValue());
        }
    }
}
