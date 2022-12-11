package net.team.project.configuration;

import net.team.project.Project;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationFile {

    protected final boolean createIfNotExist, resource;
    protected final Project project;

    protected FileConfiguration config;
    protected File file, path;
    protected String name;

    public ConfigurationFile(Project project, File path, String name, boolean createIfNotExist, boolean resource) {
        this.project = project;
        this.path = path;
        this.name = name + ".yml";
        this.createIfNotExist = createIfNotExist;
        this.resource = resource;
        create();

        this.reloadConfig();
        project.logger.info("Loaded configuration file with name " + name);
    }
    public ConfigurationFile(Project project, String path, String name, boolean createIfNotExist, boolean resource) {
        this(project, new File(path), name, createIfNotExist, resource);
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public void save() {
        try {
            config.save(file);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public File reloadFile() {
        file = new File(path, name);
        return file;
    }

    public FileConfiguration reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void reload() {
        reloadFile();
        reloadConfig();
    }

    public void create() {
        if (file == null) {
            reloadFile();
        }
        if (!createIfNotExist || file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (resource) {
            project.saveResource(name, false);
        } else {
            try {
                file.createNewFile();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if (config == null) {
            reloadConfig();
        }
    }
}