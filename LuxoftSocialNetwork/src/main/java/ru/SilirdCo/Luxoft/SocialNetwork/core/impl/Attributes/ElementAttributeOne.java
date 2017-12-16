package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes;

public class ElementAttributeOne<Type> extends ElementAttribute {
   private Type value;

    private String typeClass;

    public ElementAttributeOne(int type, String field, Type value)
    {
        super(type, field);
        this.value = value;

        if (value == null) {
            this.typeClass = Object.class.getName();
        }
        else {
            this.typeClass = value.getClass().getName();
        }
    }

    public void setValue( Type value)
    {
        this.value = value;
        if (value == null) {
            this.typeClass = Object.class.getName();
        }
        else {
            this.typeClass = value.getClass().getName();
        }
    }

    public Type getValue()
    {
        return  value;
    }
}
