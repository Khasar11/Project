package net.team.project;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.team.project.commandsHelper.CommandInitializer;
import net.team.project.configuration.ConfigurationFile;
import net.team.project.vault.VaultInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Project extends JavaPlugin {
    private Project project;
    private final CommandInitializer commandInit = new CommandInitializer(project);
    public Logger logger = Logger.getLogger(this.getName());
    private final VaultInitializer vaultInitializer = new VaultInitializer(project);
    ConfigurationFile mainConfig = new ConfigurationFile(project, project.getDataFolder(), "config-main", true, true);

    @Override
    public void onEnable() {
        project = this;
        commandInit.initializeCommands();
        vaultInitializer.setup();
        logger.log(Level.INFO, (String) mainConfig.getConfig().get("startup-readout"));
    }

    @Override
    public void onDisable() {

    }
}
