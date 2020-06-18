package fr.kazou.treatment;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.PluginTask;
import fr.kazou.treatment.event.TreatmentTransformEvent;

import java.util.ArrayList;

public class TreatmentTaskTransform extends PluginTask<Treatment> {
    private Player p;
    private int delay = 0;
    private int slot;
    private static ArrayList<Player> inWaiting = new ArrayList<>();
    String[] id = Treatment.getInstance().itema;
    String[] item = Treatment.getInstance().itemb;

    public TreatmentTaskTransform(Player p, int slot) {
        super(Treatment.getInstance());
        this.p = p;
        this.slot = slot;
        inWaiting.add(p);
    }

    @Override
    public void onRun(int i) {
        if(delay < Treatment.getInstance().time){
           if(!Treatment.getInstance().isTreatment(p) || p.getInventory().getItem(slot).getId() != Integer.parseInt(id[0])){
               p.sendMessage(Treatment.getInstance().getLanguage("treatment_cancelled"));
               inWaiting.remove(p);
               this.cancel();
           }
        }

        if(delay == Treatment.getInstance().time){
            TreatmentTransformEvent ev = new TreatmentTransformEvent(p);
            Server.getInstance().getPluginManager().callEvent(ev);
                if(ev.isCancelled()){
                    inWaiting.remove(p);
                    this.cancel();
                    return;
                }
            if(p.getInventory().getItem(slot).getId() != Integer.parseInt(id[0])){
                p.sendMessage(Treatment.getInstance().getLanguage("item_moved"));
                inWaiting.remove(p);
                this.cancel();
            } else {
                if(p.getInventory().canAddItem(Item.get(Integer.parseInt(id[0]), Integer.parseInt(id[1]), Integer.parseInt(id[2])))){
                    if(p.getInventory().getItem(slot).getCount() >= 2){
                        p.getInventory().setItem(slot, Item.get(p.getInventory().getItem(slot).getId(), p.getInventory().getItem(slot).getDamage(), p.getInventory().getItem(slot).getCount() - Integer.parseInt(id[2])));
                        p.getInventory().addItem(Item.get(Integer.parseInt(item[0]), Integer.parseInt(item[1]), Integer.parseInt(item[2])));
                        p.sendMessage(Treatment.getInstance().getLanguage("end_treatment"));
                        inWaiting.remove(p);
                        this.cancel();
                    } else {
                        p.getInventory().setItem(slot, Item.get(0));
                        p.getInventory().addItem(Item.get(Integer.parseInt(item[0]), Integer.parseInt(item[1]), Integer.parseInt(item[2])));
                        p.sendMessage(Treatment.getInstance().getLanguage("end_treatment"));
                        inWaiting.remove(p);
                        this.cancel();
                    }
                }
            }
        }
        delay++;

    }

    public static boolean isWaiting(Player p){
        return inWaiting.contains(p);
    }
}
