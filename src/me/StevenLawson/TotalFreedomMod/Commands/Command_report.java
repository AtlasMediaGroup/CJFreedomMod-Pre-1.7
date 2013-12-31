package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Reports a player for a offence for admins to review.", usage = "/<command> <partialname> <reason>")
public class Command_report extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        Player player;
        try
        {
            player = getPlayer(args[0]);
        }
        catch (PlayerNotFoundException ex)
        {
            playerMsg(ex.getMessage(), ChatColor.RED);
            return true;
        }


        String ban_reason = null;
        if (args.length <= 2)
        {
            return false;
        }
        else if (args.length >= 2)
        {
            ban_reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        }


        for (Player admins : Bukkit.getOnlinePlayers())
        {
            if (TFM_SuperadminList.isUserSuperadmin(admins))
            {
                admins.sendMessage(TFM_Util.colorize("&8[&4CJFreedomMod System&8] &a" + sender.getName() + " &4has reported &a" + player.getName() + " &4 for &2" + ban_reason + "&4."));

            }
        }
        player.sendMessage(TFM_Util.colorize("&8[&4CJFreedomMod System&8] &4Please note that you have been reported for &2" + ban_reason + " &4and that a admin will be reviewing this shortly ."));

        sender.sendMessage(TFM_Util.colorize("&8[&4CJFreedomMod System&8] &4Your report against &a " + player.getName() + " &4for &2" + ban_reason + " &4has been recieved and a admin will be reviewing it shortly ."));

        return true;
    }
}
