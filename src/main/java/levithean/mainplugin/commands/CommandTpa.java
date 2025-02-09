package levithean.mainplugin.commands;

import levithean.mainplugin.api.ChatManager;
import levithean.mainplugin.api.HelpPageManager;
import levithean.mainplugin.api.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTpa implements CommandExecutor {

    private Player player;
    private final String prefix = ChatManager.getCommandsPrefix(4);
    private String commande;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        commande = label;

        if(sender instanceof org.bukkit.entity.Player) {
            player = new Player(sender);

            if(args.length == 0)
                showHelp();

            else if(args.length == 1) {
                Player target = new Player(args[0]);
                Teleport(target, args[0]);
            } else
                showHelp();
        } else
            ChatManager.sendConsole(sender, prefix + "&cYou need to be a player to perform this command");

        return false;
    }

    private void Teleport(Player target, String saisie_player) {

        if(target.isOnline()) {

            if(target.isSameAs(player.getName()))
                player.sendMessage("&cErreur: Vous ne pouvez pas vous téléporter à vous même !");

            else {
                player.teleportTo(target);
                player.sendMessage(prefix + "Vous vous êtes téléporté à &b" + target.getName() + " &f!");
            }

        } else
            player.sendMessage("&cErreur: Le joueur '" + saisie_player + "' n'est pas connecté !");
    }

    private void showHelp() {
        player.sendEmptyLine();
        HelpPageManager help = new HelpPageManager(player, prefix, 1, 1);
        help.addtoHelpPage(player, commande, "<joueur>", "Se téléporter à un joueur");
        player.sendEmptyLine();
    }
}
