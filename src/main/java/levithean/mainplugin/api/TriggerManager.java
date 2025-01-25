package levithean.mainplugin.api;

import levithean.mainplugin.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TriggerManager {

    private final JavaPlugin main;

    public TriggerManager(JavaPlugin main) {
        this.main = main;
    }

    public void sendLog(String log) {
        main.getLogger().info(log);
    }

    public void newCommand(String commande, CommandExecutor classe) {
        main.getCommand(commande).setExecutor(classe);
    }

    public void newEvent(Listener listener, Main instance) {
        main.getServer().getPluginManager().registerEvents(listener, instance);
    }
}
