package com.bingbingbing.mixin;

import com.bingbingbing.registry.ModSounds;
import com.bingbingbing.util.ModEnchantHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected abstract void spawnItemParticles(ItemStack stack, int count);

    @Inject(method = "breakItem", at = @At("HEAD"), cancellable = true)
    private void bingbingbing$replaceBreakSound(ItemStack stack, CallbackInfo ci) {
        if (stack.isEmpty() || !ModEnchantHelper.hasEnchant(stack)) {
            return;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (!self.isSilent()) {
            self.level().playLocalSound(self.getX(), self.getY(), self.getZ(),
                    ModSounds.BINGBINGBING.get(), self.getSoundSource(),
                    0.8F, 0.8F + self.level().getRandom().nextFloat() * 0.4F, false);
        }
        spawnItemParticles(stack, 5);
        ci.cancel();
    }

    @Inject(method = "playHurtSound", at = @At("HEAD"), cancellable = true)
    private void bingbingbing$replaceHurtSound(DamageSource source, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean armorMitigated = !source.is(DamageTypeTags.BYPASSES_ARMOR) && self.getArmorValue() > 0;
        if (armorMitigated && ModEnchantHelper.wearsEnchantedArmor(self)) {
            self.level().playSound(null, self.getX(), self.getY(), self.getZ(),
                    ModSounds.BINGBINGBING.get(), self.getSoundSource(), 1.0F, 1.0F);
            ci.cancel();
        }
    }
}