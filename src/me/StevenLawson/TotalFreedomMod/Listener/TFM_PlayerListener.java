package me.StevenLawson.TotalFreedomMod.Listener;

import me.RyanWild.CJFreedomMod.CJFM_DonatorWorld;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import me.StevenLawson.TotalFreedomMod.*;
import me.StevenLawson.TotalFreedomMod.Commands.Command_doomhammer;
import me.StevenLawson.TotalFreedomMod.Commands.Command_landmine;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager.RollbackEntry;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Superadmin;
import static me.StevenLawson.TotalFreedomMod.TFM_Util.playerMsg;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;


public class TFM_PlayerListener implements Listener
{
    public String name;
    private static final List<String> BLOCKED_MUTED_CMDS = Arrays.asList(StringUtils.split("say,me,msg,m,tell,r,reply,mail,email", ","));
    private static final int MSG_PER_HEARTBEAT = 10;
    private static final List<String> adminCommands = Arrays.asList(StringUtils.split("gtfo,ban,kick,smite,tban,noob,orbit,doom,saconfig,stfu", ","));
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();
        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        switch (event.getAction())
        {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
            {
                switch (event.getMaterial())
                {
                    case WATER_BUCKET:
                    {
                        if (TFM_ConfigEntry.ALLOW_WATER_PLACE.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Water buckets are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }

                    case LAVA_BUCKET:
                    {
                        if (TFM_ConfigEntry.ALLOW_LAVA_PLACE.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.COOKIE, 1));
                        player.sendMessage(ChatColor.GRAY + "Lava buckets are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }

                    case EXPLOSIVE_MINECART:
                    {
                        if (TFM_ConfigEntry.ALLOW_TNT_MINECARTS.getBoolean())
                        {
                            break;
                        }

                        player.getInventory().clear(player.getInventory().getHeldItemSlot());
                        player.sendMessage(ChatColor.GRAY + "TNT minecarts are currently disabled.");
                        event.setCancelled(true);
                        break;
                    }
                }
                break;
            }

            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            {
                switch (event.getMaterial())
                {
                    case STICK:
                    {
                        if (!TFM_SuperadminList.isUserSuperadmin(player))
                        {
                            break;
                        }

                        event.setCancelled(true);

                        final Location location = player.getTargetBlock(null, 5).getLocation();
                        final List<RollbackEntry> entries = TFM_RollbackManager.getEntriesAtLocation(location);

                        if (entries.isEmpty())
                        {
                            TFM_Util.playerMsg(player, "No block edits at that location.");
                            break;
                        }

                        TFM_Util.playerMsg(player, "Block edits at (" + ChatColor.WHITE + "x" + location.getBlockX() + ", y" + location.getBlockY() + ", z" + location.getBlockZ() + ChatColor.BLUE + ")" + ChatColor.WHITE + ":", ChatColor.BLUE);
                        for (RollbackEntry entry : entries)
                        {
                            TFM_Util.playerMsg(player, " - " + ChatColor.BLUE + entry.author + " " + entry.getType() + " " + StringUtils.capitalize(entry.getMaterial().toString().toLowerCase()) + (entry.data == 0 ? "" : ":" + entry.data));
                        }

                        break;
                    }

                    case BONE:
                    {
                        if (!playerdata.mobThrowerEnabled())
                        {
                            break;
                        }

                        Location player_pos = player.getLocation();
                        Vector direction = player_pos.getDirection().normalize();

                        LivingEntity rezzed_mob = (LivingEntity) player.getWorld().spawnEntity(player_pos.add(direction.multiply(2.0)), playerdata.mobThrowerCreature());
                        rezzed_mob.setVelocity(direction.multiply(playerdata.mobThrowerSpeed()));
                        playerdata.enqueueMob(rezzed_mob);

                        event.setCancelled(true);
                        break;
                    }

                    case SULPHUR:
                    {
                        if (!playerdata.isMP44Armed())
                        {
                            break;
                        }

                        event.setCancelled(true);

                        if (playerdata.toggleMP44Firing())
                        {
                            playerdata.startArrowShooter(TotalFreedomMod.plugin);
                        }
                        else
                        {
                            playerdata.stopArrowShooter();
                        }
                        break;
                    }

                    case BLAZE_ROD:
                    {
                        if (!TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
                        {
                            break;
                        }

                        if (!TFM_SuperadminList.isSeniorAdmin(player, true))
                        {
                            break;
                        }

                        event.setCancelled(true);
                        Block targetBlock;

                        if (event.getAction().equals(Action.LEFT_CLICK_AIR))
                        {
                            targetBlock = player.getTargetBlock(null, 120);
                        }
                        else
                        {
                            targetBlock = event.getClickedBlock();
                        }

                        if (targetBlock == null)
                        {
                            player.sendMessage("Can't resolve target block.");
                            break;
                        }

                        player.getWorld().createExplosion(targetBlock.getLocation(), 4F, true);
                        player.getWorld().strikeLightning(targetBlock.getLocation());

                        break;
                    }

                    case CARROT:
                    {
                        if (!TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean())
                        {
                            break;
                        }

                        if (!TFM_SuperadminList.isSeniorAdmin(player, true))
                        {
                            break;
                        }

                        Location location = player.getLocation().clone();

                        Vector playerPostion = location.toVector().add(new Vector(0.0, 1.65, 0.0));
                        Vector playerDirection = location.getDirection().normalize();

                        double distance = 150.0;
                        Block targetBlock = player.getTargetBlock(null, Math.round((float) distance));
                        if (targetBlock != null)
                        {
                            distance = location.distance(targetBlock.getLocation());
                        }

                        final List<Block> affected = new ArrayList<Block>();

                        Block lastBlock = null;
                        for (double offset = 0.0; offset <= distance; offset += (distance / 25.0))
                        {
                            Block block = playerPostion.clone().add(playerDirection.clone().multiply(offset)).toLocation(player.getWorld()).getBlock();

                            if (!block.equals(lastBlock))
                            {
                                if (block.isEmpty())
                                {
                                    affected.add(block);
                                    block.setType(Material.TNT);
                                }
                                else
                                {
                                    break;
                                }
                            }

                            lastBlock = block;
                        }

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                for (Block tntBlock : affected)
                                {
                                    TNTPrimed tnt = tntBlock.getWorld().spawn(tntBlock.getLocation(), TNTPrimed.class);
                                    tnt.setFuseTicks(5);
                                    tntBlock.setType(Material.AIR);
                                }
                            }
                        }.runTaskLater(TotalFreedomMod.plugin, 30L);

                        event.setCancelled(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        TFM_AdminWorld.getInstance().validateMovement(event);
        CJFM_DonatorWorld.getInstance().validateMovement(event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void chatBotEvent(AsyncPlayerChatEvent event)
    {
        //to add later
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onAttack(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof Player)
        {
            Player attacker = (Player)event.getDamager();
            
            //define a doomhammer
            ItemStack dhammer = new ItemStack(Material.DIAMOND_AXE, 1);
            ItemMeta dhammermeta = dhammer.getItemMeta();
            List<String> lores = new ArrayList<String>();
            lores.add(ChatColor.RED + "The all powerful Doom Hammer!");
            dhammermeta.setDisplayName(ChatColor.DARK_RED + "Doom Hammer");
            dhammermeta.setLore(lores);
            dhammer.setItemMeta(dhammermeta);
        
            if(event.getEntity() instanceof Player && TFM_ConfigEntry.DHAMMER_MODE.getBoolean() == true)
            {
                if (TFM_SuperadminList.isSeniorAdmin((CommandSender) attacker))
                {  
                Player player = (Player) event.getEntity();
                Player sender = (Player) event.getDamager();
                Command_doomhammer.doom(player, sender);
                }
            }
            if(event.getEntity() instanceof Player && attacker.getGameMode() == GameMode.CREATIVE)
            {
                event.setCancelled(true);
                playerMsg(attacker, "No GM PVP!", ChatColor.RED);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        try
        {
            if (from.getWorld() == to.getWorld() && from.distanceSquared(to) < (0.0001 * 0.0001))
            {
                // If player just rotated, but didn't move, don't process this event.
                return;
            }
        }
        catch (IllegalArgumentException ex)
        {
        }

        if (!TFM_AdminWorld.getInstance().validateMovement(event))
        {
            return;
        }

        if (!CJFM_DonatorWorld.getInstance().validateMovement(event))
        {
            return;
        }

        Player player = event.getPlayer();
        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        for (Entry<Player, Double> fuckoff : TotalFreedomMod.fuckoffEnabledFor.entrySet())
        {
            Player fuckoffPlayer = fuckoff.getKey();

            if (fuckoffPlayer.equals(player) || !fuckoffPlayer.isOnline())
            {
                continue;
            }

            double fuckoffRange = fuckoff.getValue().doubleValue();

            Location playerLocation = player.getLocation();
            Location fuckoffLocation = fuckoffPlayer.getLocation();

            double distanceSquared;
            try
            {
                distanceSquared = playerLocation.distanceSquared(fuckoffLocation);
            }
            catch (IllegalArgumentException ex)
            {
                continue;
            }

            if (distanceSquared < (fuckoffRange * fuckoffRange))
            {
                event.setTo(fuckoffLocation.clone().add(playerLocation.subtract(fuckoffLocation).toVector().normalize().multiply(fuckoffRange * 1.1)));
                break;
            }
        }

        boolean freeze = false;
        if (TotalFreedomMod.allPlayersFrozen)
        {
            if (!TFM_SuperadminList.isUserSuperadmin(player))
            {
                freeze = true;
            }
        }
        else
        {
            if (playerdata.isFrozen())
            {
                freeze = true;
            }
        }

        if (freeze)
        {
            Location freezeTo = to.clone();

            freezeTo.setX(from.getX());
            freezeTo.setY(from.getY());
            freezeTo.setZ(from.getZ());

            event.setTo(freezeTo);
        }

        if (playerdata.isCaged())
        {
            Location targetPos = player.getLocation().add(0, 1, 0);

            boolean outOfCage;
            if (!targetPos.getWorld().equals(playerdata.getCagePos().getWorld()))
            {
                outOfCage = true;
            }
            else
            {
                outOfCage = targetPos.distanceSquared(playerdata.getCagePos()) > (2.5 * 2.5);
            }

            if (outOfCage)
            {
                playerdata.setCaged(true, targetPos, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.OUTER), playerdata.getCageMaterial(TFM_PlayerData.CageLayer.INNER));
                playerdata.regenerateHistory();
                playerdata.clearHistory();
                TFM_Util.buildHistory(targetPos, 2, playerdata);
                TFM_Util.generateHollowCube(targetPos, 2, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.OUTER));
                TFM_Util.generateCube(targetPos, 1, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.INNER));
            }
        }

        if (playerdata.isOrbiting())
        {
            if (player.getVelocity().length() < playerdata.orbitStrength() * (2.0 / 3.0))
            {
                player.setVelocity(new Vector(0, playerdata.orbitStrength(), 0));
            }
        }

        if (TFM_Jumppads.getInstance().getMode().isOn())
        {
            TFM_Jumppads.getInstance().PlayerMoveEvent(event);
        }

        if (!(TFM_ConfigEntry.LANDMINES_ENABLED.getBoolean() && TFM_ConfigEntry.ALLOW_EXPLOSIONS.getBoolean()))
        {
            return;
        }

        Iterator<Command_landmine.TFM_LandmineData> landmines = Command_landmine.TFM_LandmineData.landmines.iterator();
        while (landmines.hasNext())
        {
            Command_landmine.TFM_LandmineData landmine = landmines.next();

            Location location = landmine.location;
            if (location.getBlock().getType() != Material.TNT)
            {
                landmines.remove();
                continue;
            }

            if (landmine.player.equals(player))
            {
                break;
            }

            if (!player.getWorld().equals(location.getWorld()))
            {
                break;
            }

            if (!(player.getLocation().distanceSquared(location) <= (landmine.radius * landmine.radius)))
            {
                break;
            }

            landmine.location.getBlock().setType(Material.AIR);

            TNTPrimed tnt1 = location.getWorld().spawn(location, TNTPrimed.class);
            tnt1.setFuseTicks(40);
            tnt1.setPassenger(player);
            tnt1.setVelocity(new Vector(0.0, 2.0, 0.0));

            TNTPrimed tnt2 = location.getWorld().spawn(player.getLocation(), TNTPrimed.class);
            tnt2.setFuseTicks(1);

            player.setGameMode(GameMode.SURVIVAL);
            landmines.remove();
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeavesDecay(LeavesDecayEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        try
        {
            final Player player = event.getPlayer();
            String message = event.getMessage().trim();

            TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

            // Check for spam
            Long lastRan = TFM_Heartbeat.getLastRan();
            if (lastRan == null || lastRan + TotalFreedomMod.HEARTBEAT_RATE * 1000L < System.currentTimeMillis())
            {
                //TFM_Log.warning("Heartbeat service timeout - can't check block place/break rates.");
            }
            else
            {
                if (playerdata.incrementAndGetMsgCount() > MSG_PER_HEARTBEAT)
                {
                    TFM_Util.bcastMsg(player.getName() + " was automatically kicked for spamming chat.", ChatColor.RED);
                    TFM_Util.autoEject(player, "Kicked for spamming chat.");

                    playerdata.resetMsgCount();

                    event.setCancelled(true);
                    return;
                }
            }

            // Check for message repeat
            if (playerdata.getLastMessage().equalsIgnoreCase(message) && !TFM_SuperadminList.isUserSuperadmin(player))
            {
                TFM_Util.playerMsg(player, "Please do not repeat messages.");
                event.setCancelled(true);
                return;
            }

            playerdata.setLastMessage(message);

            // Check for muted
            if (playerdata.isMuted())
            {
                if (!TFM_SuperadminList.isUserSuperadmin(player))
                {
                    player.sendMessage(ChatColor.RED + "You are muted, STFU!");
                    event.setCancelled(true);
                    return;
                }

                playerdata.setMuted(false);
            }

            // Strip color from messages
            Pattern p = Pattern.compile(" (?i) " + String.valueOf('\u00A7') + "[mnko]");
            message = p.matcher(message).replaceAll("");

            // Truncate messages that are too long - 100 characters is vanilla client max
            if (message.length() > 100)
            {
                message = message.substring(0, 100);
                TFM_Util.playerMsg(player, "Message was shortened because it was too long to send.");
            }

            // Check for caps
            if (message.length() >= 6 && !TFM_SuperadminList.isUserSuperadmin(player))
            {
                int caps = 0;
                for (char c : message.toCharArray())
                {
                    if (Character.isUpperCase(c))
                    {
                        caps++;
                    }
                }
                if (((float) caps / (float) message.length()) > 0.65) //Compute a ratio so that longer sentences can have more caps.
                {
                    message = message.toLowerCase();
                }
            }

            // Check for adminchat
            if (playerdata.inAdminChat())
            {
                TFM_Util.adminChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }

            // Check for donatorchat
            if (playerdata.inDonatorChat())
            {
                CJFM_Util.donatorChatMessage(player, message, false);
                event.setCancelled(true);
                return;
            }

            // Finally, set message
            event.setMessage(message);

            // Set the tag
            if (playerdata.getTag() != null)
            {
                player.setDisplayName((playerdata.getTag() + " " + player.getDisplayName().replaceAll(" ", "")));
            }

        }
        catch (Exception ex)
        {
            TFM_Log.severe(ex);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        String command = event.getMessage();
        Player player = event.getPlayer();

        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        playerdata.setLastCommand(command);

        if (playerdata.incrementAndGetMsgCount() > MSG_PER_HEARTBEAT)
        {
            TFM_Util.bcastMsg(player.getName() + " was automatically kicked for spamming commands.", ChatColor.RED);
            TFM_Util.autoEject(player, "Kicked for spamming commands.");

            playerdata.resetMsgCount();

            TFM_Util.TFM_EntityWiper.wipeEntities(true, true);

            event.setCancelled(true);
            return;
        }

        if (playerdata.allCommandsBlocked())
        {
            TFM_Util.playerMsg(player, "Your commands have been blocked by an admin.", ChatColor.RED);
            event.setCancelled(true);
            return;
        }

        // Block commands if player is muted
        if (playerdata.isMuted())
        {
            if (!TFM_SuperadminList.isUserSuperadmin(player))
            {
                for (String commandName : BLOCKED_MUTED_CMDS)
                {
                    if (Pattern.compile("^/" + commandName.toLowerCase() + " ").matcher(command).find())
                    {
                        player.sendMessage(ChatColor.RED + "That command is blocked while you are muted.");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            else
            {
                playerdata.setMuted(false);
            }
        }

        if (TFM_ConfigEntry.PREPROCESS_LOG_ENABLED.getBoolean())
        {
            TFM_Log.info(String.format("[PREPROCESS_COMMAND] %s(%s): %s", player.getName(), ChatColor.stripColor(player.getDisplayName()), command), true);
        }

        command = command.toLowerCase().trim();

        // Blocked commands
        if (TFM_CommandBlocker.getInstance().isCommandBlocked(command, event.getPlayer()))
        {
            // CommandBlocker handles messages and broadcasts
            event.setCancelled(true);
        }

        if (!TFM_SuperadminList.isUserSuperadmin(player))
        {
            for (Player pl : Bukkit.getOnlinePlayers())
            {
                if (TFM_SuperadminList.isUserSuperadmin(pl) && TFM_PlayerData.getPlayerData(pl).cmdspyEnabled())
                {
                    TFM_Util.playerMsg(pl, player.getName() + ": " + command);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        if (TFM_ConfigEntry.AUTO_ENTITY_WIPE.getBoolean())
        {
            if (event.getPlayer().getWorld().getEntities().size() > 750)
            {
                event.setCancelled(true);
            }
            else
            {
                event.getItemDrop().remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent event)
    {
        Player player = event.getPlayer();
        if (TotalFreedomMod.fuckoffEnabledFor.containsKey(player))
        {
            TotalFreedomMod.fuckoffEnabledFor.remove(player);
        }
        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        playerdata.disarmMP44();
        if (playerdata.isCaged())
        {
            playerdata.regenerateHistory();
            playerdata.clearHistory();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if (TotalFreedomMod.fuckoffEnabledFor.containsKey(player))
        {
            TotalFreedomMod.fuckoffEnabledFor.remove(player);
        }
        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
        playerdata.disarmMP44();
        if (playerdata.isCaged())
        {
            playerdata.regenerateHistory();
            playerdata.clearHistory();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();

        final TFM_Superadmin entry = TFM_SuperadminList.getAdminEntry(player.getName());
        if (entry != null && !entry.isSeniorAdmin() && entry.isTelnetAdmin())
        {

            player.setPlayerListName(ChatColor.GREEN + player.getName());

            /*   if (player.getName().length() < 11)
             {
             player.setPlayerListName(ChatColor.GREEN + "[STA] " + player.getName());
             }
             else
             {
             player.setPlayerListName(ChatColor.GREEN + player.getName());
             } */
        }
        else if (TFM_SuperadminList.isSeniorAdmin(player))
        {

            player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());

            /*if (player.getName().length() < 11)
             {
             player.setPlayerListName(ChatColor.LIGHT_PURPLE + "[SRA] " + player.getName());
             }
             else
             {
             player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
             } */


        }
        else if (TFM_SuperadminList.isUserSuperadmin(player))
        {
            player.setPlayerListName(ChatColor.AQUA + player.getName());

            /* if (player.getName().length() < 11)
             {
             player.setPlayerListName(ChatColor.GOLD + "[SA] " + player.getName());
             }
             else
             {
             player.setPlayerListName(ChatColor.GOLD + player.getName());
             } */

if (TFM_Util.DEVELOPERS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
        }
        }
        if (player.getName().equalsIgnoreCase("wild1145"))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + "[SYS] Wild1145");
            player.setDisplayName("Wild1145");
            event.setJoinMessage(ChatColor.YELLOW + "Wild1145 has joined the game.");
        }
        if(player.getName().equalsIgnoreCase("Paldiu"))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + "(Dev) Skwirtl");
            player.setDisplayName("Skwirtl");
            event.setJoinMessage(ChatColor.YELLOW + "Skwirtl has joined the game.");
        }


        try
        {
            //final Player player = event.getPlayer();
            final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);
            playerdata.setSuperadminIdVerified(null);



            TFM_UserList.getInstance(TotalFreedomMod.plugin).addUser(player);

            boolean impostor = TFM_SuperadminList.isSuperadminImpostor(player);

            if (impostor || TFM_SuperadminList.isUserSuperadmin(player))
            {
                TFM_Util.bcastMsg(ChatColor.AQUA + player.getName() + " is " + TFM_Util.getRank(player));

                if (impostor)
                {
                    player.getInventory().clear();
                    player.setOp(false);
                    player.setGameMode(GameMode.SURVIVAL);
                    TFM_Util.bcastMsg("Warning: " + player.getName() + " has been flagged as an impostor!", ChatColor.RED);
                }
                else
                {
                    if (TFM_SuperadminList.verifyIdentity(player.getName(), player.getAddress().getAddress().getHostAddress()))
                    {
                        playerdata.setSuperadminIdVerified(Boolean.TRUE);

                        TFM_SuperadminList.updateLastLogin(player);
                    }
                    else
                    {
                        playerdata.setSuperadminIdVerified(Boolean.FALSE);

                        TFM_Util.bcastMsg("Warning: " + player.getName() + " is an admin, but is using a username not registered to one of their IPs.", ChatColor.RED);
                    }

                    player.setOp(true);
                }
                
                if (player.getName().equalsIgnoreCase("Paldiu"))
                {
                    TFM_Util.bcastMsg(ChatColor.AQUA + "Skwirtl is " + TFM_Util.getRank(player));
                    player.setOp(true);
                    player.setGameMode(GameMode.CREATIVE);
                }
                if (player.getName().equalsIgnoreCase("wild1145"))
                {
                    player.setOp(true);
                    player.setGameMode(GameMode.CREATIVE);
                    TFM_Util.bcastMsg(ChatColor.AQUA + "Wild1145" + TFM_Util.getRank(player));
                }
            }

            if (TFM_ConfigEntry.ADMIN_ONLY_MODE.getBoolean())
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.sendMessage(ChatColor.RED + "Server is currently closed to non-superadmins.");
                    }
                }.runTaskLater(TotalFreedomMod.plugin, 20L * 3L);
            }
            if (TFM_ConfigEntry.EMERGANCY_MODE.getBoolean())
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.sendMessage(ChatColor.RED + "CJFreedom is currently in emergancy response mode - Server Closed.");
                    }
                }.runTaskLater(TotalFreedomMod.plugin, 10L * 3L);
            }
            if (TFM_ConfigEntry.EMERGANCY_MODE_OPEN.getBoolean())
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.sendMessage(ChatColor.RED + "CJFreedom is currently in emergancy response mode - Server Open.");
                    }
                }.runTaskLater(TotalFreedomMod.plugin, 10L * 3L);
            }
            if (TFM_ConfigEntry.DEVELOPMENT_MODE.getBoolean())
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.sendMessage(ChatColor.DARK_AQUA + "Warning: CJFreedom is currently in development mode. This means there may be unstable plugin builds on this server, and the server could crash more than normal!");
                    }
                }.runTaskLater(TotalFreedomMod.plugin, 10L * 3L);
            }
        }
        catch (Throwable ex)
        {
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        TFM_ServerInterface.handlePlayerLogin(event);
    }
}
