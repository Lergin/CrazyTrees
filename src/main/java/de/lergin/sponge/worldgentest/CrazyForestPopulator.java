package de.lergin.sponge.worldgentest;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.mutable.ColoredData;
import org.spongepowered.api.data.manipulator.mutable.WetData;
import org.spongepowered.api.data.manipulator.mutable.block.TreeData;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.gen.PopulatorObject;
import org.spongepowered.api.world.gen.PopulatorObjects;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Malte on 07.01.2016.
 */
public class CrazyForestPopulator implements PopulatorObject{


    @Inject
    Logger logger;

    @Override
    public boolean canPlaceAt(World world, int i, int i1, int i2) {
        BlockType replace = BlockTypes.WATER;

        for(int j = 0; j < 8; j++){
            if(
                    world.getBlockType(i,i1+j,i2) != replace ||
                    world.getBlockType(i+1,i1+j,i2) != replace ||
                    world.getBlockType(i-1,i1+j,i2) != replace ||
                    world.getBlockType(i,i1+j,i2+1) != replace ||
                    world.getBlockType(i,i1+j,i2-1) != replace
                    ){
                return false;
            }
        }

        return true;

    }

    @Override
    public void placeObject(World world, Random random, int x, int y, int z) {

    }

    @Override
    public String getId() {
        return "crazyForest:test";
    }

    @Override
    public String getName() {
        return "Crazy Forest";
    }
}
