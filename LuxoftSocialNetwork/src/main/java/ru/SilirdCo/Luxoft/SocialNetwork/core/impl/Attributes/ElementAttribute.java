package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes;

public abstract class ElementAttribute {

    private String field;
    private int type;

    private String clazz;

    public ElementAttribute(int type, String field) {
        clazz = this.getClass().getName();
        this.type = type;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
