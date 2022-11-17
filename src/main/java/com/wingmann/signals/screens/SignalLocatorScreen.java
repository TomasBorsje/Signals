package com.wingmann.signals.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wingmann.signals.Signals;
import com.wingmann.signals.blockentities.SignalLocatorBlockEntity;
import com.wingmann.signals.blocks.SignalLocatorBlock;
import com.wingmann.signals.containers.SignalLocatorContainer;
import com.wingmann.signals.network.PacketStartSignalScan;
import com.wingmann.signals.network.SignalsPacketHandler;
import com.wingmann.signals.registry.SignalData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.util.List;

public class SignalLocatorScreen extends AbstractContainerScreen<SignalLocatorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(Signals.MODID, "textures/gui/signal_locator_gui.png");
    private final static int SIGNAL_PREVIEW_WIDTH = 56;
    private final static int SIGNAL_PREVIEW_HEIGHT = 40;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_X = 114;
    private final static int SIGNAL_PREVIEW_TOP_LEFT_Y = 13;

    private final static Rectangle scanButton = new Rectangle(13,48,43,14);

    public SignalLocatorScreen(SignalLocatorContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageHeight = 152;
        this.imageWidth = 180;
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
        Font font = Minecraft.getInstance().font;
        List<SignalData> signals = getMenu().getBlockEntity().getSignalDataList();
        for(int i = 0; i < SignalLocatorBlockEntity.MAX_SIGNALS && i < signals.size(); i++) {
            renderSignalInfo(matrixStack, font, signals.get(i), i);
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        // TODO: Make this work with non-fullscreen windows
        if(mouseX >= relX + scanButton.x && mouseX < relX + scanButton.x + scanButton.width &&
                mouseY >= relY + scanButton.y && mouseY < relY + scanButton.y + scanButton.height) {
            this.blit(matrixStack, relX + scanButton.x, relY + scanButton.y, this.imageWidth, 0, scanButton.width, scanButton.height);
        }
        SignalLocatorBlockEntity entity = getMenu().getBlockEntity();
        if (entity != null && entity.hasNonEmptyTape() && entity.tryGetSignalData() != null) {
            // Draw signal preview
            RenderSystem.setShaderTexture(0, getMenu().getBlockEntity().tryGetSignalData().signalPreviewResourceLocation);
            this.blit(matrixStack, relX + SIGNAL_PREVIEW_TOP_LEFT_X, relY + SIGNAL_PREVIEW_TOP_LEFT_Y, 0, 0,
                    SIGNAL_PREVIEW_WIDTH, SIGNAL_PREVIEW_HEIGHT);
        }
    }

    private void renderSignalInfo(PoseStack matrixStack, Font font, SignalData data, int index) {
        if(data == null || data.id.equals("unknown")) { return; }
        drawString(matrixStack, font, Component.translatable(data.signalName), 2, font.lineHeight * index + 2, 0xffffff);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        // If clicked between the signal preview
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        if(pMouseX > relX + scanButton.x && pMouseX < relX + scanButton.x + scanButton.width &&
                pMouseY > relY + scanButton.y && pMouseY < relY + scanButton.y + scanButton.height) {
            // Try set tape data
            SignalsPacketHandler.sendToServer(new PacketStartSignalScan(0, getMenu().getBlockEntity().getBlockPos()));
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
