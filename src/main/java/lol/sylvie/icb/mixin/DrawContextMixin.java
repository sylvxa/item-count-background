package lol.sylvie.icb.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import lol.sylvie.icb.ItemCountBackground;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow public abstract void fill(int x1, int y1, int x2, int y2, int color);

    @ModifyVariable(method = "drawStackCount", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int icb$offsetX(int x) {
        return x + ItemCountBackground.CONFIG.xOffset;
    }

    @ModifyVariable(method = "drawStackCount", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private int icb$offsetY(int y) {
        return y + ItemCountBackground.CONFIG.yOffset;
    }

    @Inject(method = "drawStackCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"))
    private void icb$addTextBackground(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackCountText, CallbackInfo ci, @Local(ordinal = 1) String actualText) {
        int textWidth = textRenderer.getWidth(actualText);
        // These magic numbers are from the original method
        int textX = x + 17 - textWidth;
        int textY = y + 9;

        int padding = ItemCountBackground.CONFIG.padding;
        int shadow = ItemCountBackground.CONFIG.textShadow ? 0 : 1;

        fill(textX - padding, textY - padding, textX + textWidth - shadow + padding, textY - 1 - shadow + textRenderer.fontHeight + padding, ItemCountBackground.CONFIG.color);
    }

    @ModifyArg(method = "drawStackCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"))
    public boolean icb$modifyTextShadow(boolean shadow) {
        return ItemCountBackground.CONFIG.textShadow;
    }
}
