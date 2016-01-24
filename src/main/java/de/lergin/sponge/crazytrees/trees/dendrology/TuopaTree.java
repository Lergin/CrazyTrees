package de.lergin.sponge.crazytrees.trees.dendrology;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of TuopaTree by MinecraftModArchive/Dendrology
 */
public class TuopaTree extends DendrologyTree {

    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        for (int level = 0; level <= 6 * height + 1; level++)
        {
            if (level != 6 * height + 1) placeLog(world, x, y + level, z);

            if (height == 1 && level > 2) for (int dX = -1; dX <= 1; dX++)
            {
                for (int dZ = -1; dZ <= 1; dZ++)
                    if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, x + dX, y + level, z + dZ);
            }

            if (height == 2 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, x + dX, y + level, z + dZ);

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level == 7) placeLeaves(world, x + dX, y + 7, z + dZ);

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height - 1 && level > 3)
                        placeLeaves(world, x + dX, y + level, z + dZ);
                }

            if (height == 3 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, x + dX, y + level, z + dZ);

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height && level > 3)
                        placeLeaves(world, x + dX, y + level, z + dZ);
                }

            if (height == 4 && level > 2) for (int dX = -3; dX <= 3; dX++)
                for (int dZ = -3; dZ <= 3; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, x + dX, y + level, z + dZ);

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level <= 14 && level >= 2)
                        placeLeaves(world, x + dX, y + level, z + dZ);

                    if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) &&
                            (level == 6 * height || level == 5)) placeLeaves(world, x + dX, y + level, z + dZ);

                    if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
                            (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && level <= 6 * height - 1 && level > 5)
                        placeLeaves(world, x + dX, y + level, z + dZ);
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
            return new TuopaTree();
        }
    }
}
