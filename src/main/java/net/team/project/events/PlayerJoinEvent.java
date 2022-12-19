package net.team.project.events;

import net.team.project.Project;
import net.team.project.utils.User;
import net.team.project.utils.UserH;
import net.team.project.utils.general;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(org.bukkit.event.player.PlayerJoinEvent e) {
        Project project = Project.getInstance();
        UUID uuid = e.getPlayer().getUniqueId();

        // Add user to active users / load their configuration
        UserH.userList.put(uuid, new User(project, uuid));

        if (!e.getPlayer().hasPlayedBefore()) {
            UserH.userList.get(uuid).getConfig().set("first-join", LocalDateTime.now().toString());
        }

        // if join message in player's config is not set use from server config
        e.setJoinMessage(
                UserH.userList.get(uuid).getConfig().getString("join-message") != null
                ? general.fixPlaceholders(uuid, general.Fg(UserH.userList.get(uuid).getConfig().getString("join-message")))
                : general.fixPlaceholders(uuid, general.Fg(project.cfh.main.getConfig().getString("default-join-message"))));
    }
}
