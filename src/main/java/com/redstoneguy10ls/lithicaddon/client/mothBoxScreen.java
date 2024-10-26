package com.redstoneguy10ls.lithicaddon.client;

import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.container.mothboxContainer;
import com.redstoneguy10ls.lithicaddon.util.LithicHelpers;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.client.screen.TFCContainerScreen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class mothBoxScreen extends BlockEntityScreen<mothBlockEntity, mothboxContainer> {

    private static final ResourceLocation TEXTURE = LithicHelpers.identifier("textures/gui/moth_box.png");
    public mothBoxScreen(mothboxContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, TEXTURE);
        //inventoryLabelY += 20;
        //imageHeight += 20;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTicks, mouseX, mouseY);
        final float leaves = (float) blockEntity.getLeaves() / 64;
        if(leaves > 0)
        {
            final int pixels = 66 - Mth.ceil(leaves*66);
            graphics.blit(texture, leftPos+157,topPos+15+pixels,176,pixels,6,104-pixels);
        }
    }
}
