package levithean.mainplugin.commands;

import levithean.mainplugin.api.ChatManager;
import levithean.mainplugin.api.Player;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandFly implements CommandExecutor {

    private Player player;
    private String commande;
    private final String prefix = ChatManager.getCommandsPrefix(0);
    private boolean fly_impossible;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        commande = label;

        if(sender instanceof org.bukkit.entity.Player) {
            player = new Player(sender);

            if(args.length == 0)
                selfFly();

            else if(args.length == 1) {

                if(args[0].equalsIgnoreCase("help"))
                    showHelp();

                else {
                    Player target = new Player(args[0]);
                    Fly(target, args[0]);
                }

            } else
                showHelp();

        } else
            ChatManager.sendConsole(sender, prefix + "&cYou need to be a player to perform this command");

        return false;
    }

    private void selfFly() {

        fly_impossible = player.hasMode(GameMode.CREATIVE) || player.hasMode(GameMode.SPECTATOR);

        if(fly_impossible)
            player.sendMessage("&cErreur: Vous devez être en mode survie ou aventure !");

        else {

            if(!player.hasFlyPerm()) {
                player.setFly(true);
                player.sendMessage(prefix + "&aActivé");

            } else {
                player.setFly(false);
                player.sendMessage(prefix + "&cDésactivé");
            }
        }
    }

    private void Fly(Player target, String saisie_player) {
        String joueur = player.getName();

        if(target.isOnline()) {

            if(target.isSameAs(joueur))
                selfFly();

            else {
                fly_impossible = target.hasMode(GameMode.CREATIVE) || target.hasMode(GameMode.SPECTATOR);
                String cible = target.getName();

                if (fly_impossible)
                    player.sendMessage("&cErreur: &b" + cible + " &cdoit être en mode survie ou aventure !");

                else {

                    if (!target.hasFlyPerm()) {
                        target.setFly(true);
                        player.sendMessage(prefix + "Vous avez autorisé &b" + cible + " &fà voler sur le serveur !");

                        if (!target.isSameAs(joueur))
                            sendMessage(target, true);

                    } else {
                        target.setFly(false);
                        player.sendMessage(prefix + "Le joueur &b" + cible + " &fne peut dorénavant plus voler sur le serveur !");
                        sendMessage(target, false);
                    }
                }
            }
        } else
            player.sendMessage("&cErreur: Le joueur '" + saisie_player + "' n'est pas connecté !");
    }

    private void sendMessage(Player target, boolean fly) {

        String joueur = player.getName();

        target.sendEmptyLine();
        ChatManager.addSeparator(target);
        target.sendEmptyLine();

        if(fly) {
            target.sendMessage(prefix + "On dirait que c'est votre jour de chance !");
            target.sendEmptyLine();
            target.sendMessage("Un admin (&b" + joueur + "&f) vous &aautorise &fà voler sur le serveur !");
            target.sendEmptyLine();
            target.sendMessage("&7&oEnjoy :)");

        } else {
            target.sendMessage(prefix + "Un admin (&b" + joueur + "&f) vous a &cretiré &fl'autorisation de voler sur le serveur !");
            target.sendEmptyLine();
            target.sendMessage("&eNous espérons que vous avez été sage comme une image :0");
        }

        target.sendEmptyLine();
        ChatManager.addSeparator(target);
        target.sendEmptyLine();
    }

    private void showHelp() {
        player.sendEmptyLine();
        ChatManager.newHelpPage(player, prefix, 1, 1);
        ChatManager.addtoHelpPage(player, commande, "Pouvoir voler en mode survie ou aventure");
        ChatManager.addtoHelpPage(player, commande, "<pseudo>", "Autoriser un joueur à voler sur le serveur");
        player.sendEmptyLine();
    }
}
