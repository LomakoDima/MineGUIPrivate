package ru.dimalab.minegui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import ru.dimalab.minegui.manager.ScreenManager;

public class TestOpen extends Item {

    public TestOpen(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            Minecraft.getInstance().execute(() -> {
                ScreenManager.openScreen(new TestScreen(Minecraft.getInstance()));
                ScreenManager.printStack();
            });
        }
        return InteractionResult.SUCCESS;
    }
}
