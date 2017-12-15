package ru.SilirdCo.Luxoft.view.impl.Util.Factories;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.SilirdCo.Luxoft.view.impl.Events.Event;
import ru.SilirdCo.Luxoft.view.impl.Events.Sender;
import ru.SilirdCo.Luxoft.view.interfaces.Messages.ISender;

public class SenderFactory {
    private ApplicationContext context = new ClassPathXmlApplicationContext("Factories/SenderFactory.xml");

    private static SenderFactory instance;

    public static SenderFactory getInstance() {
        if (instance == null) {
            instance = new SenderFactory();
        }
        return instance;
    }

    public ISender<Event> getEvents() {
        return context.getBean("sender", Sender.class);
    }
}
