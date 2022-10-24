package com.example.examplemod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.example.examplemod.setup.Registration.ITEM_PROPERTIES;

public class TapeItem extends Item {

    public TapeItem() {
        super(ITEM_PROPERTIES.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        if(stack.hasTag() && stack.getTag().contains("lore")) {
            // TODO: Proper formatting
            components.add(Component.literal(stack.getTag().getString("lore")).withStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(0x425df5))));
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
