package ch.flomod.warpSystem.commands;

import ch.flomod.warpSystem.WarpSystem;
import ch.flomod.warpSystem.utils.FileConfig;
import ch.flomod.warpSystem.utils.LocationUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            WarpSystem.INSTANCE.log("Du bist kein Spieler.");
            return true;
        }

        Player player = (Player) sender;

        FileConfig warps =  new FileConfig("locations.yml");

        if(!(player.hasPermission("ch.flomod.warpsystem.setwarp"))) {
            player.sendMessage(WarpSystem.PREFIX + "Du darfst das nicht!");
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 0.2f, 1.2f);
            return true;
        }

        if (args == null || args.length == 0) {
            player.sendMessage(WarpSystem.PREFIX + "Du musst den Namen für den neuen Warp eingeben.");
            return true;
        }

        String warpname = args[0];

        WarpSystem.INSTANCE.log(warpname);
        warps.set(warpname, LocationUtils.loc2Str(player.getLocation()));
        warps.saveConfig();
        player.sendMessage(WarpSystem.PREFIX + "'"+ warpname + "' wurde erfolgreich gesetzt.");


        return true;
    }
}
