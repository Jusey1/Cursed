package net.salju.curse.events;

import net.salju.curse.init.CursedTags;
import net.salju.curse.init.CursedConfig;
import net.salju.curse.gui.CurseGuiMenu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.world.entity.TamableAnimal;

@Mod.EventBusSubscriber
public class CurseEvents {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onHurt(LivingHurtEvent event) {
		if (event.getEntity() instanceof Player player && CurseManager.isCursed(player)) {
			event.setAmount(event.getAmount() * ((float) CursedConfig.DEATH.get() / 100));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onKnockKnock(LivingKnockBackEvent event) {
		if (event.getEntity() instanceof Player player && CurseManager.isCursed(player)) {
			event.setStrength(event.getStrength() * ((float) CursedConfig.KNOCK.get() / 100));
		}
	}

	@SubscribeEvent
	public static void onLivingDropXp(LivingExperienceDropEvent event) {
		if (event.getAttackingPlayer() != null) {
			if (CurseManager.isCursed(event.getAttackingPlayer())) {
				event.setDroppedExperience(event.getDroppedExperience() * (CursedConfig.EXP.get() / 100));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event) {
		if (event.getSource().getEntity() instanceof Player player) {
			int loot = event.getLootingLevel();
			if (CurseManager.isCursed(player) && CursedConfig.DROPS.get()) {
				ItemStack stack = new ItemStack(CurseManager.getItem(event.getEntity().getType()));
				if (player.level() instanceof ServerLevel lvl && Math.random() >= 0.85) {
					if (stack.is(CursedTags.RARES)) {
						if (Math.random() >= 0.85) {
							ItemEntity item = new ItemEntity(lvl, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
							item.setPickUpDelay(10);
							lvl.addFreshEntity(item);
						}
					} else {
						for (int i = 0; i < (int) (Mth.nextInt(RandomSource.create(), (0 + loot), (2 + loot))); i++) {
							ItemEntity item = new ItemEntity(lvl, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);
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
			if (CurseManager.isCursed(player)) {
				if (!player.level().isClientSide() && !player.isCreative() && !player.isSpectator()) {
					if (player.getRemainingFireTicks() == 1 && CursedConfig.FIRE.get()) {
						player.setSecondsOnFire(120);
					}
					if (CursedConfig.ANGRY.get()) {
						for (Mob angry : player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(28.0D))) {
							if (angry instanceof NeutralMob) {
								if ((angry instanceof IronGolem golem && golem.isPlayerCreated()) || (angry instanceof TamableAnimal pet && pet.isTame()) || angry.getType().is(CursedTags.ANGRY)) {
									continue;
								} else if (angry.getTarget() == null) {
									if (player.hasLineOfSight(angry) || player.distanceTo(angry) <= 4.0D) {
										angry.setTarget(player);
									}
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
	public static void onPlayerClone(PlayerEvent.Clone event) {
		if (CurseManager.isCursed(event.getOriginal())) {
			CurseManager.setCursed(event.getEntity(), true);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer ply && !CurseManager.isCursed(ply)) {
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
}