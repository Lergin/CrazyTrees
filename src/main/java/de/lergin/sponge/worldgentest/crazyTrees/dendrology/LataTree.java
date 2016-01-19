package de.lergin.sponge.worldgentest.crazyTrees.dendrology;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of LataTree by MinecraftModArchive/Dendrology
 */
public class LataTree extends DendrologyTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(15) + 6;

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        for (int level = 0; level <= height; level++)
        {
            if (level == height) leafGen(world, x, y + level, z);
            else placeLog(world, x, y + level, z);

            if (level > 3 && level < height)
            {
                final int branchRarity = height / level + 1;

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, -1, 0);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, 1, 0);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, 0, -1);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, 0, 1);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, -1, 1);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, -1, -1);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, 1, 1);

                if (random.nextInt(branchRarity) == 0) branch(world, random, x, y, z, height, level, 1, -1);
            }
        }

    }

    private void branch(World world, Random rand, int x, int y, int z, int treeHeight, int branchLevel, int dX, int dZ)
    {
        final int length = treeHeight - branchLevel;

        int x1 = x;
        int y1 = y + branchLevel;
        int z1 = z;

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                x1--;
                logDirection = Direction.NORTH;

                if (dZ == 0 && rand.nextInt(4) == 0) z1 += rand.nextInt(3) - 1;
            } else if (dX == 1 && rand.nextInt(3) > 0)
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
            } else if (dZ == 1 && rand.nextInt(3) > 0)
            {
                z1++;
                logDirection = Direction.EAST;

                if (dX == 0 && rand.nextInt(4) == 0) x1 += rand.nextInt(3) - 1;
            }

            placeLog(world, x1, y1, z1);
            logDirection = Direction.UP;

            if (rand.nextInt(3) == 0)
            {
                leafGen(world, x1, y1, z1);
            }

            if (rand.nextInt(3) > 0)
            {
                y1++;
            }

            if (i == length)
            {
                placeLog(world, x1, y1, z1);
                leafGen(world, x1, y1, z1);
            }
        }
    }

    private void leafGen(World world, int x, int y, int z)
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
            return new LataTree();
        }
    }
}
