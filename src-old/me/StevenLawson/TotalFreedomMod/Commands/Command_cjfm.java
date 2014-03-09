package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows information about TotalFreedomMod", usage = "/<command>")
public class Command_cjfm extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        
        playerMsg(String.format("Version " + ChatColor.BLUE + "%s.%s" + ChatColor.BLUE + ", built %s.\n"
            + ChatColor.GOLD + "Created by Madgeek1450 and DarthSalamon, later worked on by Wild1145 for CJFreedom.\n"
            + ChatColor.GREEN + "Visit " + ChatColor.AQUA + "http://www.thecjgcjg.com" + ChatColor.GREEN + " for more information.", TotalFreedomMod.pluginVersion, TotalFreedomMod.buildNumber, TotalFreedomMod.buildDate));

        return true;
    }
}
