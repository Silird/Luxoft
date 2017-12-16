package ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Server.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationColumn;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationLinkColumn;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationMapping;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.*;

import java.io.IOException;

public class GenerationEntity {
    private static final Logger logger = LoggerFactory.getLogger(GenerationEntity.class);

    private GenerationImports generationImports = new GenerationImports();

    private StringBuilder imports = new StringBuilder();

    private StringBuilder header = new StringBuilder();

    private StringBuilder variables = new StringBuilder();

    private StringBuilder setGet = new StringBuilder();

    private StringBuilder footer = new StringBuilder();

    public GenerationEntity() {

    }

    public boolean start(GenerationMapping mapping) {

        generationImports.clear();
        generationImports.setClassName(new Import(GenerationUtils.getTitleName(mapping.getTableName()),
                ImportType.DATA));

        imports.delete(0, imports.length());

        header.delete(0, header.length());

        variables.delete(0, variables.length());

        setGet.delete(0, setGet.length());

        footer.delete(0, footer.length());

        generateImports(mapping);

        generateHeader(mapping);

        generateFooter(mapping);

        generateVariables(mapping);

        generateSetGet(mapping);

        if (!generationImports.isEmpty()) {
            String im = generationImports.getImports();
            imports.append(im);
            imports.append("\n");
        }

        StringBuilder file = new StringBuilder();

        file.append(imports.toString())
                .append(header.toString())
                .append(variables.toString())
                .append(setGet.toString())
                .append(footer.toString());

        try {
            String fileName = GenerationUtils.getTitleName(mapping.getTableName()) + ".java";
            GenerationUtils.write(file.toString(), fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void generateImports(GenerationMapping mapping) {
        imports.append("package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated;\n" +
                "\n" +
                "import java.io.Serializable;\n" +
                "import org.hibernate.annotations.GenericGenerator;\n" +
                "import javax.persistence.*;\n" +
                "import javax.xml.bind.annotation.XmlRootElement;\n" +
                "import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.BaseEntity;\n" +
                "\n");
    }

    private void generateHeader(GenerationMapping mapping) {
        header.append("@Entity\n");
        if (mapping.getParentTable() != null) {
            GenerationMapping parentTableMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentTableMapping != null) {
                header.append("@PrimaryKeyJoinColumn(name = \"" + mapping.getIdColumn().getName() + "\", referencedColumnName = \"" + parentTableMapping.getIdColumn().getName() + "\")\n");
            }
        }
        if (mapping.isParent()) {
            header.append("@Inheritance(strategy = InheritanceType.JOINED)\n");
        }
        header.append("@Table(name = \"" + mapping.getTableName() + "\", schema = \"public\")\n");
        header.append("@XmlRootElement\n");

        header.append("public class " + GenerationUtils.getTitleName(mapping.getTableName()));
        if (mapping.getParentTable() == null) {
            header.append(" extends BaseEntity ");
        }
        else {
            GenerationMapping parentTableMapping = SpringFactory.getInstance().getMapping(mapping.getParentTable());

            if (parentTableMapping != null) {
                header.append(" extends " + GenerationUtils.getTitleName(parentTableMapping.getTableName()) + " ");
            }
        }
        header.append("implements Serializable {\n\n");
    }

    private void generateFooter(GenerationMapping mapping) {
        GenerationColumn id = mapping.getIdColumn();
        String name = GenerationUtils.getName(GenerationUtils.getMainNameColumn(mapping).getName());
        String titleName = GenerationUtils.getTitleName(mapping.getTableName());
        String nameId = GenerationUtils.getName(id.getName());

        if (mapping.getParentTable() == null) {
            footer.append("    @Override\n" +
                    "    public boolean equals(Object object) {\n" +
                    "        if (this == object) return true;\n" +
                    "        if((object == null) || (getClass()!= object.getClass())) return  false;\n" +
                    "\n" +
                    "        " + titleName + " other = (" + titleName + ") object;\n" +
                    "        if ((this." + nameId + " == null) && (other." + nameId + " != null)) return false;\n" +
                    "        if  ((this." + nameId + " != null) && (!this." + nameId + ".equals(other." + nameId + "))) return false;\n" +
                    "\n" +
                    "        return true;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public String toString() {\n" +
                    "        String result;\n" +
                    "        if ((" + name + " == null)) {\n" +
                    "            result = \"[Без названия]\";\n" +
                    "        }\n" +
                    "        else {\n" +
                    "            result = \"\" + " + name + ";\n" +
                    "        }\n" +
                    "        return result;\n" +
                    "    }\n");
        }

        footer.append("\n" +
                "}");
    }

    private void generateVariables(GenerationMapping mapping) {
        GenerationColumn id = mapping.getIdColumn();
        String nameId = GenerationUtils.getName(id.getName());

        if (mapping.getParentTable() == null) {
            variables.append("    @Id\n" +
                    "    @Basic(optional = false)\n" +
                    "    @GenericGenerator(name = \"key_gen\", strategy = \"increment\")\n" +
                    "    @GeneratedValue(generator = \"key_gen\")\n" +
                    "    @Column(name = \"" + id.getName() + "\", nullable = false)\n" +
                    "    private " + id.getType() + " " + nameId + ";\n\n");
        }

        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                if (column.isNotNullable()) {
                    variables.append("    @Basic(optional = false)\n");
                }

                variables.append("    @Column(name = \"" + column.getName() + "\"");
                if (column.getType().equals("String")) {
                    variables.append(", columnDefinition=\"TEXT\"");
                }
                if (column.isNotNullable()) {
                    variables.append(", nullable = false");
                }
                variables.append(")\n");

                if (column.getType().equals("Date")) {
                    variables.append("    @Temporal(TemporalType.TIMESTAMP)\n");

                    generationImports.addType(new Import(column.getType(), ImportType.NONE.NONE));
                }

                variables.append("    private " + column.getType() + " " + GenerationUtils.getName(column.getName()) + ";\n");
            }
        }

        if (mapping.getLinkColumns() != null) {
            variables.append("\n");
            for (GenerationLinkColumn column : mapping.getLinkColumns()) {
                GenerationMapping linkTableMapping = SpringFactory.getInstance().getMapping(column.getType());

                if (linkTableMapping != null) {
                    String nameColumn = GenerationUtils.getName(column.getName());

                    generationImports.addType(new Import(column.getType(), ImportType.DATA));

                    variables.append("    @JoinColumn(name = \"id_" + column.getName() + "\", referencedColumnName = \"" + linkTableMapping.getIdColumn().getName() + "\"");
                    if (column.isNotNullable()) {
                        variables.append(", nullable = false");
                    }
                    variables.append(")\n");
                    variables.append("    @ManyToOne(fetch = FetchType." + column.getFetch());
                    if (column.isNotNullable()) {
                        variables.append(", optional = false");
                    }
                    variables.append(")\n");
                    variables.append("    private " + column.getType() + " " + nameColumn + ";\n");
                }
            }
        }
    }

