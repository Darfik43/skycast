package com.darfik.skycast.service;
import com.darfik.skycast.dao.UserSessionDAO;
import com.darfik.skycast.mapper.UserSessionMapper;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.UserSession;
import com.darfik.skycast.model.dto.UserSessionDTO;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserSessionService {
    private final UserSessionDAO userSessionDAO;

    public void updateUserSessions(User user, UserSessionDTO userSessionDTO) {
        userSessionDAO.deleteExpiredSessions(user);

        UserSession userSession = UserSessionMapper.toModel(userSessionDTO);

        userSession.setId(userSessionDTO.id());
        userSession.setUser(user);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));

        userSessionDAO.update(userSession);
    }

    public void logout(UserSessionDTO userSessionDTO) {
        userSessionDAO.delete(userSessionDTO.id());
    }
}
