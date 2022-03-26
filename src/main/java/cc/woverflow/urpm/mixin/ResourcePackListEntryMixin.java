package cc.woverflow.urpm.mixin;

import cc.woverflow.urpm.config.UntitledConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackListEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourcePackListEntry.class)
public class ResourcePackListEntryMixin {
    @Shadow @Final protected Minecraft mc;

    @Inject(method = "drawEntry", at = @At("HEAD"), cancellable = true)
    private void cancel(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo ci) {
        if (mc.theWorld == null) return;
        if (UntitledConfig.INSTANCE.getTransparentPackGUI()) {
            if (y < 32 - slotHeight || y > mc.currentScreen.height - 48) {
                ci.cancel();
            }
        }
    }
}
