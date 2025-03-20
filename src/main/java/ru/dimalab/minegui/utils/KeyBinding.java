package ru.dimalab.minegui.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.minegui.open";
    public static final String KEY_OPEN_SCR = "key.minegui.open_screen";

    public static final KeyMapping OPEN_SCREEN_KEY = new KeyMapping(KEY_OPEN_SCR, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, KeyHelper.N.getId(), KEY_CATEGORY_TUTORIAL);
}
