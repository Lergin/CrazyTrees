package de.lergin.sponge.worldgentest;

import com.google.inject.Inject;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import de.lergin.sponge.worldgentest.crazyTrees.TreeGenerator;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.block.RedstonePoweredData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.recipe.Recipe;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Chunk;

import java.util.Random;
import java.util.Set;

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

        int chunkX = player.getLocation().getBlockPosition().getX() >> 4;
        int chunkZ = player.getLocation().getBlockPosition().getZ() >> 4;

        Chunk chunk = player.getWorld().getChunk(chunkX, 0, chunkZ).get();



        //PopulatorObjects.RED.placeObject(player.getWorld(), new Random(), x, y, z);

        //PopulatorObjects.DESERT_WELL.placeObject(player.getWorld(), new Random(), x, y, z);

        //BiomeTreeTypes.SWAMP.getLargePopulatorObject().orElse(BiomeTreeTypes.JUNGLE_BUSH.getPopulatorObject()).placeObject(player.getWorld(), new Random(), x, y, z);


        

        CrazyTree crazyTree = CrazyTree.builder().leaveBlock(BlockTypes.SPONGE)
                .woodBlock(BlockTypes.IRON_ORE).placeBlockUnderTree(false).treeHeight(32,8)
                .treeType(TreeGenerator.OAK).build();

        crazyTree.placeObject(player.getWorld(), new Random(), x+3, y, z);

        return CommandResult.success();
    }
}
