package ru.SilirdCo.Luxoft.Web.service;

import ru.SilirdCo.Luxoft.Web.DAO.IDAO;
import ru.SilirdCo.Luxoft.Web.DAO.UserDAO;
import ru.SilirdCo.Luxoft.Web.Model.User;

import java.util.List;

public class UserService implements IService<User> {

    private IDAO dao = new UserDAO();

    public List<User> getAll() {
        return dao.getAll();
    }
}
