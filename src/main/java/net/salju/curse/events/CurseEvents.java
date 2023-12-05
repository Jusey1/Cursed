package net.salju.curse.events;

import net.salju.curse.init.CursedTags;
import net.salju.curse.init.CursedConfig;
import net.salju.curse.gui.CurseGuiMenu;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;

@Mod.EventBusSubscriber
public class CurseEvents {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onHurt(LivingHurtEvent event) {
		float d = event.getAmount();
		if (event.getEntity() != null) {
			LivingEntity target = event.getEntity();
			if (target instanceof Player player && CurseHelpers.isCursed(player)) {
				float c = ((float) CursedConfig.DEATH.get() / 100);
				event.setAmount(d * c);
			}
		}
		if (event.getSource().getEntity() != null) {
			Entity source = event.getSource().getEntity();
			if (source instanceof Player player && CurseHelpers.isCursed(player)) {
				float c = ((float) CursedConfig.WEAK.get() / 100);
				event.setAmount(d * c);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onKnockKnock(LivingKnockBackEvent event) {
		if (event.getEntity() != null) {
			LivingEntity target = event.getEntity();
			if (target instanceof Player player && CurseHelpers.isCursed(player)) {
				float d = event.getStrength();
				float c = ((float) CursedConfig.KNOCK.get() / 100);
				event.setStrength(d * c);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDropXp(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() != null && event.getEntity() != null) {
			Player player = event.getAttackingPlayer();
			if (CurseHelpers.isCursed(player)) {
				int x = event.getDroppedExperience();
				int c = (CursedConfig.EXP.get() / 100);
				event.setDroppedExperience(x * c);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event) {
		if (event.getEntity() != null && event.getSource() != null && event.getSource().getEntity() != null) {
			LivingEntity target = event.getEntity();
			double x = target.getX();
			double y = target.getY();
			double z = target.getZ();
			int loot = event.getLootingLevel();
			if (event.getSource().getEntity() instanceof Player player && CurseHelpers.isCursed(player) && CursedConfig.DROPS.get()) {
				if (player.level() instanceof ServerLevel lvl && Math.random() >= 0.85) {
					if (target instanceof ZombifiedPiglin || target instanceof AbstractPiglin) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.GOLD_INGOT));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Drowned || target instanceof Guardian) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.LAPIS_LAZULI));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Husk) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.SAND));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Zombie) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.SLIME_BALL));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Witch) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.PHANTOM_MEMBRANE));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof SpellcasterIllager) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), 0, 1)); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.DIAMOND));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof AbstractIllager) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.EMERALD));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof WitherSkeleton && (Math.random() >= (0.9 - (0.05 * loot)))) {
						ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.NETHERITE_SCRAP));
						item.setPickUpDelay(10);
						lvl.addFreshEntity(item);
					} else if (target instanceof EnderMan) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), 0, (1 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.ENDER_EYE));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Skeleton) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (1 + loot), (5 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.ARROW));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Creeper) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (1 + loot), (5 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.GUNPOWDER));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof MagmaCube || target instanceof Vex) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.GLOWSTONE_DUST));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Blaze) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), 0, 2)); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.BLAZE_ROD));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else if (target instanceof Spider) {
						for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), 0, 2)); index0++) {
							ItemEntity item = new ItemEntity(lvl, x, y, z, new ItemStack(Items.FERMENTED_SPIDER_EYE));
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Player player = event.player;
			if (CurseHelpers.isCursed(player)) {
				if (!player.level().isClientSide() && !player.isCreative() && !player.isSpectator()) {
					if (player.getRemainingFireTicks() == 1 && CursedConfig.FIRE.get()) {
						player.setSecondsOnFire(120);
					}
					if (CursedConfig.ANGRY.get()) {
						for (Mob angry : player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(28.0D))) {
							if ((angry instanceof IronGolem golem && golem.isPlayerCreated()) || angry.getType().is(CursedTags.ANGRY) || (player.level().dimension() == Level.END && !CursedConfig.END.get())) {
								continue;
							}
							if (!(angry instanceof Animal) && angry.getTarget() == null && (angry instanceof NeutralMob || angry instanceof AbstractPiglin)) {
								if (player.hasLineOfSight(angry) || player.distanceTo(angry) <= 4.0D) {
									angry.setTarget(player);
								}
							}
						}
					}
				}
				if (player.isSleeping() && player.getSleepTimer() >= 95 && CursedConfig.SLEEP.get()) {
					player.stopSleeping();
					player.displayClientMessage(Component.translatable("gui.curse.sleep_message"), true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLooter(LootingLevelEvent event) {
		if (event.getDamageSource() != null && event.getDamageSource().getEntity() != null) {
			if (event.getDamageSource().getEntity() instanceof Player player && CurseHelpers.isCursed(player) && CursedConfig.LOOT.get()) {
				event.setLootingLevel(event.getLootingLevel() + 1);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		if (CurseHelpers.isCursed(event.getOriginal())) {
			CurseHelpers.setCursed(event.getEntity(), true);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		Player player = event.getEntity();
		if (player instanceof ServerPlayer ply && !CurseHelpers.isCursed(ply)) {
			if (!ply.getAdvancements().getOrStartProgress(ply.server.getAdvancements().getAdvancement(new ResourceLocation("minecraft:story/root"))).isDone()) {
				ply.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return CommonComponents.EMPTY;
					}

					@Override
					public AbstractContainerMenu createMenu(int i, Inventory inv, Player player) {
						return new CurseGuiMenu(i, inv, null);
					}
				});
			}
		}
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockState state = event.getState();
		if (CurseHelpers.isCursed(player) && !player.isCreative() && CursedConfig.ORE.get()) {
			int fort = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
			if (!(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) != 0)) {
				if (event.getLevel() instanceof ServerLevel lvl && state.is(CursedTags.BONUS)) {
					for (int i = 0; i < Mth.nextInt(lvl.getRandom(), 0, 2); i++) {
						if (Math.random() >= 0.65 - (0.05 * fort)) {
							state.getBlock().dropResources(state, lvl, event.getPos(), null, player, player.getMainHandItem());
						}
					}
				}
			}
		}
	}
}