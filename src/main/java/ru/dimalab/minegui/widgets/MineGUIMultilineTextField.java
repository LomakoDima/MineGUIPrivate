package ru.dimalab.minegui.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.util.ArrayList;
import java.util.List;

public class MineGUIMultilineTextField implements MineGUIWidget {
    private final int x, y, width, height;
    private List<String> lines = new ArrayList<>();
    private int maxLines = 5;
    private String text = "";
    private String placeholder = "Введите текст";
    private boolean focused = false;
    private int textColor = 0xFFFFFF;
    private int placeholderColor = 0x888888;
    private int backgroundColor = FastColor.ARGB32.color(255, 40, 40, 40);
    private int borderColor = FastColor.ARGB32.color(255, 80, 80, 80);
    private int borderColorFocused = FastColor.ARGB32.color(255, 0, 120, 255);
    private int cursorPosition = 0;
    private MineGUIBlinkingCursor cursor;

    public MineGUIMultilineTextField(int x, int y, int width, int height, String placeholder) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.cursor = new MineGUIBlinkingCursor(x + 4, y + 4);
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        wrapText();
    }

    public void setText(String newText) {
        this.text = newText;
        this.cursorPosition = newText.length();
        wrapText();
    }

    public String getText() {
        return text;
    }

    private void wrapText() {
        lines.clear();
        Font font = net.minecraft.client.Minecraft.getInstance().font;
        List<FormattedText> wrappedLines = font.getSplitter().splitLines(Component.literal(text), width - 8, Component.literal(text).getStyle());
        for (int i = 0; i < Math.min(wrappedLines.size(), maxLines); i++) {
            lines.add(wrappedLines.get(i).getString());
        }
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        graphics.fill(x, y, x + width, y + height, backgroundColor);
        graphics.renderOutline(x, y, width, height, focused ? borderColorFocused : borderColor);

        int textX = x + 4;
        int textY = y + 4;

        if (lines.isEmpty() && !focused) {
            graphics.drawString(font, Component.literal(placeholder), textX, textY, placeholderColor, false);
        } else {
            for (int i = 0; i < lines.size(); i++) {
                graphics.drawString(font, Component.literal(lines.get(i)), textX, textY + i * font.lineHeight, textColor, false);
            }
        }

        if (focused) {
            updateCursorPosition(font, textX, textY);
            cursor.render(graphics, font, mouseX, mouseY);
        }
    }

    private void updateCursorPosition(Font font, int textX, int textY) {
        int cursorX = textX + font.width(text.substring(0, cursorPosition));
        int cursorY = textY + (lines.size() - 1) * font.lineHeight;
        cursor.setPosition(cursorX, cursorY); // Добавь этот метод в `MineGUIBlinkingCursor`
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        focused = isInside(mouseX, mouseY);
        return focused;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (focused) {
            text = text.substring(0, cursorPosition) + codePoint + text.substring(cursorPosition);
            cursorPosition++;
            wrapText();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!focused) return false;
        if (keyCode == 259 && cursorPosition > 0) { // Backspace key
            text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
            cursorPosition--;
            wrapText();
            return true;
        }
        if (keyCode == 262 && cursorPosition < text.length()) { // Right arrow
            cursorPosition++;
            return true;
        }
        if (keyCode == 263 && cursorPosition > 0) { // Left arrow
            cursorPosition--;
            return true;
        }
        return false;
    }

    private boolean isInside(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
