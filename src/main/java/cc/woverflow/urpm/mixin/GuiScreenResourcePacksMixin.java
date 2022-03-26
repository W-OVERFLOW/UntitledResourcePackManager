package cc.woverflow.urpm.mixin;

import cc.woverflow.urpm.config.UntitledConfig;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackRepository;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(GuiScreenResourcePacks.class)
public class GuiScreenResourcePacksMixin extends GuiScreen {

    @Shadow private List<ResourcePackListEntry> availableResourcePacks;

    @Shadow private GuiResourcePackAvailable availableResourcePacksList;

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;removeAll(Ljava/util/Collection;)Z"))
    private boolean captureArgs(List<ResourcePackRepository.Entry> instance, Collection<?> objects) {
        if (UntitledConfig.INSTANCE.getHideIncompatiblePacks()) {
            return instance.removeAll(objects) && instance.removeIf((f) -> f.func_183027_f() != 1);
        } else {
            return instance.removeAll(objects);
        }
    }

    @Redirect(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiScreenResourcePacks;availableResourcePacksList:Lnet/minecraft/client/gui/GuiResourcePackAvailable;", opcode = Opcodes.PUTFIELD))
    private void reversePacks(GuiScreenResourcePacks instance, GuiResourcePackAvailable value) {
        if (UntitledConfig.INSTANCE.getReversePacks()) {
            availableResourcePacks = new ArrayList<>(Lists.reverse(availableResourcePacks));
        }
        availableResourcePacksList = new GuiResourcePackAvailable(mc, value.width, value.height, availableResourcePacks);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreenResourcePacks;drawBackground(I)V"))
    private void redirectBackground(GuiScreenResourcePacks instance, int i) {
        if (UntitledConfig.INSTANCE.getTransparentPackGUI()) {
            drawDefaultBackground();
        } else {
            drawBackground(i);
        }
    }
}
