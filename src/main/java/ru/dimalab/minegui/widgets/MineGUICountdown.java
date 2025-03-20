/*package ru.dimalab.minegui.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

public class MineGUICountdown implements MineGUIWidget {
    private int x, y;
    private int width, height;
    private long endTime;
    private Runnable onFinish;

    public MineGUICountdown(int x, int y, int seconds, Runnable onFinish) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 20;
        this.endTime = System.currentTimeMillis() + (seconds * 1000L);
        this.onFinish = onFinish;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        long remainingTime = (endTime - System.currentTimeMillis()) / 1000;
        String timeText = remainingTime > 0 ? remainingTime + " сек" : "";
        graphics.drawString(font, Component.literal(timeText), x, y, 0xFFFFFF);
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() >= endTime && onFinish != null) {
            onFinish.run();
            onFinish = null; // Убираем обработчик, чтобы не вызывался бесконечно
        }
    }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }
    @Override
    public int getWidth() { return width; }
    @Override
    public int getHeight() { return height; }
}

 */