package io.github.niko_sk2x.fastflyblockbreaking.mixin;

import io.github.niko_sk2x.fastflyblockbreaking.Config;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class FFBB extends LivingEntity {
    @Shadow @Final private Abilities abilities;

    protected FFBB(EntityType<? extends LivingEntity> type, Level world) {
        super(type, world);
    }

    @Redirect(method = "getDestroySpeed",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;onGround:Z", opcode = Opcodes.GETFIELD))
    public boolean blockBreakSpeed(Player _this){
        return this.onGround || this.abilities.mayfly || this.abilities.flying || Config.alwaysFastBreaking;
    }
}
