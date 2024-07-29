package hitachi.barcow.events;

import hitachi.barcow.HexUtil;
import hitachi.barcow.Main;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ClickingOnBlockCow implements Listener {

    public boolean ifNamespacedkey(String name, ItemStack item) {
        if (item != null) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null && itemMeta.getPersistentDataContainer().has(
                    Objects.requireNonNull(NamespacedKey.fromString(name)),
                    PersistentDataType.STRING)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onClickingOnBlock(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (ifNamespacedkey("barcow", item)) {
                event.setCancelled(true);
                final int amountItem = player.getInventory().getItemInMainHand().getAmount();
                player.getInventory().getItemInMainHand().setAmount(amountItem - 1);

                final Location location = Objects.requireNonNull(event.getClickedBlock().getLocation());
                final MushroomCow mob = location.getWorld().spawn(location.add(0.5D, 1.0D, 0.5D), MushroomCow.class);
                final TNTPrimed tnt = location.getWorld().spawn(location.add(0.5D, 1.0D, 0.5D), TNTPrimed.class);
                final int fuseTime = Main.getVaules.getConfig().getInt("item.timeExplosion");
                // settings tnt (start)
                tnt.setFuseTicks(20 * fuseTime);
                tnt.setCustomNameVisible(true);
                // settings tnt (end)

                (new BukkitRunnable() {
                    int remaining = fuseTime;
                    public void run() {
                        String hologram = HexUtil.color(Main.getVaules.getConfig().getString("messages.toExplosion"));
                        tnt.setCustomName(hologram.replace("{time}", String.valueOf(this.remaining)));
                        --this.remaining;
                        if (this.remaining == -1) {
                            //-
                        }
                    }
                }).runTaskTimer(Main.getVaules, 0L, 20L);

                // settings mob (start)
                mob.addPassenger(tnt);
                // settings mob (end)

            }
        }

    }
}
