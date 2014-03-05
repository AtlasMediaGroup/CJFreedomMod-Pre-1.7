package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Senior Command - A terrible command with horrific ideas.", usage = "/<command> <arg1> <partialname>", aliases = "hcs")
public class Command_impl extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String lbl, String[] args, boolean senderIsConsole)
    {
        if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("exterminate"))
            {
                final Player p;
                Server server = Bukkit.getServer();
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                TFM_Util.adminAction(sender.getName(), "Exterminating " + p.getName() + "...", true);
                final Location pos1 = p.getLocation();
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        for (int x = -1; x <= 1; x++)
                        {
                            for (int z = -1; z <= 1; z++)
                            {
                                final Location pos2 = new Location(pos1.getWorld(), pos1.getBlockX() + x, pos1.getBlockY(), pos1.getBlockZ() + z);
                                pos1.getWorld().strikeLightning(pos2);
                            }
                        }
                    }
                }.runTaskLater(plugin, 20L);
                
                p.getLocation().getWorld().createExplosion(p.getLocation(), 3);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        p.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getBlockX(), 0, p.getLocation().getBlockZ()));
                        p.setVelocity(new Vector(0, -10, 0));
                    }
                }.runTaskLater(plugin, 20L * 2L);
                
                TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
                
                playerdata.setCaged(true, pos1, Material.GLASS, Material.AIR);
            }
            
            else if (args[0].equalsIgnoreCase("jelly"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                final Location loc = p.getLocation();
                TFM_Util.bcastMsg("Hey " + p.getName() + ", what's the difference between jelly and jam?", ChatColor.RED);
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        Location strikePos = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
                        loc.getWorld().strikeLightning(strikePos);
                    }
                }
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        TFM_Util.bcastMsg("I can't jelly my banhammer up your ass.", ChatColor.RED);
                        loc.getWorld().createExplosion(loc, 3);
                        p.setHealth(0.0);
                        p.closeInventory();
                        p.getInventory().clear();
                        TFM_WorldEditBridge.getInstance().undo(p, 15);
                        TFM_RollbackManager.rollback(p.getName());
                    }
                }.runTaskLater(plugin, 60L);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        String userIP = p.getAddress().getAddress().getHostAddress();
                        String[] IPParts = userIP.split("\\.");
                        if (IPParts.length == 4)
                        {
                            userIP = String.format("%s.%s.*.*", IPParts[0], IPParts[1]);
                        }
                        TFM_Util.bcastMsg(String.format("%s - banning: %s, IP: %s.", sender.getName(), p.getName(), userIP), ChatColor.RED);
                        TFM_ServerInterface.banIP(userIP, null, null, null);
                        TFM_ServerInterface.banUsername(p.getName(), null, null, null);
                        p.kickPlayer(ChatColor.RED + "You couldn't handle the banhammer.");
                    }
                }.runTaskLater(plugin, 80L);
            }
            
            else if (args[0].equalsIgnoreCase("csg"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                TFM_Util.bcastMsg(p.getName() + " has been a naughty, naughty boy.");
                final Location l = p.getLocation();
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        Location strikePos = new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z);
                        l.getWorld().strikeLightning(strikePos);
                    }
                }
                
                TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
                playerdata.setCaged(true, l, Material.GLASS, Material.AIR);
                p.teleport(new Location(l.getWorld(), l.getBlockX(), 120, l.getBlockZ()));
                p.setVelocity(new Vector(0, 10, 0));
                p.teleport(new Location(l.getWorld(), l.getBlockX(), 0, l.getBlockZ()));
                p.setVelocity(new Vector(0, -10, 0));
                p.setHealth(0.0);
                
            }
            
            else if (args[0].equalsIgnoreCase("wtf"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                TFM_Util.bcastMsg(p.getName() + " is being a damn idjit.", ChatColor.RED);
                p.sendMessage(ChatColor.RED + "What the hell are you doing you damn idjit?");
                final Location l = p.getLocation();
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        Location strikePos = new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z);
                        l.getWorld().strikeLightning(strikePos);
                    }
                }
                p.setHealth(0.0);
            }
            
            else if (args[0].equalsIgnoreCase("fgt"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                TFM_Util.bcastMsg(p.getName() + " doesn't know when to stop.", ChatColor.RED);
                p.getInventory().clear();
                p.closeInventory();
                p.setHealth(0.0);
                Location l = p.getLocation();
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        Location strikePos = new Location(l.getWorld(), l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z);
                        l.getWorld().strikeLightning(strikePos);
                    }
                }
            }
            
            else if (args[0].equalsIgnoreCase("troll"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                final Location l = p.getLocation();
                final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        playerdata.setCaged(true, l, Material.GLASS, Material.AIR);
                    }
                }.runTaskLater(plugin, 20L);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        playerdata.setMuted(true);
                        playerdata.setCommandsBlocked(true);
                    }
                }.runTaskLater(plugin, 40L);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        playerdata.startOrbiting(10.0);
                        playerdata.setFrozen(true);
                    }
                }.runTaskLater(plugin, 60L);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        playerdata.setHalted(true);
                        p.setDisplayName("A_Naughty_Player");
                        cancelLockup(playerdata);
                        playerdata.setLockupScheduleID(new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                if (p.isOnline())
                                {
                                    p.openInventory(p.getInventory());
                                }
                                else
                                {
                                    cancelLockup(playerdata);
                                }
                            }
                        }.runTaskTimer(plugin, 0L, 5L));
                    }
                }.runTaskLater(plugin, 80L);
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        p.kickPlayer(ChatColor.RED + "GTFO MY FACE");
                    }
                }.runTaskLater(plugin, 100L);
            }
            
            else if (args[0].equalsIgnoreCase("drown"))
            {
                final Player p;
                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    playerMsg(ex.getMessage(), ChatColor.RED);
                    return true;
                }
                
                TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
                TFM_Util.adminAction(sender_p.getName(), "Drowning " + p.getName(), true);
                playerdata.setCommandsBlocked(true);
                p.setGameMode(GameMode.SURVIVAL);
                playerdata.setCaged(true, p.getLocation(), Material.GLASS, Material.WATER);
                playerdata.setFrozen(true);
                playerdata.setMuted(true);
            }
        }
        
        return true;
    }
    
    private void cancelLockup(TFM_PlayerData playerdata)
    {
        BukkitTask lockupScheduleID = playerdata.getLockupScheduleID();
        if (lockupScheduleID != null)
        {
            lockupScheduleID.cancel();
            playerdata.setLockupScheduleID(null);
        }
    }
}