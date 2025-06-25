package cn.owen233666.decorcollections.block.entity;

import cn.owen233666.decorcollections.DecorCollections;
import cn.owen233666.decorcollections.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DecorCollections.MOD_ID);

    public static final Supplier<BlockEntityType<SlimeBottleBlockEntity>> SLIMEBOTTLE_BE =
            BLOCK_ENTITIES.register("slimebottle_be", () -> BlockEntityType.Builder.of(
                    SlimeBottleBlockEntity::new, ModBlocks.SLIMEBOTTLE.get()).build(null));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
