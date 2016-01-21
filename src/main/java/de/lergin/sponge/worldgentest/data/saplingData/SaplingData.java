package de.lergin.sponge.worldgentest.data.saplingData;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class SaplingData extends AbstractData<SaplingData, ImmutableSaplingData> {
  //  private CrazyTreeType treeType;
    private BlockState log;
    private BlockState leave;

    public SaplingData() {
        this(/*CrazyTreeType.OAK, */BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState());
    }

    public SaplingData(/*CrazyTreeType treeType,*/ BlockState log, BlockState leave) {
    //    this.treeType = treeType;
        this.log = log;
        this.leave = leave;
    }

/*    public Value<CrazyTreeType> crazyTreeType() {
        return Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.OAK, this.treeType);
    }
*/
    public Value<BlockState> log() {
        return Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_LOG, BlockTypes.LOG.getDefaultState(), this.log);
    }

    public Value<BlockState> leave() {
        return Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_LEAVE, BlockTypes.LEAVES.getDefaultState(), this.leave);
    }

    @Override
    protected void registerGettersAndSetters() {
       /* registerFieldGetter(SaplingKeys.CRAZY_TREE_TYPE, () -> this.treeType);
        registerFieldSetter(SaplingKeys.CRAZY_TREE_TYPE, value -> this.treeType = value);
        registerKeyValue(SaplingKeys.CRAZY_TREE_TYPE, this::crazyTreeType);
*/
        registerFieldGetter(SaplingKeys.CRAZY_TREE_LEAVE, () -> this.leave);
        registerFieldSetter(SaplingKeys.CRAZY_TREE_LEAVE, value -> this.leave = value);
        registerKeyValue(SaplingKeys.CRAZY_TREE_LEAVE, this::leave);

        registerFieldGetter(SaplingKeys.CRAZY_TREE_LOG, () -> this.log);
        registerFieldSetter(SaplingKeys.CRAZY_TREE_LOG, value -> this.log = checkNotNull(value));
        registerKeyValue(SaplingKeys.CRAZY_TREE_LOG, this::log);

    }

    @Override
    public Optional<SaplingData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return Optional.empty();
    }

    @Override
    public Optional<SaplingData> from(DataContainer container) {
        if (!container.contains(SaplingKeys.CRAZY_TREE_LOG.getQuery(),/* SaplingKeys.CRAZY_TREE_TYPE.getQuery(),*/ SaplingKeys.CRAZY_TREE_LEAVE.getQuery())) {
            return Optional.empty();
        }
       // final CrazyTreeType treeType = (CrazyTreeType) container.get(SaplingKeys.CRAZY_TREE_TYPE.getQuery()).get();
        final BlockState log = (BlockState) container.get(SaplingKeys.CRAZY_TREE_LOG.getQuery()).get();
        final BlockState leave = (BlockState) container.get(SaplingKeys.CRAZY_TREE_LOG.getQuery()).get();
        this.log = log;
      //  this.treeType = treeType;
        this.leave = leave;
        return Optional.of(this);
    }

    @Override
    public SaplingData copy() {
        return new SaplingData(/*this.treeType,*/ this.log, this.leave);
    }

    @Override
    public ImmutableSaplingData asImmutable() {
        return new ImmutableSaplingData(/*this.treeType,*/ this.log, this.leave);
    }

    @Override
    public int compareTo(SaplingData o) {
        return 0;//(this.leave == o.leave && this.log == o.log && this.treeType == o.treeType)?0:1;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(SaplingKeys.CRAZY_TREE_LOG, this.log)
             //   .set(SaplingKeys.CRAZY_TREE_TYPE, this.treeType)
                .set(SaplingKeys.CRAZY_TREE_LEAVE, this.leave);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
               // .add("treeType", this.treeType)
                .add("log", this.log)
                .add("leave", this.leave)
                .toString();
    }
}
