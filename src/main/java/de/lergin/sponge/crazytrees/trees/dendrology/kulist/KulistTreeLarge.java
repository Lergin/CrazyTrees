package de.lergin.sponge.crazytrees.trees.dendrology.kulist;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of LargeKulistTree by MinecraftModArchive/Dendrology
 */
public class KulistTreeLarge extends KulistTree {

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

    @Override
    public CrazyTree.Builder builder() {
        return new Builder();
    }

    public static class Builder extends CrazyTree.Builder {
        @Override
        public CrazyTree getTreeType() {
            return new KulistTreeLarge();
        }
    }
}
