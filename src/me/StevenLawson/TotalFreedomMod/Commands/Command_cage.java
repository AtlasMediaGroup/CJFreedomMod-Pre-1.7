package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Place a cage around someone.", usage = "/<command> <partialname> <off | [[outermaterial] [innermaterial]]>")
public class Command_cage extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
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

        TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        Material cage_material_outer = Material.GLASS;
        Material cage_material_inner = Material.AIR;
        if (args.length >= 2)
        {
            if (TFM_Util.isStopCommand(args[1]))
            {
                TFM_Util.adminAction(sender.getName(), "Uncaging " + player.getName() + ".", true);

                playerdata.setCaged(false);
                playerdata.regenerateHistory();
                playerdata.clearHistory();

                return true;
            }
            else
            {
                cage_material_outer = Material.matchMaterial(args[1]);
                if (cage_material_outer == null)
                {
                    cage_material_outer = Material.GLASS;
                }
            }
        }

        if (args.length >= 3)
        {
            if (args[2].equalsIgnoreCase("water"))
            {
                cage_material_inner = Material.STATIONARY_WATER;
            }
            else if (args[2].equalsIgnoreCase("lava"))
            {
                cage_material_inner = Material.STATIONARY_LAVA;
            }
        }

        Location targetPos = player.getLocation().add(0, 1, 0);
        playerdata.setCaged(true, targetPos, cage_material_outer, cage_material_inner);
        playerdata.regenerateHistory();
        playerdata.clearHistory();
        TFM_Util.buildHistory(targetPos, 2, playerdata);
        TFM_Util.generateCube(targetPos, 2, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.OUTER));
        TFM_Util.generateCube(targetPos, 1, playerdata.getCageMaterial(TFM_PlayerData.CageLayer.INNER));

        player.setGameMode(GameMode.SURVIVAL);

        TFM_Util.adminAction(sender.getName(), "Caging " + player.getName() + ".", true);

        return true;
    }
}
