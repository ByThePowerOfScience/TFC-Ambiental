package com.lumintorious.tfcambiental.api;

import com.lumintorious.tfcambiental.TFCAmbiental;
import com.lumintorious.tfcambiental.TFCAmbientalConfig;
import com.lumintorious.tfcambiental.capability.TemperatureCapability;
import com.lumintorious.tfcambiental.modifier.TempModifier;
import com.lumintorious.tfcambiental.modifier.TempModifierStorage;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.dries007.tfc.common.capabilities.food.TFCFoodData;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;

import java.util.Optional;
import java.util.Set;

@FunctionalInterface
public interface EnvironmentalTemperatureProvider
{
    Optional<TempModifier> getModifier(Player player);

    static boolean calculateEnclosure(Player player, int radius) {
        PathNavigationRegion region = new PathNavigationRegion(
                player.level(),
                player.getOnPos().above().offset(-radius, -radius, -radius),
                player.getOnPos().above().offset(radius, 400, radius)
        );
        Bee guineaPig = new Bee(EntityType.BEE, player.level());
        guineaPig.setPos(player.getPosition(0));
        guineaPig.setBaby(true);
        FlyNodeEvaluator evaluator = new FlyNodeEvaluator();
        PathFinder finder = new PathFinder(evaluator, 500);
        Path path = finder.findPath(
                region,
                guineaPig,
                Set.of(player.getOnPos().above().atY(258)),
                500,
                0,
                12
        );
        return path == null || path.getNodeCount() < 255 - player.getOnPos().above().getY();
    }

    static float getEnvironmentTemperatureWithTimeOfDay(Player player) {
        return getEnvironmentTemperature(player) + handleTimeOfDay(player).get().getChange();
    }

    static float getEnvironmentTemperature(Player player) {
        float actual = Climate.getTemperature(player.level(), player.getOnPos());
        float diff = actual - 15;
        return 20 + (diff + 0.5f * Math.signum(diff));
    }

    static float getEnvironmentHumidity(Player player) {
        return Climate.getRainfall(player.level(), player.getOnPos()) / 3000;
    }

    static int getSkylight(Player player) {
        BlockPos pos = new BlockPos(player.getOnPos()).above(1);
        return player.level().getBrightness(LightLayer.SKY, pos);
    }

    static int getBlockLight(Player player) {
        BlockPos pos = new BlockPos(player.getOnPos()).above(1);
        return player.level().getBrightness(LightLayer.BLOCK, pos);
    }

