package ru.SilirdCo.Luxoft.impl.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final static Logger logger = LoggerFactory.getLogger(Network.class);

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
