package cc.woverflow.urpm.mixin;

import cc.woverflow.urpm.config.UntitledConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiResourcePackList;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiSlot.class)
public abstract class GuiSlotMixin {

    @Shadow @Final protected Minecraft mc;

    @Shadow protected abstract void drawContainerBackground(Tessellator tessellator);

    @Shadow protected abstract void overlayBackground(int startY, int endY, int startAlpha, int endAlpha);

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;drawContainerBackground(Lnet/minecraft/client/renderer/Tessellator;)V"))
    private void redirectBackground(GuiSlot instance, Tessellator tessellator) {
        if (mc.theWorld == null || !UntitledConfig.INSTANCE.getTransparentPackGUI() || !(instance instanceof GuiResourcePackList)) {
            drawContainerBackground(tessellator);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiSlot;overlayBackground(IIII)V"))
    private void redirectOverlay(GuiSlot instance, int startY, int endY, int startAlpha, int endAlpha) {
        if (mc.theWorld == null || !UntitledConfig.INSTANCE.getTransparentPackGUI() || !(instance instanceof GuiResourcePackList)) {
            overlayBackground(startY, endY, startAlpha, endAlpha);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V", ordinal = 0))
    private void redirectDraw1(Tessellator instance) {
        if (mc.theWorld == null || !UntitledConfig.INSTANCE.getTransparentPackGUI() || !(mc.currentScreen instanceof GuiScreenResourcePacks)) {
            instance.draw();
        } else {
            instance.getWorldRenderer().finishDrawing();
            instance.getWorldRenderer().reset();
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V", ordinal = 1))
    private void redirectDraw2(Tessellator instance) {
        if (mc.theWorld == null || !UntitledConfig.INSTANCE.getTransparentPackGUI() || !(mc.currentScreen instanceof GuiScreenResourcePacks)) {
            instance.draw();
        } else {
            instance.getWorldRenderer().finishDrawing();
            instance.getWorldRenderer().reset();
        }
    }
}
