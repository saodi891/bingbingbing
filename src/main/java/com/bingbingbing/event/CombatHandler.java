package com.bingbingbing.event;

import com.bingbingbing.Bingbingbing;
import com.bingbingbing.registry.ModSounds;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Thorns has no dedicated vanilla sound in 1.20.1, so this plays bingbingbing when the
 * thorns effect of a bingbingbing-enchanted armor piece triggers (additive by nature).
 */
@Mod.EventBusSubscriber(modid = Bingbingbing.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getSource().is(DamageTypes.THORNS)) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof LivingEntity living)) {
            return;
        }
        if (living.level().isClientSide()) {
            return;
        }
        if (ModEnchantHelper.wearsEnchantedArmor(living)) {
            living.level().playSound(null, living.getX(), living.getY(), living.getZ(),
                    ModSounds.BINGBINGBING.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}