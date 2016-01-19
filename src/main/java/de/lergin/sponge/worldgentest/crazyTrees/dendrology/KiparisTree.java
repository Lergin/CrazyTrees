package de.lergin.sponge.worldgentest.crazyTrees.dendrology;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of KiparisTree by MinecraftModArchive/Dendrology
 */
public class KiparisTree extends DendrologyTree {

    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }


        final int size = Math.round((height - 1) / 4);

        this.placeBlockUnderTree(world, x, y, z);

        for (int dY = 0; dY <= height; dY++)
        {
            if (dY != height) placeLog(world, x, y + dY, z);

            if (dY >= 1)
            {
                switch (size)
                {
                    case 1:
                        genSmallLeaves(world, x, y + dY, z);
                        break;
                    case 2:
                        genMediumLeaves(world, x, y, z, dY);
                        break;
                    case 3:
                        genLargeLeaves(world, x, y, z, dY);
                        break;
                    default:
                        genExtraLargeLeaves(world, x, y, z, dY);
                        break;
                }
            }

            if (dY == height) placeLeaves(world, x, y + dY + 1, z);
            if (dY == height && (size == 4 || size == 3)) placeLeaves(world, x, y + dY + 2, z);
        }
    }

    private void genExtraLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY <= 14 && dY >= 2)
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) && dY == 12 ||
                        dY == 11 || dY == 3) placeLeaves(world, x + dX, y + dY, z + dZ);

                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
                        (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && dY <= 10 && dY >= 4)
                    placeLeaves(world, x + dX, y + dY, z + dZ);
            }
    }

    private void genLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                        (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 10 && dY >= 2)
                    placeLeaves(world, x + dX, y + dY, z + dZ);
            }
    }

    private void genMediumLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                {
                    placeLeaves(world, x + dX, y + dY, z + dZ);
                }
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY == 7)
                {
                    placeLeaves(world, x + dX, y + 7, z + dZ);
                }
                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                        (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 6 && dY >= 2)
                {
                    placeLeaves(world, x + dX, y + dY, z + dZ);
                }
            }
    }

    private void genSmallLeaves(World world, int x, int y, int z)
    {
        for (int dX = -1; dX <= 1; dX++)
            for (int dZ = -1; dZ <= 1; dZ++)
                if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, x + dX, y, z + dZ);
    }

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new KiparisTree();
        }
    }
}
