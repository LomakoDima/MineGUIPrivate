package ru.dimalab.minegui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.dimalab.minegui.MineGUI;
import ru.dimalab.minegui.utils.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MineGUI.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.OPEN_SCREEN_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new ExampleScreen(Minecraft.getInstance(), "Example"));
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("§6[MineGUI] §3Интерфейс успешно открыт!"));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MineGUI.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_SCREEN_KEY);
        }
    }
}
