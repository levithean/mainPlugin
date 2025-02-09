package levithean.mainplugin.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ChatManager {

    private static final ArrayList<String> prefix = new ArrayList<>();

    protected static String setColor(String texte) {
        return ChatColor.translateAlternateColorCodes('&', texte);
    }

    public static void sendConsole(CommandSender sender, String message) {
        sender.sendMessage(setColor(message));
    }

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(setColor(message));
    }

    public static void addSeparator(Player player) {
        player.sendMessage("&6-----------------------------------------------------");
    }

    public static String getCommandsPrefix(int index) {

        prefix.add("&6[Fly] &f"); //0
        prefix.add("&6[Heal] &f"); //1
        prefix.add("&6[Gamemode] &f"); //2
        prefix.add("&6[Whois] &f"); //3
        prefix.add("&6[Teleport] &f"); //4

        return prefix.get(index);
    }
}