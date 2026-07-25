package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamData extends PersistentState {

    private final Map<String, UUID> leaders = new HashMap<>();
    private final Map<String, BlockPos> bases = new HashMap<>();


    public TeamData() {
    }


    public static final Codec<TeamData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.unboundedMap(
                                            Codec.STRING,
                                            Codec.STRING
                                    ).fieldOf("leaders")
                                    .forGetter(data -> {

                                        Map<String, String> map = new HashMap<>();

                                        for (var entry : data.leaders.entrySet()) {
                                            map.put(
                                                    entry.getKey(),
                                                    entry.getValue().toString()
                                            );
                                        }

                                        return map;
                                    }),


                            Codec.unboundedMap(
                                            Codec.STRING,
                                            BlockPos.CODEC
                                    ).fieldOf("bases")
                                    .forGetter(data -> data.bases)


                    ).apply(instance, (leaders, bases) -> {

                        TeamData data = new TeamData();


                        leaders.forEach((team, uuid) ->
                                data.leaders.put(
                                        team,
                                        UUID.fromString(uuid)
                                )
                        );


                        data.bases.putAll(bases);


                        return data;
                    })
            );



    public static final PersistentStateType<TeamData> TYPE =
            new PersistentStateType<>(
                    For_the_greater_god.MOD_ID + "_teams",
                    TeamData::new,
                    CODEC,
                    null
            );



    public static TeamData get(ServerWorld world) {

        return world.getPersistentStateManager()
                .getOrCreate(TYPE);
    }



    // =========================
    // Leaders
    // =========================


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



    // =========================
    // Bases
    // =========================


    public void setBase(String team, BlockPos pos) {

        bases.put(team, pos);

        markDirty();
    }



    public BlockPos getBase(String team) {

        return bases.get(team);
    }



    public boolean hasBase(String team) {

        return bases.containsKey(team);
    }



    public void removeBase(String team) {

        bases.remove(team);

        markDirty();
    }



    public Map<String, BlockPos> getAllBases() {

        return Map.copyOf(bases);
    }
}
