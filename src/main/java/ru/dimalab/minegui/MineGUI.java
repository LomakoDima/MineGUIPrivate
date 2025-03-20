package ru.dimalab.minegui;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dimalab.minegui.screens.CreativeBLA;
import ru.dimalab.minegui.screens.ModItems;

@Mod(MineGUI.MODID)
public class MineGUI {
    public static final String MODID = "minegui";
    public static final Logger LOGGER = LoggerFactory.getLogger(MineGUI.class);

    public MineGUI() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        MinecraftForge.EVENT_BUS.register(this);

        CreativeBLA.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
