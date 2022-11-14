package com.wingmann.signals.items;

import com.wingmann.signals.setup.Registration;
import com.wingmann.signals.util.TapeTag;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class TapeItem extends Item {

    public static final int GREEN = 0x18f233;
    public static final int YELLOW = 0xf2f233;
    public static final int ORANGE = 0xf28333;
    public static final int RED = 0xeb4034;

    public TapeItem() {
        super(Registration.ITEM_PROPERTIES.stacksTo(1));
    }

    private static final int DOWNLOAD_BAR_LENGTH = 40;

    /**
     * Set tape info when crafted.
     */
    @Override
    public void onCraftedBy(ItemStack stack, Level p_41448_, Player p_41449_) {
        super.onCraftedBy(stack, p_41448_, p_41449_);
        if(stack.hasTag()) {
            TapeTag tag = new TapeTag(stack.getTag());
            stack.setTag(tag.toCompoundTag());
        } else {
            stack.setTag(new TapeTag().toCompoundTag());
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
        if (this.allowedIn(tab)) {
            ItemStack nbtStack = new ItemStack(this);
            nbtStack.setTag(new TapeTag().toCompoundTag());
            stacks.add(nbtStack);
        }
    }

    private MutableComponent getObfuscatedString(String translationKey, float filterProgress, Style style) {
        MutableComponent base = Component.literal("").withStyle(style);
        String translatedName = I18n.get(translationKey);
        // One off to get some random doubles based on signal name
        double[] doubles = new Random(translationKey.hashCode()).doubles(translatedName.length()).toArray();

        for(int i = 0; i < translatedName.length(); i++) {
            MutableComponent letter = Component.literal(translatedName.substring(i, i + 1));
            if(Math.min(doubles[i] + 0.05, 0.98) > filterProgress) { // Obfuscate if filter progress is lower than 0.05-0.98 randomly
                base.append(letter.withStyle(style.withObfuscated(true)));
            } else {
                base.append(letter);
            }
        }
        return base;
    }

    private MutableComponent getCensoredString(String translationKey, float filterProgress, Style style) {
        MutableComponent base = Component.literal("").withStyle(style);
        String translatedName = I18n.get(translationKey);
        // One off to get some random doubles based on signal name
        double[] doubles = new Random(translationKey.hashCode()).doubles(translatedName.length()).toArray();

        for(int i = 0; i < translatedName.length(); i++) {
            MutableComponent letter = Component.literal(translatedName.substring(i, i + 1));
            if(Math.min(doubles[i] + 0.05, 0.98) > filterProgress) { // Obfuscate if filter progress is lower than 0.05-0.98 randomly
                base.append(Component.literal("-").withStyle(style));
            } else {
                base.append(letter);
            }
        }
        return base;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        if(stack.hasTag() && TapeTag.isValidTapeTag(stack.getTag())) {
            TapeTag tapeTag = new TapeTag(stack.getTag());
            if(tapeTag.isEmpty()) {
                // Show empty tooltip
                components.add(Component.translatable("signals.empty_tape").withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0x555555))));
            } else {
                // Show filled tooltip
                // Signal Name
                components.add(Component.translatable("signals.tooltips.signal_name_title").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5555FF))).append(getObfuscatedString(tapeTag.getData().signalName, tapeTag.downloadProgress, Style.EMPTY.withColor(TextColor.fromRgb(0xff00ff)))));
                // Signal Description
                components.add(Component.translatable("signals.tooltips.signal_description_title").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5555FF))));
                // components.add(Component.translatable(tapeTag.getData().signalDescription).withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0xA3A3A3))));
                components.add(getCensoredString(tapeTag.getData().signalDescription, tapeTag.downloadProgress, Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0xA3A3A3))));
                // Download Progress
                int downloadColour;
                if (tapeTag.downloadProgress == 1) {
                    downloadColour = GREEN; // Green if done
                } else if (tapeTag.downloadProgress > 0.66) {
                    downloadColour = YELLOW; // Yellow if 66% done
                } else if (tapeTag.downloadProgress > 0.33) {
                    downloadColour = ORANGE; // Orange if 33% done
                } else {
                    downloadColour = RED; // Red if < 33% done
                }
                int completedBars = (int) (tapeTag.downloadProgress * DOWNLOAD_BAR_LENGTH);
                String downloadBar = "[" + StringUtils.repeat('|', completedBars) + StringUtils.repeat('.', DOWNLOAD_BAR_LENGTH - completedBars) + "] (" + String.format("%.1f",tapeTag.downloadProgress * 100) + "%)";

                components.add(Component.translatable("signals.tooltips.download_title").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5555FF))));
                components.add(Component.literal(downloadBar).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(downloadColour))));
            }
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static boolean isTapeItem(ItemStack stack) {
        return stack != ItemStack.EMPTY && stack.getItem() instanceof TapeItem;
    }
}
