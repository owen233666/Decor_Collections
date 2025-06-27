package cn.owen233666.decorcollections.block;

import cn.owen233666.decorcollections.DecorCollections;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;

import java.util.List;


public class SlimeBottleBlockItem extends BlockItem {
    private final boolean wearable; // 是否可穿戴标记

    // 定义属性修饰符的唯一ID
    private static final ResourceLocation ARMOR_MODIFIER_ID = ResourceLocation.tryBuild(DecorCollections.MOD_ID, "slime_bottle_armor");
    private static final ResourceLocation HEALTH_MODIFIER_ID = ResourceLocation.tryBuild(DecorCollections.MOD_ID, "slime_bottle_health");

    // 定义属性加成值
    private static final double ARMOR_BONUS = 2.0; // +2盔甲值
    private static final double HEALTH_BONUS = 4.0; // +2最大生命值

    public SlimeBottleBlockItem(Block block, Item.Properties properties) {
        this(block, properties, true); // 默认可穿戴
    }

    public SlimeBottleBlockItem(Block block, Item.Properties properties, boolean wearable) {
        super(block, properties.stacksTo(1)); // 可穿戴物品通常堆叠为1
        this.wearable = wearable;
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return wearable ? EquipmentSlot.HEAD : super.getEquipmentSlot(stack);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, LivingEntity entity) {
        return wearable ? slot == EquipmentSlot.HEAD : super.canEquip(stack, slot, entity);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();

        if (player != null) {
            if (player.isShiftKeyDown()) {
                return super.useOn(context);
            }
            ItemStack helmetSlot = player.getItemBySlot(EquipmentSlot.HEAD);
            if (helmetSlot.isEmpty()) {
                player.setItemSlot(EquipmentSlot.HEAD, context.getItemInHand().copy());
                context.getItemInHand().setCount(0);
                level.playSound(
                        null,                  // 位置跟踪器（null表示使用玩家位置）
                        player.getX(),         // X坐标
                        player.getY(),         // Y坐标
                        player.getZ(),         // Z坐标
                        SoundEvents.SLIME_JUMP, // 声音事件
                        SoundSource.PLAYERS,   // 声音源（玩家类别）
                        1.0F,                  // 音量
                        1.0F                   // 音高
                );
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                ItemStack oldHelmet = helmetSlot.copy();
                player.setItemSlot(EquipmentSlot.HEAD, context.getItemInHand().copy());
                player.getInventory().setItem(player.getInventory().selected, oldHelmet);
                level.playSound(
                        null,                  // 位置跟踪器（null表示使用玩家位置）
                        player.getX(),         // X坐标
                        player.getY(),         // Y坐标
                        player.getZ(),         // Z坐标
                        SoundEvents.SLIME_JUMP, // 声音事件
                        SoundSource.PLAYERS,   // 声音源（玩家类别）
                        1.0F,                  // 音量
                        1.0F                   // 音高
                );
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

        }

        // 默认执行常规放置逻辑
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        ItemStack helmetSlot = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmetSlot.isEmpty()) {
            player.setItemSlot(EquipmentSlot.HEAD, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
            level.playSound(
                    null,                  // 位置跟踪器（null表示使用玩家位置）
                    player.getX(),         // X坐标
                    player.getY(),         // Y坐标
                    player.getZ(),         // Z坐标
                    SoundEvents.SLIME_JUMP, // 声音事件
                    SoundSource.PLAYERS,   // 声音源（玩家类别）
                    1.0F,                  // 音量
                    1.0F                   // 音高
            );
            return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide);
        } else {
            ItemStack oldHelmet = helmetSlot.copy();
            player.setItemSlot(EquipmentSlot.HEAD, player.getItemInHand(hand).copy());
            player.getInventory().setItem(player.getInventory().selected, oldHelmet);
            level.playSound(
                    null,                  // 位置跟踪器（null表示使用玩家位置）
                    player.getX(),         // X坐标
                    player.getY(),         // Y坐标
                    player.getZ(),         // Z坐标
                    SoundEvents.SLIME_JUMP, // 声音事件
                    SoundSource.PLAYERS,   // 声音源（玩家类别）
                    1.0F,                  // 音量
                    1.0F                   // 音高
            );
            return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.decorcollections.slime_bottle"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @EventBusSubscriber(modid = DecorCollections.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class EventHandler {
        @SubscribeEvent
        public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
            LivingEntity entity = event.getEntity();

            // 只处理玩家
            if (!(entity instanceof Player player)) return;

            // 只处理头盔槽位的变化
            if (event.getSlot() != EquipmentSlot.HEAD) return;

            // 移除所有属性修饰符
            removeAllModifiers(player);

            // 检查新装备是否为SlimeBottleBlockItem
            ItemStack newItem = event.getTo();
            if (newItem.getItem() instanceof SlimeBottleBlockItem slimeBottle) {
                // 应用属性修饰符
                applyModifiers(player);
            }

            // 更新玩家生命值状态
            updatePlayerHealth(player);
        }

        private static void applyModifiers(Player player) {
            // 应用盔甲值修饰符
            AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);
            if (armorAttribute != null) {
                AttributeModifier armorModifier = new AttributeModifier(
                        ARMOR_MODIFIER_ID,
                        ARMOR_BONUS,
                        AttributeModifier.Operation.ADD_VALUE
                );
                if (!armorAttribute.hasModifier(ARMOR_MODIFIER_ID)) {
                    armorAttribute.addPermanentModifier(armorModifier);
                }
            }

            // 应用最大生命值修饰符
            AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttribute != null) {
                AttributeModifier healthModifier = new AttributeModifier(
                        HEALTH_MODIFIER_ID,
                        HEALTH_BONUS,
                        AttributeModifier.Operation.ADD_VALUE
                );
                if (!healthAttribute.hasModifier(HEALTH_MODIFIER_ID)) {
                    healthAttribute.addPermanentModifier(healthModifier);
                }
            }
        }

        private static void removeAllModifiers(Player player) {
            // 移除盔甲值修饰符
            AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);
            if (armorAttribute != null && armorAttribute.hasModifier(ARMOR_MODIFIER_ID)) {
                armorAttribute.removeModifier(ARMOR_MODIFIER_ID);
            }

            // 移除最大生命值修饰符
            AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttribute != null && healthAttribute.hasModifier(HEALTH_MODIFIER_ID)) {
                healthAttribute.removeModifier(HEALTH_MODIFIER_ID);
            }
        }

        private static void updatePlayerHealth(Player player) {
            AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr == null) return;

            double newMaxHealth = maxHealthAttr.getValue();
            double currentHealth = player.getHealth();

            // 如果当前生命值超过新的最大值，调整到新最大值
            if (currentHealth > newMaxHealth) {
                player.setHealth((float) newMaxHealth);
            }
        }
    }
}