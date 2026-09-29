package com.bingbingbing.item;

import com.bingbingbing.registry.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/** The bingbingbing enchanted book: a bingbingbing-namespace item styled like a vanilla enchanted book. */
public class BingbingbingBookItem extends Item {

    public BingbingbingBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // Grey enchantment line, matching how the vanilla enchanted book lists its enchantment.
        tooltip.add(ModEnchantments.BINGBINGBING.get().getFullname(1));
    }
}
