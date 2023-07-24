package com.peasenet.mods.esp;

import com.peasenet.config.EspConfig;
import com.peasenet.gavui.color.Colors;
import com.peasenet.main.Settings;
import com.peasenet.mods.Mod;
import com.peasenet.mods.ModCategory;
import com.peasenet.settings.SettingBuilder;
import com.peasenet.util.PlayerUtils;
import com.peasenet.util.RenderUtils;
import com.peasenet.util.event.EventManager;
import com.peasenet.util.event.data.BlockEntityRender;
import com.peasenet.util.listeners.BlockEntityRenderListener;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

/**
 * An example mod for the GavinsMod client.
 */

/*
    By extending the Mod class, we can use the Mod class's methods and variables, as well as hook into
    the GavinsMod client's events if needed.
 */
public class ModSignEsp extends EspMod implements BlockEntityRenderListener {
    public ModSignEsp() {
        super(
                "Sign ESP",
                "gavinsmod.mod.esp.signesp",
                "signesp",
                GLFW.GLFW_KEY_UNKNOWN
        );
        var cfg = (EspConfig) Settings.getConfig("esp");
        var menu = new SettingBuilder()
                .setTitle(this.getTranslationKey())
                .setColor(cfg.getSignColor())
                .buildColorSetting();
        menu.setCallback(()-> {
            cfg.setSignColor(menu.getColor());
        });
        addSetting(menu);
    }
    
    @Override
    public void onEnable() {
        EventManager.getEventManager().subscribe(BlockEntityRenderListener.class, this);
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        EventManager.getEventManager().unsubscribe(BlockEntityRenderListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRenderBlockEntity(BlockEntityRender er) {
        if (!(er.getEntity() instanceof SignBlockEntity))
            return;
        var box = new Box(er.getEntity().getPos());
        var cfg = (EspConfig) Settings.getConfig("esp");
        RenderUtils.INSTANCE.drawBox(er.getStack(), er.getBuffer(), box, cfg.getSignColor(), cfg.getAlpha());
    }
}
