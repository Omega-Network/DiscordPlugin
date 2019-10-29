package disc;

import io.anuke.arc.Core;
import io.anuke.arc.Events;
import io.anuke.arc.util.CommandHandler;
import io.anuke.mindustry.entities.type.Player;
import io.anuke.mindustry.game.EventType;
import io.anuke.mindustry.plugin.Plugin;
//javacord
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Messageable;
import org.w3c.dom.Text;
//file
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.lang.Thread;
import java.util.Optional;

public class discordPlugin extends Plugin{
    List<String> data; //token, channelid
    DiscordApi api;

    //register event handlers and create variables in the constructor
    public discordPlugin(){
        try{
            Path path = Paths.get(String.valueOf(Core.settings.getDataDirectory().child("mods/token.txt")));
            data = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("[ERR!] discordplugin: " + e.toString());
        }
        api = new DiscordApiBuilder().setToken(data.get(0)).login().join();
        BotThread bt = new BotThread(api, Thread.currentThread());
        bt.setDaemon(false);
        bt.start();
    }


    //register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler){



    }

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler){
        handler.<Player>register("d", "<text...>", "Sends a message to discord.", (args, player) -> {
            Optional<Channel> dc =  ((Optional<Channel>)this.api.getChannelById(data.get(1)));
            if (!dc.isPresent()) {
                System.out.println("[ERR!] discordplugin: channel not found!");
                return;
            }
            Optional<TextChannel> dtc = dc.get().asTextChannel();
            if (!dtc.isPresent()){
                System.out.println("[ERR!] discordplugin: textchannel not found!");
                return;
            }
            dtc.get().sendMessage(player.name +": " + args[0]);
        });
    }
}