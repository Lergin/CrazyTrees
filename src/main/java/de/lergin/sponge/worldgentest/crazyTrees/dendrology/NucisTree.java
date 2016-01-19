package de.lergin.sponge.worldgentest.crazyTrees.dendrology;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of NucisTree by MinecraftModArchive/Dendrology
 */
public class NucisTree extends DendrologyTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        for (int level = 0; level < height; level++)
        {
            placeLog(world, x, y + level, z);

            if (level > 3)
            {
                final int branchRarity = height / (level - 2) + 1;

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

        leafGen(world, x, y + height, z);
    }

    private void branch(World world, Random random, int x, int y, int z, int height, int level, int dX, int dZ)
    {
        int level1 = level + y;
        final int lengthToGo = height - level;

        int x1 = x;
        int z1 = z;

        int index = 0;
        while (index <= lengthToGo)
        {
            if (dX == -1 && random.nextInt(3) > 0)
            {
                x1--;
                logDirection = Direction.NORTH;

                if (dZ == 0 && random.nextInt(4) == 0) z1 = z1 + random.nextInt(3) - 1;
            }
            else if (dX == 1 && random.nextInt(3) > 0)
            {
                x1++;
                logDirection = Direction.NORTH;

                if (dZ == 0 && random.nextInt(4) == 0) z1 = z1 + random.nextInt(3) - 1;
            }

            if (dZ == -1 && random.nextInt(3) > 0)
            {
                z1--;
                logDirection = Direction.EAST;

                if (dX == 0 && random.nextInt(4) == 0) x1 = x1 + random.nextInt(3) - 1;
            } else if (dZ == 1 && random.nextInt(3) > 0)
            {
                z1++;
                logDirection = Direction.EAST;

                if (dX == 0 && random.nextInt(4) == 0) x1 = x1 + random.nextInt(3) - 1;
            }

            placeLog(world, x1, level1, z1);

            if (random.nextInt(3) > 0) level1++;

            if (index == lengthToGo || random.nextInt(6) == 0)
            {
                placeLog(world, x1, level1, z1);
                leafGen(world, x1, level1, z1);
            }

            logDirection = Direction.UP;

            index++;
        }
    }

    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
        {
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
    }

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new NucisTree();
        }
    }
}
