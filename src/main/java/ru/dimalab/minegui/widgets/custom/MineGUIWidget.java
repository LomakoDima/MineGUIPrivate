package ru.dimalab.minegui.widgets.custom;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface MineGUIWidget {
    default void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {}
    default void render1(GuiGraphics graphics, Font font, int mouseX, int mouseY, int width, int height, boolean active) {}

    default void tick() {
    }

    default boolean mouseClicked(int mouseX, int mouseY, int button) {
        return false;
    }

    default boolean mouseReleased(int mouseX, int mouseY, int button) {
        return false;
    }

    default boolean mouseDragged(int mouseX, int mouseY, int button, double dragX, double dragY) {
        return false;
    }

    default boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    int DEFAULT_X = 0;
    int DEFAULT_Y = 0;

    default int getX() { return DEFAULT_X; }
    default int getY() { return DEFAULT_Y; }
    default void setPosition(int x, int y) {}

    default int getWidth() { return 0; }

    default int getHeight() { return 0; }
}
