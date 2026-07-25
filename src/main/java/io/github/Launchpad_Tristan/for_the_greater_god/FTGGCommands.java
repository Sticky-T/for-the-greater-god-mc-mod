package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class FTGGCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("ftggteam")


                        // HELP
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

                                    Teams:
                                    Zeus
                                    Kronos
                                    Hades
                                    Poseidon
                                    """).formatted(Formatting.RED),
                                            false
                                    );

                                    return 1;
                                })
                        )


                        // INFO
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


                                    if(team == null) {

                                        player.sendMessage(
                                                Text.literal(
                                                        "You are not in a god team."
                                                ).formatted(Formatting.RED),
                                                false
                                        );

                                    } else {

                                        player.sendMessage(
                                                Text.literal(
                                                        "Your god team: " + team
                                                ).formatted(Formatting.GREEN),
                                                false
                                        );
                                    }

                                    return 1;
                                })
                        )


                        // LEAVE
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


                                    if(oldTeam == null) {

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


                                    if(scoreboardTeam != null) {

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


                        // JOIN
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



                                            if(newTeam == null) {

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



                                            // remove old scoreboard team
                                            if(oldTeam != null) {

                                                Team oldScoreboardTeam =
                                                        scoreboard.getTeam(oldTeam);


                                                if(oldScoreboardTeam != null) {

                                                    scoreboard.removeScoreHolderFromTeam(
                                                            player.getNameForScoreboard(),
                                                            oldScoreboardTeam
                                                    );
                                                }
                                            }



                                            // save permanently
                                            data.setTeam(
                                                    player.getUuid(),
                                                    chosen
                                            );



                                            // update scoreboard display
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
        );
    }
}
