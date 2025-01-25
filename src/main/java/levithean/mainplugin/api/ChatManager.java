package levithean.mainplugin.api;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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

    public static void setInteractiveCommand(TextComponent message_formate, String commande) {
        message_formate.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, setColor("/" + commande)));
    }

    public static void setHoverInteractiveText(TextComponent message_formate, String message) {
        message_formate.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(setColor(message))));
    }

    public static void setInteractiveMessage(TextComponent message_formate, String message) {

        if(message_formate == null)
            message_formate = new TextComponent(setColor(message));
        else
            message_formate.setText(setColor(message));
    }

    public static void addInteractiveText(TextComponent message_formate, String message) {
        message_formate.addExtra(setColor(message));
    }

    public static void addInteractiveText(TextComponent message_formate, TextComponent message) {
        message_formate.addExtra(message);
    }

    public static void newHelpPage(Player player, String prefix, int page, int nombre_pages) {
        player.sendMessage(prefix + "Page d'aide &a[" + page + "/" + nombre_pages + "] &f:");
        player.sendEmptyLine();
    }

    public static void addtoHelpPage(Player player, String commande, String description) {
        player.sendMessage("&a/" + commande + "&f: " + description);
    }

    public static void addtoHelpPage(Player player, String commande, String args, String description) {
        player.sendMessage("&a/" + commande + " &6" + args + "&f: " + description);
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
