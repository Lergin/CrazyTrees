package de.lergin.sponge.crazytrees.trees.vanilla.oak;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.dendrology.DendrologyTree;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of VanillaTree by MinecraftModArchive/Dendrology
 */
public class OakTree extends DendrologyTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > this.getTreeHeightMin()){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        placeCanopy(world, random, x, y, z, height);

        for (int dY = 0; dY < height; ++dY)
            placeLog(world, x, y + dY, z);
    }


    private void placeCanopy(World world, Random rand, int x, int y, int z, int height)
    {
        for (int y1 = y - 3 + height; y1 <= y + height; ++y1)
        {
            final int distanceToTopOfTrunk = y1 - (y + height);
            final int radius = 1 - distanceToTopOfTrunk / 2;

            for (int x1 = x - radius; x1 <= x + radius; ++x1)
            {
                final int dX = x1 - x;

                for (int z1 = z - radius; z1 <= z + radius; ++z1)
                {
                    final int dZ = z1 - z;

                    if (Math.abs(dX) != radius || Math.abs(dZ) != radius ||
                            rand.nextInt(2) != 0 && distanceToTopOfTrunk != 0) placeLeaves(world, x1, y1, z1);
                }
            }
        }
    }

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new OakTree();
        }
    }
}
