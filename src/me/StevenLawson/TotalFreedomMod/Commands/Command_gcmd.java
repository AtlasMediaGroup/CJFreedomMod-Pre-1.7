package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Arrays;
import java.util.List;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Send a command as someone else.", usage = "/<command> <fromname> <outcommand>")
public class Command_gcmd extends TFM_Command
{
    private final List<String> nope = Arrays.asList("sys", "cookie", "donator", "dev", "cake", "unicorn", "potato", "pie", "adminworld", "donatorworld", "otoggle", "panel", "camzie");
    
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        String base_command = StringUtils.join(args, " ");
        
        for (String disallowed : nope)
        {
            if (base_command.contains(disallowed))
            {
                playerMsg("You used a forbidden command within your gcmd, try again, ommitting said command.", ChatColor.RED);
                return true;
            }
        }
        
        if (args.length < 2)
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
            sender.sendMessage(ex.getMessage());
            return true;
        }

        String outcommand = "";
        try
        {
            StringBuilder outcommand_bldr = new StringBuilder();
            for (int i = 1; i < args.length; i++)
            {
                outcommand_bldr.append(args[i]).append(" ");
            }
            outcommand = outcommand_bldr.toString().trim();
        }
        catch (Throwable ex)
        {
            sender.sendMessage(ChatColor.GRAY + "Error building command: " + ex.getMessage());
            return true;
        }

        try
        {
            playerMsg("Sending command as " + player.getName() + ": " + outcommand);
            if (server.dispatchCommand(player, outcommand))
            {
                playerMsg("Command sent.");
            }
            else
            {
                playerMsg("Unknown error sending command.");
            }
        }
        catch (Throwable ex)
        {
            playerMsg("Error sending command: " + ex.getMessage());
        }

        return true;
    }
}
