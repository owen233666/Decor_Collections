package cn.owen233666.decorcollections.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SlimeBottleBlockEntity extends BlockEntity {
    public SlimeBottleBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SLIMEBOTTLE_BE.get(), pos, blockState);
    }
}
