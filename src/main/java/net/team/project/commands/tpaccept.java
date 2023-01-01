package net.team.project.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.team.project.Project;
import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import net.team.project.utils.TeleportRequest;
import net.team.project.utils.general;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class tpaccept {

    private void handleTeleport(Player target, Player sender, Project project, TeleportRequest request) {
        sender.sendMessage(general.Mg("teleport-accept-sender")
                .replaceText(TextReplacementConfig.builder().matchLiteral(" {0}")
                        .replacement(Component.text(target.name() + "")).build()));
        target.sendMessage(general.Mg("teleport-accept-target")
                .replaceText(TextReplacementConfig.builder().matchLiteral("{0}")
                        .replacement(sender.getName()).build()));

        general.delayedTeleport(target, sender, project);

        project.temporaryStorage.teleportationRequestCollection.remove(request);
    }

    @Command(names = {"tpaccept", "tpyes"}, permission = "project.tpa", description = "Accept a teleportation request", playerOnly = true)
    public void tpRequest(Player sender, @Param(name = "player", required = false) Player target) {
        Project project = Project.getInstance();
        Stream<TeleportRequest> request = project.temporaryStorage.teleportationRequestCollection
                .stream().filter(e -> e.to == sender.getUniqueId());
        if (request.findFirst().isPresent()) {
            TeleportRequest targettedRequest = request.filter(e -> e.from == target.getUniqueId()).findFirst().orElse(null);
            TeleportRequest nonTargettedRequest = request.findFirst().orElse(null);
            if (targettedRequest != null) {
                handleTeleport(target, sender, project,  targettedRequest);
            } else {
                if (nonTargettedRequest != null) {
                    Player newTarget = Bukkit.getPlayer(nonTargettedRequest.from);
                    handleTeleport(newTarget, sender, project,  nonTargettedRequest);
                } else {
                    sender.sendMessage(general.Mg("teleport-no-active-requests"));
                }
            }
        } else {
            sender.sendMessage(general.Mg("teleport-no-active-requests"));
        }
    }
}
