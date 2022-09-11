package disc.command;

//mindustry + arc
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.gen.Call;

//javacord

import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.world.modules.ItemModule;
import org.javacord.api.entity.channel.GroupChannelUpdater;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.entity.permission.Role;

import disc.discordPlugin;

import static disc.utilmethods.*;


public class comCommands implements MessageCreateListener {
    private discordPlugin mainData;
    @Override
    public void onMessageCreate(MessageCreateEvent event){
        String[] incoming_msg = event.getMessageContent().split("\\s+");
        Role moderator = mainData.discRoles.get("987815204500951092");

        switch (incoming_msg[0]){
            case "..chat":
                String[] msg2 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Call.sendMessage("[royale]" +event.getMessageAuthor().getName()+ " From discord >[] " + msg2[1].trim());
                break;
            case "..modchat":
                if (moderator == null) {
                    if (event.isPrivateMessage()) return;
                    event.getChannel().sendMessage("You do not have permission to use this command");
                    return;
                }
                if (!hasPermission(moderator, event)) return;
                String[] msg3 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Call.sendMessage("[scarlet]Mod Chat >[] " + msg3[1].trim());

                break;
            case "..players":
                StringBuilder lijst = new StringBuilder();
                StringBuilder admins = new StringBuilder();
                lijst.append("Players: " + Groups.player.size()+"\n");
                if(Groups.player.count(p->p.admin) > 0) {
                    admins.append("Online Admins: ");// + Vars.playerGroup.all().count(p->p.isAdmin)+"\n");
                }
                for (Player p : Groups.player){
                    if (p.admin()){
                        admins.append("* " + p.name.trim() + "\n");
                    } else {
                        lijst.append("* " + p.name.trim() + "\n");
                    }
                }
                new MessageBuilder().appendCode("", lijst.toString() + admins.toString()).send(event.getChannel());
                break;
            case "..info":
                try {
                    StringBuilder lijst2 = new StringBuilder();
                    lijst2.append("Map: " + Vars.state.map.name() + "\n" + "Author: " + Vars.state.map.author() + "\n");
                    lijst2.append("Wave: " + Vars.state.wave + "\n");
                    lijst2.append("Enemies: " + Vars.state.enemies + "\n");
                    lijst2.append("Players: " + Groups.player.size() + '\n');
//                    lijst2.append("Admins (online): " + Vars.playerGroup.all().count(p -> p.isAdmin));
                    new MessageBuilder().appendCode("", lijst2.toString()).send(event.getChannel());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "..infores":
                //event.getChannel().sendMessage("not implemented yet...");
                if (!Vars.state.rules.waves){
                    event.getChannel().sendMessage("Only available when playing survivalmode!");
                    return;
                } else if(Groups.player.isEmpty()) {
                    event.getChannel().sendMessage("No players online!");
                } else {
                    StringBuilder lijst3 = new StringBuilder();
                    lijst3.append("Amount of items in the core\n\n");
                    ItemModule core = Groups.player.first().core().items;
                    lijst3.append("Copper: " + core.get(Items.copper) + "\n");
                    lijst3.append("Lead: " + core.get(Items.lead) + "\n");
                    lijst3.append("Graphite: " + core.get(Items.graphite) + "\n");
                    lijst3.append("Metaglass: " + core.get(Items.metaglass) + "\n");
                    lijst3.append("Titanium: " + core.get(Items.titanium) + "\n");
                    lijst3.append("Thorium: " + core.get(Items.thorium) + "\n");
                    lijst3.append("Silicon: " + core.get(Items.silicon) + "\n");
                    lijst3.append("Plastanium: " + core.get(Items.plastanium) + "\n");
                    lijst3.append("Phase fabric: " + core.get(Items.phaseFabric) + "\n");
                    lijst3.append("Surge alloy: " + core.get(Items.surgeAlloy) + "\n");

                    new MessageBuilder().appendCode("", lijst3.toString()).send(event.getChannel());
                }
                break;
            default:
                break;
        }
    }
}
