package ru.SilirdCo.Luxoft.CodeGen.Generation.Entity;

public class GenerationColumn {
    private String name;
    private String type;
    private boolean notNullable = false;

    public GenerationColumn() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNotNullable() {
        return notNullable;
    }

    public void setNotNullable(boolean notNullable) {
        this.notNullable = notNullable;
    }
}
