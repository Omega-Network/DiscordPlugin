package disc.command;

//mindustry + arc

import disc.discordPlugin;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.world.modules.ItemModule;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import static disc.utilmethods.hasPermission;
import static disc.discConstants.*;


public class comCommands implements MessageCreateListener {
    private final discordPlugin mainData;
    public comCommands(discordPlugin _data){
        this.mainData = _data;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event){
        String[] incoming_msg = event.getMessageContent().split("\\s+");
        Role moderator = mainData.discRoles.get("role_id");

        switch (incoming_msg[0]){
            case prefix + "chat":
                String[] msg2 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Call.sendMessage("[sky]" +event.getMessageAuthor().getName()+ " from discord >[] " + msg2[1].trim());
                event.getChannel().sendMessage("Sent :white_check_mark:");
                break;
            case prefix + "peepee":
                //ping command displaying ms
                long time = System.currentTimeMillis();
                event.getChannel().sendMessage("poopoo!").thenAcceptAsync(message -> {
                    long ping = System.currentTimeMillis() - time;
                    message.edit("poopoo: " + ping + "ms");
                });
                break;
            case prefix + "modchat":
                if (moderator == null) {
                    if (event.isPrivateMessage()) return;
                    event.getChannel().sendMessage("You do not have permission to use this command");
                    return;
                }
                if (!hasPermission(moderator, event)) return;
                String[] msg3 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Call.sendMessage("[scarlet]Administrator >[] " + msg3[1].trim());
                event.getChannel().sendMessage(":white_check_mark:");
                break;
            case "..modteamchat":
                if (moderator == null) {
                    if (event.isPrivateMessage()) return;
                    event.getChannel().sendMessage("You do not have permission to use this command");
                    return;
                }
                if (!hasPermission(moderator, event)) return;
                String[] msg3 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 3);
                try{
                    Team t = Team.get(Integer.parseInt(msg3[1]));
                    String msg = "[scarlet]T Administrator >[] " + msg3[2].trim();
                    Groups.player.each(p -> p.team == t, p -> p.sendMessage(msg));
                    event.getChannel().sendMessage(":white_check_mark:");
                }catch(Exception e){
                    event.getChannel().sendMessage(":boom: Your message did not send.");
                }

                break;
            case prefix + "players":
                StringBuilder lijst = new StringBuilder();
                StringBuilder admins = new StringBuilder();
                lijst.append("Players: ").append(Groups.player.size()).append("\n");
                if(Groups.player.count(p->p.admin) > 0) {
                    admins.append("Online Admins: \n");// + Vars.playerGroup.all().count(p->p.isAdmin)+"\n");
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
            case prefix + "info":
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
            case prefix + "infores":
                //event.getChannel().sendMessage("not implemented yet...");
                if (!Vars.state.rules.waves){
                    event.getChannel().sendMessage("Only available when playing survivalmode!");
                    return;
                } else if(Groups.player.isEmpty()) {
                    event.getChannel().sendMessage("No players online! Could not check resources.");
                } else {
                    StringBuilder lijst3 = new StringBuilder();
                    lijst3.append("Amount of items in the core\n\n");
                    ItemModule core = Groups.player.first().core().items;
                    core.each((i, a) -> lijst3.append(i.name).append(" ").append(a).append("\n"));
                    new MessageBuilder().appendCode("", lijst3.toString()).send(event.getChannel());
                }
                break;
                //ban command
            case prefix + "ban":
                if (moderator == null) {
                    if (event.isPrivateMessage()) return;
                    event.getChannel().sendMessage("You do not have permission to use this command");
                    return;
                }
                if (!hasPermission(moderator, event)) return;
                if (incoming_msg.length < 2) {
                    event.getChannel().sendMessage("Please specify a player to ban");
                    return;
                }
                String[] msg4 = (event.getMessageContent().replace('\n', ' ')).split("\\s+", 2);
                Player player = Groups.player.find(p -> p.name.equalsIgnoreCase(msg4[1]));
                if (player == null) {
                    event.getChannel().sendMessage("Player not found");
                    return;
                }
                player.con.kick("You have been banned from the server");
                event.getChannel().sendMessage("Player banned");
                break;
            case prefix + "help":
                event.getChannel().sendMessage("Commands: \n" +
                        "```\n" + prefix +
                        "chat <message> - send a message to the server chat\n" + prefix +
                        "players - list all the players online\n" + prefix +
                        "info - get info about the server\n" + prefix +
                        "infores - get info about the resources in the core\n" + prefix +
                        "maps - list all maps on the server\n" + prefix +
                        "help - list all the commands\n" + prefix +
                        "```");
                break;
            case prefix + "cat":
                event.getChannel().sendMessage("https://cataas.com/cat");
                break;
            default:
                break;
        }
    }
}
