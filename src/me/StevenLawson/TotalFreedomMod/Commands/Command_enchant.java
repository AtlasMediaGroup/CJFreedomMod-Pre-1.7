package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Enchant items.", usage = "/<command> <list | addall | reset | add <name> | remove <name> | god <int>>", aliases = "ench")
public class Command_enchant extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        ItemStack itemInHand = sender_p.getItemInHand();

        if (itemInHand == null)
        {
            playerMsg("You are holding an invalid item.");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("custom") && TFM_SuperadminList.isSeniorAdmin(sender))
        {
            int level = 0;
            
            if (args.length == 3)
            {
                level = Integer.parseInt(args[2]);
            }
            
            else
            {
                level = 10;
            }
            
            Enchantment enchantment = Enchantment.getById(Integer.parseInt(args[1]));
            itemInHand.addUnsafeEnchantment(enchantment, level);
        }
        
        if (args[0].equalsIgnoreCase("god"))
        {
            if (!TFM_SuperadminList.isUserSuperadmin(sender))
            {
                playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            }
            
            else for (Enchantment ench : Enchantment.values())
            {
                int level;
                if (args.length == 2)
                {
                    level = Integer.parseInt(args[1]);
                }
                
                else
                {
                    level = 10;
                }
                if (!ench.equals(Enchantment.PROTECTION_FALL))
                {
                    itemInHand.addUnsafeEnchantment(ench, level);
                }
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("list"))
        {
            boolean has_enchantments = false;

            StringBuilder possible_ench = new StringBuilder("Possible enchantments for held item: ");
            for (Enchantment ench : Enchantment.values())
            {
                if (ench.canEnchantItem(itemInHand))
                {
                    has_enchantments = true;
                    possible_ench.append(ench.getName()).append(", ");
                }
            }

            if (has_enchantments)
            {
                playerMsg(possible_ench.toString());
            }
            else
            {
                playerMsg("The held item has no enchantments.");
            }
        }
        else if (args[0].equalsIgnoreCase("addall"))
        {
            for (Enchantment ench : Enchantment.values())
            {
                try
                {
                    if (ench.canEnchantItem(itemInHand))
                    {
                        itemInHand.addEnchantment(ench, ench.getMaxLevel());
                    }
                }
                catch (Exception ex)
                {
                    TFM_Log.info("Error using " + ench.getName() + " on " + itemInHand.getType().name() + " held by " + sender_p.getName() + ".");
                }
            }

            playerMsg("Added all possible enchantments for this item.");
        }
        else if (args[0].equalsIgnoreCase("reset"))
        {
            for (Enchantment ench : itemInHand.getEnchantments().keySet())
            {
                itemInHand.removeEnchantment(ench);
            }

            playerMsg("Removed all enchantments.");
        }
        else
        {
            if (args.length < 2)
            {
                return false;
            }

            Enchantment ench = null;

            try
            {
                ench = Enchantment.getByName(args[1]);
            }
            catch (Exception ex)
            {
            }

            if (ench == null)
            {
                playerMsg(args[1] + " is an invalid enchantment for the held item. Type \"/enchant list\" for valid enchantments for this item.");
                return true;
            }

            if (args[0].equalsIgnoreCase("add"))
            {
                if (ench.canEnchantItem(itemInHand))
                {
                    itemInHand.addEnchantment(ench, ench.getMaxLevel());

                    playerMsg("Added enchantment: " + ench.getName());
                }
                else
                {
                    playerMsg("Can't use this enchantment on held item.");
                }
            }
            else if (TFM_Util.isRemoveCommand(args[0]))
            {
                itemInHand.removeEnchantment(ench);

                playerMsg("Removed enchantment: " + ench.getName());
            }
        }

        return true;
    }
}
