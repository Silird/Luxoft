package ru.SilirdCo.Luxoft.Web.DAO;

import ru.SilirdCo.Luxoft.Web.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IDAO<User> {

    public List<User> getAll()
    {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1);
        user1.setLogin("Alex");
        user1.setPassword("123");
        users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setLogin("Silird");
        user2.setPassword("321");
        users.add(user2);

        return users;
    }
}
