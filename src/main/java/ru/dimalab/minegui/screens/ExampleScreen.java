package ru.dimalab.minegui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import ru.dimalab.minegui.MineGUI;
import ru.dimalab.minegui.screens.utils.CollapsibleWindow;
import ru.dimalab.minegui.screens.utils.MineGUIHorizontalBox;
import ru.dimalab.minegui.utils.*;
import ru.dimalab.minegui.utils.math.interpolation.InterpolationType;
import ru.dimalab.minegui.widgets.*;
import ru.dimalab.minegui.widgets.custom.MineGUIWidget;

public class ExampleScreen extends MineGUIScreen {
    public ExampleScreen(Minecraft mc, String title) {
        super(mc, title);
    }

    @Override
    protected void initWidgets() {
        MineGUIHorizontalBox hBox = new MineGUIHorizontalBox(200, 50, 125);
        //MineGUIVerticalBox vBox = new MineGUIVerticalBox(200, 100, 10);


        MineGUIButton gradientButton = new MineGUIButton.Builder()
                //.setPosition(50, 50)
                .setSize(120, 30)
                .setText(Component.literal("Gradient"))
                .setOnClick(() -> System.out.println("Gradient Button Clicked!"))
                .setGradientColors(0xFF4A90E2, 0xFF0057A3, 0xFF76C7C0, 0xFF3B8D99)
                .setTextColor(0xFFFFFFFF)
                .setBorderColor(0xFFFFFFFF)
                .setBorderThickness(1)
                .build();

        MineGUIButton solidButton = new MineGUIButton.Builder()
                //.setPosition(50, 90)
                .setSize(120, 30)
                .setText(Component.literal("Solid Color"))
                .setOnClick(() -> System.out.println("Solid Button Clicked!"))
                .setSolidColor(0xFFFF5733, 0xFFC70039)
                .setTextColor(0xFFFFFFFF)
                .build();

        MineGUIButton button1 = new MineGUIButton.Builder()
                //.setPosition(50, 140)
                .setSize(120, 30)
                .setText(Component.literal("Click Me"))
                .setTextGradient(0xFF4A90E2, 0xFF0057A3)
                .setHoverTextGradient(0xFFFF5733, 0xFFC70039)
                .build();

        MineGUIButton button = new MineGUIButton.Builder()
                .setPosition(50, 200)
                .setSize(120, 30)
                .setText(Component.literal("Click Me"))
                .setHoverText(Component.literal("Hovered!"))
                .setTextStyle(TextStyle.ITALIC)
                .setHoverTextStyle(TextStyle.UNDERLINE)
                .setTextSize(9.0f)
                .setHoverTextSize(12.0f)  // Увеличиваем текст при наведении
                .setOnClick(() -> System.out.println("Button Clicked!"))
                .setSolidColor(0xFF4A90E2, 0xFF0057A3)
                .setTextColor(0xFFFFFFFF)
                .build();

        /*MineGUIText textWidget = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 50)
                .setColor(ColorPalette.WHITE)
                .build();

        MineGUIText textWidget1 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 70)
                .setColor(ColorPalette.WHITE)
                .setMaxWidth(200)
                .build();

        MineGUIText textWidget12 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 110)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .build();

        MineGUIText textWidget123 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 130)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setMaxWidth(200)
                .build();

        MineGUIText textWidget1234 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 170)
                .setColor(ColorPalette.WHITE)
                .addStyle(TextStyle.STRIKETHROUGH)
                .build();

        MineGUIText textWidget12345 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 190)
                .setColor(ColorPalette.WHITE)
                .addStyle(TextStyle.UNDERLINE)
                .build();

        MineGUIText textWidget123456 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 210)
                .setColor(ColorPalette.WHITE)
                .addStyle(TextStyle.BOLD)
                .build();

        MineGUIText textWidget1234567 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 230)
                .setColor(ColorPalette.WHITE)
                .addStyle(TextStyle.ITALIC)
                .build();

        MineGUIText textWidget12345678 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 250)
                .setColor(ColorPalette.WHITE)
                .addStyle(TextStyle.OBFUSCATED)
                .build();

        MineGUIText textLetter = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически"))
                .setPosition(250, 270)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setLetterSpacing(4f)
                .build();

        MineGUIText exampleAnim1 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)" + " ( " + InterpolationType.LINEAR + " )"))
                .setPosition(250, 290)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setAnimation(AnimationType.SCALE_UP, InterpolationType.LINEAR, 1500)
                .build();

        MineGUIText exampleAnim2 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"  + " ( " + InterpolationType.QUADRATIC + " )"))
                .setPosition(250, 310)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setAnimation(AnimationType.SCALE_UP, InterpolationType.QUADRATIC, 1500)
                .build();

        MineGUIText exampleAnim3 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"  + " ( " + InterpolationType.EASE_IN + " )"))
                .setPosition(250, 330)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setAnimation(AnimationType.SCALE_UP, InterpolationType.EASE_IN, 1500)
                .build();

        MineGUIText exampleAnim4 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"  + " ( " + InterpolationType.EASE_OUT + " )"))
                .setPosition(250, 350)
                //.setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                //.setAnimation(AnimationType.SCALE_UP, InterpolationType.EASE_OUT, 1500)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .withOutline(true)
                .outlineColor(ColorPalette.BLACK)
                .outlineThickness(1)
                .build();

        MineGUIText exampleAnim5 = new MineGUIText.Builder()
                .setText(Component.literal("Это текст который должен переноситься автоматически, а если нет то значит и фиг с ним :)"))
                .setPosition(250, 370)
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setAnimation(AnimationType.TYPEWRITER, InterpolationType.EASE_IN, 60)
                .build();

        MineGUIText mineText = new MineGUIText.Builder()
                .setPosition(250, 390)
                .setText(Component.literal("Увеличенный текст"))
                .setGradientColors(ColorPalette.CRIMSON, ColorPalette.LIGHT_SALMON)
                .setScale(2f)
                .build();

        MineGUIImage mineGUIImage = new MineGUIImage.Builder()
                .position(20, 350)
                .size(128, 128)
                .texture(ResourceLocation.fromNamespaceAndPath(MineGUI.MODID, "textures/gui/button.png"))
                .border(true)                          // Включаем обводку
                .borderColor(0xFFFF0000)               // Красный цвет ARGB (полностью непрозрачный)
                .borderThickness(2)
                .build();*/

        //MineGUICountdown countdown = new MineGUICountdown(250, 300, 100, () -> {});

        MineGUISlider slider = new MineGUISlider.Builder()
                .setPosition(50, 100)
                .setSize(200, 10)
                .setRange(0, 100, 5)
                .setDefaultValue(50)
                .setLabel(Component.literal("Громкость"))
                .setColors(
                        ColorPalette.DARK_GRAY,
                        ColorPalette.EMERALD,
                        ColorPalette.GOLD,
                        ColorPalette.BLACK,
                        ColorPalette.WHITE
                )
                .setOnValueChanged(value -> MineGUI.LOGGER.info("Новое значение: {}", value))
                .build();


        MineGUITabs tabs = new MineGUITabs.Builder()
                .setPosition(20, 10)
                .setSize(200, 30, 100)
                .addTab("Главная", 0, ColorPalette.DARK_GRAY, ColorPalette.CHOCOLATE, ColorPalette.WHITE)
                .addTab("Настройки", 1, ColorPalette.DARK_GRAY, ColorPalette.CHOCOLATE, ColorPalette.WHITE)
                .addTab("О нас", 2, ColorPalette.DARK_GRAY, ColorPalette.CHOCOLATE, ColorPalette.WHITE)
                .setOnTabSelected(tab -> System.out.println("Выбрана вкладка: " + tab.getLabel()))
                .build();


        MineGUIMarquee marquee = new MineGUIMarquee.Builder()
                .setText("Это работает,  не так ли?")
                .setPosition(50, 130)
                .setWidth(200)
                .setColor(ColorPalette.LIGHT_CORAL)
                .setSpeed(3f)
                .build();

        MineGUICheckBox checkBox = new MineGUICheckBox.Builder(10, 20, 16, Component.literal("Check me!"))
                .defaultValue(true)
                .boxColor(ColorPalette.LIGHT_GRAY)
                .borderColor(ColorPalette.BLACK)
                .checkColor(ColorPalette.EMERALD)
                .textColor(ColorPalette.WHITE)
                .onToggle(checked -> {
                    MineGUI.LOGGER.info("Checkbox toggled! Checked: {}", checked);
                })
                .build();

        MineGUITable table = new MineGUITable.Builder()
                .position(600, 150)
                .columns(3)
                .columnWidth(100)
                .rowHeight(20)
                .headerColor(ColorPalette.DARK_BLUE)
                .rowColor1(ColorPalette.SILVER)
                .rowColor2(ColorPalette.LIGHT_GRAY)
                .textColor(ColorPalette.BLACK)
                .borderColor(ColorPalette.NAVY)
                .build();

        table.setHeaders("ID", "Name", "Status");

        table.addRow("1", "Alice", "Active");
        table.addRow("2", "Bob", "Inactive");
        table.addRow("3", "Charlie", "Pending");


        CollapsibleWindow window = new CollapsibleWindow.Builder()
                .setPosition(20, 300)
                .setSize(350, 100)
                .setTitle("Debug")
                .build();

        CollapsibleWindow window1 = new CollapsibleWindow.Builder()
                .setPosition(120, 300)
                .setSize(350, 100)
                .setTitle("Debug1")
                .build();

        MineGUIMultilineTextField mineGUIMultilineTextField = new MineGUIMultilineTextField(100, 400, 200, 100, "Введите текст");

        //MineGUIBlinkingCursor blinkingCursor = new MineGUIBlinkingCursor(200, 200);

        Level level = Minecraft.getInstance().level;

        assert level != null;
        Villager creeper = EntityType.VILLAGER.create(level);

        if (creeper != null) {
            // Настраиваем начальные параметры (необязательно)
            creeper.setNoAi(true);            // Отключаем ИИ, чтобы не двигался
            creeper.setInvulnerable(true);    // Неуязвимость
            creeper.setSilent(true);          // Без звука

            MineGUIEntityWidget entityWidget = new MineGUIEntityWidget.Builder()
                    .position(400, 122)
                    .size(150, 200)
                    .scale(1f)
                    //.shadow(true)
                    .rotateOnMouse(true)
                    .offset(2, -5)
                    .entity(creeper)
                    //.borderColor(ColorPalette.EMERALD)
                    .background(ResourceLocation.fromNamespaceAndPath(MineGUI.MODID, "textures/gui/entity_overlay.png"))
                    .backgroundSize(150, 210)
                    .backgroundPosition(400, 110)
                    .build();





            window.addChild(button1, 20, 5);


        hBox.addChild(gradientButton);
        hBox.addChild(solidButton);
        hBox.addChild(button);

        //window1.addChild(hBox, 10, 2);

        // вертикальный контейнер
        //vBox.addChild(button);
        //vBox.addChild(gradientButton);
        //vBox.addChild(solidButton);



        addWidgets(/*gradientButton,*/ /*textWidget,*/ /*solidButton, button1*/ /*textWidget1,
                textWidget12, textWidget123,  textWidget1234, textWidget12345, mineText,
                textWidget123456, textWidget1234567, textWidget12345678, textLetter, exampleAnim1,
                exampleAnim2, exampleAnim3, exampleAnim4, exampleAnim5, mineGUIImage,*/ /*canvasPanel*/
                hBox, slider, tabs, marquee, checkBox, table, entityWidget, window, mineGUIMultilineTextField
                //vBox
        );

        }
    }
}
