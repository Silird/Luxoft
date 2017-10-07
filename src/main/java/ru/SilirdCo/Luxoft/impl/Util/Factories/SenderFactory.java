package ru.SilirdCo.Luxoft.impl.Util.Factories;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.SilirdCo.Luxoft.impl.Messages.Event;
import ru.SilirdCo.Luxoft.impl.Messages.Sender;
import ru.SilirdCo.Luxoft.interfaces.Messages.ISender;

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
