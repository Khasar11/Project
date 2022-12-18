package net.team.project.events;

import net.team.project.Project;
import net.team.project.utils.UserH;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerQuitEvent implements Listener {
    @EventHandler
    public void playerQuitEvent(org.bukkit.event.player.PlayerQuitEvent e) {
        Project project = Project.getInstance();
        UUID uuid = e.getPlayer().getUniqueId();
        UserH.userList.get(uuid).save();
        UserH.userList.remove(e.getPlayer().getUniqueId());
        project.logger.info("Unloaded configuration file with name " + uuid);
    }
}
