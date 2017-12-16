package ru.SilirdCo.Luxoft.CodeGen.Generation.Entity;

import java.util.List;

public class GenerationConfiguration {
    private List<String> tables;

    private List<String> tablesTestByPass;

    public GenerationConfiguration() {

    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public List<String> getTablesTestByPass() {
        return tablesTestByPass;
    }

    public void setTablesTestByPass(List<String> tablesTestByPass) {
        this.tablesTestByPass = tablesTestByPass;
    }
}
