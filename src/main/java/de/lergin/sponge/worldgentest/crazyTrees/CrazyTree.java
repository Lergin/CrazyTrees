package de.lergin.sponge.worldgentest.crazyTrees;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.gen.PopulatorObject;

import java.util.ArrayList;

public abstract class CrazyTree implements PopulatorObject {
    BlockState woodBlock;
    BlockState leaveBlock;
    boolean placeBlockUnderTree;
    BlockState underTreeBlock;
    ArrayList<BlockState> replaceBlocks;
    ArrayList<BlockState> groundBlocks;
    int treeHeightMax;
    int treeHeightMin;
    TreeGenerator treeGenerator;


    public CrazyTree(){
        super();
    }

    public static Builder builder(){
        return new Builder();
    }

    //TODO: change to something better ;)
    @Override
    public String getId() {
        return "crazyForest:test";
    }

    @Override
    public String getName() {
        return "crazyTree";
    }

   /* @Override
    public boolean canPlaceAt(World world, int i, int i1, int i2) {
        return treeGenerator.canPlaceAt(world, i, i1, i2, treeHeightMax, treeHeightMin, replaceBlocks, groundBlocks);
    }

    @Override
    public void placeObject(World world, Random random, int i, int i1, int i2) {
        treeGenerator.placeObject(world, random, i, i1, i2, woodBlock, leaveBlock, underTreeBlock, treeHeightMax,
                treeHeightMin, placeBlockUnderTree, replaceBlocks);
    }*/

    public static class Builder {
        private BlockState woodBlock = BlockState.builder().blockType(BlockTypes.LOG).build();
        private BlockState leaveBlock = BlockState.builder().blockType(BlockTypes.LEAVES).build();
        private boolean placeBlockUnderTree = true;
        private BlockState underTreeBlock = BlockState.builder().blockType(BlockTypes.DIRT).build();
        private ArrayList<BlockState> replaceBlocks = new ArrayList<>();
        private ArrayList<BlockState> groundBlocks = new ArrayList<>();
        private int treeHeightMax = 7;
        private int treeHeightMin = 4;
        private TreeGenerator treeGenerator = TreeGenerator.OAK;


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

        public Builder treeType(TreeGenerator treeGenerator) {
            this.treeGenerator = treeGenerator;

            return this;
        }

        public CrazyTree build() {
            CrazyTree crazyTree = new EwcalyTree();

            crazyTree.woodBlock = this.woodBlock;
            crazyTree.leaveBlock = this.leaveBlock;
            crazyTree.underTreeBlock = this.underTreeBlock;
            crazyTree.treeHeightMax = this.treeHeightMax;
            crazyTree.treeHeightMin = this.treeHeightMin;
            crazyTree.placeBlockUnderTree = this.placeBlockUnderTree;
            crazyTree.treeGenerator = this.treeGenerator;

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
