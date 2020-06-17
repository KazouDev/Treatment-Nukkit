package fr.kazou.treatment.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import fr.kazou.treatment.Treatment;

public class treatmentCommand extends Command {
    public treatmentCommand() {
        super("treatment");
        setDescription("/t pos <1/2/get> / item <a/b> / reload");
        setAliases(new String[]{"t"});
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("pos|item|reload", CommandParamType.STRING, false),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("treatment.manage")) {
                if (args.length < 1) {
                    p.sendMessage("§cNo permission's.");
                }
                switch (args[0]) {
                    case "reload":
                        Treatment.getInstance().reloadConfig();
                        Treatment.getInstance().setupConfig();
                        p.sendMessage(Treatment.prefix + " Config Reloaded !");
                        break;
                    case "item":
                        if (args.length != 2){ p.sendMessage("§c/t item <a/b>"); return false;}
                        if (args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("b")) {
                            Treatment.getInstance().getConfig().set("item_" + args[1], p.getInventory().getItemInHand().getId());
                            Treatment.getInstance().getConfig().save();
                            p.sendMessage(Treatment.prefix + " Item_ " + args[1] + " -> Name " + p.getInventory().getItemInHand().getName() + ", ID:" + p.getInventory().getItemInHand().getId());
                            return false;
                        }
                        break;
                    case "pos":
                        if (args.length != 2){ p.sendMessage("§c/t pos <1/2/get>");return false;}
                        if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2")) {
                            Treatment.getInstance().getConfig().set("Area.p" + args[1], (int) p.getLocation().x + ":" + (int) p.getLocation().z + ":" + p.getLevel().getName());
                            Treatment.getInstance().getConfig().save();
                            p.sendMessage(Treatment.prefix + " Position " + args[1] + " -> x: " + (int) p.getLocation().x + ", z:" + (int) p.getLocation().z + " world: " + p.getLevel().getName());
                        } else if(args[1].equalsIgnoreCase("get")){
                            String[] p1 = Treatment.getInstance().getConfig().getString("Area.p1").split(":");
                            String[] p2 = Treatment.getInstance().getConfig().getString("Area.p2").split(":");

                            p.sendMessage(Treatment.prefix + " Position 1" + " -> x: " + p1[0] + ", z:" + p1[1] + " world: " + p1[2]);
                            p.sendMessage(Treatment.prefix + " Position 2" + " -> x: " + p2[0] + ", z:" + p2[1] + " world: " + p2[2]);
                        } else {
                            p.sendMessage("/t pos <1/2/get>");
                        }
                        break;
                    default:
                        p.sendMessage("§c/t pos <1/2/get> / item <a/b> / reload");
                        break;

                }
            } else {
                p.sendMessage("§cNo permission's");
            }
        }
        return false;
    }
}
