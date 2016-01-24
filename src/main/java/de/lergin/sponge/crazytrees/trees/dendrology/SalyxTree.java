package de.lergin.sponge.crazytrees.trees.dendrology;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of SalyxTree by MinecraftModArchive/Dendrology
 */
public class SalyxTree extends DendrologyTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {

        //todo add size option to builder
        int size = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        this.placeBlockUnderTree(world, x, y, z);

        for (int dX = -1; dX <= 1; dX++)
            for (int dZ = -1; dZ <= 1; dZ++)
                for (int dY = 0; dY <= 4; dY++)
                    placeLog(world, x + dX, y + dY, z + dZ);

        for (int dY = 5; dY <= 6 + size / 2; dY++)
            placeLog(world, x, y + dY, z);

        mainBranch(world, random, x + 2, y + 4, z + 2, 1, 1, size);
        mainBranch(world, random, x + 2, y + 4, z, 1, 0, size);
        inner(world, random, x + 1, y + 5, z + 1, 1, 1, size);
        inner(world, random, x + 1, y + 5, z, 1, 0, size);
        innerInner(world, random, x, y + 6 + size / 2, z, 1, 1, size);

        mainBranch(world, random, x + 2, y + 4, z - 2, 1, -1, size);
        mainBranch(world, random, x, y + 4, z - 2, 0, -1, size);
        inner(world, random, x + 1, y + 5, z - 1, 1, -1, size);
        inner(world, random, x, y + 5, z - 1, 0, -1, size);
        innerInner(world, random, x, y + 6 + size / 2, z, 1, -1, size);

        mainBranch(world, random, x - 2, y + 4, z - 2, -1, -1, size);
        mainBranch(world, random, x - 2, y + 4, z, -1, 0, size);
        inner(world, random, x - 1, y + 5, z - 1, -1, -1, size);
        inner(world, random, x - 1, y + 5, z, -1, 0, size);
        innerInner(world, random, x, y + 6 + size / 2, z, -1, -1, size);

