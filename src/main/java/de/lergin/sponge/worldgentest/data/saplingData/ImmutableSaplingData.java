package de.lergin.sponge.worldgentest.data.saplingData;

import com.google.common.collect.ComparisonChain;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Optional;

public class ImmutableSaplingData extends AbstractImmutableData<ImmutableSaplingData, SaplingData> {

    private final BlockState log;
    private final BlockState leave;
    private final BlockStateComparator blockStateComparator = new BlockStateComparator();

    public ImmutableSaplingData() {
        this(BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState());
    }

    public ImmutableSaplingData(BlockState log, BlockState leave) {
        this.log = log;
        this.leave = leave;
    }

    public ImmutableValue<BlockState> log() {
        return Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_LOG, BlockTypes.LOG.getDefaultState(), this.log).asImmutable();
    }

    public ImmutableValue<BlockState> leave() {
        return Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_LEAVE, BlockTypes.LEAVES.getDefaultState(), this.leave).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(SaplingKeys.CRAZY_TREE_LEAVE, this::getLeave);
        registerKeyValue(SaplingKeys.CRAZY_TREE_LEAVE, this::leave);

        registerFieldGetter(SaplingKeys.CRAZY_TREE_LOG, this::getLog);
        registerKeyValue(SaplingKeys.CRAZY_TREE_LOG, this::log);
    }

    @Override
    public <E> Optional<ImmutableSaplingData> with(Key<? extends BaseValue<E>> key, E value) {
        return Optional.empty();
    }

    @Override
    public SaplingData asMutable() {
        return new SaplingData(this.log, this.leave);
    }

    @Override
    public int compareTo(ImmutableSaplingData o) {
        return ComparisonChain.start()
                .compare(o.leave, this.leave, blockStateComparator)
                .compare(o.log, this.log, blockStateComparator)
                .result();
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(SaplingKeys.CRAZY_TREE_LOG, this.log)
                .set(SaplingKeys.CRAZY_TREE_LEAVE, this.leave);
    }

    private BlockState getLog() {
        return this.log;
    }


    private BlockState getLeave() {
        return this.leave;
    }

}
