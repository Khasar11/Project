package net.team.project.commands;

import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import org.bukkit.entity.Player;

public class tpo {
    @Command(names = {"tpo"}, permission = "project.tpo", playerOnly = true, description = "teleport to a player secretly")
    public void tpo(Player player, @Param(name = "player")
                    Player target, @Param(name = "other-player", required = false) Player otherTarget) {
        final boolean b = otherTarget == null ? player.teleport(target) : target.teleport(otherTarget);
    }
}
