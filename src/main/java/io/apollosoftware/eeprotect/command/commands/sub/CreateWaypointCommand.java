package io.apollosoftware.eeprotect.command.commands.sub;


import io.apollosoftware.eeprotect.EEProtect;
import io.apollosoftware.eeprotect.PluginMessage;
import io.apollosoftware.eeprotect.command.commands.EntryCommand;
import io.apollosoftware.eeprotect.command.commands.WaypointCommand;
import io.apollosoftware.eeprotect.data.ClaimData;
import io.apollosoftware.eeprotect.data.UserData;
import io.apollosoftware.lib.command.CommandException;
import io.apollosoftware.lib.command.PluginDependant;
import io.apollosoftware.lib.command.SubCompartment;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Copyright © 2016 APOLLOSOFTWARE.IO
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law.
 */

@PluginDependant
public class CreateWaypointCommand extends SubCompartment<WaypointCommand> {


    private EEProtect plugin;

    public CreateWaypointCommand(EEProtect plugin) {
        super(ChatColor.GREEN + "/wp create <name>", "add", "create", "set");

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("You must be a player to execute this command");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return;
        }

        try {
            Player player = (Player) sender;
            UserData user = plugin.getDatabaseLoader().createIfNotExists(player);
            String name = args[0];

            if (plugin.createNewWaypoint(user, name, player.getLocation()))
                new PluginMessage("CREATE_WAYPOINT").param(name).sendTo(player);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException();
        }


    }
}
