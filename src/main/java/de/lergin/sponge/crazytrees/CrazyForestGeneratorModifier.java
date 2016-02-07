package de.lergin.sponge.crazytrees;

import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.util.weighted.VariableAmount;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.gen.WorldGenerator;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import org.spongepowered.api.world.gen.populator.Forest;

//currendly not in use
class CrazyForestGeneratorModifier implements WorldGeneratorModifier {

    @Override
    public String getId() {
        return "crazytrees:forestmodifier";
    }

    @Override
    public String getName() {
        return "Crazy Trees Forest Modifier";
    }

    @Override
    public void modifyWorldGenerator(WorldCreationSettings world, DataContainer settings, WorldGenerator worldGenerator) {
        Forest forest = Forest.builder().perChunk(VariableAmount.fixed(1))
                .type(CrazyTreeType.EWACALY.getBuilder().leaveBlock(BlockTypes.HAY_BLOCK).build(), 1)
                .build();

        worldGenerator.getPopulators().add(forest);
    }
}