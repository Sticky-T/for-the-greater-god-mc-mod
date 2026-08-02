package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class FTGGCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("ftggteam")


                        // ==========================
                        // HELP
                        // ==========================
                        .then(Commands.literal("help")
                                .executes(context -> {

                                    ServerPlayer player =
                                            context.getSource().getPlayer();

                                    player.sendSystemMessage(
                                            Component.literal("""
                                                    For The Greater God Commands:

                                                    /ftggteam join <team>
                                                    /ftggteam leave
                                                    /ftggteam info
                                                    /ftggteam base <set, tp, info>

                                                    Teams:
                                                    Zeus
                                                    Cronus
                                                    Hades
                                                    Poseidon
                                                    """)
                                                    .withStyle(ChatFormatting.RED),
                                            false
                                    );

                                    return 1;
                                })
                        )


                        // ==========================
                        // INFO
                        // ==========================
                        .then(Commands.literal("info")
                                .executes(context -> {

                                    ServerPlayer player =
                                            context.getSource().getPlayer();


                                    TeamData data =
                                            TeamData.get(
                                                    player.level().getServer().overworld()
                                            );


                                    String team =
                                            data.getTeam(
                                                    player.getUUID()
                                            );


                                    if (team == null) {

                                        player.sendSystemMessage(
                                                Component.literal(
                                                        "You are not on a team."
                                                ).withStyle(ChatFormatting.RED),
                                                false
                                        );

                                    } else {

                                        player.sendSystemMessage(
                                                Component.literal(
                                                        "Your team: " + team
                                                ).withStyle(ChatFormatting.GREEN),
                                                false
                                        );
                                    }


                                    return 1;
                                })
                        )


                        // ==========================
                        // LEAVE
                        // ==========================
                        .then(Commands.literal("leave")
                                .executes(context -> {

                                    ServerPlayer player =
                                            context.getSource().getPlayer();


                                    TeamData data =
                                            TeamData.get(
                                                    player.level().getServer().overworld()
                                            );


                                    String oldTeam =
                                            data.getTeam(
                                                    player.getUUID()
                                            );

                                    if (oldTeam != null && data.isLeader(oldTeam, player.getUUID())) {

                                        player.sendSystemMessage(
                                                Component.literal(
                                                        "You must transfer leadership before leaving your team."
                                                ).withStyle(ChatFormatting.RED),
                                                false
                                        );

                                        return 0;
                                    }


                                    if (oldTeam == null) {

                                        player.sendSystemMessage(
                                                Component.literal(
                                                        "You are not in a team."
                                                ).withStyle(ChatFormatting.RED),
                                                false
                                        );

                                        return 0;
                                    }


                                    data.removeTeam(
                                            player.getUUID()
                                    );

                                    TeamGear.removeGear(player);


                                    Scoreboard scoreboard =
                                            player.level().getServer().getScoreboard();


                                    PlayerTeam scoreboardTeam =
                                            scoreboard.getPlayerTeam(oldTeam);


                                    if (scoreboardTeam != null) {

                                        scoreboard.removePlayerFromTeam(
                                                player.getScoreboardName(),
                                                scoreboardTeam
                                        );
                                    }


                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "You left "
                                                            + oldTeam
                                                            + "."
                                            ).withStyle(ChatFormatting.GOLD),
                                            false
                                    );


                                    return 1;
                                })
                        )


                        // ==========================
                        // JOIN
                        // ==========================
                        .then(Commands.literal("join")
                                .then(Commands.argument(
                                                "team",
                                                StringArgumentType.word()
                                        )

                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayer();


                                            String chosen =
                                                    StringArgumentType.getString(
                                                            context,
                                                            "team"
                                                    );


                                            Scoreboard scoreboard =
                                                    player.level().getServer().getScoreboard();


                                            PlayerTeam newTeam =
                                                    scoreboard.getPlayerTeam(chosen);


                                            if (newTeam == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "Unknown team. Use Zeus, Cronus, Hades, or Poseidon."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            TeamData data =
                                                    TeamData.get(
                                                            player.level().getServer().overworld()
                                                    );


                                            String oldTeam =
                                                    data.getTeam(
                                                            player.getUUID()
                                                    );


                                            if (oldTeam != null) {

                                                PlayerTeam oldScoreboardTeam =
                                                        scoreboard.getPlayerTeam(oldTeam);


                                                if (oldScoreboardTeam != null) {

                                                    scoreboard.removePlayerFromTeam(
                                                            player.getScoreboardName(),
                                                            oldScoreboardTeam
                                                    );
                                                }
                                            }

                                            if (oldTeam != null) {
                                                TeamGear.removeGear(player);
                                            }

                                            data.setTeam(
                                                    player.getUUID(),
                                                    chosen
                                            );

                                            boolean leader = data.isLeader(chosen, player.getUUID());

                                            TeamGear.giveGear(
                                                    player,
                                                    chosen,
                                                    leader
                                            );



                                            scoreboard.addPlayerToTeam(
                                                    player.getScoreboardName(),
                                                    newTeam
                                            );


                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "Joined "
                                                                    + chosen
                                                                    + "!"
                                                    ).withStyle(ChatFormatting.GOLD),
                                                    false
                                            );


                                            return 1;

                                        }))
                        )
                        .then(Commands.literal("base")


                                // ==========================
                                // /ftggteam base set
                                // ==========================
                                .then(Commands.literal("set")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayer();


                                            TeamData data =
                                                    TeamData.get(
                                                            player.level().getServer().overworld()
                                                    );


                                            String team =
                                                    data.getTeam(player.getUUID());


                                            if (team == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "You must be in a team to set a base."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            if (!data.isLeader(team, player.getUUID())) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "Only the team leader can set the base."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            data.setBase(
                                                    team,
                                                    player.blockPosition()
                                            );


                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "Base set!"
                                                    ).withStyle(ChatFormatting.GREEN),
                                                    false
                                            );


                                            return 1;
                                        })
                                )



                                // ==========================
                                // /ftggteam base tp
                                // ==========================
                                .then(Commands.literal("tp")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayer();


                                            TeamData data =
                                                    TeamData.get(
                                                            player.level().getServer().overworld()
                                                    );


                                            String team =
                                                    data.getTeam(player.getUUID());


                                            if (team == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "You are not in a team."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            BlockPos base =
                                                    data.getBase(team);


                                            if (base == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "Your team does not have a base."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            ServerLevel world =
                                                    (ServerLevel) player.level();


                                            player.teleportTo(
                                                    world,
                                                    base.getX() + 0.5,
                                                    base.getY() + 1,
                                                    base.getZ() + 0.5,
                                                    Set.of(),
                                                    player.getYRot(),
                                                    player.getXRot(),
                                                    false
                                            );


                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "Teleported to team base."
                                                    ).withStyle(ChatFormatting.GREEN),
                                                    false
                                            );


                                            return 1;
                                        })
                                )



                                // ==========================
                                // /ftggteam base info
                                // ==========================
                                .then(Commands.literal("info")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayer();


                                            TeamData data =
                                                    TeamData.get(
                                                            player.level().getServer().overworld()
                                                    );


                                            String team =
                                                    data.getTeam(player.getUUID());


                                            if (team == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "You are not in a team."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            BlockPos base =
                                                    data.getBase(team);


                                            if (base == null) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "No base set."
                                                        ).withStyle(ChatFormatting.RED),
                                                        false
                                                );

                                                return 0;
                                            }


                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "Team base: " + base
                                                    ).withStyle(ChatFormatting.GREEN),
                                                    false
                                            );


                                            return 1;
                                        })
                                )
                        )


                        // ==========================
                        // ADMIN COMMANDS
                        // ==========================
                        .then(Commands.literal("admin")
                                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN))


                                // /ftggteam admin leader <team> <player>
                                .then(Commands.literal("leader")
                                        .then(Commands.argument(
                                                                "team",
                                                                StringArgumentType.word()
                                                        )
                                                        .then(Commands.argument(
                                                                                "player",
                                                                                EntityArgument.player()
                                                                        )
                                                                        .executes(context -> {

                                                                            String team =
                                                                                    StringArgumentType.getString(
                                                                                            context,
                                                                                            "team"
                                                                                    );


                                                                            ServerPlayer target =
                                                                                    EntityArgument.getPlayer(
                                                                                            context,
                                                                                            "player"
                                                                                    );


                                                                            TeamData data =
                                                                                    TeamData.get(
                                                                                            context.getSource()
                                                                                                    .getServer()
                                                                                                    .overworld()
                                                                                    );

                                                                            String playerTeam = data.getTeam(target.getUUID());

                                                                            if (!team.equals(playerTeam)) {

                                                                                context.getSource().sendFailure(
                                                                                        Component.literal(target.getName().getString() + " is not on the " + team + " team.")
                                                                                );

                                                                                return 0;
                                                                            }

                                                                            UUID previousLeader = data.getLeader(team);

                                                                            data.setLeader(
                                                                                    team,
                                                                                    target.getUUID()
                                                                            );

                                                                            if (team.equals(data.getTeam(target.getUUID()))) {

                                                                                TeamGear.removeGear(target);

                                                                                TeamGear.giveGear(
                                                                                        target,
                                                                                        team,
                                                                                        true
                                                                                );
                                                                            }


                                                                            context.getSource()
                                                                                    .sendSuccess(
                                                                                            () -> Component.literal(
                                                                                                    "Set "
                                                                                                            + target.getName().getString()
                                                                                                            + " as leader of "
                                                                                                            + team
                                                                                            ).withStyle(ChatFormatting.GREEN),
                                                                                            true
                                                                                    );

                                                                            if (previousLeader != null && !previousLeader.equals(target.getUUID())) {

                                                                                ServerPlayer oldLeader =
                                                                                        context.getSource()
                                                                                                .getServer()
                                                                                                .getPlayerList()
                                                                                                .getPlayer(previousLeader);

                                                                                if (oldLeader != null) {

                                                                                    String oldLeaderTeam =
                                                                                            data.getTeam(oldLeader.getUUID());

                                                                                    if (oldLeaderTeam != null) {

                                                                                        TeamGear.removeGear(oldLeader);

                                                                                        TeamGear.giveGear(
                                                                                                oldLeader,
                                                                                                oldLeaderTeam,
                                                                                                false
                                                                                        );
                                                                                    }
                                                                                }
                                                                            }

                                                                            return 1;
                                                                        })
                                                        )
                                        )
                                )



                                // /ftggteam admin friendlyfire <team> <true/false>
                                .then(Commands.literal("friendlyfire")
                                        .then(Commands.argument(
                                                                "team",
                                                                StringArgumentType.word()
                                                        )
                                                        .then(Commands.argument(
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


                                                                            PlayerTeam team =
                                                                                    scoreboard.getPlayerTeam(
                                                                                            teamName
                                                                                    );


                                                                            if (team == null) {

                                                                                context.getSource()
                                                                                        .sendFailure(
                                                                                                Component.literal(
                                                                                                        "Unknown scoreboard team."
                                                                                                )
                                                                                        );

                                                                                return 0;
                                                                            }


                                                                            team.setAllowFriendlyFire(
                                                                                    enabled
                                                                            );


                                                                            context.getSource()
                                                                                    .sendSuccess(
                                                                                            () -> Component.literal(
                                                                                                    "Friendly fire for "
                                                                                                            + teamName
                                                                                                            + " set to "
                                                                                                            + enabled
                                                                                            ).withStyle(ChatFormatting.GREEN),
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

