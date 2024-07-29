package hitachi.barcow;

import hitachi.barcow.commands.BarCow;
import hitachi.barcow.events.ClickingOnBlockCow;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Plugin getVaules;

    @Override
    public void onEnable() {
        getVaules = this;
        saveDefaultConfig();
        getCommand("barcow").setExecutor(new BarCow());
        getServer().getPluginManager().registerEvents(new ClickingOnBlockCow(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
