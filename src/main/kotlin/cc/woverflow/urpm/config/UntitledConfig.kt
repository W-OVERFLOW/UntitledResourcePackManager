package cc.woverflow.urpm.config

import cc.woverflow.urpm.UntitledResourcePackManager
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import java.io.File

object UntitledConfig : Vigilant(File(UntitledResourcePackManager.modDir, "${UntitledResourcePackManager.ID}.toml"), UntitledResourcePackManager.NAME) {

    @Property(
        type = PropertyType.SWITCH,
        name = "Pack Search Box",
        category = "General",
        description = "Add a search box to search packs in the pack GUI.",
    )
    var packSearchBox = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Remove Pack GUI Background",
        category = "General",
        description = "Remove the dirt background in the pack GUI.",
    )
    var transparentPackGUI = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Incompatible Packs",
        category = "General",
        description = "Remove incompatible packs from the pack GUI.",
    )
    var hideIncompatiblePacks = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Reverse Packs",
        category = "General",
        description = "Reverse the order of available packs in the pack GUI.",
    )
    var reversePacks = false

    init {
        initialize()
    }
}