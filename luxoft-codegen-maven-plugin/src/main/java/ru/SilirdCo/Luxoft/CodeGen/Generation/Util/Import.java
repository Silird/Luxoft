package ru.SilirdCo.Luxoft.CodeGen.Generation.Util;

public class Import {
    private String name;
    private ImportType type;

    public Import(String name, ImportType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImportType getType() {
        return type;
    }

    public void setType(ImportType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if((object == null) || (getClass() != object.getClass())) return  false;

        Import other = (Import) object;
        if ((this.name == null) && (other.name != null)) return false;
        if  ((this.name != null) && (!this.name.equals(other.name))) return false;

        if ((this.type == null) && (other.type != null)) return false;
        if  ((this.type != null) && (!this.type.equals(other.type))) return false;

        return true;
    }
}
