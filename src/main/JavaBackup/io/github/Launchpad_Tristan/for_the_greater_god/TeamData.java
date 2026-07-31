package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamData extends PersistentState {

    private final Map<UUID, String> playerTeams = new HashMap<>();
    private final Map<String, UUID> leaders = new HashMap<>();
    private final Map<String, BlockPos> bases = new HashMap<>();


    public static final Codec<TeamData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.unboundedMap(
                                            Uuids.CODEC,
                                            Codec.STRING
                                    ).fieldOf("playerTeams")
                                    .forGetter(data -> data.playerTeams),


                            Codec.unboundedMap(
                                            Codec.STRING,
                                            Uuids.CODEC
                                    ).fieldOf("leaders")
                                    .forGetter(data -> data.leaders),


                            Codec.unboundedMap(
                                            Codec.STRING,
                                            BlockPos.CODEC
                                    ).fieldOf("bases")
                                    .forGetter(data -> data.bases)

                    ).apply(instance, (playerTeams, leaders, bases) -> {

                        TeamData data = new TeamData();

                        data.playerTeams.putAll(playerTeams);
                        data.leaders.putAll(leaders);
                        data.bases.putAll(bases);

                        return data;
                    })
            );



    public static TeamData get(ServerWorld world) {

        return world.getPersistentStateManager()
                .getOrCreate(
                        new PersistentStateType<>(
                                "ftgg_team_data",
                                TeamData::new,
                                CODEC,
                                null
                        )
                );
    }



    // ======================
    // PLAYER TEAMS
    // ======================

    public void setTeam(UUID player, String team) {
        playerTeams.put(player, team);
        markDirty();
    }


    public String getTeam(UUID player) {
        return playerTeams.get(player);
    }


    public boolean hasTeam(UUID player) {
        return playerTeams.containsKey(player);
    }


    public void removeTeam(UUID player) {
        playerTeams.remove(player);
        markDirty();
    }



    // ======================
    // LEADERS
    // ======================

    public void setLeader(String team, UUID uuid) {
        leaders.put(team, uuid);
        markDirty();
    }


    public UUID getLeader(String team) {
        return leaders.get(team);
    }


    public boolean isLeader(String team, UUID uuid) {
        return uuid.equals(leaders.get(team));
    }



    // ======================
    // BASES
    // ======================

    public void setBase(String team, BlockPos pos) {
        bases.put(team, pos);
        markDirty();
    }


    public BlockPos getBase(String team) {
        return bases.get(team);
    }


    public Map<String, BlockPos> getAllBases() {
        return bases;
    }
}
