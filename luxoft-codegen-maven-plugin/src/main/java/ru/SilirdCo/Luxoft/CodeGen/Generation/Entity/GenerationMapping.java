package ru.SilirdCo.Luxoft.CodeGen.Generation.Entity;

import java.util.List;

public class GenerationMapping {
    /**
     * Название таблицы
     */
    private String tableName;

    /**
     * Колонка идентификатор
     */
    private GenerationColumn idColumn;

    /**
     * Колонка имени
     */
    private GenerationColumn mainName;

    /**
     * Все колонки примитивных типов
     */
    private List<GenerationColumn> columns;

    /**
     * Все колонки ссылок на другие таблицы
     */
    private List<GenerationLinkColumn> linkColumns;

    /**
     * Название родительской таблицы, null, если её нет
     */
    private String parentTable;

    /**
     * Является ли таблица родительской
     */
    private boolean parent = false;

    /**
     * Зависит ли работа таблицы от параметров
     */
    private boolean dependOnParameters = false;

    /**
     * Колонка для сортировки
     */
    private GenerationColumn sortColumn;

    public GenerationMapping() {

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<GenerationColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GenerationColumn> columns) {
        this.columns = columns;
    }

    public GenerationColumn getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(GenerationColumn idColumn) {
        this.idColumn = idColumn;
    }

    public GenerationColumn getMainName() {
        return mainName;
    }

    public void setMainName(GenerationColumn mainName) {
        this.mainName = mainName;
    }

    public List<GenerationLinkColumn> getLinkColumns() {
        return linkColumns;
    }

    public void setLinkColumns(List<GenerationLinkColumn> linkColumns) {
        this.linkColumns = linkColumns;
    }

    public String getParentTable() {
        return parentTable;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isDependOnParameters() {
        return dependOnParameters;
    }

    public void setDependOnParameters(boolean dependOnParameters) {
        this.dependOnParameters = dependOnParameters;
    }

    public GenerationColumn getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(GenerationColumn sortColumn) {
        this.sortColumn = sortColumn;
    }
}
