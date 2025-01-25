package levithean.mainplugin.commands;

import levithean.mainplugin.api.ChatManager;
import levithean.mainplugin.api.Player;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHeal implements CommandExecutor {

    private Player player;
    private String commande;
    private final String prefix = ChatManager.getCommandsPrefix(1);
    private boolean heal_impossible;
    private boolean full_vie;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        commande = label;

        if(sender instanceof org.bukkit.entity.Player) {
            player = new Player(sender);

            if(args.length == 0)
                selfHeal();

            else if(args.length == 1) {
                Player target = new Player(args[0]);
                Heal(target, args[0]);

            } else
                showHelp();
        } else
            ChatManager.sendConsole(sender, prefix + "&cYou need to be a player to perform this command");

        return false;
    }

    private void selfHeal() {
        heal_impossible = player.hasMode(GameMode.CREATIVE) || player.hasMode(GameMode.SPECTATOR);
        full_vie = player.hasMaxHealth() || player.hasMaxFood();

        if(heal_impossible)
            player.sendMessage("&cErreur: Vous devez être en mode survie ou aventure !");

        else {

            if(full_vie)
                player.sendMessage("&cErreur: Vous devez avoir perdu quelques coeurs ou avoir faim !");

            else {
                player.setHealth(20);
                player.setFood(20);
                player.sendMessage(prefix + "Vous vous êtes guéri !");
            }
        }
    }

    private void Heal(Player target, String saisie_player) {
        String joueur = player.getName();

        if(target.isOnline()) {

            if(target.isSameAs(joueur))
                selfHeal();

            else {
                heal_impossible = target.hasMode(GameMode.CREATIVE) || target.hasMode(GameMode.SPECTATOR);
                full_vie = target.hasMaxHealth() || target.hasMaxFood();
                String cible = target.getName();

                if(heal_impossible)
                    player.sendMessage("&cErreur: Vous devez être en mode survie ou aventure !");

                else {

                    if(full_vie)
                        player.sendMessage("&cErreur: Le joueur &b" + target.getName() + " &cdoit avoir perdu quelques coeurs ou avoir faim !");

                    else {
                        target.setHealth(20);
                        target.setFood(20);
                        player.sendMessage(prefix + "Vous avez guéri &b" + cible);
                        target.sendMessage(prefix + "Vous avez été guéri par &b" + joueur + " &f!");
                    }
                }
            }
        } else
            player.sendMessage("&cErreur: Le joueur '" + saisie_player + "' n'est pas connecté !");
    }

    private void showHelp() {
        player.sendEmptyLine();
        ChatManager.newHelpPage(player, prefix, 1, 1);
        ChatManager.addtoHelpPage(player, commande, "Se guérir");
        ChatManager.addtoHelpPage(player, commande, "[<mode>]", "Guérir un joueur");
        player.sendEmptyLine();
    }
}
