package com.bingbingbing.util;

import com.bingbingbing.registry.ModEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/** Small helpers used by both the Forge event handlers and the mixins. */
public final class ModEnchantHelper {

    private ModEnchantHelper() {}

    /** True if the stack carries the bingbingbing enchantment. */
    public static boolean hasEnchant(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BINGBINGBING.get(), stack) > 0;
    }

    /** True if any equipped armor piece carries the enchantment. */
    public static boolean wearsEnchantedArmor(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        for (ItemStack piece : entity.getArmorSlots()) {
            if (hasEnchant(piece)) {
                return true;
            }
        }
        return false;
    }
}
