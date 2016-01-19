package de.lergin.sponge.worldgentest.crazyTrees.dendrology.kulist;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.DendrologyTree;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of NormalKulistTree by MinecraftModArchive/Dendrology
 */
public class KulistTree extends DendrologyTree {
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

            if (level == height) leafGen(world, x, y + level, z);

            if (level > 2 && level < height)
            {
                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, -1, 0);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, 1, 0);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, 0, -1);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, 0, 1);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, -1, 1);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, -1, -1);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, 1, 1);

                if (random.nextInt(6) == 0) branch(world, random, x, y, z, height, level, 1, -1);
            }
        }
    }

    void branch(World world, Random rand, int x, int y, int z, int height, int level, int dX, int dZ)
    {
        int x1 = x;
        int z1 = z;
        int level1 = level + y;
        final int length = height - level;

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                x1--;
                logDirection = Direction.NORTH;

                if (dZ == 0 && rand.nextInt(4) == 0) z1 += rand.nextInt(3) - 1;
            }

            if (dX == 1 && rand.nextInt(3) > 0)
            {
                x1++;
                logDirection = Direction.NORTH;

                if (dZ == 0 && rand.nextInt(4) == 0) z1 += rand.nextInt(3) - 1;
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                z1--;
                logDirection = Direction.EAST;

                if (dX == 0 && rand.nextInt(4) == 0) x1 += rand.nextInt(3) - 1;
            }

            if (dZ == 1 && rand.nextInt(3) > 0)
            {
                z1++;
                logDirection = Direction.EAST;

                if (dX == 0 && rand.nextInt(4) == 0) x1 += rand.nextInt(3) - 1;
            }

            placeLog(world, x1, level1, z1);
            logDirection = Direction.UP;

            if (rand.nextInt(3) > 0) level1++;

            if (i == length)
            {
                placeLog(world, x1, level1, z1);
                leafGen(world, x1, level1, z1);
            }
        }
    }

    void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, x + dX, y, z + dZ);

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, x + dX, y + 1, z + dZ);
                    placeLeaves(world, x + dX, y - 1, z + dZ);
                }

                if (Math.abs(dX) + Math.abs(dZ) < 2)
                {
                    placeLeaves(world, x + dX, y + 2, z + dZ);
                    placeLeaves(world, x + dX, y - 2, z + dZ);
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
            return new KulistTree();
        }
    }
}
