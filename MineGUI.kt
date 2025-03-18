package ru.dimalab.minegui

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.slf4j.Logger
import ru.dimalab.minegui.screens.CreativeBLA
import ru.dimalab.minegui.screens.ModItems

@Mod(MineGUI.MODID)
class MineGUI {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus

        MinecraftForge.EVENT_BUS.register(this)

        CreativeBLA.register(modEventBus)
        ModItems.register(modEventBus)

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    companion object {
        const val MODID: String = "minegui"
        val LOGGER: Logger = LogUtils.getLogger()
    }
}