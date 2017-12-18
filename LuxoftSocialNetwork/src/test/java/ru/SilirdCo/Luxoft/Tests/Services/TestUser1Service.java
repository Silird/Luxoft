package ru.SilirdCo.Luxoft.Tests.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.VarUtils;
import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.Services.IService;
import ru.SilirdCo.Luxoft.Tests.Util.DefaultValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestUser1Service {
    private final static Logger logger = LoggerFactory.getLogger(TestUser1Service.class);

    private IService<User1> service = ServiceFactory.getInstance().getUser1Service();

    private User1 user = new User1();

    private DefaultValue defaultValue = new DefaultValue("Bank");

    @BeforeClass
    public void start() {
        logger.info(" [#] Начало тестирования сервиса user");
    }

    @AfterClass
    public void end() {
        logger.info(" [#] Конец тестирования сервиса user");
    }

    @Test(groups = {"user"})
    public void testEmptyTable() {
        logger.info(" [*] Проверка на пустоту");
        Assert.assertEquals(true, service.getElements().isEmpty(), " [x] Справочник не пустой");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testEmptyTable"})
    public void testAddingNewElement() {
        logger.info(" [*] Проверка добавления элемента");
        defaultValue.incrementCounter();
        user = new User1();
        user.setId(null);
        user.setLogin((String) defaultValue.getValue("String"));
        user.setPassword((String) defaultValue.getValue("String"));

        user = service.save(user);
        Assert.assertNotNull(user, " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));
        Assert.assertNotNull(user.getId(), " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));

        defaultValue.incrementCounter();
        user = new User1();
        user.setId(null);
        user.setLogin((String) defaultValue.getValue("String"));
        user.setPassword((String) defaultValue.getValue("String"));

        user = service.save(user);
        Assert.assertNotNull(user, " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));
        Assert.assertNotNull(user.getId(), " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testAddingNewElement"})
    public void testGettingElements() {
        logger.info(" [*] Проверка получения элементов");

        Assert.assertEquals(2, service.getElements().size(), " [x] Ошибка получения элементов");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testAddingNewElement"})
    public void testFindingSomeElement() {
        logger.info(" [*] Проверка получения любого элемента");

        user = service.getFirstResult();

        Assert.assertNotNull(user, " [x] Ошибка получения любого элемента");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testFindingSomeElement"})
    public void testFindingElementById() {
        logger.info(" [*] Проверка получения элемента по идентификатору");

        user = service.getFirstResult();

        User1 userTMP = service.findById(user.getId());

        Assert.assertEquals(user.getId(), userTMP.getId(), " [x] Ошибка получения элемента по id");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testFindingElementById", "testGettingElements"})
    public void testUpdateElement() {
        logger.info(" [*] Проверка обновления элемента");

        user = service.getFirstResult();
        user.setLogin((String) defaultValue.getUpdatedValue("String"));
        user.setPassword((String) defaultValue.getUpdatedValue("String"));

        User1 userTMP = service.save(user);

        Assert.assertEquals(user.getId(), userTMP.getId(), " [x] Ошибка обновления элемента");
        Assert.assertEquals(user.getLogin(), userTMP.getLogin(), " [x] Ошибка обновления элемента");
        Assert.assertEquals(user.getPassword(), userTMP.getPassword(), " [x] Ошибка обновления элемента");

        userTMP = service.findById(user.getId());

        Assert.assertEquals(user.getId(), userTMP.getId(), " [x] Ошибка обновления элемента");
        Assert.assertEquals(user.getLogin(), userTMP.getLogin(), " [x] Ошибка обновления элемента");
        Assert.assertEquals(user.getPassword(), userTMP.getPassword(), " [x] Ошибка обновления элемента");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testUpdateElement"})
    public void testRemovingElement() {
        logger.info(" [*] Проверка удаления");

        user = service.getFirstResult();

        Boolean result = service.remove(user.getId());

        Assert.assertTrue(VarUtils.getBoolean(result), " [x] Ошибка удаления");

        User1 userTMP = service.findById(user.getId());

        Assert.assertNull(userTMP, " [x] Ошибка удаления");
    }

    @Test(groups = {"user"}, dependsOnMethods = {"testRemovingElement"})
    public void testRemovingList() {
        logger.info(" [*] Проверка удаления листа");

        List<Integer> ids = new ArrayList<>();

        defaultValue.incrementCounter();
        user = new User1();
        user.setId(null);
        user.setLogin((String) defaultValue.getValue("String"));
        user.setPassword((String) defaultValue.getValue("String"));

        user = service.save(user);
        Assert.assertNotNull(user, " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));
        Assert.assertNotNull(user.getId(), " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));

        ids.add(user.getId());

        defaultValue.incrementCounter();
        user = new User1();
        user.setId(null);
        user.setLogin((String) defaultValue.getValue("String"));
        user.setPassword((String) defaultValue.getValue("String"));

        user = service.save(user);
        Assert.assertNotNull(user, " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));
        Assert.assertNotNull(user.getId(), " [x] Ошибка добавления элемента: " + defaultValue.getValue("String"));

        ids.add(user.getId());

        Boolean result = service.removeList(ids);

        Assert.assertTrue(VarUtils.getBoolean(result), " [x] Ошибка удаления");

        for (Integer integer : ids) {
            User1 userTMP = service.findById(integer);

            Assert.assertNull(userTMP, " [x] Ошибка удаления");
        }
    }
}