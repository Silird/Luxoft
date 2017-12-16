package ru.SilirdCo.Luxoft.CodeGen.Plugin;

import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Main.MainCodeGen;
import ru.SilirdCo.Luxoft.CodeGen.Generation.Util.GenerationUtils;

import java.io.File;

@Mojo(name = "generate-code-sources", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresProject = true, threadSafe = false)
public class GenerateCodeMojo extends AbstractMojo {
    private static final Logger logger = LoggerFactory.getLogger(GenerateCodeMojo.class);

    @Parameter(defaultValue = "${project.artifactId}", readonly = true)
    private String artifactId;

    @Parameter(defaultValue = "${project.version}", readonly = true)
    private String version;

    @Parameter(defaultValue = "${project.packaging}", readonly = true)
    private String packaging;

    @Parameter(defaultValue = "${basedir}", readonly = true)
    private File startBaseDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            logger.info("Starting generation...\n\n");

            String directory = startBaseDirectory.getCanonicalPath();



            System.out.println("\n\n===========================\n");
            System.out.println("canonical path: " + directory);
            System.out.println("\n===========================\n\n");

            String[] directories;
            if (SystemUtils.IS_OS_LINUX) {
                directories = directory.split("/");
            }
            else {
                directories = directory.split("\\\\");
            }

            if (directories.length > 2) {
                directory = "";
                for (int i = 0; i < directories.length - 1; i++) {
                    if (i != 0) {
                        if (SystemUtils.IS_OS_LINUX) {
                            directory += "/";
                        }
                        else {
                            directory += "\\";
                        }
                    }
                    directory += directories[i];
                }
            }

            System.out.println("\n\n===========================\n");
            System.out.println("generation base path: " + directory);
            System.out.println("\n===========================\n\n");

            GenerationUtils.baseDirectory = directory;
            MainCodeGen.main(null);

            logger.info("\n\nFinishing generation...");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
