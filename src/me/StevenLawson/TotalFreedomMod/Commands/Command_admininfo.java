package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows you how to become a admin.", usage = "/<command>")
public class Command_admininfo extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if(args.length != 0)
        {
            return false;
        }
        else
        {
            playerMsg(ChatColor.AQUA + "The following is accurate as of 25/01/2014");
            playerMsg(ChatColor.AQUA + "To apply for admin you need to go to the forums at http://www.thecjgcjg.com/forum");
            playerMsg(ChatColor.AQUA + "Then read the requirements.");
            playerMsg(ChatColor.AQUA + "Then if you feel you are ready, make a new thread in the Admin Aplications board.");
            playerMsg(ChatColor.AQUA + "And fill out the template in the NEW thread.");
            playerMsg(ChatColor.RED + "We ask for you not to ask existing admins for recommendations, this will get your application denied.");
            playerMsg(ChatColor.RED + "DO NOT Lie on your application, this is a way just to get it rejected!");
            playerMsg(ChatColor.AQUA + "Good Luck!");
            return true;
        }
    }
}
