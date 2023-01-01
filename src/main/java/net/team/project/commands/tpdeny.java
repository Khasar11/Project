package net.team.project.commands;

import net.kyori.adventure.text.TextReplacementConfig;
import net.team.project.Project;
import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import net.team.project.utils.general;
import org.bukkit.entity.Player;

public class tpdeny {

     @Command(names = {"tpdeny", "tpno"}, permission = "project.tpa", description = "Deny a teleportation request", playerOnly = true)
     public void tpRequest(Player sender, @Param(name = "player", required = false) Player target) {
          Project project = Project.getInstance();
          // removes teleport request from temporary storage if found
          if (target != null) {
              project.temporaryStorage.teleportationRequestCollection
                      .remove(
                              project.temporaryStorage.teleportationRequestCollection.stream()
                                      .filter(e -> e.from == target.getUniqueId() && e.to == sender.getUniqueId())
                                      .findAny()
                                      .orElse(null)
                      );
              sender.sendMessage(general.Mg("teleport-deny-sender-yesarg")
                      .replaceText(TextReplacementConfig.builder()
                              .matchLiteral("{0}").replacement(target.name())
                              .build()));
              target.sendMessage(general.Mg("teleport-deny-target")
                      .replaceText(TextReplacementConfig.builder()
                              .matchLiteral("{0}").replacement(sender.name())
                              .build()));
          }
          else { // Removes any teleport request mentioning the sender if target isnt specified
              project.temporaryStorage.teleportationRequestCollection
                      .remove(
                              project.temporaryStorage.teleportationRequestCollection.stream()
                                      .filter(e -> e.to == sender.getUniqueId())
                                      .findAny()
                                      .orElse(null)
                      );
              sender.sendMessage(general.Mg("teleport-deny-sender-noarg"));
          }
     }
}
