package ru.SilirdCo.Luxoft.CodeGen.Generation.Main;

import ru.SilirdCo.Luxoft.CodeGen.Generation.Entity.GenerationConfiguration;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Generators.Generation;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.SpringFactory;

public class MainCodeGen {
    public static void main(String[] args) {
        GenerationConfiguration configurationDictonary = SpringFactory.getInstance().getConfiguration();
        Generation generation = new Generation(configurationDictonary);

        if (!generation.start()) {
            System.exit(1);
        }
    }
}
