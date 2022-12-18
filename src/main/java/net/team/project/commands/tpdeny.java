package net.team.project.commands;

import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import org.bukkit.entity.Player;

public class tpdeny {
    @Command(names = {"tpaccept", "tpyes"}, permission = "project.tpa", playerOnly = true)
    public void _tpdeny(Player player, @Param(name = "player", required = false) Player target) {

    }
}