    static Optional<TempModifier> handleFire(Player player) {
        if (player.isOnFire()) {
            TempModifier.defined("on_fire", 4f, 4f, -1f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleGeneralTemperature(Player player) {
        return Optional.of(new TempModifier("environment", getEnvironmentTemperature(player), getEnvironmentHumidity(player)));
    }

    static Optional<TempModifier> handleTimeOfDay(Player player) {
        int dayTicks = (int) (player.level().dayTime() % 24000);
        if (dayTicks < 6000) {
            return TempModifier.defined("morning", 2f, 0);
        } else if (dayTicks < 12000) {
            return TempModifier.defined("afternoon", 4f, 0, -0.02f);
        } else if (dayTicks < 18000) {
            return TempModifier.defined("evening", 1f, 0);
        } else {
            return TempModifier.defined("night", 1f, 0);
        }
    }

    static Optional<TempModifier> handleWater(Player player) {
        if (player.isInWater()) {
            BlockPos pos = player.getOnPos().above();
            BlockState state = player.level().getBlockState(pos);
            if (state.getFluidState().is(TFCAmbiental.SPRING_WATER)) {
                return TempModifier.defined("in_hot_water", 5f, 6f, 10f);
            } else if (state.getBlock() == Blocks.LAVA) {
                return TempModifier.defined("in_lava", 10f, 5f, -10f);
            }
            return TempModifier.defined("in_water", -5f, 6f, 10f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleRain(Player player) {
        if (player.level().isRaining()) {
            var isInRain = player.level().isRainingAt(player.blockPosition());
            if (getSkylight(player) < 15) {
                return TempModifier.defined("weather", -2f, 0.1f, isInRain ? 0.5f : 0);
            }
            return TempModifier.defined("weather", -4f, 0.3f, isInRain ? 4f : 0.5f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleWind(Player player) {
        return player.getCapability(TemperatureCapability.CAPABILITY).map(temperatureCapability -> {
            var wind = Climate.getWindVector(player.level(), player.blockPosition());
            // Can lower the player down to 3 under ambient temperature and makes cooling faster
            float temperature = temperatureCapability.getTemperature();
            float targetTemperature = temperatureCapability.getTargetTemperature();
            float potency = temperature < targetTemperature ? 0.1f * temperatureCapability.getWetness() * wind.length() : 0f;
            float change = temperature > EnvironmentalTemperatureProvider.getEnvironmentTemperature(player) - 3f ? -0.01f : 0f;
            return TempModifier.defined("weather", change * wind.length(), potency);

        }).orElse(TempModifier.none());
    }

    static Optional<TempModifier> handleSprinting(Player player) {
        if (player.isSprinting()) {
            return TempModifier.defined("sprint", 2f, 0.3f, -0.05f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleUnderground(Player player) {
        if (getSkylight(player) < 2) {
            return TempModifier.defined("underground", -6f, 0.2f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleShade(Player player) {
        int light = Math.max(12, getSkylight(player));
        if (light < 15) {
            float temp = getEnvironmentTemperatureWithTimeOfDay(player);
            float avg = TFCAmbientalConfig.COMMON.averageTemperature.get().floatValue();
            if (temp > avg) {
                return TempModifier.defined("shade", -Math.abs(avg - temp) * 0.6f, 0f);
            }
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleCozy(Player player) {
        float temp = getEnvironmentTemperatureWithTimeOfDay(player);
        float avg = TFCAmbientalConfig.COMMON.averageTemperature.get().floatValue();

        if (temp < avg - 1) {
            final boolean[] isInside = {false};
            player.getCapability(TemperatureCapability.CAPABILITY).ifPresent(temperatureCapability -> {
                if (player.tickCount % 20 == 0) {
                    temperatureCapability.setInside(EnvironmentalTemperatureProvider.calculateEnclosure(player, 30));
                }
                isInside[0] = temperatureCapability.isInside();
            });

            if (isInside[0]) {
                return TempModifier.defined("cozy", Math.abs(avg - 1 - temp) * 0.6f, 0f);
            }
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleThirst(Player player) {
        if (player.getFoodData() instanceof TFCFoodData stats) {
            if (stats.getThirst() > 80f && getEnvironmentTemperatureWithTimeOfDay(player) > TFCAmbientalConfig.COMMON.averageTemperature.get().floatValue() + 3) {
                return TempModifier.defined("well_hydrated", -2.5f, 0f);
            }
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleFood(Player player) {
        if (player.getFoodData().getFoodLevel() > 14 && getEnvironmentTemperatureWithTimeOfDay(player) < TFCAmbientalConfig.COMMON.averageTemperature.get().floatValue() - 3) {
            return TempModifier.defined("well_fed", 2.5f, 0f);
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleDiet(Player player) {
        if (player.getFoodData() instanceof TFCFoodData stats) {
            if (getEnvironmentTemperatureWithTimeOfDay(player) < TFCAmbientalConfig.COMMON.coolThreshold.get().floatValue()) {
                float grainLevel = stats.getNutrition().getNutrient(Nutrient.GRAIN);
                float meatLevel = stats.getNutrition().getNutrient(Nutrient.PROTEIN);
                return TempModifier.defined("nutrients", 4f * grainLevel * meatLevel, 0f);
            }
            if (getEnvironmentTemperatureWithTimeOfDay(player) > TFCAmbientalConfig.COMMON.hotThreshold.get().floatValue()) {
                float fruitLevel = stats.getNutrition().getNutrient(Nutrient.FRUIT);
                float veggieLevel = stats.getNutrition().getNutrient(Nutrient.VEGETABLES);
                return TempModifier.defined("nutrients", -4f * fruitLevel * veggieLevel, 0f);
            }
        }
        return TempModifier.none();
    }

    static Optional<TempModifier> handleWetness(Player player) {
        return player.getCapability(TemperatureCapability.CAPABILITY).map(temperatureCapability -> {
            // TODO Wool clothing halves the effect of wetness
            var mod = -0.01f;
            var potency = 0.2f;
            // If you're wet in a cold environment, the temperature drop is significantly higher
            if (temperatureCapability.getWetness() > 1.5f && !player.isInWater()) {
                var envTemperature = getEnvironmentTemperature(player);
                potency = envTemperature < temperatureCapability.getTemperature() ? 5.5f : potency;
            }
            return TempModifier.defined("wetness", mod * temperatureCapability.getWetness(), potency, !player.isInWater() ? -0.03f : 0);
        }).orElse(TempModifier.none());
    }

    static void evaluateAll(Player player, TempModifierStorage storage) {
        for (var fn : AmbientalRegistry.ENVIRONMENT) {
            storage.add(fn.getModifier(player));
        }
    }
}
