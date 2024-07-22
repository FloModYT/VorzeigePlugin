package ch.flomod.warpSystem;

import ch.flomod.warpSystem.commands.SetWarpCommand;
import ch.flomod.warpSystem.commands.WarpCommand;
import ch.flomod.warpSystem.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarpSystem extends JavaPlugin {

    public static String PREFIX = "§a[§6WarpSystem§a]§r ";
    public static WarpSystem INSTANCE;

    public WarpSystem() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.registerCommands();
        this.registerListeners();

        log("WarpSystem enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void log(String text){
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    private void registerCommands() {
        Bukkit.getPluginCommand("setwarp").setExecutor(new SetWarpCommand());
        Bukkit.getPluginCommand("warp").setExecutor(new WarpCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
