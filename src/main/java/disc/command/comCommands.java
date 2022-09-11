package disc.command;

//mindustry + arc
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.gen.Call;

//javacord

import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.world.modules.ItemModule;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.entity.permission.Role;

import disc.discordPlugin;

import java.util.Arrays;

import static disc.utilmethods.*;


public class comCommands implements MessageCreateListener {
    private final discordPlugin mainData;
    public comCommands(discordPlugin _data){
        this.mainData = _data;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event){
        String[] incoming_msg = event.getMessageContent().split("\\s+");
        Role moderator = mainData.discRoles.get("987815204500951092");

        switch (incoming_msg[0]){
            case "..chat":
                String[] msg2 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Call.sendMessage("[sky]" +event.getMessageAuthor().getName()+ " From discord >[] " + msg2[1].trim());
                event.getChannel().sendMessage("Sent :white_check_mark:");
                break;
            case "..peepee":
                //ping command displaying ms
                long time = System.currentTimeMillis();
                event.getChannel().sendMessage("poopoo!").thenAcceptAsync(message -> {
                    long ping = System.currentTimeMillis() - time;
                    message.edit("poopoo: " + ping + "ms");
                });
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
                lijst.append("Players: ").append(Groups.player.size()).append("\n");
                if(Groups.player.count(p->p.admin) > 0) {
                    admins.append("Online Admins: ");// + Vars.playerGroup.all().count(p->p.isAdmin)+"\n");
                }
                for (Player p : Groups.player){
                    if (p.admin()){
                        admins.append("* ").append(p.name.trim()).append("\n");
                    } else {
                        lijst.append("* ").append(p.name.trim()).append("\n");
                    }
                }
                new MessageBuilder().appendCode("", lijst.toString() + admins).send(event.getChannel());
                break;
            case "..info":
                try {
                    String lijst2 = "Map: " + Vars.state.map.name() + "\n" + "Author: " + Vars.state.map.author() + "\n" +
                            "Wave: " + Vars.state.wave + "\n" +
                            "Enemies: " + Vars.state.enemies + "\n" +
                            "Players: " + Groups.player.size() + '\n';
                    //lijst2.append("Admins (online): " + Vars.playerGroup.all().count(p -> p.isAdmin));
                    new MessageBuilder().appendCode("", lijst2).send(event.getChannel());
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
                    for (String s : Arrays.asList("Copper: " + core.get(Items.copper) + "\n", "Lead: " + core.get(Items.lead) + "\n", "Graphite: " + core.get(Items.graphite) + "\n", "Metaglass: " + core.get(Items.metaglass) + "\n", "Titanium: " + core.get(Items.titanium) + "\n", "Thorium: " + core.get(Items.thorium) + "\n", "Silicon: " + core.get(Items.silicon) + "\n", "Plastanium: " + core.get(Items.plastanium) + "\n", "Phase fabric: " + core.get(Items.phaseFabric) + "\n", "Surge alloy: " + core.get(Items.surgeAlloy) + "\n")) {
                        lijst3.append(s);
                    }

                    new MessageBuilder().appendCode("", lijst3.toString()).send(event.getChannel());
                }
                break;
            default:
                break;
        }
    }
}
