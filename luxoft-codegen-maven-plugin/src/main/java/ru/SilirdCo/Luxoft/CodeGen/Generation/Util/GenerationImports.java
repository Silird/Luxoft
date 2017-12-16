package ru.SilirdCo.Luxoft.CodeGen.Generation.Util;

import java.util.ArrayList;
import java.util.List;

public class GenerationImports {
    private Import className;

    private List<Import> types = new ArrayList<>();

    public GenerationImports() {

    }

    public void addType(Import type) {
        if (!type.equals(className) && !types.contains(type)) {
            types.add(type);
        }
    }

    public void clear() {
        types.clear();
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public String getImports() {
        StringBuilder imports = new StringBuilder();
        for (Import type : types) {
            switch (type.getType()) {
                case NONE: {
                    switch (type.getName()) {
                        case "Date": {
                            imports.append("import java.util.Date;\n");
                            break;
                        }
                        case "List": {
                            imports.append("import java.util.List;\n");
                            break;
                        }
                        case "ArrayList": {
                            imports.append("import java.util.ArrayList;\n");
                            break;
                        }
                        default: {}
                    }
                    break;
                }
                case DATA: {
                    imports.append("import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.")
                            .append(type.getName())
                            .append(";\n");
                    break;
                }
                default: {}
            }
        }

        return imports.toString();
    }

    public void setClassName(Import className) {
        this.className = className;
    }
}
