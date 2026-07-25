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

                        // /ftggteam help
                        .then(CommandManager.literal("help")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();

                                    player.sendMessage(
                                            Text.literal(
                                                    """
                                                    For The Greater God Commands:
        
                                                    /ftggteam join <team>
                                                    /ftggteam leave
                                                    /ftggteam info
        
                                                    Teams:
                                                    Zeus
                                                    Kronos
                                                    Hades
                                                    Poseidon
                                                    """
                                            ).formatted(Formatting.RED),
                                            false
                                    );

                                    return 1;
                                })
                        )


                        // /ftggteam info
                        .then(CommandManager.literal("info")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();

                                    Scoreboard scoreboard =
                                            player.getServer().getScoreboard();

                                    Team team =
                                            scoreboard.getTeam(player.getNameForScoreboard());

                                    if (team != null) {

                                        player.sendMessage(
                                                Text.literal(
                                                        "Your god team: "
                                                                + team.getName()
                                                ).formatted(Formatting.GREEN),
                                                false
                                        );

                                    } else {

                                        player.sendMessage(
                                                Text.literal(
                                                        "You are not in a god team."
                                                ).formatted(Formatting.RED),
                                                false
                                        );
                                    }

                                    return 1;
                                })
                        )


                        // /ftggteam leave
                        .then(CommandManager.literal("leave")
                                .executes(context -> {

                                    ServerPlayerEntity player =
                                            context.getSource().getPlayer();

                                    Scoreboard scoreboard =
                                            player.getServer().getScoreboard();


                                    Team team =
                                            scoreboard.getTeam(
                                                    player.getNameForScoreboard()
                                            );


                                    if (team != null) {

                                        scoreboard.removeScoreHolderFromTeam(
                                                player.getNameForScoreboard(),
                                                team
                                        );

                                        player.sendMessage(
                                                Text.literal(
                                                        "You left "
                                                                + team.getName()
                                                                + "."
                                                ),
                                                false
                                        );

                                    } else {

                                        player.sendMessage(
                                                Text.literal(
                                                        "You are not in a team."
                                                ).formatted(Formatting.RED),
                                                false
                                        );
                                    }

                                    return 1;
                                })
                        )


                        // /ftggteam join <team>
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
                                                            player.getServer()
                                                                    .getScoreboard();


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


                                                    Team oldTeam =
                                                            scoreboard.getTeam(
                                                                    player.getNameForScoreboard()
                                                            );


                                                    if (oldTeam != null) {

                                                        scoreboard.removeScoreHolderFromTeam(
                                                                player.getNameForScoreboard(),
                                                                oldTeam
                                                        );
                                                    }


                                                    scoreboard.addScoreHolderToTeam(
                                                            player.getNameForScoreboard(),
                                                            newTeam
                                                    );


                                                    player.sendMessage(
                                                            Text.literal(
                                                                    "Joined "
                                                                            + newTeam.getName()
                                                                            + "!"
                                                            ).formatted(
                                                                    Formatting.GOLD
                                                            ),
                                                            false
                                                    );


                                                    return 1;

                                                })
                                )
                        )
        );
    }
}
