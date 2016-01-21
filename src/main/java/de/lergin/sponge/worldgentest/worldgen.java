package de.lergin.sponge.worldgentest;

/**
 * Created by Malte on 07.01.2016.
 */

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeBuilder;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.util.weighted.VariableAmount;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.gen.*;
import org.spongepowered.api.world.gen.populator.*;

class worldgen implements WorldGeneratorModifier {

    @Override
    public String getId() {
        return "example:mymodifier";
    }

    @Override
    public String getName() {
        return "My Modifier";
    }

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
    /*    BiomeGenerationSettings desertSettings = worldGenerator.getBiomeSettings(BiomeTypes.DESERT);
        for (Cactus populator : desertSettings.getPopulators(Cactus.class)) {
            populator.setHeight(5);
            populator.setCactiPerChunk(20);
        }
*/
        //PopulatorObject crazyForestPopulator = new CrazyForestPopulator();

    /*    BiomeGenerationSettings forestSettings = worldGenerator.getBiomeSettings(BiomeTypes.FOREST);
        for (Forest populator : forestSettings.getPopulators(Forest.class)) {
            populator.setSupplierOverride(chunkLocation -> {
                return crazyForestPopulator;
            });
        }
*/
  /*      BiomeGenerationSettings plainsSettings = worldGenerator.getBiomeSettings(BiomeTypes.PLAINS);
        for (Flower populator : plainsSettings.getPopulators(Flower.class)) {
            populator.setFlowersPerChunk(40);
        }

        for (DesertWell populator : desertSettings.getPopulators(DesertWell.class)) {
            populator.setSpawnProbability(1);
        }

        BiomeGenerationSettings swampSettings = worldGenerator.getBiomeSettings(BiomeTypes.SWAMPLAND);
        for (WaterLily populator : desertSettings.getPopulators(WaterLily.class)) {
            populator.setWaterLilyPerChunk(1);
        }
*/

        CrazyTree crazyTree = CrazyTreeBuilder.EWACALY_LARGE.build();

      // CrazyTree crazyTree = CrazyTree.builder().leaveBlock(BlockTypes.DIAMOND_BLOCK)
      //          .woodBlock(BlockTypes.OBSIDIAN).build();

        Forest forest = Forest.builder().perChunk(VariableAmount.fixed(1))
                .type(CrazyTreeType.EWACALY.getBuilder().leaveBlock(BlockTypes.HAY_BLOCK).build(), 0.1)
          //      .type(CrazyTreeType.KULIST.getBuilder().leaveBlock(BlockTypes.SPONGE).woodBlock(BlockTypes.LOG2).build(), 0.1)
          //      .type(CrazyTreeType.KULIST.getBuilder().build(), 0.1)
          //      .type(CrazyTreeType.LATA.getBuilder().build(), 0.1)
          //      .type(CrazyTreeType.CEDRUM.getBuilder().build(), 0.1)
          //      .type(CrazyTreeType.SALYX.getBuilder().leaveBlock(BlockTypes.SPONGE).woodBlock(BlockTypes.LOG2).build(), 0.1)
          //      .type(CrazyTreeType.SALYX.getBuilder().build(), 0.1)
          //      .type(CrazyTreeType.TUOPA.getBuilder().build(), 0.1)
          //      .type(CrazyTreeType.NUCIS.getBuilder().build(), 0.1)
                .build();

        worldGenerator.getPopulators().add(forest);
    }

}