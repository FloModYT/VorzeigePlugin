package ch.flomod.warpSystem.listeners;

import ch.flomod.warpSystem.inventorys.WarpInventory;
import ch.flomod.warpSystem.utils.FileConfig;
import ch.flomod.warpSystem.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();

        // Überprüfen, ob das aktuelle Item und das Inventory existieren
        if (clickedItem == null || clickedItem.getType() == Material.AIR || inv.getHolder() == null) {
            return;
        }

        // Überprüfen des InventoryHolder-Typs
        if (inv.getHolder() instanceof WarpInventory) {
            WarpInventory warpInv = (WarpInventory) inv.getHolder(); // Korrektes Casting hier
            int currentPage = warpInv.getPage();

            // Verarbeiten des geklickten Items
            if (clickedItem.getType() == Material.BLACK_STAINED_GLASS_PANE) {
                event.setCancelled(true);
            } else if (clickedItem.getType() == Material.FILLED_MAP) {
                String name = clickedItem.getItemMeta().getDisplayName();
                FileConfig warps = new FileConfig("locations.yml");
                Location location = LocationUtils.str2Loc(Objects.requireNonNull(warps.getString(name)));
                LocationUtils.teleport(player, location);
                event.setCancelled(true);
            } else if (clickedItem.getType() == Material.BARRIER) {
                player.closeInventory();
                event.setCancelled(true);
            } else if (clickedItem.getType() == Material.ARROW) {
                String displayName = clickedItem.getItemMeta().getDisplayName();
                if (displayName.contains("Nächste Seite") && currentPage < warpInv.getTotalPages()) {
                    int nextPage = currentPage + 1;
                    event.getWhoClicked().openInventory(WarpInventory.createInventory(nextPage));
                } else if (displayName.contains("Vorherige Seite") && currentPage > 1) {
                    int prevPage = currentPage - 1;
                    event.getWhoClicked().openInventory(WarpInventory.createInventory(prevPage));
                }
                event.setCancelled(true);
            }
        } else {
            // Debugging oder Logging hier hinzufügen
            System.out.println("Unexpected InventoryHolder type: " + inv.getHolder().getClass().getName());
        }
    }
}
