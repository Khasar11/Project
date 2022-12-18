package net.team.project.utils;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.team.project.Project;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultInitializer {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    private final Server server;
    Project project;
    public VaultInitializer(Project project) {
        this.project = project;
        this.server = project.getServer();
        project.logger.info("Initialized vault initializer");
    }

    public static Permission getPermissions() {
        return perms;
    }
    public static Chat getChat() {
        return chat;
    }
    public static Economy getEconomy() {
        return econ;
    }

    public void setup() {
        if (!setupEconomy() ) {
            project.logger.severe("No vault dependency found, please install vault, a lot of features will not work without it");
            return;
        }
        setupPermissions();
        setupChat();
        project.logger.info("Loaded vault dependency");
    }

    private boolean setupEconomy() {
        if (server.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = server.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = server.getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
