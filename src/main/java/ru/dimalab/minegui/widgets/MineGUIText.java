package ru.dimalab.minegui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import ru.dimalab.minegui.utils.AnimationConfig;
import ru.dimalab.minegui.utils.AnimationType;
import ru.dimalab.minegui.utils.ColorPalette;
import ru.dimalab.minegui.utils.TextStyle;
import ru.dimalab.minegui.utils.math.interpolation.InterpolationType;
import ru.dimalab.minegui.utils.math.interpolation.Interpolator;
import ru.dimalab.minegui.utils.math.interpolation.types.EaseInInterpolator;
import ru.dimalab.minegui.utils.math.interpolation.types.EaseOutInterpolator;
import ru.dimalab.minegui.utils.math.interpolation.types.LinearInterpolator;
import ru.dimalab.minegui.utils.math.interpolation.types.QuadraticInterpolator;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class MineGUIText implements MineGUIWidget {
    private final Component text;
    private int x, y;
    private final ColorPalette color;
    private final ColorPalette gradientStartColor;
    private final ColorPalette gradientEndColor;
    private final boolean useGradient;
    private final int maxWidth;
    private final float scale;
    private final EnumSet<TextStyle> styles;
    private final AnimationConfig animationConfig;
    private long startTime;
    private final float letterSpacing;
    private final boolean useOutline;
    private final ColorPalette outlineColor;
    private final int outlineThickness;

    private MineGUIText(Builder builder) {
        this.text = builder.text;
        this.x = builder.x;
        this.y = builder.y;
        this.color = builder.color;
        this.gradientStartColor = builder.gradientStartColor;
        this.gradientEndColor = builder.gradientEndColor;
        this.useGradient = builder.useGradient;
        this.maxWidth = builder.maxWidth;
        this.scale = builder.scale;
        this.styles = builder.styles;
        this.animationConfig = builder.animationConfig;
        this.startTime = System.currentTimeMillis();
        this.letterSpacing = builder.letterSpacing;
        this.useOutline = builder.useOutline;
        this.outlineColor = builder.outlineColor;
        this.outlineThickness = builder.outlineThickness;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        float progress = Math.min(1.0f, (float) elapsedTime / animationConfig.getSpeed());
        Interpolator interpolator = getInterpolator(animationConfig.getInterpolationType());
        float interpolatedProgress = interpolator.interpolate(0, 1, progress);

        List<FormattedText> lines = font.getSplitter().splitLines(text, maxWidth, Style.EMPTY);
        int lineHeight = (int) (font.lineHeight * scale) + 2;

        int currentY = y;

        int maxChars = animationConfig.getAnimationType() == AnimationType.TYPEWRITER
                ? (int) (elapsedTime / animationConfig.getSpeed())
                : Integer.MAX_VALUE;

        graphics.pose().pushPose();

        graphics.pose().translate(x, y, 0);
        graphics.pose().scale(scale, scale, 1.0f);
        graphics.pose().translate(-x, -y, 0);

        for (FormattedText line : lines) {
            String rawText = line.getString();
            String animatedText = getAnimatedText(rawText, maxChars);

            if (animationConfig.getAnimationType() == AnimationType.FADE_IN) {
                int alpha = (int) (255 * interpolatedProgress);
                int textColor = (alpha << 24) | (color.getColor() & 0xFFFFFF);

                if (useGradient) {
                    if(useOutline){
                        drawGradientOutlinedText(graphics, font, animatedText, x, currentY);
                    } else {
                        drawGradientText(graphics, font, animatedText, x, currentY);
                    }

                } else {
                    if(useOutline){
                        drawOutlinedText(graphics, font, animatedText, x, currentY, textColor, outlineColor.getColor(), outlineThickness);
                    } else {
                        drawStyledText(graphics, font, animatedText, x, currentY, textColor);
                    }
                }
            } else if (animationConfig.getAnimationType() == AnimationType.SCALE_UP) {
                graphics.pose().scale(interpolatedProgress, interpolatedProgress, 1.0f);
                if (useGradient) {
                    if(useOutline){
                        drawGradientOutlinedText(graphics, font, animatedText, (int) (x / interpolatedProgress), (int) (y / interpolatedProgress));
                    } else {
                        drawGradientText(graphics, font, animatedText, (int) (x / interpolatedProgress), (int) (y / interpolatedProgress));
                    }
                } else {
                    if(useOutline){
                        drawOutlinedText(graphics, font, animatedText, (int) (x / interpolatedProgress), (int) (y / interpolatedProgress), color.getColor(), outlineColor.getColor(), outlineThickness);
                    } else {
                        drawStyledText(graphics, font, animatedText, (int) (x / interpolatedProgress), (int) (y / interpolatedProgress), color.getColor());
                    }
                }
            } else {
                if (useGradient) {
                    if(useOutline){
                        drawGradientOutlinedText(graphics, font, animatedText, x, currentY);
                    } else {
                        drawGradientText(graphics, font, animatedText, x, currentY);
                    }
                } else {
                    if(useOutline){
                        drawOutlinedText(graphics, font, animatedText, x, currentY, color.getColor(), outlineColor.getColor(), outlineThickness);
                    } else {
                        drawStyledText(graphics, font, animatedText, x, currentY, color.getColor());
                    }
                }
            }

            currentY += lineHeight;
        }

        graphics.pose().popPose();
    }

    private String getAnimatedText(String text, int maxChars) {
        return text.substring(0, Math.min(text.length(), maxChars));
    }

    private void drawGradientOutlinedText(GuiGraphics graphics, Font font, String text, int x, int y) {
        int length = text.length();
        float currentX = x;

        boolean obfuscate = styles.contains(TextStyle.OBFUSCATED);

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / Math.max(length - 1, 1);

            // Считаем цвет по градиенту
            int blendedColor = blendColors(
                    gradientStartColor.getColor(),
                    gradientEndColor.getColor(),
                    ratio
            );

            // Выбираем символ
            char charToRender = obfuscate
                    ? (char) (33 + Math.random() * (126 - 33))
                    : text.charAt(i);

            String character = String.valueOf(charToRender);

            // Обрабатываем стили текста
            String styledCharacter = applyStyles(character);

            // Рисуем обведённый символ с градиентным цветом
            drawOutlinedText(
                    graphics,
                    font,
                    styledCharacter,
                    (int) currentX, // Можно оставить float, если твой drawOutlinedText это поддерживает
                    y,
                    blendedColor,
                    outlineColor.getColor(),
                    outlineThickness
            );

            // Прибавляем ширину текущего символа + отступ
            float charWidth = font.width(styledCharacter);

            currentX += charWidth + letterSpacing;
        }
    }

    private void drawStyledText(GuiGraphics graphics, Font font, String text, int x, int y, int color) {
        int currentX = x;
        boolean obfuscate = styles.contains(TextStyle.OBFUSCATED);

        for (int i = 0; i < text.length(); i++) {
            char charToRender = obfuscate ? (char) (33 + Math.random() * (126 - 33)) : text.charAt(i);

            // Стили применяются через §-коды, но нужно рисовать по одному символу с учетом стиля
            String styledChar = applyStyles(String.valueOf(charToRender));

            graphics.drawString(font, Component.literal(styledChar), currentX, y, color);

            currentX += (int) (font.width(String.valueOf(charToRender)) + letterSpacing);
        }
    }

    private void drawOutlinedText(GuiGraphics graphics, Font font, String text, int x, int y, int textColor, int outlineColor, int outlineThickness) {
        if (outlineThickness < 1) outlineThickness = 1;

        float radiusSquared = outlineThickness * outlineThickness;

        for (int dx = -outlineThickness; dx <= outlineThickness; dx++) {
            for (int dy = -outlineThickness; dy <= outlineThickness; dy++) {

                // Пропускаем центр
                if (dx == 0 && dy == 0) continue;

                float distanceSquared = dx * dx + dy * dy;

                // Пропускаем точки вне круга
                if (distanceSquared > radiusSquared) continue;

                // Сглаживание по краям
                float alphaFactor = 1.0f - (distanceSquared / radiusSquared);
                int blendedColor = applyAlphaToColor(outlineColor, alphaFactor);

                graphics.drawString(font, text, x + dx, y + dy, blendedColor, false);
            }
        }

        // Основной текст
        graphics.drawString(font, text, x, y, textColor, false);
    }

    private int applyAlphaToColor(int color, float alphaFactor) {
        int alpha = (int) (((color >> 24) & 0xFF) * alphaFactor);
        int rgb = color & 0x00FFFFFF;
        return (alpha << 24) | rgb;
    }

    private void drawGradientText(GuiGraphics graphics, Font font, String text, int x, int y) {
        int length = text.length();
        int currentX = x;
        boolean obfuscate = styles.contains(TextStyle.OBFUSCATED);

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / Math.max(length - 1, 1);
            int blendedColor = blendColors(gradientStartColor.getColor(), gradientEndColor.getColor(), ratio);

            char charToRender = obfuscate ? (char) (33 + Math.random() * (126 - 33)) : text.charAt(i);

            graphics.drawString(font, Component.literal(applyStyles(String.valueOf(charToRender))), currentX, y, blendedColor);
            currentX += (int) (font.width(String.valueOf(charToRender)) + letterSpacing);
        }
    }

    private String applyObfuscation(String text) {
        StringBuilder obfuscatedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c != ' ') {
                obfuscatedText.append((char) (33 + Math.random() * (126 - 33)));
            } else {
                obfuscatedText.append(c);
            }
        }
        return obfuscatedText.toString();
    }

    private Interpolator getInterpolator(InterpolationType type) {
        return switch (type) {
            case LINEAR -> new LinearInterpolator();
            case QUADRATIC -> new QuadraticInterpolator();
            case EASE_IN -> new EaseInInterpolator();
            case EASE_OUT -> new EaseOutInterpolator();
        };
    }

    private int blendColors(int color1, int color2, float ratio) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = (color2 & 0xFF);

        int r = (int) (r1 + (r2 - r1) * ratio);
        int g = (int) (g1 + (g2 - g1) * ratio);
        int b = (int) (b1 + (b2 - b1) * ratio);

        return (r << 16) | (g << 8) | b;
    }

    private String applyStyles(String text) {
        for (TextStyle style : styles) {
            text = style.apply(text);
        }
        return text;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        boolean hovered = isMouseOver(mouseX, mouseY);
        System.out.println("MineGUIText clicked: " + hovered);
        return hovered;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        return false;
    }

    @Override
    public int getWidth() {
        // Получаем ширину строки
        return Minecraft.getInstance().font.width(text.getString());
    }

    @Override
    public int getHeight() {
        // Высота строки с учётом масштаба
        return (int) (Minecraft.getInstance().font.lineHeight * scale);
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        int width = getWidth();
        int height = getHeight();

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public static class Builder {
        private Component text;
        private int x, y;
        private ColorPalette color = ColorPalette.RED;
        private ColorPalette gradientStartColor;
        private ColorPalette gradientEndColor;
        private boolean useGradient = false;
        private int maxWidth = Integer.MAX_VALUE;
        private float scale = 1.0f;
        private float letterSpacing;
        private final EnumSet<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        private AnimationConfig animationConfig = new AnimationConfig(AnimationType.NONE, InterpolationType.LINEAR, 100);
        private boolean useOutline = false;
        private ColorPalette outlineColor = ColorPalette.BLACK;
        private int outlineThickness = 1;



        public Builder setText(Component text) {
            this.text = text;
            return this;
        }

        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setColor(ColorPalette color) {
            this.color = color;
            this.useGradient = false;
            return this;
        }

        public Builder setGradientColors(ColorPalette startColor, ColorPalette endColor) {
            this.gradientStartColor = startColor;
            this.gradientEndColor = endColor;
            this.useGradient = true;
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder addStyle(TextStyle style) {
            this.styles.add(style);
            return this;
        }

        public Builder setAnimation(AnimationType type, InterpolationType interpolation, int speed) {
            this.animationConfig = new AnimationConfig(type, interpolation, speed);
            return this;
        }

        public Builder setLetterSpacing(float spacing) {
            this.letterSpacing = spacing;
            return this;
        }

        public Builder withOutline(boolean useOutline) {
            this.useOutline = useOutline;
            return this;
        }

        public Builder outlineColor(ColorPalette color) {
            this.outlineColor = color;
            return this;
        }

        public Builder outlineThickness(int thickness) {
            this.outlineThickness = thickness;
            return this;
        }


        public MineGUIText build() {
            return new MineGUIText(this);
        }
    }

}
