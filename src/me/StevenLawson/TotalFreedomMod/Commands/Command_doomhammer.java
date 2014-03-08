package me.StevenLawson.TotalFreedomMod.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.StevenLawson.TotalFreedomMod.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Gain a doomhammer!", usage = "/<command>")
public class Command_doomhammer extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        //define a doomhammer
        PlayerInventory inventory = sender_p.getInventory();
        ItemStack dhammer = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta dhammermeta = dhammer.getItemMeta();
        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.RED + "The all powerful Doom Hammer!");
        dhammermeta.setDisplayName(ChatColor.DARK_RED + "Doom Hammer");
        dhammermeta.setLore(lores);
        dhammer.setItemMeta(dhammermeta);
                    
        if (args.length != 0)
        {
            return false;
        }

        if (TFM_ConfigEntry.DHAMMER_MODE.getBoolean() == false)
        {
            TFM_Util.adminAction(sender.getName(), "Enabling The DoomHammer!", true);
            TFM_ConfigEntry.DHAMMER_MODE.setBoolean(true);
            inventory.addItem(dhammer);
            for (Player player : server.getOnlinePlayers())
            {
                if (!TFM_SuperadminList.isSeniorAdmin(player))
                {
                    player.setGameMode(GameMode.SURVIVAL);
                    TFM_PlayerData playerData = TFM_PlayerData.getPlayerData(player);
                    playerData.setFrozen(true);
                }
            }
            return true;
        }
        
        if (TFM_ConfigEntry.DHAMMER_MODE.getBoolean() == true)
        {
            TFM_Util.adminAction(sender.getName(), "Disabling the DoomHammer, YOU ARE SAFE... FOR NOW!!!!", true);
            TFM_ConfigEntry.DHAMMER_MODE.setBoolean(false);
            inventory.removeItem(dhammer);
            for (Player player : server.getOnlinePlayers())
            {
                if (!TFM_SuperadminList.isSeniorAdmin(player))
                {
                    player.setGameMode(GameMode.CREATIVE);
                    TFM_PlayerData playerData = TFM_PlayerData.getPlayerData(player);
                    playerData.setFrozen(false);
                }
            }
            return true;
        }
        
        return true;
    }
    
    public static void doom(final Player player, final Player sender)
    {
        TFM_Util.adminAction(sender.getName() + "'s Doom Hammer", "Casting oblivion over " + player.getName(), true);
        TFM_Util.bcastMsg(player.getName() + " will be completely obliviated!", ChatColor.RED);

        final String IP = player.getAddress().getAddress().getHostAddress().trim();

        // remove from superadmin
        if (TFM_SuperadminList.isUserSuperadmin(player))
        {
            TFM_Util.adminAction(sender.getName() + "'s Doom Hammer", "Removing " + player.getName() + " from the superadmin list.", true);
            TFM_SuperadminList.removeSuperadmin(player);
        }

        // remove from whitelist
        player.setWhitelisted(false);

        // deop
        player.setOp(false);

        // ban IP
        TFM_ServerInterface.banIP(IP, null, null, null);

        // ban name
        TFM_ServerInterface.banUsername(player.getName(), null, null, null);

        // set gamemode to survival
        player.setGameMode(GameMode.SURVIVAL);

        // clear inventory
        player.closeInventory();
        player.getInventory().clear();

        // ignite player
        player.setFireTicks(10000);

        // generate explosion
        player.getWorld().createExplosion(player.getLocation(), 4F);

        // strike lightning
        player.getWorld().strikeLightning(player.getLocation());

        // kill (if not done already)
        player.setHealth(0.0);

        // message
        TFM_Util.adminAction(sender.getName() + "'s Doom Hammer", "Banning " + player.getName() + ", IP: " + IP, true);

        // generate explosion
        player.getWorld().createExplosion(player.getLocation(), 4F);

        //kick player
        player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your shit together!");
        long unixTime = System.currentTimeMillis() / 1000L;
        
        String user_ip = player.getAddress().getAddress().getHostAddress();
        String[] ip_parts = user_ip.split("\\.");
        if (ip_parts.length == 4)
        {
            user_ip = String.format("%s.%s.%s.%s", ip_parts[0], ip_parts[1], ip_parts[2], ip_parts[3]);
        }
        try
        {
            //log to SQL
            TotalFreedomMod.updateDatabase("INSERT INTO cjf_bans (bannedplayer, adminname, reason, time, ip) VALUES ('" + player.getName() + "', '" + sender.getName() + "', '" + "Obliterated by the DoomHammer" + "', '" + unixTime + "', '" + user_ip + "');");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Command_doomhammer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}