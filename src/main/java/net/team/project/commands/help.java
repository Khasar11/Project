package net.team.project.commands;

import net.team.project.api.commandAPI.Command;
import net.team.project.api.commandAPI.paramter.Param;
import net.team.project.utils.general;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class help {
    @Command(names = {"help"}, permission = "project.help", description = "Open the help menu")
    public void helpCommand(CommandSender sender, @Param(name = "page", required = false) String page) {
        Map<String, String> commandList = new TreeMap<>();
        List<Map<String, String>> helpPages;
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            for (HelpTopic cmd : plugin.getServer().getHelpMap().getHelpTopics()) {
                if (!cmd.canSee(sender) || !cmd.getName().startsWith("/"))
                    continue;
                commandList.put(cmd.getName(), cmd.getShortText());
            }
        }

        helpPages = splitMap(commandList, Math.round(commandList.size() / 10));

        if (page == null) page = "1";

        try {
            if (helpPages.get(Integer.parseInt(page)) == null) ;
        } catch (Exception e) {
            page = "1";
        }
        page = Integer.parseInt(page) - 1 + "";
        Map<String, String> toSend;
        try {
            toSend = helpPages.get(Integer.parseInt(page));
        } catch (Exception e) {
            toSend = helpPages.get(Integer.parseInt("0"));
        }
        sender.sendMessage(general.sFg("help-top").replace("{0}", Integer.parseInt(page) + 1 + "").replace("{1}", helpPages.size() - 1 + ""));
        for (String key : toSend.keySet()) {
            sender.sendMessage(general.sFg("help-commands")
                    .replace("{0}", key)
                    .replace("{1}", toSend.get(key)));
        }
        sender.sendMessage(general.sFg("help-bottom")
                .replace("{0}", page)
                .replace("{1}", helpPages.size() + ""));
    }

    public static List<Map<String, String>> splitMap(Map<String, String> originalMap, int splitSize) {
        int mapSize = originalMap.size();
        int elementsPerNewMap = mapSize / splitSize;
        List<Map<String, String>> newListOfMaps = new ArrayList<>();
        List<List<String>> listOfMapKeysForIndexing = new ArrayList<>();
        List<String> listOfAllKeys = new ArrayList<>(originalMap.keySet());
        int maxIndex = listOfAllKeys.size() - 1;
        int startIndex = 0;
        int endIndex = elementsPerNewMap;
        for (int i = 0; i < splitSize; i++) {
            listOfMapKeysForIndexing.add(listOfAllKeys.subList(startIndex, endIndex));
            startIndex = Math.min((endIndex + 1), maxIndex);
            endIndex = Math.min((endIndex + elementsPerNewMap), maxIndex);
        }

        for (List<String> keyList : listOfMapKeysForIndexing) {
            Map<String, String> subMap = new TreeMap<>();
            for (String key : keyList) {
                subMap.put(key, originalMap.get(key));
            }
            newListOfMaps.add(subMap);
        }
        return newListOfMaps;
    }
}
