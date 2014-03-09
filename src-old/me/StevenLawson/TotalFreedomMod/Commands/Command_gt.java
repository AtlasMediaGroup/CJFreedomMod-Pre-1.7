/* I'm commenting this out as it's bugged and doesn't work in game and I'll get round to fixing it anyways :P It's much better to have LESS little red exclamation marks everywhere :3

package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Send a player into the null void.", usage = "/<command> <player>")
public class Command_gt extends TFM_Command
{
	@Override
	public boolean run(CommandSender sender, Player sender_p, Command cmd, String lbl, String[] args, boolean senderIsConsole)
	{
	    if (args.length != 1)
	    {
		    return false;
	    }
		else
		{
			final Player p;
			try
			{
				p = getPlayer(args[0]);
			}
			catch (PlayerNotFoundException ex)
			{
				playerMsg(ex.getMessage(), ChatColor.RED);
				return true;
			}
		
			TFM_Util.adminAction(sender_p.getName(), "Sending " + p.getName() + " into the null void.", true);
			
			final Location l = p.getLocation();
			l.setY(120);
			p.teleport(l);
			p.setVelocity(new Vector(0, 10, 0));
			for (int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1;, z++)
				{
					Location strikePos = new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z));
					l.getWorld().strikeLightning(strikePos);
				}
			}
			
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					l.setY(0);
					p.teleport(l);
					p.setVelocity(new Vector(0, -10, 0));
				}
			}.runTaskLater(plugin, 40L);
			
		}
		
		return true;
	}
} */