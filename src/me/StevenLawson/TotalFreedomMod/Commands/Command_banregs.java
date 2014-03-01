//Made by Hawkeyeshi
package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows you the CJFreedom Admin Banning Regulations!", usage = "/<command>")
public class Command_banregs extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if(args.length != 0)
        {
            // playerMsg(ChatColor.RED + "Too many arguments! /<command>");
        }
        else
        {
            playerMsg(ChatColor.AQUA + "--- CJFreedom Banning Regulations ---");
            playerMsg(ChatColor.LIGHT_PURPLE + "A Player Is Griefing - Ban");
            playerMsg(ChatColor.LIGHT_PURPLE + "Large World-edits - If over 15,000 Then ban Else check that they are not griefing");
            playerMsg(ChatColor.LIGHT_PURPLE + "'Trolling' - /stfu and if they begin to grief then tempban");
            playerMsg(ChatColor.LIGHT_PURPLE + "Unwanted PvP - /smite");
            playerMsg(ChatColor.LIGHT_PURPLE + "Teleporting Other Players - Smite on first offense, Ban after that");
            playerMsg(ChatColor.LIGHT_PURPLE + "Using admin commands - Ignore It");
            playerMsg(ChatColor.LIGHT_PURPLE + "Spamming Chat - Tempban On first offense then /gtfo");
            playerMsg(ChatColor.LIGHT_PURPLE + "Ignoring Admin Requests - /smite");
            playerMsg(ChatColor.LIGHT_PURPLE + "Vulgar Language - If it's too vulgar, warn on first offense, smite on second.");
            playerMsg(ChatColor.LIGHT_PURPLE + "Sexual Language - Extremely sexual stuff needs a tempban of 5 minutes for first offense, gtfo a second.");
            playerMsg(ChatColor.LIGHT_PURPLE + "Harassing Other Players - /gtfo");
            playerMsg(ChatColor.LIGHT_PURPLE + "Advertising at least 1 or 2 times - /gtfo");
            playerMsg(ChatColor.BLUE + "This can be used if you are an OP planning on applying for Super Admin, or if you're an admin wanting to refresh on the Banning Regulations!");
            playerMsg(ChatColor.DARK_RED + "Note: These are directly copied from Wild1145's thread on the CJFreedom Forums!");
            
            
        }
        return false;
    }
}
