package lol.sylvie.icb.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import lol.sylvie.icb.ItemCountBackground;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Shadow public abstract void fill(int x1, int y1, int x2, int y2, int color);

    @ModifyVariable(method = "renderItemCount", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int icb$offsetX(int x) {
        return x + ItemCountBackground.CONFIG.xOffset;
    }

    @ModifyVariable(method = "renderItemCount", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private int icb$offsetY(int y) {
        return y + ItemCountBackground.CONFIG.yOffset;
    }

    @Inject(method = "renderItemCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    private void icb$addTextBackground(Font textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci, @Local(ordinal = 1) String actualText) {
        int textWidth = textRenderer.width(actualText);
        // These magic numbers are from the original method
        int textX = x + 17 - textWidth;
        int textY = y + 9;

        int padding = ItemCountBackground.CONFIG.padding;

        fill(textX - padding, textY - padding, textX + textWidth - 1 + padding, textY - 2 + textRenderer.lineHeight + padding, ItemCountBackground.CONFIG.color);
    }

    @ModifyArg(method = "renderItemCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    public boolean icb$modifyTextShadow(boolean shadow) {
        return ItemCountBackground.CONFIG.textShadow;
    }
}
