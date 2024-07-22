package ch.flomod.warpSystem.commands;

import ch.flomod.warpSystem.WarpSystem;
import ch.flomod.warpSystem.inventorys.WarpInventory;
import ch.flomod.warpSystem.utils.FileConfig;
import ch.flomod.warpSystem.utils.LocationUtils;
import org.bukkit.Sound;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            WarpSystem.INSTANCE.log("Du bist kein Spieler.");
            return true;
        }

        Player player = (Player) sender;

        if(!(player.hasPermission("ch.flomod.warpsystem.warp"))) {
            player.sendMessage(WarpSystem.PREFIX + "Du darfst das nicht!");
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 0.2f, 1.2f);
            return true;
        }

        player.openInventory(new WarpInventory(1).getInventory());

        return true;
    }
}
