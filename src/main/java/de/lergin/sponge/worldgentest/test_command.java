package de.lergin.sponge.worldgentest;

import com.google.inject.Inject;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeBuilder;
import org.slf4j.Logger;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Random;


/**
 * Created by Malte on 02.01.2016.
 */
public class test_command implements CommandExecutor {

    @Inject
    Logger logger;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;

        int x  = player.getLocation().getBlockX() + 2;
        int y  = player.getLocation().getBlockY();
        int z  = player.getLocation().getBlockZ();

        CrazyTree crazyTree = CrazyTreeBuilder.OAK.leaveBlock(BlockTypes.SPONGE).woodBlock(BlockTypes.OBSIDIAN).placeBlockUnderTree(false).build();


        crazyTree.placeObject(player.getWorld(), new Random(), x+3, y, z);


        return CommandResult.success();
    }
}
