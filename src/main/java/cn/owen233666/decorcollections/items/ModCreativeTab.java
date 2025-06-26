package cn.owen233666.decorcollections.items;

import cn.owen233666.decorcollections.DecorCollections;
import cn.owen233666.decorcollections.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DecorCollections.MOD_ID);

    public static final Supplier<CreativeModeTab> DECOR_COLLECTIONS_TAB = CREATIVE_MODE_TAB.register("decor_collections_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SLIMEBOTTLE.get()))
                    .title(Component.translatable("creativetab.decorcollections.all"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.SLIMEBOTTLE.get());
                    })).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
