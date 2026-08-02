package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class TeamData extends SavedData {

    private final Map<UUID, String> playerTeams = new HashMap<>();
    private final Map<String, UUID> leaders = new HashMap<>();
    private final Map<String, BlockPos> bases = new HashMap<>();


    public static final Codec<TeamData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(

                            Codec.unboundedMap(
                                            UUIDUtil.AUTHLIB_CODEC,
                                            Codec.STRING
                                    ).fieldOf("playerTeams")
                                    .forGetter(data -> data.playerTeams),


                            Codec.unboundedMap(
                                            Codec.STRING,
                                            UUIDUtil.AUTHLIB_CODEC
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



    public static TeamData get(ServerLevel world) {

        return world.getDataStorage()
                .computeIfAbsent(
                        new SavedDataType<>(
                                Identifier.fromNamespaceAndPath("ftgg", "team_data"),
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
        setDirty();
    }


    public String getTeam(UUID player) {
        return playerTeams.get(player);
    }


    public boolean hasTeam(UUID player) {
        return playerTeams.containsKey(player);
    }


    public void removeTeam(UUID player) {
        playerTeams.remove(player);
        setDirty();
    }



    // ======================
    // LEADERS
    // ======================

    public void setLeader(String team, UUID uuid) {
        leaders.put(team, uuid);
        setDirty();
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
        setDirty();
    }


    public BlockPos getBase(String team) {
        return bases.get(team);
    }


    public Map<String, BlockPos> getAllBases() {
        return bases;
    }
}
