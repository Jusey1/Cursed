package net.salju.curse.events;

import net.salju.curse.init.*;
import net.salju.curse.item.component.CursedItem;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.TamableAnimal;

@EventBusSubscriber
public class CurseEvents {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onHurt(LivingIncomingDamageEvent event) {
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
						for (int i = 0; i < Mth.nextInt(player.getRandom(), 1, 2); i++) {
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
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		Player player = event.getEntity();
		if (CurseManager.isCursed(player)) {
			if (!player.level().isClientSide() && !player.isCreative() && !player.isSpectator()) {
				if (player.getRemainingFireTicks() == 1 && CursedConfig.FIRE.get()) {
					player.setRemainingFireTicks(120);
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

	@SubscribeEvent
	public static void onPlayerDone(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntity() instanceof Player player) {
			CursedItem data = event.getItem().get(CursedData.CURSED);
			if (data != null) {
				CurseManager.setCursed(player, data.getCursedStatus());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!CurseManager.isCursed(event.getEntity())) {
			if (CursedConfig.ALWAYS.get()) {
				CurseManager.setCursed(event.getEntity(), true);
			} else if (CursedConfig.APPLE.get()) {
				CompoundTag data = event.getEntity().getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
				if (!data.getBoolean("cursed_has_joined")) {
					ItemStack apple = new ItemStack(Items.GOLDEN_APPLE);
					apple.set(CursedData.CURSED, new CursedItem(true));
					ItemHandlerHelper.giveItemToPlayer(event.getEntity(), apple);
					data.putBoolean("cursed_has_joined", true);
					event.getEntity().getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
				}
			}
		}
	}
}