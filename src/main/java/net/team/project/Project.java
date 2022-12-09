package net.team.project;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class Project extends JavaPlugin {
    private static Project project;
    private final CommandInitializer commandInit = new CommandInitializer(project);
    public Logger logger = Logger.getLogger(this.getName());

    @Override
    public void onEnable() {
        project = this;
        commandInit.initializeCommands();
    }

    @Override
    public void onDisable() {

    }

    public static Project getPlugin() {
        return project;
    }
}
