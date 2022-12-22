package net.team.project.events;

import net.kyori.adventure.text.Component;
import net.team.project.utils.UserH;
import net.team.project.utils.general;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.UUID;

public class AsyncChatEvent implements Listener {

    @EventHandler
    public void asyncPlayerChatEvent(io.papermc.paper.event.player.AsyncChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        // set chat format
        e.renderer((source, sourceDisplayName, message, viewer) ->
                Component.text("")
                        .append(UserH.userList.get(uuid).getConfig().getString("chat-format") != null
                                ? general.fixPlaceholders(uuid, general.F(UserH.userList.get(uuid).getConfig().getString("chat-format")))
                                : general.fixPlaceholders(uuid, general.Fg("default-chat-format"))
                        .append(message))
        );
    }
}
