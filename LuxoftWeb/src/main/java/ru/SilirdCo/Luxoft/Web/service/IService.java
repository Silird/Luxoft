package ru.SilirdCo.Luxoft.Web.service;

import ru.SilirdCo.Luxoft.Web.Model.User;

import java.util.List;

public interface IService<Data> {
    List<Data> getAll();
}
