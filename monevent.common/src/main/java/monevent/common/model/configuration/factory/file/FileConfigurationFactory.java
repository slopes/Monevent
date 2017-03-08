package monevent.common.model.configuration.factory.file;

import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.configuration.factory.ConfigurationFactoryBase;
import ro.fortsoft.pf4j.util.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steph on 28/02/2016.
 */
public class FileConfigurationFactory<T extends Configuration> extends ConfigurationFactoryBase<T> {
    private final String configurationDirectory;
    private final String extension;

    public FileConfigurationFactory(String name, String baseConfigurationDirectory) {
        super(name);
        this.configurationDirectory = baseConfigurationDirectory;
        this.extension = "json";
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

    @Override
    public void add(T configuration) {
        List<File> matchingFiles = new ArrayList<>();
        findFiles(configuration.getName(), new File(this.configurationDirectory), matchingFiles);
        if (matchingFiles != null && matchingFiles.size() > 0) {
            for (File file : matchingFiles) {
                if (file.isFile() && !file.getParentFile().getName().equals(configuration.getCategory())) {
                    throw new ConfigurationException(String.format("A configuration %s  already exists for the category  : %s", file.getName(), file.getParentFile().getName()));
                }
            }
        }
        File file = Paths.get(this.configurationDirectory, configuration.getCategory(), String.format("%s.%s", configuration.getName(), this.extension)).toFile();
        try {
            if ( !file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            getMapper().writeValue(file, configuration);
        } catch (JsonGenerationException error) {
            error("Cannot add configuration.", error);
        } catch (JsonMappingException error) {
            error("Cannot add configuration.", error);
        } catch (JsonParseException error) {
            error("Cannot add configuration.", error);
        } catch (IOException error) {
            error("Cannot add configuration.", error);
        }
    }

    @Override
    public boolean canBuild(String configurationFullName) {
        List<File> matchingFiles = new ArrayList<>();
        findFiles(configurationFullName, new File(this.configurationDirectory), matchingFiles);
        if (matchingFiles == null || matchingFiles.size() == 0) return false;
        if (matchingFiles.size() > 1) return false;
        return matchingFiles.get(0).exists() && matchingFiles.get(0).isFile();
    }

    private void findFiles(String configurationFullName, File file, List<File> matchingFiles) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                findFiles(configurationFullName, subFile, matchingFiles);
            }
        } else {
            if (file.getName().replace("." + this.extension, "").equals(configurationFullName)) {
                matchingFiles.add(file);
            }
        }
    }

    @Override
    public T build(String configurationFullName) {
        List<File> matchingFiles = new ArrayList<>();
        findFiles(configurationFullName, new File(this.configurationDirectory), matchingFiles);
        File file = matchingFiles.get(0);
        if (file.exists() && file.isFile()) {
            try {
                T value = (T) getMapper().readValue(file, Configuration.class);
                return value;
            } catch (IOException jsonError) {
                error("Cannot deserialize entity.", jsonError);
            }
        }
        return null;
    }

}
