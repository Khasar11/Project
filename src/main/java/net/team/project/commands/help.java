package net.team.project.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import net.team.project.utils.general;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class help {

    @Command(names = {"help"}, permission = "project.help", description = "show help to user")
    public void cmd(CommandSender sender, @Param(name = "page", required = false) String pageString) {
        int page;
        try {
            page = Integer.parseInt(pageString);
        } catch (NumberFormatException e) { page = 1; };

        TreeMap<String, String> commands = new TreeMap<>(Collections.emptySortedMap());
        List<Map<String, String>> pages = new LinkedList<>();

        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            for (HelpTopic cmd : plugin.getServer().getHelpMap().getHelpTopics()) {
                commands.put(cmd.getName(), cmd.getShortText());
            }
        }

        int pageKeyValueIndex = 0;
        int entriesPerPage = 10;
        Map<String, String> nullpage = new TreeMap<>();
        nullpage.put("", "");
        pages.add(nullpage);
        Map<String, String> currentPage = new TreeMap<>();
        for (String s : commands.keySet()) {
            if (pageKeyValueIndex % entriesPerPage == 0) {
                currentPage = new TreeMap<>();
                if (pageKeyValueIndex != 0) {
                    pages.add(currentPage);
                }
            }
            currentPage.put(s, commands.get(s));
            pageKeyValueIndex++;
        }

        if (page >= pages.size()) page = 1;

        Map<String, String> toSend = pages.get(page);
        if (page == 0) { sender.sendMessage(general.Mg("0th-help-page")); return; }
        sender.sendMessage(general.Mg("help-top")
                .replaceText(
                        general.replacementMaker("{0}", Component.text(page+"")))
                .replaceText(
                        general.replacementMaker("{1}", Component.text(pages.size()+1))));
        for (String s : toSend.keySet()) {
            sender.sendMessage(general.Mg("help-commands")
                    .replaceText(TextReplacementConfig.builder()
                            .matchLiteral("{0}").replacement(s).build())
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{1}").replacement(toSend.get(s))
                            .build()));
        }
        sender.sendMessage(general.Mg("help-bottom"));
    }
}
