package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes;

public enum AttributeType {


    NOTHING(0),
    // одно значение
    EQUAL(1),
    NOT_EQUAL(2),
    LESS(3),
    LESS_OR_EQUAL(4),
    MORE(5),
    MORE_OR_EQUAL(6),

    // два значения
    INTERVAL_IN_IN(7),
    INTERVAL_IN_OUT(8),
    INTERVAL_OUT_IN(9),
    INTERVAL_OUT_OUT(10),

    // одно значение
    CONTAIN(11),
    DOES_NOT_CONTAIN(12),

    // Список значений
    IN_THE_LIST(13),
    NOT_IN_THE_LIST(14);

    private int id;

    AttributeType(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }
}