    private void generateSetGet(GenerationMapping mapping) {
        GenerationColumn id = mapping.getIdColumn();
        String nameId = GenerationUtils.getName(id.getName());
        String titleNameId = GenerationUtils.getTitleName(id.getName());

        if (mapping.getParentTable() == null) {
            setGet.append("    public " + id.getType() + " get" +titleNameId + "() {\n" +
                    "        return " + nameId + ";\n" +
                    "    }\n" +
                    "\n" +
                    "    public void set" +titleNameId + "(" + id.getType() + " " + nameId + ") {\n" +
                    "        this." + nameId + " = " + nameId + ";\n" +
                    "    }\n\n");
        }

        if (mapping.getColumns() != null) {
            for (GenerationColumn column : mapping.getColumns()) {
                String tittleNameColumn = GenerationUtils.getTitleName(column.getName());
                String nameColumn = GenerationUtils.getName(column.getName());

                setGet.append("    public " + column.getType() + " get" + tittleNameColumn + "() {\n" +
                        "        return " + nameColumn + ";\n" +
                        "    }\n" +
                        "\n" +
                        "    public void set" + tittleNameColumn + "(" + column.getType() + " " + nameColumn + ") {\n" +
                        "        this." + nameColumn + " = " + nameColumn + ";\n" +
                        "    }\n\n");
            }
        }

        if (mapping.getLinkColumns() != null) {
            for (GenerationColumn column : mapping.getLinkColumns()) {
                String tittleNameColumn = GenerationUtils.getTitleName(column.getName());
                String nameColumn = GenerationUtils.getName(column.getName());

                setGet.append("    public " + column.getType() + " get" + tittleNameColumn + "() {\n" +
                        "        return " + nameColumn + ";\n" +
                        "    }\n" +
                        "\n" +
                        "    public void set" + tittleNameColumn + "(" + column.getType() + " " + nameColumn + ") {\n" +
                        "        this." + nameColumn + " = " + nameColumn + ";\n" +
                        "    }\n\n");
            }
        }
    }
}
