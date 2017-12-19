package ru.SilirdCo.Luxoft.Web.DAO;

import ru.SilirdCo.Luxoft.Web.Model.User;

import java.util.List;

public interface IDAO<Data> {
    List<Data> getAll();
}
