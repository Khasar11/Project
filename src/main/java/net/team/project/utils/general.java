package net.team.project.utils;

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

    // get messages.yml string
    public static String g(String path) {
        return Project.getInstance().cfh.messages.getConfig().getString(path);
    }

    // string format
    public static String sF(String toFormat) {
        return ChatColor.translateAlternateColorCodes('&', toFormat);
    }

    // get from path and return formatted
    public static String sFg(String path) {
        return sF(g(path));
    }

    public static boolean checkPerm(CommandSender sender, String perm) {
        if (sender.hasPermission(perm)) {
            return true;
        }
        sender.sendMessage(sFg("no-permission").replace("{0}", perm));
        return false;
    }

    public static boolean isConsoleUser(CommandSender sender) {
        if (sender instanceof Player) return false;
        sender.sendMessage(sFg("cuse"));
        return true;
    }

    public static void sendDM(CommandSender toSend, String replace0, String replace1, String replace2) {
        toSend.sendMessage(sFg("message-format")
                .replace("{0}", replace0)
                .replace("{1}", replace1)
                .replace("{2}", replace2));
    }

    public static String fixPlaceholders(UUID uuid, String s) {
        Player p = Bukkit.getPlayer(uuid);
        String mainGroup = chat.getPlayerGroups(p)[0];
        return s.replace("{USERNAME}", p.getName())
                    .replace("{DISPAYNAME}", p.displayName().toString()
                    .replace("{PREFIX}", chat.getGroupPrefix(p.getWorld(), mainGroup))
                    .replace("{SUFFIX}", chat.getGroupSuffix(p.getWorld(), mainGroup))
                    .replace("{PING}", p.getPing()+""));
    }
}
