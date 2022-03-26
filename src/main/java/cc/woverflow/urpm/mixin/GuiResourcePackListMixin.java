package cc.woverflow.urpm.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiResourcePackList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiResourcePackList.class)
public class GuiResourcePackListMixin {

    @Redirect(method = "drawListHeader", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int redirectDrawString(FontRenderer instance, String text, int x, int y, int color) {
        if (y > 23) {
            return instance.drawString(text, x, y, color);
        }
        return 0;
    }
}
