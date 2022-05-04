package com.firesoftitan.play.titanbox.bungee;

import com.firesoftitan.play.titanbox.bungee.enums.SubChannels;
import com.firesoftitan.play.titanbox.bungee.listeners.MainListener;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;

public final class TitanboxBungee extends Plugin {

    public static TitanboxBungee instants;
    private MainListener mainListener;
    @Override
    public void onEnable() {
        instants = this;
        this.mainListener = new MainListener();
        this.mainListener.registerEvents();
        getProxy().registerChannel( "titanbox:1" );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void sendCustomData(ServerInfo serverInfo, SubChannels subChannels, String... data1)
    {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        // perform a check to see if globally are no players
        if ( networkPlayers == null || networkPlayers.isEmpty() )
        {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF( subChannels.getName() ); // the channel could be whatever you want
        for(String d: data1) {
            out.writeUTF(d); // this data could be whatever you want
        }

        serverInfo.sendData( "titanbox:1", out.toByteArray() );
    }
}
