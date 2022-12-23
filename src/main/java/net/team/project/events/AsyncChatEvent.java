package net.team.project.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.team.project.Project;
import net.team.project.utils.UserH;
import net.team.project.utils.general;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.UUID;

public class AsyncChatEvent implements Listener {

    @EventHandler
    public void asyncPlayerChatEvent(io.papermc.paper.event.player.AsyncChatEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        MiniMessage mm = MiniMessage.miniMessage();

        String original = PlainTextComponentSerializer.plainText().serialize(e.message());
        for (Player p : Project.getInstance().getServer().getOnlinePlayers()) {
            if (original.contains(p.getName())) {
                p.playSound(p, Sound.valueOf(Project.getInstance().cfh.main.getConfig().getString("ping-sound")), 2, 2);
            }
        }

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
