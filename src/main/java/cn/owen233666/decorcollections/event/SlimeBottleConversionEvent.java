package cn.owen233666.decorcollections.event;

import cn.owen233666.decorcollections.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Random;

@EventBusSubscriber(modid = "decorcollections")
public class SlimeBottleConversionEvent {

    private static final Random random = new Random();

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        Level level = player.level();

        if (hand == InteractionHand.MAIN_HAND &&
                mainHandItem.is(Items.GLASS_BOTTLE) &&
                offHandItem.is(Items.NETHER_STAR) &&
                event.getTarget().getType() == EntityType.SLIME) {
            Slime slime = (Slime) event.getTarget();
            if (slime.getSize() == 1) {
                DamageSource damageSource = new DamageSources(player.registryAccess()).playerAttack(player);
                if (random.nextInt(4) == 0) {
                    offHandItem.shrink(1);
                    mainHandItem.shrink(1);
                    ItemStack slimeBottleStack = new ItemStack(ModBlocks.SLIMEBOTTLE.get().asItem());
                    if (!player.getInventory().add(slimeBottleStack)) {
                        player.drop(slimeBottleStack, false);
                    }
                    slime.remove(Entity.RemovalReason.DISCARDED);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                } else {
                    mainHandItem.shrink(1);
                    ItemStack newBottleStack = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.getInventory().add(newBottleStack)) {
                        player.drop(newBottleStack, false);
                    }
                    offHandItem.shrink(1);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                }
            }else{
                player.displayClientMessage(Component.translatable("message.decorcollections.slime_too_big"), true);
            }
        }
    }
}