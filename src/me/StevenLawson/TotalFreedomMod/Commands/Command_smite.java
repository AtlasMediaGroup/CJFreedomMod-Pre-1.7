package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Someone being a little bitch? Smite them down...", usage = "/<command> <playername> [reason]")
public class Command_smite extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        String smite_reason = null;
        
        if (args.length < 1)
        {
            return false;
        }
        
        if (args.length >= 2)
        {
            smite_reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        }
        
        if (args.length == 1)
        {
            smite_reason = "NO_REASON_SPECIFIED";
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


        smite(player, smite_reason, sender.getName());

        return true;
    }

    public static void smite(final Player player, final String smite_reason, final String sender)
    {
        TFM_Util.bcastMsg(player.getName() + " has been a naughty, naughty boy!", ChatColor.RED);
        if (smite_reason != "NO_REASON_SPECIFIED")
        {
            TFM_Util.bcastMsg("They have been smitten for: " + smite_reason + " by " + sender, ChatColor.RED);
        }

        //Deop
        player.setOp(false);

        //Set gamemode to survival:
        player.setGameMode(GameMode.SURVIVAL);

        //Clear inventory:
        player.getInventory().clear();
        
        //Kill:
        player.setHealth(0.0);

        //Strike with lightning effect:
        final Location targetPos = player.getLocation();
        final World world = player.getWorld();
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(world, targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                world.strikeLightning(strike_pos);
            }
        }
    }
}
