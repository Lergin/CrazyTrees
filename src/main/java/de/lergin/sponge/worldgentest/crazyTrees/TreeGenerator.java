package de.lergin.sponge.worldgentest.crazyTrees;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Malte on 14.01.2016.
 */
public enum  TreeGenerator implements TreeGeneratorable{

    OAK {
        @Override
        public boolean canPlaceAt(World world, int i, int i1, int i2, int treeHeightMax, int treeHeightMin,
                                       ArrayList<BlockState> replaceBlocks, ArrayList<BlockState> groundBlocks){
            // no halve trees due to world height
            if(i1 < 1 || i1 > 256 - treeHeightMax){
                return false;
            }

            // we only want the trees on some ground types
            if(!groundBlocks.contains(world.getBlock(i, i1 - 1, i2))){
                return false;
            }

            // test if all the blocks of the tree trunk and the surrounding ones can be replaced
            // so we have no crazy positions
            for(int j = 0; j < treeHeightMax; j++){
                if(
                        !replaceBlocks.contains(world.getBlock(i,i1+j,i2))  ||
                                !replaceBlocks.contains(world.getBlock(i+1,i1+j,i2))  ||
                                !replaceBlocks.contains(world.getBlock(i-1,i1+j,i2))  ||
                                !replaceBlocks.contains(world.getBlock(i,i1+j,i2+1))  ||
                                !replaceBlocks.contains(world.getBlock(i,i1+j,i2-1))
                        ){
                    return false;
                }
            }

            return true;
        }

        @Override
        public void placeObject(World world, Random random, int i, int i1, int i2, BlockState woodBlock,
                                BlockState leaveBlock, BlockState underTreeBlock, int treeHeightMax, int treeHeightMin,
                                boolean placeBlockUnderTree, ArrayList<BlockState> replaceBlocks){
            int treeHeight = random.nextInt(treeHeightMax - treeHeightMin + 1) + treeHeightMin;

            if (i1 >= 1 && i1 + treeHeight + 1 <= 256) {

                if (placeBlockUnderTree) {
                    world.getLocation(i, i1 - 1, i2).setBlock(underTreeBlock);
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

                                if (replaceBlocks.contains(block.getBlock())) {
                                    block.setBlock(leaveBlock);
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < treeHeight; ++j) {
                    Location block = world.getLocation(i, i1 + j, i2);

                    block.setBlock(woodBlock);
                }
            }
        }
    },
    CRAZY {
        @Override
        public boolean canPlaceAt(World world, int i, int i1, int i2, int treeHeightMax, int treeHeightMin, ArrayList<BlockState> replaceBlocks, ArrayList<BlockState> groundBlocks) {
            // no halve trees due to world height
            if (i1 < 1 || i1 > 256 - treeHeightMax) {
                return false;
            }

            // we only want the trees on some ground types
            if (!groundBlocks.contains(world.getBlock(i, i1 - 1, i2))) {
                return false;
            }

            // test if all the blocks of the tree trunk and the surrounding ones can be replaced
            // so we have no crazy positions
            for (int j = 0; j < treeHeightMax; j++) {
                if (
                        !replaceBlocks.contains(world.getBlock(i, i1 + j, i2)) ||
                                !replaceBlocks.contains(world.getBlock(i + 1, i1 + j, i2)) ||
                                !replaceBlocks.contains(world.getBlock(i - 1, i1 + j, i2)) ||
                                !replaceBlocks.contains(world.getBlock(i, i1 + j, i2 + 1)) ||
                                !replaceBlocks.contains(world.getBlock(i, i1 + j, i2 - 1))
                        ) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void placeObject(World world, Random random, int i, int i1, int i2, BlockState woodBlock,
                                BlockState leaveBlock, BlockState underTreeBlock, int treeHeightMax, int treeHeightMin,
                                boolean placeBlockUnderTree, ArrayList<BlockState> replaceBlocks) {
            int treeHeight = random.nextInt(treeHeightMax - treeHeightMin + 1) + treeHeightMin;

            if (i1 >= 1 && i1 + treeHeight + 1 <= 256) {

                if (placeBlockUnderTree) {
                    world.getLocation(i, i1 - 1, i2).setBlock(underTreeBlock);
                }

                int leaveHeight = 5;
                int leaveRadius = 1;

                for (int leaveStartY = i1 - leaveHeight + treeHeight; leaveStartY <= i1 + treeHeight; leaveStartY++) {
                    int currentLeaveStage = leaveStartY - i1 - treeHeight;
                    int currentLeaveRadius = leaveRadius - currentLeaveStage / 2;

                    for (int leaveX = i - currentLeaveRadius; leaveX <= i + currentLeaveRadius; leaveX++) {
                        for (int leaveZ = i2 - currentLeaveRadius; leaveZ <= i2 + currentLeaveRadius; leaveZ++) {
                            if (Math.abs(leaveX - i) != currentLeaveRadius ||
                                    Math.abs(leaveZ - i2) != currentLeaveRadius ||
                                    random.nextInt(3) != 0 && currentLeaveStage != 0) {
                                Location block = world.getLocation(leaveX, leaveStartY, leaveZ);

                                if (replaceBlocks.contains(block.getBlock())) {
                                    block.setBlock(leaveBlock);
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < treeHeight; ++j) {
                    Location block = world.getLocation(i, i1 + j, i2);

                    block.setBlock(woodBlock);
                }
            }
        }
    }
}


interface TreeGeneratorable{
    boolean canPlaceAt(World world, int i, int i1, int i2, int treeHeightMax, int treeHeightMin,
                       ArrayList<BlockState> replaceBlocks, ArrayList<BlockState> groundBlocks);

    void placeObject(World world, Random random, int x, int y, int z, BlockState woodBlock,
                     BlockState leaveBlock, BlockState underTreeBlock, int treeHeightMax, int treeHeightMin,
                     boolean placeBlockUnderTree, ArrayList<BlockState> replaceBlocks);
}