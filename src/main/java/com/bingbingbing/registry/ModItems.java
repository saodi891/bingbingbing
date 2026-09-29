package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.item.BingbingbingBookItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Bingbingbing.MODID);

    /** The bingbingbing book: a bingbingbing-namespace item with its own texture, used to apply the enchant via anvil. */
    public static final RegistryObject<Item> BINGBINGBING_BOOK =
            ITEMS.register("bingbingbing_book",
                    () -> new BingbingbingBookItem(new Item.Properties().stacksTo(16)));

    /** Block item for the ice anvil. */
    public static final RegistryObject<Item> ICE_ANVIL =
            ITEMS.register("ice_anvil",
                    () -> new BlockItem(ModBlocks.ICE_ANVIL.get(), new Item.Properties()));
}
