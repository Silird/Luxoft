package ru.SilirdCo.Luxoft.impl.Messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.interfaces.Messages.ISender;
import rx.Observable;
import rx.subjects.PublishSubject;

public class Sender implements ISender<Event> {
    private final Logger logger = LoggerFactory.getLogger(Sender.class);
    private PublishSubject<Event> subject = PublishSubject.create();

    @Override
    public Observable<Event> getObservable() {
        return subject;
    }

    @Override
    public void send(Event event) {
        subject.onNext(event);
    }
}
