package com.bingbingbing.registry;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.menu.IceAnvilMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Bingbingbing.MODID);

    // Typed as MenuType<AnvilMenu> so the vanilla AnvilScreen can be registered for it directly.
    public static final RegistryObject<MenuType<AnvilMenu>> ICE_ANVIL = MENUS.register("ice_anvil",
            () -> IForgeMenuType.create(
                    (IContainerFactory<AnvilMenu>) (windowId, inv, data) -> new IceAnvilMenu(windowId, inv)));
}
