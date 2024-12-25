package com.goby56.wakes.config;

import com.goby56.wakes.config.enums.EffectSpawningRule;
import com.goby56.wakes.config.enums.Resolution;
import com.goby56.wakes.render.enums.RenderType;
import com.goby56.wakes.render.enums.WakeColor;
import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public class WakesConfig extends MidnightConfig {
    public static final String GENERAL = "general";
    public static final String APPEARANCE = "appearance";
    public static final String DEBUG = "debug";
    // Debug
    @Entry(category = GENERAL) public static boolean disableMod = false; // TODO SWITCH TO ENABLE MOD TOGGLE
    @Entry(category = GENERAL) public static boolean pickBoat = true;

    @Hidden() public static List<Float> wakeGradientRanges = Lists.newArrayList(0.1f, 0.4f, 0.6f, 0.8f);
    @Hidden() public static List<String> wakeColors = Lists.newArrayList(
            "#ffff00ff",
            "#ffff00ff",
            "#ffff00ff",
            "#ffff00ff",
            "#ffff00ff"
    );

    // Spawning
    @Entry(category = GENERAL) public static EffectSpawningRule boatSpawning = EffectSpawningRule.SIMULATION_AND_PLANES;
    @Entry(category = GENERAL) public static EffectSpawningRule playerSpawning = EffectSpawningRule.ONLY_SIMULATION;
    @Entry(category = GENERAL) public static EffectSpawningRule otherPlayersSpawning = EffectSpawningRule.ONLY_SIMULATION;
    @Entry(category = GENERAL) public static EffectSpawningRule mobSpawning = EffectSpawningRule.ONLY_SIMULATION;
    @Entry(category = GENERAL) public static EffectSpawningRule itemSpawning = EffectSpawningRule.ONLY_SIMULATION;

    // // Behaviour
    @Entry(category = GENERAL) public static float wavePropagationFactor = 0.95f;
    @Entry(category = GENERAL) public static float waveDecayFactor = 0.5f;
    @Entry(category = GENERAL) public static int initialStrength = 20;
    @Entry(category = GENERAL) public static int paddleStrength = 100;
    @Entry(category = GENERAL) public static int splashStrength = 100;
    @Entry(category = GENERAL) public static boolean spawnParticles = true;

    @Entry(category = APPEARANCE) public static Resolution wakeResolution = Resolution.SIXTEEN;
    @Entry(category = APPEARANCE) public static float wakeOpacity = 1f;
    @Entry(category = APPEARANCE) public static boolean firstPersonSplashPlane = false;

    // Splash plane
    @Entry(category = APPEARANCE) public static float splashPlaneWidth = 2f;
    @Entry(category = APPEARANCE) public static float splashPlaneHeight = 1.5f;
    @Entry(category = APPEARANCE) public static float splashPlaneDepth = 3f;
    @Entry(category = APPEARANCE) public static float splashPlaneOffset = -0.2f;
    @Entry(category = APPEARANCE) public static float splashPlaneGap = 1f;
    @Entry(category = APPEARANCE) public static int splashPlaneResolution = 5;
    @Entry(category = APPEARANCE) public static float maxSplashPlaneVelocity = 0.5f;
    @Entry(category = APPEARANCE) public static float splashPlaneScale = 0.8f;

    @Entry(category = DEBUG) public static boolean debugColors = false;
    @Entry(category = DEBUG) public static int floodFillDistance = 2;
    @Entry(category = DEBUG) public static int ticksBeforeFill = 2;
    @Entry(category = DEBUG) public static RenderType renderType = RenderType.AUTO;
    @Entry(category = DEBUG) public static boolean drawDebugBoxes = false;
    @Entry(category = DEBUG) public static boolean showDebugInfo = false;
    @Entry(category = DEBUG) public static float shaderLightPassthrough = 0.5f;
    
    public static WakeColor getWakeColor(int i) {
        return new WakeColor(wakeColors.get(i));
    }
}
