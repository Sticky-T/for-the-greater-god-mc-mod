package io.github.Launchpad_Tristan.for_the_greater_god;

import io.github.Launchpad_Tristan.for_the_greater_god.mixin.PlayerEntityMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Formatting;

import java.util.LinkedHashMap;
import java.util.Map;

public class For_the_greater_god implements ModInitializer {

    public static final String MOD_ID = "for_the_greater_god";

    public static final Map<String, Formatting> GOD_TEAMS = new LinkedHashMap<>();

    static {
        GOD_TEAMS.put("Zeus", Formatting.YELLOW);
        GOD_TEAMS.put("Kronos", Formatting.DARK_GREEN);
        GOD_TEAMS.put("Hades", Formatting.DARK_RED);
        GOD_TEAMS.put("Poseidon", Formatting.BLUE);
    }

    @Override
    public void onInitialize() {

        System.out.println("[For The Greater God] Loading...");

        ServerLifecycleEvents.SERVER_STARTED.register(this::createTeams);

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    FTGGCommands.register(dispatcher);
                }
        );

        ServerTickEvents.END_SERVER_TICK.register(BaseProtection::tick);

        System.out.println("[For The Greater God] Loaded!");

        ModItems.initialize();

        ModComponents.initialize();

        TeamEvents.initialize();

        DivineGearEvents.register();

        DivineProtectionEvents.register();
    }


    private void createTeams(MinecraftServer server) {

        Scoreboard scoreboard = server.getScoreboard();

        for (Map.Entry<String, Formatting> entry : GOD_TEAMS.entrySet()) {

            if (scoreboard.getTeam(entry.getKey()) == null) {

                Team team = scoreboard.addTeam(entry.getKey());
                team.setColor(entry.getValue());

            }
        }
    }
}
