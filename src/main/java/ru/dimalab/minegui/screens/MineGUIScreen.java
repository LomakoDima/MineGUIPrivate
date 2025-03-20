package ru.dimalab.minegui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.dimalab.minegui.MineGUI;
import ru.dimalab.minegui.manager.ScreenManager;
import ru.dimalab.minegui.utils.ColorPalette;
import ru.dimalab.minegui.utils.KeyHelper;
import ru.dimalab.minegui.widgets.MineGUIButton;
import ru.dimalab.minegui.widgets.MineGUIText;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MineGUIScreen extends Screen {
    private int x;
    private int y;

    protected final Minecraft minecraftInstance;
    protected final List<MineGUIWidget> widgets = new ArrayList<>();

    protected boolean debugMode = false;
    protected MineGUIButton addButton;
    protected MineGUIText debugText;
    protected List<MineGUIButton> addMenuButtons = new ArrayList<>();

    private MineGUIWidget draggingWidget = null;
    private int dragOffsetX, dragOffsetY;

    public MineGUIScreen(Minecraft mc, String title) {
        super(Component.literal(title));
        this.minecraftInstance = mc;
        this.minecraft = mc;
    }

    protected abstract void initWidgets();

    @Override
    protected void init() {
        super.init();
        widgets.clear();
        initWidgets();
        if (debugMode) {
            createAddButton();
        }
    }

    @Override
    public void tick() {
        super.tick();

        for (MineGUIWidget widget : widgets) {
            widget.tick();
        }

        if (debugMode) {
            if (addButton != null) addButton.tick();
            for (MineGUIButton menuButton : addMenuButtons) {
                menuButton.tick();
            }
        }
    }

    public void addWidgets(MineGUIWidget... widgetsToAdd) {
        Collections.addAll(widgets, widgetsToAdd);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.minecraft == null) return;

        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        for (MineGUIWidget widget : widgets) {
            widget.render(guiGraphics, this.font, mouseX, mouseY);
        }

        if (debugMode) {
            if (addButton != null) addButton.render(guiGraphics, this.font, mouseX, mouseY);
            for (MineGUIButton menuButton : addMenuButtons) {
                menuButton.render(guiGraphics, this.font, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean handled = false;


            if (addButton != null) {
                handled |= addButton.mouseClicked((int) mouseX, (int) mouseY, button);
            }

            List<MineGUIButton> buttonsCopy = new ArrayList<>(addMenuButtons);
            for (MineGUIButton menuButton : buttonsCopy) {
                if (menuButton.mouseClicked((int) mouseX, (int) mouseY, button)) {
                    return true;
                }
            }



            for (MineGUIWidget widget : widgets) {
                if (widget.mouseClicked((int) mouseX, (int) mouseY, button)) {
                    handled = true;

                    // Захватываем виджет для перетаскивания
                    draggingWidget = widget;
                    dragOffsetX = (int) mouseX - widget.getX();
                    dragOffsetY = (int) mouseY - widget.getY();

                    setDragging(true);
                }
            }




            for (MineGUIWidget widget : widgets) {
                boolean isHovered = isMouseOver(widget, (int) mouseX, (int) mouseY);
                if (isHovered && button == 0) { // ЛКМ
                    draggingWidget = widget;
                    dragOffsetX = (int) mouseX - widget.getX();
                    dragOffsetY = (int) mouseY - widget.getY();

                    setDragging(true);
                    MineGUI.LOGGER.info("Начали перетаскивание: {}", widget);

                    // Дополнительно: вызов mouseClicked у виджета
                    widget.mouseClicked((int) mouseX, (int) mouseY, button);

                    return true; // перехватываем событие
                }

            }



        return handled || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingWidget != null) {
            draggingWidget.mouseReleased((int) mouseX, (int) mouseY, button);
            draggingWidget = null;
            setDragging(false);
            return true;
        }


            if (addButton != null) addButton.mouseReleased((int) mouseX, (int) mouseY, button);
            for (MineGUIButton menuButton : addMenuButtons) {
                menuButton.mouseReleased((int) mouseX, (int) mouseY, button);
            }


        for (MineGUIWidget widget : widgets) {
            widget.mouseReleased((int) mouseX, (int) mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == KeyHelper.D.getId()) {
            debugMode = !debugMode;

            if (debugMode) {
                createAddButton();
                addDebugText();
            } else {
                removeDebugButtons();
                removeDebugText();

            }

            return true;
        }

        boolean handled = false;

        for (MineGUIWidget widget : widgets) {
            handled |= widget.keyPressed(keyCode, scanCode, modifiers);
        }

        return handled || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingWidget != null && button == 0) {
            int newX = (int) mouseX - dragOffsetX;
            int newY = (int) mouseY - dragOffsetY;

            draggingWidget.setPosition(newX, newY);
            draggingWidget.mouseDragged((int) mouseX, (int) mouseY, button, deltaX, deltaY);

            MineGUI.LOGGER.info("Перетаскиваем виджет: {}", draggingWidget);

            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        for (MineGUIWidget widget : widgets) {
            if (widget.charTyped(codePoint, modifiers)) {
                return true;
            }
        }
        return super.charTyped(codePoint, modifiers);
    }

    private boolean isMouseOver(MineGUIWidget widget, int mouseX, int mouseY) {
        return mouseX >= widget.getX() &&
                mouseY >= widget.getY() &&
                mouseX < widget.getX() + widget.getWidth() &&
                mouseY < widget.getY() + widget.getHeight();
    }

    protected void createAddButton() {
        addButton = new MineGUIButton.Builder()
                .setPosition(this.width - 30, 10)
                .setSize(20, 20)
                .setText(Component.literal("+"))
                .setGradientColors(0xFF4A90E2, 0xFF0057A3, 0xFF76C7C0, 0xFF3B8D99)
                .setTextColor(0xFFFFFFFF)
                .setOnClick(this::toggleAddMenu)
                .build();
    }

    protected void toggleAddMenu() {
        if (addMenuButtons.isEmpty()) {
            createAddMenu();
        } else {
            addMenuButtons.clear();
        }
    }

    protected void createAddMenu() {
        addMenuButtons.clear();

        MineGUIButton textButton = new MineGUIButton.Builder()
                .setPosition(this.width - 140, 10)
                .setSize(100, 20)
                .setText(Component.literal("Добавить текст"))
                .setSolidColor(0xFF555555, 0xFF333333)
                .setTextColor(0xFFFFFFFF)
                .setOnClick(() -> {
                    addTextWidget();
                    addMenuButtons.clear();
                })
                .build();

        MineGUIButton buttonButton = new MineGUIButton.Builder()
                .setPosition(this.width - 140, 35)
                .setSize(100, 20)
                .setText(Component.literal("Добавить кнопку"))
                .setSolidColor(0xFF555555, 0xFF333333)
                .setTextColor(0xFFFFFFFF)
                .setOnClick(() -> {
                    addButtonWidget();
                    addMenuButtons.clear();
                })
                .build();

        addMenuButtons.add(textButton);
        addMenuButtons.add(buttonButton);
    }

    protected void removeDebugButtons() {
        addButton = null;
        addMenuButtons.clear();
    }

    protected void removeDebugText() {
        widgets.remove(debugText);
        debugText = null;
    }

    protected void addTextWidget() {
        int offset = widgets.size() * 20; // Смещение вниз на 20 пикселей за каждый добавленный виджет
        MineGUIText text = new MineGUIText.Builder()
                .setText(Component.literal("Текст #" + (widgets.size() + 1)))
                .setPosition(100, 100 + offset)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .build();

        addWidgets(text);
    }

    protected void addButtonWidget() {
        int offset = widgets.size() * 20;
        MineGUIButton button = new MineGUIButton.Builder()
                .setPosition(100, 150 + offset)
                .setSize(100, 20)
                .setText(Component.literal("Кнопка #" + (widgets.size() + 1)))
                .setOnClick(() -> System.out.println("Нажата кнопка #" + widgets.size()))
                .setSolidColor(0xFF4A90E2, 0xFF0057A3)
                .setTextColor(0xFFFFFFFF)
                .build();

        addWidgets(button);
    }

    protected void addDebugText() {
        debugText = new MineGUIText.Builder()
                .setText(Component.literal("РЕЖИМ ОТЛАДКИ"))
                .setPosition(280, 2)
                .setColor(ColorPalette.RED) // Красный -> Жёлтый
                .setScale(1.5f) // Увеличенный размер шрифта
                .build();

        addWidgets(debugText);
    }

    @Override
    public void onClose() {
        ScreenManager.closeScreen();
    }
}
