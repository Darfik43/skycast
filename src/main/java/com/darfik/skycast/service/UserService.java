package com.darfik.skycast.service;

import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.DatabaseException;
import com.darfik.skycast.exception.InvalidCredentialsException;
import com.darfik.skycast.exception.UserAlreadyExistsException;
import com.darfik.skycast.mapper.UserMapper;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.UserSessionDTO;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;

@AllArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final UserSessionService userSessionService;
    private final PasswordEncryptor passwordEncryptor;

    public void registerUser(UserDTO userDTO) throws UserAlreadyExistsException, DatabaseException {
            User user = UserMapper.toModel(userDTO);
        if (!userExists(user.getUsername())) {
            try {
                user.setPassword(passwordEncryptor.encryptPassword(userDTO.getPassword()));
                userDAO.save(user);
            } catch (HibernateException e) {
                throw new DatabaseException("Error saving user");
            }
        } else {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public void authorizeUser(UserDTO userDTO, UserSessionDTO userSessionDTO) throws InvalidCredentialsException {
        if (userExists(userDTO.getUsername()) && isPasswordCorrect(userDTO)) {
            userDAO.find(userDTO.getUsername())
                    .ifPresent(user -> userSessionService.updateUserSessions(user, userSessionDTO));
        } else {
            throw new InvalidCredentialsException("Incorrect username or password");
        }
    }

    private boolean isPasswordCorrect(UserDTO userDTO) {
        return userDAO.find(userDTO.getUsername())
                .map(user -> passwordEncryptor.verifyPassword(userDTO.getPassword(), user.getPassword())).orElse(false);
    }

    private boolean userExists(String username) {
        return userDAO.find(username).isPresent();
    }

    public void logout(UserSessionDTO userSessionDTO) {
        if (userSessionDTO != null) {
            userSessionService.logout(userSessionDTO);
        }
    }
}
