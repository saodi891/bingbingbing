package com.bingbingbing.client;

import com.bingbingbing.Bingbingbing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;

/**
 * The ice anvil's screen. Identical layout to the vanilla {@link AnvilScreen} (rename box, cost bar,
 * input-slot overlay) but drawn with the mod's own GUI texture, so the sheet at
 * {@code assets/bingbingbing/textures/gui/container/ice_anvil.png} can be edited on top of the vanilla
 * anvil layout.
 *
 * <p>The constructor takes {@link AnvilMenu} (not IceAnvilMenu) so it matches the
 * {@code MenuType<AnvilMenu>} the ice anvil registers; at runtime the instance is an IceAnvilMenu.
 */
public class IceAnvilScreen extends AnvilScreen {

    private static final ResourceLocation ICE_ANVIL_GUI =
            new ResourceLocation(Bingbingbing.MODID, "textures/gui/container/ice_anvil.png");

    public IceAnvilScreen(AnvilMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Mirror ItemCombinerScreen#renderBg (main panel) then AnvilScreen#renderBg (input-slot overlay),
        // but sourced from our texture. The overlay strip lives just below the panel in the sheet:
        //   y = imageHeight      -> slot has an item
        //   y = imageHeight + 16 -> slot empty (draws the red X)
        guiGraphics.blit(ICE_ANVIL_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderFg(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(ICE_ANVIL_GUI, this.leftPos + 59, this.topPos + 20, 0,
                this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
    }
}
