package levithean.mainplugin.commands;

import levithean.mainplugin.api.ChatManager;
import levithean.mainplugin.api.Player;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandInfo implements CommandExecutor {

    private Player player;
    private String commande;
    private final String prefix = ChatManager.getCommandsPrefix(3);
    private boolean fly_impossible;
    private GameMode gamemode;
    private World.Environment dimension;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        commande = label;

        if(sender instanceof org.bukkit.entity.Player) {
            player = new Player(sender);

            if(args.length == 0)
                selfInfos();

            else if(args.length == 1) {

                if(args[0].equalsIgnoreCase("help"))
                    showHelp();

                else {
                    Player target = new Player(args[0]);
                    Infos(target, args[0]);
                }

            } else
                showHelp();

        } else
            ChatManager.sendConsole(sender, prefix + "&cYou need to be a player to perform this command");

        return false;
    }

    private void selfInfos() {
        gamemode = player.getMode();
        fly_impossible = player.hasMode(GameMode.CREATIVE) || player.hasMode(GameMode.SPECTATOR);
        dimension = player.getDimension();
        String joueur = player.getName();

        player.sendEmptyLine();
        player.sendMessage(prefix + "Vos infos (&b" + joueur + "&f) :");
        player.sendEmptyLine();
        addInfo("IP", player.getIPAddress());

        if(player.isSameAs("levithean"))
            addInfo("Grade", "&4Admin");
        else
            addInfo("Grade", "&7Joueur");

        switch(gamemode) {

            case SURVIVAL:
                addInfo("Mode de jeu", "&cSurvie");
                break;

            case CREATIVE:
                addInfo("Mode de jeu", "&aCréatif");
                break;

            case ADVENTURE:
                addInfo("Mode de jeu", "&4Aventure");
                break;

            case SPECTATOR:
                addInfo("Mode de jeu", "&7Spectateur");
                break;
        }

        if(gamemode.equals(GameMode.SURVIVAL) || gamemode.equals(GameMode.ADVENTURE))
            addInfo("Vie", player.getHealth() + " &c❤");

        if(!fly_impossible) {

            if(player.hasFlyPerm())
                addInfo("Fly", "&aActivé");
            else
                addInfo("Fly", "&cDésactivé");

        }

        switch(dimension) {

            case NORMAL:
                addInfo("Monde", "&aNormal &7(Overworld)");
                break;

            case NETHER:
                addInfo("Monde", "&cNether");
                break;

            case THE_END:
                addInfo("Monde:", "&eEnd");
                break;
        }

        double x = player.getLocation("x");
        double y = player.getLocation("y");
        double z = player.getLocation("z");
        String position = convertPosition(x, y, z);

        addInfo("Position", position);
        player.sendEmptyLine();
    }

    private void Infos(Player target, String saisie_player) {
        String joueur = player.getName();

        if(target.isOnline()) {

            if(target.isSameAs(joueur))
                selfInfos();

            else {
                gamemode = target.getMode();
                fly_impossible = target.hasMode(GameMode.CREATIVE) || target.hasMode(GameMode.SPECTATOR);
                dimension = target.getDimension();
                String cible = target.getName();

                player.sendEmptyLine();
                player.sendMessage(prefix + "Infos sur &b" + cible + " &f:");
                player.sendEmptyLine();
                addInfo("IP", target.getIPAddress());

                if(target.isSameAs("levithean"))
                    addInfo("Grade", "&4Admin");
                else
                    addInfo("Grade", "&7Joueur");

                switch(gamemode) {

                    case SURVIVAL:
                        addInfo("Mode de jeu", "&cSurvie");
                        break;

                    case CREATIVE:
                        addInfo("Mode de jeu", "&aCréatif");
                        break;

                    case ADVENTURE:
                        addInfo("Mode de jeu", "&4Aventure");
                        break;

                    case SPECTATOR:
                        addInfo("Mode de jeu", "&7Spectateur");
                        break;
                }

                if(gamemode.equals(GameMode.SURVIVAL) || gamemode.equals(GameMode.ADVENTURE))
                    addInfo("Vie", target.getHealth() + " &c❤");

                if(!fly_impossible) {

                    if(target.hasFlyPerm())
                        addInfo("Fly", "&aActivé");
                    else
                        addInfo("Fly", "&cDésactivé");

                }

                switch(dimension) {

                    case NORMAL:
                        addInfo("Monde", "&aNormal &7(Overworld)");
                        break;

                    case NETHER:
                        addInfo("Monde", "&cNether");
                        break;

                    case THE_END:
                        addInfo("Monde:", "&eEnd");
                        break;
                }

                double x = target.getLocation("x");
                double y = target.getLocation("y");
                double z = target.getLocation("z");
                String position = convertPosition(x, y, z);

                addInfoPosition(target, position);
                player.sendEmptyLine();
            }

        } else
            player.sendMessage("&cErreur: Le joueur '" + saisie_player + "' n'est pas connecté !");
    }

    private void showHelp() {
        player.sendEmptyLine();
        ChatManager.newHelpPage(player, prefix, 1, 1);
        ChatManager.addtoHelpPage(player, commande, "Voir vos infos");
        ChatManager.addtoHelpPage(player, commande, "<pseudo>", "Voir les infos d'un joueur");
        player.sendEmptyLine();
    }

    private void addInfo(String nom, String info) {
        player.sendMessage("&6• &f" + nom + ": " + info);
    }

    private String convertPosition(double x, double y, double z) {

        int arrondi_x = (int)Math.round(x);
        int arrondi_y = (int)Math.round(y);
        int arrondi_z = (int)Math.round(z);

        return "&e[" + arrondi_x + ", " + arrondi_y + ", " + arrondi_z + "]";
    }

    private void addInfoPosition(Player target, String position) {

        if(!target.isSameAs(player.getName())) {
            TextComponent msg_position = new TextComponent();
            TextComponent msg_position2 = new TextComponent(position);
            ChatManager.setInteractiveMessage(msg_position, "&6• &fPosition: ");
            ChatManager.setInteractiveMessage(msg_position2, position);
            ChatManager.addInteractiveText(msg_position, msg_position2);

            ChatManager.setInteractiveCommand(msg_position2, "tp " + target.getName());
            ChatManager.setHoverInteractiveText(msg_position2, "Se téléporter à &b" + target.getName() + "\n&7&oCommande: ''/tp " + target.getName() + "''");

            player.sendInteractiveMessage(msg_position);

        } else
            addInfo("Position", position);
    }
}
