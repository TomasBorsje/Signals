package com.wingmann.signals.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wingmann.signals.Signals;
import com.wingmann.signals.blocks.SignalLocatorBlock;
import com.wingmann.signals.containers.SignalLocatorContainer;
import com.wingmann.signals.network.PacketSelectSignal;
import com.wingmann.signals.network.SignalsPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SignalLocatorScreen extends AbstractContainerScreen<SignalLocatorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(Signals.MODID, "textures/gui/signal_locator_gui.png");
    private final ResourceLocation PLANET_TEXTURE = new ResourceLocation(Signals.MODID, "textures/gui/planet.png");

    private final static int SIGNAL_PREVIEW_WIDTH = 56;
    private final static int SIGNAL_PREVIEW_HEIGHT = 40;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_X = 114;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_Y = 13;

    public SignalLocatorScreen(SignalLocatorContainer container, Inventory inv, Component name) {
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
        drawString(matrixStack, Minecraft.getInstance().font, Component.translatable(SignalLocatorBlock.SCREEN_SIGNAL_LOCATOR), 10, 10, 0xffffff);

    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        if(getMenu().getBlockEntity() != null && getMenu().getBlockEntity().hasNonEmptyTape() && getMenu().getBlockEntity().tryGetSignalData() != null) {
            // Draw signal preview TODO: cache resourcelocations
            RenderSystem.setShaderTexture(0, new ResourceLocation(Signals.MODID, "textures/gui/signalpreviews/"+getMenu().getBlockEntity().tryGetSignalData().signalPreviewTexture+".png"));
            this.blit(matrixStack, relX + SIGNAL_PREVIEW_TOP_LEFT_X, relY + SIGNAL_PREVIEW_TOP_LEFT_Y, 0, 0,
                    SIGNAL_PREVIEW_WIDTH, SIGNAL_PREVIEW_HEIGHT);
        }

    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        // If clicked between the signal preview
        if(pMouseX > SIGNAL_PREVIEW_TOP_LEFT_X && pMouseX < SIGNAL_PREVIEW_TOP_LEFT_X + SIGNAL_PREVIEW_WIDTH &&
                pMouseY > SIGNAL_PREVIEW_TOP_LEFT_Y && pMouseY < SIGNAL_PREVIEW_TOP_LEFT_Y + SIGNAL_PREVIEW_HEIGHT) {
            // Try set tape data
            // TODO: Use selected index etc
            SignalsPacketHandler.sendToServer(new PacketSelectSignal(0, getMenu().getBlockEntity().getBlockPos()));
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
