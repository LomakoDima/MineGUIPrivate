package ru.dimalab.minegui.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import ru.dimalab.minegui.utils.ColorPalette;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.util.function.Consumer;

public class MineGUICheckBox implements MineGUIWidget {
    private final int x, y, size;
    private boolean checked;
    private final Component label;
    private Consumer<Boolean> onToggle;

    private int boxColor;
    private int borderColor;
    private int checkColor;
    private int textColor;

    private MineGUICheckBox(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.size = builder.size;
        this.label = builder.label;
        this.checked = builder.defaultValue;

        this.boxColor = builder.boxColor.getColor();
        this.borderColor = builder.borderColor.getColor();
        this.checkColor = builder.checkColor.getColor();
        this.textColor = builder.textColor.getColor();

        this.onToggle = builder.onToggle;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        graphics.fill(x, y, x + size, y + size, boxColor);
        graphics.renderOutline(x, y, size, size, borderColor);

        if (checked) {
            int padding = 4;
            graphics.fill(x + padding, y + padding, x + size - padding, y + size - padding, checkColor);
        }

        int textX = x + size + 8;
        int textY = y + (size - font.lineHeight) / 2;
        graphics.drawString(font, label, textX, textY, textColor, false);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            toggle();
            return true; // клики обработаны
        }
        return false; // не обработаны
    }

    // ✅ Переключатель чекбокса
    private void toggle() {
        checked = !checked;
        if (onToggle != null) {
            onToggle.accept(checked);
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + size && mouseY >= y && mouseY < y + size;
    }

    public boolean isChecked() {
        return checked;
    }

    // 🔨 Builder для удобного создания чекбокса
    public static class Builder {
        private int x, y, size;
        private Component label;
        private boolean defaultValue;

        private ColorPalette boxColor = ColorPalette.DARK_GRAY;
        private ColorPalette borderColor = ColorPalette.BLACK;
        private ColorPalette checkColor = ColorPalette.GREEN;
        private ColorPalette textColor = ColorPalette.WHITE;

        private Consumer<Boolean> onToggle;

        public Builder(int x, int y, int size, Component label) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.label = label;
        }

        public Builder defaultValue(boolean checked) {
            this.defaultValue = checked;
            return this;
        }

        public Builder boxColor(ColorPalette color) {
            this.boxColor = color;
            return this;
        }

        public Builder borderColor(ColorPalette color) {
            this.borderColor = color;
            return this;
        }

        public Builder checkColor(ColorPalette color) {
            this.checkColor = color;
            return this;
        }

        public Builder textColor(ColorPalette color) {
            this.textColor = color;
            return this;
        }

        public Builder onToggle(Consumer<Boolean> callback) {
            this.onToggle = callback;
            return this;
        }

        public MineGUICheckBox build() {
            return new MineGUICheckBox(this);
        }
    }
}
