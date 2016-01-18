package de.lergin.sponge.worldgentest.crazyTrees.oak;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTree;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Random;

/**
 * Created by Malte on 18.01.2016.
 */
public class OakTree extends CrazyTree {
    @Override
    public boolean canPlaceAt(World world, int i, int i1, int i2) {
        // no halve trees due to world height
        if(i1 < 1 || i1 > 256 - this.getTreeHeightMax()){
            return false;
        }

        // we only want the trees on some ground types
        if(!this.getGroundBlocks().contains(world.getBlock(i, i1 - 1, i2))){
            return false;
        }

        // test if all the blocks of the tree trunk and the surrounding ones can be replaced
        // so we have no crazy positions
        for(int j = 0; j < this.getTreeHeightMax(); j++){
            if(
                    !this.getReplaceBlocks().contains(world.getBlock(i,i1+j,i2))  ||
                            !this.getReplaceBlocks().contains(world.getBlock(i+1,i1+j,i2))  ||
                            !this.getReplaceBlocks().contains(world.getBlock(i-1,i1+j,i2))  ||
                            !this.getReplaceBlocks().contains(world.getBlock(i,i1+j,i2+1))  ||
                            !this.getReplaceBlocks().contains(world.getBlock(i,i1+j,i2-1))
                    ){
                return false;
            }
        }

        return true;
    }

    @Override
    public void placeObject(World world, Random random, int i, int i1, int i2) {
        int treeHeight = random.nextInt(this.getTreeHeightMax() - this.getTreeHeightMin() + 1) + this.getTreeHeightMin();

        if (i1 >= 1 && i1 + treeHeight + 1 <= 256) {

            if (this.isPlaceBlockUnderTree()) {
                world.getLocation(i, i1 - 1, i2).setBlock(this.getUnderTreeBlock());
            }

            int leaveHeight = 3;
            int leaveRadius = 1;

            for (int leaveStartY = i1 - leaveHeight + treeHeight; leaveStartY <= i1 + treeHeight; leaveStartY++) {
                int currentLeaveStage = leaveStartY - i1 - treeHeight;
                int currentLeaveRadius = leaveRadius - currentLeaveStage / 2;

                for (int leaveX = i - currentLeaveRadius; leaveX <= i + currentLeaveRadius; leaveX++) {
                    for (int leaveZ = i2 - currentLeaveRadius; leaveZ <= i2 + currentLeaveRadius; leaveZ++) {
                        if (Math.abs(leaveX - i) != currentLeaveRadius ||
                                Math.abs(leaveZ - i2) != currentLeaveRadius ||
                                random.nextInt(2) != 0 && currentLeaveStage != 0) {
                            Location block = world.getLocation(leaveX, leaveStartY, leaveZ);

                            if (this.getReplaceBlocks().contains(block.getBlock())) {
                                block.setBlock(this.getLeaveBlock());
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < treeHeight; ++j) {
                Location block = world.getLocation(i, i1 + j, i2);

                block.setBlock(this.getWoodBlock());
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
            return new OakTree();
        }
    }
}
