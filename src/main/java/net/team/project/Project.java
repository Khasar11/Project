package net.team.project;

import java.util.logging.Logger;

import net.team.project.commandRegistration.CommandRegistrator;
import net.team.project.configuration.ConfigurationFile;
import net.team.project.vault.VaultInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Project extends JavaPlugin {
    private Project project;
    public Logger logger;

    ConfigurationFile mainConfig;
    ConfigurationFile announceConfig;
    private CommandRegistrator commandRegistrator;
    private VaultInitializer vaultInitializer;

    @Override
    public void onEnable() {
        project = this;
        logger = getLogger();

        commandRegistrator= new CommandRegistrator(project);
        vaultInitializer = new VaultInitializer(project);

        mainConfig = new ConfigurationFile(project, project.getDataFolder(), "cfg-main", true, true);

        commandRegistrator.initializeCommands();
        vaultInitializer.setup();

        logger.info(mainConfig.getConfig().getString("startup-readout"));
    }

    @Override
    public void onDisable() {
    logger.info("Disabling plugin");
    }
}
