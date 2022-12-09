package net.team.project;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class Project extends JavaPlugin {
    private Project project;
    private final CommandInitializer commandInit = new CommandInitializer(project);
    public Logger logger = Logger.getLogger(this.getName());
    private final VaultInitializer vaultInitializer = new VaultInitializer(project);

    @Override
    public void onEnable() {
        project = this;
        commandInit.initializeCommands();
        vaultInitializer.setup();
    }

    @Override
    public void onDisable() {

    }
}
