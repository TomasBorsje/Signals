package com.wingmann.signals.screens;

import com.wingmann.signals.Signals;
import com.wingmann.signals.blocks.SignalTerminalBlock;
import com.wingmann.signals.containers.SignalTerminalContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SignalTerminalScreen extends AbstractContainerScreen<SignalTerminalContainer> {
    private final ResourceLocation GUI = new ResourceLocation(Signals.MODID, "textures/gui/signal_terminal_gui.png");
    private final ResourceLocation PLANET_TEXTURE = new ResourceLocation(Signals.MODID, "textures/gui/planet.png");

    private final static int SIGNAL_PREVIEW_WIDTH = 56;
    private final static int SIGNAL_PREVIEW_HEIGHT = 40;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_X = 114;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_Y = 13;

    public SignalTerminalScreen(SignalTerminalContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, Component.translatable(SignalTerminalBlock.SCREEN_SIGNAL_TERMINAL), 10, 10, 0xffffff);

    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(getMenu().getBlockEntity() != null && getMenu().getBlockEntity().tryGetSignalData() != null) {
            // Draw signal preview TODO: cache resourcelocations
            RenderSystem.setShaderTexture(0, new ResourceLocation(Signals.MODID, "textures/gui/signalpreviews/"+getMenu().getBlockEntity().tryGetSignalData().signalPreviewTexture+".png"));
            this.blit(matrixStack, relX + SIGNAL_PREVIEW_TOP_LEFT_X, relY + SIGNAL_PREVIEW_TOP_LEFT_Y, 0, 0,
                    SIGNAL_PREVIEW_WIDTH, SIGNAL_PREVIEW_HEIGHT);
        }

    }
}
