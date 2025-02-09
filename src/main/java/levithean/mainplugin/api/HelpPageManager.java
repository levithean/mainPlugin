package levithean.mainplugin.api;

public class HelpPageManager {

    public HelpPageManager(Player player, String prefix, int page, int nombre_pages) {
        player.sendMessage(prefix + "Page d'aide &a[" + page + "/" + nombre_pages + "] &f:");
        player.sendEmptyLine();
    }

    public void addtoHelpPage(Player player, String commande, String description) {
        player.sendMessage("&a/" + commande + "&f: " + description);
    }

    public void addtoHelpPage(Player player, String commande, String args, String description) {
        player.sendMessage("&a/" + commande + " &6" + args + "&f: " + description);
    }
}
