package de.lergin.sponge.worldgentest.crazyTrees.dendrology.cedrum;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.DendrologyTree;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of CedrumTree by MinecraftModArchive/Dendrology
 */
public class CedrumTree extends DendrologyTree {
    @Override
    public Builder builder() {
        return new Builder();
    }

    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        for (int level = 0; level <= height; level++)
        {
            placeLog(world, x, y + level, z);

            if (level == height) leafTop(world, x, y + level, z);

            if (level > 5 && level < height)
            {
                if (level == height - 1)
                {
                    leafGen(world, 2, x, y + level, z);
                }

                if (level == height - 4 || level == height - 7)
                {
                    logGen(world, x, y, z, level);
                    leafGen(world, level == height - 4 ? 3 : 4, x, y + level, z);
                }

                if (level == height - 10 || level == height - 13)
                {
                    leafGen(world, level == height - 10 ? 3 : 2, x, y + level, z);
                }
            }
        }
    }

    void logGen(World world, int x, int y, int z, int level){
        for (int next = 1; next < 3; next++)
        {
            //Todo: when DirectionData is implemented test if right directions / is working

            logDirection = Direction.EAST;
            placeLog(world, x + next, y + level - 2, z);
            placeLog(world, x - next, y + level - 2, z);
            logDirection = Direction.NORTH;
            placeLog(world, x, y + level - 2, z + next);
            placeLog(world, x, y + level - 2, z - next);
            logDirection = Direction.UP;
        }

    }

    void leafTop(World world, int x, int y, int z)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) < 3) placeLeaves(world, x + dX, y, z + dZ);

                if (Math.abs(dX) + Math.abs(dZ) < 2) placeLeaves(world, x + dX, y + 1, z + dZ);

                if (Math.abs(dX) == 0 && Math.abs(dZ) == 0) placeLeaves(world, x + dX, y + 2, z + dZ);
            }
    }

    void leafGen(World world, int size, int x, int y, int z)
    {
        final int radius;
        final int limiter1;
        final int limiter2;
        final int limiter3;

        switch (size)
        {
            case 3:
                radius = 4;
                limiter1 = 3;
                limiter2 = 5;
                limiter3 = 7;
                break;
            case 4:
                radius = 5;
                limiter1 = 5;
                limiter2 = 7;
                limiter3 = 8;
                break;
            case 5:
                radius = 6;
                limiter1 = 7;
                limiter2 = 8;
                limiter3 = 9;
                break;
            default:
                radius = 3;
                limiter1 = 2;
                limiter2 = 3;
                limiter3 = 5;
        }
        doLeafGen(world, x, y, z, radius, limiter1, limiter2, limiter3);
    }

    private void doLeafGen(World world, int x, int y, int z, int radius, int limiter1, int limiter2, int limiter3)
    {
        for (int dX = -radius; dX <= radius; dX++)
            for (int dZ = -radius; dZ <= radius; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) < limiter1) placeLeaves(world, x + dX, y, z + dZ);

                if (Math.abs(dX) + Math.abs(dZ) < limiter2) placeLeaves(world, x + dX, y - 1, z + dZ);

                if (Math.abs(dX) + Math.abs(dZ) < limiter3) placeLeaves(world, x + dX, y - 2, z + dZ);
            }
    }

    public static class Builder extends CrazyTree.Builder{
        @Override
        public CrazyTree getTreeType() {
            return new CedrumTree();
        }
    }
}
