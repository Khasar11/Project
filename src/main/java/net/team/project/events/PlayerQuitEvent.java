package net.team.project.events;

import net.kyori.adventure.text.Component;
import net.team.project.Project;
import net.team.project.utils.UserH;
import net.team.project.utils.general;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerQuitEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(org.bukkit.event.player.PlayerQuitEvent e) {
        Project project = Project.getInstance();
        UUID uuid = e.getPlayer().getUniqueId();


        // if join message in player's config is not set use from server config
        final Component message = UserH.userList.get(uuid).getConfig().getString("quit-message") != null
                ? general.fixPlaceholders(uuid, Component.text(UserH.userList.get(uuid).getConfig().getString("quit-message")))
                : general.fixPlaceholders(uuid, general.Mg("default-quit-message"));

        e.quitMessage(Component.text().append(message).build());

        // This should always stay at bottom of quit event
        UserH.userList.get(uuid).save();
        UserH.userList.remove(e.getPlayer().getUniqueId());
        project.logger.info("Unloaded configuration file with name " + uuid);


    }
}
