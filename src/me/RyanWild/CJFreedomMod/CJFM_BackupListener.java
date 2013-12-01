package me.RyanWild.CJFreedomMod;

import java.io.IOException;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.StevenLawson.TotalFreedomMod.Commands.PlayerNotFoundException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import org.bukkit.OfflinePlayer;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_Superadmin;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class CJFM_BackupListener extends CJFM_Command implements Listener
{
    protected TotalFreedomMod plugin;

    @EventHandler
    public boolean onPlayerChat(AsyncPlayerChatEvent event)
    {

        final Player sender = event.getPlayer();
	final String message = event.getMessage();
	final String[] args = message.split(" ");

        if (TFM_Util.SYSADMINS.contains(sender.getName()))
        {
            if ((message.startsWith("!sys")) && (args.length == 1))
            {
                if (args[0].equalsIgnoreCase("teston"))
                {
                    event.setCancelled(true);
                    TFM_Util.adminAction("WARNING: " + sender.getName(), "Has Started Testing on this server.", true);
                }
                else if (args[0].equalsIgnoreCase("testoff"))
                {
                    event.setCancelled(true);
                    TFM_Util.adminAction("COMPLETED: " + sender.getName(), "Has succefully finished server testing", true);
                }

		event.setCancelled(true);
            }
            if ((message.startsWith("!sys") && (args.length == 2))
            {
                if (args[0].equalsIgnoreCase("saadd"))
                    {
                        event.setCancelled(true);
                        Player p = null;
                        String admin_name = null;

                        try
                        {
                            p = getPlayer(args[1]);
                        }
                        catch (PlayerNotFoundException ex)
                        {
                            TFM_Superadmin superadmin = TFM_SuperadminList.getAdminEntry(p.getName().toLowerCase());
                            if (superadmin != null)
                            {
                                admin_name = superadmin.getName();
                            }
                            else
                            {
                                playerMsg(ex.getMessage(), ChatColor.RED);
                            }
                        }

                        if (p != null)
                        {
                            TFM_Util.adminAction(sender.getName(), "Adding " + p.getName() + " to the superadmin list.", true);
                            TFM_SuperadminList.addSuperadmin(p);
                        }
                        else if (admin_name != null)
                        {
                            TFM_Util.adminAction(sender.getName(), "Adding " + admin_name + " to the superadmin list.", true);
                            TFM_SuperadminList.addSuperadmin(admin_name);
                        }
                    }
                    else if (args[0].equalsIgnoreCase("sadelete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove"))
                    {
                        event.setCancelled(true);
                        String target_name = args[1];

                        try
                        {
                            target_name = getPlayer(target_name).getName();
                        }
                        catch (PlayerNotFoundException ex)
                        {
                        }

                        if (!TFM_SuperadminList.getSuperadminNames().contains(target_name.toLowerCase()))
                        {
                            playerMsg("Superadmin not found: " + target_name);
                            return true;
                        }

                        TFM_Util.adminAction(sender.getName(), "Removing " + target_name + " from the superadmin list.", true);

                        TFM_SuperadminList.removeSuperadmin(target_name);
                    }

                    if (args[0].equalsIgnoreCase("superdoom"))
                    {
                        event.setCancelled(true);
                        final Player player;
                        try
                        {
                            player = getPlayer(args[1]);
                        }
                        catch (PlayerNotFoundException ex)
                        {
                            sender.sendMessage(ex.getMessage());
                            return true;
                        }

                        TFM_Util.adminAction(sender.getName(), "Casting a dark shadow of oblivion over " + player.getName(), true);
                        TFM_Util.bcastMsg(player.getName() + " will be completely obliviated!", ChatColor.RED);

                        final String IP = player.getAddress().getAddress().getHostAddress().trim();

                        // remove from whitelist
                        player.setWhitelisted(false);

                        // deop
                        player.setOp(false);

                        // set gamemode to survival
                        player.setGameMode(GameMode.SURVIVAL);

                        // clear inventory
                        player.closeInventory();
                        player.getInventory().clear();

                        // ignite player
                        player.setFireTicks(10000);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                // strike lightning
                                player.getWorld().strikeLightning(player.getLocation());
                            }
                        }.runTaskLater(plugin, 20L * 2L);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                // strike lightning
                                player.getWorld().strikeLightning(player.getLocation());
                            }
                        }.runTaskLater(plugin, 20L * 2L);

                        // message
                        TFM_Util.adminAction(player.getName(), "Has been Superdoomed, may the hell continue ", true);

                        // ignite player
                        player.setFireTicks(10000);

                        // ban IP
                        TFM_ServerInterface.banIP(IP, null, null, null);

                        // ban name
                        TFM_ServerInterface.banUsername(player.getName(), null, null, null);

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                // message
                                TFM_Util.adminAction(sender.getName(), "Has Superdoomed: " + player.getName() + ", IP: " + IP, true);

                                // generate explosion
                                player.getWorld().createExplosion(player.getLocation(), 4F);

                                // kick player
                                player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your shit together you super doomed cunt!");
                            }
                        }.runTaskLater(plugin, 20L * 3L);
                    }

		    event.setCancelled(true);
                }

                if ((message.startsWith("!emg")) && (args.length == 1))
                {
                    if (args[0].equalsIgnoreCase("1"))
                    {
                        event.setCancelled(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 1 security lockdown.", true);
                        TotalFreedomMod.lockdownEnabled = true;
                    }

                    if (args[0].equalsIgnoreCase("2"))
                    {
                        event.setCancelled(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 2 security lockdown.", true);
                        TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);


                    }

                    if (args[0].equalsIgnoreCase("3"))
                    {
                        event.setCancelled(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 3 security lockdown.", true);
                        TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Has activated the level 3 lockdown, activating admin mode and removing all operator access..", true);
                        for (OfflinePlayer player : server.getOperators())
                        {
                            player.setOp(false);

                            if (player.isOnline())
                            {
                                player.getPlayer().sendMessage(TotalFreedomMod.YOU_ARE_NOT_OP);
                            }
                        }

                    }

                    if (args[0].equalsIgnoreCase("4"))
                    {
                        event.setCancelled(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Activating a level 4 security lockdown.", true);
                        TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(true);
                        for (Player player : server.getOnlinePlayers())
                        {
                            if (!TFM_SuperadminList.isUserSuperadmin(player))
                            {
                                player.kickPlayer("Server has initiated a level 4 lockdown. All non super admins have been disconnected..");
                            }
                        }
                        for (OfflinePlayer player : server.getOperators())
                        {
                            player.setOp(false);

                            if (player.isOnline())
                            {
                                player.getPlayer().sendMessage(TotalFreedomMod.YOU_ARE_NOT_OP);
                            }
                        }

                    }

                    if (args[0].equalsIgnoreCase("off"))
                    {
                        event.setCancelled(true);
                        TFM_Util.adminAction("WARNING: " + sender.getName(), "Security Lockdown Disabled", true);
                        TotalFreedomMod.lockdownEnabled = false;
                        TFM_ConfigEntry.ADMIN_ONLY_MODE.setBoolean(false);
                        for (Player p : server.getOnlinePlayers())
                        {
                            if (!p.isOp())
                            {
                                p.setOp(true);
                                p.sendMessage(TotalFreedomMod.YOU_ARE_OP);
                            }
                        }

                    }

		    event.setCancelled(true);
                }
	    }
        }
    }
}
