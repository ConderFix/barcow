package hitachi.barcow.commands;

import hitachi.barcow.HexUtil;
import hitachi.barcow.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BarCow implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("barcow.admin")) {
            commandSender.sendMessage("У вас нет прав для выдачи коровы.");
            return true;
        }
        if (strings[0].equals("give")) {
            String playerName = strings[1];
            Player player = Main.getVaules.getServer().getPlayer(playerName);
            if (player == null) {
                commandSender.sendMessage("Player offline");
            }
            Material materialConfig = Material.valueOf(Main.getVaules.getConfig().getString("item.material"));
            ItemStack itemStack = new ItemStack(materialConfig, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(HexUtil.color(Main.getVaules.getConfig().getString("item.name")));
            boolean checkGlow = Main.getVaules.getConfig().getBoolean("item.glow");
            if (checkGlow) {
                itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemMeta.getPersistentDataContainer().set(
                    NamespacedKey.fromString("barcow"),
                    PersistentDataType.STRING, "");
            List<String> lore = Main.getVaules.getConfig().getStringList("item.lore");
            List<String> translatedLore = new ArrayList<>();
            for (String line : lore) {
                String translatedLine = HexUtil.color(line);
                translatedLore.add(translatedLine);
            }
            itemMeta.setLore(translatedLore);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            commandSender.sendMessage("okey");

        }
        return true;
    }
}
