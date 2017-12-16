package ru.SilirdCo.Luxoft.CodeGen.Generation.Entity;

public class GenerationLinkColumn extends GenerationColumn {
    private String fetch = "LAZY";

    private boolean needed = false;

    public GenerationLinkColumn() {

    }

    public String getFetch() {
        return fetch;
    }

    public void setFetch(String fetch) {
        this.fetch = fetch;
    }

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }
}
