package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes;

import java.util.List;

public class ElementAttributeList<Type> extends ElementAttribute {
    private List<Type> value;
    private String typeClass;

    public ElementAttributeList(int type, String field, List<Type> value)
    {
        super(type, field);
        this.value = value;

        if (value.isEmpty()) {
            typeClass = Object.class.getName();
        }
        else {
            if (value.get(0) == null) {
                this.typeClass = Object.class.getName();
            }
            else {
                this.typeClass = value.get(0).getClass().getName();
            }
        }
    }

    public void setValue(List<Type> value)
    {
        this.value = value;
        if (value.isEmpty()) {
            typeClass = Object.class.getName();
        }
        else {
            if (value.get(0) == null) {
                this.typeClass = Object.class.getName();
            }
            else {
                this.typeClass = value.get(0).getClass().getName();
            }
        }
    }

    public List<Type> getValue()
    {
        return value;
    }
}
