package com.bingbingbing.event;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.registry.ModEnchantments;
import com.bingbingbing.registry.ModItems;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/** Mod-bus listeners (creative tab placement). */
@Mod.EventBusSubscriber(modid = Bingbingbing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // Vanilla auto-adds a minecraft:enchanted_book for every enchantment; drop ours.
            List<ItemStack> toRemove = new ArrayList<>();
            for (var entry : event.getEntries()) {
                if (isBingbingbingBook(entry.getKey())) {
                    toRemove.add(entry.getKey());
                }
            }
            for (ItemStack stack : toRemove) {
                event.getEntries().remove(stack);
            }
            event.accept(ModItems.BINGBINGBING_BOOK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.ICE_ANVIL.get());
        }
    }

    private static boolean isBingbingbingBook(ItemStack stack) {
        if (!stack.is(Items.ENCHANTED_BOOK)) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ENCHANTMENTS.getKey(ModEnchantments.BINGBINGBING.get());
        if (id == null) {
            return false;
        }
        ListTag stored = EnchantedBookItem.getEnchantments(stack);
        for (int i = 0; i < stored.size(); i++) {
            if (id.toString().equals(stored.getCompound(i).getString("id"))) {
                return true;
            }
        }
        return false;
    }
}
