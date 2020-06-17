package fr.kazou.treatment.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import fr.kazou.treatment.Treatment;

import java.util.concurrent.TransferQueue;

public class treatmentCommand extends Command {
    public treatmentCommand() {
        super("treatment");
        setDescription("/t pos <1/2/get> / item <a/b> / reload");
        setAliases(new String[]{"t"});
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("pos|item|reload|time", CommandParamType.STRING, false),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("treatment.manage")) {
                if (args.length < 1) {
                    p.sendMessage("§c/t pos <1/2/get> / item <a/b/get> / time <get/set> (time) / reload");
                    return false;
                }
                switch (args[0]) {
                    case "reload":
                        Treatment.getInstance().reloadConfig();
                        Treatment.getInstance().setupConfig();
                        p.sendMessage(Treatment.prefix + " Config Reloaded !");
                        break;
                    case "item":
                        if (args.length != 2){ p.sendMessage("§c/t item <a/b/get>"); return false;}
                        String[] itema = Treatment.getInstance().itema;
                        String[] itemb = Treatment.getInstance().itemb;
                        if (args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("b")) {
                            Treatment.getInstance().getConfig().set("item_" + args[1], p.getInventory().getItemInHand().getId()+":"+p.getInventory().getItemInHand().getDamage()+":"+p.getInventory().getItemInHand().getCount());
                            Treatment.getInstance().getConfig().save();
                            p.sendMessage(Treatment.prefix + " Item_ " + args[1] + " -> Name " + p.getInventory().getItemInHand().getName() + ", ID:" + p.getInventory().getItemInHand().getId());
                            return false;
                        } else if(args[1].equalsIgnoreCase("get")){
                            p.sendMessage(Treatment.prefix + " Item A: " + Item.get(Integer.parseInt(itema[0]), Integer.parseInt(itema[1])).getName() + " ("+ Integer.parseInt(itema[2]) +") -> Item B: " + Item.get(Integer.parseInt(itemb[0]), Integer.parseInt(itemb[1])).getName() + " ("+ Integer.parseInt(itemb[2]) + ")");
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
                    case "time":
                        if(args.length < 2 || args.length > 3){p.sendMessage("§c/t time <get/set> (time)"); return false;}
                        if(args[1].equalsIgnoreCase("get")){
                            p.sendMessage(Treatment.prefix + " Time to transform: " + Treatment.getInstance().getConfig().getInt("time"));
                            return false;
                        } else if(args[1].equalsIgnoreCase("set")){
                            if(args.length == 3){
                                try{
                                    int t = Integer.parseInt(args[2]);
                                    Treatment.getInstance().getConfig().set("time", t);
                                    Treatment.getInstance().getConfig().save();
                                    p.sendMessage(Treatment.prefix + " Time set to " + t);
                                    return false;
                                } catch (NumberFormatException e){
                                    p.sendMessage("§c/t time <get/set> (number)");
                                }
                            } else {
                                p.sendMessage("§c/t time <get/set> (time)");
                                return false;
                            }
                        }
                        break;
                    default:
                        p.sendMessage("§c/t pos <1/2/get> / item <a/b/get> / time <get/set> (time) / reload");
                        break;

                }
            } else {
                p.sendMessage("§cNo permission's");
                return false;
            }
        }
        return false;
    }
}
