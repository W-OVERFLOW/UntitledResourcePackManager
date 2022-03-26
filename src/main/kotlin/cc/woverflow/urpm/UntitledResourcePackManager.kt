package cc.woverflow.urpm

import cc.woverflow.onecore.utils.Updater
import cc.woverflow.onecore.utils.command
import cc.woverflow.onecore.utils.openScreen
import cc.woverflow.urpm.config.UntitledConfig
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

@Mod(
    modid = UntitledResourcePackManager.ID,
    name = UntitledResourcePackManager.NAME,
    version = UntitledResourcePackManager.VER,
    modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter"
)
object UntitledResourcePackManager {

    const val NAME = "@NAME@"
    const val VER = "@VER@"
    const val ID = "@ID@"

    val modDir = File(File(Minecraft.getMinecraft().mcDataDir, "W-OVERFLOW"), NAME)

    @Mod.EventHandler
    fun onFMLPreInitialization(event: FMLPreInitializationEvent) {
        if (!modDir.exists()) modDir.mkdirs()
        Updater.addToUpdater(event.sourceFile, NAME, ID, VER, "W-OVERFLOW/$ID")
    }

    @Mod.EventHandler
    fun onInitialization(event: FMLInitializationEvent) {
        UntitledConfig.preload()
        command(ID) {
            main {
                UntitledConfig.openScreen()
            }
        }
    }
}
