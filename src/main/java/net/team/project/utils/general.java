package net.team.project.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.milkbowl.vault.chat.Chat;
import net.team.project.Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class general {
    private final static Chat chat = VaultInitializer.getChat();

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
    public static String F(String toFormat) {
        return ChatColor.translateAlternateColorCodes('&', toFormat);
    }

    public static String Fg(String path) { // "format" and get from config
        try {
            return F(Project.getInstance().cfh.messages.getConfig().getString(path));
        } catch (java.lang.IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "Error on net.team.project.utils.general.Fg("+path+")"+ "contact admin/developer";
    }

    public static void sendDM(CommandSender toSend, String replace0, String replace1, String replace2) {
        toSend.sendMessage(Fg("message-format")
                .replace("{0}", replace0)
                .replace("{1}", replace1)
                .replace("{2}", replace2));
    }

    public static Component fixPlaceholders(UUID uuid, String input) {
        Player p = Bukkit.getPlayer(uuid);
        String mainGroup = chat.getPlayerGroups(p)[0];
        TextReplacementConfig config = TextReplacementConfig.builder().matchLiteral("{DISPLAYNAME}")
                .replacement(p.displayName().hoverEvent(
                        Component.text(p.getName() + "\n" + uuid + "\n" + (p.isOnline() ?
                                UserH.userList.get(uuid).getConfig().getString("first-join") :
                                ""))))
                .matchLiteral("{PREFIX}")
                .replacement(Component.text(mainGroup != null ?
                                chat.getGroupPrefix(p.getWorld(), mainGroup) :
                                "")
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Prefix from group/user, users main group: " + mainGroup))))
                .matchLiteral("{SUFFIX}")
                .replacement(Component.text(mainGroup != null ?
                                chat.getGroupSuffix(p.getWorld(), mainGroup) :
                                "")
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Prefix from group/user, users main group: " + mainGroup))))
                .matchLiteral("{TAG}")
                .replacement(Component.text(checkElseEmpty(UserH.userList.get(p.getUniqueId()).getConfig().getString("tag")))
                        .hoverEvent(HoverEvent.showText(Component.text("Given to user by: " + checkElseEmpty(UserH.userList.get(p.getUniqueId()).getConfig().getString("tag-from"))))))
                .matchLiteral("{USERNAME}")
                .replacement(Component.text(p.getName())
                        .hoverEvent(HoverEvent.showText(Component.text(uuid +
                                "\n" +
                                (p.isOnline() ?
                                        UserH.userList.get(uuid).getConfig().getString("first-join") :
                                        "") +
                                "\n" +
                                "&b" + p.getWorld().getName()))))
                .matchLiteral("{PING}").replacement(Component.text(p.getPing()))
                .build();

        Component comp = Component.text(input).replaceText(config);
        return comp;
    }

    public static String checkElseEmpty(String string) {
        return string != null ? string : "";
    }

    /*public static String fixPlaceholders(UUID uuid, String s) {
        Player p = Bukkit.getPlayer(uuid);
        String mainGroup = chat.getPlayerGroups(p)[0];
        return s.replace("{USERNAME}", p.getName())
                    .replace("{DISPLAYNAME}", p.displayName()
                    .replace("{PREFIX}", mainGroup != null ? chat.getGroupPrefix(p.getWorld(), mainGroup) : "")
                    .replace("{SUFFIX}", mainGroup != null ? chat.getGroupSuffix(p.getWorld(), mainGroup) : "")
                    .replace("{PING}", p.getPing()+"")
                    .replace("{TAG}",
                            UserH.userList.get(p.getUniqueId()).getConfig().getString("tag") != null
                            ? UserH.userList.get(p.getUniqueId()).getConfig().getString("tag")
                            : "");
    }*/
}
