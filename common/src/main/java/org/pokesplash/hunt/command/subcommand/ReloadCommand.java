package org.pokesplash.hunt.command.subcommand;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Subcommand;
import org.pokesplash.hunt.util.Utils;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

public class ReloadCommand extends Subcommand {

	public ReloadCommand() {
		super("§9Usage:\\n§3- hunt reload");
	}

	/**
	 * Method used to add to the base command for this subcommand.
	 * @return source to complete the command.
	 */
	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("reload")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Hunt.permissions.hasPermission(ctx.getPlayer(),
								Hunt.permissions.getPermission("HuntReload"));
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	/**
	 * Method to perform the logic when the command is executed.
	 * @param context the source of the command.
	 * @return integer to complete command.
	 */
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		Hunt.load();

		context.getSource().sendSystemMessage(Component.literal(Utils.formatMessage(
				Hunt.language.getReloadMessage(),
				context.getSource().isPlayer())));

		return 1;
	}
}