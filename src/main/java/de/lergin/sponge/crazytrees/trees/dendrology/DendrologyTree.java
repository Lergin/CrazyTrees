package de.lergin.sponge.crazytrees.trees.dendrology;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * making it easier to implement tree generators from MinecraftModArchive/Dendrology
 */
public abstract class DendrologyTree extends CrazyTree {
    public Direction logDirection = Direction.UP;

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return canPlaceAt(world, x, y, z, this.getTreeHeightMin()) && (this.getGroundBlocks().contains(world.getBlock(x, y - 1, z)) || this.getGroundBlocks().isEmpty());
    }

    public boolean canPlaceAt(World world, int x, int y, int z, int height) {
        return height > 0 &&
                !(y < 1 || y + height + 1 > 256) &&
                hasRoomToGrow(world, x, y, z, height);

    }

    public boolean hasRoomToGrow(World world, int x, int y, int z, int height) {
        for (int y1 = y; y1 <= y + 1 + height; ++y1)
            if (!this.getReplaceBlocks().contains(world.getBlock(x, y1, z)) && !this.getReplaceBlocks().isEmpty()) return false;

        return true;
    }

    public void placeLog(World world, int x, int y, int z) {
        final Location<World> loc = world.getLocation(x, y, z);
        BlockState block = this.getWoodBlock();

        if (block.supports(Keys.DIRECTION))
            block = block.with(Keys.DIRECTION, logDirection).get();

        if (canBeReplacedByLog(loc))
            loc.setBlock(block);
    }

    public boolean canBeReplacedByLog(Location<World> loc) {
        return this.getReplaceBlocks().contains(loc.getBlock()) || this.getReplaceBlocks().isEmpty() || loc.getBlock() == this.getLeaveBlock();
    }

    public void placeLeaves(World world, int x, int y, int z) {
        if (this.getReplaceBlocks().contains(world.getBlock(x, y, z)) || this.getReplaceBlocks().isEmpty()
                && world.getBlock(x,y,z) != this.getWoodBlock())
            world.getLocation(x, y, z).setBlock(this.getLeaveBlock());
    }

    public void placeBlockUnderTree(World world, int x, int y, int z){
        if(this.isPlaceBlockUnderTree()) {
            final Location block = world.getLocation(x, y, z);
            block.add(0, -1, 0).setBlock(this.getUnderTreeBlock());
        }
    }
}
