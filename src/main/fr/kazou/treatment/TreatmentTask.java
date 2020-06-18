package fr.kazou.treatment;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;

public class TreatmentTask extends PluginTask<Treatment> {

    private Player p;
    public TreatmentTask(Player p) {
        super(Treatment.getInstance());
        this.p = p;
    }

    @Override
    public void onRun(int i) {
        if(!p.isOnline()){
            Server.getInstance().getLogger().alert("[TASK] " + p.getPlayer().getName() + " disconnect.");
            this.cancel();
        }
        if(Treatment.getInstance().isTreatment(p)){
            if(!TreatmentTaskTransform.isWaiting(p)){
                String[] item = Treatment.getInstance().itema;
                for(int s = 0; s<=8; s++){
                    if(p.getInventory().getItem(s).getId() == Integer.parseInt(item[0]) && p.getInventory().getItem(s).getDamage() == Integer.parseInt(item[1]) && p.getInventory().getItem(s).getCount() >= Integer.parseInt(item[2])){
                        p.sendMessage(Treatment.getInstance().getLanguage("start_treatment").replace("[time]", String.valueOf(Treatment.getInstance().time)));
                        Treatment.getInstance().getServer().getScheduler().scheduleRepeatingTask(new TreatmentTaskTransform(p, s), 20);
                        return;
                    }
                }
                if(Treatment.getInstance().msg_no_item) {
                    p.sendMessage(Treatment.getInstance().getLanguage("no_item_hotbar"));
                }
            }
        }
    }
}
