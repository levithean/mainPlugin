package levithean.mainplugin;

import levithean.mainplugin.api.TriggerManager;
import levithean.mainplugin.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private TriggerManager trigger;

    @Override
    public void onEnable() {
        trigger = new TriggerManager(this);
        trigger.sendLog("Plugin demarre");

        trigger.newCommand("fly", new CommandFly());
        trigger.newCommand("heal", new CommandHeal());
        trigger.newCommand("gamemode", new CommandGamemode());
        trigger.newCommand("info", new CommandInfo());
    }

    @Override
    public void onDisable() {
        trigger.sendLog("Plugin eteint");
    }
}
