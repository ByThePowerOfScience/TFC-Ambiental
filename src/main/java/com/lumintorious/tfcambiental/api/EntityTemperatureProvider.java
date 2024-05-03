package com.lumintorious.tfcambiental.api;

import com.lumintorious.tfcambiental.TFCAmbiental;
import com.lumintorious.tfcambiental.modifier.TempModifier;
import com.lumintorious.tfcambiental.modifier.TempModifierStorage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@FunctionalInterface
public interface EntityTemperatureProvider
{
    Optional<TempModifier> getModifier(Player player);

    static void evaluateAll(Player player, TempModifierStorage storage) {
        for (var fn : AmbientalRegistry.ENTITIES) {
            storage.add(fn.getModifier(player));
        }
    }

    static Optional<TempModifier> handleHotEntities(Player player) {
        List<Entity> nearbyEntities = player.level().getEntitiesOfClass(Entity.class, (new AABB(player.blockPosition()).inflate(5.0D, 2.0D, 5.0D)));
        if (nearbyEntities.size() > 0) {
            AtomicReference<Float> change = new AtomicReference<>(0.0f);
            nearbyEntities.forEach(entity -> {
                if (entity.getType().is(TFCAmbiental.HOT_ENTITIES)) {
                    change.updateAndGet(v -> v + 1.0f);
                }
            });
            return TempModifier.defined("hot_entity", change.get(), 0);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleColdEntities(Player player) {
        List<Entity> nearbyEntities = player.level().getEntitiesOfClass(Entity.class, (new AABB(player.blockPosition()).inflate(5.0D, 2.0D, 5.0D)));
        if (nearbyEntities.size() > 0) {
            AtomicReference<Float> change = new AtomicReference<>(0.0f);
            nearbyEntities.forEach(entity -> {
                if (entity.getType().is(TFCAmbiental.COLD_ENTITIES)) {
                    change.updateAndGet(v -> v + 1.0f);
                }
            });
            return TempModifier.defined("cold_entity", -1 * change.get(), 0);
        }
        return TempModifier.none();
    }
}
