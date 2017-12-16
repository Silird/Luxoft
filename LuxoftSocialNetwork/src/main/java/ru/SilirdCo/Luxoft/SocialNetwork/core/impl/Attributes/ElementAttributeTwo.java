package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes;

public class ElementAttributeTwo<Type> extends ElementAttribute {
    private Type value1;
    private   Type value2;

    private String typeClass1;
    private String typeClass2;

    public ElementAttributeTwo(int type, String field, Type value1, Type value2)
    {
        super(type, field);
        this.value1 = value1;
        this.value2 = value2;

        if (value1 == null) {
            this.typeClass1 = Object.class.getName();
        }
        else {
            this.typeClass1 = value1.getClass().getName();
        }

        if (value2 == null) {
            this.typeClass2 = Object.class.getName();
        }
        else {
            this.typeClass2 = value2.getClass().getName();
        }
    }

    public Type getValue1(){
        return value1;
    }

    public Type getValue2(){
        return value2;
    }

    public void setValue1(Type value1)
    {
        this.value1 = value1;
        if (value1 == null) {
            this.typeClass1 = Object.class.getName();
        }
        else {
            this.typeClass1 = value1.getClass().getName();
        }
    }

    public void setValue2(Type value2)
    {
        this.value2 = value2;
        if (value2 == null) {
            this.typeClass2 = Object.class.getName();
        }
        else {
            this.typeClass2 = value2.getClass().getName();
        }
    }
}
