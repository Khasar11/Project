package net.team.project.events;

import net.team.project.Project;
import net.team.project.utils.User;
import net.team.project.utils.UserH;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void playerJoinEvent(org.bukkit.event.player.PlayerJoinEvent e) {
        Project project = Project.getInstance();
        UUID uuid = e.getPlayer().getUniqueId();
        UserH.userList.put(uuid, new User(project, uuid));

        if (!e.getPlayer().hasPlayedBefore()) {
            UserH.userList.get(uuid).getConfig().set("first-join", LocalDateTime.now().toString());
        }
    }
}
