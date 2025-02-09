package levithean.mainplugin.commands;

import levithean.mainplugin.api.ChatManager;
import levithean.mainplugin.api.HelpPageManager;
import levithean.mainplugin.api.Player;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandGamemode implements CommandExecutor {

    private Player player;
    private String commande;
    private final String prefix = ChatManager.getCommandsPrefix(2);
    private boolean survie;
    private boolean creatif;
    private boolean aventure;
    private boolean sp;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        commande = label;

        if(sender instanceof org.bukkit.entity.Player) {
            player = new Player(sender);

            if(args.length == 0)
                showHelp();

            else if(args.length == 1)
                selfGamemode(args[0]);

            else if(args.length == 2) {
                Player target = new Player(args[1]);
                Gamemode(target, args[0], args[1]);

            } else
                showHelp();
        } else
            ChatManager.sendConsole(sender, prefix + "&cYou need to be a player to perform this command");

        return false;
    }

    private void selfGamemode(String mode) {
        survie = mode.equalsIgnoreCase("survival") || mode.equals("0");
        creatif = mode.equalsIgnoreCase("creative") || mode.equals("1");
        aventure = mode.equalsIgnoreCase("aventure") || mode.equals("2");
        sp = mode.equalsIgnoreCase("spectator") || mode.equalsIgnoreCase("sp") || mode.equals("3");

        if(survie) {
            player.setMode(GameMode.SURVIVAL);
            player.sendMessage(prefix + "Vous êtes maintenant en &csurvie &f!");

        } else if(creatif) {
            player.setMode(GameMode.CREATIVE);
            player.sendMessage(prefix + "Vous êtes maintenant en &acréatif &f!");

        } else if(aventure) {
            player.setMode(GameMode.ADVENTURE);
            player.sendMessage(prefix + "Vous êtes maintenant en &4aventure &f!");

        } else if(sp) {
            player.setMode(GameMode.SPECTATOR);
            player.sendMessage(prefix + "Vous êtes maintenant en &7spectateur &f!");

        } else
            player.sendMessage("&cErreur: Veuillez saisir un mode de jeu correct !");
    }

    private void Gamemode(Player target, String mode, String saisie_player) {
        String joueur = player.getName();

        if(target.isOnline()) {

            if(target.isSameAs(joueur))
                selfGamemode(mode);

            else {
                String cible = target.getName();
                survie = mode.equalsIgnoreCase("survival") || mode.equals("0");
                creatif = mode.equalsIgnoreCase("creative") || mode.equals("1");
                aventure = mode.equalsIgnoreCase("aventure") || mode.equals("2");
                sp = mode.equalsIgnoreCase("spectator") || mode.equalsIgnoreCase("sp") || mode.equals("3");

                if(survie) {
                    target.setMode(GameMode.SURVIVAL);
                    player.sendMessage(prefix + "Vous avez changé le mode de jeu de &b" + cible + " &fen &csurvie &f!");
                    target.sendMessage(prefix + "Votre mode de jeu a été changé en &csurvie par &b" + joueur + " &f!");

                } else if(creatif) {
                    target.setMode(GameMode.CREATIVE);
                    player.sendMessage(prefix + "Vous avez changé le mode de jeu de &b" + cible + " &fen &acréatif &f!");
                    target.sendMessage(prefix + "Votre mode de jeu a été changé en &acréatif par &b" + joueur + " &f!");

                } else if(aventure) {
                    target.setMode(GameMode.ADVENTURE);
                    player.sendMessage(prefix + "Vous avez changé le mode de jeu de &b" + cible + " &fen &4aventure &f!");
                    target.sendMessage(prefix + "Votre mode de jeu a été changé en &4aventure par &b" + joueur + " &f!");

                } else if(sp) {
                    target.setMode(GameMode.SPECTATOR);
                    player.sendMessage(prefix + "Vous avez changé le mode de jeu de &b" + cible + " &fen &7spectateur &f!");
                    target.sendMessage(prefix + "Votre mode de jeu a été changé en &7spectateur par &b" + joueur + " &f!");

                } else
                    player.sendMessage("&cErreur: Veuillez saisir un mode de jeu correct !");
            }
        }


        else
            player.sendMessage("&cErreur: Le joueur '" + saisie_player + "' n'est pas connecté !");
    }

    private void showHelp() {
        player.sendEmptyLine();
        HelpPageManager help = new HelpPageManager(player, prefix, 1, 1);
        help.addtoHelpPage(player, commande, "<mode>", "Changer son mode de jeu");
        help.addtoHelpPage(player, commande, "<joueur>", "Changer le mode de jeu d'un joueur");
        player.sendEmptyLine();
    }
}
