package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "This command shows you how to donate to the server.", usage = "/<command>")
public class Command_donatorinfo extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        playerMsg(ChatColor.AQUA + "This message has been wrote on the 30/08./13, things may change");
        playerMsg(ChatColor.BLUE + "Donator ($5) or Senior Donator ($10)");
        playerMsg(ChatColor.GREEN + "Commands:");
        playerMsg(ChatColor.YELLOW + "/cake, /cookie, /expel, /ender /donatorworld");
        playerMsg(ChatColor.GOLD + "Donator Link: http://cjfreedom.community-piston.net/");
        playerMsg(ChatColor.RED + "Things to buy if you are already a donor:");
        playerMsg(ChatColor.BLACK + "Custom Prefix($6.50), Custom Title ($1)");
        return true;

    }
}
