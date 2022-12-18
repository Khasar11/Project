package net.team.project.commands;

import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import org.bukkit.entity.Player;

public class tpa {
    @Command(names = {"tpa", "tpask"}, permission = "project.tpa", playerOnly = true)
    public void _tpa(Player player, @Param(name = "player") Player target) {

    }
}
