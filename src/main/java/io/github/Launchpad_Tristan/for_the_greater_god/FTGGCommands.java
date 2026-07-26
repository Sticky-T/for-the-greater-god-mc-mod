package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.command.argument.EntityArgumentType;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import java.util.Set;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;

public class FTGGCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("ftggteam")


                        // ==========================
                        // HELP
                        // ==========================
                        .then(CommandManager.literal("help")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();

                                    player.sendMessage(
                                            Text.literal("""
                                                    For The Greater God Commands:

                                                    /ftggteam join <team>
                                                    /ftggteam leave
                                                    /ftggteam info
                                                    /ftggteam base

                                                    Teams:
                                                    Zeus
                                                    Kronos
                                                    Hades
                                                    Poseidon
                                                    """)
                                                    .formatted(Formatting.RED),
                                            false
                                    );

                                    return 1;
                                })
                        )


                        // ==========================
                        // INFO
                        // ==========================
                        .then(CommandManager.literal("info")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();


                                    TeamData data =
                                            TeamData.get(
                                                    player.getServer().getOverworld()
                                            );


                                    String team =
                                            data.getTeam(
                                                    player.getUuid()
                                            );


                                    if (team == null) {

                                        player.sendMessage(
                                                Text.literal(
                                                        "You are not on a team."
                                                ).formatted(Formatting.RED),
                                                false
                                        );

                                    } else {

                                        player.sendMessage(
                                                Text.literal(
                                                        "Your team: " + team
                                                ).formatted(Formatting.GREEN),
                                                false
                                        );
                                    }


                                    return 1;
                                })
                        )


                        // ==========================
                        // LEAVE
                        // ==========================
                        .then(CommandManager.literal("leave")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();


                                    TeamData data =
                                            TeamData.get(
                                                    player.getServer().getOverworld()
                                            );


                                    String oldTeam =
                                            data.getTeam(
                                                    player.getUuid()
                                            );


                                    if (oldTeam == null) {

                                        player.sendMessage(
                                                Text.literal(
                                                        "You are not in a team."
                                                ).formatted(Formatting.RED),
                                                false
                                        );

                                        return 0;
                                    }


                                    data.removeTeam(
                                            player.getUuid()
                                    );


                                    Scoreboard scoreboard =
                                            player.getServer().getScoreboard();


                                    Team scoreboardTeam =
                                            scoreboard.getTeam(oldTeam);


                                    if (scoreboardTeam != null) {

                                        scoreboard.removeScoreHolderFromTeam(
                                                player.getNameForScoreboard(),
                                                scoreboardTeam
                                        );
                                    }


                                    player.sendMessage(
                                            Text.literal(
                                                    "You left "
                                                            + oldTeam
                                                            + "."
                                            ).formatted(Formatting.GOLD),
                                            false
                                    );


                                    return 1;
                                })
                        )


                        // ==========================
                        // JOIN
                        // ==========================
                        .then(CommandManager.literal("join")
                                .then(CommandManager.argument(
                                                "team",
                                                StringArgumentType.word()
                                        )

                                        .executes(context -> {

                                            ServerPlayerEntity player =
                                                    context.getSource().getPlayer();


                                            String chosen =
                                                    StringArgumentType.getString(
                                                            context,
                                                            "team"
                                                    );


                                            Scoreboard scoreboard =
                                                    player.getServer().getScoreboard();


                                            Team newTeam =
                                                    scoreboard.getTeam(chosen);


                                            if (newTeam == null) {

                                                player.sendMessage(
                                                        Text.literal(
                                                                "Unknown team. Use Zeus, Kronos, Hades, or Poseidon."
                                                        ).formatted(Formatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            TeamData data =
                                                    TeamData.get(
                                                            player.getServer().getOverworld()
                                                    );


                                            String oldTeam =
                                                    data.getTeam(
                                                            player.getUuid()
                                                    );


                                            if (oldTeam != null) {

                                                Team oldScoreboardTeam =
                                                        scoreboard.getTeam(oldTeam);


                                                if (oldScoreboardTeam != null) {

                                                    scoreboard.removeScoreHolderFromTeam(
                                                            player.getNameForScoreboard(),
                                                            oldScoreboardTeam
                                                    );
                                                }
                                            }


                                            data.setTeam(
                                                    player.getUuid(),
                                                    chosen
                                            );


                                            scoreboard.addScoreHolderToTeam(
                                                    player.getNameForScoreboard(),
                                                    newTeam
                                            );


                                            player.sendMessage(
                                                    Text.literal(
                                                            "Joined "
                                                                    + chosen
                                                                    + "!"
                                                    ).formatted(Formatting.GOLD),
                                                    false
                                            );


                                            return 1;

                                        }))
                        )
                        // ==========================
                        // BASE COMMANDS
                        // ==========================
                        .then(CommandManager.literal("base")

                                // /ftggteam base set
                                .then(CommandManager.literal("set")
                                        .executes(context -> {

                                            ServerPlayerEntity player =
                                                    context.getSource().getPlayer();


                                            TeamData data =
                                                    TeamData.get(
                                                            player.getServer().getOverworld()
                                                    );


                                            String team =
                                                    data.getTeam(
                                                            player.getUuid()
                                                    );


                                            if (team == null) {

                                                player.sendMessage(
                                                        Text.literal(
                                                                "You must be in a team to set a base."
                                                        ).formatted(Formatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            if (!data.isLeader(
                                                    team,
                                                    player.getUuid()
                                            )) {

                                                player.sendMessage(
                                                        Text.literal(
                                                                "Only the team leader can set the base."
                                                        ).formatted(Formatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            data.setBase(
                                                    team,
                                                    player.getBlockPos()
                                            );


                                            player.sendMessage(
                                                    Text.literal(
                                                            "Base set for "
                                                                    + team
                                                                    + " at "
                                                                    + player.getBlockPos()
                                                    ).formatted(Formatting.GREEN),
                                                    false
                                            );


                                            return 1;
                                        })

                                        // /ftggteam base tp
                                        .then(CommandManager.literal("tp")
                                                .executes(context -> {

                                                    ServerPlayerEntity player =
                                                            context.getSource().getPlayer();

                                                    TeamData data =
                                                            TeamData.get(
                                                                    player.getServer().getOverworld()
                                                            );

                                                    String team =
                                                            data.getTeam(
                                                                    player.getUuid()
                                                            );

                                                    if (team == null) {

                                                        player.sendMessage(
                                                                Text.literal(
                                                                        "You are not in a team."
                                                                ).formatted(Formatting.RED),
                                                                false
                                                        );

                                                        return 0;
                                                    }

                                                    BlockPos base =
                                                            data.getBase(team);

                                                    if (base == null) {

                                                        player.sendMessage(
                                                                Text.literal(
                                                                        "Your team does not have a base set."
                                                                ).formatted(Formatting.RED),
                                                                false
                                                        );

                                                        return 0;
                                                    }

                                                    ServerWorld world = (ServerWorld) player.getWorld();

                                                    player.teleport(
                                                            world,
                                                            base.getX() + 0.5,
                                                            base.getY() + 1,
                                                            base.getZ() + 0.5,
                                                            Set.<PositionFlag>of(),
                                                            player.getYaw(),
                                                            player.getPitch(),
                                                            false
                                                    );

                                                    player.sendMessage(
                                                            Text.literal(
                                                                    "Teleported to your team base."
                                                            ).formatted(Formatting.GREEN),
                                                            false
                                                    );

                                                    return 1;
                                                })
                                        )
                                )


                                // /ftggteam base info
                                .then(CommandManager.literal("info")
                                        .executes(context -> {

                                            ServerPlayerEntity player =
                                                    context.getSource().getPlayer();


                                            TeamData data =
                                                    TeamData.get(
                                                            player.getServer().getOverworld()
                                                    );


                                            String team =
                                                    data.getTeam(
                                                            player.getUuid()
                                                    );


                                            if (team == null) {

                                                player.sendMessage(
                                                        Text.literal(
                                                                "You are not in a team."
                                                        ).formatted(Formatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            if (data.getBase(team) == null) {

                                                player.sendMessage(
                                                        Text.literal(
                                                                "Your team does not have a base set."
                                                        ).formatted(Formatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            player.sendMessage(
                                                    Text.literal(
                                                            "Team base: "
                                                                    + data.getBase(team)
                                                    ).formatted(Formatting.GREEN),
                                                    false
                                            );


                                            return 1;
                                        })
                                )
                        )


                        // ==========================
                        // ADMIN COMMANDS
                        // ==========================
                        .then(CommandManager.literal("admin")
                                .requires(source -> source.hasPermissionLevel(2))


                                // /ftggteam admin leader <team> <player>
                                .then(CommandManager.literal("leader")
                                        .then(CommandManager.argument(
                                                                "team",
                                                                StringArgumentType.word()
                                                        )
                                                        .then(CommandManager.argument(
                                                                                "player",
                                                                                EntityArgumentType.player()
                                                                        )
                                                                        .executes(context -> {

                                                                            String team =
                                                                                    StringArgumentType.getString(
                                                                                            context,
                                                                                            "team"
                                                                                    );


                                                                            ServerPlayerEntity target =
                                                                                    EntityArgumentType.getPlayer(
                                                                                            context,
                                                                                            "player"
                                                                                    );


                                                                            TeamData data =
                                                                                    TeamData.get(
                                                                                            context.getSource()
                                                                                                    .getServer()
                                                                                                    .getOverworld()
                                                                                    );


                                                                            data.setLeader(
                                                                                    team,
                                                                                    target.getUuid()
                                                                            );


                                                                            context.getSource()
                                                                                    .sendFeedback(
                                                                                            () -> Text.literal(
                                                                                                    "Set "
                                                                                                            + target.getName().getString()
                                                                                                            + " as leader of "
                                                                                                            + team
                                                                                            ).formatted(Formatting.GREEN),
                                                                                            true
                                                                                    );


                                                                            return 1;
                                                                        })
                                                        )
                                        )
                                )



                                // /ftggteam admin friendlyfire <team> <true/false>
                                .then(CommandManager.literal("friendlyfire")
                                        .then(CommandManager.argument(
                                                                "team",
                                                                StringArgumentType.word()
                                                        )
                                                        .then(CommandManager.argument(
                                                                                "enabled",
                                                                                BoolArgumentType.bool()
                                                                        )
                                                                        .executes(context -> {


                                                                            String teamName =
                                                                                    StringArgumentType.getString(
                                                                                            context,
                                                                                            "team"
                                                                                    );


                                                                            boolean enabled =
                                                                                    BoolArgumentType.getBool(
                                                                                            context,
                                                                                            "enabled"
                                                                                    );


                                                                            Scoreboard scoreboard =
                                                                                    context.getSource()
                                                                                            .getServer()
                                                                                            .getScoreboard();


                                                                            Team team =
                                                                                    scoreboard.getTeam(
                                                                                            teamName
                                                                                    );


                                                                            if (team == null) {

                                                                                context.getSource()
                                                                                        .sendError(
                                                                                                Text.literal(
                                                                                                        "Unknown scoreboard team."
                                                                                                )
                                                                                        );

                                                                                return 0;
                                                                            }


                                                                            team.setFriendlyFireAllowed(
                                                                                    enabled
                                                                            );


                                                                            context.getSource()
                                                                                    .sendFeedback(
                                                                                            () -> Text.literal(
                                                                                                    "Friendly fire for "
                                                                                                            + teamName
                                                                                                            + " set to "
                                                                                                            + enabled
                                                                                            ).formatted(Formatting.GREEN),
                                                                                            true
                                                                                    );


                                                                            return 1;

                                                                        })
                                                        )
                                        )
                                )
                        )
        );
    }
}

