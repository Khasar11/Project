package net.team.project;

import net.team.project.utils.ConfigurationFile;

public class ConfigurationHeader {

    private final Project project;

    public ConfigurationFile main;
    public ConfigurationFile messages;
    public ConfigurationFile announce;
    
    public ConfigurationHeader(Project project) {
        this.project = project;
    }

    public void initializeConfigurations() {
        main = new ConfigurationFile(project, project.getDataFolder(), "main", true, true);
        messages = new ConfigurationFile(project, project.getDataFolder(), "messages", true, true);
    }
}
