package com.yourgame.model.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HandleStayedLoggedIn {
    private static HandleStayedLoggedIn instance;
    public static HandleStayedLoggedIn getInstance() {
        if (instance == null) {
            try {
                instance = new HandleStayedLoggedIn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private final File file = new File("users.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<User> users;

    public HandleStayedLoggedIn() throws IOException {
        if (file.exists()) {
            users = mapper.readValue(file, new TypeReference<>() {});
        } else {
            users = new ArrayList<>();
        }
    }

    public void addUser(User user) throws IOException {
        users.add(user);
        saveToFile();
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }

    public void updateUser(String username, User updatedUser) throws IOException {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.set(i, updatedUser);
                saveToFile();
                return;
            }
        }
    }

    public void saveToFile() throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }

    public List<User> getAllUsers() {
        return users;
    }
}
