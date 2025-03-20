package ru.dimalab.minegui.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraftforge.fml.loading.FMLPaths;
import ru.dimalab.minegui.utils.ColorPalette;
import ru.dimalab.minegui.utils.MouseHelper;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.util.function.Consumer;

public class MineGUISlider implements MineGUIWidget {
    private final int x, y;
    private final int width, height;
    private final int minValue, maxValue;
    private final int step;
    private int currentValue;

    private boolean dragging = false;
    private final Component label;
    private Consumer<Integer> onValueChanged;

    private int backgroundColor;
    private int fillColor;
    private int knobColor;
    private int borderColor;
    private int textColor;

    private MineGUISlider(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.width = builder.width;
        this.height = builder.height;
        this.minValue = builder.minValue;
        this.maxValue = builder.maxValue;
        this.step = builder.step;
        this.currentValue = clampValue(builder.defaultValue);
        this.label = builder.label;
        this.onValueChanged = builder.onValueChanged;
        this.backgroundColor = builder.backgroundColor.getColor();
        this.fillColor = builder.fillColor.getColor();
        this.knobColor = builder.knobColor.getColor();
        this.borderColor = builder.borderColor.getColor();
        this.textColor = builder.textColor.getColor();
    }

    public static class Builder {
        private int x, y, width, height;
        private int minValue, maxValue, step, defaultValue;
        private Component label;
        private Consumer<Integer> onValueChanged;
        private ColorPalette backgroundColor = ColorPalette.DIM_GRAY;
        private ColorPalette fillColor = ColorPalette.LIME_GREEN;
        private ColorPalette knobColor = ColorPalette.WHITE;
        private ColorPalette borderColor = ColorPalette.BLACK;
        private ColorPalette textColor = ColorPalette.WHITE;

        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setRange(int minValue, int maxValue, int step) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.step = step;
            return this;
        }

        public Builder setDefaultValue(int defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder setLabel(Component label) {
            this.label = label;
            return this;
        }

        public Builder setOnValueChanged(Consumer<Integer> onValueChanged) {
            this.onValueChanged = onValueChanged;
            return this;
        }

        public Builder setColors(ColorPalette backgroundColor, ColorPalette fillColor, ColorPalette knobColor, ColorPalette borderColor, ColorPalette textColor) {
            this.backgroundColor = backgroundColor;
            this.fillColor = fillColor;
            this.knobColor = knobColor;
            this.borderColor = borderColor;
            this.textColor = textColor;
            return this;
        }

        public MineGUISlider build() {
            return new MineGUISlider(this);
        }
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        graphics.fill(x, y, x + width, y + height, backgroundColor);
        int filledWidth = (int) ((currentValue - minValue) / (float) (maxValue - minValue) * width);
        graphics.fill(x, y, x + filledWidth, y + height, fillColor);
        int knobX = x + filledWidth - height / 2;
        graphics.fill(knobX, y - 2, knobX + height, y + height + 2, knobColor);
        String display = label.getString() + ": " + currentValue;
        int textWidth = font.width(display);
        int textX = x + (width - textWidth) / 2;
        int textY = y - font.lineHeight - 4;
        graphics.drawString(font, Component.literal(display), textX, textY, textColor, false);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (MouseHelper.LEFT.isLeft() && isMouseOver(mouseX, mouseY)) {
            dragging = true;
            updateValueFromMouse(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        if (MouseHelper.LEFT.isLeft()) {
            dragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY, int button, double dragX, double dragY) {
        if (dragging && MouseHelper.LEFT.isLeft()) {
            updateValueFromMouse(mouseX);
            return true;
        }
        return false;
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void updateValueFromMouse(int mouseX) {
        float percent = (mouseX - x) / (float) width;
        int rawValue = minValue + Math.round((maxValue - minValue) * percent);
        int steppedValue = clampValue(Math.round(rawValue / (float) step) * step);
        if (steppedValue != currentValue) {
            currentValue = steppedValue;
            if (onValueChanged != null) {
                onValueChanged.accept(currentValue);
            }
        }
    }

    private int clampValue(int value) {
        return Math.max(minValue, Math.min(maxValue, value));
    }

    public int getValue() {
        return currentValue;
    }

    public void setValue(int value) {
        this.currentValue = clampValue(value);
    }
}
