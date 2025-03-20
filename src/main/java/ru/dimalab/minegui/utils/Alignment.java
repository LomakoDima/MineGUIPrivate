package ru.dimalab.minegui.utils;

public enum Alignment {
    LEFT,
    CENTER,
    RIGHT,
    TOP,
    BOTTOM,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public int getX(int containerWidth, int elementWidth) {
        return switch (this) {
            case TOP, BOTTOM, LEFT, TOP_LEFT, BOTTOM_LEFT -> 0;
            case CENTER -> (containerWidth - elementWidth) / 2;
            case RIGHT, TOP_RIGHT, BOTTOM_RIGHT -> containerWidth - elementWidth;
        };
    }

    public int getY(int containerHeight, int elementHeight) {
        return switch (this) {
            case TOP, TOP_LEFT, TOP_RIGHT, LEFT, RIGHT -> 0;
            case CENTER -> (containerHeight - elementHeight) / 2;
            case BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT -> containerHeight - elementHeight;
        };
    }
}
