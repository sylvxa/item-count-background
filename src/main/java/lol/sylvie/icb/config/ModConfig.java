package lol.sylvie.icb.config;

import lol.sylvie.icb.ItemCountBackground;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ItemCountBackground.MOD_ID)
public class ModConfig implements ConfigData {
    public boolean textShadow = false;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int color = 0xaa000000;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 32)
    public int padding = 1;

    @ConfigEntry.BoundedDiscrete(min = -64, max = 64)
    public int xOffset = -1;

    @ConfigEntry.BoundedDiscrete(min = -64, max = 64)
    public int yOffset = -1;
}
