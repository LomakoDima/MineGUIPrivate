package ru.dimalab.minegui.screens.utils;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import ru.dimalab.minegui.utils.MouseHelper;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleWindow implements MineGUIWidget {
    private int x, y, width, height;
    private String title;
    private boolean expanded = true;
    private List<MineGUIWidget> children = new ArrayList<>();
    private float animationProgress = 1.0f;
    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;
    private List<int[]> childOffsets = new ArrayList<>();

    public CollapsibleWindow(String title, int x, int y, int width, int height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addChild(MineGUIWidget widget, int offsetX, int offsetY) {
        children.add(widget);
        childOffsets.add(new int[]{offsetX, offsetY});
        widget.setPosition(x + offsetX, y + 15 + offsetY);
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        graphics.fill(x, y, x + width, y + 15, 0xFF444444);
        graphics.drawString(font, title, x + 15, y + 5, 0xFFFFFF);

        // Рисуем стрелку
        String arrow = expanded ? "▼" : "▶";
        graphics.drawString(font, arrow, x + 5, y + 5, 0xFFFFFF);

        // Анимация
        if (expanded && animationProgress < 1.0f) {
            animationProgress += 0.1f;
        } else if (!expanded && animationProgress > 0.0f) {
            animationProgress -= 0.1f;
        }

        int animatedHeight = expanded ? height : 0;

        if (animatedHeight > 0 || dragging) {
            graphics.fill(x, y + 15, x + width, y + 15 + animatedHeight, 0xFF222222);
            for (MineGUIWidget child : children) {
                child.render(graphics, font, mouseX, mouseY);
            }
        }

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15) {
            expanded = !expanded;
            dragging = true;
            return true;
        }
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + Math.max(15, height) && MouseHelper.LEFT.isLeft()) {
            dragging = true;
            dragOffsetX = mouseX - x;
            dragOffsetY = mouseY - y;
            return true;
        }

        return false;
    }


    @Override
    public boolean mouseDragged(int mouseX, int mouseY, int button, double dragX, double dragY) {
        if (dragging) {
            int newX = mouseX - dragOffsetX;
            int newY = mouseY - dragOffsetY;

            x = newX;
            y = newY;

            // Двигаем дочерние элементы **относительно нового положения окна**
            for (int i = 0; i < children.size(); i++) {
                MineGUIWidget child = children.get(i);
                int[] offset = childOffsets.get(i);
                child.setPosition(x + offset[0], y + 15 + offset[1]); // Теперь виджеты остаются в своём месте!
            }

            return true;

        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false;
        return false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public static class Builder {
        private String title;
        private int x, y, width, height;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

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

        public CollapsibleWindow build() {
            return new CollapsibleWindow(title, x, y, width, height);
        }
    }
}
