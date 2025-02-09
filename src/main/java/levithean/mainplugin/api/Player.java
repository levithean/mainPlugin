package levithean.mainplugin.api;

import levithean.mainplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;

import static levithean.mainplugin.api.ChatManager.setColor;

public class Player {

    private final org.bukkit.entity.Player player;

    public Player(String player) {
        this.player = Bukkit.getPlayer(player);
    }

    public Player(CommandSender sender) {
        this.player = (org.bukkit.entity.Player) sender;
    }

    public Player(PlayerJoinEvent e) {
        this.player = e.getPlayer();
    }

    public void sendMessage(String message) {
        player.sendMessage(setColor(message));
    }

    public void sendEmptyLine() {
        sendMessage("");
    }

    public void sendTitle(String title, String subtitle, int duree_apparition, int duree, int duree_disparition) {
        player.sendTitle(setColor(title), setColor(subtitle), 20 * duree_apparition, 20 * duree, 20 * duree_disparition);
    }

    public void sendTitle(String title, String subtitle) {
        sendTitle(title, subtitle, 20 * 3, 20 * 5, 20 * 3);
    }

    public void sendInteractiveMessage(InteractMessageManager message) {
        player.spigot().sendMessage(message.getMessage());
    }

    public void setMode(GameMode mode) {
        player.setGameMode(mode);
    }

    public void setFly(boolean fly) {
        player.setAllowFlight(fly);
    }

    public void setHealth(double vie) {
        player.setHealth(vie);
    }

    public void setFood(int food) {
        player.setFoodLevel(food);
    }

    public String getName() {
        return player.getName();
    }

    public String getDisplayName() {
        return player.getDisplayName();
    }

    public String getIPAddress() {
        return player.getAddress().getAddress().getHostAddress();
    }

    public double getLocation(String coordonnee) {

        if(coordonnee.equalsIgnoreCase("x"))
            return player.getLocation().getX();

        else if(coordonnee.equalsIgnoreCase("y"))
            return player.getLocation().getY();

        else if(coordonnee.equalsIgnoreCase("z"))
            return player.getLocation().getZ();

        return 0;
    }

    public GameMode getMode() {
        return player.getGameMode();
    }

    public World.Environment getDimension() {
        return player.getWorld().getEnvironment();
    }

    public int getHealth() {
        return (int)player.getHealth();
    }

    public int getFood() {
        return player.getFoodLevel();
    }

    public boolean isOnline() {
        return player != null && player.isOnline();
    }

    public boolean isSameAs(String nom) {
        return nom.equals(getName());
    }

    public boolean hasMaxHealth() {
        return player.getHealth() == 20;
    }

    public boolean hasMaxFood() {
        return player.getFoodLevel() == 20;
    }

    public boolean hasMode(GameMode mode) {
        return player.getGameMode() == mode;
    }

    public boolean hasFlyPerm() {
        return player.getAllowFlight();
    }

    public void setVanish(Main instance, boolean vanish) {

        for(org.bukkit.entity.Player players : Bukkit.getOnlinePlayers()) {

            if(vanish)
                players.hidePlayer(instance, player);
            else
                players.showPlayer(instance, player);
        }
    }

    public void teleportTo(Player target) {
        player.teleport(target.getPlayer());
    }

    private org.bukkit.entity.Player getPlayer() {
        return player;
    }
}
