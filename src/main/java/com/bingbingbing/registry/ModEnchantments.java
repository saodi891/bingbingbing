package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.enchantment.BingbingbingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Bingbingbing.MODID);

    // Applicable to tools/weapons (TieredItem = sword + pickaxe/axe/shovel/hoe) and armor.
    public static final EnchantmentCategory CATEGORY = EnchantmentCategory.create(
            "BINGBINGBING",
            item -> item instanceof TieredItem || item instanceof ArmorItem);

    public static final RegistryObject<Enchantment> BINGBINGBING = ENCHANTMENTS.register(
            "bingbingbing",
            () -> new BingbingbingEnchantment(CATEGORY, EquipmentSlot.values()));
}
