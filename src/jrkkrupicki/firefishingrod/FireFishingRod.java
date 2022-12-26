package jrkkrupicki.firefishingrod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FireFishingRod extends JavaPlugin implements Listener {

    private ItemStack fireFishingRod;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        fireFishingRod = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = fireFishingRod.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Fire Fishing Rod");
        meta.setLore(List.of(ChatColor.GRAY + "No more raw fishes"));
        fireFishingRod.setItemMeta(meta);
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(this, "fire_fishing_rod"), fireFishingRod);
        recipe.addIngredient(Material.FISHING_ROD);
        recipe.addIngredient(Material.FLINT_AND_STEEL);
        Bukkit.addRecipe(recipe);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(!itemInHand.isSimilar(fireFishingRod)) return;
        Entity caught = event.getCaught();
        if(caught == null) return;
        if(!(caught instanceof Item)) return;
        ItemStack item = ((Item)caught).getItemStack();
        if(item.getType().equals(Material.COD)) item.setType(Material.COOKED_COD);
        else if(item.getType().equals(Material.SALMON)) item.setType(Material.COOKED_SALMON);
    }
}
