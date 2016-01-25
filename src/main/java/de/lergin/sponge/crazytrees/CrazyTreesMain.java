package de.lergin.sponge.crazytrees;

import static org.spongepowered.api.command.args.GenericArguments.onlyOne;
import static org.spongepowered.api.command.args.GenericArguments.playerOrSource;
import static org.spongepowered.api.command.args.GenericArguments.seq;
import static org.spongepowered.api.command.args.GenericArguments.world;

import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import de.lergin.sponge.crazytrees.commands.TestCommand;
import de.lergin.sponge.crazytrees.commands.TestDataAddCommand;
import de.lergin.sponge.crazytrees.commands.TestDataValidateCommand;
import de.lergin.sponge.crazytrees.commands.TestTpWorld;
import de.lergin.sponge.crazytrees.data.itemDrop.*;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import de.lergin.sponge.crazytrees.data.saplingData.*;
import de.lergin.sponge.crazytrees.util.ConfigHelper;
import de.lergin.sponge.crazytrees.util.TranslationHelper;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.data.type.DoublePlantTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.*;
import java.nio.file.Path;
import java.util.*;


@Plugin(id = "crazyTrees", name = "Crazy Trees", version = "0.1")
public class CrazyTreesMain {
    @Inject
    @ConfigDir(sharedRoot = false)
    public Path confDir;

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }


    private static CrazyTreesMain instance;

    public static CrazyTreesMain instance() {
        return instance;
    }

    public static final String DISPLAY_NAME_DATA_TRANSMISSION_KEY = UUID.randomUUID().toString();

    @Listener
    public void gameConstruct(GameConstructionEvent event) {
        instance = this;
    }

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
        ConfigHelper.loadConfig();

        //translation setup
        Locale.setDefault(
                Locale.forLanguageTag(
                        ConfigHelper.getNode("translation", "defaultLanguage").getString("en")
                )
        );

        logger.info(
                TranslationHelper.s(Locale.ENGLISH, "translation.default.set", Locale.getDefault().toLanguageTag())
        );


        final Locale logLanguage = Locale.forLanguageTag(
                ConfigHelper.getNode("translation", "logLanguage").getString("en")
        );

        TranslationHelper.setLogLanguage(logLanguage);
        logger.info(TranslationHelper.l("translation.log.set", logLanguage.toLanguageTag()));



        Sponge.getDataManager().register(SaplingData.class, ImmutableSaplingData.class,
                new SaplingDataManipulatorBuilder());
        Sponge.getDataManager().register(CrazyTreeTypeData.class, ImmutableCrazyTreeTypeData.class,
                new CrazyTreeTypeDataManipulatorBuilder());

        Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.OAK);
    }

    @Listener
    public void onGameInitialization(GameInitializationEvent event) {

        Sponge.getDataManager().register(ItemDropData.class, ImmutableItemDropData.class, new ItemDropDataManipulatorBuilder());
        Sponge.getDataManager().registerBuilder(ItemDrop.class, new ItemDropBuilder());



        Sponge.getRegistry().register(CatalogTypes.WORLD_GENERATOR_MODIFIER, new CrazyForestGeneratorModifier());

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Validates data"))
                        .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                        .executor(new TestDataValidateCommand())
                        .build(),
                "validateData");

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Validates data"))
                        .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                        .executor(new TestDataAddCommand())
                        .build(),
                "addData"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("just a test command"))
                        .executor(new TestCommand())
                        .build(),
                "test"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Teleports a player to another world"))
                        .arguments(seq(playerOrSource(Text.of("target")), onlyOne(world(Text.of("world")))))
                        .permission("worldstest.command.tpworld")
                        .executor(new TestTpWorld())
                        .build(),
                "tpworld"
        );
    }

    @Listener void onGameStopping(GameStoppingEvent event){
        ConfigHelper.saveConfig();
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event) {

        BlockSnapshot blockSnapshot = event.getTransactions().iterator().next().getOriginal();

        if (blockSnapshot.supports(Keys.TREE_TYPE)) {
            ItemStack rose = ItemTypes.DOUBLE_PLANT.getTemplate().createStack();
            rose.offer(Keys.DOUBLE_PLANT_TYPE, DoublePlantTypes.ROSE);
            try {
                DataContainer dataContainer = ItemStack.of(ItemTypes.DIAMOND, 2).toContainer();
                StringWriter writer = new StringWriter();
                HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setSink(() -> new BufferedWriter(writer)).build();

                loader.save(ConfigurateTranslator.instance().translateData(dataContainer));

                String toString = writer.toString();

                rose.offer(Keys.DISPLAY_NAME, Text.of(DISPLAY_NAME_DATA_TRANSMISSION_KEY + toString));
                Optional<Entity> optional = blockSnapshot.getLocation().get().getExtent().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
                if (optional.isPresent()) {
                    Item item = (Item) optional.get();
                    item.offer(Keys.REPRESENTED_ITEM, rose.createSnapshot());
                    blockSnapshot.getLocation().get().getExtent().spawnEntity(item, event.getCause());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Listener
    public void onTestEvent(DropItemEvent.Destruct event){
        for (Entity entity : event.getEntities()) {
            ItemStackSnapshot item = ((Item) entity).getItemData().item().get();

            Optional<Text> displayNameDataOptional = item.get(Keys.DISPLAY_NAME);

            if (displayNameDataOptional.isPresent()) {
                Text displayName = displayNameDataOptional.get();

                logger.info(displayName.toPlain());

                if (displayName.toPlain().startsWith(DISPLAY_NAME_DATA_TRANSMISSION_KEY)) {

                    String itemString = displayName.toPlain().substring(36);

                    logger.info(itemString);

                    try {
                        StringReader reader = new StringReader(itemString);

                        DataView view = ConfigurateTranslator.instance().translateFrom(HoconConfigurationLoader.builder().setSource(() -> new BufferedReader(reader)).build().load());

                        event.getCause().first(Player.class).get().setHelmet(ItemStack.builder().fromContainer(view).build());

                        event.setCancelled(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    CrazyTreeType crazyTreeBuilder = CrazyTreeType.HEKUR;

    @Listener
    public void onInteractBlockSecondary(InteractBlockEvent.Secondary event) {
        BlockSnapshot blockSnapshot = event.getTargetBlock();

        if(blockSnapshot.getState().getType() == BlockTypes.SAPLING){
            Optional<Player> playerOptional = event.getCause().first(Player.class);

            if(playerOptional.isPresent()){
                Player player = playerOptional.get();

                Optional<ItemStack> itemStackOptional = player.getItemInHand();

                if(itemStackOptional.isPresent()){
                    ItemStack itemStack = itemStackOptional.get();

                    ItemType itemType = itemStack.getItem();

                    itemStack.offer(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.DELNAS);

                    Optional<CrazyTreeType> optional = itemStack.get(SaplingKeys.CRAZY_TREE_TYPE);
                    if (optional.isPresent()) {
                        System.out.println(optional.get().toString());
                    }

                    System.out.println(itemStack.supports(SaplingKeys.CRAZY_TREE_LOG));

                    if(itemType == ItemTypes.NETHER_STAR){



/*                        System.out.println(player.getLocation().getTileEntity().get().supports(SaplingKeys.CRAZY_TREE_TYPE));

                       BlockState state = BlockTypes.SAPLING.getDefaultState();
                        CrazyTreeTypeData data = new CrazyTreeTypeDataManipulatorBuilder().create();
                        data.set(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.DELNAS);
                        System.out.println(state.with(data.asImmutable()).toString());

                        blockSnapshot.getLocation().get().setBlock(newState);

                        Optional<CrazyTreeType> optional = blockSnapshot.getState().get(SaplingKeys.CRAZY_TREE_TYPE);
                        if (optional.isPresent()) {
                            System.out.println(optional.get().toString());
                        }
*/
                        crazyTreeBuilder = CrazyTreeType.random();
                    }


                    Optional<BlockType> optionalItemStackBlockType = itemType.getBlock();

                    if(optionalItemStackBlockType.isPresent()){
                        itemStack.setQuantity(itemStack.getQuantity() - 1);

                        ParticleEffect particleEffect = ParticleEffect.builder().type(ParticleTypes.SMOKE_NORMAL).count(1).motion(Vector3d.ZERO).build();

                        player.spawnParticles(particleEffect, blockSnapshot.getLocation().get().getPosition().add(0.5,0.5,0.5));



                        BlockState blockState = optionalItemStackBlockType.get().getDefaultState();

                        /*
                        todo: uncomment when implemented (itemStack.getValues())

                        Iterator<ImmutableValue<?>> itemStackDataValues = itemStack.getValues().iterator();

                        while (itemStackDataValues.hasNext()){
                            ImmutableValue<?> immutableValue = itemStackDataValues.next();

                            if(blockState.supports(immutableValue)){
                                blockState.with(immutableValue);
                            }
                        }
                        */


                        //destroy sapling
                        blockSnapshot.getLocation().get().setBlockType(BlockTypes.AIR);

                        //create a tree
                        CrazyTree hekurTree = crazyTreeBuilder.getBuilder().woodBlock(blockState).leaveBlock(BlockTypes.LEAVES).replaceBlock(BlockTypes.AIR).treeHeight(14,4).build();

                        //generate it at the position of the sapling
                        hekurTree.placeObject(player.getWorld(), new Random(), blockSnapshot.getLocation().get());

                        //reduce itemAmount in inventory
                        player.setItemInHand(itemStack);

                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @Listener
    public void onChangeBlockEventPlace(ChangeBlockEvent.Place event) {

      /*  BlockSnapshot blockSnapshot = event.getTransactions().iterator().next().getOriginal();
        System.out.println(blockSnapshot.getState().supports(SaplingKeys.CRAZY_TREE_TYPE));

        BlockState state = BlockTypes.SAPLING.getDefaultState();
        CrazyTreeTypeData data = new CrazyTreeTypeDataManipulatorBuilder().create();
        data.set(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.DELNAS);

        System.out.println(state.with(data.asImmutable()).toString());

        BlockState newState = state.with(data.asImmutable()).get();

        blockSnapshot.getLocation().get().setBlock(newState);

        Optional<CrazyTreeType> optional = blockSnapshot.getState().get(SaplingKeys.CRAZY_TREE_TYPE);
        if (optional.isPresent()) {
            System.out.println(optional.get().toString());
        }*/
    }
}
