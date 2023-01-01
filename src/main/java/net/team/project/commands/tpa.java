package net.team.project.commands;

import net.kyori.adventure.text.TextReplacementConfig;
import net.team.project.Project;
import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import net.team.project.utils.TeleportRequest;
import net.team.project.utils.general;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class tpa {
    @Command(names = {"tpdeny", "tpno"}, permission = "project.tpa", description = "Send a teleportation request", playerOnly = true)
    public void tpRequest(Player sender, @Param(name = "player") Player target) {
        Project project = Project.getInstance();
        TeleportRequest request = new TeleportRequest(sender.getUniqueId(),
                target.getUniqueId(),
                LocalDateTime.now());
        // add the request to the plugins temporary storage
        project.temporaryStorage.teleportationRequestCollection.add(request);

        sender.sendMessage(general.Mg("teleport-requested-sender")
                .replaceText(TextReplacementConfig.builder().matchLiteral("{0}").replacement(target.name()).build()));
        target.sendMessage(general.Mg("teleport-requested-target")
                .replaceText(TextReplacementConfig.builder().matchLiteral("{0}").replacement(sender.name()).build()));

        project.getServer().getScheduler().scheduleSyncDelayedTask(Project.getInstance(), () -> {
            sender.sendMessage(general.Mg("teleport-requested-expire")
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{0}").replacement(target.name()).build()));
            project.temporaryStorage.teleportationRequestCollection.remove(request);
        }, Long.parseLong((String) general.Cg("teleportation-expire-time-seconds")));
    }
}
