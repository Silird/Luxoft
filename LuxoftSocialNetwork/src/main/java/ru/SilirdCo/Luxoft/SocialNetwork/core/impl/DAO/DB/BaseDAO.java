package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.DAO.DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.*;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.BaseEntity;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.PersistenceUtil;
import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.DAO.IDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseDAO<Data extends BaseEntity> implements IDAO<Data> {
    private static final Logger logger = LoggerFactory.getLogger(BaseDAO.class);

    /**
     * Название поля, по которому будет происходить сортировка
     */
    protected String orderAttribute = null;
    /**
     * Лямбда получения пустого объекта класса Data
     */
    protected Supplier<Data> emptyDataSupplier = null;
    /**
     * Класс, определяющий сущность, с которой работаем
     */
    protected Class<Data> genericClass = null;

    /**
     * Конструктор класса
     *
     * @param genericClass - Класс, определяющий сущность, с которой работаем
     * @param emptyDataSupplier - Лямбда получения пустого объекта класса Data
     */
    public BaseDAO(final Class<Data> genericClass, final Supplier<Data> emptyDataSupplier) {
        this.emptyDataSupplier = emptyDataSupplier;
        //innerEntity = ConstructorUtils.invokeConstructor(genericClass);
    }


    /**
     * Обновление существующей сущности
     *
     * @param entity - сущность, которую надо обновить (обновляется поле с таким же id)
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    @Override
    public Data update(Data entity) throws PersistenceException {
        PersistenceException persistenceException = null;
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();
                session.getTransaction().begin();

            entity = session.merge(entity);

            session.getTransaction().commit();
        }
        catch (Exception ex) {
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
        if (persistenceException != null) {
            throw persistenceException;
        }

        return entity;
    }

    /**
     * Удаление данной сущности
     *
     * @param entity - сущность для удаления (удаляется поле с таким же id)
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    @Override
    public void remove(Data entity) throws PersistenceException {
        PersistenceException persistenceException = null;
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();
                session.getTransaction().begin();

            session.remove(session.contains(entity) ? entity : session.merge(entity));

            session.getTransaction().commit();
        }
        catch (Exception ex) {
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
        if (persistenceException != null) {
            throw persistenceException;
        }
    }

    /**
     * Удаление данной сущности
     *
     * @param id - идентификатор сущности для удаления
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    @Override
    public void remove(Integer id) throws PersistenceException {
        Data innerEntity = emptyDataSupplier.get();
        innerEntity.setId(id);
        remove(innerEntity);
    }

    /**
     * Получение сущности по идентификатору
     *
     * @param id - идентификатор сущности для поиска
     * @return - объекта (банк) с данным идентификатором
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    @Override
    public Data getByID(Integer id) throws PersistenceException {
        PersistenceException persistenceException = null;
        Data entity = null;
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();

            entity = session.find(genericClass, id);
        }
        catch (Exception ex) {
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
        }
        if (persistenceException != null) {
            throw persistenceException;
        }

        return entity;
    }

    @Override
    public Data getFirstResult() throws PersistenceException {
        PersistenceException persistenceException = null;
        Data entity = null;
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Data> criteriaQuery = builder.createQuery(genericClass);
            Root<Data> root = criteriaQuery.from(genericClass);
            if (orderAttribute != null) {
                criteriaQuery.orderBy(builder.asc(root.get(orderAttribute)));
            }

            entity = session.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }
        catch (Exception ex) {
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
        }
        if (persistenceException != null) {
            throw persistenceException;
        }

        return entity;
    }

    /**
     * Получение списка всех сущностей
     *
     * @return - список всех сущностей
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    @Override
    public List<Data> getAll() throws PersistenceException {
        PersistenceException persistenceException = null;
        List<Data> entities = new ArrayList<>();
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Data> criteriaQuery = builder.createQuery(genericClass);
            Root<Data> root = criteriaQuery.from(genericClass);
            if (orderAttribute != null) {
                criteriaQuery.orderBy(builder.asc(root.get(orderAttribute)));
            }

            entities = session.createQuery(criteriaQuery).getResultList();
        }
        catch (Exception ex) {
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
        }
        if (persistenceException != null) {
            throw persistenceException;
        }
        return entities;
    }


    @Override
    public List<Data> getByAttribute(List<ElementAttribute> attributes) throws PersistenceException {
        PersistenceException persistenceException = null;
        if ((attributes == null) || (attributes.isEmpty())) {
            return getAll();
        }
        List<Data> entities = new ArrayList<>();
        EntityManager session = null;
        try {
            session = PersistenceUtil.getSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Data> criteriaQuery = builder.createQuery(genericClass);
            Root<Data> root = criteriaQuery.from(genericClass);

            Predicate all = getWherePredicate(attributes, builder, root);

            if (all == null) {
                return entities;
            }
            criteriaQuery.where(all);
            criteriaQuery.orderBy(builder.asc(root.get(orderAttribute)));

            entities = session.createQuery(criteriaQuery).getResultList();
        }
        catch (Exception ex) {
            if ((session != null) && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            persistenceException = PersistenceUtil.ExceptionHandler(ex);
        }
        if (persistenceException != null) {
            throw persistenceException;
        }
        return entities;
    }

    private Predicate getWherePredicate(List<ElementAttribute> attributes, CriteriaBuilder builder, Root<Data> root){

        Predicate all = null;
        Predicate newPredicate = null;

        for (ElementAttribute attribute : attributes) {

            if (attribute.getType() == AttributeType.EQUAL.getID()) {

                newPredicate = AttributeEQUAL(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.NOT_EQUAL.getID()) {

                newPredicate = AttributeNOT_EQUAL(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.LESS.getID()) {

                newPredicate = AttributeLESS(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.LESS_OR_EQUAL.getID()) {

                newPredicate = AttributeLESS_OR_EQUAL(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.MORE.getID()) {

                newPredicate = AttributeMORE(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.MORE_OR_EQUAL.getID()) {

                newPredicate = AttributeMORE_OR_EQUAL(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.CONTAIN.getID()) {

                newPredicate = AttributeCONTAIN(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.DOES_NOT_CONTAIN.getID()) {

                newPredicate = AttributeDOES_NOT_CONTAIN(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.INTERVAL_OUT_OUT.getID()) {

                newPredicate = AttributeINTERVAL_OUT_OUT(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.INTERVAL_OUT_IN.getID()) {

                newPredicate = AttributeINTERVAL_OUT_IN(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.INTERVAL_IN_OUT.getID()) {

                newPredicate = AttributeINTERVAL_IN_OUT(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.INTERVAL_IN_IN.getID()) {

                newPredicate = AttributeINTERVAL_IN_IN(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.IN_THE_LIST.getID()) {

                newPredicate = AttributeIN_THE_LIST(attribute, builder, root);

            }
            else if (attribute.getType() == AttributeType.NOT_IN_THE_LIST.getID()) {

                newPredicate = AttributeNOT_IN_THE_LIST(attribute, builder, root);

            }
            if (newPredicate != null) {
                if (all != null) {
                    all = builder.and(all, newPredicate);
                } else {
                    all = newPredicate;
                }
            }

        }

        return all;
    }

    private Predicate AttributeEQUAL(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;
        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getField() != null) {
                if (attributeOne.getValue() == null) {
                    newPredicate = builder.isNull(root.get(attributeOne.getField()));
                }
                else {
                    if (attributeOne.getValue() instanceof String) {
                        Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                        String stringValue = attributeOne.getValue().toString().toUpperCase();
                        newPredicate = builder.like(expressionField, stringValue);
                    }
                    else {
                        Expression expressionField = root.get(attributeOne.getField());
                        Expression expressionValue = builder.literal(attributeOne.getValue());
                        newPredicate = builder.equal(expressionField, expressionValue);
                    }
                }
            }

        }
        else {
            logger.warn("Неверно задан атрибут для поиска по критерию EQUAL");
        }


        return newPredicate;
    }

    private Predicate AttributeNOT_EQUAL(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root){

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getField() != null) {
                if (attributeOne.getValue() == null) {
                    newPredicate = builder.isNull(root.get(attributeOne.getField()));
                } else {
                    if (attributeOne.getValue() instanceof String) {
                        Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                        String stringValue = attributeOne.getValue().toString().toUpperCase();
                        newPredicate = builder.notLike(expressionField, stringValue);
                    } else {
                        Expression expressionField = root.get(attributeOne.getField());
                        Expression expressionValue = builder.literal(attributeOne.getValue());
                        newPredicate = builder.notEqual(expressionField, expressionValue);
                    }
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию NOT_EQUAL");
        }

        return newPredicate;
    }

    private Predicate AttributeLESS(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {
        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getField() != null) {
                if (attributeOne.getValue() == null) {
                    newPredicate = builder.isNull(root.get(attributeOne.getField()));
                } else {
                    if (attributeOne.getValue() instanceof String) {
                        Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                        String stringValue = attributeOne.getValue().toString().toUpperCase();
                        newPredicate = builder.lessThan(expressionField, stringValue);
                    } else {
                        Expression expressionField = root.get(attributeOne.getField());
                        Expression expressionValue = builder.literal(attributeOne.getValue());
                        newPredicate = builder.lessThan(expressionField, expressionValue);
                    }
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию LESS");
        }

        return newPredicate;
    }

    private Predicate AttributeLESS_OR_EQUAL(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {
        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getValue() == null) {
                newPredicate = builder.isNull(root.get(attributeOne.getField()));
            } else {
                if (attributeOne.getValue() instanceof String) {
                    Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                    String stringValue = attributeOne.getValue().toString().toUpperCase();
                    newPredicate = builder.lessThanOrEqualTo(expressionField, stringValue);
                } else {
                    Expression expressionField = root.get(attributeOne.getField());
                    Expression expressionValue = builder.literal(attributeOne.getValue());
                    newPredicate = builder.lessThanOrEqualTo(expressionField, expressionValue);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию LESS_OR_EQUAL");
        }

        return newPredicate;
    }

    private Predicate AttributeMORE(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getValue() == null) {
                newPredicate = builder.isNull(root.get(attributeOne.getField()));
            } else {
                if (attributeOne.getValue() instanceof String) {
                    Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                    String stringValue = attributeOne.getValue().toString().toUpperCase();
                    newPredicate = builder.greaterThan(expressionField, stringValue);
                } else {
                    Expression expressionField = root.get(attributeOne.getField());
                    Expression expressionValue = builder.literal(attributeOne.getValue());
                    newPredicate = builder.greaterThan(expressionField, expressionValue);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию MORE");
        }

        return newPredicate;
    }

    private Predicate AttributeMORE_OR_EQUAL(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getValue() == null) {
                newPredicate = builder.isNull(root.get(attributeOne.getField()));
            } else {
                if (attributeOne.getValue() instanceof String) {
                    Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                    String stringValue = attributeOne.getValue().toString().toUpperCase();
                    newPredicate = builder.greaterThanOrEqualTo(expressionField, stringValue);
                } else {
                    Expression expressionField = root.get(attributeOne.getField());
                    Expression expressionValue = builder.literal(attributeOne.getValue());
                    newPredicate = builder.greaterThanOrEqualTo(expressionField, expressionValue);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию MORE_OR_EQUAL");
        }

        return newPredicate;
    }

    private Predicate AttributeCONTAIN(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getValue() == null) {
                newPredicate = builder.isNull(root.get(attributeOne.getField()));
            } else {
                if(attributeOne.getValue() instanceof String) {
                    Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                    String stringValue = "%" + attributeOne.getValue().toString().toUpperCase() + "%";
                    newPredicate = builder.like(expressionField, stringValue);
                }
                else{
                    Expression expressionField = builder.upper(root.get(attributeOne.getField()));
                    String stringValue = "%" + attributeOne.getValue().toString().toUpperCase() + "%";
                    newPredicate = builder.equal(expressionField, stringValue);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию CONTAIN");
        }

        return newPredicate;
    }

    private Predicate AttributeDOES_NOT_CONTAIN(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeOne) {
            ElementAttributeOne attributeOne = (ElementAttributeOne) attribute;
            if (attributeOne.getValue() == null) {
                newPredicate = builder.isNull(root.get(attributeOne.getField()));
            } else {
                if (attributeOne.getValue() instanceof String) {
                    Expression<String> expressionField = builder.upper(root.get(attributeOne.getField()).as(String.class));
                    String stringValue = "%" + attributeOne.getValue().toString().toUpperCase() + "%";
                    newPredicate = builder.notLike(expressionField, stringValue);

                } else {
                    Expression expressionField = builder.upper(root.get(attributeOne.getField()));
                    String stringValue = "%" + attributeOne.getValue().toString().toUpperCase() + "%";
                    newPredicate = builder.notEqual(expressionField, stringValue);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию DOES_NOT_CONTAIN");
        }

        return newPredicate;
    }

    private Predicate AttributeINTERVAL_OUT_OUT(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeTwo) {
            ElementAttributeTwo attributeTwo = (ElementAttributeTwo) attribute;
            if ((attributeTwo.getValue1() == null) && (attributeTwo.getValue2() == null)) {
                newPredicate = builder.isNull(root.get(attributeTwo.getField()));
            }
            else {
                if ((attributeTwo.getValue1() instanceof String) && (attributeTwo.getValue1() instanceof String)) {
                    Expression<String> expressionField = builder.upper(root.get(attributeTwo.getField()).as(String.class));
                    String stringValue1 = attributeTwo.getValue1().toString().toUpperCase();
                    String stringValue2 = attributeTwo.getValue2().toString().toUpperCase();
                    newPredicate = builder.
                            and(builder
                                            .and(builder.between(expressionField, stringValue1, stringValue2),
                                                    builder.notEqual(expressionField, stringValue1)),
                                    builder.notEqual(expressionField, stringValue2));
                }
                else {
                    Expression expressionField = root.get(attributeTwo.getField());
                    Expression expressionValue1 = builder.literal(attributeTwo.getValue1());
                    Expression expressionValue2 = builder.literal(attributeTwo.getValue2());
                    newPredicate = builder.
                            and(builder
                                            .and(builder.between(expressionField, expressionValue1, expressionValue2),
                                                    builder.notEqual(expressionField, expressionValue1)),
                                    builder.notEqual(expressionField, expressionValue2));
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию INTERVAL_IN_IN");
        }

        return newPredicate;
    }

    private Predicate AttributeINTERVAL_OUT_IN(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeTwo) {
            ElementAttributeTwo attributeTwo = (ElementAttributeTwo) attribute;
            if ((attributeTwo.getValue1() == null) && (attributeTwo.getValue2() == null)) {
                newPredicate = builder.isNull(root.get(attributeTwo.getField()));
            } else {
                if ((attributeTwo.getValue1() instanceof String) && (attributeTwo.getValue1() instanceof String)) {

                    Expression<String> expressionField = builder.upper(root.get(attributeTwo.getField()).as(String.class));
                    String stringValue1 = attributeTwo.getValue1().toString().toUpperCase();
                    String stringValue2 = attributeTwo.getValue2().toString().toUpperCase();
                    newPredicate = builder.
                            and(builder.between(expressionField, stringValue1, stringValue2),
                                    builder.notEqual(expressionField, stringValue1));
                } else {
                    Expression expressionField = root.get(attributeTwo.getField());
                    Expression expressionValue1 = builder.literal(attributeTwo.getValue1());
                    Expression expressionValue2 = builder.literal(attributeTwo.getValue2());
                    newPredicate = builder.
                            and(builder.between(expressionField, expressionValue1, expressionValue2),
                                    builder.notEqual(expressionField, expressionValue1));
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию INTERVAL_IN_OUT");
        }

        return newPredicate;
    }

    private Predicate AttributeINTERVAL_IN_OUT(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeTwo) {
            ElementAttributeTwo attributeTwo = (ElementAttributeTwo) attribute;

            if ((attributeTwo.getValue1() == null) && (attributeTwo.getValue2() == null)) {
                newPredicate = builder.isNull(root.get(attributeTwo.getField()));
            } else {
                if ((attributeTwo.getValue1() instanceof String) && (attributeTwo.getValue1() instanceof String)) {
                    Expression<String> expressionField = builder.upper(root.get(attributeTwo.getField()).as(String.class));
                    String stringValue1 = attributeTwo.getValue1().toString().toUpperCase();
                    String stringValue2 = attributeTwo.getValue2().toString().toUpperCase();
                    newPredicate = builder.
                            and(builder.between(expressionField, stringValue1, stringValue2),
                                    builder.notEqual(expressionField, stringValue2));
                } else {
                    Expression expressionField = root.get(attributeTwo.getField());
                    Expression expressionValue1 = builder.literal(attributeTwo.getValue1());
                    Expression expressionValue2 = builder.literal(attributeTwo.getValue2());
                    newPredicate = builder.
                            and(builder.between(expressionField, expressionValue1, expressionValue2),
                                    builder.notEqual(expressionField, expressionValue2));
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию INTERVAL_OUT_IN");
        }

        return newPredicate;
    }

    private Predicate AttributeINTERVAL_IN_IN(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;


        if (attribute instanceof ElementAttributeTwo) {
            ElementAttributeTwo attributeTwo = (ElementAttributeTwo) attribute;
            if ((attributeTwo.getValue1() == null) && (attributeTwo.getValue2() == null)) {
                newPredicate = builder.isNull(root.get(attributeTwo.getField()));
            } else {
                if ((attributeTwo.getValue1() instanceof String) && (attributeTwo.getValue1() instanceof String)) {
                    Expression<String> expressionField = builder.upper(root.get(attributeTwo.getField()).as(String.class));
                    String stringValue1 = attributeTwo.getValue1().toString().toUpperCase();
                    String stringValue2 = attributeTwo.getValue2().toString().toUpperCase();
                    newPredicate = builder.between(expressionField, stringValue1, stringValue2);
                } else {
                    Expression expressionField = root.get(attributeTwo.getField());
                    Expression expressionValue1 = builder.literal(attributeTwo.getValue1());
                    Expression expressionValue2 = builder.literal(attributeTwo.getValue2());
                    newPredicate = builder.between(expressionField, expressionValue1, expressionValue2);
                }
            }
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию INTERVAL_OUT_OUT");
        }

        return newPredicate;
    }

    private Predicate AttributeIN_THE_LIST(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeList) {
            ElementAttributeList attributeList = (ElementAttributeList) attribute;

            List ListAttribute = attributeList.getValue();

            Predicate predicate1 = null;
            Predicate predicate2 = null;
            for (Object object : ListAttribute) {
                if (object == null) {
                    Expression expressionField = root.get(attributeList.getField());
                    predicate1 = builder.isNull(expressionField);
                }
                else if (object instanceof String) {
                    Expression<String> expressionField = builder.upper((root.get(attributeList.getField()).as(String.class)));
                    String stringValue = object.toString().toUpperCase();
                    predicate1 = builder.equal(expressionField, stringValue);
                }
                else {
                    Expression expressionField = root.get(attributeList.getField());
                    Expression expressionValue = builder.literal(object);
                    predicate1 = builder.equal(expressionField, expressionValue);
                }
                if (predicate2 == null) {
                    predicate2 = predicate1;
                } else {
                    predicate2 = builder.or(predicate2, predicate1);
                }
            }

            newPredicate = predicate2;
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию IN_THE_LIST");
        }

        return newPredicate;
    }

    private Predicate AttributeNOT_IN_THE_LIST(ElementAttribute attribute, CriteriaBuilder builder, Root<Data> root) {

        Predicate newPredicate = null;

        if (attribute instanceof ElementAttributeList) {
            ElementAttributeList attributeList = (ElementAttributeList) attribute;
            List ListAttribute = attributeList.getValue();
            Predicate predicate1 = null;
            Predicate predicate2 = null;
            for (Object object : ListAttribute) {
                if (object == null) {
                    Expression expressionField = root.get(attributeList.getField());
                    predicate2 = builder.isNull(expressionField);
                }
                else if (object instanceof String) {
                    Expression<String> expressionField = builder.upper((root.get(attributeList.getField()).as(String.class)));
                    String stringValue = object.toString().toUpperCase();
                    predicate1 = builder.notLike(expressionField, stringValue);
                    if (predicate2 == null) {
                        predicate2 = predicate1;
                    } else {
                        predicate2 = builder.and(predicate2, predicate1);
                    }

                } else {
                    Expression expressionField = root.get(attributeList.getField());
                    Expression expressionValue = builder.literal(object);
                    predicate1 = builder.notEqual(expressionField, expressionValue);
                    if (predicate2 == null) {
                        predicate2 = predicate1;
                    } else {
                        predicate2 = builder.and(predicate2, predicate1);
                    }
                }
            }
            newPredicate = predicate2;
        } else {
            logger.warn("Неверно задан атрибут для поиска по критерию NOT_IN_THE_LIST");
        }
        return newPredicate;
    }
}
