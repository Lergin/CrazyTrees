package de.lergin.sponge.worldgentest.crazyTrees;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Created by Malte on 18.01.2016.
 */
public class EwcalyTree extends CrazyTree {
    private int logDirection = 0;

    @Override
    public boolean canPlaceAt(World world, int i, int i1, int i2) {
        return true;
    }

    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        final int height = random.nextInt(treeHeightMax - treeHeightMin + 1) + treeHeightMin;

        //   if (isPoorGrowthConditions(world, x, y, z, height, getSaplingBlock())) return false;

        final Location block = world.getLocation(x, y , z);

        if(placeBlockUnderTree)
            block.add(0, -1, 0).setBlock(underTreeBlock);

        for (int dy = 0; dy <= height; dy++)
            placeLog(world, x, y + dy, z);

        int size = 1;

        for (int y1 = y + height / 2; y1 <= y + height; y1++)
        {
            if (random.nextInt(5) > 2 || y1 == y + height)
            {
                if (random.nextInt(20) < 1) size = 2;

                if (random.nextInt(4) == 0 && y1 - y > 10 && y1 - y < 20) size = 2;

                if (y1 - y >= 20) size = 3;

                for (int dX = -size; dX <= size; dX++)
                    for (int dZ = -size; dZ <= size; dZ++)
                    {
                        placeLeaves(world, x + dX, y1, z + dZ);
                        if (size != Math.abs(dX) || size != Math.abs(dZ)) placeLeaves(world, x + dX, y1, z + dZ);

                       // if (size == 3 &&
                       //         (Math.abs(dX) == 3 && Math.abs(dZ) == 2 || Math.abs(dX) == 2 && Math.abs(dZ) == 3))
                        //    world.getLocation(x + dX, y1, z + dZ).setBlockType(BlockTypes.AIR);

                        if (y1 == y + height && Math.abs(dX) < 3 && Math.abs(dZ) < 3 &&
                                (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                        {
                            if (size > 1) placeLeaves(world, x + dX, y1 + 1, z + dZ);

                            if (size == 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                                placeLeaves(world, x + dX, y1 + 1, z + dZ);
                        }
                    }
            }
        }

        for (int dY = height / 2; dY <= height - 5; dY++)
        {
            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, -1, 0, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, 1, 0, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, 0, -1, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, 0, 1, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, -1, 1, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, -1, -1, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, 1, 1, height);

            if (random.nextInt(9) == 0) branches(world, x, y + dY, z, 1, -1, height);
        }
    }

    void branches(World world, int x, int y, int z, int dX, int dZ, int height)
    {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i < 8; i++)
        {
            if (dX == -1)
            {
                x1--;
                logDirection = 4;
            }

            if (dX == 1)
            {
                x1++;
                logDirection = 4;
            }

            if (dZ == -1)
            {
                z1--;
                logDirection = 8;
            }

            if (dZ == 1)
            {
                z1++;
                logDirection = 8;
            }

            placeLog(world, x1, y1, z1);
            logDirection = 0;

            if ((i == 4 || i == 7) && height >= 13) genLeaves(world, x1, y1, z1);

            if ((i == 4 || i == 7) && height < 13) genLeavesS(world, x1, y1, z1);

            y1++;
        }
    }


    void genLeaves(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
        {
            for (int dY = -3; dY <= 3; dY++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dY) != 3) && (Math.abs(dX) != 2 || Math.abs(dY) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dY) != 2)) placeLeaves(world, x + dX, y, z + dY);

                if (Math.abs(dX) < 3 && Math.abs(dY) < 3 && (Math.abs(dX) != 2 || Math.abs(dY) != 2))
                {
                    placeLeaves(world, x + dX, y - 1, z + dY);
                    placeLeaves(world, x + dX, y + 1, z + dY);
                }
            }
        }
    }

    void placeLeaves(World world, int x, int y, int z)
    {
        if (replaceBlocks.contains(world.getBlock(x, y, z)))
            world.getLocation(x, y, z).setBlock(leaveBlock);
    }

    void placeLog(World world, int x, int y, int z)
    {
        final Location<World> loc = world.getLocation(x, y, z);

        if (canBeReplacedByLog(loc))
            loc.setBlock(woodBlock);
    }

    boolean canBeReplacedByLog(Location<World> loc)
    {
        return replaceBlocks.contains(loc.getBlock()) || loc.getBlock() == leaveBlock;
    }


    void genLeavesS(World world, int i3, int j3, int k3)
    {
        for (int x = -2; x <= 2; x++)
        {
            for (int y = -2; y <= 2; y++)
            {
                if (Math.abs(x) != 2 || Math.abs(y) != 2) placeLeaves(world, i3 + x, j3, k3 + y);

                if (Math.abs(x) < 2 && Math.abs(y) < 2 && (Math.abs(x) != 1 || Math.abs(y) != 1))
                {
                    placeLeaves(world, i3 + x, j3 + 1, k3 + y);
                    placeLeaves(world, i3 + x, j3 - 1, k3 + y);
                }
            }
        }
    }
}
