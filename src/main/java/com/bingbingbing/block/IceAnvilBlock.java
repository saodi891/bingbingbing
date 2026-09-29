package com.bingbingbing.block;

import com.bingbingbing.menu.IceAnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Behaves like a vanilla anvil, but:
 *  - opens the {@link IceAnvilMenu} (reusable bingbingbing book, free, no anvil damage),
 *  - sounds like ice (SoundType.GLASS is set on the block properties),
 *  - deals half of an anvil's fall damage,
 *  - shatters instead of settling when it lands.
 */
public class IceAnvilBlock extends AnvilBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.bingbingbing.ice_anvil");

    public IceAnvilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (containerId, inventory, player) ->
                        new IceAnvilMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)),
                CONTAINER_TITLE);
    }

    /** Half of the vanilla anvil's fall damage (anvil is 2.0 per block, max 40). */
    @Override
    protected void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(1.0F, 20);
    }

    /** Landing shatters the ice anvil: remove the just-placed block and play the ice break sound. */
    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity entity) {
        level.destroyBlock(pos, false);
    }
}
