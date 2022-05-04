package com.firesoftitan.play.titanbox.bungee.listeners;

import com.firesoftitan.play.titanbox.bungee.TitanboxBungee;
import com.firesoftitan.play.titanbox.bungee.enums.SubChannels;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainListener implements Listener {

    public MainListener(){

    }
    public void registerEvents(){
        PluginManager pm = TitanboxBungee.instants.getProxy().getPluginManager();
        pm.registerListener(TitanboxBungee.instants, this);
    }
    HashMap<UUID, String> muted = new HashMap<UUID, String>();
    @EventHandler
    public void onChatEvent (ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer)event.getSender();
        if (muted.containsKey(player.getUniqueId())) event.setCancelled(true);
        ServerInfo serverInfo = player.getServer().getInfo();
        TitanboxBungee.instants.sendCustomData(serverInfo, SubChannels.CHAT, player.getUniqueId().toString(), event.getMessage());

    }
    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent event)
    {
        if ( !event.getTag().equalsIgnoreCase( "titanbox:1" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase( "chat:toggle" ) )
        {
            // the receiver is a ProxiedPlayer when a server talks to the proxy
            if (event.getReceiver() instanceof ProxiedPlayer)
            {
                ProxiedPlayer receiver = (ProxiedPlayer) event.getReceiver();
                // do things
                UUID uuid = UUID.fromString(in.readUTF());
                Boolean canTalk = Boolean.parseBoolean(in.readUTF());
                if (canTalk) muted.remove(uuid);
                if (!canTalk) muted.put(uuid, System.currentTimeMillis() + "");
            }

            // the receiver is a server when the proxy talks to a server
            if (event.getReceiver() instanceof Server)
            {
                Server receiver = (Server) event.getReceiver();
                // do things
                System.out.println("Server");
            }
        }
    }
}
