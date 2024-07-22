package ch.flomod.warpSystem.inventorys;

import ch.flomod.warpSystem.WarpSystem;
import ch.flomod.warpSystem.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class WarpInventory implements InventoryHolder {

    private final Inventory inventory;
    private final int page;
    private final int totalPages;

    private static final int ITEMS_PER_PAGE = 14;

    public WarpInventory(int page) {
        this.page = page;

        FileConfig warpsConfig = new FileConfig("locations.yml");
        Set<String> warps = warpsConfig.getKeys(false);
        List<ItemStack> items = new ArrayList<>();

        for (String warp : warps) {
            ItemStack item = new ItemStack(Material.FILLED_MAP);
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setDisplayName(warp);
                item.setItemMeta(itemMeta);
                items.add(item);
            }
        }

        totalPages = (int) Math.ceil((double) items.size() / ITEMS_PER_PAGE);
        this.inventory = createPage(page, items);
    }

    private Inventory createPage(int page, List<ItemStack> items) {
        Inventory inv = Bukkit.createInventory(this, 9 * 5, ChatColor.GOLD + "WarpSystem - Seite " + page);

        // Definiere die spezifischen Slots, die du nutzen willst
        int[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

        Set<Integer> itemSlots = new HashSet<>();
        for (int slot : slots) {
            itemSlots.add(slot);
        }

        // Setze alle Plätze auf den Placeholder
        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderItemMeta = placeholder.getItemMeta();
        if (placeholderItemMeta != null) {
            placeholderItemMeta.setDisplayName("");
            placeholder.setItemMeta(placeholderItemMeta);
        }

        for (int i = 0; i < inv.getSize(); i++) {
            if (!itemSlots.contains(i)) {
                inv.setItem(i, placeholder);
            }
        }

        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, items.size());

        // Setze die Items in die definierten Slots
        for (int i = startIndex; i < endIndex; i++) {
            int slotIndex = slots[i - startIndex];
            inv.setItem(slotIndex, items.get(i));
        }

        // Füge Navigations-Items hinzu
        if (page > 1) {
            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemMeta prevPageMeta = prevPage.getItemMeta();
            if (prevPageMeta != null) {
                prevPageMeta.setDisplayName(ChatColor.GREEN + "Vorherige Seite");
                prevPage.setItemMeta(prevPageMeta);
                inv.setItem(36, prevPage); // Füge den "Previous Page"-Button hinzu
            }
        }

        if (page < totalPages) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            if (nextPageMeta != null) {
                nextPageMeta.setDisplayName(ChatColor.GREEN + "Nächste Seite");
                nextPage.setItemMeta(nextPageMeta);
                inv.setItem(44, nextPage); // Füge den "Next Page"-Button hinzu
            }
        }

        return inv;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public static Inventory createInventory(int page) {
        return new WarpInventory(page).getInventory();
    }
}