        mainBranch(world, random, x - 2, y + 4, z + 2, -1, 1, size);
        mainBranch(world, random, x, y + 4, z + 2, 0, 1, size);
        inner(world, random, x - 1, y + 5, z + 1, -1, 1, size);
        inner(world, random, x, y + 5, z + 1, 0, 1, size);
        innerInner(world, random, x, y + 6 + size / 2, z, -1, 1, size);

    }

    private void mainBranch(World world, Random rand, int x, int y, int z, int dX, int dZ, int size)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        if (dX != 0) logDirection = Direction.NORTH;
        if (dZ != 0) logDirection = Direction.EAST;

        final int j = dX == 1 ? 1 : -1;
        final int k = calcK(dX, dZ);
        final int m = calcM(dX, dZ);
        final int n = calcN(dX, dZ);

        final int pos = 2 * size + size / 2;
        int bend = 0;

        for (int i = 0; i < pos; i++)
        {
            placeLog(world, x1, y1, z1);
            placeLog(world, x1, y1 - 1, z1);

            if (dZ == 0) z1 += rand.nextInt(3) - 1;
            else if (dZ == 1) z1 += rand.nextInt(2);
            else z1 -= rand.nextInt(2);

            if (dX == 0) x1 += rand.nextInt(3) - 1;
            else if (dX == 1) x1 += rand.nextInt(2);
            else x1 -= rand.nextInt(2);

            if (bend == 0 && rand.nextInt(3) == 0)
            {
                y1++;
            } else if (bend == 2 && rand.nextInt(2) == 0)
            {
                y1--;
            }

            if (rand.nextInt(24) == 0)
            {
                final Direction currentLogDirection = logDirection;
                secFlag(world, rand, x1, y1, z1, j, k, size);
                secFlag(world, rand, x1, y1, z1, m, n, size);
                logDirection = currentLogDirection;
            }

            if (i == pos / 3) bend = 1;
            else if (i == 2 * pos / 3) bend = 2;

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, x1, y1, z1);
                placeLog(world, x1, y1, z1);
            }
        }
        logDirection = Direction.UP;
    }

    private void secFlag(World world, Random rand, int x, int y, int z, int dX, int dZ, int size)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        if (dX != 0) logDirection = Direction.NORTH;
        if (dZ != 0) logDirection = Direction.EAST;

        for (int i = 0; i < 2 * size; i++)
        {
            y1 += rand.nextInt(3) - 1;

            if (dX == 1) x1 += rand.nextInt(2);
            else if (dX == -1) x1 -= rand.nextInt(2);
            else x1 += rand.nextInt(3) - 1;

            if (dZ == 1) z1 += rand.nextInt(2);
            else if (dZ == -1) z1 -= rand.nextInt(2);
            else z1 += rand.nextInt(3) - 1;

            placeLog(world, x1, y1, z1);

            if (rand.nextInt(4) > 0)
            {
                placeLog(world, x1, y1, z1);
                genLeaves(world, x1, y1, z1);
            }
        }
        logDirection = Direction.UP;
    }

    private void inner(World world, Random rand, int x, int y, int z, int dX, int dZ, int size)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        if (dX != 0) logDirection = Direction.NORTH;
        if (dZ != 0) logDirection = Direction.EAST;

        int j = 5;

        for (int i = 0; i < 2 * size && j < 14; i++)
        {
            placeLog(world, x1, y1, z1);

            if (rand.nextInt(1 + i / 4) == 0)
            {
                y1++;
                j++;
            }

            if (dZ == 0) z1 += rand.nextInt(3) - 1;
            else if (dZ == 1) z1 += rand.nextInt(2);
            else z1 -= rand.nextInt(2);

            if (dX == 0) x1 += rand.nextInt(3) - 1;
            else if (dX == 1) x1 += rand.nextInt(2);
            else x1 -= rand.nextInt(2);

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, x1, y1, z1);
                placeLog(world, x1, y1, z1);
            }
        }

        logDirection = Direction.UP;
    }

    private void innerInner(World world, Random rand, int x, int y, int z, int dX, int dZ, int size)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;

        if (dX != 0) logDirection = Direction.NORTH;
        if (dZ != 0) logDirection = Direction.EAST;

        int j = 6 + size / 2;

        for (int i = 0; i < 2 * size + 1 && j < 16; i++)
        {
            placeLog(world, x1, y1, z1);

            y1++;
            j++;

            for(int k = 0; k < 2; k++) {
                if (rand.nextInt(3) == 0) if (dX == -1) x1--;
                else if (dX == 1) x1++;
            }

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, x1, y1, z1);
                placeLog(world, x1, y1, z1);
            }
        }

        logDirection = Direction.UP;
    }

    private void genLeaves(World world, int x, int y, int z)
    {
        placeLeaves(world, x, y + 1, z);
        placeLeaves(world, x, y + 2, z);

        for (int dY = 1; dY >= -2; dY--)
        {
            placeLeaves(world, x, y + dY - 1, z);

            for (int dX = -1; dX <= 1; dX++)
                for (int dZ = -1; dZ <= 1; dZ++)
                    if ((dX != 0 || dZ != 0) && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, x + dX, y + dY, z + dZ);
        }
    }

    private static int calcK(int dX, int dZ)
    {
        if (dZ == -1 && dX == 0) return -1;

        if (dZ == 1 && dX != -1 || dZ == 0 && dX != 0) return 1;

        return 0;
    }

    private static int calcM(int dX, int dZ)
    {
        if (dZ != 0 && dX == 0 || dZ == 0 && dX == 1) return 1;

        if (dZ == 0 && dX == -1 || dZ == 1 && dX == 1) return -1;

        return 0;
    }

    private static int calcN(int dX, int dZ)
    {
        if (dZ == 1) return 1;

        if (dZ == -1 || dZ == 0 && dX != 0) return -1;

        return 0;
    }

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new SalyxTree();
        }
    }
}
