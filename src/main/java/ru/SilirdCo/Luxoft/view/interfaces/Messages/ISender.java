package ru.SilirdCo.Luxoft.view.interfaces.Messages;

import rx.Observable;

public interface ISender<T>  {
    /**
     * Получение Observable, на который приходят входящие сообщения
     * @return - Observable
     */
    Observable<T> getObservable();

    /**
     * Отправка события
     * @param event - событие
     */
    void send(T event);
}
