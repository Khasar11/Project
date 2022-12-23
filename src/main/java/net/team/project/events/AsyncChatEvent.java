package net.team.project.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.team.project.utils.UserH;
import net.team.project.utils.general;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.UUID;

public class AsyncChatEvent implements Listener {

    @EventHandler
    public void asyncPlayerChatEvent(io.papermc.paper.event.player.AsyncChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        MiniMessage mm = MiniMessage.miniMessage();

        // set chat format
        e.renderer((source, sourceDisplayName, message, viewer) ->
                Component.text("")
                        .append(UserH.userList.get(uuid).getConfig().getString("chat-format") != null
                                ? general.fixPlaceholders(uuid, Component.text(UserH.userList.get(uuid).getConfig().getString("chat-format")))
                                : general.fixPlaceholders(uuid, general.Mg("default-chat-format"))
                        .append(e.getPlayer().hasPermission("project.chatformat")
                                ? mm.deserialize(PlainTextComponentSerializer.plainText().serialize(message))
                                : message))
        );
    }
}
