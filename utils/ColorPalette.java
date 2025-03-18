package ru.dimalab.minegui.utils;

public enum ColorPalette {
    // Reds
    RED(0xFFFF0000),
    DARK_RED(0xFF8B0000),
    FIREBRICK(0xFFB22222),
    CRIMSON(0xFFDC143C),
    INDIAN_RED(0xFFCD5C5C),
    LIGHT_CORAL(0xFFF08080),
    SALMON(0xFFFA8072),
    DARK_SALMON(0xFFE9967A),
    LIGHT_SALMON(0xFFFFA07A),

    // Oranges
    ORANGE(0xFFFFA500),
    DARK_ORANGE(0xFFFF8C00),
    CORAL(0xFFFF7F50),
    TOMATO(0xFFFF6347),
    ORANGE_RED(0xFFFF4500),
    GOLD(0xFFFFD700),

    // Yellows
    YELLOW(0xFFFFFF00),
    LIGHT_YELLOW(0xFFFFFFE0),
    LEMON_CHIFFON(0xFFFFFACD),
    LIGHT_GOLDENROD(0xFFFAFAD2),

    // Greens
    GREEN(0xFF008000),
    DARK_GREEN(0xFF006400),
    LIME(0xFF00FF00),
    LIME_GREEN(0xFF32CD32),
    SPRING_GREEN(0xFF00FF7F),
    SEA_GREEN(0xFF2E8B57),
    MEDIUM_SEA_GREEN(0xFF3CB371),

    // Blues
    BLUE(0xFF0000FF),
    DARK_BLUE(0xFF00008B),
    MIDNIGHT_BLUE(0xFF191970),
    NAVY(0xFF000080),
    ROYAL_BLUE(0xFF4169E1),
    SKY_BLUE(0xFF87CEEB),
    LIGHT_SKY_BLUE(0xFF87CEFA),
    DEEP_SKY_BLUE(0xFF00BFFF),

    // Purples
    PURPLE(0xFF800080),
    MEDIUM_PURPLE(0xFF9370DB),
    BLUE_VIOLET(0xFF8A2BE2),
    INDIGO(0xFF4B0082),
    PLUM(0xFFDDA0DD),
    ORCHID(0xFFDA70D6),

    // Browns
    BROWN(0xFFA52A2A),
    SADDLE_BROWN(0xFF8B4513),
    SIENNA(0xFFA0522D),
    CHOCOLATE(0xFFD2691E),

    // Grays
    BLACK(0xFF000000),
    DIM_GRAY(0xFF696969),
    GRAY(0xFF808080),
    DARK_GRAY(0xFFA9A9A9),
    SILVER(0xFFC0C0C0),
    LIGHT_GRAY(0xFFD3D3D3),
    WHITE(0xFFFFFFFF);

    private final int color;

    ColorPalette(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public static ColorPalette fromHex(int hex) {
        for (ColorPalette c : values()) {
            if (c.color == hex) {
                return c;
            }
        }
        throw new IllegalArgumentException("Цвет не найден: " + Integer.toHexString(hex));
    }
}
