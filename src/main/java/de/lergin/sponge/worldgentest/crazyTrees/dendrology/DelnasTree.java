package de.lergin.sponge.worldgentest.crazyTrees.dendrology;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of DelnasTree by MinecraftModArchive/Dendrology
 */
public class DelnasTree extends DendrologyTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        if (random.nextInt(10) > 0) for (int dY = 0; dY <= height; dY++)
        {
            placeLog(world, x, y + dY, z);

            if (dY == height) leafGen(world, x, y + dY, z);
        }
        else switch (random.nextInt(4))
        {
            case 0:
                growDirect(world, random, x, y, z, 1, 0, height);
                break;

            case 1:
                growDirect(world, random, x, y, z, 0, 1, height);
                break;

            case 2:
                growDirect(world, random, x, y, z, -1, 0, height);
                break;

            default:
                growDirect(world, random, x, y, z, 0, -1, height);
        }

    }

    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) <= 3 &&
                        !(Math.abs(dX) + Math.abs(dZ) == 3 && Math.abs(dX) != 0 && Math.abs(dZ) != 0))
                    placeLeaves(world, x + dX, y, z + dZ);
                if (Math.abs(dX) < 2 && Math.abs(dZ) < 2 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, x + dX, y + 1, z + dZ);
            }
    }

    private void growDirect(World world, Random rand, int x, int y, int z, int dX, int dZ, int height)
    {
        int x1 = x;
        int z1 = z;

        placeLog(world, x1, y, z1);

        if (dX == 1) placeLog(world, x1 - 1, y, z1);

        if (dX == -1) placeLog(world, x1 + 1, y, z1);

        if (dZ == 1) placeLog(world, x1, y, z1 - 1);

        if (dZ == -1) placeLog(world, x1, y, z1 + 1);

        int addlRandomLengthX = 0;
        int addlRandomLengthZ = 0;
        for (int level = 0; level <= height; level++)
        {
            if (dX == 1 && rand.nextInt(2 + addlRandomLengthX) == 0) x1++;

            if (dX == -1 && rand.nextInt(2 + addlRandomLengthX) == 0) x1--;

            if (dZ == 1 && rand.nextInt(2 + addlRandomLengthZ) == 0) z1++;

            if (dZ == -1 && rand.nextInt(2 + addlRandomLengthZ) == 0) z1--;

            addlRandomLengthX++;
            addlRandomLengthZ++;
            placeLog(world, x1, level + y, z1);
        }
        leafGen(world, x1, height + y, z1);
    }

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new DelnasTree();
        }
    }
}
