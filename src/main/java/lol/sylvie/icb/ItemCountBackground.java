package lol.sylvie.icb;

import lol.sylvie.icb.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class ItemCountBackground implements ClientModInitializer {
    public static final String MOD_ID = "item-count-background";

    public static ModConfig CONFIG = null;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
