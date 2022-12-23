package net.team.project.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.chat.Chat;
import net.team.project.Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class general {
    private final static Chat chat = VaultInitializer.getChat();
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static String nowFormatted() {
        java.util.Date date = new java.util.Date();
        return date.toString();
    }

    public static long strToUnix(String s) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
        Date date = new Date();
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    public static String unixToStr(long unix) {
        Date d = new Date(unix * 1000);
        return d.toString();
    }

    public static long getUnix() {
        return System.currentTimeMillis() / 1000L;
    }


    // string format
    public static Component Mg(String path) { // "format" and get from config
        try {
            return mm.deserialize(Project.getInstance().cfh.messages.getConfig().getString(path));
        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Component.text("Error on net.team.project.utils.general.Fg("+path+")"+ "contact admin/developer");
    }

    public static Component Ug(UUID uuid, String path) {
        return UserH.userList.get(uuid).getConfig().getString(path) != null ?
             mm.deserialize(UserH.userList.get(uuid).getConfig().getString(path)) : Component.text("");
    }

    public static Component fixPlaceholders(UUID uuid, Component input) {
        Player p = Bukkit.getPlayer(uuid);
        String mainGroup = chat.getPlayerGroups(p)[0];
        TextReplacementConfig config = TextReplacementConfig.builder()
                .match("\\{DISPLAYNAME\\}|\\{PREFIX\\}|\\{SUFFIX\\}|\\{TAG\\}|\\{USERNAME\\}|\\{PING\\}")
                .replacement((matchResult, builder) -> {
                    switch (matchResult.group()) {
                        case "{USERNAME}":
                            builder = builder.content("").append(Component.text(p.getName())
                                .hoverEvent(HoverEvent.showText(Component.text(uuid +
                                        "\n" + p.getWorld().getName()))));
                            break;
                        case "{DISPLAYNAME}":
                            builder = builder.content("").append(p.displayName().hoverEvent(
                                    Component.text(p.getName() + "\n" + uuid + ("\n"+p.getWorld().getName()))));
                            break;
                        case "{PING}":
                            builder = builder.content("").append(Component.text(p.getPing()));
                            break;
                        case "{PREFIX}":
                            builder = builder.content("").append(mm.deserialize(mainGroup != null ?
                                            chat.getGroupPrefix(p.getWorld(), mainGroup) :
                                            "")
                                    .hoverEvent(HoverEvent.showText(
                                            Component.text("Prefix from group/user, users main group: " + mainGroup))));
                            break;
                        case "{SUFFIX}":
                            builder = builder.content("").append(mm.deserialize(mainGroup != null ?
                                            chat.getGroupSuffix(p.getWorld(), mainGroup) :
                                            "")
                                    .hoverEvent(HoverEvent.showText(
                                            Component.text("Suffix from group/user, users main group: " + mainGroup))));
                            break;
                        case "{TAG}":
                            builder = builder.content("").append(
                                    Ug(uuid, "tag")
                                    .hoverEvent(HoverEvent.showText(
                                            Component.text("Given to user by: " +
                                                    Ug(uuid, "tag-from")))));
                            break;
                        case "{WORLD}":
                            builder = builder.content("").append(Component.text(p.getWorld().getName()));
                    }
                    return builder;
                }).build();
        return input.replaceText(config);
    }
}
