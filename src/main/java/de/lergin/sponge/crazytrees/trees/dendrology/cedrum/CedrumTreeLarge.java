package de.lergin.sponge.crazytrees.trees.dendrology.cedrum;

import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Generator Code: edited version of CedrumTreeLarge by MinecraftModArchive/Dendrology
 */
public class CedrumTreeLarge extends CedrumTree {
    @Override
    public void placeObject(World world, Random random, int x, int y, int z){
        int height = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        while (!canPlaceAt(world, x, y, z, height) && height > 0){
            height--;
        }

        this.placeBlockUnderTree(world, x, y, z);

        for (int level = height; level >= 0; level--)
        {
            placeLog(world, x, y + level, z);

            if (level > 5 && level < height)
            {
                if (level == height - 1)
                {
                    leafGen(world, 2, x, y + level, z);
                }

                if (level == height - 4 || level == height - 7 || level == height - 10 || level == height - 13)
                {
                    logGen(world, x, y, z, level);

                    final int size = level == height - 4 ? 3 :
                            level == height - 7 ? 4 : level == height - 10 ? 5 : random.nextInt(3) + 2;
                    leafGen(world, size, x, y + level, z);
                }
            }

            if (level == height) leafTop(world, x, y + level, z);
        }
    }

}
