package de.lergin.sponge.crazytrees;

import static org.spongepowered.api.command.args.GenericArguments.onlyOne;
import static org.spongepowered.api.command.args.GenericArguments.playerOrSource;
import static org.spongepowered.api.command.args.GenericArguments.seq;
import static org.spongepowered.api.command.args.GenericArguments.world;

import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import de.lergin.sponge.crazytrees.data.saplingData.*;
import de.lergin.sponge.crazytrees.util.ConfigHelper;
import de.lergin.sponge.crazytrees.util.TranslationHelper;
import org.slf4j.Logger;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;


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
                        ConfigHelper.getNode("translation", "defaultLanguage").getString("EN")
                )
        );
        logger.info(TranslationHelper.s(Locale.ENGLISH, "translation.default.set", Locale.getDefault().toLanguageTag()));


        final Locale logLanguage = Locale.forLanguageTag(
                ConfigHelper.getNode("translation", "logLanguage").getString("EN")
        );

        TranslationHelper.setLogLanguage(logLanguage);
        logger.info(TranslationHelper.l("translation.log.set", logLanguage.toLanguageTag()));



        Sponge.getDataManager().register(SaplingData.class, ImmutableSaplingData.class, new SaplingDataManipulatorBuilder());
        Sponge.getDataManager().register(CrazyTreeTypeData.class, ImmutableCrazyTreeTypeData.class,
                new CrazyTreeTypeDataManipulatorBuilder());

        Sponge.getRegistry().getValueFactory().createValue(SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.OAK);





        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Teleports a player to another world"))
                        .arguments(seq(playerOrSource(Text.of("target")), onlyOne(world(Text.of("world")))))
                        .permission("worldstest.command.tpworld")
                        .executor((src, args) -> {
                            final Optional<WorldProperties> optWorldProperties = args.getOne("world");
                            final Optional<World> optWorld = Sponge.getServer().getWorld(optWorldProperties.get().getWorldName());
                            if (!optWorld.isPresent()) {
                                throw new CommandException(Text.of("World [", Text.of(TextColors.AQUA, optWorldProperties.get().getWorldName()),
                                        "] "
                                                + "was not found."));
                            }
                            for (Player target : args.<Player>getAll("target")) {
                                target.setLocation(new Location<>(optWorld.get(), optWorld.get().getProperties()
                                        .getSpawnPosition()));
                            }
                            return CommandResult.success();
                        })
                        .build(),
                "tpworld"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("just a test command"))
                        .permission("worldstest.command.test")
                        .executor(new test_command())
                        .build(),
                "test"
        );
    }

    @Listener
    public void onGameInitialization(GameInitializationEvent event) {
        Sponge.getGame().getRegistry().register(CatalogTypes.WORLD_GENERATOR_MODIFIER, new CrazyForestGeneratorModifier());

        CommandSpec skillDataSpec = CommandSpec.builder()
                .description(Text.of("Applies skill data"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.enumValue(Text.of("amount"), CrazyTreeType.class))))
                .executor(new SkillDataExecturo())
                .build();
        CommandSpec skillValidate = CommandSpec.builder()
                .description(Text.of("Validates skill data"))
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new SkillValidator())
                .build();

        Sponge.getGame().getCommandManager().register(this, skillValidate, "validateData");
        Sponge.getGame().getCommandManager().register(this, skillDataSpec, "fakeData");


    }

    @Listener void onGameStopping(GameStoppingEvent event){
        ConfigHelper.saveConfig();
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event) {

       /* BlockSnapshot blockSnapshot = event.getTransactions().iterator().next().getOriginal();

        if (blockSnapshot.supports(Keys.TREE_TYPE)) {
            ItemStack rose = ItemTypes.DOUBLE_PLANT.getTemplate().createStack();
            rose.offer(Keys.DOUBLE_PLANT_TYPE, DoublePlantTypes.ROSE);
            Optional<Entity> optional = blockSnapshot.getLocation().get().getExtent().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
            if (optional.isPresent()) {
                Item item = (Item) optional.get();
                item.offer(Keys.REPRESENTED_ITEM, rose.createSnapshot());
                blockSnapshot.getLocation().get().getExtent().spawnEntity(item, event.getCause());
            }


            Optional<TreeType> treeTypeOptional = blockSnapshot.getState().get(Keys.TREE_TYPE);
            if (treeTypeOptional.isPresent() && treeTypeOptional.get().equals(TreeTypes.BIRCH)) {


                ItemStack apple = ItemTypes.APPLE.getTemplate().createStack();
                Optional<Entity> optionalApple = blockSnapshot.getLocation().get().getExtent().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
                if (optionalApple.isPresent()) {
                    Item item = (Item) optionalApple.get();
                    item.offer(Keys.REPRESENTED_ITEM, apple.createSnapshot());
                    blockSnapshot.getLocation().get().getExtent().spawnEntity(item, event.getCause());
                }
            }
        }
    */

    }

    CrazyTreeType crazyTreeBuilder = CrazyTreeType.HEKUR;

    @Listener
    public void onItemDrop(InteractBlockEvent.Secondary event) {
        BlockSnapshot blockSnapshot = event.getTargetBlock();

        if(blockSnapshot.getState().getType() == BlockTypes.SAPLING){
            Optional<Player> playerOptional = event.getCause().first(Player.class);

            if(playerOptional.isPresent()){
                Player player = playerOptional.get();

                player.sendMessage(TranslationHelper.p(player, "test"));

                logger.info(TranslationHelper.s("test"));

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
    public void onItemDrops(ChangeBlockEvent.Place event) {

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

    public static class SkillDataExecturo implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

            Optional<Player> target = args.getOne("player");
            Optional<CrazyTreeType> integer = args.getOne("amount");
            if (target.isPresent()) {
                Player player = target.get();
                player.offer(new SaplingData(BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState()));
                player.offer(new CrazyTreeTypeData(CrazyTreeType.DELNAS));
            } else {
                if (src instanceof Player && integer.isPresent()) {
                    Player player = (Player) src;
                    player.offer(new SaplingData( BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState()));
                    player.offer(new CrazyTreeTypeData(integer.get()));
                }
            }
            return CommandResult.success();
        }
    }

    public static class SkillValidator implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            Optional<Player> target = args.getOne("player");
            if (target.isPresent()) {
                Player player = target.get();
                Optional<SaplingData> optional = player.get(SaplingData.class);
                if (optional.isPresent()) {
                    src.sendMessage(Text.of("Data available!"));
                    System.out.println(optional.get().toString());
                }
            } else {
                if (src instanceof Player) {
                    Player player = (Player) src;
                    Optional<CrazyTreeTypeData> optional = player.get(CrazyTreeTypeData.class);
                    Optional<SaplingData> optional2 = player.get(SaplingData.class);
                    if (optional.isPresent() && optional2.isPresent()) {
                        src.sendMessage(Text.of("Data available!"));
                        System.out.println(optional.get().toString());
                        System.out.println(optional2.get().toString());
                    }
                }
            }
            return CommandResult.success();
        }
    }
}
