package ru.dimalab.minegui.utils;

public enum Visibility {
    VISIBLE,   // Полностью видимый элемент (рендерится и обрабатывает события)
    HIDDEN,    // Не рендерится, но может обрабатывать события (опционально)
    GONE;      // Полностью отключён (не рендерится и не обрабатывает события)

    public boolean isVisible() {
        return this == VISIBLE;
    }

    public boolean isGone() {
        return this == GONE;
    }

    public boolean isHidden() {
        return this == HIDDEN;
    }
}
