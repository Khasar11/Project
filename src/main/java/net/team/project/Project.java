package net.team.project;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.team.project.economy.PEconomy;
import net.team.project.utils.CommandRegistrator;
import net.team.project.utils.VaultInitializer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class Project extends JavaPlugin {
    private static Project project;
    public Logger logger;
    public ConfigurationHeader cfh;

    @Override
    public void onEnable() {
        project = this;
        if (cfh.main.getConfig().getBoolean("enabled")) {
            logger = getLogger();

            CommandRegistrator commandRegistrator = new CommandRegistrator(project);
            EventRegistrator eventRegistrator = new EventRegistrator(project);
            VaultInitializer vaultInitializer = new VaultInitializer(project);

            cfh.initializeConfigurations();

            // Register the plugin as an economy provider for vault
            project.getServer().getServicesManager().register(Economy.class, new PEconomy(), this, ServicePriority.Highest);

            commandRegistrator.initializeCommands();
            eventRegistrator.initializeEvents();
            //vaultInitializer.setup();

            logger.info(cfh.messages.getConfig().getString("startup"));
        } else logger.warning(cfh.messages.getConfig().getString("disabled"));
    }

    @Override
    public void onDisable() {
        logger.info(cfh.messages.getConfig().getString("disable"));
    }

    public static Project getInstance() { return project; }
}
