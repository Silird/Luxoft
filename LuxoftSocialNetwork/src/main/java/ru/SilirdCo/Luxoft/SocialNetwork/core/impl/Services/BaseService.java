package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.BaseEntity;
import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.DAO.IDAO;
import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.Services.IService;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseService<Data extends BaseEntity> implements IService<Data> {

    private final IDAO<Data> dao;

    protected BaseService(IDAO<Data> dao) {
        this.dao = dao;
    }

    @Override
    public List<Data> getElements() {
        List<Data> result;

        try {
            result = dao.getAll();
        }
        catch (PersistenceException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public Data getFirstResult() {
        Data result;

        try {
            result = dao.getFirstResult();
        }
        catch (PersistenceException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public Data findById(Integer id) {
        Data result;

        try {
            result = dao.getByID(id);
        }
        catch (PersistenceException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public List<Data> findByAttribute(List<ElementAttribute> attributes) {
        List<Data> result;

        try {
            result = dao.getByAttribute(attributes);
        }
        catch (PersistenceException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public Data save(Data entity) {
        Data result;

        try {
            result = dao.update(entity);
        }
        catch (PersistenceException ex) {
            result = null;
        }

        return result;
    }

    @Override
    public List<Data> save(List<Data> entities) {
        List<Data> result = new ArrayList<>();

        try {
            if (entities != null) {
                for (Data entity : entities) {
                    result.add(dao.update(entity));
                }
            }
        }
        catch (PersistenceException ignored) {
        }

        return result;
    }

    @Override
    public Boolean remove(Integer id) {
        Boolean result;

        try {
            dao.remove(id);
            result = true;
        }
        catch (PersistenceException ex) {
            result = false;
        }

        return result;
    }

    @Override
    public Boolean removeList(List<Integer> list) {
        Boolean result;

        try {
            if (list != null) {
                for (Integer id : list) {
                    dao.remove(id);
                }
            }
            result = true;
        }
        catch (PersistenceException ex) {
            result = false;
        }

        return result;
    }
}
