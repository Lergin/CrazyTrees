package de.lergin.sponge.crazytrees.trees;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.gen.PopulatorObject;

import java.util.ArrayList;
import java.util.Random;

public abstract class CrazyTree implements PopulatorObject {
    BlockState woodBlock;
    BlockState leaveBlock;
    boolean placeBlockUnderTree;
    BlockState underTreeBlock;
    ArrayList<BlockState> replaceBlocks;
    ArrayList<BlockState> groundBlocks;
    int treeHeightMax;
    int treeHeightMin;

    /**
     * returns the minimum height the tree can have
     * @return the minimum height
     */
    public int getTreeHeightMin() {
        return treeHeightMin;
    }

    /**
     * returns the Blockstate the tree will use as woodblock
     * @return the Blockstate of the woodblocks
     */
    public BlockState getWoodBlock() {
        return woodBlock;
    }

    /**
     * returns the Blockstate the tree will use as leaveblock
     * @return the Blockstate of the leaveblock
     */
    public BlockState getLeaveBlock() {
        return leaveBlock;
    }

    /**
     * returns if the tree is replacing the block under it
     * @return true if it will replace it else false
     */
    public boolean isPlaceBlockUnderTree() {
        return placeBlockUnderTree;
    }

    /**
     * returns the Blockstate the tree will use to replace the block under the tree
     * @return the Blockstate of the block under the tree
     */
    public BlockState getUnderTreeBlock() {
        return underTreeBlock;
    }

    /**
     * returns the blocks that the tree can replace
     * @return a ArrayList of the Blockstates that can be replaced
     */
    public ArrayList<BlockState> getReplaceBlocks() {
        return replaceBlocks;
    }

    /**
     * returns the blocks on that the tree may generate
     * @return a ArrayList of the Blockstates on top of that the tree can be generated
     */
    public ArrayList<BlockState> getGroundBlocks() {
        return groundBlocks;
    }

    /**
     * returns the maximum height this tree may have
     * @return the maximum height
     */
    public int getTreeHeightMax() {
        return treeHeightMax;
    }

    public CrazyTree(){
        super();
    }

    public abstract Builder builder();

    public void placeObject(World world, Random random, Location location){
        this.placeObject(world, random, location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    //TODO: change to something better ;)
    @Override
    public String getId() {
        return "crazyTrees:CrazyTree";
    }

    @Override
    public String getName() {
        return "crazyTree";
    }

    /**
     * the Builder for CrazyTrees
     */
    public static abstract class Builder {
        private BlockState woodBlock = BlockState.builder().blockType(BlockTypes.LOG).build();
        private BlockState leaveBlock = BlockState.builder().blockType(BlockTypes.LEAVES).build();
        private boolean placeBlockUnderTree = true;
        private BlockState underTreeBlock = BlockState.builder().blockType(BlockTypes.DIRT).build();
        private ArrayList<BlockState> replaceBlocks = new ArrayList<>();
        private ArrayList<BlockState> groundBlocks = new ArrayList<>();
        private int treeHeightMax = 7;
        private int treeHeightMin = 4;

        public Builder woodBlock(BlockState woodBlock) {
            this.woodBlock = woodBlock;

            return this;
        }

        public Builder woodBlock(BlockType woodBlock) {
            this.woodBlock = woodBlock.getDefaultState();

            return this;
        }

        public Builder leaveBlock(BlockState leaveBlock) {
            this.leaveBlock = leaveBlock;

            return this;
        }

        public Builder leaveBlock(BlockType leaveBlock) {
            this.leaveBlock = leaveBlock.getDefaultState();

            return this;
        }

        public Builder underTreeBlock(BlockState underTreeBlock) {
            this.underTreeBlock = underTreeBlock;

            return this;
        }

        public Builder underTreeBlock(BlockType underTreeBlock) {
            this.underTreeBlock = underTreeBlock.getDefaultState();

            return this;
        }

        public Builder placeBlockUnderTree(boolean placeBlockUnderTree) {
            this.placeBlockUnderTree = placeBlockUnderTree;

            return this;
        }

        public Builder treeHeight(int treeHeightMax, int treeHeightMin) {
            this.treeHeightMax = treeHeightMax;
            this.treeHeightMin = treeHeightMin;

            return this;
        }

        /**
         * adds a BlockState that will be replaced by this Tree
         * @param replaceBlock the BlockState
         * @return self
         */
        public Builder replaceBlock(BlockState replaceBlock) {
            replaceBlocks.add(replaceBlock);

            return this;
        }

        /**
         * adds a BlockType that will be replaced by this Tree
         * @param replaceBlock the BlockType
         * @return self
         */
        public Builder replaceBlock(BlockType replaceBlock) {
            replaceBlocks.add(replaceBlock.getDefaultState());

            return this;
        }

        /**
         * adds a BlockState on top of that the tree will be generated
         * @param groundBlock the Blockstate
         * @return self
         */
        public Builder groundBlock(BlockState groundBlock) {
            groundBlocks.add(groundBlock);

            return this;
        }

        /**
         * adds a BlockType on top of that the tree will be generated
         * @param groundBlock the BlockType
         * @return self
         */
        public Builder groundBlock(BlockType groundBlock) {
            groundBlocks.add(groundBlock.getDefaultState());

            return this;
        }

        public abstract CrazyTree getTreeType();

        public CrazyTree build() {
            CrazyTree crazyTree = getTreeType();

            crazyTree.woodBlock = this.woodBlock;
            crazyTree.leaveBlock = this.leaveBlock;
            crazyTree.underTreeBlock = this.underTreeBlock;
            crazyTree.treeHeightMax = this.treeHeightMax;
            crazyTree.treeHeightMin = this.treeHeightMin;
            crazyTree.placeBlockUnderTree = this.placeBlockUnderTree;

            if(this.groundBlocks.size() == 0){
                this.groundBlocks.add(BlockState.builder().blockType(BlockTypes.DIRT).build());
                this.groundBlocks.add(BlockState.builder().blockType(BlockTypes.GRASS).build());
            }

            crazyTree.groundBlocks = this.groundBlocks;

            if(this.replaceBlocks.size() == 0){
                this.replaceBlocks.add(BlockState.builder().blockType(BlockTypes.AIR).build());
            }

            crazyTree.replaceBlocks = this.replaceBlocks;

            return crazyTree;
        }

    }
}